package androidx.recyclerview.widget;

import android.graphics.Canvas;
import android.view.View;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public interface ItemTouchUIUtil {
    void clearView(View view);

    void onDraw(Canvas canvas, RecyclerView recyclerView, View view, float f2, float f3, int i2, boolean z2);

    void onDrawOver(Canvas canvas, RecyclerView recyclerView, View view, float f2, float f3, int i2, boolean z2);

    void onSelected(View view);
}
