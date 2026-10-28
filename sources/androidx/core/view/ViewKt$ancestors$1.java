package androidx.core.view;

import android.view.ViewParent;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public /* synthetic */ class ViewKt$ancestors$1 extends kotlin.jvm.internal.j implements p.l {
    public static final ViewKt$ancestors$1 INSTANCE = new ViewKt$ancestors$1();

    public ViewKt$ancestors$1() {
        super(1, kotlin.jvm.internal.c.NO_RECEIVER, ViewParent.class, "getParent", "getParent()Landroid/view/ViewParent;");
    }

    @Override // p.l
    public final ViewParent invoke(ViewParent viewParent) {
        return viewParent.getParent();
    }
}
