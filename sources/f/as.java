package f;

import com.zenoatom.app.HistoryActivity;
import org.json.JSONObject;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class as implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ HistoryActivity f345c;

    public /* synthetic */ as(HistoryActivity historyActivity, int i2) {
        this.b = i2;
        this.f345c = historyActivity;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.b;
        HistoryActivity historyActivity = this.f345c;
        switch (i2) {
            case 0:
                try {
                    JSONObject jSONObjectOptJSONObject = w.y.n(historyActivity.f196c).optJSONObject("data");
                    JSONObject jSONObjectOptJSONObject2 = jSONObjectOptJSONObject != null ? jSONObjectOptJSONObject.optJSONObject("attribute") : null;
                    historyActivity.b.post(new u(3, this, jSONObjectOptJSONObject2 != null ? jSONObjectOptJSONObject2.optJSONArray("instantWin") : null));
                } catch (Exception e2) {
                    historyActivity.b.post(new u(4, this, e2));
                    return;
                }
                break;
            default:
                try {
                    JSONObject jSONObjectOptJSONObject3 = w.y.l(historyActivity.f196c).optJSONObject("data");
                    JSONObject jSONObjectOptJSONObject4 = jSONObjectOptJSONObject3 != null ? jSONObjectOptJSONObject3.optJSONObject("attribute") : null;
                    historyActivity.b.post(new u(5, this, jSONObjectOptJSONObject4 != null ? jSONObjectOptJSONObject4.optJSONArray("instantWin") : null));
                } catch (Exception e3) {
                    historyActivity.b.post(new u(6, this, e3));
                }
                break;
        }
    }
}
