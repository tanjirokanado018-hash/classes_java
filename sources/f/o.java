package f;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import com.google.android.material.snackbar.Snackbar;
import com.zenoatom.app.AccountsActivity;
import com.zenoatom.app.CityRunActivity;
import com.zenoatom.app.ClaimActivity;
import com.zenoatom.app.DashboardActivity;
import com.zenoatom.app.LoginActivity;
import com.zenoatom.app.SplashActivity;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class o implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f381c;

    public /* synthetic */ o(int i2, Object obj) {
        this.b = i2;
        this.f381c = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        o oVar;
        Intent intent;
        NetworkInfo activeNetworkInfo;
        int i2 = this.b;
        int i3 = 1;
        int i4 = 0;
        Object obj = this.f381c;
        switch (i2) {
            case 0:
                CityRunActivity cityRunActivity = (CityRunActivity) obj;
                boolean z2 = false;
                for (Map.Entry entry : cityRunActivity.f158i.entrySet()) {
                    r rVar = (r) entry.getValue();
                    if (rVar.f387e) {
                        View view = (View) cityRunActivity.f160k.get(entry.getKey());
                        if (view != null) {
                            LinearLayout linearLayout = (LinearLayout) view.findViewById(2131231028);
                            TextView textView = (TextView) view.findViewById(2131231294);
                            if (linearLayout != null && textView != null && linearLayout.getVisibility() == 0) {
                                long jMax = Math.max(0L, cityRunActivity.n(rVar.f394l.size()) - ((System.currentTimeMillis() / 1000) - rVar.f393k));
                                textView.setText(jMax == 0 ? "CLAIMING..." : String.format("%02d:%02d", Long.valueOf(jMax / 60), Long.valueOf(jMax % 60)));
                            }
                        }
                        z2 = true;
                    }
                }
                Handler handler = cityRunActivity.f161l;
                if (!z2 && (oVar = cityRunActivity.f164o) != null) {
                    handler.removeCallbacks(oVar);
                    cityRunActivity.f164o = null;
                }
                handler.postDelayed(this, 1000L);
                break;
            case 1:
                o oVar2 = (o) obj;
                ProgressBar progressBar = ((ClaimActivity) oVar2.f381c).d;
                if (progressBar != null) {
                    progressBar.setVisibility(8);
                }
                ScrollView scrollView = ((ClaimActivity) oVar2.f381c).f168e;
                if (scrollView != null) {
                    scrollView.setVisibility(0);
                }
                break;
            case 2:
                try {
                    G g2 = ((ClaimActivity) obj).f167c;
                    w.y.k(g2, "https://store.atom.com.mm/mytmapi/v1/my/dashboard?isFirstTime=1&isFirstInstall=1&msisdn=" + g2.f296a + "&userid=" + String.valueOf(g2.b) + "&v=4.14.1");
                    break;
                } catch (Exception unused) {
                }
                try {
                    G g3 = ((ClaimActivity) obj).f167c;
                    JSONObject jSONObjectOptJSONObject = w.y.k(g3, "https://store.atom.com.mm/mytmapi/v2/my/point-system/claim-list?msisdn=" + g3.f296a + "&userid=" + String.valueOf(g3.b) + "&v=4.14.1").optJSONObject("data");
                    JSONObject jSONObjectOptJSONObject2 = jSONObjectOptJSONObject != null ? jSONObjectOptJSONObject.optJSONObject("attribute") : null;
                    ((ClaimActivity) obj).b.post(new u(i4, this, jSONObjectOptJSONObject2 != null ? jSONObjectOptJSONObject2.optJSONArray("items") : null));
                } catch (Exception unused2) {
                    ((ClaimActivity) obj).b.post(new o(i3, this));
                    return;
                }
                break;
            case 3:
                z zVar = (z) obj;
                DashboardActivity.l(zVar.f419c);
                zVar.f419c.v(false);
                break;
            case 4:
                z zVar2 = (z) obj;
                DashboardActivity.n(zVar2.f419c);
                zVar2.f419c.u(false);
                break;
            case 5:
                LoginActivity loginActivity = ((av) obj).f349c;
                int i5 = LoginActivity.f208w;
                loginActivity.k();
                break;
            case 6:
                ax axVar = (ax) obj;
                LoginActivity loginActivity2 = axVar.d;
                int i6 = LoginActivity.f208w;
                loginActivity2.l(false);
                LoginActivity loginActivity3 = axVar.d;
                LinearLayout linearLayout2 = loginActivity3.f213g;
                if (linearLayout2 != null) {
                    linearLayout2.setVisibility(8);
                }
                LinearLayout linearLayout3 = loginActivity3.f214h;
                if (linearLayout3 != null) {
                    linearLayout3.setVisibility(0);
                }
                TextView textView2 = loginActivity3.f224r;
                if (textView2 != null) {
                    textView2.setText(loginActivity3.d + "  OTP");
                }
                loginActivity3.j();
                loginActivity3.n("OTP sent to " + axVar.f352c);
                break;
            case 7:
                ax axVar2 = (ax) obj;
                LoginActivity loginActivity4 = axVar2.d;
                int i7 = LoginActivity.f208w;
                loginActivity4.m(false);
                LoginActivity loginActivity5 = axVar2.d;
                if (loginActivity5.f228v) {
                    intent = new Intent(loginActivity5, (Class<?>) AccountsActivity.class);
                    intent.addFlags(AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL);
                } else {
                    intent = new Intent(loginActivity5, (Class<?>) DashboardActivity.class);
                }
                loginActivity5.startActivity(intent);
                loginActivity5.finish();
                break;
            default:
                SplashActivity splashActivity = (SplashActivity) obj;
                if (!splashActivity.f256e) {
                    ConnectivityManager connectivityManager = (ConnectivityManager) splashActivity.getSystemService("connectivity");
                    Handler handler2 = splashActivity.b;
                    if (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || !activeNetworkInfo.isConnected()) {
                        handler2.postDelayed(this, 1500L);
                    } else {
                        Snackbar snackbar = splashActivity.f255c;
                        if (snackbar != null && snackbar.isShown()) {
                            splashActivity.f255c.dismiss();
                        }
                        splashActivity.f256e = true;
                        o oVar3 = splashActivity.d;
                        if (oVar3 != null) {
                            handler2.removeCallbacks(oVar3);
                        }
                        splashActivity.f254a.execute(new androidx.lifecycle.f(4, splashActivity));
                    }
                    break;
                }
                break;
        }
    }
}
