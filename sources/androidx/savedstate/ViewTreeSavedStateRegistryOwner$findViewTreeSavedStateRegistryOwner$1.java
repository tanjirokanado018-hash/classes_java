package androidx.savedstate;

import android.view.View;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ViewTreeSavedStateRegistryOwner$findViewTreeSavedStateRegistryOwner$1 extends l implements p.l {
    public static final ViewTreeSavedStateRegistryOwner$findViewTreeSavedStateRegistryOwner$1 INSTANCE = new ViewTreeSavedStateRegistryOwner$findViewTreeSavedStateRegistryOwner$1();

    public ViewTreeSavedStateRegistryOwner$findViewTreeSavedStateRegistryOwner$1() {
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
