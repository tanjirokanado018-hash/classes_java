package androidx.core.view;

import android.view.View;
import android.view.ViewGroup;
import java.util.Iterator;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ViewGroupKt$descendants$1$1 extends kotlin.jvm.internal.l implements p.l {
    public static final ViewGroupKt$descendants$1$1 INSTANCE = new ViewGroupKt$descendants$1$1();

    public ViewGroupKt$descendants$1$1() {
        super(1);
    }

    @Override // p.l
    public final Iterator<View> invoke(View view) {
        u.g children;
        ViewGroup viewGroup = view instanceof ViewGroup ? (ViewGroup) view : null;
        if (viewGroup == null || (children = ViewGroupKt.getChildren(viewGroup)) == null) {
            return null;
        }
        return children.iterator();
    }
}
