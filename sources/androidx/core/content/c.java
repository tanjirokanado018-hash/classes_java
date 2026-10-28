package androidx.core.content;

import android.content.ComponentName;
import android.net.Uri;
import androidx.core.util.Predicate;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class c implements Predicate {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f26a;
    public final /* synthetic */ String b;

    public /* synthetic */ c(String str, int i2) {
        this.f26a = i2;
        this.b = str;
    }

    @Override // androidx.core.util.Predicate
    public final /* synthetic */ Predicate and(Predicate predicate) {
        int i2 = this.f26a;
        return androidx.core.util.c.a(this, predicate);
    }

    @Override // androidx.core.util.Predicate
    public final /* synthetic */ Predicate negate() {
        switch (this.f26a) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
        }
        return androidx.core.util.c.b(this);
    }

    @Override // androidx.core.util.Predicate
    public final /* synthetic */ Predicate or(Predicate predicate) {
        int i2 = this.f26a;
        return androidx.core.util.c.c(this, predicate);
    }

    @Override // androidx.core.util.Predicate
    public final boolean test(Object obj) {
        int i2 = this.f26a;
        String str = this.b;
        switch (i2) {
            case 0:
                return IntentSanitizer.Builder.lambda$allowExtraOutput$16(str, (Uri) obj);
            case 1:
                return str.equals((String) obj);
            case 2:
                return IntentSanitizer.Builder.lambda$allowComponentWithPackage$9(str, (ComponentName) obj);
            case 3:
                return IntentSanitizer.Builder.lambda$allowDataWithAuthority$8(str, (Uri) obj);
            case 4:
                return IntentSanitizer.Builder.lambda$allowClipDataUriWithAuthority$11(str, (Uri) obj);
            case 5:
                return str.equals((String) obj);
            case 6:
                return str.equals((String) obj);
            case 7:
                return str.equals((String) obj);
            default:
                return IntentSanitizer.Builder.lambda$allowExtraStreamUriWithAuthority$15(str, (Uri) obj);
        }
    }
}
