package i;

import p.l;
import w.q;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class b implements i {
    public final l b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final i f426c;

    public b(i baseKey, q qVar) {
        kotlin.jvm.internal.k.g(baseKey, "baseKey");
        this.b = qVar;
        this.f426c = baseKey instanceof b ? ((b) baseKey).f426c : baseKey;
    }
}
