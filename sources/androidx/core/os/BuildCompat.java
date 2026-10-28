package androidx.core.os;

import android.os.Build;
import android.os.ext.SdkExtensions;
import androidx.annotation.ChecksSdkIntAtLeast;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class BuildCompat {

    @ChecksSdkIntAtLeast(extension = 1000000)
    public static final int AD_SERVICES_EXTENSION_INT;
    public static final BuildCompat INSTANCE = new BuildCompat();

    @ChecksSdkIntAtLeast(extension = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_BIAS)
    public static final int R_EXTENSION_INT;

    @ChecksSdkIntAtLeast(extension = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_WIDTH_DEFAULT)
    public static final int S_EXTENSION_INT;

    @ChecksSdkIntAtLeast(extension = 33)
    public static final int T_EXTENSION_INT;

    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_BIAS)
    public static final class Api30Impl {
        public static final Api30Impl INSTANCE = new Api30Impl();

        private Api30Impl() {
        }

        @DoNotInline
        public final int getExtensionVersion(int i2) {
            return SdkExtensions.getExtensionVersion(i2);
        }
    }

    @Retention(RetentionPolicy.CLASS)
    public @interface PrereleaseSdkCheck {
    }

    static {
        int i2 = Build.VERSION.SDK_INT;
        R_EXTENSION_INT = i2 >= 30 ? Api30Impl.INSTANCE.getExtensionVersion(30) : 0;
        S_EXTENSION_INT = i2 >= 30 ? Api30Impl.INSTANCE.getExtensionVersion(31) : 0;
        T_EXTENSION_INT = i2 >= 30 ? Api30Impl.INSTANCE.getExtensionVersion(33) : 0;
        AD_SERVICES_EXTENSION_INT = i2 >= 30 ? Api30Impl.INSTANCE.getExtensionVersion(1000000) : 0;
    }

    private BuildCompat() {
    }

    @ChecksSdkIntAtLeast(api = 24)
    public static final boolean isAtLeastN() {
        return Build.VERSION.SDK_INT >= 24;
    }

    @ChecksSdkIntAtLeast(api = 25)
    public static final boolean isAtLeastNMR1() {
        return Build.VERSION.SDK_INT >= 25;
    }

    @ChecksSdkIntAtLeast(api = 26)
    public static final boolean isAtLeastO() {
        return Build.VERSION.SDK_INT >= 26;
    }

    @ChecksSdkIntAtLeast(api = 27)
    public static final boolean isAtLeastOMR1() {
        return Build.VERSION.SDK_INT >= 27;
    }

    @ChecksSdkIntAtLeast(api = 28)
    public static final boolean isAtLeastP() {
        return Build.VERSION.SDK_INT >= 28;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @VisibleForTesting
    public static final boolean isAtLeastPreReleaseCodename(String codename, String buildCodename) {
        k.g(codename, "codename");
        k.g(buildCodename, "buildCodename");
        if (k.a("REL", buildCodename)) {
            return false;
        }
        Locale locale = Locale.ROOT;
        String upperCase = buildCodename.toUpperCase(locale);
        k.f(upperCase, "this as java.lang.String).toUpperCase(Locale.ROOT)");
        String upperCase2 = codename.toUpperCase(locale);
        k.f(upperCase2, "this as java.lang.String).toUpperCase(Locale.ROOT)");
        return upperCase.compareTo(upperCase2) >= 0;
    }

    @ChecksSdkIntAtLeast(api = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS)
    public static final boolean isAtLeastQ() {
        return Build.VERSION.SDK_INT >= 29;
    }

    @ChecksSdkIntAtLeast(api = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_BIAS)
    public static final boolean isAtLeastR() {
        return Build.VERSION.SDK_INT >= 30;
    }

    @ChecksSdkIntAtLeast(api = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_WIDTH_DEFAULT, codename = "S")
    public static final boolean isAtLeastS() {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 < 31) {
            if (i2 >= 30) {
                String CODENAME = Build.VERSION.CODENAME;
                k.f(CODENAME, "CODENAME");
                if (isAtLeastPreReleaseCodename("S", CODENAME)) {
                }
            }
            return false;
        }
        return true;
    }

    @ChecksSdkIntAtLeast(api = 32, codename = "Sv2")
    public static final boolean isAtLeastSv2() {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 < 32) {
            if (i2 >= 31) {
                String CODENAME = Build.VERSION.CODENAME;
                k.f(CODENAME, "CODENAME");
                if (isAtLeastPreReleaseCodename("Sv2", CODENAME)) {
                }
            }
            return false;
        }
        return true;
    }

    @ChecksSdkIntAtLeast(api = 33, codename = "Tiramisu")
    public static final boolean isAtLeastT() {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 < 33) {
            if (i2 >= 32) {
                String CODENAME = Build.VERSION.CODENAME;
                k.f(CODENAME, "CODENAME");
                if (isAtLeastPreReleaseCodename("Tiramisu", CODENAME)) {
                }
            }
            return false;
        }
        return true;
    }

    @ChecksSdkIntAtLeast(api = 34, codename = "UpsideDownCake")
    public static final boolean isAtLeastU() {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 < 34) {
            if (i2 >= 33) {
                String CODENAME = Build.VERSION.CODENAME;
                k.f(CODENAME, "CODENAME");
                if (isAtLeastPreReleaseCodename("UpsideDownCake", CODENAME)) {
                }
            }
            return false;
        }
        return true;
    }

    @ChecksSdkIntAtLeast(codename = "VanillaIceCream")
    @PrereleaseSdkCheck
    public static final boolean isAtLeastV() {
        if (Build.VERSION.SDK_INT >= 34) {
            String CODENAME = Build.VERSION.CODENAME;
            k.f(CODENAME, "CODENAME");
            if (isAtLeastPreReleaseCodename("VanillaIceCream", CODENAME)) {
                return true;
            }
        }
        return false;
    }
}
