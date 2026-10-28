package f;

import com.zenoatom.app.LoginActivity;
import org.json.JSONObject;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ax implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ String f352c;
    public final /* synthetic */ LoginActivity d;

    public /* synthetic */ ax(LoginActivity loginActivity, String str, int i2) {
        this.b = i2;
        this.d = loginActivity;
        this.f352c = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        JSONObject jSONObjectOptJSONObject;
        int i2 = this.b;
        int i3 = 7;
        String str = this.f352c;
        LoginActivity loginActivity = this.d;
        switch (i2) {
            case 0:
                try {
                    JSONObject jSONObjectOptJSONObject2 = w.y.ad("https://store.atom.com.mm/mytmapi/v1/my/local-auth/send-otp?msisdn=&userid=-1&v=4.14.1", "{\"msisdn\":\"" + str + "\"}", null, null).optJSONObject("data");
                    jSONObjectOptJSONObject = jSONObjectOptJSONObject2 != null ? jSONObjectOptJSONObject2.optJSONObject("attribute") : null;
                    if (jSONObjectOptJSONObject == null) {
                        throw new Exception("OTP send failed");
                    }
                    loginActivity.f210c = jSONObjectOptJSONObject.optString("code", "");
                    loginActivity.d = str;
                    loginActivity.b.post(new o(6, this));
                    return;
                } catch (Exception e2) {
                    loginActivity.b.post(new u(i3, this, e2));
                    return;
                }
            default:
                try {
                    JSONObject jSONObjectOptJSONObject3 = w.y.an(loginActivity.d, loginActivity.f210c, str).optJSONObject("data");
                    jSONObjectOptJSONObject = jSONObjectOptJSONObject3 != null ? jSONObjectOptJSONObject3.optJSONObject("attribute") : null;
                    if (jSONObjectOptJSONObject == null) {
                        throw new Exception("OTP verify failed");
                    }
                    String strOptString = jSONObjectOptJSONObject.optString("token", "");
                    long jOptLong = jSONObjectOptJSONObject.optLong("user_id", 0L);
                    String strOptString2 = jSONObjectOptJSONObject.optString("msisdn", loginActivity.d);
                    String strOptString3 = jSONObjectOptJSONObject.optString("refresh_token", "");
                    long jOptLong2 = jSONObjectOptJSONObject.optLong("access_token_expire_at", (System.currentTimeMillis() / 1000) + 86400);
                    if (strOptString.isEmpty()) {
                        throw new Exception("No token returned");
                    }
                    w.y.ah(loginActivity, new G(strOptString2, jOptLong, strOptString, strOptString3, jOptLong2));
                    loginActivity.b.post(new o(i3, this));
                    return;
                } catch (Exception e3) {
                    loginActivity.b.post(new u(8, this, e3));
                    return;
                }
        }
    }
}
