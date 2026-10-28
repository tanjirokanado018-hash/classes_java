package com.zenoatom.app;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.zenoatom.app.HistoryActivity;
import f.G;
import f.as;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class HistoryActivity extends BaseActivity {

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public static final /* synthetic */ int f194p = 0;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final ExecutorService f195a = Executors.newCachedThreadPool();
    public final Handler b = new Handler(Looper.getMainLooper());

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public G f196c;
    public LinearLayout d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public LinearLayout f197e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public ProgressBar f198f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public ProgressBar f199g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public LinearLayout f200h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public LinearLayout f201i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public ImageView f202j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public ImageView f203k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public TextView f204l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public TextView f205m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public LinearLayout f206n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public LinearLayout f207o;

    public static void j(HistoryActivity historyActivity, LinearLayout linearLayout, String str) {
        historyActivity.getClass();
        LinearLayout linearLayout2 = new LinearLayout(historyActivity);
        linearLayout2.setOrientation(1);
        linearLayout2.setGravity(17);
        linearLayout2.setPadding(historyActivity.l(16), historyActivity.l(48), historyActivity.l(16), historyActivity.l(48));
        linearLayout2.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        linearLayout2.setBackgroundResource(2131165324);
        ImageView imageView = new ImageView(historyActivity);
        imageView.setImageResource(2131165431);
        imageView.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(historyActivity.l(40), historyActivity.l(40));
        layoutParams.setMargins(0, 0, 0, historyActivity.l(10));
        imageView.setLayoutParams(layoutParams);
        linearLayout2.addView(imageView);
        TextView textView = new TextView(historyActivity);
        textView.setText(str);
        textView.setTextColor(-1);
        textView.setGravity(17);
        textView.setTextSize(13.0f);
        linearLayout2.addView(textView);
        linearLayout.addView(linearLayout2);
    }

    public static void k(HistoryActivity historyActivity, LinearLayout linearLayout, String str, String str2, String str3, boolean z2) {
        historyActivity.getClass();
        LinearLayout linearLayout2 = new LinearLayout(historyActivity);
        linearLayout2.setOrientation(0);
        linearLayout2.setPadding(historyActivity.l(14), historyActivity.l(14), historyActivity.l(14), historyActivity.l(14));
        linearLayout2.setGravity(16);
        linearLayout2.setBackgroundResource(2131165324);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.setMargins(0, 0, 0, historyActivity.l(8));
        linearLayout2.setLayoutParams(layoutParams);
        LinearLayout linearLayout3 = new LinearLayout(historyActivity);
        linearLayout3.setGravity(17);
        linearLayout3.setBackgroundResource(z2 ? 2131165364 : 2131165365);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(historyActivity.l(44), historyActivity.l(44));
        layoutParams2.setMargins(0, 0, historyActivity.l(14), 0);
        linearLayout3.setLayoutParams(layoutParams2);
        ImageView imageView = new ImageView(historyActivity);
        imageView.setImageResource(2131165431);
        imageView.setColorFilter(z2 ? -1395960 : -4160260, PorterDuff.Mode.SRC_IN);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(historyActivity.l(20), historyActivity.l(20)));
        linearLayout3.addView(imageView);
        linearLayout2.addView(linearLayout3);
        LinearLayout linearLayout4 = new LinearLayout(historyActivity);
        linearLayout4.setOrientation(1);
        linearLayout4.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1.0f));
        TextView textView = new TextView(historyActivity);
        textView.setText(str);
        textView.setTextColor(-460545);
        textView.setTextSize(13.0f);
        textView.setTypeface(null, 1);
        textView.setMaxLines(1);
        TextUtils.TruncateAt truncateAt = TextUtils.TruncateAt.END;
        textView.setEllipsize(truncateAt);
        linearLayout4.addView(textView);
        if (str2 != null && !str2.isEmpty()) {
            TextView textView2 = new TextView(historyActivity);
            textView2.setText(str2);
            textView2.setTextColor(-1);
            textView2.setTextSize(11.0f);
            textView2.setMaxLines(1);
            textView2.setEllipsize(truncateAt);
            linearLayout4.addView(textView2);
        }
        if (str3 != null && !str3.isEmpty()) {
            LinearLayout linearLayout5 = new LinearLayout(historyActivity);
            linearLayout5.setOrientation(0);
            linearLayout5.setGravity(16);
            LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-2, -2);
            layoutParams3.setMargins(0, historyActivity.l(3), 0, 0);
            linearLayout5.setLayoutParams(layoutParams3);
            ImageView imageView2 = new ImageView(historyActivity);
            imageView2.setImageResource(2131165413);
            LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(historyActivity.l(11), historyActivity.l(11));
            layoutParams4.setMargins(0, 0, historyActivity.l(4), 0);
            imageView2.setLayoutParams(layoutParams4);
            linearLayout5.addView(imageView2);
            TextView textView3 = new TextView(historyActivity);
            textView3.setText(str3);
            textView3.setTextColor(-1);
            textView3.setTextSize(10.0f);
            linearLayout5.addView(textView3);
            linearLayout4.addView(linearLayout5);
        }
        linearLayout2.addView(linearLayout4);
        linearLayout.addView(linearLayout2);
    }

    public final int l(int i2) {
        return (int) (i2 * getResources().getDisplayMetrics().density);
    }

    public final void m() {
        ProgressBar progressBar = this.f198f;
        int i2 = 0;
        if (progressBar != null) {
            progressBar.setVisibility(0);
        }
        LinearLayout linearLayout = this.d;
        if (linearLayout != null) {
            linearLayout.removeAllViews();
        }
        as asVar = new as(this, i2);
        ExecutorService executorService = this.f195a;
        executorService.execute(asVar);
        ProgressBar progressBar2 = this.f199g;
        if (progressBar2 != null) {
            progressBar2.setVisibility(0);
        }
        LinearLayout linearLayout2 = this.f197e;
        if (linearLayout2 != null) {
            linearLayout2.removeAllViews();
        }
        executorService.execute(new as(this, 1));
    }

    public final void n() {
        LinearLayout linearLayout = this.f206n;
        if (linearLayout != null) {
            linearLayout.setVisibility(0);
        }
        LinearLayout linearLayout2 = this.f207o;
        if (linearLayout2 != null) {
            linearLayout2.setVisibility(8);
        }
        LinearLayout linearLayout3 = this.f200h;
        if (linearLayout3 != null) {
            linearLayout3.setBackgroundResource(2131165382);
        }
        LinearLayout linearLayout4 = this.f201i;
        if (linearLayout4 != null) {
            linearLayout4.setBackgroundResource(0);
        }
        ImageView imageView = this.f202j;
        if (imageView != null) {
            imageView.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
        }
        ImageView imageView2 = this.f203k;
        if (imageView2 != null) {
            imageView2.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
        }
        TextView textView = this.f204l;
        if (textView != null) {
            textView.setTextColor(-1);
            this.f204l.setTypeface(null, 1);
        }
        TextView textView2 = this.f205m;
        if (textView2 != null) {
            textView2.setTextColor(-1);
            this.f205m.setTypeface(null, 0);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        G gV = y.v(this);
        if (gV == null) {
            startActivity(new Intent(this, (Class<?>) LoginActivity.class));
            finish();
            return;
        }
        this.f196c = gV;
        setContentView(2131427360);
        LinearLayout linearLayout = (LinearLayout) findViewById(2131230816);
        if (linearLayout != null) {
            final int i2 = 0;
            linearLayout.setOnClickListener(new View.OnClickListener(this) { // from class: f.ar
                public final /* synthetic */ HistoryActivity b;

                {
                    this.b = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i3 = i2;
                    HistoryActivity historyActivity = this.b;
                    switch (i3) {
                        case 0:
                            int i4 = HistoryActivity.f194p;
                            historyActivity.finish();
                            break;
                        case 1:
                            int i5 = HistoryActivity.f194p;
                            historyActivity.m();
                            break;
                        case 2:
                            int i6 = HistoryActivity.f194p;
                            historyActivity.n();
                            break;
                        default:
                            LinearLayout linearLayout2 = historyActivity.f207o;
                            if (linearLayout2 != null) {
                                linearLayout2.setVisibility(0);
                            }
                            LinearLayout linearLayout3 = historyActivity.f206n;
                            if (linearLayout3 != null) {
                                linearLayout3.setVisibility(8);
                            }
                            LinearLayout linearLayout4 = historyActivity.f201i;
                            if (linearLayout4 != null) {
                                linearLayout4.setBackgroundResource(2131165383);
                            }
                            LinearLayout linearLayout5 = historyActivity.f200h;
                            if (linearLayout5 != null) {
                                linearLayout5.setBackgroundResource(0);
                            }
                            ImageView imageView = historyActivity.f203k;
                            if (imageView != null) {
                                imageView.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
                            }
                            ImageView imageView2 = historyActivity.f202j;
                            if (imageView2 != null) {
                                imageView2.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
                            }
                            TextView textView = historyActivity.f205m;
                            if (textView != null) {
                                textView.setTextColor(-1);
                                historyActivity.f205m.setTypeface(null, 1);
                            }
                            TextView textView2 = historyActivity.f204l;
                            if (textView2 != null) {
                                textView2.setTextColor(-1);
                                historyActivity.f204l.setTypeface(null, 0);
                            }
                            break;
                    }
                }
            });
        }
        LinearLayout linearLayout2 = (LinearLayout) findViewById(2131230851);
        if (linearLayout2 != null) {
            final int i3 = 1;
            linearLayout2.setOnClickListener(new View.OnClickListener(this) { // from class: f.ar
                public final /* synthetic */ HistoryActivity b;

                {
                    this.b = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i4 = i3;
                    HistoryActivity historyActivity = this.b;
                    switch (i4) {
                        case 0:
                            int i5 = HistoryActivity.f194p;
                            historyActivity.finish();
                            break;
                        case 1:
                            int i6 = HistoryActivity.f194p;
                            historyActivity.m();
                            break;
                        case 2:
                            int i7 = HistoryActivity.f194p;
                            historyActivity.n();
                            break;
                        default:
                            LinearLayout linearLayout3 = historyActivity.f207o;
                            if (linearLayout3 != null) {
                                linearLayout3.setVisibility(0);
                            }
                            LinearLayout linearLayout4 = historyActivity.f206n;
                            if (linearLayout4 != null) {
                                linearLayout4.setVisibility(8);
                            }
                            LinearLayout linearLayout5 = historyActivity.f201i;
                            if (linearLayout5 != null) {
                                linearLayout5.setBackgroundResource(2131165383);
                            }
                            LinearLayout linearLayout6 = historyActivity.f200h;
                            if (linearLayout6 != null) {
                                linearLayout6.setBackgroundResource(0);
                            }
                            ImageView imageView = historyActivity.f203k;
                            if (imageView != null) {
                                imageView.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
                            }
                            ImageView imageView2 = historyActivity.f202j;
                            if (imageView2 != null) {
                                imageView2.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
                            }
                            TextView textView = historyActivity.f205m;
                            if (textView != null) {
                                textView.setTextColor(-1);
                                historyActivity.f205m.setTypeface(null, 1);
                            }
                            TextView textView2 = historyActivity.f204l;
                            if (textView2 != null) {
                                textView2.setTextColor(-1);
                                historyActivity.f204l.setTypeface(null, 0);
                            }
                            break;
                    }
                }
            });
        }
        this.f200h = (LinearLayout) findViewById(2131230857);
        this.f201i = (LinearLayout) findViewById(2131230856);
        this.f202j = (ImageView) findViewById(2131230993);
        this.f203k = (ImageView) findViewById(2131230990);
        this.f204l = (TextView) findViewById(2131231251);
        this.f205m = (TextView) findViewById(2131231249);
        this.f206n = (LinearLayout) findViewById(2131231185);
        this.f207o = (LinearLayout) findViewById(2131231184);
        this.d = (LinearLayout) findViewById(2131231286);
        this.f197e = (LinearLayout) findViewById(2131230972);
        this.f198f = (ProgressBar) findViewById(2131231147);
        this.f199g = (ProgressBar) findViewById(2131231145);
        LinearLayout linearLayout3 = this.f200h;
        if (linearLayout3 != null) {
            final int i4 = 2;
            linearLayout3.setOnClickListener(new View.OnClickListener(this) { // from class: f.ar
                public final /* synthetic */ HistoryActivity b;

                {
                    this.b = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i5 = i4;
                    HistoryActivity historyActivity = this.b;
                    switch (i5) {
                        case 0:
                            int i6 = HistoryActivity.f194p;
                            historyActivity.finish();
                            break;
                        case 1:
                            int i7 = HistoryActivity.f194p;
                            historyActivity.m();
                            break;
                        case 2:
                            int i8 = HistoryActivity.f194p;
                            historyActivity.n();
                            break;
                        default:
                            LinearLayout linearLayout4 = historyActivity.f207o;
                            if (linearLayout4 != null) {
                                linearLayout4.setVisibility(0);
                            }
                            LinearLayout linearLayout5 = historyActivity.f206n;
                            if (linearLayout5 != null) {
                                linearLayout5.setVisibility(8);
                            }
                            LinearLayout linearLayout6 = historyActivity.f201i;
                            if (linearLayout6 != null) {
                                linearLayout6.setBackgroundResource(2131165383);
                            }
                            LinearLayout linearLayout7 = historyActivity.f200h;
                            if (linearLayout7 != null) {
                                linearLayout7.setBackgroundResource(0);
                            }
                            ImageView imageView = historyActivity.f203k;
                            if (imageView != null) {
                                imageView.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
                            }
                            ImageView imageView2 = historyActivity.f202j;
                            if (imageView2 != null) {
                                imageView2.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
                            }
                            TextView textView = historyActivity.f205m;
                            if (textView != null) {
                                textView.setTextColor(-1);
                                historyActivity.f205m.setTypeface(null, 1);
                            }
                            TextView textView2 = historyActivity.f204l;
                            if (textView2 != null) {
                                textView2.setTextColor(-1);
                                historyActivity.f204l.setTypeface(null, 0);
                            }
                            break;
                    }
                }
            });
        }
        LinearLayout linearLayout4 = this.f201i;
        if (linearLayout4 != null) {
            final int i5 = 3;
            linearLayout4.setOnClickListener(new View.OnClickListener(this) { // from class: f.ar
                public final /* synthetic */ HistoryActivity b;

                {
                    this.b = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i6 = i5;
                    HistoryActivity historyActivity = this.b;
                    switch (i6) {
                        case 0:
                            int i7 = HistoryActivity.f194p;
                            historyActivity.finish();
                            break;
                        case 1:
                            int i8 = HistoryActivity.f194p;
                            historyActivity.m();
                            break;
                        case 2:
                            int i9 = HistoryActivity.f194p;
                            historyActivity.n();
                            break;
                        default:
                            LinearLayout linearLayout5 = historyActivity.f207o;
                            if (linearLayout5 != null) {
                                linearLayout5.setVisibility(0);
                            }
                            LinearLayout linearLayout6 = historyActivity.f206n;
                            if (linearLayout6 != null) {
                                linearLayout6.setVisibility(8);
                            }
                            LinearLayout linearLayout7 = historyActivity.f201i;
                            if (linearLayout7 != null) {
                                linearLayout7.setBackgroundResource(2131165383);
                            }
                            LinearLayout linearLayout8 = historyActivity.f200h;
                            if (linearLayout8 != null) {
                                linearLayout8.setBackgroundResource(0);
                            }
                            ImageView imageView = historyActivity.f203k;
                            if (imageView != null) {
                                imageView.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
                            }
                            ImageView imageView2 = historyActivity.f202j;
                            if (imageView2 != null) {
                                imageView2.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
                            }
                            TextView textView = historyActivity.f205m;
                            if (textView != null) {
                                textView.setTextColor(-1);
                                historyActivity.f205m.setTypeface(null, 1);
                            }
                            TextView textView2 = historyActivity.f204l;
                            if (textView2 != null) {
                                textView2.setTextColor(-1);
                                historyActivity.f204l.setTypeface(null, 0);
                            }
                            break;
                    }
                }
            });
        }
        n();
        m();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        this.f195a.shutdownNow();
    }
}
