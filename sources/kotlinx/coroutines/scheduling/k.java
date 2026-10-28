package kotlinx.coroutines.scheduling;

import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class k extends i {
    public final Runnable d;

    public k(Runnable runnable, long j2, j jVar) {
        super(j2, jVar);
        this.d = runnable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            this.d.run();
        } finally {
            this.f484c.getClass();
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Task[");
        Runnable runnable = this.d;
        sb.append(runnable.getClass().getSimpleName());
        sb.append('@');
        sb.append(y.m(runnable));
        sb.append(", ");
        sb.append(this.b);
        sb.append(", ");
        sb.append(this.f484c);
        sb.append(']');
        return sb.toString();
    }
}
