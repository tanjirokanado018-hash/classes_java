package f;

import android.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import java.io.InputStream;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class I {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final J f300a = new J(1124073471, 1560281087, 905969663, 1124073471);

    /* JADX WARN: Code duplicated, block: B:31:0x012b A[Catch: Exception -> 0x012f, TRY_LEAVE, TryCatch #0 {Exception -> 0x012f, blocks: (B:28:0x00e6, B:30:0x00f9, B:31:0x012b), top: B:38:0x00e6 }] */
    public static void a(Activity activity, J j2) {
        J j3;
        ViewGroup viewGroup = (ViewGroup) activity.getWindow().getDecorView().findViewById(R.id.content);
        if (viewGroup == null || viewGroup.getChildCount() == 0) {
            return;
        }
        View childAt = viewGroup.getChildAt(0);
        float f2 = activity.getResources().getDisplayMetrics().density;
        int iMax = Math.max(1, (int) f2);
        int i2 = (int) (12.0f * f2);
        int i3 = (int) (16.0f * f2);
        int i4 = (int) (20.0f * f2);
        int i5 = (int) (24.0f * f2);
        if (w.y.q(activity) == -2) {
            String string = activity.getSharedPreferences("theme_prefs", 0).getString("custom_bg_uri", null);
            if (string == null) {
                b(childAt);
            } else {
                try {
                    InputStream inputStreamOpenInputStream = activity.getContentResolver().openInputStream(Uri.parse(string));
                    if (inputStreamOpenInputStream == null) {
                        b(childAt);
                    } else {
                        Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpenInputStream);
                        inputStreamOpenInputStream.close();
                        if (bitmapDecodeStream == null) {
                            b(childAt);
                        } else {
                            DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
                            BitmapDrawable bitmapDrawable = new BitmapDrawable(activity.getResources(), Bitmap.createScaledBitmap(bitmapDecodeStream, displayMetrics.widthPixels, displayMetrics.heightPixels, true));
                            bitmapDrawable.setAlpha(220);
                            childAt.setBackground(new LayerDrawable(new Drawable[]{new ColorDrawable(-16117986), bitmapDrawable}));
                        }
                    }
                } catch (Exception unused) {
                    b(childAt);
                }
            }
            j3 = f300a;
        } else if (j2.f305f) {
            int iQ = w.y.q(activity);
            int[] iArr = {2131165536, 2131165537};
            if (iQ < 0 || iQ >= 2) {
                b(childAt);
                j3 = j2;
            } else {
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 1;
                    Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(activity.getResources(), iArr[iQ], options);
                    if (bitmapDecodeResource != null) {
                        DisplayMetrics displayMetrics2 = activity.getResources().getDisplayMetrics();
                        BitmapDrawable bitmapDrawable2 = new BitmapDrawable(activity.getResources(), Bitmap.createScaledBitmap(bitmapDecodeResource, displayMetrics2.widthPixels, displayMetrics2.heightPixels, true));
                        bitmapDrawable2.setAlpha(220);
                        childAt.setBackground(new LayerDrawable(new Drawable[]{new ColorDrawable(-16117986), bitmapDrawable2}));
                    } else {
                        b(childAt);
                    }
                } catch (Exception unused2) {
                    b(childAt);
                }
                j3 = j2;
            }
        } else {
            b(childAt);
            j3 = new J(-15063755, -14535606, -15986144, -16117988);
        }
        d(childAt, j3, iMax, i2, i3, i4, i5, f2);
    }

    public static void b(View view) {
        view.setBackground(new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{-14999488, -16117986}));
    }

    public static GradientDrawable c(int i2, int i3, int i4, int i5) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(0);
        gradientDrawable.setColor(i2);
        gradientDrawable.setCornerRadius(i5);
        if (i3 != 0 && i4 > 0) {
            gradientDrawable.setStroke(i4, i3);
        }
        return gradientDrawable;
    }

    public static void d(View view, J j2, int i2, int i3, int i4, int i5, int i6, float f2) {
        GradientDrawable gradientDrawableC;
        Object tag = view.getTag();
        String str = tag instanceof String ? (String) tag : "";
        str.getClass();
        switch (str) {
            case "theme_tab_row":
                view.setBackground(c(j2.f304e, j2.b, i2, i6));
                break;
            case "theme_circle":
                int i7 = j2.f303c;
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setShape(1);
                gradientDrawable.setColor(i7);
                int i8 = j2.b;
                if (i8 != 0 && i2 > 0) {
                    gradientDrawable.setStroke(i2, i8);
                }
                view.setBackground(gradientDrawable);
                break;
            case "theme_card_inner":
                view.setBackground(c(j2.f303c, 0, 0, i3));
                break;
            case "theme_card":
                gradientDrawableC = c(j2.f302a, j2.b, i2, i5);
                view.setBackground(gradientDrawableC);
                break;
            case "theme_game_card":
                gradientDrawableC = new GradientDrawable();
                gradientDrawableC.setShape(0);
                gradientDrawableC.setColor(j2.f302a);
                gradientDrawableC.setCornerRadius(i5);
                gradientDrawableC.setStroke(i2, j2.b);
                view.setBackground(gradientDrawableC);
                break;
            default:
                break;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i9 = 0; i9 < viewGroup.getChildCount(); i9++) {
                d(viewGroup.getChildAt(i9), j2, i2, i3, i4, i5, i6, f2);
            }
        }
    }
}
