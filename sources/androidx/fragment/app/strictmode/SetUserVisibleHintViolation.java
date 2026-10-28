package androidx.fragment.app.strictmode;

import androidx.fragment.app.Fragment;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class SetUserVisibleHintViolation extends Violation {
    private final boolean isVisibleToUser;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SetUserVisibleHintViolation(Fragment fragment, boolean z2) {
        super(fragment, "Attempting to set user visible hint to " + z2 + " for fragment " + fragment);
        k.g(fragment, "fragment");
        this.isVisibleToUser = z2;
    }

    public final boolean isVisibleToUser() {
        return this.isVisibleToUser;
    }
}
