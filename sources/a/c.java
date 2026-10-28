package a;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.os.ResultReceiver;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class c implements Parcelable.Creator {
    @Override // android.os.Parcelable.Creator
    public final Object createFromParcel(Parcel parcel) {
        return new ResultReceiver(parcel);
    }

    @Override // android.os.Parcelable.Creator
    public final Object[] newArray(int i2) {
        return new ResultReceiver[i2];
    }
}
