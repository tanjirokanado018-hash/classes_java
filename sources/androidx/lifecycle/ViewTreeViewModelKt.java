package androidx.lifecycle;

import android.view.View;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ViewTreeViewModelKt {
    public static final /* synthetic */ ViewModelStoreOwner findViewTreeViewModelStoreOwner(View view) {
        k.g(view, "view");
        return ViewTreeViewModelStoreOwner.get(view);
    }
}
