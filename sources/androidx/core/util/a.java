package androidx.core.util;

import androidx.core.content.IntentSanitizer;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class a implements Predicate {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f45a;
    public final /* synthetic */ Predicate b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f46c;

    public /* synthetic */ a(Predicate predicate, Predicate predicate2, int i2) {
        this.f45a = i2;
        this.b = predicate;
        this.f46c = predicate2;
    }

    @Override // androidx.core.util.Predicate
    public final /* synthetic */ Predicate and(Predicate predicate) {
        int i2 = this.f45a;
        return c.a(this, predicate);
    }

    @Override // androidx.core.util.Predicate
    public final /* synthetic */ Predicate negate() {
        switch (this.f45a) {
            case 0:
                break;
            case 1:
                break;
        }
        return c.b(this);
    }

    @Override // androidx.core.util.Predicate
    public final /* synthetic */ Predicate or(Predicate predicate) {
        int i2 = this.f45a;
        return c.c(this, predicate);
    }

    @Override // androidx.core.util.Predicate
    public final boolean test(Object obj) {
        int i2 = this.f45a;
        Predicate predicate = this.b;
        Object obj2 = this.f46c;
        switch (i2) {
            case 0:
                return c.f(predicate, (Predicate) obj2, obj);
            case 1:
                return c.d(predicate, (Predicate) obj2, obj);
            default:
                return IntentSanitizer.Builder.lambda$allowExtra$13((Class) obj2, predicate, obj);
        }
    }

    public /* synthetic */ a(Class cls, Predicate predicate) {
        this.f45a = 2;
        this.f46c = cls;
        this.b = predicate;
    }
}
