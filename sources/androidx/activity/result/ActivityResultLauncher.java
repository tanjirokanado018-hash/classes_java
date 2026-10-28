package androidx.activity.result;

import android.annotation.SuppressLint;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class ActivityResultLauncher<I> {
    @NonNull
    public abstract ActivityResultContract<I, ?> getContract();

    public void launch(@SuppressLint({"UnknownNullness"}) I i2) {
        launch(i2, null);
    }

    public abstract void launch(@SuppressLint({"UnknownNullness"}) I i2, @Nullable ActivityOptionsCompat activityOptionsCompat);

    @MainThread
    public abstract void unregister();
}
