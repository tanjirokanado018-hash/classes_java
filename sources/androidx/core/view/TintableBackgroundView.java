package androidx.core.view;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import androidx.annotation.Nullable;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public interface TintableBackgroundView {
    @Nullable
    ColorStateList getSupportBackgroundTintList();

    @Nullable
    PorterDuff.Mode getSupportBackgroundTintMode();

    void setSupportBackgroundTintList(@Nullable ColorStateList colorStateList);

    void setSupportBackgroundTintMode(@Nullable PorterDuff.Mode mode);
}
