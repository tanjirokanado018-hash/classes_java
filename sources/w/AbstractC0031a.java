package w;

/* JADX INFO: renamed from: w.a, reason: case insensitive filesystem */
/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class AbstractC0031a extends ay implements i.e, w {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final i.j f519c;

    public AbstractC0031a(i.j jVar, boolean z2) {
        super(z2);
        t((ar) jVar.get(t.f551c));
        this.f519c = jVar.plus(this);
    }

    @Override // w.ay, w.ar
    public final boolean a() {
        return super.a();
    }

    @Override // w.ay
    public final String g() {
        return getClass().getSimpleName().concat(" was cancelled");
    }

    @Override // i.e
    public final i.j getContext() {
        return this.f519c;
    }

    @Override // w.w
    public final i.j getCoroutineContext() {
        return this.f519c;
    }

    @Override // i.e
    public final void resumeWith(Object obj) {
        Throwable thA = g.f.a(obj);
        if (thA != null) {
            obj = new m(false, thA);
        }
        Object objV = v(obj);
        if (objV == y.f554c) {
            return;
        }
        d(objV);
    }

    @Override // w.ay
    public final void s(g.b bVar) {
        kotlin.jvm.internal.k.j(this.f519c, bVar);
    }

    @Override // w.ay
    public final String w() {
        return super.w();
    }

    @Override // w.ay
    public final void z(Object obj) {
        if (obj instanceof m) {
            Throwable th = ((m) obj).f547a;
        }
    }
}
