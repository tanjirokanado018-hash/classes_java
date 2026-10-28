package u;

import java.util.Iterator;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class f implements g {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f509a = 1;
    public final p.l b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final Object f510c;

    public f(g gVar, p.l transformer) {
        kotlin.jvm.internal.k.g(transformer, "transformer");
        this.f510c = gVar;
        this.b = transformer;
    }

    @Override // u.g
    public final Iterator iterator() {
        switch (this.f509a) {
            case 0:
                return new e(this);
            default:
                return new o(this);
        }
    }

    public f(l lVar, p.l getNextValue) {
        kotlin.jvm.internal.k.g(getNextValue, "getNextValue");
        this.f510c = lVar;
        this.b = getNextValue;
    }
}
