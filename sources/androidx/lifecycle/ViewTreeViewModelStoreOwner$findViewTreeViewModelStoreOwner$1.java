package androidx.lifecycle;

import android.view.View;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ViewTreeViewModelStoreOwner$findViewTreeViewModelStoreOwner$1 extends l implements p.l {
    public static final ViewTreeViewModelStoreOwner$findViewTreeViewModelStoreOwner$1 INSTANCE = new ViewTreeViewModelStoreOwner$findViewTreeViewModelStoreOwner$1();

    public ViewTreeViewModelStoreOwner$findViewTreeViewModelStoreOwner$1() {
        super(1);
    }

    @Override // p.l
    public final View invoke(View view) {
        k.g(view, "view");
        Object parent = view.getParent();
        if (parent instanceof View) {
            return (View) parent;
        }
        return null;
    }
}
