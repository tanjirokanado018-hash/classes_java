package androidx.startup;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY})
public final class StartupException extends RuntimeException {
    public StartupException(@NonNull String str) {
        super(str);
    }

    public StartupException(@NonNull String str, @NonNull Throwable th) {
        super(str, th);
    }

    public StartupException(@NonNull Throwable th) {
        super(th);
    }
}
