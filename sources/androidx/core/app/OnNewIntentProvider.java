package androidx.core.app;

import android.content.Intent;
import androidx.core.util.Consumer;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public interface OnNewIntentProvider {
    void addOnNewIntentListener(Consumer<Intent> consumer);

    void removeOnNewIntentListener(Consumer<Intent> consumer);
}
