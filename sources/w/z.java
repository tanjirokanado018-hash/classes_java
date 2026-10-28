package w;

import androidx.core.location.LocationRequestCompat;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class z extends aj implements Runnable {
    private static volatile Thread _thread;
    private static volatile int debugStatus;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public static final z f559f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public static final long f560g;

    static {
        Long l2;
        z zVar = new z();
        f559f = zVar;
        zVar.e(false);
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        try {
            l2 = Long.getLong("kotlinx.coroutines.DefaultExecutor.keepAlive", 1000L);
        } catch (SecurityException unused) {
            l2 = 1000L;
        }
        f560g = timeUnit.toNanos(l2.longValue());
    }

    @Override // w.ak
    public final Thread d() {
        Thread thread = _thread;
        if (thread == null) {
            synchronized (this) {
                thread = _thread;
                if (thread == null) {
                    thread = new Thread(this, "kotlinx.coroutines.DefaultExecutor");
                    _thread = thread;
                    thread.setDaemon(true);
                    thread.start();
                }
            }
        }
        return thread;
    }

    @Override // w.aj, w.ak
    public final void g() {
        debugStatus = 4;
        super.g();
    }

    @Override // w.aj
    public final void h(Runnable runnable) {
        if (debugStatus == 4) {
            throw new RejectedExecutionException("DefaultExecutor was shut down. This error indicates that Dispatchers.shutdown() was invoked prior to completion of exiting coroutines, leaving coroutines in incomplete state. Please refer to Dispatchers.shutdown documentation for more details");
        }
        super.h(runnable);
    }

    public final synchronized void m() {
        int i2 = debugStatus;
        if (i2 == 2 || i2 == 3) {
            debugStatus = 3;
            l();
            notifyAll();
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        H.f517a.set(this);
        try {
            synchronized (this) {
                int i2 = debugStatus;
                if (i2 != 2 && i2 != 3) {
                    debugStatus = 1;
                    notifyAll();
                    long j2 = Long.MAX_VALUE;
                    while (true) {
                        Thread.interrupted();
                        long jK = k();
                        if (jK == LocationRequestCompat.PASSIVE_INTERVAL) {
                            long jNanoTime = System.nanoTime();
                            if (j2 == LocationRequestCompat.PASSIVE_INTERVAL) {
                                j2 = f560g + jNanoTime;
                            }
                            long j3 = j2 - jNanoTime;
                            if (j3 <= 0) {
                                _thread = null;
                                m();
                                if (j()) {
                                    return;
                                }
                                d();
                                return;
                            }
                            if (jK > j3) {
                                jK = j3;
                            }
                        } else {
                            j2 = Long.MAX_VALUE;
                        }
                        if (jK > 0) {
                            int i3 = debugStatus;
                            if (i3 == 2 || i3 == 3) {
                                break;
                                break;
                            }
                            LockSupport.parkNanos(this, jK);
                        }
                    }
                    _thread = null;
                    m();
                    if (j()) {
                        return;
                    }
                    d();
                    return;
                }
                _thread = null;
                m();
                if (j()) {
                    return;
                }
                d();
            }
        } catch (Throwable th) {
            _thread = null;
            m();
            if (!j()) {
                d();
            }
            throw th;
        }
    }
}
