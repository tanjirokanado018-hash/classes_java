package f;

import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class F implements ViewTreeObserver.OnGlobalLayoutListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ LinearLayout f295a;

    public F(LinearLayout linearLayout) {
        this.f295a = linearLayout;
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public final void onGlobalLayout() {
        LinearLayout linearLayout = this.f295a;
        linearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        int i2 = 0;
        for (int i3 = 0; i3 < linearLayout.getChildCount(); i3++) {
            int height = linearLayout.getChildAt(i3).getHeight();
            if (height > i2) {
                i2 = height;
            }
        }
        if (i2 > 0) {
            for (int i4 = 0; i4 < linearLayout.getChildCount(); i4++) {
                View childAt = linearLayout.getChildAt(i4);
                if (childAt.getHeight() != i2) {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams();
                    layoutParams.height = i2;
                    childAt.setLayoutParams(layoutParams);
                }
            }
        }
    }
}
