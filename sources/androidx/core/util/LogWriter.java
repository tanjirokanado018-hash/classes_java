package androidx.core.util;

import androidx.annotation.RestrictTo;
import java.io.Writer;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
@Deprecated
public class LogWriter extends Writer {
    private StringBuilder mBuilder = new StringBuilder(128);
    private final String mTag;

    public LogWriter(String str) {
        this.mTag = str;
    }

    private void flushBuilder() {
        if (this.mBuilder.length() > 0) {
            this.mBuilder.toString();
            StringBuilder sb = this.mBuilder;
            sb.delete(0, sb.length());
        }
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        flushBuilder();
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() {
        flushBuilder();
    }

    @Override // java.io.Writer
    public void write(char[] cArr, int i2, int i3) {
        for (int i4 = 0; i4 < i3; i4++) {
            char c2 = cArr[i2 + i4];
            if (c2 == '\n') {
                flushBuilder();
            } else {
                this.mBuilder.append(c2);
            }
        }
    }
}
