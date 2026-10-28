package androidx.activity;

import androidx.annotation.GuardedBy;
import androidx.annotation.RestrictTo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class FullyDrawnReporter {
    private final Executor executor;
    private final Object lock;

    @GuardedBy("lock")
    private final List<p.a> onReportCallbacks;
    private final p.a reportFullyDrawn;

    @GuardedBy("lock")
    private boolean reportPosted;
    private final Runnable reportRunnable;

    @GuardedBy("lock")
    private boolean reportedFullyDrawn;

    @GuardedBy("lock")
    private int reporterCount;

    public FullyDrawnReporter(Executor executor, p.a reportFullyDrawn) {
        k.g(executor, "executor");
        k.g(reportFullyDrawn, "reportFullyDrawn");
        this.executor = executor;
        this.reportFullyDrawn = reportFullyDrawn;
        this.lock = new Object();
        this.onReportCallbacks = new ArrayList();
        this.reportRunnable = new d(3, this);
    }

    private final void postWhenReportersAreDone() {
        if (this.reportPosted || this.reporterCount != 0) {
            return;
        }
        this.reportPosted = true;
        this.executor.execute(this.reportRunnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void reportRunnable$lambda$2(FullyDrawnReporter this$0) {
        k.g(this$0, "this$0");
        synchronized (this$0.lock) {
            this$0.reportPosted = false;
            if (this$0.reporterCount == 0 && !this$0.reportedFullyDrawn) {
                this$0.reportFullyDrawn.invoke();
                this$0.fullyDrawnReported();
            }
        }
    }

    public final void addOnReportDrawnListener(p.a callback) {
        boolean z2;
        k.g(callback, "callback");
        synchronized (this.lock) {
            if (this.reportedFullyDrawn) {
                z2 = true;
            } else {
                this.onReportCallbacks.add(callback);
                z2 = false;
            }
        }
        if (z2) {
            callback.invoke();
        }
    }

    public final void addReporter() {
        synchronized (this.lock) {
            if (!this.reportedFullyDrawn) {
                this.reporterCount++;
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public final void fullyDrawnReported() {
        synchronized (this.lock) {
            try {
                this.reportedFullyDrawn = true;
                Iterator<T> it = this.onReportCallbacks.iterator();
                while (it.hasNext()) {
                    ((p.a) it.next()).invoke();
                }
                this.onReportCallbacks.clear();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isFullyDrawnReported() {
        boolean z2;
        synchronized (this.lock) {
            z2 = this.reportedFullyDrawn;
        }
        return z2;
    }

    public final void removeOnReportDrawnListener(p.a callback) {
        k.g(callback, "callback");
        synchronized (this.lock) {
            this.onReportCallbacks.remove(callback);
        }
    }

    public final void removeReporter() {
        int i2;
        synchronized (this.lock) {
            if (!this.reportedFullyDrawn && (i2 = this.reporterCount) > 0) {
                this.reporterCount = i2 - 1;
                postWhenReportersAreDone();
            }
        }
    }
}
