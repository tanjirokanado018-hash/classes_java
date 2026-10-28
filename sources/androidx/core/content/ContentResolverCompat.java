package androidx.core.content;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.OperationCanceledException;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ContentResolverCompat {
    private ContentResolverCompat() {
    }

    @Nullable
    public static Cursor query(@NonNull ContentResolver contentResolver, @NonNull Uri uri, @Nullable String[] strArr, @Nullable String str, @Nullable String[] strArr2, @Nullable String str2, @Nullable CancellationSignal cancellationSignal) throws Exception {
        try {
            return contentResolver.query(uri, strArr, str, strArr2, str2, cancellationSignal);
        } catch (Exception e2) {
            if (e2 instanceof OperationCanceledException) {
                throw new androidx.core.os.OperationCanceledException();
            }
            throw e2;
        }
    }

    @Nullable
    @Deprecated
    public static Cursor query(@NonNull ContentResolver contentResolver, @NonNull Uri uri, @Nullable String[] strArr, @Nullable String str, @Nullable String[] strArr2, @Nullable String str2, @Nullable androidx.core.os.CancellationSignal cancellationSignal) {
        return query(contentResolver, uri, strArr, str, strArr2, str2, cancellationSignal != null ? (CancellationSignal) cancellationSignal.getCancellationSignalObject() : null);
    }
}
