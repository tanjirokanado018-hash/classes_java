package w;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.core.app.NotificationCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.zenoatom.app.DashboardActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class y {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final kotlinx.coroutines.internal.q f553a = new kotlinx.coroutines.internal.q("CLOSED_EMPTY");
    public static final kotlinx.coroutines.internal.q b = new kotlinx.coroutines.internal.q("COMPLETING_ALREADY");

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static final kotlinx.coroutines.internal.q f554c = new kotlinx.coroutines.internal.q("COMPLETING_WAITING_CHILDREN");
    public static final kotlinx.coroutines.internal.q d = new kotlinx.coroutines.internal.q("COMPLETING_RETRY");

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public static final kotlinx.coroutines.internal.q f555e = new kotlinx.coroutines.internal.q("TOO_LATE_TO_CANCEL");

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public static final kotlinx.coroutines.internal.q f556f = new kotlinx.coroutines.internal.q("SEALED");

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public static final ah f557g = new ah(false);

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public static final ah f558h = new ah(true);

    public static void a(Throwable th, Throwable exception) {
        kotlin.jvm.internal.k.g(th, "<this>");
        kotlin.jvm.internal.k.g(exception, "exception");
        if (th != exception) {
            l.c.f495a.a(th, exception);
        }
    }

    public static JSONObject aa(HttpURLConnection httpURLConnection, String str) throws Exception {
        int responseCode;
        JSONArray jSONArrayOptJSONArray;
        try {
            responseCode = httpURLConnection.getResponseCode();
        } catch (Exception unused) {
            responseCode = 0;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (responseCode < 200 || responseCode >= 300) {
                String strI = i(jSONObject);
                if (strI == null) {
                    strI = androidx.appcompat.graphics.drawable.a.c("HTTP ", responseCode);
                }
                throw new Exception(strI);
            }
            JSONArray jSONArrayOptJSONArray2 = jSONObject.optJSONArray("errors");
            if (jSONArrayOptJSONArray2 != null && jSONArrayOptJSONArray2.length() > 0) {
                String strI2 = i(jSONObject);
                throw new Exception(strI2 != null ? strI2 : "Request failed");
            }
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
            if (jSONObjectOptJSONObject == null || (jSONArrayOptJSONArray = jSONObjectOptJSONObject.optJSONArray("errors")) == null || jSONArrayOptJSONArray.length() <= 0) {
                return jSONObject;
            }
            String strI3 = i(jSONObject);
            throw new Exception(strI3 != null ? strI3 : "Request failed");
        } catch (Exception e2) {
            if (responseCode >= 200 && responseCode < 300) {
                throw e2;
            }
            if (str == null || str.isEmpty()) {
                str = androidx.appcompat.graphics.drawable.a.c("HTTP ", responseCode);
            }
            throw new Exception(str);
        }
    }

    public static void ab(Context context, ArrayList arrayList) {
        try {
            JSONArray jSONArray = new JSONArray();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                f.G g2 = (f.G) it.next();
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("msisdn", g2.f296a);
                jSONObject.put("userId", g2.b);
                jSONObject.put("token", g2.f297c);
                jSONObject.put("refreshToken", g2.d);
                jSONObject.put("expiresAt", g2.f298e);
                jSONArray.put(jSONObject);
            }
            context.getSharedPreferences("zenoatom_prefs", 0).edit().putString("accounts_json", jSONArray.toString()).apply();
        } catch (Exception unused) {
        }
    }

    public static i.j ac(i.j jVar, i.j context) {
        kotlin.jvm.internal.k.g(context, "context");
        return context == i.k.b ? jVar : (i.j) context.fold(jVar, i.c.d);
    }

    public static JSONObject ad(String str, String str2, f.G g2, String str3) throws IOException {
        HttpURLConnection httpURLConnectionZ = z(str, "POST", g2, str3);
        httpURLConnectionZ.setDoOutput(true);
        byte[] bytes = str2.getBytes("UTF-8");
        httpURLConnectionZ.setFixedLengthStreamingMode(bytes.length);
        OutputStream outputStream = httpURLConnectionZ.getOutputStream();
        outputStream.write(bytes);
        outputStream.close();
        return aa(httpURLConnectionZ, ae(httpURLConnectionZ));
    }

    public static String ae(HttpURLConnection httpURLConnection) throws Exception {
        int responseCode;
        InputStream errorStream;
        try {
            responseCode = httpURLConnection.getResponseCode();
        } catch (Exception unused) {
            responseCode = 0;
        }
        if (responseCode == 304) {
            throw new Exception("Server returned cached response (304). Please try again.");
        }
        try {
            try {
                errorStream = httpURLConnection.getInputStream();
            } catch (Exception unused2) {
                errorStream = httpURLConnection.getErrorStream();
            }
        } catch (Exception unused3) {
            errorStream = null;
        }
        if (errorStream == null) {
            throw new Exception("Empty response from server (HTTP " + responseCode + ")");
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(errorStream, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                bufferedReader.close();
                return sb.toString();
            }
            sb.append(line);
        }
    }

    public static JSONObject af(f.G g2, String str, String str2, String str3, String str4, String str5) {
        String strAm = am("/point-system/redeem", g2.f296a, String.valueOf(g2.b));
        StringBuilder sb = new StringBuilder("{\"category\":\"");
        sb.append(str);
        sb.append("\",\"keyword\":\"");
        sb.append(str2);
        sb.append("\",\"partner\":\"");
        sb.append(str3);
        sb.append("\",\"rewardType\":\"");
        sb.append(str4);
        sb.append("\",\"title\":\"");
        return ad(strAm, androidx.appcompat.graphics.drawable.a.g(sb, str5, "\"}"), g2, null);
    }

    public static void ag(f.r rVar) {
        rVar.f392j = System.currentTimeMillis() / 1000;
        if (!r(rVar)) {
            rVar.a("warn", "Could not fetch user data");
            return;
        }
        JSONArray jSONArray = rVar.f406x;
        if (jSONArray == null) {
            jSONArray = new JSONArray();
        }
        if (rVar.f389g) {
            rVar.f387e = false;
            rVar.f403u = "stopped";
            rVar.a("success", "Game End");
            return;
        }
        if (rVar.f393k == 0) {
            rVar.f393k = System.currentTimeMillis() / 1000;
        }
        long jCurrentTimeMillis = (System.currentTimeMillis() / 1000) - rVar.f393k;
        ArrayList arrayList = rVar.f394l;
        long size = (((long) arrayList.size()) * 60) + 60;
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            try {
                JSONObject jSONObject = jSONArray.getJSONObject(i2);
                String strOptString = jSONObject.optString("milestone_id", "");
                String strOptString2 = jSONObject.optString("milestone_threshold_value", "0");
                int iOptInt = jSONObject.optInt("milestone_coins", 0);
                if (!arrayList.contains(strOptString)) {
                    if (jCurrentTimeMillis >= size) {
                        String strD = d(rVar, strOptString, strOptString2);
                        if (strD == null) {
                            rVar.a("warn", "claimMysteryBox failed");
                            return;
                        }
                        if (!"Already claimed".equals(strD)) {
                            rVar.f398p++;
                            rVar.f397o += iOptInt;
                            rVar.a("success", strD);
                            rVar.f408z = strD;
                        }
                        rVar.f393k = System.currentTimeMillis() / 1000;
                        c(rVar, jSONArray);
                        return;
                    }
                    return;
                }
            } catch (Exception unused) {
                return;
            }
        }
    }

    public static void ah(Context context, f.G g2) {
        ArrayList arrayListW = w(context);
        for (int i2 = 0; i2 < arrayListW.size(); i2++) {
            if (((f.G) arrayListW.get(i2)).f296a.equals(g2.f296a)) {
                arrayListW.set(i2, g2);
                ab(context, arrayListW);
                ai(context, g2.f296a);
            }
        }
        arrayListW.add(g2);
        ab(context, arrayListW);
        ai(context, g2.f296a);
    }

    public static void ai(Context context, String str) {
        context.getSharedPreferences("zenoatom_prefs", 0).edit().putString("active_msisdn", str).apply();
    }

    public static final void aj(Object obj) throws Throwable {
        if (obj instanceof g.e) {
            throw ((g.e) obj).b;
        }
    }

    public static final String ak(i.e eVar) {
        Object objF;
        if (eVar instanceof kotlinx.coroutines.internal.d) {
            return eVar.toString();
        }
        try {
            objF = eVar + '@' + m(eVar);
        } catch (Throwable th) {
            objF = f(th);
        }
        if (g.f.a(objF) != null) {
            objF = eVar.getClass().getName() + '@' + m(eVar);
        }
        return (String) objF;
    }

    public static final Map al(Map map) {
        Map.Entry entry = (Map.Entry) map.entrySet().iterator().next();
        Map mapSingletonMap = Collections.singletonMap(entry.getKey(), entry.getValue());
        kotlin.jvm.internal.k.f(mapSingletonMap, "with(entries.iterator().…ingletonMap(key, value) }");
        return mapSingletonMap;
    }

    public static String am(String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder("https://store.atom.com.mm/mytmapi/v1/my");
        sb.append(str);
        sb.append("?msisdn=");
        sb.append(str2);
        sb.append("&userid=");
        return androidx.appcompat.graphics.drawable.a.g(sb, str3, "&v=4.14.1");
    }

    public static JSONObject an(String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder("{\"msisdn\":\"");
        sb.append(str);
        sb.append("\",\"code\":\"");
        sb.append(str2);
        sb.append("\",\"otp\":\"");
        return ad("https://store.atom.com.mm/mytmapi/v1/my/local-auth/verify-otp?msisdn=&userid=-1&v=4.14.1", androidx.appcompat.graphics.drawable.a.g(sb, str3, "\"}"), null, null);
    }

    public static JSONObject b(f.r rVar, String str, JSONObject jSONObject) throws JSONException, IOException {
        InputStream errorStream;
        String string;
        JSONArray jSONArrayNames;
        JSONObject jSONObject2 = new JSONObject();
        String str2 = rVar.f386c;
        if (str2 != null && !str2.isEmpty()) {
            jSONObject2.put("user_id", rVar.f386c);
        }
        if (jSONObject != null && (jSONArrayNames = jSONObject.names()) != null) {
            for (int i2 = 0; i2 < jSONArrayNames.length(); i2++) {
                String string2 = jSONArrayNames.getString(i2);
                jSONObject2.put(string2, jSONObject.get(string2));
            }
        }
        byte[] bytes = jSONObject2.toString().getBytes("UTF-8");
        String str3 = rVar.d;
        String str4 = (str3 == null || str3.isEmpty()) ? "https://cityrun.pro/playgame" : rVar.d;
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("https://cityrun.pro".concat(str)).openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setConnectTimeout(30000);
        httpURLConnection.setReadTimeout(30000);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setInstanceFollowRedirects(false);
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 12; Redmi K30 5G Build/SKQ1.211006.001; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/149.0.7827.91 Mobile Safari/537.36");
        httpURLConnection.setRequestProperty("Accept", "*/*");
        httpURLConnection.setRequestProperty("Accept-Language", "my,en-US;q=0.9,en;q=0.8");
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setRequestProperty("AUTH-TOKEN", "YXRvbUdhbWVzQVBJX1NaMDAwMTpBIzlLIVF4UjckUDlAMg==");
        httpURLConnection.setRequestProperty("Origin", "https://cityrun.pro");
        httpURLConnection.setRequestProperty("Referer", str4);
        httpURLConnection.setRequestProperty("X-Requested-With", "mm.com.atom.store");
        httpURLConnection.setRequestProperty("sec-ch-ua", "\"Android WebView\";v=\"149\", \"Chromium\";v=\"149\", \"Not)A;Brand\";v=\"24\"");
        httpURLConnection.setRequestProperty("sec-ch-ua-mobile", "?1");
        httpURLConnection.setRequestProperty("sec-ch-ua-platform", "\"Android\"");
        httpURLConnection.setRequestProperty("Sec-Fetch-Site", "same-origin");
        httpURLConnection.setRequestProperty("Sec-Fetch-Mode", "cors");
        httpURLConnection.setRequestProperty("Sec-Fetch-Dest", "empty");
        String str5 = rVar.b;
        if (str5 != null && !str5.isEmpty()) {
            httpURLConnection.setRequestProperty("Cookie", rVar.b);
        }
        OutputStream outputStream = httpURLConnection.getOutputStream();
        try {
            outputStream.write(bytes);
            outputStream.close();
            for (Map.Entry<String, List<String>> entry : httpURLConnection.getHeaderFields().entrySet()) {
                String key = entry.getKey();
                if (key != null && key.equalsIgnoreCase("Set-Cookie")) {
                    for (String strSubstring : entry.getValue()) {
                        if (strSubstring.startsWith("ci_session=")) {
                            int iIndexOf = strSubstring.indexOf(59);
                            if (iIndexOf > 0) {
                                strSubstring = strSubstring.substring(0, iIndexOf);
                            }
                            rVar.b = strSubstring;
                        }
                    }
                }
            }
            httpURLConnection.getResponseCode();
            try {
                errorStream = httpURLConnection.getInputStream();
            } catch (Exception unused) {
                errorStream = httpURLConnection.getErrorStream();
            }
            if (errorStream != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(errorStream, "UTF-8"));
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    sb.append(line);
                }
                bufferedReader.close();
                string = sb.toString();
            } else {
                string = "";
            }
            httpURLConnection.disconnect();
            try {
                return new JSONObject(string);
            } catch (Exception unused2) {
                return new JSONObject();
            }
        } catch (Throwable th) {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static void c(f.r rVar, JSONArray jSONArray) {
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            try {
                if (!rVar.f394l.contains(jSONArray.getJSONObject(i2).optString("milestone_id", ""))) {
                    return;
                }
            } catch (Exception unused) {
                return;
            }
        }
        if (jSONArray.length() > 0) {
            rVar.f389g = true;
            rVar.f387e = false;
            rVar.f403u = "stopped";
            rVar.a("success", "Game End");
        }
    }

    public static String d(f.r rVar, String str, String str2) {
        int i2;
        try {
            i2 = Integer.parseInt(str2.trim());
        } catch (Exception unused) {
            i2 = 0;
        }
        try {
            rVar.a("info", "Claiming Milestone: score=" + i2);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("score", i2);
            JSONObject jSONObjectB = b(rVar, "/claimMysteryBox", jSONObject);
            String strOptString = jSONObjectB.optString(NotificationCompat.CATEGORY_STATUS, "");
            String strOptString2 = jSONObjectB.optString("description", "");
            boolean zEquals = "success".equals(strOptString);
            ArrayList arrayList = rVar.f394l;
            if (!zEquals) {
                if (strOptString2.toLowerCase().contains("claimed")) {
                    arrayList.add(str);
                    return "Already claimed";
                }
                return null;
            }
            arrayList.add(str);
            JSONArray jSONArrayOptJSONArray = jSONObjectB.optJSONArray("milestone_reward_details");
            if (jSONArrayOptJSONArray != null && jSONArrayOptJSONArray.length() > 0) {
                StringBuilder sb = new StringBuilder();
                for (int i3 = 0; i3 < jSONArrayOptJSONArray.length(); i3++) {
                    JSONObject jSONObject2 = jSONArrayOptJSONArray.getJSONObject(i3);
                    String strOptString3 = jSONObject2.optString("milestone_reward_dp_value", "");
                    String strOptString4 = jSONObject2.optString("milestone_coins", "");
                    String strOptString5 = jSONObject2.optString("milestone_runs", "");
                    if (!strOptString3.isEmpty()) {
                        sb.append("Data - ");
                        sb.append(strOptString3);
                        sb.append("\n");
                    }
                    if (!strOptString4.isEmpty()) {
                        sb.append("Diamond - ");
                        sb.append(strOptString4);
                        sb.append(" coins\n");
                    }
                    if (!strOptString5.isEmpty()) {
                        sb.append("Run - ");
                        sb.append(strOptString5);
                        sb.append(" runs\n");
                    }
                }
                if (sb.length() > 0) {
                    sb.setLength(sb.length() - 1);
                    return sb.toString();
                }
            }
            return strOptString2.isEmpty() ? "Reward sent" : strOptString2;
        } catch (Exception unused2) {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static i.e e(Object obj, i.e completion, p.p pVar) {
        kotlin.jvm.internal.k.g(pVar, "<this>");
        kotlin.jvm.internal.k.g(completion, "completion");
        if (pVar instanceof k.a) {
            return ((k.a) pVar).create(obj, completion);
        }
        i.j context = completion.getContext();
        return context == i.k.b ? new j.b(obj, completion, pVar) : new j.c(completion, context, pVar, obj);
    }

    public static final g.e f(Throwable exception) {
        kotlin.jvm.internal.k.g(exception, "exception");
        return new g.e(exception);
    }

    public static JSONObject g(f.G g2) {
        return ad(am("/tohtohunited/draw", g2.f296a, String.valueOf(g2.b)), "{\"isCompleted\":1,\"currentPlayLevel\":3,\"chosenPrize\":\"Instant\"}", g2, t("b^[VCHDL786mkTp]*" + String.valueOf(g2.b), "{\"isCompleted\":1,\"currentPlayLevel\":3,\"chosenPrize\":\"Instant\"}"));
    }

    public static f.G h(DashboardActivity dashboardActivity, f.G g2) throws Exception {
        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
        long j2 = g2.f298e;
        if (j2 > 0 && j2 - jCurrentTimeMillis > 300) {
            return g2;
        }
        String str = g2.d;
        if (str == null || str.isEmpty()) {
            throw new Exception("No refresh token stored");
        }
        JSONObject jSONObjectOptJSONObject = ad("https://store.atom.com.mm/mytmapi/v3/my/oauth/refresh-token?msisdn=" + g2.f296a + "&userid=" + String.valueOf(g2.b) + "&v=4.14.1", androidx.appcompat.graphics.drawable.a.f("{\"refresh_token\":\"", str, "\"}"), null, null).optJSONObject("data");
        JSONObject jSONObjectOptJSONObject2 = jSONObjectOptJSONObject != null ? jSONObjectOptJSONObject.optJSONObject("attribute") : null;
        if (jSONObjectOptJSONObject2 == null) {
            throw new Exception("Refresh token response missing attribute");
        }
        String strOptString = jSONObjectOptJSONObject2.optString("token", "");
        String strOptString2 = jSONObjectOptJSONObject2.optString("refresh_token", str);
        long jOptLong = jSONObjectOptJSONObject2.optLong("access_token_expire_at", (System.currentTimeMillis() / 1000) + 86400);
        if (strOptString.isEmpty()) {
            throw new Exception("Refresh returned empty token");
        }
        f.G g3 = new f.G(g2.f296a, g2.b, strOptString, strOptString2, jOptLong);
        ah(dashboardActivity, g3);
        return g3;
    }

    public static String i(JSONObject jSONObject) {
        JSONArray jSONArrayOptJSONArray;
        JSONObject jSONObjectOptJSONObject;
        String strOptString;
        JSONObject jSONObjectOptJSONObject2;
        JSONArray jSONArrayOptJSONArray2 = jSONObject.optJSONArray("errors");
        if (jSONArrayOptJSONArray2 != null && jSONArrayOptJSONArray2.length() > 0 && (jSONObjectOptJSONObject2 = jSONArrayOptJSONArray2.optJSONObject(0)) != null) {
            String strOptString2 = jSONObjectOptJSONObject2.optString("message", null);
            if (strOptString2 != null && !strOptString2.isEmpty()) {
                return strOptString2;
            }
            String strOptString3 = jSONObjectOptJSONObject2.optString("code", null);
            if (strOptString3 != null && !strOptString3.isEmpty()) {
                return strOptString3;
            }
        }
        JSONObject jSONObjectOptJSONObject3 = jSONObject.optJSONObject("data");
        if (jSONObjectOptJSONObject3 != null && (jSONArrayOptJSONArray = jSONObjectOptJSONObject3.optJSONArray("errors")) != null && jSONArrayOptJSONArray.length() > 0 && (jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(0)) != null && (strOptString = jSONObjectOptJSONObject.optString("message", null)) != null && !strOptString.isEmpty()) {
            return strOptString;
        }
        String strOptString4 = jSONObject.optString("errorMessage", null);
        if (strOptString4 != null && !strOptString4.isEmpty()) {
            return strOptString4;
        }
        String strOptString5 = jSONObject.optString("message", null);
        if (strOptString5 != null && !strOptString5.isEmpty()) {
            return strOptString5;
        }
        String strOptString6 = jSONObject.optString("error", null);
        if (strOptString6 == null || strOptString6.isEmpty()) {
            return null;
        }
        return strOptString6;
    }

    public static i.h j(i.h hVar, i.i key) {
        kotlin.jvm.internal.k.g(key, "key");
        if (kotlin.jvm.internal.k.a(hVar.getKey(), key)) {
            return hVar;
        }
        return null;
    }

    public static JSONObject k(f.G g2, String str) throws ProtocolException {
        HttpURLConnection httpURLConnectionZ = z(str, "GET", g2, null);
        return aa(httpURLConnectionZ, ae(httpURLConnectionZ));
    }

    public static JSONObject l(f.G g2) {
        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
        StringBuilder sb = new StringBuilder();
        sb.append("https://store.atom.com.mm/mytmapi/v1/goldenfarm/history/instant?msisdn=" + g2.f296a + "&userid=" + String.valueOf(g2.b) + "&v=4.14.1");
        sb.append("&firstID=");
        sb.append(604800 + jCurrentTimeMillis);
        sb.append("&lastID=");
        sb.append(jCurrentTimeMillis + 691200);
        return k(g2, sb.toString());
    }

    public static final String m(Object obj) {
        return Integer.toHexString(System.identityHashCode(obj));
    }

    public static JSONObject n(f.G g2) {
        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
        return k(g2, am("/tohtohunited/history/win", g2.f296a, String.valueOf(g2.b)) + "&firstID=" + (604800 + jCurrentTimeMillis) + "&lastID=" + (jCurrentTimeMillis + 691200));
    }

    public static final Class o(t.c cVar) {
        kotlin.jvm.internal.k.g(cVar, "<this>");
        Class clsA = ((kotlin.jvm.internal.d) cVar).a();
        if (!clsA.isPrimitive()) {
            return clsA;
        }
        String name = clsA.getName();
        switch (name.hashCode()) {
            case -1325958191:
                return !name.equals("double") ? clsA : Double.class;
            case 104431:
                return !name.equals("int") ? clsA : Integer.class;
            case 3039496:
                return !name.equals("byte") ? clsA : Byte.class;
            case 3052374:
                return !name.equals("char") ? clsA : Character.class;
            case 3327612:
                return !name.equals("long") ? clsA : Long.class;
            case 3625364:
                return !name.equals("void") ? clsA : Void.class;
            case 64711720:
                return !name.equals("boolean") ? clsA : Boolean.class;
            case 97526364:
                return !name.equals("float") ? clsA : Float.class;
            case 109413500:
                return !name.equals("short") ? clsA : Short.class;
            default:
                return clsA;
        }
    }

    public static final int p(List list) {
        kotlin.jvm.internal.k.g(list, "<this>");
        return list.size() - 1;
    }

    public static int q(Context context) {
        return context.getSharedPreferences("theme_prefs", 0).getInt("bg_index", -1);
    }

    public static boolean r(f.r rVar) {
        try {
            JSONObject jSONObjectB = b(rVar, "/getUserData", null);
            String strOptString = jSONObjectB.optString(NotificationCompat.CATEGORY_STATUS, "");
            JSONObject jSONObjectOptJSONObject = jSONObjectB.optJSONObject("data");
            if (!"success".equals(strOptString) || jSONObjectOptJSONObject == null) {
                String strOptString2 = jSONObjectB.optString("description", "");
                if (strOptString2.isEmpty()) {
                    strOptString2 = jSONObjectB.toString().substring(0, Math.min(80, jSONObjectB.toString().length()));
                }
                rVar.a("warn", "getUserData: " + strOptString2);
                return false;
            }
            rVar.f401s = jSONObjectOptJSONObject.optInt("user_coins", 0);
            rVar.f402t = jSONObjectOptJSONObject.optInt("user_runs", 0);
            String str = rVar.f386c;
            if (str == null || str.isEmpty()) {
                rVar.f386c = jSONObjectOptJSONObject.optString("user_id", "");
            }
            String str2 = rVar.f405w;
            if (str2 == null || str2.isEmpty()) {
                rVar.f405w = jSONObjectOptJSONObject.optString("user_name", "");
            }
            rVar.f406x = jSONObjectOptJSONObject.optJSONArray("milestones");
            rVar.f407y = jSONObjectOptJSONObject.optJSONArray("coins_redemption");
            if (rVar.f406x != null) {
                boolean z2 = rVar.f389g;
                rVar.f389g = false;
                ArrayList arrayList = rVar.f394l;
                arrayList.clear();
                boolean z3 = rVar.f406x.length() > 0;
                for (int i2 = 0; i2 < rVar.f406x.length(); i2++) {
                    JSONObject jSONObjectOptJSONObject2 = rVar.f406x.optJSONObject(i2);
                    if (jSONObjectOptJSONObject2 == null) {
                        z3 = false;
                    } else {
                        String strOptString3 = jSONObjectOptJSONObject2.optString("milestone_id", "");
                        boolean zEquals = "1".equals(jSONObjectOptJSONObject2.optString("milestone_claimed", "0"));
                        if (zEquals && !strOptString3.isEmpty()) {
                            arrayList.add(strOptString3);
                        }
                        if (!zEquals) {
                            z3 = false;
                        }
                    }
                }
                if (z3 && rVar.f406x.length() > 0) {
                    rVar.f389g = true;
                    rVar.f395m = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                } else if (z2) {
                    rVar.f390h = false;
                }
            }
            return true;
        } catch (Exception e2) {
            rVar.a("warn", "getUserData exception: " + e2.getMessage());
            return false;
        }
    }

    public static JSONObject s(f.G g2) {
        int iRandom = ((int) (Math.random() * 8.0d)) + 155;
        String strT = t(androidx.appcompat.graphics.drawable.a.k("b^[VCHDL786mkTp]*", String.valueOf(g2.b)), "{\"score\":" + iRandom + "}");
        return ad(am("/goldenfarm/draw", g2.f296a, String.valueOf(g2.b)), "{\"score\":" + iRandom + "}", g2, strT);
    }

    public static String t(String str, String str2) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(str.getBytes("UTF-8"), "HmacSHA256"));
            byte[] bArrDoFinal = mac.doFinal(str2.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b2 : bArrDoFinal) {
                sb.append(String.format("%02x", Byte.valueOf(b2)));
            }
            return sb.toString();
        } catch (Exception e2) {
            e2.getMessage();
            return "";
        }
    }

    public static i.e u(i.e eVar) {
        i.e eVarIntercepted;
        kotlin.jvm.internal.k.g(eVar, "<this>");
        k.c cVar = eVar instanceof k.c ? (k.c) eVar : null;
        return (cVar == null || (eVarIntercepted = cVar.intercepted()) == null) ? eVar : eVarIntercepted;
    }

    public static f.G v(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("zenoatom_prefs", 0);
        String string = sharedPreferences.getString("active_msisdn", null);
        if (string != null && !string.isEmpty()) {
            for (f.G g2 : w(context)) {
                if (g2.f296a.equals(string)) {
                    return g2;
                }
            }
        }
        String string2 = sharedPreferences.getString("token", null);
        if (string2 == null || string2.isEmpty()) {
            return null;
        }
        f.G g3 = new f.G(sharedPreferences.getString("msisdn", ""), sharedPreferences.getLong("userId", 0L), string2, sharedPreferences.getString("refreshToken", ""), sharedPreferences.getLong("expiresAt", 0L));
        ah(context, g3);
        context.getSharedPreferences("zenoatom_prefs", 0).edit().remove("msisdn").remove("userId").remove("token").remove("refreshToken").remove("expiresAt").apply();
        return g3;
    }

    public static ArrayList w(Context context) {
        ArrayList arrayList = new ArrayList();
        try {
            String string = context.getSharedPreferences("zenoatom_prefs", 0).getString("accounts_json", null);
            if (string != null && !string.isEmpty()) {
                JSONArray jSONArray = new JSONArray(string);
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i2);
                    arrayList.add(new f.G(jSONObject.getString("msisdn"), jSONObject.getLong("userId"), jSONObject.getString("token"), jSONObject.optString("refreshToken", ""), jSONObject.optLong("expiresAt", 0L)));
                }
            }
        } catch (Exception unused) {
        }
        return arrayList;
    }

    public static int x(int i2) {
        if (i2 < 0) {
            return i2;
        }
        if (i2 < 3) {
            return i2 + 1;
        }
        if (i2 < 1073741824) {
            return (int) ((i2 / 0.75f) + 1.0f);
        }
        return Integer.MAX_VALUE;
    }

    public static i.j y(i.h hVar, i.i key) {
        kotlin.jvm.internal.k.g(key, "key");
        return kotlin.jvm.internal.k.a(hVar.getKey(), key) ? i.k.b : hVar;
    }

    public static HttpURLConnection z(String str, String str2, f.G g2, String str3) throws ProtocolException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.setRequestMethod(str2);
        httpURLConnection.setConnectTimeout(15000);
        httpURLConnection.setReadTimeout(AccessibilityNodeInfoCompat.EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_MAX_LENGTH);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setRequestProperty("accept", "application/json, text/plain, */*");
        httpURLConnection.setRequestProperty("user-agent", "MyTM/4.13.0/Android/30");
        httpURLConnection.setRequestProperty("device-name", "Xiaomi 2201122C");
        httpURLConnection.setRequestProperty("x-server-select", "production");
        if ("GET".equals(str2)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            httpURLConnection.setRequestProperty("If-Modified-Since", simpleDateFormat.format(new Date()));
        }
        if ("POST".equals(str2) || "PUT".equals(str2)) {
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        }
        if (g2 != null) {
            httpURLConnection.setRequestProperty("authorization", "Bearer " + g2.f297c);
        }
        if (str3 != null && !str3.isEmpty()) {
            httpURLConnection.setRequestProperty("Checksum", str3);
        }
        return httpURLConnection;
    }
}
