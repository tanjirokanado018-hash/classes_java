package f;

import android.widget.TextView;
import com.zenoatom.app.DashboardActivity;
import org.json.JSONArray;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ad implements Runnable {
    public final /* synthetic */ String b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ String f314c;
    public final /* synthetic */ String d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final /* synthetic */ String f315e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final /* synthetic */ boolean f316f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public final /* synthetic */ JSONArray f317g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public final /* synthetic */ JSONArray f318h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public final /* synthetic */ JSONArray f319i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public final /* synthetic */ JSONArray f320j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public final /* synthetic */ z f321k;

    public ad(z zVar, String str, String str2, String str3, String str4, boolean z2, JSONArray jSONArray, JSONArray jSONArray2, JSONArray jSONArray3, JSONArray jSONArray4, JSONArray jSONArray5) {
        this.f321k = zVar;
        this.b = str;
        this.f314c = str2;
        this.d = str3;
        this.f315e = str4;
        this.f316f = z2;
        this.f317g = jSONArray;
        this.f318h = jSONArray2;
        this.f319i = jSONArray3;
        this.f320j = jSONArray5;
    }

    @Override // java.lang.Runnable
    public final void run() {
        z zVar = this.f321k;
        zVar.f419c.d.setText(this.b);
        zVar.f419c.f172e.setText(this.f314c);
        zVar.f419c.f173f.setText(this.d);
        TextView textView = zVar.f419c.f174g;
        String str = this.f315e;
        textView.setText(str);
        zVar.f419c.f174g.setTextColor(this.f316f ? -1096636 : -680437);
        DashboardActivity dashboardActivity = zVar.f419c;
        dashboardActivity.K = str;
        dashboardActivity.G = this.f317g;
        dashboardActivity.H = this.f318h;
        dashboardActivity.I = this.f319i;
        dashboardActivity.getClass();
        zVar.f419c.J = this.f320j;
    }
}
