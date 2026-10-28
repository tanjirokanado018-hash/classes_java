package android.support.v4.app;

import android.app.Notification;
import android.os.IInterface;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public interface c extends IInterface {
    public static final String b = "android$support$v4$app$INotificationSideChannel".replace('$', '.');

    void cancel(String str, int i2, String str2);

    void cancelAll(String str);

    void notify(String str, int i2, String str2, Notification notification);
}
