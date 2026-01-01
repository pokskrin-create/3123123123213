package org.apache.tika.pipes;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.tika.config.Field;
import org.apache.tika.config.Initializable;
import org.apache.tika.config.InitializableProblemHandler;
import org.apache.tika.config.Param;
import org.apache.tika.exception.TikaConfigException;
import org.apache.tika.pipes.PipesResult;

/* loaded from: classes4.dex */
public abstract class PipesReporterBase extends PipesReporter implements Initializable {
    private StatusFilter statusFilter;
    private final Set<PipesResult.STATUS> includes = new HashSet();
    private final Set<PipesResult.STATUS> excludes = new HashSet();

    @Override // org.apache.tika.config.Initializable
    public void checkInitialization(InitializableProblemHandler initializableProblemHandler) throws TikaConfigException {
    }

    @Override // org.apache.tika.config.Initializable
    public void initialize(Map<String, Param> map) throws TikaConfigException {
        this.statusFilter = buildStatusFilter(this.includes, this.excludes);
    }

    private StatusFilter buildStatusFilter(Set<PipesResult.STATUS> set, Set<PipesResult.STATUS> set2) throws TikaConfigException {
        if (set.size() > 0 && set2.size() > 0) {
            throw new TikaConfigException("Only one of includes and excludes may have any contents");
        }
        if (set.size() > 0) {
            return new IncludesFilter(set);
        }
        if (set2.size() > 0) {
            return new ExcludesFilter(set2);
        }
        return new AcceptAllFilter();
    }

    public boolean accept(PipesResult.STATUS status) {
        return this.statusFilter.accept(status);
    }

    @Field
    public void setIncludes(List<String> list) throws TikaConfigException {
        for (String str : list) {
            try {
                this.includes.add(PipesResult.STATUS.valueOf(str));
            } catch (IllegalArgumentException e) {
                throw new TikaConfigException("I regret I don't recognize " + str + ". I only understand: " + getOptionString(), e);
            }
        }
    }

    @Field
    public void setExcludes(List<String> list) throws TikaConfigException {
        for (String str : list) {
            try {
                this.excludes.add(PipesResult.STATUS.valueOf(str));
            } catch (IllegalArgumentException e) {
                throw new TikaConfigException("I regret I don't recognize " + str + ". I only understand: " + getOptionString(), e);
            }
        }
    }

    private String getOptionString() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (PipesResult.STATUS status : PipesResult.STATUS.values()) {
            i++;
            if (i > 1) {
                sb.append(", ");
            }
            sb.append(status.name());
        }
        return sb.toString();
    }

    private static abstract class StatusFilter {
        abstract boolean accept(PipesResult.STATUS status);

        private StatusFilter() {
        }
    }

    private static class IncludesFilter extends StatusFilter {
        private final Set<PipesResult.STATUS> includes;

        private IncludesFilter(Set<PipesResult.STATUS> set) {
            super();
            this.includes = set;
        }

        @Override // org.apache.tika.pipes.PipesReporterBase.StatusFilter
        boolean accept(PipesResult.STATUS status) {
            return this.includes.contains(status);
        }
    }

    private static class ExcludesFilter extends StatusFilter {
        private final Set<PipesResult.STATUS> excludes;

        ExcludesFilter(Set<PipesResult.STATUS> set) {
            super();
            this.excludes = set;
        }

        @Override // org.apache.tika.pipes.PipesReporterBase.StatusFilter
        boolean accept(PipesResult.STATUS status) {
            return !this.excludes.contains(status);
        }
    }

    private static class AcceptAllFilter extends StatusFilter {
        @Override // org.apache.tika.pipes.PipesReporterBase.StatusFilter
        boolean accept(PipesResult.STATUS status) {
            return true;
        }

        private AcceptAllFilter() {
            super();
        }
    }
}
