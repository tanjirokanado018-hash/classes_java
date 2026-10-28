package androidx.core.os;

import android.os.Bundle;
import android.util.Size;
import android.util.SizeF;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RequiresApi(21)
final class BundleApi21ImplKt {
    public static final BundleApi21ImplKt INSTANCE = new BundleApi21ImplKt();

    private BundleApi21ImplKt() {
    }

    @DoNotInline
    public static final void putSize(Bundle bundle, String str, Size size) {
        bundle.putSize(str, size);
    }

    @DoNotInline
    public static final void putSizeF(Bundle bundle, String str, SizeF sizeF) {
        bundle.putSizeF(str, sizeF);
    }
}
