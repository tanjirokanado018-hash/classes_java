package w;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class av extends au {

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final ay f529f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public final aw f530g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public final C0040j f531h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public final Object f532i;

    public av(ay ayVar, aw awVar, C0040j c0040j, Object obj) {
        this.f529f = ayVar;
        this.f530g = awVar;
        this.f531h = c0040j;
        this.f532i = obj;
    }

    @Override // p.l
    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
        m((Throwable) obj);
        return g.i.f423a;
    }

    @Override // w.au
    public final void m(Throwable th) {
        ay ayVar = this.f529f;
        ayVar.getClass();
        C0040j c0040jX = ay.x(this.f531h);
        aw awVar = this.f530g;
        Object obj = this.f532i;
        if (c0040jX != null) {
            while (kotlin.jvm.internal.k.k(c0040jX.f543f, false, new av(ayVar, awVar, c0040jX, obj), 1) == B.b) {
                c0040jX = ay.x(c0040jX);
                if (c0040jX == null) {
                }
            }
            return;
        }
        ayVar.c(ayVar.k(awVar, obj));
    }
}
