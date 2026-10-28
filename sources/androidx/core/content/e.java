package androidx.core.content;

import android.content.ComponentName;
import android.content.UriMatcher;
import android.net.Uri;
import androidx.core.util.Predicate;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class e implements Predicate {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f28a;
    public final /* synthetic */ Object b;

    public /* synthetic */ e(int i2, Object obj) {
        this.f28a = i2;
        this.b = obj;
    }

    @Override // androidx.core.util.Predicate
    public final /* synthetic */ Predicate and(Predicate predicate) {
        int i2 = this.f28a;
        return androidx.core.util.c.a(this, predicate);
    }

    @Override // androidx.core.util.Predicate
    public final /* synthetic */ Predicate negate() {
        switch (this.f28a) {
            case 0:
                break;
        }
        return androidx.core.util.c.b(this);
    }

    @Override // androidx.core.util.Predicate
    public final /* synthetic */ Predicate or(Predicate predicate) {
        int i2 = this.f28a;
        return androidx.core.util.c.c(this, predicate);
    }

    @Override // androidx.core.util.Predicate
    public final boolean test(Object obj) {
        int i2 = this.f28a;
        Object obj2 = this.b;
        switch (i2) {
            case 0:
                return ((ComponentName) obj2).equals((ComponentName) obj);
            default:
                return UriMatcherCompat.lambda$asPredicate$0((UriMatcher) obj2, (Uri) obj);
        }
    }
}
