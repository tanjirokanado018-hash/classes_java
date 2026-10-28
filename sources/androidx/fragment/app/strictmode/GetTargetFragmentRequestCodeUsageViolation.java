package androidx.fragment.app.strictmode;

import androidx.fragment.app.Fragment;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class GetTargetFragmentRequestCodeUsageViolation extends TargetFragmentUsageViolation {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GetTargetFragmentRequestCodeUsageViolation(Fragment fragment) {
        super(fragment, "Attempting to get target request code from fragment " + fragment);
        k.g(fragment, "fragment");
    }
}
