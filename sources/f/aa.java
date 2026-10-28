package f;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.zenoatom.app.DashboardActivity;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class aa implements SwipeRefreshLayout.OnRefreshListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ DashboardActivity f307a;

    public aa(DashboardActivity dashboardActivity) {
        this.f307a = dashboardActivity;
    }

    @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
    public final void onRefresh() {
        int i2 = DashboardActivity.L;
        this.f307a.s();
    }
}
