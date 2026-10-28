package androidx.core.app;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Binder;
import android.os.Build;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class AppOpsManagerCompat {
    public static final int MODE_ALLOWED = 0;
    public static final int MODE_DEFAULT = 3;
    public static final int MODE_ERRORED = 2;
    public static final int MODE_IGNORED = 1;

    @RequiresApi(23)
    public static class Api23Impl {
        private Api23Impl() {
        }

        @DoNotInline
        public static <T> T getSystemService(Context context, Class<T> cls) {
            return (T) context.getSystemService(cls);
        }

        @DoNotInline
        public static int noteProxyOp(AppOpsManager appOpsManager, String str, String str2) {
            return appOpsManager.noteProxyOp(str, str2);
        }

        @DoNotInline
        public static int noteProxyOpNoThrow(AppOpsManager appOpsManager, String str, String str2) {
            return appOpsManager.noteProxyOpNoThrow(str, str2);
        }

        @DoNotInline
        public static String permissionToOp(String str) {
            return AppOpsManager.permissionToOp(str);
        }
    }

    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS)
    public static class Api29Impl {
        private Api29Impl() {
        }

        @DoNotInline
        public static int checkOpNoThrow(@Nullable AppOpsManager appOpsManager, @NonNull String str, int i2, @NonNull String str2) {
            if (appOpsManager == null) {
                return 1;
            }
            return appOpsManager.checkOpNoThrow(str, i2, str2);
        }

        @NonNull
        @DoNotInline
        public static String getOpPackageName(@NonNull Context context) {
            return context.getOpPackageName();
        }

        @Nullable
        @DoNotInline
        public static AppOpsManager getSystemService(@NonNull Context context) {
            return (AppOpsManager) context.getSystemService(AppOpsManager.class);
        }
    }

    private AppOpsManagerCompat() {
    }

    public static int checkOrNoteProxyOp(@NonNull Context context, int i2, @NonNull String str, @NonNull String str2) {
        if (Build.VERSION.SDK_INT < 29) {
            return noteProxyOpNoThrow(context, str, str2);
        }
        AppOpsManager systemService = Api29Impl.getSystemService(context);
        int iCheckOpNoThrow = Api29Impl.checkOpNoThrow(systemService, str, Binder.getCallingUid(), str2);
        return iCheckOpNoThrow != 0 ? iCheckOpNoThrow : Api29Impl.checkOpNoThrow(systemService, str, i2, Api29Impl.getOpPackageName(context));
    }

    public static int noteOp(@NonNull Context context, @NonNull String str, int i2, @NonNull String str2) {
        return ((AppOpsManager) context.getSystemService("appops")).noteOp(str, i2, str2);
    }

    public static int noteOpNoThrow(@NonNull Context context, @NonNull String str, int i2, @NonNull String str2) {
        return ((AppOpsManager) context.getSystemService("appops")).noteOpNoThrow(str, i2, str2);
    }

    public static int noteProxyOp(@NonNull Context context, @NonNull String str, @NonNull String str2) {
        if (Build.VERSION.SDK_INT >= 23) {
            return Api23Impl.noteProxyOp((AppOpsManager) Api23Impl.getSystemService(context, AppOpsManager.class), str, str2);
        }
        return 1;
    }

    public static int noteProxyOpNoThrow(@NonNull Context context, @NonNull String str, @NonNull String str2) {
        if (Build.VERSION.SDK_INT >= 23) {
            return Api23Impl.noteProxyOpNoThrow((AppOpsManager) Api23Impl.getSystemService(context, AppOpsManager.class), str, str2);
        }
        return 1;
    }

    @Nullable
    public static String permissionToOp(@NonNull String str) {
        if (Build.VERSION.SDK_INT >= 23) {
            return Api23Impl.permissionToOp(str);
        }
        return null;
    }
}
