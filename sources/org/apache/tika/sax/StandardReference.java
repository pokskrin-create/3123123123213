package org.apache.tika.sax;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import org.apache.tika.utils.StringUtils;

/* loaded from: classes4.dex */
public class StandardReference {
    private String identifier;
    private String mainOrganization;
    private double score;
    private String secondOrganization;
    private String separator;

    private StandardReference(String str, String str2, String str3, String str4, double d) {
        this.mainOrganization = str;
        this.separator = str2;
        this.secondOrganization = str3;
        this.identifier = str4;
        this.score = d;
    }

    public String getMainOrganizationAcronym() {
        return this.mainOrganization;
    }

    public void setMainOrganizationAcronym(String str) {
        this.mainOrganization = str;
    }

    public String getSeparator() {
        return this.separator;
    }

    public void setSeparator(String str) {
        this.separator = str;
    }

    public String getSecondOrganizationAcronym() {
        return this.secondOrganization;
    }

    public void setSecondOrganizationAcronym(String str) {
        this.secondOrganization = str;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(String str) {
        this.identifier = str;
    }

    public double getScore() {
        return this.score;
    }

    public void setScore(double d) {
        this.score = d;
    }

    public String toString() {
        String str = this.mainOrganization;
        String str2 = this.separator;
        if (str2 != null && !str2.isEmpty()) {
            str = str + this.separator + this.secondOrganization;
        }
        return str + StringUtils.SPACE + this.identifier;
    }

    public static class StandardReferenceBuilder {
        private final String identifier;
        private final String mainOrganization;
        private String separator = null;
        private String secondOrganization = null;
        private double score = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;

        public StandardReferenceBuilder(String str, String str2) {
            this.mainOrganization = str;
            this.identifier = str2;
        }

        public StandardReferenceBuilder setSecondOrganization(String str, String str2) {
            this.separator = str;
            this.secondOrganization = str2;
            return this;
        }

        public StandardReferenceBuilder setScore(double d) {
            this.score = d;
            return this;
        }

        public StandardReference build() {
            return new StandardReference(this.mainOrganization, this.separator, this.secondOrganization, this.identifier, this.score);
        }
    }
}
