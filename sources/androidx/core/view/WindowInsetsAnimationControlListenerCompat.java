package androidx.core.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public interface WindowInsetsAnimationControlListenerCompat {
    void onCancelled(@Nullable WindowInsetsAnimationControllerCompat windowInsetsAnimationControllerCompat);

    void onFinished(@NonNull WindowInsetsAnimationControllerCompat windowInsetsAnimationControllerCompat);

    void onReady(@NonNull WindowInsetsAnimationControllerCompat windowInsetsAnimationControllerCompat, int i2);
}
