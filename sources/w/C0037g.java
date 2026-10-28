package w;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/* JADX INFO: renamed from: w.g, reason: case insensitive filesystem */
/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class C0037g extends m {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static final /* synthetic */ AtomicIntegerFieldUpdater f541c = AtomicIntegerFieldUpdater.newUpdater(C0037g.class, "_resumed");
    private volatile /* synthetic */ int _resumed;

    public C0037g(i.e eVar, Throwable th, boolean z2) {
        super(z2, th);
        this._resumed = 0;
    }
}
