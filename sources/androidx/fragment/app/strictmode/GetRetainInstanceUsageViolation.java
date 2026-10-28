package androidx.fragment.app.strictmode;

import androidx.fragment.app.Fragment;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class GetRetainInstanceUsageViolation extends RetainInstanceUsageViolation {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GetRetainInstanceUsageViolation(Fragment fragment) {
        super(fragment, "Attempting to get retain instance for fragment " + fragment);
        k.g(fragment, "fragment");
    }
}
