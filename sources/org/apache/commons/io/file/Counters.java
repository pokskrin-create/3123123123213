package org.apache.commons.io.file;

import java.math.BigInteger;
import java.util.Objects;

/* loaded from: classes4.dex */
public class Counters {

    public interface Counter {
        void add(long j);

        long get();

        BigInteger getBigInteger();

        Long getLong();

        void increment();

        default void reset() {
        }
    }

    public interface PathCounters {
        Counter getByteCounter();

        Counter getDirectoryCounter();

        Counter getFileCounter();

        default void reset() {
        }
    }

    private static class AbstractPathCounters implements PathCounters {
        private final Counter byteCounter;
        private final Counter directoryCounter;
        private final Counter fileCounter;

        protected AbstractPathCounters(Counter counter, Counter counter2, Counter counter3) {
            this.byteCounter = counter;
            this.directoryCounter = counter2;
            this.fileCounter = counter3;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AbstractPathCounters)) {
                return false;
            }
            AbstractPathCounters abstractPathCounters = (AbstractPathCounters) obj;
            return Objects.equals(this.byteCounter, abstractPathCounters.byteCounter) && Objects.equals(this.directoryCounter, abstractPathCounters.directoryCounter) && Objects.equals(this.fileCounter, abstractPathCounters.fileCounter);
        }

        @Override // org.apache.commons.io.file.Counters.PathCounters
        public Counter getByteCounter() {
            return this.byteCounter;
        }

        @Override // org.apache.commons.io.file.Counters.PathCounters
        public Counter getDirectoryCounter() {
            return this.directoryCounter;
        }

        @Override // org.apache.commons.io.file.Counters.PathCounters
        public Counter getFileCounter() {
            return this.fileCounter;
        }

        public int hashCode() {
            return Objects.hash(this.byteCounter, this.directoryCounter, this.fileCounter);
        }

        @Override // org.apache.commons.io.file.Counters.PathCounters
        public void reset() {
            this.byteCounter.reset();
            this.directoryCounter.reset();
            this.fileCounter.reset();
        }

        public String toString() {
            return String.format("%,d files, %,d directories, %,d bytes", Long.valueOf(this.fileCounter.get()), Long.valueOf(this.directoryCounter.get()), Long.valueOf(this.byteCounter.get()));
        }
    }

    private static final class BigIntegerCounter implements Counter {
        private BigInteger value;

        private BigIntegerCounter() {
            this.value = BigInteger.ZERO;
        }

        @Override // org.apache.commons.io.file.Counters.Counter
        public void add(long j) {
            this.value = this.value.add(BigInteger.valueOf(j));
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof Counter) {
                return Objects.equals(this.value, ((Counter) obj).getBigInteger());
            }
            return false;
        }

        @Override // org.apache.commons.io.file.Counters.Counter
        public long get() {
            return this.value.longValueExact();
        }

        @Override // org.apache.commons.io.file.Counters.Counter
        public BigInteger getBigInteger() {
            return this.value;
        }

        @Override // org.apache.commons.io.file.Counters.Counter
        public Long getLong() {
            return Long.valueOf(this.value.longValueExact());
        }

        public int hashCode() {
            return Objects.hash(this.value);
        }

        @Override // org.apache.commons.io.file.Counters.Counter
        public void increment() {
            this.value = this.value.add(BigInteger.ONE);
        }

        @Override // org.apache.commons.io.file.Counters.Counter
        public void reset() {
            this.value = BigInteger.ZERO;
        }

        public String toString() {
            return this.value.toString();
        }
    }

    private static final class BigIntegerPathCounters extends AbstractPathCounters {
        protected BigIntegerPathCounters() {
            super(Counters.bigIntegerCounter(), Counters.bigIntegerCounter(), Counters.bigIntegerCounter());
        }
    }

    private static final class LongCounter implements Counter {
        private long value;

        private LongCounter() {
        }

        @Override // org.apache.commons.io.file.Counters.Counter
        public void add(long j) {
            this.value += j;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Counter) && this.value == ((Counter) obj).get();
        }

        @Override // org.apache.commons.io.file.Counters.Counter
        public long get() {
            return this.value;
        }

        @Override // org.apache.commons.io.file.Counters.Counter
        public BigInteger getBigInteger() {
            return BigInteger.valueOf(this.value);
        }

        @Override // org.apache.commons.io.file.Counters.Counter
        public Long getLong() {
            return Long.valueOf(this.value);
        }

        public int hashCode() {
            return Objects.hash(Long.valueOf(this.value));
        }

        @Override // org.apache.commons.io.file.Counters.Counter
        public void increment() {
            this.value++;
        }

        @Override // org.apache.commons.io.file.Counters.Counter
        public void reset() {
            this.value = 0L;
        }

        public String toString() {
            return Long.toString(this.value);
        }
    }

    private static final class LongPathCounters extends AbstractPathCounters {
        protected LongPathCounters() {
            super(Counters.longCounter(), Counters.longCounter(), Counters.longCounter());
        }
    }

    private static final class NoopCounter implements Counter {
        static final NoopCounter INSTANCE = new NoopCounter();

        @Override // org.apache.commons.io.file.Counters.Counter
        public void add(long j) {
        }

        @Override // org.apache.commons.io.file.Counters.Counter
        public long get() {
            return 0L;
        }

        @Override // org.apache.commons.io.file.Counters.Counter
        public void increment() {
        }

        private NoopCounter() {
        }

        @Override // org.apache.commons.io.file.Counters.Counter
        public BigInteger getBigInteger() {
            return BigInteger.ZERO;
        }

        @Override // org.apache.commons.io.file.Counters.Counter
        public Long getLong() {
            return 0L;
        }

        public String toString() {
            return "0";
        }
    }

    private static final class NoopPathCounters extends AbstractPathCounters {
        static final NoopPathCounters INSTANCE = new NoopPathCounters();

        private NoopPathCounters() {
            super(Counters.noopCounter(), Counters.noopCounter(), Counters.noopCounter());
        }
    }

    public static Counter bigIntegerCounter() {
        return new BigIntegerCounter();
    }

    public static PathCounters bigIntegerPathCounters() {
        return new BigIntegerPathCounters();
    }

    public static Counter longCounter() {
        return new LongCounter();
    }

    public static PathCounters longPathCounters() {
        return new LongPathCounters();
    }

    public static Counter noopCounter() {
        return NoopCounter.INSTANCE;
    }

    public static PathCounters noopPathCounters() {
        return NoopPathCounters.INSTANCE;
    }

    @Deprecated
    public Counters() {
    }
}
