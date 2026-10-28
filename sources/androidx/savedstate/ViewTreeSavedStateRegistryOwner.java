package androidx.savedstate;

import android.view.View;
import kotlin.jvm.internal.k;
import u.j;
import u.m;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ViewTreeSavedStateRegistryOwner {
    public static final SavedStateRegistryOwner get(View view) {
        k.g(view, "<this>");
        return (SavedStateRegistryOwner) j.aa(j.ab(m.z(view, ViewTreeSavedStateRegistryOwner$findViewTreeSavedStateRegistryOwner$1.INSTANCE), ViewTreeSavedStateRegistryOwner$findViewTreeSavedStateRegistryOwner$2.INSTANCE));
    }

    public static final void set(View view, SavedStateRegistryOwner savedStateRegistryOwner) {
        k.g(view, "<this>");
        view.setTag(R.id.view_tree_saved_state_registry_owner, savedStateRegistryOwner);
    }
}
