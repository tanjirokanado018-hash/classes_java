package androidx.core.app.unusedapprestrictions;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY})
public interface IUnusedAppRestrictionsBackportService extends IInterface {
    public static final String DESCRIPTOR = "androidx$core$app$unusedapprestrictions$IUnusedAppRestrictionsBackportService".replace('$', '.');

    public static class Default implements IUnusedAppRestrictionsBackportService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportService
        public void isPermissionRevocationEnabledForApp(IUnusedAppRestrictionsBackportCallback iUnusedAppRestrictionsBackportCallback) {
        }
    }

    public static abstract class Stub extends Binder implements IUnusedAppRestrictionsBackportService {
        static final int TRANSACTION_isPermissionRevocationEnabledForApp = 1;

        public static class Proxy implements IUnusedAppRestrictionsBackportService {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUnusedAppRestrictionsBackportService.DESCRIPTOR;
            }

            @Override // androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportService
            public void isPermissionRevocationEnabledForApp(IUnusedAppRestrictionsBackportCallback iUnusedAppRestrictionsBackportCallback) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUnusedAppRestrictionsBackportService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iUnusedAppRestrictionsBackportCallback);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IUnusedAppRestrictionsBackportService.DESCRIPTOR);
        }

        public static IUnusedAppRestrictionsBackportService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IUnusedAppRestrictionsBackportService.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IUnusedAppRestrictionsBackportService)) ? new Proxy(iBinder) : (IUnusedAppRestrictionsBackportService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i2, Parcel parcel, Parcel parcel2, int i3) {
            String str = IUnusedAppRestrictionsBackportService.DESCRIPTOR;
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
            isPermissionRevocationEnabledForApp(IUnusedAppRestrictionsBackportCallback.Stub.asInterface(parcel.readStrongBinder()));
            return true;
        }
    }

    void isPermissionRevocationEnabledForApp(IUnusedAppRestrictionsBackportCallback iUnusedAppRestrictionsBackportCallback);
}
