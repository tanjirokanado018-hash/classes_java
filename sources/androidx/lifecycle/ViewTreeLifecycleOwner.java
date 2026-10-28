package androidx.lifecycle;

import android.view.View;
import androidx.lifecycle.runtime.R;
import kotlin.jvm.internal.k;
import u.j;
import u.m;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ViewTreeLifecycleOwner {
    public static final LifecycleOwner get(View view) {
        k.g(view, "<this>");
        return (LifecycleOwner) j.aa(j.ab(m.z(view, ViewTreeLifecycleOwner$findViewTreeLifecycleOwner$1.INSTANCE), ViewTreeLifecycleOwner$findViewTreeLifecycleOwner$2.INSTANCE));
    }

    public static final void set(View view, LifecycleOwner lifecycleOwner) {
        k.g(view, "<this>");
        view.setTag(R.id.view_tree_lifecycle_owner, lifecycleOwner);
    }
}
