package androidx.core.os;

import android.content.res.Configuration;
import android.os.Build;
import android.os.LocaleList;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ConfigurationCompat {

    @RequiresApi(24)
    public static class Api24Impl {
        private Api24Impl() {
        }

        @DoNotInline
        public static LocaleList getLocales(Configuration configuration) {
            return configuration.getLocales();
        }

        @DoNotInline
        public static void setLocales(@NonNull Configuration configuration, @NonNull LocaleListCompat localeListCompat) {
            configuration.setLocales((LocaleList) localeListCompat.unwrap());
        }
    }

    private ConfigurationCompat() {
    }

    @NonNull
    public static LocaleListCompat getLocales(@NonNull Configuration configuration) {
        return Build.VERSION.SDK_INT >= 24 ? LocaleListCompat.wrap(Api24Impl.getLocales(configuration)) : LocaleListCompat.create(configuration.locale);
    }

    public static void setLocales(@NonNull Configuration configuration, @NonNull LocaleListCompat localeListCompat) {
        if (Build.VERSION.SDK_INT >= 24) {
            Api24Impl.setLocales(configuration, localeListCompat);
        } else {
            if (localeListCompat.isEmpty()) {
                return;
            }
            configuration.setLocale(localeListCompat.get(0));
        }
    }
}
