package androidx.core.service.quicksettings;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.PendingIntentCompat;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class PendingIntentActivityWrapper {
    private final Context mContext;
    private final int mFlags;

    @NonNull
    private final Intent mIntent;
    private final boolean mIsMutable;

    @Nullable
    private final Bundle mOptions;

    @Nullable
    private final PendingIntent mPendingIntent;
    private final int mRequestCode;

    public PendingIntentActivityWrapper(@NonNull Context context, int i2, @NonNull Intent intent, int i3, @Nullable Bundle bundle, boolean z2) {
        this.mContext = context;
        this.mRequestCode = i2;
        this.mIntent = intent;
        this.mFlags = i3;
        this.mOptions = bundle;
        this.mIsMutable = z2;
        this.mPendingIntent = createPendingIntent();
    }

    @Nullable
    private PendingIntent createPendingIntent() {
        Bundle bundle = this.mOptions;
        Context context = this.mContext;
        int i2 = this.mRequestCode;
        Intent intent = this.mIntent;
        int i3 = this.mFlags;
        return bundle == null ? PendingIntentCompat.getActivity(context, i2, intent, i3, this.mIsMutable) : PendingIntentCompat.getActivity(context, i2, intent, i3, bundle, this.mIsMutable);
    }

    @NonNull
    public Context getContext() {
        return this.mContext;
    }

    public int getFlags() {
        return this.mFlags;
    }

    @NonNull
    public Intent getIntent() {
        return this.mIntent;
    }

    @NonNull
    public Bundle getOptions() {
        return this.mOptions;
    }

    @Nullable
    public PendingIntent getPendingIntent() {
        return this.mPendingIntent;
    }

    public int getRequestCode() {
        return this.mRequestCode;
    }

    public boolean isMutable() {
        return this.mIsMutable;
    }

    public PendingIntentActivityWrapper(@NonNull Context context, int i2, @NonNull Intent intent, int i3, boolean z2) {
        this(context, i2, intent, i3, null, z2);
    }
}
