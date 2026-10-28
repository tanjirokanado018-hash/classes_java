package w;

/* JADX INFO: renamed from: w.j, reason: case insensitive filesystem */
/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class C0040j extends at implements InterfaceC0039i {

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final InterfaceC0041k f543f;

    public C0040j(ay ayVar) {
        this.f543f = ayVar;
    }

    @Override // w.InterfaceC0039i
    public final boolean c(Throwable th) {
        return l().h(th);
    }

    @Override // p.l
    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
        m((Throwable) obj);
        return g.i.f423a;
    }

    @Override // w.au
    public final void m(Throwable th) {
        ((ay) this.f543f).e(l());
    }
}
