package f;

import android.graphics.Bitmap;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import com.zenoatom.app.CityRunActivity;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class p extends WebViewClient {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ CityRunActivity f382a;

    public p(CityRunActivity cityRunActivity) {
        this.f382a = cityRunActivity;
    }

    @Override // android.webkit.WebViewClient
    public final void onPageFinished(WebView webView, String str) {
        String str2;
        super.onPageFinished(webView, str);
        CityRunActivity cityRunActivity = this.f382a;
        cityRunActivity.d.setVisibility(4);
        TextView textView = cityRunActivity.f155f;
        if (textView != null) {
            if (str.length() > 55) {
                str2 = str.substring(0, 55) + "…";
            } else {
                str2 = str;
            }
            textView.setText(str2);
        }
        CityRunActivity.j(cityRunActivity, str);
    }

    @Override // android.webkit.WebViewClient
    public final void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        super.onPageStarted(webView, str, bitmap);
        CityRunActivity cityRunActivity = this.f382a;
        cityRunActivity.d.setVisibility(0);
        CityRunActivity.j(cityRunActivity, str);
    }

    @Override // android.webkit.WebViewClient
    public final boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
        return CityRunActivity.j(this.f382a, webResourceRequest.getUrl().toString());
    }
}
