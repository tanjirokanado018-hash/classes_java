package w;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class F extends ay {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final boolean f516c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public F(ar arVar) {
        super(true);
        boolean z2 = true;
        t(arVar);
        InterfaceC0039i interfaceC0039iP = p();
        C0040j c0040j = interfaceC0039iP instanceof C0040j ? (C0040j) interfaceC0039iP : null;
        if (c0040j != null) {
            do {
                ay ayVarL = c0040j.l();
                if (!ayVarL.m()) {
                    InterfaceC0039i interfaceC0039iP2 = ayVarL.p();
                    c0040j = interfaceC0039iP2 instanceof C0040j ? (C0040j) interfaceC0039iP2 : null;
                }
            } while (c0040j != null);
            z2 = false;
        } else {
            z2 = false;
        }
        this.f516c = z2;
    }

    @Override // w.ay
    public final boolean h(Throwable th) {
        return false;
    }

    @Override // w.ay
    public final boolean m() {
        return this.f516c;
    }

    @Override // w.ay
    public final /* bridge */ /* synthetic */ boolean n() {
        return true;
    }
}
