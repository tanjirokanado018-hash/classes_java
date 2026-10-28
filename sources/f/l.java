package f;

import android.os.Handler;
import com.zenoatom.app.CityRunActivity;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class l implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ CityRunActivity f374c;
    public final /* synthetic */ r d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final /* synthetic */ String f375e;

    public /* synthetic */ l(CityRunActivity cityRunActivity, r rVar, String str, int i2) {
        this.b = i2;
        this.f374c = cityRunActivity;
        this.d = rVar;
        this.f375e = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = 2;
        int i3 = this.b;
        int i4 = 1;
        String str = this.f375e;
        CityRunActivity cityRunActivity = this.f374c;
        r rVar = this.d;
        switch (i3) {
            case 0:
                int i5 = CityRunActivity.f151p;
                cityRunActivity.getClass();
                w.y.r(rVar);
                cityRunActivity.f161l.post(new l(cityRunActivity, rVar, str, i4));
                break;
            case 1:
                int i6 = CityRunActivity.f151p;
                cityRunActivity.getClass();
                if (rVar.f389g) {
                    cityRunActivity.t("ယနေ့ Milestones ပြီးပါပြီ — မနက်ဖြန် ပြန်လာပါ");
                    cityRunActivity.u(str);
                    break;
                } else {
                    rVar.f387e = true;
                    rVar.f403u = "running";
                    rVar.f400r = System.currentTimeMillis();
                    rVar.a("info", "Bot started");
                    cityRunActivity.r();
                    cityRunActivity.u(str);
                    RunnableC0029i runnableC0029i = new RunnableC0029i(cityRunActivity, str, i2);
                    cityRunActivity.f159j.put(str, runnableC0029i);
                    Handler handler = cityRunActivity.f161l;
                    handler.postDelayed(runnableC0029i, 4000L);
                    if (cityRunActivity.f164o == null) {
                        o oVar = new o(0, cityRunActivity);
                        cityRunActivity.f164o = oVar;
                        handler.post(oVar);
                        break;
                    }
                }
                break;
            default:
                int i7 = CityRunActivity.f151p;
                cityRunActivity.getClass();
                try {
                    w.y.ag(rVar);
                } catch (Exception e2) {
                    rVar.a("error", "Error: " + e2.getMessage());
                }
                cityRunActivity.r();
                Handler handler2 = cityRunActivity.f161l;
                handler2.post(new RunnableC0029i(cityRunActivity, str, 3));
                if (rVar.f387e) {
                    RunnableC0029i runnableC0029i2 = new RunnableC0029i(cityRunActivity, str, i2);
                    cityRunActivity.f159j.put(str, runnableC0029i2);
                    handler2.postDelayed(runnableC0029i2, 4000L);
                }
                break;
        }
    }
}
