package a;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.support.v4.os.ResultReceiver;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class d extends Binder implements b {
    public static final /* synthetic */ int d = 0;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ ResultReceiver f2c;

    public d(ResultReceiver resultReceiver) {
        this.f2c = resultReceiver;
        attachInterface(this, b.f1a);
    }

    @Override // android.os.Binder
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public final boolean onTransact(int i2, Parcel parcel, Parcel parcel2, int i3) {
        String str = b.f1a;
        if (i2 >= 1 && i2 <= 16777215) {
            parcel.enforceInterface(str);
        }
        if (i2 == 1598968902) {
            parcel2.writeString(str);
            return true;
        }
        if (i2 != 1) {
            return super.onTransact(i2, parcel, parcel2, i3);
        }
        parcel.readInt();
        this.f2c.getClass();
        return true;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this;
    }
}
