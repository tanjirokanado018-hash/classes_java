package androidx.appcompat.app;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.job.JobWorkItem;
import android.content.Intent;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract /* synthetic */ class d {
    public static /* bridge */ /* synthetic */ NotificationChannel g(Object obj) {
        return (NotificationChannel) obj;
    }

    public static /* bridge */ /* synthetic */ NotificationChannelGroup h(Object obj) {
        return (NotificationChannelGroup) obj;
    }

    public static /* synthetic */ JobWorkItem j(Intent intent) {
        return new JobWorkItem(intent);
    }
}
