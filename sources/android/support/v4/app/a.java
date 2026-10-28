package android.support.v4.app;

import android.app.Notification;
import android.os.IBinder;
import android.os.Parcel;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class a implements c {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public IBinder f3c;

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.f3c;
    }

    @Override // android.support.v4.app.c
    public final void cancel(String str, int i2, String str2) {
        Parcel parcelObtain = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(c.b);
            parcelObtain.writeString(str);
            parcelObtain.writeInt(i2);
            parcelObtain.writeString(str2);
            this.f3c.transact(2, parcelObtain, null, 1);
        } finally {
            parcelObtain.recycle();
        }
    }

    @Override // android.support.v4.app.c
    public final void cancelAll(String str) {
        Parcel parcelObtain = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(c.b);
            parcelObtain.writeString(str);
            this.f3c.transact(3, parcelObtain, null, 1);
        } finally {
            parcelObtain.recycle();
        }
    }

    @Override // android.support.v4.app.c
    public final void notify(String str, int i2, String str2, Notification notification) {
        Parcel parcelObtain = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(c.b);
            parcelObtain.writeString(str);
            parcelObtain.writeInt(i2);
            parcelObtain.writeString(str2);
            if (notification != null) {
                parcelObtain.writeInt(1);
                notification.writeToParcel(parcelObtain, 0);
            } else {
                parcelObtain.writeInt(0);
            }
            this.f3c.transact(1, parcelObtain, null, 1);
        } finally {
            parcelObtain.recycle();
        }
    }
}
