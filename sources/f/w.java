package f;

import android.widget.TextView;
import com.zenoatom.app.ClaimActivity;
import org.json.JSONObject;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class w implements Runnable {
    public final /* synthetic */ long b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ TextView f415c;
    public final /* synthetic */ String d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final /* synthetic */ ClaimActivity f416e;

    public w(ClaimActivity claimActivity, long j2, TextView textView, String str) {
        this.f416e = claimActivity;
        this.b = j2;
        this.f415c = textView;
        this.d = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ClaimActivity claimActivity = this.f416e;
        try {
            G g2 = claimActivity.f167c;
            long j2 = this.b;
            JSONObject jSONObjectOptJSONObject = w.y.ad(w.y.am("/point-system/claim", g2.f296a, String.valueOf(g2.b)), "{\"id\":" + j2 + "}", g2, null).optJSONObject("data");
            JSONObject jSONObjectOptJSONObject2 = jSONObjectOptJSONObject != null ? jSONObjectOptJSONObject.optJSONObject("attribute") : null;
            claimActivity.b.post(new v(this, jSONObjectOptJSONObject2 != null ? jSONObjectOptJSONObject2.optString("title", "အောင်မြင်ပါသည်!") : "အောင်မြင်ပါသည်!", jSONObjectOptJSONObject2 != null ? jSONObjectOptJSONObject2.optString("message", "Points ရရှိပြီးပါပြီ") : "Points ရရှိပြီးပါပြီ", 0));
        } catch (Exception e2) {
            claimActivity.b.post(new u(1, this, e2.getMessage() != null ? e2.getMessage() : "Claim မရပါ"));
        }
    }
}
