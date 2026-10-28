package f;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import com.zenoatom.app.DashboardActivity;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ai extends GestureDetector.SimpleOnGestureListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ DashboardActivity f332a;

    public ai(DashboardActivity dashboardActivity) {
        this.f332a = dashboardActivity;
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
    public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
        if (motionEvent != null && motionEvent2 != null) {
            float x2 = motionEvent2.getX() - motionEvent.getX();
            if (Math.abs(x2) > 80.0f) {
                int i2 = x2 < 0.0f ? 1 : 0;
                DashboardActivity dashboardActivity = this.f332a;
                dashboardActivity.ay = i2;
                LinearLayout linearLayout = dashboardActivity.as;
                if (linearLayout != null && dashboardActivity.at != null) {
                    linearLayout.setVisibility(i2 == 0 ? 0 : 8);
                    dashboardActivity.at.setVisibility(i2 != 1 ? 8 : 0);
                    dashboardActivity.y();
                    dashboardActivity.x();
                }
                return true;
            }
        }
        return false;
    }
}
