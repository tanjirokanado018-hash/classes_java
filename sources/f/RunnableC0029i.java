package f;

import android.os.Handler;
import com.zenoatom.app.CityRunActivity;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: renamed from: f.i, reason: case insensitive filesystem */
/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class RunnableC0029i implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ CityRunActivity f367c;
    public final /* synthetic */ String d;

    public /* synthetic */ RunnableC0029i(CityRunActivity cityRunActivity, String str, int i2) {
        this.b = i2;
        this.f367c = cityRunActivity;
        this.d = str;
    }

    /* JADX WARN: Code duplicated, block: B:83:0x01ce  */
    @Override // java.lang.Runnable
    public final void run() throws UnsupportedEncodingException {
        Handler handler;
        RunnableC0028h runnableC0028h;
        Handler handler2;
        InputStream errorStream;
        String string;
        String str;
        String str2;
        int i2 = this.b;
        String str3 = this.d;
        CityRunActivity cityRunActivity = this.f367c;
        switch (i2) {
            case 0:
                Handler handler3 = cityRunActivity.f161l;
                r rVar = new r();
                rVar.f385a = "pending";
                rVar.a("info", "Logging in via encrypted MSISDN: pending");
                try {
                    String str4 = "https://cityrun.pro/ws/redirect/?AdNetwork=atom_app&ClickID=&Publisher&msisdn=" + URLEncoder.encode(str3, "UTF-8");
                    new CookieManager();
                    boolean z2 = false;
                    String strSubstring = null;
                    String strDecode = null;
                    int i3 = 0;
                    while (true) {
                        if (i3 < 12) {
                            try {
                                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str4).openConnection();
                                httpURLConnection.setInstanceFollowRedirects(z2);
                                httpURLConnection.setConnectTimeout(30000);
                                httpURLConnection.setReadTimeout(30000);
                                httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 12; Redmi K30 5G Build/SKQ1.211006.001; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/149.0.7827.91 Mobile Safari/537.36");
                                httpURLConnection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
                                httpURLConnection.setRequestProperty("Accept-Language", "my,en-US;q=0.9,en;q=0.8");
                                httpURLConnection.setRequestProperty("X-Requested-With", "mm.com.atom.store");
                                httpURLConnection.setRequestProperty("sec-ch-ua-mobile", "?1");
                                if (strSubstring != null) {
                                    httpURLConnection.setRequestProperty("Cookie", "ci_session=" + strSubstring);
                                }
                                int responseCode = httpURLConnection.getResponseCode();
                                for (Map.Entry<String, List<String>> entry : httpURLConnection.getHeaderFields().entrySet()) {
                                    String key = entry.getKey();
                                    if (key != null) {
                                        str = strSubstring;
                                        if (key.equalsIgnoreCase("Set-Cookie")) {
                                            Iterator<String> it = entry.getValue().iterator();
                                            strSubstring = str;
                                            while (it.hasNext()) {
                                                Iterator<String> it2 = it;
                                                String next = it.next();
                                                if (next.startsWith("ci_session=")) {
                                                    int iIndexOf = next.indexOf(59);
                                                    strSubstring = iIndexOf > 0 ? next.substring(11, iIndexOf) : next.substring(11);
                                                }
                                                strDecode = strDecode;
                                                it = it2;
                                            }
                                            str2 = strDecode;
                                        }
                                        strDecode = str2;
                                    } else {
                                        str = strSubstring;
                                    }
                                    str2 = strDecode;
                                    strSubstring = str;
                                    strDecode = str2;
                                }
                                String str5 = strSubstring;
                                String strDecode2 = strDecode;
                                String headerField = httpURLConnection.getHeaderField("Location");
                                if (headerField != null && headerField.contains("user_id=")) {
                                    Matcher matcher = Pattern.compile("[?&]user_id=([^&]+)").matcher(headerField);
                                    if (matcher.find()) {
                                        strDecode2 = URLDecoder.decode(matcher.group(1), "UTF-8");
                                    }
                                }
                                try {
                                    errorStream = httpURLConnection.getInputStream();
                                } catch (Exception unused) {
                                    errorStream = httpURLConnection.getErrorStream();
                                }
                                if (errorStream != null) {
                                    handler2 = handler3;
                                    try {
                                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(errorStream, "UTF-8"));
                                        StringBuilder sb = new StringBuilder();
                                        while (true) {
                                            String line = bufferedReader.readLine();
                                            if (line != null) {
                                                sb.append(line);
                                            } else {
                                                bufferedReader.close();
                                                string = sb.toString();
                                            }
                                        }
                                    } catch (Exception e2) {
                                        e = e2;
                                        handler = handler2;
                                        rVar.a("error", "Login exception: " + e.getMessage());
                                        runnableC0028h = new RunnableC0028h(cityRunActivity, 5);
                                        handler.post(runnableC0028h);
                                        return;
                                    }
                                } else {
                                    handler2 = handler3;
                                    string = "";
                                }
                                httpURLConnection.disconnect();
                                if (strDecode2 == null && string.contains("user_id=")) {
                                    Matcher matcher2 = Pattern.compile("user_id=([^&\"']+)").matcher(string);
                                    if (matcher2.find()) {
                                        strDecode = URLDecoder.decode(matcher2.group(1), "UTF-8");
                                    } else {
                                        strDecode = strDecode2;
                                    }
                                } else {
                                    strDecode = strDecode2;
                                }
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("Hop ");
                                sb2.append(i3);
                                sb2.append(": HTTP ");
                                sb2.append(responseCode);
                                sb2.append(headerField != null ? " → " + headerField.substring(0, Math.min(60, headerField.length())) : "");
                                rVar.a("info", sb2.toString());
                                if (responseCode < 300 || responseCode >= 400 || headerField == null) {
                                    strSubstring = str5;
                                } else {
                                    if (!headerField.startsWith("http")) {
                                        headerField = "https://cityrun.pro/" + headerField.replaceAll("^/+", "");
                                    }
                                    str4 = headerField;
                                    i3++;
                                    strSubstring = str5;
                                    handler3 = handler2;
                                    z2 = false;
                                }
                            } catch (Exception e3) {
                                e = e3;
                                handler2 = handler3;
                                handler = handler2;
                                rVar.a("error", "Login exception: " + e.getMessage());
                                runnableC0028h = new RunnableC0028h(cityRunActivity, 5);
                                handler.post(runnableC0028h);
                                return;
                            }
                        } else {
                            handler2 = handler3;
                        }
                        break;
                    }
                    if (strSubstring == null || strSubstring.isEmpty()) {
                        handler = handler2;
                        try {
                            rVar.a("error", "No ci_session cookie in redirect chain.");
                        } catch (Exception e4) {
                            e = e4;
                            rVar.a("error", "Login exception: " + e.getMessage());
                        }
                    } else {
                        rVar.b = "ci_session=" + strSubstring;
                        if (strDecode == null || strDecode.isEmpty()) {
                            rVar.d = "https://cityrun.pro/playgame";
                            rVar.a("warn", "Could not extract user_id — trying getUserData anyway");
                        } else {
                            rVar.f386c = strDecode;
                            rVar.d = "https://cityrun.pro/final_games/toh-toh-v68/?user_id=" + strDecode + "&user_type=sub";
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append("User ID: ");
                            sb3.append(strDecode);
                            rVar.a("success", sb3.toString());
                        }
                        boolean zR = w.y.r(rVar);
                        if (zR) {
                            rVar.a("success", "Login OK — Coins: " + rVar.f401s + ", Runs: " + rVar.f402t);
                        } else {
                            rVar.a("error", "Login failed — session invalid");
                        }
                        if (zR) {
                            String str6 = rVar.f386c;
                            String strReplaceAll = (str6 == null || str6.isEmpty()) ? "u" + Integer.toHexString(str3.hashCode() & 268435455) : rVar.f386c.replaceAll("[^0-9a-zA-Z_-]", "");
                            rVar.f385a = strReplaceAll;
                            rVar.f403u = "idle";
                            rVar.a("success", "Account ready — Bot စတင်ရန် START နှိပ်ပါ");
                            LinkedHashMap linkedHashMap = cityRunActivity.f158i;
                            if (!linkedHashMap.containsKey(strReplaceAll)) {
                                linkedHashMap.put(strReplaceAll, rVar);
                            }
                            cityRunActivity.r();
                            runnableC0028h = new RunnableC0028h(cityRunActivity, 4);
                            handler = handler2;
                        } else {
                            handler = handler2;
                        }
                        handler.post(runnableC0028h);
                    }
                } catch (Exception e5) {
                    e = e5;
                    handler = handler3;
                }
                runnableC0028h = new RunnableC0028h(cityRunActivity, 5);
                handler.post(runnableC0028h);
                break;
            case 1:
                int i4 = CityRunActivity.f151p;
                cityRunActivity.u(str3);
                break;
            case 2:
                r rVar2 = (r) cityRunActivity.f158i.get(str3);
                if (rVar2 != null && rVar2.f387e) {
                    cityRunActivity.f162m.execute(new l(cityRunActivity, rVar2, str3, 2));
                    break;
                }
                break;
            default:
                int i5 = CityRunActivity.f151p;
                cityRunActivity.u(str3);
                break;
        }
    }
}
