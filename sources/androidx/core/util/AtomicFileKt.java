package androidx.core.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import kotlin.jvm.internal.k;
import p.l;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class AtomicFileKt {
    public static final byte[] readBytes(android.util.AtomicFile atomicFile) {
        return atomicFile.readFully();
    }

    public static final String readText(android.util.AtomicFile atomicFile, Charset charset) {
        return new String(atomicFile.readFully(), charset);
    }

    public static /* synthetic */ String readText$default(android.util.AtomicFile atomicFile, Charset charset, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            charset = v.a.f515a;
        }
        return readText(atomicFile, charset);
    }

    public static final void tryWrite(android.util.AtomicFile atomicFile, l lVar) throws IOException {
        FileOutputStream fileOutputStreamStartWrite = atomicFile.startWrite();
        try {
            lVar.invoke(fileOutputStreamStartWrite);
            atomicFile.finishWrite(fileOutputStreamStartWrite);
        } catch (Throwable th) {
            atomicFile.failWrite(fileOutputStreamStartWrite);
            throw th;
        }
    }

    public static final void writeBytes(android.util.AtomicFile atomicFile, byte[] bArr) throws IOException {
        FileOutputStream fileOutputStreamStartWrite = atomicFile.startWrite();
        try {
            fileOutputStreamStartWrite.write(bArr);
            atomicFile.finishWrite(fileOutputStreamStartWrite);
        } catch (Throwable th) {
            atomicFile.failWrite(fileOutputStreamStartWrite);
            throw th;
        }
    }

    public static final void writeText(android.util.AtomicFile atomicFile, String str, Charset charset) throws IOException {
        byte[] bytes = str.getBytes(charset);
        k.f(bytes, "this as java.lang.String).getBytes(charset)");
        writeBytes(atomicFile, bytes);
    }

    public static /* synthetic */ void writeText$default(android.util.AtomicFile atomicFile, String str, Charset charset, int i2, Object obj) throws IOException {
        if ((i2 & 2) != 0) {
            charset = v.a.f515a;
        }
        writeText(atomicFile, str, charset);
    }
}
