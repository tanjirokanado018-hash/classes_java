package androidx.profileinstaller;

import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import java.io.File;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
class BenchmarkOperation {

    @RequiresApi(api = 21)
    public static class Api21ContextHelper {
        private Api21ContextHelper() {
        }

        public static File getCodeCacheDir(Context context) {
            return context.getCodeCacheDir();
        }
    }

    @RequiresApi(api = 24)
    public static class Api24ContextHelper {
        private Api24ContextHelper() {
        }

        public static File getDeviceProtectedCodeCacheDir(Context context) {
            return context.createDeviceProtectedStorageContext().getCodeCacheDir();
        }
    }

    private BenchmarkOperation() {
    }

    public static boolean deleteFilesRecursively(File file) {
        if (!file.isDirectory()) {
            file.delete();
            return true;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null) {
            return false;
        }
        boolean z2 = true;
        for (File file2 : fileArrListFiles) {
            z2 = deleteFilesRecursively(file2) && z2;
        }
        return z2;
    }

    public static void dropShaderCache(@NonNull Context context, @NonNull ProfileInstallReceiver.ResultDiagnostics resultDiagnostics) {
        File codeCacheDir;
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 24) {
            codeCacheDir = Api24ContextHelper.getDeviceProtectedCodeCacheDir(context);
        } else {
            codeCacheDir = i2 >= 23 ? Api21ContextHelper.getCodeCacheDir(context) : context.getCacheDir();
        }
        resultDiagnostics.onResultReceived(deleteFilesRecursively(codeCacheDir) ? 14 : 15, null);
    }
}
