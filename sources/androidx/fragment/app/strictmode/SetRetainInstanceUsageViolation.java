package androidx.fragment.app.strictmode;

import androidx.fragment.app.Fragment;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class SetRetainInstanceUsageViolation extends RetainInstanceUsageViolation {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SetRetainInstanceUsageViolation(Fragment fragment) {
        super(fragment, "Attempting to set retain instance for fragment " + fragment);
        k.g(fragment, "fragment");
    }
}
