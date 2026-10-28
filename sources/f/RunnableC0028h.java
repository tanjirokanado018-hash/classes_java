package f;

import com.zenoatom.app.CityRunActivity;

/* JADX INFO: renamed from: f.h, reason: case insensitive filesystem */
/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class RunnableC0028h implements Runnable {
    public final /* synthetic */ int b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ CityRunActivity f366c;

    public /* synthetic */ RunnableC0028h(CityRunActivity cityRunActivity, int i2) {
        this.b = i2;
        this.f366c = cityRunActivity;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.b) {
            case 0:
                CityRunActivity cityRunActivity = this.f366c;
                cityRunActivity.f154e.setVisibility(0);
                cityRunActivity.f155f.setText("MSISDN တွေ့ပြီ — Auto Login လုပ်နေသည်…");
                break;
            case 1:
                CityRunActivity cityRunActivity2 = this.f366c;
                cityRunActivity2.f161l.post(new RunnableC0028h(cityRunActivity2, 3));
                break;
            case 2:
                CityRunActivity cityRunActivity3 = this.f366c;
                if (cityRunActivity3.f158i.isEmpty() && !cityRunActivity3.f163n) {
                    cityRunActivity3.f153c.stopLoading();
                    cityRunActivity3.q();
                    break;
                }
                break;
            case 3:
                this.f366c.f157h.setRefreshing(false);
                break;
            case 4:
                int i2 = CityRunActivity.f151p;
                CityRunActivity cityRunActivity4 = this.f366c;
                cityRunActivity4.f153c.stopLoading();
                cityRunActivity4.b.setVisibility(8);
                cityRunActivity4.f163n = false;
                cityRunActivity4.t("Auto Login အောင်မြင်သည်");
                cityRunActivity4.q();
                break;
            default:
                CityRunActivity cityRunActivity5 = this.f366c;
                if (cityRunActivity5.b.getVisibility() != 0) {
                    cityRunActivity5.f163n = false;
                    cityRunActivity5.q();
                } else {
                    cityRunActivity5.f154e.setVisibility(8);
                    cityRunActivity5.f155f.setText("Login မအောင်မြင် — ထပ်ကြိုးစားပါ");
                    cityRunActivity5.f163n = false;
                    cityRunActivity5.f153c.loadUrl("http://blatom.cityrun.pro/home/GetGWMDN?source=https%3a%2f%2fcityrun.pro%2f%3fpop2home%3dfalse");
                }
                break;
        }
    }
}
