package com.google.android.material.datepicker;

import androidx.annotation.Nullable;
import java.util.Calendar;
import java.util.TimeZone;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
class TimeSource {
    private static final TimeSource SYSTEM_TIME_SOURCE = new TimeSource(null, null);

    @Nullable
    private final Long fixedTimeMs;

    @Nullable
    private final TimeZone fixedTimeZone;

    private TimeSource(@Nullable Long l2, @Nullable TimeZone timeZone) {
        this.fixedTimeMs = l2;
        this.fixedTimeZone = timeZone;
    }

    public static TimeSource fixed(long j2) {
        return new TimeSource(Long.valueOf(j2), null);
    }

    public static TimeSource system() {
        return SYSTEM_TIME_SOURCE;
    }

    public Calendar now() {
        return now(this.fixedTimeZone);
    }

    public static TimeSource fixed(long j2, @Nullable TimeZone timeZone) {
        return new TimeSource(Long.valueOf(j2), timeZone);
    }

    public Calendar now(@Nullable TimeZone timeZone) {
        Calendar calendar = timeZone == null ? Calendar.getInstance() : Calendar.getInstance(timeZone);
        Long l2 = this.fixedTimeMs;
        if (l2 != null) {
            calendar.setTimeInMillis(l2.longValue());
        }
        return calendar;
    }
}
