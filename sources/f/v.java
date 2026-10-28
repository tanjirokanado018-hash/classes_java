package f;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.zenoatom.app.ClaimActivity;
import com.zenoatom.app.DashboardActivity;
import org.json.JSONObject;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class v implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ String f413c;
    public final /* synthetic */ String d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final /* synthetic */ Object f414e;

    public /* synthetic */ v(Object obj, String str, String str2, int i2) {
        this.b = i2;
        this.f414e = obj;
        this.f413c = str;
        this.d = str2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.b;
        String str = this.d;
        String strSubstring = this.f413c;
        Object obj = this.f414e;
        switch (i2) {
            case 0:
                w wVar = (w) obj;
                ClaimActivity claimActivity = wVar.f416e;
                int i3 = ClaimActivity.f165g;
                View viewInflate = claimActivity.getLayoutInflater().inflate(2131427390, (ViewGroup) null);
                TextView textView = (TextView) viewInflate.findViewById(2131231336);
                TextView textView2 = (TextView) viewInflate.findViewById(2131231335);
                LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(2131230827);
                LinearLayout linearLayout2 = (LinearLayout) viewInflate.findViewById(2131230841);
                if (strSubstring != null && !strSubstring.isEmpty()) {
                    textView.setText(strSubstring);
                }
                if (str != null) {
                    textView2.setText(str);
                }
                Dialog dialog = new Dialog(claimActivity);
                dialog.requestWindowFeature(1);
                dialog.setContentView(viewInflate);
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    dialog.getWindow().setLayout((int) (claimActivity.getResources().getDisplayMetrics().widthPixels * 0.88f), -2);
                }
                ViewOnClickListenerC0023c viewOnClickListenerC0023c = new ViewOnClickListenerC0023c(dialog, 4);
                if (linearLayout != null) {
                    linearLayout.setOnClickListener(viewOnClickListenerC0023c);
                }
                if (linearLayout2 != null) {
                    linearLayout2.setOnClickListener(viewOnClickListenerC0023c);
                }
                dialog.show();
                wVar.f416e.l();
                break;
            case 1:
                z zVar = (z) obj;
                TextView textView3 = zVar.f419c.aw;
                DashboardActivity dashboardActivity = zVar.f419c;
                if (textView3 != null) {
                    if (strSubstring.contains(" ")) {
                        strSubstring = strSubstring.substring(0, strSubstring.lastIndexOf(" "));
                    }
                    dashboardActivity.aw.setText(strSubstring);
                }
                TextView textView4 = dashboardActivity.ax;
                if (textView4 != null) {
                    textView4.setText(str);
                }
                break;
            default:
                try {
                    G g2 = ((DashboardActivity) obj).an;
                    JSONObject jSONObjectOptJSONObject = w.y.ad(w.y.am("/tohtohunited/purchase-game-pack-life", g2.f296a, String.valueOf(g2.b)), "{\"offerId\":\"" + strSubstring + "\"}", g2, null).optJSONObject("data");
                    JSONObject jSONObjectOptJSONObject2 = jSONObjectOptJSONObject != null ? jSONObjectOptJSONObject.optJSONObject("attribute") : null;
                    ((DashboardActivity) obj).ao.post(new al(this, (jSONObjectOptJSONObject2 == null || !jSONObjectOptJSONObject2.has("message")) ? str + "\nဝယ်ယူမှု အောင်မြင်ပါသည်။" : jSONObjectOptJSONObject2.optString("message", "ဝယ်ယူမှု အောင်မြင်ပါသည်"), 0));
                } catch (Exception e2) {
                    ((DashboardActivity) obj).ao.post(new al(this, e2.getMessage() != null ? e2.getMessage() : "ဝယ်ယူမှု မအောင်မြင်ပါ", 1));
                }
                break;
        }
    }
}
