package f;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;
import com.zenoatom.app.AccountsActivity;
import com.zenoatom.app.BaseActivity;
import com.zenoatom.app.CityRunActivity;
import com.zenoatom.app.DashboardActivity;
import com.zenoatom.app.LoginActivity;
import java.util.ArrayList;
import java.util.function.Predicate;

/* JADX INFO: renamed from: f.d, reason: case insensitive filesystem */
/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class ViewOnClickListenerC0024d implements View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f358a;
    public final /* synthetic */ Dialog b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ BaseActivity f359c;
    public final /* synthetic */ Object d;

    public /* synthetic */ ViewOnClickListenerC0024d(BaseActivity baseActivity, Dialog dialog, Object obj, int i2) {
        this.f358a = i2;
        this.f359c = baseActivity;
        this.b = dialog;
        this.d = obj;
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [f.H] */
    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i2 = this.f358a;
        Dialog dialog = this.b;
        Object obj = this.d;
        BaseActivity baseActivity = this.f359c;
        switch (i2) {
            case 0:
                AccountsActivity accountsActivity = (AccountsActivity) baseActivity;
                int i3 = AccountsActivity.b;
                accountsActivity.getClass();
                dialog.dismiss();
                final String str = ((G) obj).f296a;
                ArrayList arrayListW = w.y.w(accountsActivity);
                arrayListW.removeIf(new Predicate() { // from class: f.H
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj2) {
                        return ((G) obj2).f296a.equals(str);
                    }
                });
                w.y.ab(accountsActivity, arrayListW);
                if (str.equals(accountsActivity.getSharedPreferences("zenoatom_prefs", 0).getString("active_msisdn", ""))) {
                    if (arrayListW.isEmpty()) {
                        accountsActivity.getSharedPreferences("zenoatom_prefs", 0).edit().remove("active_msisdn").apply();
                    } else {
                        w.y.ai(accountsActivity, ((G) arrayListW.get(0)).f296a);
                    }
                }
                if (!w.y.w(accountsActivity).isEmpty()) {
                    accountsActivity.l();
                } else {
                    accountsActivity.getSharedPreferences("zenoatom_prefs", 0).edit().clear().apply();
                    accountsActivity.startActivity(new Intent(accountsActivity, (Class<?>) LoginActivity.class));
                    accountsActivity.finish();
                }
                break;
            case 1:
                CityRunActivity cityRunActivity = (CityRunActivity) baseActivity;
                String str2 = (String) obj;
                int i4 = CityRunActivity.f151p;
                cityRunActivity.getClass();
                dialog.dismiss();
                Runnable runnable = (Runnable) cityRunActivity.f159j.remove(str2);
                if (runnable != null) {
                    cityRunActivity.f161l.removeCallbacks(runnable);
                }
                cityRunActivity.f158i.remove(str2);
                cityRunActivity.f160k.remove(str2);
                cityRunActivity.r();
                cityRunActivity.finish();
                break;
            default:
                DashboardActivity dashboardActivity = (DashboardActivity) baseActivity;
                String str3 = (String) obj;
                int i5 = DashboardActivity.L;
                dashboardActivity.getClass();
                dialog.dismiss();
                if (str3 != null && !str3.isEmpty()) {
                    try {
                        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str3));
                        intent.addFlags(268435456);
                        dashboardActivity.startActivity(intent);
                    } catch (Exception unused) {
                        Toast.makeText(dashboardActivity, "Link ဖွင့်၍ မရပါ", 0).show();
                    }
                    break;
                }
                break;
        }
    }
}
