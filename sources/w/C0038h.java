package w;

import java.util.concurrent.CancellationException;

/* JADX INFO: renamed from: w.h, reason: case insensitive filesystem */
/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class C0038h extends at {

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final C0036f f542f;

    public C0038h(C0036f c0036f) {
        this.f542f = c0036f;
    }

    @Override // p.l
    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
        m((Throwable) obj);
        return g.i.f423a;
    }

    @Override // w.au
    public final void m(Throwable th) {
        ag agVar;
        ay ayVarL = l();
        C0036f c0036f = this.f542f;
        c0036f.getClass();
        CancellationException cancellationExceptionL = ayVarL.l();
        if (c0036f.o() && ((kotlinx.coroutines.internal.d) c0036f.f538e).h(cancellationExceptionL)) {
            return;
        }
        c0036f.i(cancellationExceptionL);
        if (c0036f.o() || (agVar = c0036f.f540g) == null) {
            return;
        }
        agVar.b();
        c0036f.f540g = B.b;
    }
}
