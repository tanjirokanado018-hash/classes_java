package f;

import androidx.core.app.NotificationCompat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class r {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public String f385a = "";
    public String b = "";

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public String f386c = "";
    public String d = "";

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public boolean f387e = false;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public String f388f = null;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public boolean f389g = false;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public boolean f390h = false;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public int f391i = 6;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public long f392j = 0;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public long f393k = 0;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public final ArrayList f394l = new ArrayList();

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public String f395m = "";

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public int f396n = 0;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public int f397o = 0;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public int f398p = 0;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public int f399q = 0;

    /* JADX INFO: renamed from: r, reason: collision with root package name */
    public long f400r = 0;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public int f401s = 0;

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public int f402t = 0;

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public String f403u = "idle";

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public String f404v = null;

    /* JADX INFO: renamed from: w, reason: collision with root package name */
    public String f405w = "";

    /* JADX INFO: renamed from: x, reason: collision with root package name */
    public JSONArray f406x = null;

    /* JADX INFO: renamed from: y, reason: collision with root package name */
    public JSONArray f407y = null;

    /* JADX INFO: renamed from: z, reason: collision with root package name */
    public String f408z = null;
    public final ArrayList aa = new ArrayList();

    public static r b(JSONObject jSONObject) {
        r rVar = new r();
        try {
            rVar.f385a = jSONObject.optString("msisdn", "");
            rVar.b = jSONObject.optString("sessionCookie", "");
            rVar.f386c = jSONObject.optString("userId", "");
            rVar.d = jSONObject.optString("gameReferer", "");
            rVar.f387e = jSONObject.optBoolean("running", false);
            String strOptString = null;
            rVar.f388f = jSONObject.isNull("reducerType") ? null : jSONObject.optString("reducerType", null);
            rVar.f389g = jSONObject.optBoolean("allMilestonesClaimed", false);
            rVar.f390h = jSONObject.optBoolean("giftBoxClaimed", false);
            rVar.f391i = jSONObject.optInt("giftBoxCoins", 6);
            rVar.f392j = jSONObject.optLong("lastHeartbeat", 0L);
            rVar.f393k = jSONObject.optLong("lastMilestoneClaimTime", 0L);
            rVar.f395m = jSONObject.optString("lastClaimedDateStr", "");
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("claimedMilestones");
            if (jSONArrayOptJSONArray != null) {
                for (int i2 = 0; i2 < jSONArrayOptJSONArray.length(); i2++) {
                    rVar.f394l.add(jSONArrayOptJSONArray.optString(i2));
                }
            }
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("stats");
            if (jSONObjectOptJSONObject != null) {
                rVar.f396n = jSONObjectOptJSONObject.optInt("totalRuns", 0);
                rVar.f397o = jSONObjectOptJSONObject.optInt("totalCoinsEarned", 0);
                rVar.f398p = jSONObjectOptJSONObject.optInt("totalMilestonesClaimed", 0);
                rVar.f399q = jSONObjectOptJSONObject.optInt("totalRunsRedeemed", 0);
                rVar.f400r = jSONObjectOptJSONObject.optLong("startTime", 0L);
                rVar.f401s = jSONObjectOptJSONObject.optInt("currentCoins", 0);
                rVar.f402t = jSONObjectOptJSONObject.optInt("currentRuns", 0);
                rVar.f403u = jSONObjectOptJSONObject.optString(NotificationCompat.CATEGORY_STATUS, "idle");
                if (!jSONObjectOptJSONObject.isNull("lastError")) {
                    strOptString = jSONObjectOptJSONObject.optString("lastError", null);
                }
                rVar.f404v = strOptString;
            }
            rVar.f405w = jSONObject.optString("userName", "");
            JSONArray jSONArrayOptJSONArray2 = jSONObject.optJSONArray("milestones");
            if (jSONArrayOptJSONArray2 != null) {
                rVar.f406x = jSONArrayOptJSONArray2;
            }
            JSONArray jSONArrayOptJSONArray3 = jSONObject.optJSONArray("coinsRedemption");
            if (jSONArrayOptJSONArray3 != null) {
                rVar.f407y = jSONArrayOptJSONArray3;
            }
        } catch (Exception unused) {
        }
        return rVar;
    }

    public final void a(String str, String str2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        ArrayList arrayList = this.aa;
        arrayList.add(0, new q(simpleDateFormat.format(new Date()), str, str2));
        if (arrayList.size() > 150) {
            arrayList.remove(arrayList.size() - 1);
        }
    }

    public final JSONObject c() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("msisdn", this.f385a);
            jSONObject.put("sessionCookie", this.b);
            jSONObject.put("userId", this.f386c);
            jSONObject.put("gameReferer", this.d);
            jSONObject.put("running", this.f387e);
            Object obj = this.f388f;
            if (obj == null) {
                obj = JSONObject.NULL;
            }
            jSONObject.put("reducerType", obj);
            jSONObject.put("allMilestonesClaimed", this.f389g);
            jSONObject.put("giftBoxClaimed", this.f390h);
            jSONObject.put("giftBoxCoins", this.f391i);
            jSONObject.put("lastHeartbeat", this.f392j);
            jSONObject.put("lastMilestoneClaimTime", this.f393k);
            String str = this.f395m;
            if (str == null) {
                str = "";
            }
            jSONObject.put("lastClaimedDateStr", str);
            JSONArray jSONArray = new JSONArray();
            Iterator it = this.f394l.iterator();
            while (it.hasNext()) {
                jSONArray.put((String) it.next());
            }
            jSONObject.put("claimedMilestones", jSONArray);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("totalRuns", this.f396n);
            jSONObject2.put("totalCoinsEarned", this.f397o);
            jSONObject2.put("totalMilestonesClaimed", this.f398p);
            jSONObject2.put("totalRunsRedeemed", this.f399q);
            jSONObject2.put("startTime", this.f400r);
            jSONObject2.put("currentCoins", this.f401s);
            jSONObject2.put("currentRuns", this.f402t);
            jSONObject2.put(NotificationCompat.CATEGORY_STATUS, this.f403u);
            Object obj2 = this.f404v;
            if (obj2 == null) {
                obj2 = JSONObject.NULL;
            }
            jSONObject2.put("lastError", obj2);
            jSONObject.put("stats", jSONObject2);
            jSONObject.put("userName", this.f405w);
            JSONArray jSONArray2 = this.f406x;
            if (jSONArray2 != null) {
                jSONObject.put("milestones", jSONArray2);
            }
            JSONArray jSONArray3 = this.f407y;
            if (jSONArray3 != null) {
                jSONObject.put("coinsRedemption", jSONArray3);
            }
            return jSONObject;
        } catch (Exception unused) {
            return new JSONObject();
        }
    }
}
