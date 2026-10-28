package androidx.core.os;

import android.os.Parcel;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@Deprecated
public interface ParcelableCompatCreatorCallbacks<T> {
    T createFromParcel(Parcel parcel, ClassLoader classLoader);

    T[] newArray(int i2);
}
