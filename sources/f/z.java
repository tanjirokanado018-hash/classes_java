package f;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.widget.Button;
import android.widget.LinearLayout;
import com.google.android.material.snackbar.Snackbar;
import com.zenoatom.app.DashboardActivity;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class z implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ DashboardActivity f419c;

    public /* synthetic */ z(DashboardActivity dashboardActivity, int i2) {
        this.b = i2;
        this.f419c = dashboardActivity;
    }

    /* JADX WARN: Code duplicated, block: B:120:0x027a  */
    /* JADX WARN: Code duplicated, block: B:125:0x0285  */
    /* JADX WARN: Code duplicated, block: B:134:0x02be  */
    /* JADX WARN: Code duplicated, block: B:142:0x02d2  */
    /* JADX WARN: Code duplicated, block: B:147:0x0307  */
    /* JADX WARN: Code duplicated, block: B:153:0x0313  */
    /* JADX WARN: Code duplicated, block: B:155:0x0338  */
    /* JADX WARN: Code duplicated, block: B:157:0x0340 A[Catch: Exception -> 0x02f7, TryCatch #7 {Exception -> 0x02f7, blocks: (B:143:0x02d4, B:154:0x0315, B:157:0x0340, B:161:0x034e, B:162:0x0364, B:164:0x036a, B:166:0x0370, B:167:0x0373, B:168:0x0376), top: B:338:0x02d4 }] */
    /* JADX WARN: Code duplicated, block: B:160:0x034c  */
    /* JADX WARN: Code duplicated, block: B:164:0x036a A[Catch: Exception -> 0x02f7, TryCatch #7 {Exception -> 0x02f7, blocks: (B:143:0x02d4, B:154:0x0315, B:157:0x0340, B:161:0x034e, B:162:0x0364, B:164:0x036a, B:166:0x0370, B:167:0x0373, B:168:0x0376), top: B:338:0x02d4 }] */
    /* JADX WARN: Code duplicated, block: B:166:0x0370 A[Catch: Exception -> 0x02f7, TryCatch #7 {Exception -> 0x02f7, blocks: (B:143:0x02d4, B:154:0x0315, B:157:0x0340, B:161:0x034e, B:162:0x0364, B:164:0x036a, B:166:0x0370, B:167:0x0373, B:168:0x0376), top: B:338:0x02d4 }] */
    /* JADX WARN: Code duplicated, block: B:174:0x03bf A[Catch: Exception -> 0x041a, TRY_LEAVE, TryCatch #18 {Exception -> 0x041a, blocks: (B:172:0x03b9, B:174:0x03bf), top: B:360:0x03b9 }] */
    /* JADX WARN: Code duplicated, block: B:178:0x03f5 A[Catch: Exception -> 0x0408, TryCatch #20 {Exception -> 0x0408, blocks: (B:176:0x03ef, B:178:0x03f5), top: B:364:0x03ef }] */
    /* JADX WARN: Code duplicated, block: B:185:0x040b  */
    /* JADX WARN: Code duplicated, block: B:190:0x041a  */
    /* JADX WARN: Code duplicated, block: B:200:0x0455  */
    /* JADX WARN: Code duplicated, block: B:204:0x045f  */
    /* JADX WARN: Code duplicated, block: B:206:0x0465 A[Catch: Exception -> 0x045c, TryCatch #15 {Exception -> 0x045c, blocks: (B:201:0x0457, B:206:0x0465, B:208:0x047c, B:211:0x0486, B:213:0x048f, B:216:0x0497, B:218:0x04b7, B:217:0x04b1), top: B:354:0x0457 }] */
    /* JADX WARN: Code duplicated, block: B:208:0x047c A[Catch: Exception -> 0x045c, TryCatch #15 {Exception -> 0x045c, blocks: (B:201:0x0457, B:206:0x0465, B:208:0x047c, B:211:0x0486, B:213:0x048f, B:216:0x0497, B:218:0x04b7, B:217:0x04b1), top: B:354:0x0457 }] */
    /* JADX WARN: Code duplicated, block: B:209:0x0483  */
    /* JADX WARN: Code duplicated, block: B:211:0x0486 A[Catch: Exception -> 0x045c, TryCatch #15 {Exception -> 0x045c, blocks: (B:201:0x0457, B:206:0x0465, B:208:0x047c, B:211:0x0486, B:213:0x048f, B:216:0x0497, B:218:0x04b7, B:217:0x04b1), top: B:354:0x0457 }] */
    /* JADX WARN: Code duplicated, block: B:212:0x048e  */
    /* JADX WARN: Code duplicated, block: B:215:0x0495 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:217:0x04b1 A[Catch: Exception -> 0x045c, TryCatch #15 {Exception -> 0x045c, blocks: (B:201:0x0457, B:206:0x0465, B:208:0x047c, B:211:0x0486, B:213:0x048f, B:216:0x0497, B:218:0x04b7, B:217:0x04b1), top: B:354:0x0457 }] */
    /* JADX WARN: Code duplicated, block: B:227:0x04e5 A[Catch: Exception -> 0x04ea, TRY_LEAVE, TryCatch #2 {Exception -> 0x04ea, blocks: (B:225:0x04cb, B:227:0x04e5), top: B:329:0x04cb }] */
    /* JADX WARN: Code duplicated, block: B:231:0x04ef  */
    /* JADX WARN: Code duplicated, block: B:233:0x04f3  */
    /* JADX WARN: Code duplicated, block: B:237:0x04fd  */
    /* JADX WARN: Code duplicated, block: B:239:0x0503 A[Catch: Exception -> 0x04fa, TRY_LEAVE, TryCatch #17 {Exception -> 0x04fa, blocks: (B:234:0x04f5, B:239:0x0503, B:245:0x0511, B:250:0x051f, B:253:0x052a, B:255:0x0531, B:258:0x053b, B:261:0x0547, B:263:0x054f, B:264:0x0557, B:266:0x055f, B:267:0x0567, B:269:0x056d, B:271:0x0577, B:273:0x057f, B:274:0x0584, B:275:0x0587), top: B:358:0x04f5 }] */
    /* JADX WARN: Code duplicated, block: B:241:0x050a  */
    /* JADX WARN: Code duplicated, block: B:243:0x050e  */
    /* JADX WARN: Code duplicated, block: B:248:0x051b  */
    /* JADX WARN: Code duplicated, block: B:250:0x051f A[Catch: Exception -> 0x04fa, TryCatch #17 {Exception -> 0x04fa, blocks: (B:234:0x04f5, B:239:0x0503, B:245:0x0511, B:250:0x051f, B:253:0x052a, B:255:0x0531, B:258:0x053b, B:261:0x0547, B:263:0x054f, B:264:0x0557, B:266:0x055f, B:267:0x0567, B:269:0x056d, B:271:0x0577, B:273:0x057f, B:274:0x0584, B:275:0x0587), top: B:358:0x04f5 }] */
    /* JADX WARN: Code duplicated, block: B:251:0x0527  */
    /* JADX WARN: Code duplicated, block: B:253:0x052a A[Catch: Exception -> 0x04fa, TryCatch #17 {Exception -> 0x04fa, blocks: (B:234:0x04f5, B:239:0x0503, B:245:0x0511, B:250:0x051f, B:253:0x052a, B:255:0x0531, B:258:0x053b, B:261:0x0547, B:263:0x054f, B:264:0x0557, B:266:0x055f, B:267:0x0567, B:269:0x056d, B:271:0x0577, B:273:0x057f, B:274:0x0584, B:275:0x0587), top: B:358:0x04f5 }] */
    /* JADX WARN: Code duplicated, block: B:255:0x0531 A[Catch: Exception -> 0x04fa, TryCatch #17 {Exception -> 0x04fa, blocks: (B:234:0x04f5, B:239:0x0503, B:245:0x0511, B:250:0x051f, B:253:0x052a, B:255:0x0531, B:258:0x053b, B:261:0x0547, B:263:0x054f, B:264:0x0557, B:266:0x055f, B:267:0x0567, B:269:0x056d, B:271:0x0577, B:273:0x057f, B:274:0x0584, B:275:0x0587), top: B:358:0x04f5 }] */
    /* JADX WARN: Code duplicated, block: B:256:0x0538  */
    /* JADX WARN: Code duplicated, block: B:258:0x053b A[Catch: Exception -> 0x04fa, TryCatch #17 {Exception -> 0x04fa, blocks: (B:234:0x04f5, B:239:0x0503, B:245:0x0511, B:250:0x051f, B:253:0x052a, B:255:0x0531, B:258:0x053b, B:261:0x0547, B:263:0x054f, B:264:0x0557, B:266:0x055f, B:267:0x0567, B:269:0x056d, B:271:0x0577, B:273:0x057f, B:274:0x0584, B:275:0x0587), top: B:358:0x04f5 }] */
    /* JADX WARN: Code duplicated, block: B:259:0x0544  */
    /* JADX WARN: Code duplicated, block: B:261:0x0547 A[Catch: Exception -> 0x04fa, TryCatch #17 {Exception -> 0x04fa, blocks: (B:234:0x04f5, B:239:0x0503, B:245:0x0511, B:250:0x051f, B:253:0x052a, B:255:0x0531, B:258:0x053b, B:261:0x0547, B:263:0x054f, B:264:0x0557, B:266:0x055f, B:267:0x0567, B:269:0x056d, B:271:0x0577, B:273:0x057f, B:274:0x0584, B:275:0x0587), top: B:358:0x04f5 }] */
    /* JADX WARN: Code duplicated, block: B:263:0x054f A[Catch: Exception -> 0x04fa, TryCatch #17 {Exception -> 0x04fa, blocks: (B:234:0x04f5, B:239:0x0503, B:245:0x0511, B:250:0x051f, B:253:0x052a, B:255:0x0531, B:258:0x053b, B:261:0x0547, B:263:0x054f, B:264:0x0557, B:266:0x055f, B:267:0x0567, B:269:0x056d, B:271:0x0577, B:273:0x057f, B:274:0x0584, B:275:0x0587), top: B:358:0x04f5 }] */
    /* JADX WARN: Code duplicated, block: B:266:0x055f A[Catch: Exception -> 0x04fa, TryCatch #17 {Exception -> 0x04fa, blocks: (B:234:0x04f5, B:239:0x0503, B:245:0x0511, B:250:0x051f, B:253:0x052a, B:255:0x0531, B:258:0x053b, B:261:0x0547, B:263:0x054f, B:264:0x0557, B:266:0x055f, B:267:0x0567, B:269:0x056d, B:271:0x0577, B:273:0x057f, B:274:0x0584, B:275:0x0587), top: B:358:0x04f5 }] */
    /* JADX WARN: Code duplicated, block: B:269:0x056d A[Catch: Exception -> 0x04fa, TryCatch #17 {Exception -> 0x04fa, blocks: (B:234:0x04f5, B:239:0x0503, B:245:0x0511, B:250:0x051f, B:253:0x052a, B:255:0x0531, B:258:0x053b, B:261:0x0547, B:263:0x054f, B:264:0x0557, B:266:0x055f, B:267:0x0567, B:269:0x056d, B:271:0x0577, B:273:0x057f, B:274:0x0584, B:275:0x0587), top: B:358:0x04f5 }] */
    /* JADX WARN: Code duplicated, block: B:271:0x0577 A[Catch: Exception -> 0x04fa, TryCatch #17 {Exception -> 0x04fa, blocks: (B:234:0x04f5, B:239:0x0503, B:245:0x0511, B:250:0x051f, B:253:0x052a, B:255:0x0531, B:258:0x053b, B:261:0x0547, B:263:0x054f, B:264:0x0557, B:266:0x055f, B:267:0x0567, B:269:0x056d, B:271:0x0577, B:273:0x057f, B:274:0x0584, B:275:0x0587), top: B:358:0x04f5 }] */
    /* JADX WARN: Code duplicated, block: B:280:0x05b4 A[Catch: all -> 0x05b9, Exception -> 0x05bc, TryCatch #14 {Exception -> 0x05bc, blocks: (B:278:0x059a, B:280:0x05b4, B:287:0x05c2, B:290:0x05ce, B:292:0x05d6), top: B:352:0x059a, outer: #21 }] */
    /* JADX WARN: Code duplicated, block: B:285:0x05be  */
    /* JADX WARN: Code duplicated, block: B:287:0x05c2 A[Catch: all -> 0x05b9, Exception -> 0x05bc, TryCatch #14 {Exception -> 0x05bc, blocks: (B:278:0x059a, B:280:0x05b4, B:287:0x05c2, B:290:0x05ce, B:292:0x05d6), top: B:352:0x059a, outer: #21 }] */
    /* JADX WARN: Code duplicated, block: B:288:0x05ca  */
    /* JADX WARN: Code duplicated, block: B:290:0x05ce A[Catch: all -> 0x05b9, Exception -> 0x05bc, TryCatch #14 {Exception -> 0x05bc, blocks: (B:278:0x059a, B:280:0x05b4, B:287:0x05c2, B:290:0x05ce, B:292:0x05d6), top: B:352:0x059a, outer: #21 }] */
    /* JADX WARN: Code duplicated, block: B:291:0x05d5  */
    /* JADX WARN: Code duplicated, block: B:331:0x02cc A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:360:0x03b9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:364:0x03ef A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:371:0x027f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:375:0x0373 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:378:0x0584 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Instruction removed from duplicated block: B:174:0x03bf, please report this as an issue */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 3 */
    @Override // java.lang.Runnable
    public final void run() {
        JSONObject jSONObjectOptJSONObject;
        String strTrim;
        Handler handler;
        String str;
        JSONObject jSONObject;
        String str2;
        z zVar;
        DashboardActivity dashboardActivity;
        String str3;
        String str4;
        String str5;
        ac acVar;
        JSONObject jSONObjectOptJSONObject2;
        JSONObject jSONObjectOptJSONObject3;
        int i2;
        int iOptInt;
        int iOptInt2;
        JSONObject jSONObjectOptJSONObject4;
        JSONObject jSONObjectOptJSONObject5;
        JSONObject jSONObjectOptJSONObject6;
        JSONObject jSONObjectOptJSONObject7;
        int i3;
        int iOptInt3;
        int iOptInt4;
        String strOptString;
        int iOptInt5;
        JSONObject jSONObjectOptJSONObject8;
        JSONArray jSONArrayOptJSONArray;
        int i4;
        String strOptString2;
        JSONObject jSONObjectOptJSONObject9;
        JSONObject jSONObjectOptJSONObject10;
        JSONObject jSONObjectOptJSONObject11;
        String strOptString3;
        int iOptInt6;
        String strOptString4;
        String str6;
        Handler handler2;
        String str7;
        String string;
        String str8;
        String str9;
        String str10;
        boolean z2;
        JSONArray jSONArrayK;
        JSONArray jSONArray;
        int i5;
        String str11;
        JSONArray jSONArrayOptJSONArray2;
        JSONObject jSONObjectOptJSONObject12;
        String strOptString5;
        JSONObject jSONObjectOptJSONObject13;
        NetworkInfo activeNetworkInfo;
        int i6 = this.b;
        String str12 = "";
        String str13 = "data";
        int i7 = 0;
        DashboardActivity dashboardActivity2 = this.f419c;
        switch (i6) {
            case 0:
                int i8 = (dashboardActivity2.ay + 1) % 2;
                LinearLayout linearLayout = dashboardActivity2.as;
                if (linearLayout == null) {
                    return;
                }
                linearLayout.setVisibility(i8 == 0 ? 0 : 8);
                dashboardActivity2.at.setVisibility(i8 == 1 ? 0 : 8);
                dashboardActivity2.ay = i8;
                dashboardActivity2.y();
                dashboardActivity2.az.postDelayed(this, 5000L);
                return;
            case 1:
                int i9 = DashboardActivity.L;
                dashboardActivity2.getClass();
                try {
                    strTrim = dashboardActivity2.getPackageManager().getPackageInfo(dashboardActivity2.getPackageName(), 0).versionName.trim();
                    break;
                } catch (Exception unused) {
                    strTrim = "2.0";
                }
                dashboardActivity2.ap.execute(new u(2, dashboardActivity2, strTrim));
                return;
            case 2:
                DashboardActivity dashboardActivity3 = dashboardActivity2;
                try {
                    dashboardActivity3.an = w.y.h(dashboardActivity3, dashboardActivity3.an);
                    Handler handler3 = dashboardActivity3.ao;
                    handler3.post(new ac(this, i7));
                    try {
                        G g2 = dashboardActivity3.an;
                        JSONObject jSONObjectOptJSONObject14 = w.y.k(g2, w.y.am("/lightweight-balance", g2.f296a, String.valueOf(g2.b))).optJSONObject("data");
                        JSONObject jSONObjectOptJSONObject15 = jSONObjectOptJSONObject14 != null ? jSONObjectOptJSONObject14.optJSONObject("attribute") : null;
                        if (jSONObjectOptJSONObject15 != null) {
                            JSONObject jSONObjectOptJSONObject16 = jSONObjectOptJSONObject15.optJSONObject("packsPieData");
                            JSONObject jSONObjectOptJSONObject17 = jSONObjectOptJSONObject15.optJSONObject("mainBalance");
                            if (jSONObjectOptJSONObject16 != null) {
                                try {
                                    if (jSONObjectOptJSONObject16.optJSONObject("data") != null) {
                                        str6 = jSONObjectOptJSONObject16.getJSONObject("data").optString("remaining", "--") + " " + jSONObjectOptJSONObject16.getJSONObject("data").optString("unit", "");
                                    } else {
                                        str6 = "--";
                                    }
                                    if (jSONObjectOptJSONObject16 == null) {
                                        try {
                                            if (jSONObjectOptJSONObject16.optJSONObject("voice") != null) {
                                                handler2 = handler3;
                                                try {
                                                    StringBuilder sb = new StringBuilder();
                                                    str7 = "couponBalance";
                                                    try {
                                                        sb.append(jSONObjectOptJSONObject16.getJSONObject("voice").optString("remaining", "--"));
                                                        sb.append(" ");
                                                        sb.append(jSONObjectOptJSONObject16.getJSONObject("voice").optString("unit", ""));
                                                        string = sb.toString();
                                                    } catch (Exception e2) {
                                                        e = e2;
                                                        str2 = "attribute";
                                                        zVar = this;
                                                        handler = handler2;
                                                        str = str7;
                                                        jSONObject = null;
                                                        e.getMessage();
                                                        dashboardActivity = dashboardActivity3;
                                                        G g3 = dashboardActivity.an;
                                                        str3 = str13;
                                                        try {
                                                            jSONObjectOptJSONObject9 = w.y.k(g3, w.y.am("/point-system/dashboard", g3.f296a, String.valueOf(g3.b))).optJSONObject(str3);
                                                            if (jSONObjectOptJSONObject9 != null) {
                                                                str4 = str2;
                                                                try {
                                                                    jSONObjectOptJSONObject10 = jSONObjectOptJSONObject9.optJSONObject(str4);
                                                                } catch (Exception e3) {
                                                                    e = e3;
                                                                    e.getMessage();
                                                                }
                                                            } else {
                                                                str4 = str2;
                                                                jSONObjectOptJSONObject10 = jSONObject;
                                                            }
                                                            if (jSONObjectOptJSONObject10 != null) {
                                                                String strOptString6 = jSONObjectOptJSONObject10.optString("starStatusLabel", "STAR");
                                                                int iOptInt7 = jSONObjectOptJSONObject10.optInt("totalPoint", 0);
                                                                jSONObjectOptJSONObject11 = jSONObjectOptJSONObject10.optJSONObject("mostExpiry");
                                                                if (jSONObjectOptJSONObject11 != null) {
                                                                    strOptString3 = jSONObjectOptJSONObject11.optString("date", str12);
                                                                } else {
                                                                    strOptString3 = str12;
                                                                }
                                                                if (jSONObjectOptJSONObject11 != null) {
                                                                    iOptInt6 = jSONObjectOptJSONObject11.optInt("point", 0);
                                                                } else {
                                                                    iOptInt6 = 0;
                                                                }
                                                                if (strOptString3.isEmpty()) {
                                                                    strOptString4 = jSONObjectOptJSONObject10.optString("validityEndDateText", str12);
                                                                } else {
                                                                    strOptString4 = jSONObjectOptJSONObject10.optString("validityEndDateText", str12);
                                                                }
                                                                handler.post(new ae(zVar, strOptString6, iOptInt7, strOptString4));
                                                            }
                                                            break;
                                                        } catch (Exception e4) {
                                                            e = e4;
                                                            str4 = str2;
                                                            e.getMessage();
                                                            G g4 = dashboardActivity.an;
                                                            jSONObjectOptJSONObject4 = w.y.k(g4, w.y.am("/tohtohunited/get-coupon-balance", g4.f296a, String.valueOf(g4.b))).optJSONObject(str3);
                                                            if (jSONObjectOptJSONObject4 != null) {
                                                                jSONObjectOptJSONObject5 = jSONObjectOptJSONObject4.optJSONObject(str4);
                                                                break;
                                                            } else {
                                                                jSONObjectOptJSONObject5 = jSONObject;
                                                            }
                                                            if (jSONObjectOptJSONObject5 != null) {
                                                                str5 = str;
                                                                try {
                                                                    jSONObjectOptJSONObject6 = jSONObjectOptJSONObject5.optJSONObject(str5);
                                                                } catch (Exception e5) {
                                                                    e = e5;
                                                                    e.getMessage();
                                                                }
                                                            } else {
                                                                str5 = str;
                                                                jSONObjectOptJSONObject6 = jSONObject;
                                                            }
                                                            if (jSONObjectOptJSONObject5 != null) {
                                                                jSONObjectOptJSONObject7 = jSONObjectOptJSONObject5.optJSONObject("profileInfo");
                                                            } else {
                                                                jSONObjectOptJSONObject7 = jSONObject;
                                                            }
                                                            if (jSONObjectOptJSONObject6 != null) {
                                                                i3 = 0;
                                                                iOptInt3 = jSONObjectOptJSONObject6.optInt("totalCoupon", 0);
                                                            } else {
                                                                i3 = 0;
                                                                iOptInt3 = 0;
                                                            }
                                                            if (jSONObjectOptJSONObject6 != null) {
                                                                iOptInt4 = jSONObjectOptJSONObject6.optInt("normalCoupon", i3);
                                                            } else {
                                                                iOptInt4 = 0;
                                                            }
                                                            if (jSONObjectOptJSONObject6 != null) {
                                                                jSONObjectOptJSONObject6.optInt("specialCoupon", i3);
                                                            }
                                                            if (jSONObjectOptJSONObject7 != null) {
                                                                strOptString = jSONObjectOptJSONObject7.optString("name", str12);
                                                            } else {
                                                                strOptString = str12;
                                                            }
                                                            if (jSONObjectOptJSONObject5 != null) {
                                                                iOptInt5 = jSONObjectOptJSONObject5.optInt("isDailyBonusPopup", 0);
                                                            } else {
                                                                iOptInt5 = 0;
                                                            }
                                                            if (jSONObjectOptJSONObject5 != null) {
                                                                jSONObjectOptJSONObject8 = jSONObjectOptJSONObject5.optJSONObject("luckyChanceItems");
                                                                if (jSONObjectOptJSONObject8 != null) {
                                                                    dashboardActivity.E = jSONObjectOptJSONObject8.optJSONArray("packPurchase");
                                                                }
                                                                jSONArrayOptJSONArray = jSONObjectOptJSONObject5.optJSONArray("infoText");
                                                                if (jSONArrayOptJSONArray != null) {
                                                                    dashboardActivity.F = new ArrayList();
                                                                    for (i4 = 0; i4 < jSONArrayOptJSONArray.length(); i4++) {
                                                                        strOptString2 = jSONArrayOptJSONArray.optString(i4, str12);
                                                                        if (strOptString2.isEmpty()) {
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            handler.post(new af(this, strOptString, iOptInt3, iOptInt4, iOptInt5));
                                                            G g5 = dashboardActivity.an;
                                                            jSONObjectOptJSONObject2 = w.y.k(g5, w.y.am("/goldenfarm/get-coupon-balance", g5.f296a, String.valueOf(g5.b))).optJSONObject(str3);
                                                            if (jSONObjectOptJSONObject2 != null) {
                                                                jSONObjectOptJSONObject3 = jSONObjectOptJSONObject2.optJSONObject(str4);
                                                            } else {
                                                                jSONObjectOptJSONObject3 = jSONObject;
                                                            }
                                                            if (jSONObjectOptJSONObject3 != null) {
                                                                i2 = 0;
                                                                iOptInt = jSONObjectOptJSONObject3.optInt(str5, 0);
                                                            } else {
                                                                i2 = 0;
                                                                iOptInt = 0;
                                                            }
                                                            if (jSONObjectOptJSONObject3 != null) {
                                                                iOptInt2 = jSONObjectOptJSONObject3.optInt("weeklyTickets", i2);
                                                            } else {
                                                                iOptInt2 = 0;
                                                            }
                                                            handler.post(new ag(zVar, iOptInt, iOptInt2));
                                                            acVar = new ac(zVar, 2);
                                                            handler.post(acVar);
                                                            return;
                                                        }
                                                        G g6 = dashboardActivity.an;
                                                        jSONObjectOptJSONObject4 = w.y.k(g6, w.y.am("/tohtohunited/get-coupon-balance", g6.f296a, String.valueOf(g6.b))).optJSONObject(str3);
                                                        if (jSONObjectOptJSONObject4 != null) {
                                                            jSONObjectOptJSONObject5 = jSONObjectOptJSONObject4.optJSONObject(str4);
                                                            break;
                                                        } else {
                                                            jSONObjectOptJSONObject5 = jSONObject;
                                                        }
                                                        if (jSONObjectOptJSONObject5 != null) {
                                                            str5 = str;
                                                            jSONObjectOptJSONObject6 = jSONObjectOptJSONObject5.optJSONObject(str5);
                                                        } else {
                                                            str5 = str;
                                                            jSONObjectOptJSONObject6 = jSONObject;
                                                        }
                                                        if (jSONObjectOptJSONObject5 != null) {
                                                            jSONObjectOptJSONObject7 = jSONObjectOptJSONObject5.optJSONObject("profileInfo");
                                                        } else {
                                                            jSONObjectOptJSONObject7 = jSONObject;
                                                        }
                                                        if (jSONObjectOptJSONObject6 != null) {
                                                            i3 = 0;
                                                            iOptInt3 = jSONObjectOptJSONObject6.optInt("totalCoupon", 0);
                                                        } else {
                                                            i3 = 0;
                                                            iOptInt3 = 0;
                                                        }
                                                        if (jSONObjectOptJSONObject6 != null) {
                                                            iOptInt4 = jSONObjectOptJSONObject6.optInt("normalCoupon", i3);
                                                        } else {
                                                            iOptInt4 = 0;
                                                        }
                                                        if (jSONObjectOptJSONObject6 != null) {
                                                            jSONObjectOptJSONObject6.optInt("specialCoupon", i3);
                                                        }
                                                        if (jSONObjectOptJSONObject7 != null) {
                                                            strOptString = jSONObjectOptJSONObject7.optString("name", str12);
                                                        } else {
                                                            strOptString = str12;
                                                        }
                                                        if (jSONObjectOptJSONObject5 != null) {
                                                            iOptInt5 = jSONObjectOptJSONObject5.optInt("isDailyBonusPopup", 0);
                                                        } else {
                                                            iOptInt5 = 0;
                                                        }
                                                        if (jSONObjectOptJSONObject5 != null) {
                                                            jSONObjectOptJSONObject8 = jSONObjectOptJSONObject5.optJSONObject("luckyChanceItems");
                                                            if (jSONObjectOptJSONObject8 != null) {
                                                                dashboardActivity.E = jSONObjectOptJSONObject8.optJSONArray("packPurchase");
                                                            }
                                                            jSONArrayOptJSONArray = jSONObjectOptJSONObject5.optJSONArray("infoText");
                                                            if (jSONArrayOptJSONArray != null) {
                                                                dashboardActivity.F = new ArrayList();
                                                                while (i4 < jSONArrayOptJSONArray.length()) {
                                                                    strOptString2 = jSONArrayOptJSONArray.optString(i4, str12);
                                                                    if (strOptString2.isEmpty()) {
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        handler.post(new af(this, strOptString, iOptInt3, iOptInt4, iOptInt5));
                                                        try {
                                                            G g7 = dashboardActivity.an;
                                                            jSONObjectOptJSONObject2 = w.y.k(g7, w.y.am("/goldenfarm/get-coupon-balance", g7.f296a, String.valueOf(g7.b))).optJSONObject(str3);
                                                            if (jSONObjectOptJSONObject2 != null) {
                                                                jSONObjectOptJSONObject3 = jSONObjectOptJSONObject2.optJSONObject(str4);
                                                            } else {
                                                                jSONObjectOptJSONObject3 = jSONObject;
                                                            }
                                                            if (jSONObjectOptJSONObject3 != null) {
                                                                i2 = 0;
                                                                iOptInt = jSONObjectOptJSONObject3.optInt(str5, 0);
                                                            } else {
                                                                i2 = 0;
                                                                iOptInt = 0;
                                                            }
                                                            if (jSONObjectOptJSONObject3 != null) {
                                                                iOptInt2 = jSONObjectOptJSONObject3.optInt("weeklyTickets", i2);
                                                            } else {
                                                                iOptInt2 = 0;
                                                            }
                                                            handler.post(new ag(zVar, iOptInt, iOptInt2));
                                                            acVar = new ac(zVar, 2);
                                                        } catch (Exception e6) {
                                                            e6.getMessage();
                                                            acVar = new ac(zVar, 2);
                                                        }
                                                        handler.post(acVar);
                                                        return;
                                                    }
                                                } catch (Exception e7) {
                                                    e = e7;
                                                    str7 = "couponBalance";
                                                    str2 = "attribute";
                                                    zVar = this;
                                                    handler = handler2;
                                                    str = str7;
                                                    jSONObject = null;
                                                    e.getMessage();
                                                    dashboardActivity = dashboardActivity3;
                                                    G g8 = dashboardActivity.an;
                                                    str3 = str13;
                                                    jSONObjectOptJSONObject9 = w.y.k(g8, w.y.am("/point-system/dashboard", g8.f296a, String.valueOf(g8.b))).optJSONObject(str3);
                                                    if (jSONObjectOptJSONObject9 != null) {
                                                        str4 = str2;
                                                        jSONObjectOptJSONObject10 = jSONObjectOptJSONObject9.optJSONObject(str4);
                                                    } else {
                                                        str4 = str2;
                                                        jSONObjectOptJSONObject10 = jSONObject;
                                                    }
                                                    if (jSONObjectOptJSONObject10 != null) {
                                                        String strOptString7 = jSONObjectOptJSONObject10.optString("starStatusLabel", "STAR");
                                                        int iOptInt8 = jSONObjectOptJSONObject10.optInt("totalPoint", 0);
                                                        jSONObjectOptJSONObject11 = jSONObjectOptJSONObject10.optJSONObject("mostExpiry");
                                                        if (jSONObjectOptJSONObject11 != null) {
                                                            strOptString3 = jSONObjectOptJSONObject11.optString("date", str12);
                                                        } else {
                                                            strOptString3 = str12;
                                                        }
                                                        if (jSONObjectOptJSONObject11 != null) {
                                                            iOptInt6 = jSONObjectOptJSONObject11.optInt("point", 0);
                                                        } else {
                                                            iOptInt6 = 0;
                                                        }
                                                        if (strOptString3.isEmpty()) {
                                                            strOptString4 = jSONObjectOptJSONObject10.optString("validityEndDateText", str12);
                                                        } else {
                                                            strOptString4 = jSONObjectOptJSONObject10.optString("validityEndDateText", str12);
                                                        }
                                                        handler.post(new ae(zVar, strOptString7, iOptInt8, strOptString4));
                                                        break;
                                                    }
                                                    G g9 = dashboardActivity.an;
                                                    jSONObjectOptJSONObject4 = w.y.k(g9, w.y.am("/tohtohunited/get-coupon-balance", g9.f296a, String.valueOf(g9.b))).optJSONObject(str3);
                                                    if (jSONObjectOptJSONObject4 != null) {
                                                        jSONObjectOptJSONObject5 = jSONObjectOptJSONObject4.optJSONObject(str4);
                                                        break;
                                                    } else {
                                                        jSONObjectOptJSONObject5 = jSONObject;
                                                    }
                                                    if (jSONObjectOptJSONObject5 != null) {
                                                        str5 = str;
                                                        jSONObjectOptJSONObject6 = jSONObjectOptJSONObject5.optJSONObject(str5);
                                                    } else {
                                                        str5 = str;
                                                        jSONObjectOptJSONObject6 = jSONObject;
                                                    }
                                                    if (jSONObjectOptJSONObject5 != null) {
                                                        jSONObjectOptJSONObject7 = jSONObjectOptJSONObject5.optJSONObject("profileInfo");
                                                    } else {
                                                        jSONObjectOptJSONObject7 = jSONObject;
                                                    }
                                                    if (jSONObjectOptJSONObject6 != null) {
                                                        i3 = 0;
                                                        iOptInt3 = jSONObjectOptJSONObject6.optInt("totalCoupon", 0);
                                                    } else {
                                                        i3 = 0;
                                                        iOptInt3 = 0;
                                                    }
                                                    if (jSONObjectOptJSONObject6 != null) {
                                                        iOptInt4 = jSONObjectOptJSONObject6.optInt("normalCoupon", i3);
                                                    } else {
                                                        iOptInt4 = 0;
                                                    }
                                                    if (jSONObjectOptJSONObject6 != null) {
                                                        jSONObjectOptJSONObject6.optInt("specialCoupon", i3);
                                                    }
                                                    if (jSONObjectOptJSONObject7 != null) {
                                                        strOptString = jSONObjectOptJSONObject7.optString("name", str12);
                                                    } else {
                                                        strOptString = str12;
                                                    }
                                                    if (jSONObjectOptJSONObject5 != null) {
                                                        iOptInt5 = jSONObjectOptJSONObject5.optInt("isDailyBonusPopup", 0);
                                                    } else {
                                                        iOptInt5 = 0;
                                                    }
                                                    if (jSONObjectOptJSONObject5 != null) {
                                                        jSONObjectOptJSONObject8 = jSONObjectOptJSONObject5.optJSONObject("luckyChanceItems");
                                                        if (jSONObjectOptJSONObject8 != null) {
                                                            dashboardActivity.E = jSONObjectOptJSONObject8.optJSONArray("packPurchase");
                                                        }
                                                        jSONArrayOptJSONArray = jSONObjectOptJSONObject5.optJSONArray("infoText");
                                                        if (jSONArrayOptJSONArray != null) {
                                                            dashboardActivity.F = new ArrayList();
                                                            while (i4 < jSONArrayOptJSONArray.length()) {
                                                                strOptString2 = jSONArrayOptJSONArray.optString(i4, str12);
                                                                if (strOptString2.isEmpty()) {
                                                                }
                                                            }
                                                        }
                                                    }
                                                    handler.post(new af(this, strOptString, iOptInt3, iOptInt4, iOptInt5));
                                                    G g10 = dashboardActivity.an;
                                                    jSONObjectOptJSONObject2 = w.y.k(g10, w.y.am("/goldenfarm/get-coupon-balance", g10.f296a, String.valueOf(g10.b))).optJSONObject(str3);
                                                    if (jSONObjectOptJSONObject2 != null) {
                                                        jSONObjectOptJSONObject3 = jSONObjectOptJSONObject2.optJSONObject(str4);
                                                    } else {
                                                        jSONObjectOptJSONObject3 = jSONObject;
                                                    }
                                                    if (jSONObjectOptJSONObject3 != null) {
                                                        i2 = 0;
                                                        iOptInt = jSONObjectOptJSONObject3.optInt(str5, 0);
                                                    } else {
                                                        i2 = 0;
                                                        iOptInt = 0;
                                                    }
                                                    if (jSONObjectOptJSONObject3 != null) {
                                                        iOptInt2 = jSONObjectOptJSONObject3.optInt("weeklyTickets", i2);
                                                    } else {
                                                        iOptInt2 = 0;
                                                    }
                                                    handler.post(new ag(zVar, iOptInt, iOptInt2));
                                                    acVar = new ac(zVar, 2);
                                                    handler.post(acVar);
                                                    return;
                                                }
                                            } else {
                                                handler2 = handler3;
                                                str7 = "couponBalance";
                                                string = "--";
                                            }
                                        } catch (Exception e8) {
                                            e = e8;
                                            handler2 = handler3;
                                        }
                                    } else {
                                        handler2 = handler3;
                                        str7 = "couponBalance";
                                        string = "--";
                                    }
                                    if (jSONObjectOptJSONObject16 == null) {
                                        str8 = "attribute";
                                        str9 = "--";
                                        if (jSONObjectOptJSONObject17 != null) {
                                            str10 = jSONObjectOptJSONObject17.optString("value", "--") + " " + jSONObjectOptJSONObject17.optString("currency", "Ks");
                                        } else {
                                            str10 = "--";
                                        }
                                        if (jSONObjectOptJSONObject17 == null) {
                                            z2 = false;
                                        } else {
                                            z2 = false;
                                        }
                                        JSONArray jSONArrayK2 = DashboardActivity.k(dashboardActivity3, jSONObjectOptJSONObject16, "data");
                                        JSONArray jSONArrayK3 = DashboardActivity.k(dashboardActivity3, jSONObjectOptJSONObject16, "voice");
                                        JSONArray jSONArrayK4 = DashboardActivity.k(dashboardActivity3, jSONObjectOptJSONObject16, "sms");
                                        jSONArrayK = DashboardActivity.k(dashboardActivity3, jSONObjectOptJSONObject16, "ett");
                                        jSONArray = new JSONArray();
                                        while (i5 < jSONArrayK.length()) {
                                            jSONObjectOptJSONObject13 = jSONArrayK.optJSONObject(i5);
                                            if (jSONObjectOptJSONObject13 != null) {
                                                jSONArray.put(jSONObjectOptJSONObject13);
                                            }
                                        }
                                        str = str7;
                                        dashboardActivity3 = dashboardActivity3;
                                        str13 = "data";
                                        jSONObject = null;
                                        str2 = str8;
                                        handler = handler2;
                                        handler.post(new ad(this, str6, string, str9, str10, z2, jSONArrayK2, jSONArrayK3, jSONArrayK4, jSONArrayK, jSONArray));
                                        if (jSONObjectOptJSONObject16 == null) {
                                            zVar = this;
                                            str12 = "";
                                        } else if (jSONObjectOptJSONObject16.optJSONObject("ett") != null) {
                                            JSONObject jSONObject2 = jSONObjectOptJSONObject16.getJSONObject("ett");
                                            str11 = jSONObject2.optString("remaining", "--") + " " + jSONObject2.optString("unit", "Ks");
                                            jSONArrayOptJSONArray2 = jSONObject2.optJSONArray("packsList");
                                            if (jSONArrayOptJSONArray2 != null) {
                                                str12 = "";
                                                strOptString5 = str12;
                                                zVar = this;
                                                handler.post(new v(zVar, str11, strOptString5, 1));
                                            } else if (jSONArrayOptJSONArray2.length() > 0) {
                                                str12 = "";
                                                strOptString5 = str12;
                                                zVar = this;
                                                handler.post(new v(zVar, str11, strOptString5, 1));
                                            } else {
                                                str12 = "";
                                                strOptString5 = str12;
                                                zVar = this;
                                                handler.post(new v(zVar, str11, strOptString5, 1));
                                            }
                                        } else {
                                            zVar = this;
                                            str12 = "";
                                        }
                                        break;
                                    } else {
                                        try {
                                            if (jSONObjectOptJSONObject16.optJSONObject("sms") != null) {
                                                str8 = "attribute";
                                                try {
                                                    str9 = jSONObjectOptJSONObject16.getJSONObject("sms").optString("remaining", "--") + " " + jSONObjectOptJSONObject16.getJSONObject("sms").optString("unit", "");
                                                } catch (Exception e9) {
                                                    e = e9;
                                                    zVar = this;
                                                    handler = handler2;
                                                    str = str7;
                                                    str2 = str8;
                                                    jSONObject = null;
                                                    e.getMessage();
                                                    dashboardActivity = dashboardActivity3;
                                                    G g11 = dashboardActivity.an;
                                                    str3 = str13;
                                                    jSONObjectOptJSONObject9 = w.y.k(g11, w.y.am("/point-system/dashboard", g11.f296a, String.valueOf(g11.b))).optJSONObject(str3);
                                                    if (jSONObjectOptJSONObject9 != null) {
                                                        str4 = str2;
                                                        jSONObjectOptJSONObject10 = jSONObjectOptJSONObject9.optJSONObject(str4);
                                                    } else {
                                                        str4 = str2;
                                                        jSONObjectOptJSONObject10 = jSONObject;
                                                    }
                                                    if (jSONObjectOptJSONObject10 != null) {
                                                        String strOptString8 = jSONObjectOptJSONObject10.optString("starStatusLabel", "STAR");
                                                        int iOptInt9 = jSONObjectOptJSONObject10.optInt("totalPoint", 0);
                                                        jSONObjectOptJSONObject11 = jSONObjectOptJSONObject10.optJSONObject("mostExpiry");
                                                        if (jSONObjectOptJSONObject11 != null) {
                                                            strOptString3 = jSONObjectOptJSONObject11.optString("date", str12);
                                                        } else {
                                                            strOptString3 = str12;
                                                        }
                                                        if (jSONObjectOptJSONObject11 != null) {
                                                            iOptInt6 = jSONObjectOptJSONObject11.optInt("point", 0);
                                                        } else {
                                                            iOptInt6 = 0;
                                                        }
                                                        if (strOptString3.isEmpty()) {
                                                            strOptString4 = jSONObjectOptJSONObject10.optString("validityEndDateText", str12);
                                                        } else {
                                                            strOptString4 = jSONObjectOptJSONObject10.optString("validityEndDateText", str12);
                                                        }
                                                        handler.post(new ae(zVar, strOptString8, iOptInt9, strOptString4));
                                                        break;
                                                    }
                                                    G g12 = dashboardActivity.an;
                                                    jSONObjectOptJSONObject4 = w.y.k(g12, w.y.am("/tohtohunited/get-coupon-balance", g12.f296a, String.valueOf(g12.b))).optJSONObject(str3);
                                                    if (jSONObjectOptJSONObject4 != null) {
                                                        jSONObjectOptJSONObject5 = jSONObjectOptJSONObject4.optJSONObject(str4);
                                                        break;
                                                    } else {
                                                        jSONObjectOptJSONObject5 = jSONObject;
                                                    }
                                                    if (jSONObjectOptJSONObject5 != null) {
                                                        str5 = str;
                                                        jSONObjectOptJSONObject6 = jSONObjectOptJSONObject5.optJSONObject(str5);
                                                    } else {
                                                        str5 = str;
                                                        jSONObjectOptJSONObject6 = jSONObject;
                                                    }
                                                    if (jSONObjectOptJSONObject5 != null) {
                                                        jSONObjectOptJSONObject7 = jSONObjectOptJSONObject5.optJSONObject("profileInfo");
                                                    } else {
                                                        jSONObjectOptJSONObject7 = jSONObject;
                                                    }
                                                    if (jSONObjectOptJSONObject6 != null) {
                                                        i3 = 0;
                                                        iOptInt3 = jSONObjectOptJSONObject6.optInt("totalCoupon", 0);
                                                    } else {
                                                        i3 = 0;
                                                        iOptInt3 = 0;
                                                    }
                                                    if (jSONObjectOptJSONObject6 != null) {
                                                        iOptInt4 = jSONObjectOptJSONObject6.optInt("normalCoupon", i3);
                                                    } else {
                                                        iOptInt4 = 0;
                                                    }
                                                    if (jSONObjectOptJSONObject6 != null) {
                                                        jSONObjectOptJSONObject6.optInt("specialCoupon", i3);
                                                    }
                                                    if (jSONObjectOptJSONObject7 != null) {
                                                        strOptString = jSONObjectOptJSONObject7.optString("name", str12);
                                                    } else {
                                                        strOptString = str12;
                                                    }
                                                    if (jSONObjectOptJSONObject5 != null) {
                                                        iOptInt5 = jSONObjectOptJSONObject5.optInt("isDailyBonusPopup", 0);
                                                    } else {
                                                        iOptInt5 = 0;
                                                    }
                                                    if (jSONObjectOptJSONObject5 != null) {
                                                        jSONObjectOptJSONObject8 = jSONObjectOptJSONObject5.optJSONObject("luckyChanceItems");
                                                        if (jSONObjectOptJSONObject8 != null) {
                                                            dashboardActivity.E = jSONObjectOptJSONObject8.optJSONArray("packPurchase");
                                                        }
                                                        jSONArrayOptJSONArray = jSONObjectOptJSONObject5.optJSONArray("infoText");
                                                        if (jSONArrayOptJSONArray != null) {
                                                            dashboardActivity.F = new ArrayList();
                                                            while (i4 < jSONArrayOptJSONArray.length()) {
                                                                strOptString2 = jSONArrayOptJSONArray.optString(i4, str12);
                                                                if (strOptString2.isEmpty()) {
                                                                }
                                                            }
                                                        }
                                                    }
                                                    handler.post(new af(this, strOptString, iOptInt3, iOptInt4, iOptInt5));
                                                    G g13 = dashboardActivity.an;
                                                    jSONObjectOptJSONObject2 = w.y.k(g13, w.y.am("/goldenfarm/get-coupon-balance", g13.f296a, String.valueOf(g13.b))).optJSONObject(str3);
                                                    if (jSONObjectOptJSONObject2 != null) {
                                                        jSONObjectOptJSONObject3 = jSONObjectOptJSONObject2.optJSONObject(str4);
                                                    } else {
                                                        jSONObjectOptJSONObject3 = jSONObject;
                                                    }
                                                    if (jSONObjectOptJSONObject3 != null) {
                                                        i2 = 0;
                                                        iOptInt = jSONObjectOptJSONObject3.optInt(str5, 0);
                                                    } else {
                                                        i2 = 0;
                                                        iOptInt = 0;
                                                    }
                                                    if (jSONObjectOptJSONObject3 != null) {
                                                        iOptInt2 = jSONObjectOptJSONObject3.optInt("weeklyTickets", i2);
                                                    } else {
                                                        iOptInt2 = 0;
                                                    }
                                                    handler.post(new ag(zVar, iOptInt, iOptInt2));
                                                    acVar = new ac(zVar, 2);
                                                    handler.post(acVar);
                                                    return;
                                                }
                                            } else {
                                                str8 = "attribute";
                                                str9 = "--";
                                            }
                                            if (jSONObjectOptJSONObject17 != null) {
                                                str10 = jSONObjectOptJSONObject17.optString("value", "--") + " " + jSONObjectOptJSONObject17.optString("currency", "Ks");
                                            } else {
                                                str10 = "--";
                                            }
                                            if (jSONObjectOptJSONObject17 == null && jSONObjectOptJSONObject17.optBoolean("isBalanceLow", false)) {
                                                z2 = true;
                                            } else {
                                                z2 = false;
                                            }
                                            JSONArray jSONArrayK5 = DashboardActivity.k(dashboardActivity3, jSONObjectOptJSONObject16, "data");
                                            JSONArray jSONArrayK6 = DashboardActivity.k(dashboardActivity3, jSONObjectOptJSONObject16, "voice");
                                            JSONArray jSONArrayK7 = DashboardActivity.k(dashboardActivity3, jSONObjectOptJSONObject16, "sms");
                                            jSONArrayK = DashboardActivity.k(dashboardActivity3, jSONObjectOptJSONObject16, "ett");
                                            jSONArray = new JSONArray();
                                            for (i5 = 0; i5 < jSONArrayK.length(); i5++) {
                                                jSONObjectOptJSONObject13 = jSONArrayK.optJSONObject(i5);
                                                if (jSONObjectOptJSONObject13 != null) {
                                                    jSONArray.put(jSONObjectOptJSONObject13);
                                                }
                                            }
                                            str = str7;
                                            dashboardActivity3 = dashboardActivity3;
                                            str13 = "data";
                                            jSONObject = null;
                                            str2 = str8;
                                            handler = handler2;
                                            try {
                                                handler.post(new ad(this, str6, string, str9, str10, z2, jSONArrayK5, jSONArrayK6, jSONArrayK7, jSONArrayK, jSONArray));
                                                if (jSONObjectOptJSONObject16 == null) {
                                                    try {
                                                        if (jSONObjectOptJSONObject16.optJSONObject("ett") != null) {
                                                            JSONObject jSONObject3 = jSONObjectOptJSONObject16.getJSONObject("ett");
                                                            str11 = jSONObject3.optString("remaining", "--") + " " + jSONObject3.optString("unit", "Ks");
                                                            jSONArrayOptJSONArray2 = jSONObject3.optJSONArray("packsList");
                                                            if (jSONArrayOptJSONArray2 != null) {
                                                                str12 = "";
                                                                strOptString5 = str12;
                                                                zVar = this;
                                                                handler.post(new v(zVar, str11, strOptString5, 1));
                                                                break;
                                                            } else {
                                                                try {
                                                                    if (jSONArrayOptJSONArray2.length() > 0 || (jSONObjectOptJSONObject12 = jSONArrayOptJSONArray2.optJSONObject(0)) == null) {
                                                                        str12 = "";
                                                                        strOptString5 = str12;
                                                                    } else {
                                                                        str12 = "";
                                                                        try {
                                                                            strOptString5 = jSONObjectOptJSONObject12.optString("expireAtV2", str12);
                                                                        } catch (Exception unused2) {
                                                                            zVar = this;
                                                                        }
                                                                    }
                                                                    zVar = this;
                                                                    try {
                                                                        handler.post(new v(zVar, str11, strOptString5, 1));
                                                                        break;
                                                                    } catch (Exception unused3) {
                                                                    }
                                                                } catch (Exception unused4) {
                                                                    str12 = "";
                                                                    zVar = this;
                                                                }
                                                            }
                                                        } else {
                                                            zVar = this;
                                                            str12 = "";
                                                        }
                                                    } catch (Exception unused5) {
                                                    }
                                                } else {
                                                    zVar = this;
                                                    str12 = "";
                                                }
                                            } catch (Exception e10) {
                                                e = e10;
                                                zVar = this;
                                                str12 = "";
                                                e.getMessage();
                                            }
                                        } catch (Exception e11) {
                                            e = e11;
                                            str8 = "attribute";
                                            zVar = this;
                                            handler = handler2;
                                            str = str7;
                                            str2 = str8;
                                            jSONObject = null;
                                            e.getMessage();
                                            dashboardActivity = dashboardActivity3;
                                            G g14 = dashboardActivity.an;
                                            str3 = str13;
                                            jSONObjectOptJSONObject9 = w.y.k(g14, w.y.am("/point-system/dashboard", g14.f296a, String.valueOf(g14.b))).optJSONObject(str3);
                                            if (jSONObjectOptJSONObject9 != null) {
                                                str4 = str2;
                                                jSONObjectOptJSONObject10 = jSONObjectOptJSONObject9.optJSONObject(str4);
                                            } else {
                                                str4 = str2;
                                                jSONObjectOptJSONObject10 = jSONObject;
                                            }
                                            if (jSONObjectOptJSONObject10 != null) {
                                                String strOptString9 = jSONObjectOptJSONObject10.optString("starStatusLabel", "STAR");
                                                int iOptInt10 = jSONObjectOptJSONObject10.optInt("totalPoint", 0);
                                                jSONObjectOptJSONObject11 = jSONObjectOptJSONObject10.optJSONObject("mostExpiry");
                                                if (jSONObjectOptJSONObject11 != null) {
                                                    strOptString3 = jSONObjectOptJSONObject11.optString("date", str12);
                                                } else {
                                                    strOptString3 = str12;
                                                }
                                                if (jSONObjectOptJSONObject11 != null) {
                                                    iOptInt6 = jSONObjectOptJSONObject11.optInt("point", 0);
                                                } else {
                                                    iOptInt6 = 0;
                                                }
                                                if (strOptString3.isEmpty()) {
                                                    strOptString4 = jSONObjectOptJSONObject10.optString("validityEndDateText", str12);
                                                } else {
                                                    strOptString4 = jSONObjectOptJSONObject10.optString("validityEndDateText", str12);
                                                }
                                                handler.post(new ae(zVar, strOptString9, iOptInt10, strOptString4));
                                                break;
                                            }
                                            G g15 = dashboardActivity.an;
                                            jSONObjectOptJSONObject4 = w.y.k(g15, w.y.am("/tohtohunited/get-coupon-balance", g15.f296a, String.valueOf(g15.b))).optJSONObject(str3);
                                            if (jSONObjectOptJSONObject4 != null) {
                                                jSONObjectOptJSONObject5 = jSONObjectOptJSONObject4.optJSONObject(str4);
                                                break;
                                            } else {
                                                jSONObjectOptJSONObject5 = jSONObject;
                                            }
                                            if (jSONObjectOptJSONObject5 != null) {
                                                str5 = str;
                                                jSONObjectOptJSONObject6 = jSONObjectOptJSONObject5.optJSONObject(str5);
                                            } else {
                                                str5 = str;
                                                jSONObjectOptJSONObject6 = jSONObject;
                                            }
                                            if (jSONObjectOptJSONObject5 != null) {
                                                jSONObjectOptJSONObject7 = jSONObjectOptJSONObject5.optJSONObject("profileInfo");
                                            } else {
                                                jSONObjectOptJSONObject7 = jSONObject;
                                            }
                                            if (jSONObjectOptJSONObject6 != null) {
                                                i3 = 0;
                                                iOptInt3 = jSONObjectOptJSONObject6.optInt("totalCoupon", 0);
                                            } else {
                                                i3 = 0;
                                                iOptInt3 = 0;
                                            }
                                            if (jSONObjectOptJSONObject6 != null) {
                                                iOptInt4 = jSONObjectOptJSONObject6.optInt("normalCoupon", i3);
                                            } else {
                                                iOptInt4 = 0;
                                            }
                                            if (jSONObjectOptJSONObject6 != null) {
                                                jSONObjectOptJSONObject6.optInt("specialCoupon", i3);
                                            }
                                            if (jSONObjectOptJSONObject7 != null) {
                                                strOptString = jSONObjectOptJSONObject7.optString("name", str12);
                                            } else {
                                                strOptString = str12;
                                            }
                                            if (jSONObjectOptJSONObject5 != null) {
                                                iOptInt5 = jSONObjectOptJSONObject5.optInt("isDailyBonusPopup", 0);
                                            } else {
                                                iOptInt5 = 0;
                                            }
                                            if (jSONObjectOptJSONObject5 != null) {
                                                jSONObjectOptJSONObject8 = jSONObjectOptJSONObject5.optJSONObject("luckyChanceItems");
                                                if (jSONObjectOptJSONObject8 != null) {
                                                    dashboardActivity.E = jSONObjectOptJSONObject8.optJSONArray("packPurchase");
                                                }
                                                jSONArrayOptJSONArray = jSONObjectOptJSONObject5.optJSONArray("infoText");
                                                if (jSONArrayOptJSONArray != null) {
                                                    dashboardActivity.F = new ArrayList();
                                                    while (i4 < jSONArrayOptJSONArray.length()) {
                                                        strOptString2 = jSONArrayOptJSONArray.optString(i4, str12);
                                                        if (strOptString2.isEmpty()) {
                                                        }
                                                    }
                                                }
                                            }
                                            handler.post(new af(this, strOptString, iOptInt3, iOptInt4, iOptInt5));
                                            G g16 = dashboardActivity.an;
                                            jSONObjectOptJSONObject2 = w.y.k(g16, w.y.am("/goldenfarm/get-coupon-balance", g16.f296a, String.valueOf(g16.b))).optJSONObject(str3);
                                            if (jSONObjectOptJSONObject2 != null) {
                                                jSONObjectOptJSONObject3 = jSONObjectOptJSONObject2.optJSONObject(str4);
                                            } else {
                                                jSONObjectOptJSONObject3 = jSONObject;
                                            }
                                            if (jSONObjectOptJSONObject3 != null) {
                                                i2 = 0;
                                                iOptInt = jSONObjectOptJSONObject3.optInt(str5, 0);
                                            } else {
                                                i2 = 0;
                                                iOptInt = 0;
                                            }
                                            if (jSONObjectOptJSONObject3 != null) {
                                                iOptInt2 = jSONObjectOptJSONObject3.optInt("weeklyTickets", i2);
                                            } else {
                                                iOptInt2 = 0;
                                            }
                                            handler.post(new ag(zVar, iOptInt, iOptInt2));
                                            acVar = new ac(zVar, 2);
                                            handler.post(acVar);
                                            return;
                                        }
                                    }
                                } catch (Exception e12) {
                                    e = e12;
                                    handler = handler3;
                                    str = "couponBalance";
                                    str2 = "attribute";
                                    zVar = this;
                                    jSONObject = null;
                                    e.getMessage();
                                    dashboardActivity = dashboardActivity3;
                                    G g17 = dashboardActivity.an;
                                    str3 = str13;
                                    jSONObjectOptJSONObject9 = w.y.k(g17, w.y.am("/point-system/dashboard", g17.f296a, String.valueOf(g17.b))).optJSONObject(str3);
                                    if (jSONObjectOptJSONObject9 != null) {
                                        str4 = str2;
                                        jSONObjectOptJSONObject10 = jSONObjectOptJSONObject9.optJSONObject(str4);
                                    } else {
                                        str4 = str2;
                                        jSONObjectOptJSONObject10 = jSONObject;
                                    }
                                    if (jSONObjectOptJSONObject10 != null) {
                                        String strOptString10 = jSONObjectOptJSONObject10.optString("starStatusLabel", "STAR");
                                        int iOptInt11 = jSONObjectOptJSONObject10.optInt("totalPoint", 0);
                                        jSONObjectOptJSONObject11 = jSONObjectOptJSONObject10.optJSONObject("mostExpiry");
                                        if (jSONObjectOptJSONObject11 != null) {
                                            strOptString3 = jSONObjectOptJSONObject11.optString("date", str12);
                                        } else {
                                            strOptString3 = str12;
                                        }
                                        if (jSONObjectOptJSONObject11 != null) {
                                            iOptInt6 = jSONObjectOptJSONObject11.optInt("point", 0);
                                        } else {
                                            iOptInt6 = 0;
                                        }
                                        if (strOptString3.isEmpty()) {
                                            strOptString4 = jSONObjectOptJSONObject10.optString("validityEndDateText", str12);
                                        } else {
                                            strOptString4 = jSONObjectOptJSONObject10.optString("validityEndDateText", str12);
                                        }
                                        handler.post(new ae(zVar, strOptString10, iOptInt11, strOptString4));
                                        break;
                                    }
                                    G g18 = dashboardActivity.an;
                                    jSONObjectOptJSONObject4 = w.y.k(g18, w.y.am("/tohtohunited/get-coupon-balance", g18.f296a, String.valueOf(g18.b))).optJSONObject(str3);
                                    if (jSONObjectOptJSONObject4 != null) {
                                        jSONObjectOptJSONObject5 = jSONObjectOptJSONObject4.optJSONObject(str4);
                                        break;
                                    } else {
                                        jSONObjectOptJSONObject5 = jSONObject;
                                    }
                                    if (jSONObjectOptJSONObject5 != null) {
                                        str5 = str;
                                        jSONObjectOptJSONObject6 = jSONObjectOptJSONObject5.optJSONObject(str5);
                                    } else {
                                        str5 = str;
                                        jSONObjectOptJSONObject6 = jSONObject;
                                    }
                                    if (jSONObjectOptJSONObject5 != null) {
                                        jSONObjectOptJSONObject7 = jSONObjectOptJSONObject5.optJSONObject("profileInfo");
                                    } else {
                                        jSONObjectOptJSONObject7 = jSONObject;
                                    }
                                    if (jSONObjectOptJSONObject6 != null) {
                                        i3 = 0;
                                        iOptInt3 = jSONObjectOptJSONObject6.optInt("totalCoupon", 0);
                                    } else {
                                        i3 = 0;
                                        iOptInt3 = 0;
                                    }
                                    if (jSONObjectOptJSONObject6 != null) {
                                        iOptInt4 = jSONObjectOptJSONObject6.optInt("normalCoupon", i3);
                                    } else {
                                        iOptInt4 = 0;
                                    }
                                    if (jSONObjectOptJSONObject6 != null) {
                                        jSONObjectOptJSONObject6.optInt("specialCoupon", i3);
                                    }
                                    if (jSONObjectOptJSONObject7 != null) {
                                        strOptString = jSONObjectOptJSONObject7.optString("name", str12);
                                    } else {
                                        strOptString = str12;
                                    }
                                    if (jSONObjectOptJSONObject5 != null) {
                                        iOptInt5 = jSONObjectOptJSONObject5.optInt("isDailyBonusPopup", 0);
                                    } else {
                                        iOptInt5 = 0;
                                    }
                                    if (jSONObjectOptJSONObject5 != null) {
                                        jSONObjectOptJSONObject8 = jSONObjectOptJSONObject5.optJSONObject("luckyChanceItems");
                                        if (jSONObjectOptJSONObject8 != null) {
                                            dashboardActivity.E = jSONObjectOptJSONObject8.optJSONArray("packPurchase");
                                        }
                                        jSONArrayOptJSONArray = jSONObjectOptJSONObject5.optJSONArray("infoText");
                                        if (jSONArrayOptJSONArray != null) {
                                            dashboardActivity.F = new ArrayList();
                                            while (i4 < jSONArrayOptJSONArray.length()) {
                                                strOptString2 = jSONArrayOptJSONArray.optString(i4, str12);
                                                if (strOptString2.isEmpty()) {
                                                }
                                            }
                                        }
                                    }
                                    handler.post(new af(this, strOptString, iOptInt3, iOptInt4, iOptInt5));
                                    G g19 = dashboardActivity.an;
                                    jSONObjectOptJSONObject2 = w.y.k(g19, w.y.am("/goldenfarm/get-coupon-balance", g19.f296a, String.valueOf(g19.b))).optJSONObject(str3);
                                    if (jSONObjectOptJSONObject2 != null) {
                                        jSONObjectOptJSONObject3 = jSONObjectOptJSONObject2.optJSONObject(str4);
                                    } else {
                                        jSONObjectOptJSONObject3 = jSONObject;
                                    }
                                    if (jSONObjectOptJSONObject3 != null) {
                                        i2 = 0;
                                        iOptInt = jSONObjectOptJSONObject3.optInt(str5, 0);
                                    } else {
                                        i2 = 0;
                                        iOptInt = 0;
                                    }
                                    if (jSONObjectOptJSONObject3 != null) {
                                        iOptInt2 = jSONObjectOptJSONObject3.optInt("weeklyTickets", i2);
                                    } else {
                                        iOptInt2 = 0;
                                    }
                                    handler.post(new ag(zVar, iOptInt, iOptInt2));
                                    acVar = new ac(zVar, 2);
                                    handler.post(acVar);
                                    return;
                                }
                            } else {
                                str6 = "--";
                                if (jSONObjectOptJSONObject16 == null) {
                                    handler2 = handler3;
                                    str7 = "couponBalance";
                                    string = "--";
                                } else if (jSONObjectOptJSONObject16.optJSONObject("voice") != null) {
                                    handler2 = handler3;
                                    StringBuilder sb2 = new StringBuilder();
                                    str7 = "couponBalance";
                                    sb2.append(jSONObjectOptJSONObject16.getJSONObject("voice").optString("remaining", "--"));
                                    sb2.append(" ");
                                    sb2.append(jSONObjectOptJSONObject16.getJSONObject("voice").optString("unit", ""));
                                    string = sb2.toString();
                                } else {
                                    handler2 = handler3;
                                    str7 = "couponBalance";
                                    string = "--";
                                }
                                if (jSONObjectOptJSONObject16 == null) {
                                    if (jSONObjectOptJSONObject16.optJSONObject("sms") != null) {
                                        str8 = "attribute";
                                        str9 = jSONObjectOptJSONObject16.getJSONObject("sms").optString("remaining", "--") + " " + jSONObjectOptJSONObject16.getJSONObject("sms").optString("unit", "");
                                    } else {
                                        str8 = "attribute";
                                        str9 = "--";
                                    }
                                    if (jSONObjectOptJSONObject17 != null) {
                                        str10 = jSONObjectOptJSONObject17.optString("value", "--") + " " + jSONObjectOptJSONObject17.optString("currency", "Ks");
                                    } else {
                                        str10 = "--";
                                    }
                                    if (jSONObjectOptJSONObject17 == null) {
                                        z2 = false;
                                    } else {
                                        z2 = false;
                                    }
                                    JSONArray jSONArrayK8 = DashboardActivity.k(dashboardActivity3, jSONObjectOptJSONObject16, "data");
                                    JSONArray jSONArrayK9 = DashboardActivity.k(dashboardActivity3, jSONObjectOptJSONObject16, "voice");
                                    JSONArray jSONArrayK10 = DashboardActivity.k(dashboardActivity3, jSONObjectOptJSONObject16, "sms");
                                    jSONArrayK = DashboardActivity.k(dashboardActivity3, jSONObjectOptJSONObject16, "ett");
                                    jSONArray = new JSONArray();
                                    while (i5 < jSONArrayK.length()) {
                                        jSONObjectOptJSONObject13 = jSONArrayK.optJSONObject(i5);
                                        if (jSONObjectOptJSONObject13 != null) {
                                            jSONArray.put(jSONObjectOptJSONObject13);
                                        }
                                    }
                                    str = str7;
                                    dashboardActivity3 = dashboardActivity3;
                                    str13 = "data";
                                    jSONObject = null;
                                    str2 = str8;
                                    handler = handler2;
                                    handler.post(new ad(this, str6, string, str9, str10, z2, jSONArrayK8, jSONArrayK9, jSONArrayK10, jSONArrayK, jSONArray));
                                    if (jSONObjectOptJSONObject16 == null) {
                                        zVar = this;
                                        str12 = "";
                                    } else if (jSONObjectOptJSONObject16.optJSONObject("ett") != null) {
                                        JSONObject jSONObject4 = jSONObjectOptJSONObject16.getJSONObject("ett");
                                        str11 = jSONObject4.optString("remaining", "--") + " " + jSONObject4.optString("unit", "Ks");
                                        jSONArrayOptJSONArray2 = jSONObject4.optJSONArray("packsList");
                                        if (jSONArrayOptJSONArray2 != null) {
                                            str12 = "";
                                            strOptString5 = str12;
                                            zVar = this;
                                            handler.post(new v(zVar, str11, strOptString5, 1));
                                        } else if (jSONArrayOptJSONArray2.length() > 0) {
                                            str12 = "";
                                            strOptString5 = str12;
                                            zVar = this;
                                            handler.post(new v(zVar, str11, strOptString5, 1));
                                        } else {
                                            str12 = "";
                                            strOptString5 = str12;
                                            zVar = this;
                                            handler.post(new v(zVar, str11, strOptString5, 1));
                                        }
                                    } else {
                                        zVar = this;
                                        str12 = "";
                                    }
                                } else {
                                    str8 = "attribute";
                                    str9 = "--";
                                    if (jSONObjectOptJSONObject17 != null) {
                                        str10 = jSONObjectOptJSONObject17.optString("value", "--") + " " + jSONObjectOptJSONObject17.optString("currency", "Ks");
                                    } else {
                                        str10 = "--";
                                    }
                                    if (jSONObjectOptJSONObject17 == null) {
                                        z2 = false;
                                    } else {
                                        z2 = false;
                                    }
                                    JSONArray jSONArrayK11 = DashboardActivity.k(dashboardActivity3, jSONObjectOptJSONObject16, "data");
                                    JSONArray jSONArrayK12 = DashboardActivity.k(dashboardActivity3, jSONObjectOptJSONObject16, "voice");
                                    JSONArray jSONArrayK13 = DashboardActivity.k(dashboardActivity3, jSONObjectOptJSONObject16, "sms");
                                    jSONArrayK = DashboardActivity.k(dashboardActivity3, jSONObjectOptJSONObject16, "ett");
                                    jSONArray = new JSONArray();
                                    while (i5 < jSONArrayK.length()) {
                                        jSONObjectOptJSONObject13 = jSONArrayK.optJSONObject(i5);
                                        if (jSONObjectOptJSONObject13 != null) {
                                            jSONArray.put(jSONObjectOptJSONObject13);
                                        }
                                    }
                                    str = str7;
                                    dashboardActivity3 = dashboardActivity3;
                                    str13 = "data";
                                    jSONObject = null;
                                    str2 = str8;
                                    handler = handler2;
                                    handler.post(new ad(this, str6, string, str9, str10, z2, jSONArrayK11, jSONArrayK12, jSONArrayK13, jSONArrayK, jSONArray));
                                    if (jSONObjectOptJSONObject16 == null) {
                                        zVar = this;
                                        str12 = "";
                                    } else if (jSONObjectOptJSONObject16.optJSONObject("ett") != null) {
                                        JSONObject jSONObject5 = jSONObjectOptJSONObject16.getJSONObject("ett");
                                        str11 = jSONObject5.optString("remaining", "--") + " " + jSONObject5.optString("unit", "Ks");
                                        jSONArrayOptJSONArray2 = jSONObject5.optJSONArray("packsList");
                                        if (jSONArrayOptJSONArray2 != null) {
                                            str12 = "";
                                            strOptString5 = str12;
                                            zVar = this;
                                            handler.post(new v(zVar, str11, strOptString5, 1));
                                        } else if (jSONArrayOptJSONArray2.length() > 0) {
                                            str12 = "";
                                            strOptString5 = str12;
                                            zVar = this;
                                            handler.post(new v(zVar, str11, strOptString5, 1));
                                        } else {
                                            str12 = "";
                                            strOptString5 = str12;
                                            zVar = this;
                                            handler.post(new v(zVar, str11, strOptString5, 1));
                                        }
                                    } else {
                                        zVar = this;
                                        str12 = "";
                                    }
                                }
                            }
                        } else {
                            handler = handler3;
                            str = "couponBalance";
                            dashboardActivity3 = dashboardActivity3;
                            str13 = "data";
                            jSONObject = null;
                            str2 = "attribute";
                            zVar = this;
                            str12 = "";
                        }
                    } catch (Exception e13) {
                        e = e13;
                        handler = handler3;
                        str = "couponBalance";
                        dashboardActivity3 = dashboardActivity3;
                        str13 = "data";
                        jSONObject = null;
                        str2 = "attribute";
                        zVar = this;
                        str12 = "";
                    }
                    dashboardActivity = dashboardActivity3;
                    try {
                        G g110 = dashboardActivity.an;
                        str3 = str13;
                        jSONObjectOptJSONObject9 = w.y.k(g110, w.y.am("/point-system/dashboard", g110.f296a, String.valueOf(g110.b))).optJSONObject(str3);
                        if (jSONObjectOptJSONObject9 != null) {
                            str4 = str2;
                            jSONObjectOptJSONObject10 = jSONObjectOptJSONObject9.optJSONObject(str4);
                        } else {
                            str4 = str2;
                            jSONObjectOptJSONObject10 = jSONObject;
                        }
                        if (jSONObjectOptJSONObject10 != null) {
                            String strOptString11 = jSONObjectOptJSONObject10.optString("starStatusLabel", "STAR");
                            int iOptInt12 = jSONObjectOptJSONObject10.optInt("totalPoint", 0);
                            jSONObjectOptJSONObject11 = jSONObjectOptJSONObject10.optJSONObject("mostExpiry");
                            if (jSONObjectOptJSONObject11 != null) {
                                strOptString3 = jSONObjectOptJSONObject11.optString("date", str12);
                            } else {
                                strOptString3 = str12;
                            }
                            if (jSONObjectOptJSONObject11 != null) {
                                iOptInt6 = jSONObjectOptJSONObject11.optInt("point", 0);
                            } else {
                                iOptInt6 = 0;
                            }
                            if (strOptString3.isEmpty() || iOptInt6 <= 0) {
                                strOptString4 = jSONObjectOptJSONObject10.optString("validityEndDateText", str12);
                            } else {
                                strOptString4 = strOptString3 + " တွင် ( " + iOptInt6 + " points ) သက်တမ်းကုန်ပါမည်";
                            }
                            handler.post(new ae(zVar, strOptString11, iOptInt12, strOptString4));
                        }
                    } catch (Exception e14) {
                        e = e14;
                        str3 = str13;
                    }
                    try {
                        G g111 = dashboardActivity.an;
                        jSONObjectOptJSONObject4 = w.y.k(g111, w.y.am("/tohtohunited/get-coupon-balance", g111.f296a, String.valueOf(g111.b))).optJSONObject(str3);
                        if (jSONObjectOptJSONObject4 != null) {
                            jSONObjectOptJSONObject5 = jSONObjectOptJSONObject4.optJSONObject(str4);
                        } else {
                            jSONObjectOptJSONObject5 = jSONObject;
                        }
                        if (jSONObjectOptJSONObject5 != null) {
                            str5 = str;
                            jSONObjectOptJSONObject6 = jSONObjectOptJSONObject5.optJSONObject(str5);
                        } else {
                            str5 = str;
                            jSONObjectOptJSONObject6 = jSONObject;
                        }
                        if (jSONObjectOptJSONObject5 != null) {
                            jSONObjectOptJSONObject7 = jSONObjectOptJSONObject5.optJSONObject("profileInfo");
                        } else {
                            jSONObjectOptJSONObject7 = jSONObject;
                        }
                        if (jSONObjectOptJSONObject6 != null) {
                            i3 = 0;
                            iOptInt3 = jSONObjectOptJSONObject6.optInt("totalCoupon", 0);
                        } else {
                            i3 = 0;
                            iOptInt3 = 0;
                        }
                        if (jSONObjectOptJSONObject6 != null) {
                            iOptInt4 = jSONObjectOptJSONObject6.optInt("normalCoupon", i3);
                        } else {
                            iOptInt4 = 0;
                        }
                        if (jSONObjectOptJSONObject6 != null) {
                            jSONObjectOptJSONObject6.optInt("specialCoupon", i3);
                        }
                        if (jSONObjectOptJSONObject7 != null) {
                            strOptString = jSONObjectOptJSONObject7.optString("name", str12);
                        } else {
                            strOptString = str12;
                        }
                        if (jSONObjectOptJSONObject5 != null) {
                            iOptInt5 = jSONObjectOptJSONObject5.optInt("isDailyBonusPopup", 0);
                        } else {
                            iOptInt5 = 0;
                        }
                        if (jSONObjectOptJSONObject5 != null) {
                            jSONObjectOptJSONObject8 = jSONObjectOptJSONObject5.optJSONObject("luckyChanceItems");
                            if (jSONObjectOptJSONObject8 != null) {
                                dashboardActivity.E = jSONObjectOptJSONObject8.optJSONArray("packPurchase");
                            }
                            jSONArrayOptJSONArray = jSONObjectOptJSONObject5.optJSONArray("infoText");
                            if (jSONArrayOptJSONArray != null) {
                                dashboardActivity.F = new ArrayList();
                                while (i4 < jSONArrayOptJSONArray.length()) {
                                    strOptString2 = jSONArrayOptJSONArray.optString(i4, str12);
                                    if (strOptString2.isEmpty() && strOptString2.matches(".*[\\u1000-\\u109F\\uA9E0-\\uA9FF\\uAA60-\\uAA7F]+.*")) {
                                        dashboardActivity.F.add(strOptString2);
                                    }
                                }
                            }
                        }
                        handler.post(new af(this, strOptString, iOptInt3, iOptInt4, iOptInt5));
                    } catch (Exception e15) {
                        e = e15;
                        str5 = str;
                    }
                    try {
                        G g112 = dashboardActivity.an;
                        jSONObjectOptJSONObject2 = w.y.k(g112, w.y.am("/goldenfarm/get-coupon-balance", g112.f296a, String.valueOf(g112.b))).optJSONObject(str3);
                        if (jSONObjectOptJSONObject2 != null) {
                            jSONObjectOptJSONObject3 = jSONObjectOptJSONObject2.optJSONObject(str4);
                        } else {
                            jSONObjectOptJSONObject3 = jSONObject;
                        }
                        if (jSONObjectOptJSONObject3 != null) {
                            i2 = 0;
                            iOptInt = jSONObjectOptJSONObject3.optInt(str5, 0);
                        } else {
                            i2 = 0;
                            iOptInt = 0;
                        }
                        if (jSONObjectOptJSONObject3 != null) {
                            iOptInt2 = jSONObjectOptJSONObject3.optInt("weeklyTickets", i2);
                        } else {
                            iOptInt2 = 0;
                        }
                        handler.post(new ag(zVar, iOptInt, iOptInt2));
                        acVar = new ac(zVar, 2);
                        handler.post(acVar);
                        return;
                    } catch (Throwable th) {
                        handler.post(new ac(zVar, 2));
                        throw th;
                    }
                } catch (Exception e16) {
                    e16.getMessage();
                    int i10 = DashboardActivity.L;
                    dashboardActivity3.getClass();
                    dashboardActivity3.ao.post(new ac(this, 1));
                    return;
                }
                break;
            case 3:
                try {
                    JSONObject jSONObjectG = w.y.g(dashboardActivity2.an);
                    JSONObject jSONObjectOptJSONObject18 = jSONObjectG.optJSONObject("data");
                    jSONObjectOptJSONObject = jSONObjectOptJSONObject18 != null ? jSONObjectOptJSONObject18.optJSONObject("attribute") : null;
                    try {
                        dashboardActivity2.ao.post(new ah(this, jSONObjectOptJSONObject != null ? jSONObjectOptJSONObject.optInt("isWin", 0) : jSONObjectG.optInt("isWin", 0), jSONObjectOptJSONObject != null ? jSONObjectOptJSONObject.optString("title", "ဂုဏ်ယူပါသည်!") : "ဂုဏ်ယူပါသည်!", jSONObjectOptJSONObject != null ? jSONObjectOptJSONObject.optString("message", "OK") : jSONObjectG.optString("message", "OK"), jSONObjectOptJSONObject != null ? jSONObjectOptJSONObject.optString("prizeName", "") : ""));
                        return;
                    } catch (Exception unused6) {
                        dashboardActivity2.ao.post(new o(3, this));
                        return;
                    }
                } catch (Exception unused7) {
                }
                break;
            case 4:
                if (dashboardActivity2.f188u <= 0) {
                    dashboardActivity2.f190w = false;
                    Button button = dashboardActivity2.f181n;
                    if (button != null) {
                        button.setText("တို့တို့ ကစားမယ်");
                        dashboardActivity2.r();
                        return;
                    }
                    return;
                }
                Button button2 = dashboardActivity2.f181n;
                if (button2 != null) {
                    button2.setText("တို့တို့ ကစားမယ် (" + dashboardActivity2.f188u + "s)");
                    dashboardActivity2.f181n.setEnabled(false);
                    dashboardActivity2.f181n.setClickable(false);
                    dashboardActivity2.f181n.setAlpha(0.5f);
                }
                dashboardActivity2.f188u--;
                dashboardActivity2.ao.postDelayed(this, 1000L);
                return;
            case 5:
                try {
                    JSONObject jSONObjectS = w.y.s(dashboardActivity2.an);
                    JSONObject jSONObjectOptJSONObject19 = jSONObjectS.optJSONObject("data");
                    jSONObjectOptJSONObject = jSONObjectOptJSONObject19 != null ? jSONObjectOptJSONObject19.optJSONObject("attribute") : null;
                    dashboardActivity2.ao.post(new ae(this, jSONObjectOptJSONObject != null ? jSONObjectOptJSONObject.optInt("isWin", 0) : jSONObjectS.optInt("isWin", 0), jSONObjectOptJSONObject != null ? jSONObjectOptJSONObject.optString("title", "ဂုဏ်ယူပါသည်!") : "ဂုဏ်ယူပါသည်!", jSONObjectOptJSONObject != null ? jSONObjectOptJSONObject.optString("message", "GF Draw OK") : jSONObjectS.optString("message", "GF Draw OK")));
                    return;
                } catch (Exception unused8) {
                    dashboardActivity2.ao.post(new o(4, this));
                    return;
                }
            case 6:
                if (dashboardActivity2.f189v <= 0) {
                    dashboardActivity2.f191x = false;
                    Button button3 = dashboardActivity2.f185r;
                    if (button3 != null) {
                        button3.setText("ရွှေလယ်တော ကစားမယ်");
                        dashboardActivity2.q();
                        return;
                    }
                    return;
                }
                Button button4 = dashboardActivity2.f185r;
                if (button4 != null) {
                    button4.setText("ရွှေလယ်တော ကစားမယ် (" + dashboardActivity2.f189v + "s)");
                    dashboardActivity2.f185r.setEnabled(false);
                    dashboardActivity2.f185r.setClickable(false);
                    dashboardActivity2.f185r.setAlpha(0.5f);
                }
                dashboardActivity2.f189v--;
                dashboardActivity2.ao.postDelayed(this, 1000L);
                return;
            case 7:
                try {
                    G g20 = dashboardActivity2.an;
                    JSONObject jSONObjectK = w.y.k(g20, w.y.am("/goldenfarm/purchase-life", g20.f296a, String.valueOf(g20.b)));
                    JSONObject jSONObjectOptJSONObject20 = jSONObjectK.optJSONObject("data");
                    JSONObject jSONObjectOptJSONObject21 = jSONObjectOptJSONObject20 != null ? jSONObjectOptJSONObject20.optJSONObject("attribute") : null;
                    if (jSONObjectOptJSONObject21 != null) {
                        dashboardActivity2.ao.post(new am(this, jSONObjectOptJSONObject21.optString("message", jSONObjectK.optString("message", "Purchase complete")), 0));
                        return;
                    }
                    String strOptString12 = jSONObjectK.optString("message", null);
                    if (strOptString12 == null || strOptString12.isEmpty()) {
                        strOptString12 = "Purchase failed";
                    }
                    throw new Exception(strOptString12);
                } catch (Exception e17) {
                    dashboardActivity2.ao.post(new am(this, e17.getMessage() != null ? e17.getMessage() : "ဝယ်ယူမှု မအောင်မြင်ပါ", 1));
                    return;
                }
            default:
                int i11 = DashboardActivity.L;
                ConnectivityManager connectivityManager = (ConnectivityManager) dashboardActivity2.getSystemService("connectivity");
                if (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || !activeNetworkInfo.isConnected()) {
                    dashboardActivity2.D.postDelayed(this, 1500L);
                    return;
                }
                Snackbar snackbar = dashboardActivity2.aq;
                if (snackbar != null) {
                    snackbar.dismiss();
                }
                dashboardActivity2.recreate();
                return;
        }
    }
}
