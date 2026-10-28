package androidx.activity;

import android.view.View;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ViewTreeOnBackPressedDispatcherOwner$findViewTreeOnBackPressedDispatcherOwner$2 extends l implements p.l {
    public static final ViewTreeOnBackPressedDispatcherOwner$findViewTreeOnBackPressedDispatcherOwner$2 INSTANCE = new ViewTreeOnBackPressedDispatcherOwner$findViewTreeOnBackPressedDispatcherOwner$2();

    public ViewTreeOnBackPressedDispatcherOwner$findViewTreeOnBackPressedDispatcherOwner$2() {
        super(1);
    }

    @Override // p.l
    public final OnBackPressedDispatcherOwner invoke(View it) {
        k.g(it, "it");
        Object tag = it.getTag(R.id.view_tree_on_back_pressed_dispatcher_owner);
        if (tag instanceof OnBackPressedDispatcherOwner) {
            return (OnBackPressedDispatcherOwner) tag;
        }
        return null;
    }
}
