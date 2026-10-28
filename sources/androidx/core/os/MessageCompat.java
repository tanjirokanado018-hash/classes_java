package androidx.core.os;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Message;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class MessageCompat {
    private static boolean sTryIsAsynchronous = true;
    private static boolean sTrySetAsynchronous = true;

    @RequiresApi(22)
    public static class Api22Impl {
        private Api22Impl() {
        }

        @DoNotInline
        public static boolean isAsynchronous(Message message) {
            return message.isAsynchronous();
        }

        @DoNotInline
        public static void setAsynchronous(Message message, boolean z2) {
            message.setAsynchronous(z2);
        }
    }

    private MessageCompat() {
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0015, code lost:
    
        androidx.core.os.MessageCompat.sTryIsAsynchronous = false;
     */
    @android.annotation.SuppressLint({"NewApi"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static boolean isAsynchronous(@androidx.annotation.NonNull android.os.Message r2) {
        /*
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 22
            if (r0 < r1) goto Lb
            boolean r2 = androidx.core.os.MessageCompat.Api22Impl.isAsynchronous(r2)
            return r2
        Lb:
            boolean r0 = androidx.core.os.MessageCompat.sTryIsAsynchronous
            r1 = 0
            if (r0 == 0) goto L17
            boolean r2 = androidx.core.os.MessageCompat.Api22Impl.isAsynchronous(r2)     // Catch: java.lang.NoSuchMethodError -> L15
            return r2
        L15:
            androidx.core.os.MessageCompat.sTryIsAsynchronous = r1
        L17:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.os.MessageCompat.isAsynchronous(android.os.Message):boolean");
    }

    @SuppressLint({"NewApi"})
    public static void setAsynchronous(@NonNull Message message, boolean z2) {
        if (Build.VERSION.SDK_INT >= 22) {
            Api22Impl.setAsynchronous(message, z2);
        } else if (sTrySetAsynchronous) {
            try {
                Api22Impl.setAsynchronous(message, z2);
            } catch (NoSuchMethodError unused) {
                sTrySetAsynchronous = false;
            }
        }
    }
}
