package androidx.fragment.app.strictmode;

import androidx.fragment.app.Fragment;
import kotlin.jvm.internal.f;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class Violation extends RuntimeException {
    private final Fragment fragment;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Violation(Fragment fragment, String str) {
        super(str);
        k.g(fragment, "fragment");
        this.fragment = fragment;
    }

    public final Fragment getFragment() {
        return this.fragment;
    }

    public /* synthetic */ Violation(Fragment fragment, String str, int i2, f fVar) {
        this(fragment, (i2 & 2) != 0 ? null : str);
    }
}
