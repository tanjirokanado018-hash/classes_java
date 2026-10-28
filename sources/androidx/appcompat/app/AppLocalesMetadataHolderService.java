package androidx.appcompat.app;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class AppLocalesMetadataHolderService extends Service {

    @RequiresApi(24)
    public static class Api24Impl {
        private Api24Impl() {
        }

        @DoNotInline
        public static int getDisabledComponentFlag() {
            return 512;
        }
    }

    @NonNull
    public static ServiceInfo getServiceInfo(@NonNull Context context) {
        return context.getPackageManager().getServiceInfo(new ComponentName(context, (Class<?>) AppLocalesMetadataHolderService.class), Build.VERSION.SDK_INT >= 24 ? Api24Impl.getDisabledComponentFlag() | 128 : 640);
    }

    @Override // android.app.Service
    @NonNull
    public IBinder onBind(@NonNull Intent intent) {
        throw new UnsupportedOperationException();
    }
}
