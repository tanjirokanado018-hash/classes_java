package kotlinx.coroutines.scheduling;

import androidx.core.location.LocationRequestCompat;
import java.util.concurrent.TimeUnit;
import kotlinx.coroutines.internal.r;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class l {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final long f486a = kotlin.jvm.internal.k.t(100000, 1, LocationRequestCompat.PASSIVE_INTERVAL, "kotlinx.coroutines.scheduler.resolution.ns");
    public static final int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static final int f487c;
    public static final long d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public static final f f488e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public static final j f489f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public static final j f490g;

    static {
        int i2 = r.f462a;
        if (i2 < 2) {
            i2 = 2;
        }
        b = kotlin.jvm.internal.k.u("kotlinx.coroutines.scheduler.core.pool.size", i2, 1, 0, 8);
        f487c = kotlin.jvm.internal.k.u("kotlinx.coroutines.scheduler.max.pool.size", 2097150, 0, 2097150, 4);
        d = TimeUnit.SECONDS.toNanos(kotlin.jvm.internal.k.t(60L, 1L, LocationRequestCompat.PASSIVE_INTERVAL, "kotlinx.coroutines.scheduler.keep.alive.sec"));
        f488e = f.f483a;
        f489f = new j(0);
        f490g = new j(1);
    }
}
