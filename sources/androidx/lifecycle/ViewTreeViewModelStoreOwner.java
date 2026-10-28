package androidx.lifecycle;

import android.view.View;
import androidx.lifecycle.viewmodel.R;
import kotlin.jvm.internal.k;
import u.j;
import u.m;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ViewTreeViewModelStoreOwner {
    public static final ViewModelStoreOwner get(View view) {
        k.g(view, "<this>");
        return (ViewModelStoreOwner) j.aa(j.ab(m.z(view, ViewTreeViewModelStoreOwner$findViewTreeViewModelStoreOwner$1.INSTANCE), ViewTreeViewModelStoreOwner$findViewTreeViewModelStoreOwner$2.INSTANCE));
    }

    public static final void set(View view, ViewModelStoreOwner viewModelStoreOwner) {
        k.g(view, "<this>");
        view.setTag(R.id.view_tree_view_model_store_owner, viewModelStoreOwner);
    }
}
