package androidx.core.util;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class b implements Predicate {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f47a;
    public final /* synthetic */ Object b;

    public /* synthetic */ b(int i2, Object obj) {
        this.f47a = i2;
        this.b = obj;
    }

    @Override // androidx.core.util.Predicate
    public final /* synthetic */ Predicate and(Predicate predicate) {
        int i2 = this.f47a;
        return c.a(this, predicate);
    }

    @Override // androidx.core.util.Predicate
    public final /* synthetic */ Predicate negate() {
        switch (this.f47a) {
            case 0:
                break;
        }
        return c.b(this);
    }

    @Override // androidx.core.util.Predicate
    public final /* synthetic */ Predicate or(Predicate predicate) {
        int i2 = this.f47a;
        return c.c(this, predicate);
    }

    @Override // androidx.core.util.Predicate
    public final boolean test(Object obj) {
        int i2 = this.f47a;
        Object obj2 = this.b;
        switch (i2) {
            case 0:
                return obj2.equals(obj);
            default:
                return c.e((Predicate) obj2, obj);
        }
    }
}
