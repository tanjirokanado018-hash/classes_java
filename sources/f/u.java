package f;

import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;
import com.zenoatom.app.ClaimActivity;
import com.zenoatom.app.DashboardActivity;
import com.zenoatom.app.HistoryActivity;
import com.zenoatom.app.LoginActivity;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class u implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f412c;
    public final /* synthetic */ Object d;

    public /* synthetic */ u(int i2, Object obj, Object obj2) {
        this.b = i2;
        this.d = obj;
        this.f412c = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.b;
        int i3 = 0;
        Object obj = this.f412c;
        Object obj2 = this.d;
        switch (i2) {
            case 0:
                o oVar = (o) obj2;
                LinearLayout linearLayout = ((ClaimActivity) oVar.f381c).f169f;
                if (linearLayout != null) {
                    linearLayout.removeAllViews();
                }
                JSONArray jSONArray = (JSONArray) obj;
                if (jSONArray != null) {
                    for (int i4 = 0; i4 < jSONArray.length(); i4++) {
                        try {
                            ClaimActivity.j((ClaimActivity) ((o) obj2).f381c, ((JSONArray) obj).getJSONObject(i4));
                        } catch (Exception unused) {
                        }
                    }
                }
                Object obj3 = oVar.f381c;
                ProgressBar progressBar = ((ClaimActivity) obj3).d;
                if (progressBar != null) {
                    progressBar.setVisibility(8);
                }
                ScrollView scrollView = ((ClaimActivity) obj3).f168e;
                if (scrollView != null) {
                    scrollView.setVisibility(0);
                }
                break;
            case 1:
                w wVar = (w) obj2;
                wVar.f415c.setEnabled(true);
                wVar.f415c.setText(wVar.d);
                Toast.makeText(wVar.f416e, (String) obj, 1).show();
                break;
            case 2:
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("https://bigwin.x10.mx/atom/message.json?t=" + System.currentTimeMillis()).openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(6000);
                    httpURLConnection.setReadTimeout(6000);
                    httpURLConnection.setUseCaches(false);
                    httpURLConnection.setRequestProperty("Accept", "application/json");
                    httpURLConnection.setRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate");
                    httpURLConnection.setRequestProperty("Pragma", "no-cache");
                    httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 10; Mobile) AppleWebKit/537.36");
                    if (httpURLConnection.getResponseCode() == 200) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
                        StringBuilder sb = new StringBuilder();
                        while (true) {
                            String line = bufferedReader.readLine();
                            if (line != null) {
                                sb.append(line);
                            } else {
                                bufferedReader.close();
                                JSONObject jSONObject = new JSONObject(sb.toString());
                                ((DashboardActivity) obj2).ao.post(new ab(this, jSONObject.optString("version", (String) obj).trim(), jSONObject.optString("update_link", "").trim(), jSONObject.optString("notification", "").trim(), jSONObject.optString("notification_title", "အသိပေးချက်").trim(), jSONObject.optString("notification_btn_text", "သိရှိပြီ").trim(), jSONObject.optString("notification_link", "").trim()));
                            }
                        }
                    }
                    httpURLConnection.disconnect();
                } catch (Exception unused2) {
                    return;
                }
                break;
            case 3:
                as asVar = (as) obj2;
                ProgressBar progressBar2 = asVar.f345c.f198f;
                if (progressBar2 != null) {
                    progressBar2.setVisibility(8);
                }
                HistoryActivity historyActivity = asVar.f345c;
                if (historyActivity.d != null) {
                    JSONArray jSONArray2 = (JSONArray) obj;
                    if (jSONArray2 == null || jSONArray2.length() == 0) {
                        HistoryActivity.j(historyActivity, historyActivity.d, "အနိုင်ရထားသော ဆုများ မရှိသေးပါ");
                    } else {
                        while (i3 < jSONArray2.length()) {
                            try {
                                JSONObject jSONObject2 = ((JSONArray) obj).getJSONObject(i3);
                                HistoryActivity.k(((as) obj2).f345c, ((as) obj2).f345c.d, jSONObject2.optString("title", "Draw"), null, jSONObject2.optString("date", ""), false);
                            } catch (Exception unused3) {
                            }
                            i3++;
                        }
                    }
                    break;
                }
                break;
            case 4:
                as asVar2 = (as) obj2;
                ProgressBar progressBar3 = asVar2.f345c.f198f;
                if (progressBar3 != null) {
                    progressBar3.setVisibility(8);
                }
                HistoryActivity historyActivity2 = asVar2.f345c;
                LinearLayout linearLayout2 = historyActivity2.d;
                if (linearLayout2 != null) {
                    HistoryActivity.j(historyActivity2, linearLayout2, "Error: " + ((Exception) obj).getMessage());
                }
                break;
            case 5:
                as asVar3 = (as) obj2;
                ProgressBar progressBar4 = asVar3.f345c.f199g;
                if (progressBar4 != null) {
                    progressBar4.setVisibility(8);
                }
                HistoryActivity historyActivity3 = asVar3.f345c;
                if (historyActivity3.f197e != null) {
                    JSONArray jSONArray3 = (JSONArray) obj;
                    if (jSONArray3 == null || jSONArray3.length() == 0) {
                        HistoryActivity.j(historyActivity3, historyActivity3.f197e, "အနိုင်ရထားသော ဆုများ မရှိသေးပါ");
                    } else {
                        while (i3 < jSONArray3.length()) {
                            try {
                                JSONObject jSONObject3 = ((JSONArray) obj).getJSONObject(i3);
                                String strOptString = jSONObject3.optString("title_my", jSONObject3.optString("title", "GF Draw"));
                                String strOptString2 = jSONObject3.optString("desc_my", "");
                                HistoryActivity.k(((as) obj2).f345c, ((as) obj2).f345c.f197e, strOptString, strOptString2.isEmpty() ? null : strOptString2, jSONObject3.optString("winTime", ""), true);
                            } catch (Exception unused4) {
                            }
                            i3++;
                        }
                    }
                    break;
                }
                break;
            case 6:
                as asVar4 = (as) obj2;
                ProgressBar progressBar5 = asVar4.f345c.f199g;
                if (progressBar5 != null) {
                    progressBar5.setVisibility(8);
                }
                HistoryActivity historyActivity4 = asVar4.f345c;
                LinearLayout linearLayout3 = historyActivity4.f197e;
                if (linearLayout3 != null) {
                    HistoryActivity.j(historyActivity4, linearLayout3, "GF Error: " + ((Exception) obj).getMessage());
                }
                break;
            case 7:
                ax axVar = (ax) obj2;
                LoginActivity loginActivity = axVar.d;
                int i5 = LoginActivity.f208w;
                loginActivity.l(false);
                axVar.d.n("Error: " + ((Exception) obj).getMessage());
                break;
            default:
                ax axVar2 = (ax) obj2;
                LoginActivity loginActivity2 = axVar2.d;
                int i6 = LoginActivity.f208w;
                loginActivity2.m(false);
                axVar2.d.n("Error: " + ((Exception) obj).getMessage());
                break;
        }
    }
}
