package androidx.lifecycle;

import android.view.View;
import androidx.lifecycle.runtime.R;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ViewTreeLifecycleOwner$findViewTreeLifecycleOwner$2 extends l implements p.l {
    public static final ViewTreeLifecycleOwner$findViewTreeLifecycleOwner$2 INSTANCE = new ViewTreeLifecycleOwner$findViewTreeLifecycleOwner$2();

    public ViewTreeLifecycleOwner$findViewTreeLifecycleOwner$2() {
        super(1);
    }

    @Override // p.l
    public final LifecycleOwner invoke(View viewParent) {
        k.g(viewParent, "viewParent");
        Object tag = viewParent.getTag(R.id.view_tree_lifecycle_owner);
        if (tag instanceof LifecycleOwner) {
            return (LifecycleOwner) tag;
        }
        return null;
    }
}
