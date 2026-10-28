package f;

import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.zenoatom.app.BaseActivity;
import com.zenoatom.app.CityRunActivity;
import com.zenoatom.app.PointsActivity;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: renamed from: f.f, reason: case insensitive filesystem */
/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class RunnableC0026f implements Runnable {
    public final /* synthetic */ int b = 0;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ String f361c;
    public final /* synthetic */ BaseActivity d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final /* synthetic */ Object f362e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final /* synthetic */ Object f363f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public final /* synthetic */ Object f364g;

    public /* synthetic */ RunnableC0026f(CityRunActivity cityRunActivity, r rVar, String str, AtomicInteger atomicInteger, RunnableC0028h runnableC0028h) {
        this.d = cityRunActivity;
        this.f362e = rVar;
        this.f361c = str;
        this.f363f = atomicInteger;
        this.f364g = runnableC0028h;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.b;
        String str = this.f361c;
        Object obj = this.f364g;
        Object obj2 = this.f363f;
        Object obj3 = this.f362e;
        BaseActivity baseActivity = this.d;
        switch (i2) {
            case 0:
                CityRunActivity cityRunActivity = (CityRunActivity) baseActivity;
                AtomicInteger atomicInteger = (AtomicInteger) obj2;
                Runnable runnable = (Runnable) obj;
                int i3 = CityRunActivity.f151p;
                cityRunActivity.getClass();
                boolean zR = w.y.r((r) obj3);
                Handler handler = cityRunActivity.f161l;
                if (zR) {
                    cityRunActivity.r();
                    handler.post(new RunnableC0029i(cityRunActivity, str, 1));
                }
                if (atomicInteger.decrementAndGet() == 0 && runnable != null) {
                    handler.post(runnable);
                    break;
                }
                break;
            default:
                PointsActivity pointsActivity = (PointsActivity) baseActivity;
                TextView textView = (TextView) obj2;
                String str2 = (String) obj;
                int i4 = PointsActivity.ai;
                pointsActivity.getClass();
                ((LinearLayout) obj3).setEnabled(true);
                if (textView != null) {
                    textView.setText(str);
                }
                pointsActivity.m(str2);
                break;
        }
    }

    public /* synthetic */ RunnableC0026f(PointsActivity pointsActivity, LinearLayout linearLayout, TextView textView, String str, String str2) {
        this.d = pointsActivity;
        this.f362e = linearLayout;
        this.f363f = textView;
        this.f361c = str;
        this.f364g = str2;
    }
}
