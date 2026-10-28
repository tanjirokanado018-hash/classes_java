package androidx.activity;

import android.view.View;
import kotlin.jvm.internal.k;
import u.j;
import u.m;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ViewTreeOnBackPressedDispatcherOwner {
    public static final OnBackPressedDispatcherOwner get(View view) {
        k.g(view, "<this>");
        return (OnBackPressedDispatcherOwner) j.aa(j.ab(m.z(view, ViewTreeOnBackPressedDispatcherOwner$findViewTreeOnBackPressedDispatcherOwner$1.INSTANCE), ViewTreeOnBackPressedDispatcherOwner$findViewTreeOnBackPressedDispatcherOwner$2.INSTANCE));
    }

    public static final void set(View view, OnBackPressedDispatcherOwner onBackPressedDispatcherOwner) {
        k.g(view, "<this>");
        k.g(onBackPressedDispatcherOwner, "onBackPressedDispatcherOwner");
        view.setTag(R.id.view_tree_on_back_pressed_dispatcher_owner, onBackPressedDispatcherOwner);
    }
}
