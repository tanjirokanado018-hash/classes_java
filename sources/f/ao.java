package f;

import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.zenoatom.app.DashboardActivity;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ao implements Runnable {
    public final /* synthetic */ FrameLayout[] b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ int[] f341c;
    public final /* synthetic */ DashboardActivity d;

    public ao(DashboardActivity dashboardActivity, FrameLayout[] frameLayoutArr, int[] iArr, J[] jArr) {
        this.d = dashboardActivity;
        this.b = frameLayoutArr;
        this.f341c = iArr;
    }

    @Override // java.lang.Runnable
    public final void run() {
        float f2 = this.d.getResources().getDisplayMetrics().density;
        int i2 = (int) (3.0f * f2);
        int i3 = (int) (f2 * 14.0f);
        int i4 = 0;
        while (true) {
            ViewGroup[] viewGroupArr = this.b;
            if (i4 >= viewGroupArr.length) {
                return;
            }
            ViewGroup viewGroup = viewGroupArr[i4];
            if (viewGroup != null) {
                if (viewGroup.getChildCount() > 0) {
                    View childAt = viewGroupArr[i4].getChildAt(0);
                    childAt.setOutlineProvider(new an(i3));
                    childAt.setClipToOutline(true);
                }
                if (i4 == this.f341c[0]) {
                    int i5 = i4 < 4 ? J.f301g[i4].d : -14498466;
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setShape(0);
                    gradientDrawable.setColor(0);
                    gradientDrawable.setCornerRadius(i3);
                    gradientDrawable.setStroke(i2, i5);
                    viewGroupArr[i4].setBackground(gradientDrawable);
                    viewGroupArr[i4].setPadding(i2, i2, i2, i2);
                } else {
                    viewGroupArr[i4].setBackground(null);
                    viewGroupArr[i4].setPadding(0, 0, 0, 0);
                }
            }
            i4++;
        }
    }
}
