package android.support.v4.os;

import a.a;
import a.b;
import a.c;
import a.d;
import android.annotation.SuppressLint;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@SuppressLint({"BanParcelableUsage"})
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class ResultReceiver implements Parcelable {
    public static final Parcelable.Creator<ResultReceiver> CREATOR = new c();
    public b b;

    public ResultReceiver(Parcel parcel) {
        b bVar;
        IBinder strongBinder = parcel.readStrongBinder();
        int i2 = d.d;
        if (strongBinder == null) {
            bVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface(b.f1a);
            if (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof b)) {
                a aVar = new a();
                aVar.f0c = strongBinder;
                bVar = aVar;
            } else {
                bVar = (b) iInterfaceQueryLocalInterface;
            }
        }
        this.b = bVar;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        synchronized (this) {
            try {
                if (this.b == null) {
                    this.b = new d(this);
                }
                parcel.writeStrongBinder(this.b.asBinder());
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
