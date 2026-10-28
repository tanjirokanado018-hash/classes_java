package androidx.lifecycle;

import androidx.core.app.NotificationCompat;
import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.motion.MaterialBackOrchestrator;
import com.zenoatom.app.SplashActivity;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class f implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f70c;

    public /* synthetic */ f(int i2, Object obj) {
        this.b = i2;
        this.f70c = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.b;
        Object obj = this.f70c;
        switch (i2) {
            case 0:
                ProcessLifecycleOwner.delayedPauseRunnable$lambda$0((ProcessLifecycleOwner) obj);
                break;
            case 1:
                ((CarouselLayoutManager) obj).refreshKeylineState();
                break;
            case 2:
            default:
                SplashActivity splashActivity = (SplashActivity) obj;
                int i3 = SplashActivity.f253f;
                splashActivity.getClass();
                String string = "open";
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
                                string = jSONObject.has(NotificationCompat.CATEGORY_STATUS) ? jSONObject.getString(NotificationCompat.CATEGORY_STATUS) : "open";
                                if (jSONObject.has("cityrun")) {
                                    splashActivity.getSharedPreferences("app_config", 0).edit().putString("cityrun", jSONObject.getString("cityrun").trim()).apply();
                                }
                                if (jSONObject.has("cityrun_cooldowns")) {
                                    splashActivity.getSharedPreferences("app_config", 0).edit().putString("cityrun_cooldowns", jSONObject.getString("cityrun_cooldowns").trim()).apply();
                                }
                                if (jSONObject.has("game_cooldown")) {
                                    splashActivity.getSharedPreferences("app_config", 0).edit().putString("game_cooldown", jSONObject.getString("game_cooldown").trim()).apply();
                                }
                            }
                        }
                    }
                    httpURLConnection.disconnect();
                    break;
                } catch (Exception unused) {
                }
                splashActivity.b.postDelayed(new androidx.core.content.res.a(6, splashActivity, string), 800L);
                break;
            case 3:
                ((MaterialBackOrchestrator) obj).startListeningForBackCallbacksWithPriorityOverlay();
                break;
        }
    }
}
