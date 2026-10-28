package f;

import android.widget.TextView;
import com.zenoatom.app.DashboardActivity;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ag implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ int f328c;
    public final /* synthetic */ z d;

    public ag(z zVar, int i2, int i3) {
        this.d = zVar;
        this.b = i2;
        this.f328c = i3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        z zVar = this.d;
        TextView textView = zVar.f419c.f183p;
        int i2 = this.b;
        textView.setText(String.valueOf(i2));
        zVar.f419c.f184q.setText("Weekly " + this.f328c);
        DashboardActivity dashboardActivity = zVar.f419c;
        dashboardActivity.f193z = i2 <= 0;
        dashboardActivity.q();
    }
}
