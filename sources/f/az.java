package f;

import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.zenoatom.app.PointsActivity;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class az implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ PointsActivity f354c;

    public /* synthetic */ az(PointsActivity pointsActivity, int i2) {
        this.b = i2;
        this.f354c = pointsActivity;
    }

    /* JADX WARN: Code duplicated, block: B:82:0x0182  */
    @Override // java.lang.Runnable
    public final void run() {
        Handler handler;
        PointsActivity pointsActivity;
        long j2;
        long jOptLong;
        JSONArray jSONArray;
        JSONArray jSONArray2;
        JSONArray jSONArray3;
        JSONArray jSONArray4;
        JSONArray jSONArrayOptJSONArray;
        int i2 = 1;
        int i3 = this.b;
        final PointsActivity pointsActivity2 = this.f354c;
        switch (i3) {
            case 0:
                Handler handler2 = pointsActivity2.b;
                try {
                    G g2 = pointsActivity2.f230c;
                    JSONObject jSONObjectOptJSONObject = w.y.k(g2, w.y.am("/point-system/dashboard", g2.f296a, String.valueOf(g2.b))).optJSONObject("data");
                    JSONObject jSONObjectOptJSONObject2 = jSONObjectOptJSONObject != null ? jSONObjectOptJSONObject.optJSONObject("attribute") : null;
                    if (jSONObjectOptJSONObject2 == null) {
                        handler2.post(new az(pointsActivity2, i2));
                    } else {
                        final String strOptString = jSONObjectOptJSONObject2.optString("starStatusLabel", jSONObjectOptJSONObject2.optString("starStatus", "SILVER STAR"));
                        final long jOptLong2 = jSONObjectOptJSONObject2.optLong("totalPoint", 0L);
                        JSONObject jSONObjectOptJSONObject3 = jSONObjectOptJSONObject2.optJSONObject("mostExpiry");
                        String strOptString2 = jSONObjectOptJSONObject3 != null ? jSONObjectOptJSONObject3.optString("date", "") : "";
                        int iOptInt = jSONObjectOptJSONObject3 != null ? jSONObjectOptJSONObject3.optInt("point", 0) : 0;
                        final String strOptString3 = (strOptString2.isEmpty() || iOptInt <= 0) ? jSONObjectOptJSONObject2.optString("validityEndDateText", "") : strOptString2 + " ( " + iOptInt + " points ) သက်တမ်းကုန်ပါမည်";
                        JSONObject jSONObjectOptJSONObject4 = jSONObjectOptJSONObject2.optJSONObject("progressBar");
                        if (jSONObjectOptJSONObject4 != null) {
                            j2 = 0;
                            jOptLong = jSONObjectOptJSONObject4.optLong("totalRequiredPoint", 0L);
                        } else {
                            j2 = 0;
                            jOptLong = 0;
                        }
                        long jOptLong3 = jSONObjectOptJSONObject4 != null ? jSONObjectOptJSONObject4.optLong("earnedPoint", j2) : 0L;
                        String strOptString4 = jSONObjectOptJSONObject4 != null ? jSONObjectOptJSONObject4.optString("nextTierEligibilityText", "") : "";
                        int i4 = jOptLong > 0 ? (int) ((100 * jOptLong3) / jOptLong) : 0;
                        final JSONArray jSONArrayOptJSONArray2 = jSONObjectOptJSONObject2.optJSONArray("atomRewards") != null ? jSONObjectOptJSONObject2.optJSONArray("atomRewards") : new JSONArray();
                        JSONArray jSONArray5 = new JSONArray();
                        JSONArray jSONArray6 = new JSONArray();
                        JSONArray jSONArray7 = new JSONArray();
                        try {
                            G g3 = pointsActivity2.f230c;
                            try {
                                jSONArray4 = jSONArray6;
                                jSONArray = jSONArray7;
                                try {
                                    JSONObject jSONObjectOptJSONObject5 = w.y.k(g3, w.y.am("/point-system/details", g3.f296a, String.valueOf(g3.b))).optJSONObject("data");
                                    JSONObject jSONObjectOptJSONObject6 = jSONObjectOptJSONObject5 != null ? jSONObjectOptJSONObject5.optJSONObject("attribute") : null;
                                    if (jSONObjectOptJSONObject6 != null) {
                                        JSONObject jSONObjectOptJSONObject7 = jSONObjectOptJSONObject6.optJSONObject("atomRewards");
                                        jSONObjectOptJSONObject6.optJSONArray("atomRewards");
                                        if (jSONObjectOptJSONObject7 != null) {
                                            jSONArray5 = jSONObjectOptJSONObject7.optJSONArray("voice") != null ? jSONObjectOptJSONObject7.optJSONArray("voice") : new JSONArray();
                                            jSONArray6 = jSONObjectOptJSONObject7.optJSONArray("money") != null ? jSONObjectOptJSONObject7.optJSONArray("money") : new JSONArray();
                                            try {
                                                jSONArrayOptJSONArray = jSONObjectOptJSONObject7.optJSONArray("sms") != null ? jSONObjectOptJSONObject7.optJSONArray("sms") : new JSONArray();
                                            } catch (Exception unused) {
                                                jSONArray2 = jSONArray5;
                                                jSONArray3 = jSONArray6;
                                            }
                                        } else {
                                            jSONArray6 = jSONArray4;
                                            jSONArrayOptJSONArray = jSONArray;
                                        }
                                    } else {
                                        jSONArray6 = jSONArray4;
                                        jSONArrayOptJSONArray = jSONArray;
                                    }
                                    jSONArray2 = jSONArray5;
                                    jSONArray3 = jSONArray6;
                                    jSONArray = jSONArrayOptJSONArray;
                                } catch (Exception unused2) {
                                    jSONArray6 = jSONArray4;
                                    jSONArray2 = jSONArray5;
                                    jSONArray3 = jSONArray6;
                                    final int i5 = i4;
                                    final long j3 = jOptLong3;
                                    final long j4 = jOptLong;
                                    final String str = strOptString4;
                                    pointsActivity = pointsActivity2;
                                    final JSONArray jSONArray8 = jSONArray2;
                                    final JSONArray jSONArray9 = jSONArray3;
                                    final JSONArray jSONArray10 = jSONArray;
                                    handler = handler2;
                                    handler.post(new Runnable() { // from class: f.B
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            String str2;
                                            PointsActivity pointsActivity3 = pointsActivity2;
                                            long j5 = jOptLong2;
                                            pointsActivity3.f242p = j5;
                                            TextView textView = pointsActivity3.f232f;
                                            if (textView != null) {
                                                textView.setText(strOptString);
                                            }
                                            TextView textView2 = pointsActivity3.f233g;
                                            if (textView2 != null) {
                                                textView2.setText(j5 + " pts");
                                            }
                                            TextView textView3 = pointsActivity3.f234h;
                                            if (textView3 != null) {
                                                textView3.setText(strOptString3);
                                            }
                                            ProgressBar progressBar = pointsActivity3.f235i;
                                            if (progressBar != null) {
                                                progressBar.setMax(100);
                                                pointsActivity3.f235i.setProgress(i5);
                                            }
                                            TextView textView4 = pointsActivity3.f236j;
                                            if (textView4 != null) {
                                                textView4.setText(j3 + " Ks");
                                            }
                                            TextView textView5 = pointsActivity3.f237k;
                                            if (textView5 != null) {
                                                long j6 = j4;
                                                if (j6 > 0) {
                                                    str2 = j6 + " Ks";
                                                } else {
                                                    str2 = "";
                                                }
                                                textView5.setText(str2);
                                            }
                                            TextView textView6 = pointsActivity3.f238l;
                                            if (textView6 != null) {
                                                textView6.setText(str);
                                            }
                                            pointsActivity3.ad = jSONArrayOptJSONArray2;
                                            pointsActivity3.ae = jSONArray8;
                                            pointsActivity3.af = jSONArray9;
                                            pointsActivity3.ag = jSONArray10;
                                            pointsActivity3.l();
                                            ProgressBar progressBar2 = pointsActivity3.d;
                                            if (progressBar2 != null) {
                                                progressBar2.setVisibility(8);
                                            }
                                            ScrollView scrollView = pointsActivity3.f231e;
                                            if (scrollView != null) {
                                                scrollView.setVisibility(0);
                                            }
                                        }
                                    });
                                    return;
                                }
                            } catch (Exception unused3) {
                                jSONArray4 = jSONArray6;
                                jSONArray = jSONArray7;
                            }
                        } catch (Exception unused4) {
                            jSONArray = jSONArray7;
                        }
                        final int i6 = i4;
                        final long j5 = jOptLong3;
                        final long j6 = jOptLong;
                        final String str2 = strOptString4;
                        pointsActivity = pointsActivity2;
                        final JSONArray jSONArray11 = jSONArray2;
                        final JSONArray jSONArray12 = jSONArray3;
                        final JSONArray jSONArray13 = jSONArray;
                        try {
                            handler = handler2;
                            try {
                                handler.post(new Runnable() { // from class: f.B
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        String str3;
                                        PointsActivity pointsActivity3 = pointsActivity2;
                                        long j7 = jOptLong2;
                                        pointsActivity3.f242p = j7;
                                        TextView textView = pointsActivity3.f232f;
                                        if (textView != null) {
                                            textView.setText(strOptString);
                                        }
                                        TextView textView2 = pointsActivity3.f233g;
                                        if (textView2 != null) {
                                            textView2.setText(j7 + " pts");
                                        }
                                        TextView textView3 = pointsActivity3.f234h;
                                        if (textView3 != null) {
                                            textView3.setText(strOptString3);
                                        }
                                        ProgressBar progressBar = pointsActivity3.f235i;
                                        if (progressBar != null) {
                                            progressBar.setMax(100);
                                            pointsActivity3.f235i.setProgress(i6);
                                        }
                                        TextView textView4 = pointsActivity3.f236j;
                                        if (textView4 != null) {
                                            textView4.setText(j5 + " Ks");
                                        }
                                        TextView textView5 = pointsActivity3.f237k;
                                        if (textView5 != null) {
                                            long j8 = j6;
                                            if (j8 > 0) {
                                                str3 = j8 + " Ks";
                                            } else {
                                                str3 = "";
                                            }
                                            textView5.setText(str3);
                                        }
                                        TextView textView6 = pointsActivity3.f238l;
                                        if (textView6 != null) {
                                            textView6.setText(str2);
                                        }
                                        pointsActivity3.ad = jSONArrayOptJSONArray2;
                                        pointsActivity3.ae = jSONArray11;
                                        pointsActivity3.af = jSONArray12;
                                        pointsActivity3.ag = jSONArray13;
                                        pointsActivity3.l();
                                        ProgressBar progressBar2 = pointsActivity3.d;
                                        if (progressBar2 != null) {
                                            progressBar2.setVisibility(8);
                                        }
                                        ScrollView scrollView = pointsActivity3.f231e;
                                        if (scrollView != null) {
                                            scrollView.setVisibility(0);
                                        }
                                    }
                                });
                            } catch (Exception e2) {
                                e = e2;
                                handler.post(new androidx.core.content.res.a(5, pointsActivity, e));
                                return;
                            }
                        } catch (Exception e3) {
                            e = e3;
                            handler = handler2;
                        }
                    }
                } catch (Exception e4) {
                    e = e4;
                    handler = handler2;
                    pointsActivity = pointsActivity2;
                }
                break;
            default:
                ProgressBar progressBar = pointsActivity2.d;
                if (progressBar != null) {
                    progressBar.setVisibility(8);
                }
                Toast.makeText(pointsActivity2, "Data not available", 1).show();
                break;
        }
    }
}
