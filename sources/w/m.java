package w;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class m {
    public static final /* synthetic */ AtomicIntegerFieldUpdater b = AtomicIntegerFieldUpdater.newUpdater(m.class, "_handled");
    private volatile /* synthetic */ int _handled;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Throwable f547a;

    public m(boolean z2, Throwable th) {
        this.f547a = th;
        this._handled = z2 ? 1 : 0;
    }

    public final String toString() {
        return getClass().getSimpleName() + '[' + this.f547a + ']';
    }
}
