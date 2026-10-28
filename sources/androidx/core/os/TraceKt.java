package androidx.core.os;

import android.os.Trace;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class TraceKt {
    public static final <T> T trace(String str, p.a aVar) {
        Trace.beginSection(str);
        try {
            return (T) aVar.invoke();
        } finally {
            Trace.endSection();
        }
    }
}
