package androidx.fragment.app.strictmode;

import androidx.fragment.app.Fragment;
import kotlin.jvm.internal.f;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class TargetFragmentUsageViolation extends Violation {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TargetFragmentUsageViolation(Fragment fragment, String str) {
        super(fragment, str);
        k.g(fragment, "fragment");
    }

    public /* synthetic */ TargetFragmentUsageViolation(Fragment fragment, String str, int i2, f fVar) {
        this(fragment, (i2 & 2) != 0 ? null : str);
    }
}
