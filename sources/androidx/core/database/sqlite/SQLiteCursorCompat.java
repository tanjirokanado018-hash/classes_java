package androidx.core.database.sqlite;

import android.database.sqlite.SQLiteCursor;
import android.os.Build;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class SQLiteCursorCompat {

    @RequiresApi(28)
    public static class Api28Impl {
        private Api28Impl() {
        }

        @DoNotInline
        public static void setFillWindowForwardOnly(SQLiteCursor sQLiteCursor, boolean z2) {
            sQLiteCursor.setFillWindowForwardOnly(z2);
        }
    }

    private SQLiteCursorCompat() {
    }

    public static void setFillWindowForwardOnly(@NonNull SQLiteCursor sQLiteCursor, boolean z2) {
        if (Build.VERSION.SDK_INT >= 28) {
            Api28Impl.setFillWindowForwardOnly(sQLiteCursor, z2);
        }
    }
}
