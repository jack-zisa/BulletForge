package dev.creoii.bulletforge.util;

import java.util.function.Function;

public enum TimePeriod {
    MILLISECOND("ms", Function.identity()),
    SECOND("s", aLong -> aLong * 1_000L),
    MINUTE("m", aLong -> aLong * 60_000L),
    HOUR("h", aLong -> aLong * 3_600_000L);

    private final String suffix;
    private final Function<Long, Long> toMs;

    TimePeriod(String suffix, Function<Long, Long> toMs) {
        this.suffix = suffix;
        this.toMs = toMs;
    }

    public String suffix() {
        return suffix;
    }

    public long toMs(long l) {
        return toMs.apply(l);
    }
}
