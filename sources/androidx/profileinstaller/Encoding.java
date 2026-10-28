package androidx.profileinstaller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RequiresApi(19)
class Encoding {
    static final int SIZEOF_BYTE = 8;
    static final int UINT_16_SIZE = 2;
    static final int UINT_32_SIZE = 4;
    static final int UINT_8_SIZE = 1;

    private Encoding() {
    }

    public static int bitsToBytes(int i2) {
        return ((i2 + 7) & (-8)) / 8;
    }

    public static byte[] compress(@NonNull byte[] bArr) {
        Deflater deflater = new Deflater(1);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(byteArrayOutputStream, deflater);
            try {
                deflaterOutputStream.write(bArr);
                deflaterOutputStream.close();
                deflater.end();
                return byteArrayOutputStream.toByteArray();
            } catch (Throwable th) {
                try {
                    deflaterOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (Throwable th3) {
            deflater.end();
            throw th3;
        }
    }

    @NonNull
    public static RuntimeException error(@Nullable String str) {
        return new IllegalStateException(str);
    }

    @NonNull
    public static byte[] read(@NonNull InputStream inputStream, int i2) throws IOException {
        byte[] bArr = new byte[i2];
        int i3 = 0;
        while (i3 < i2) {
            int i4 = inputStream.read(bArr, i3, i2 - i3);
            if (i4 < 0) {
                throw error(androidx.appcompat.graphics.drawable.a.c("Not enough bytes to read: ", i2));
            }
            i3 += i4;
        }
        return bArr;
    }

    @NonNull
    public static byte[] readCompressed(@NonNull InputStream inputStream, int i2, int i3) {
        Inflater inflater = new Inflater();
        try {
            byte[] bArr = new byte[i3];
            byte[] bArr2 = new byte[2048];
            int i4 = 0;
            int iInflate = 0;
            while (!inflater.finished() && !inflater.needsDictionary() && i4 < i2) {
                int i5 = inputStream.read(bArr2);
                if (i5 < 0) {
                    throw error("Invalid zip data. Stream ended after $totalBytesRead bytes. Expected " + i2 + " bytes");
                }
                inflater.setInput(bArr2, 0, i5);
                try {
                    iInflate += inflater.inflate(bArr, iInflate, i3 - iInflate);
                    i4 += i5;
                } catch (DataFormatException e2) {
                    throw error(e2.getMessage());
                }
            }
            if (i4 == i2) {
                if (!inflater.finished()) {
                    throw error("Inflater did not finish");
                }
                inflater.end();
                return bArr;
            }
            throw error("Didn't read enough bytes during decompression. expected=" + i2 + " actual=" + i4);
        } catch (Throwable th) {
            inflater.end();
            throw th;
        }
    }

    @NonNull
    public static String readString(InputStream inputStream, int i2) {
        return new String(read(inputStream, i2), StandardCharsets.UTF_8);
    }

    public static long readUInt(@NonNull InputStream inputStream, int i2) throws IOException {
        byte[] bArr = read(inputStream, i2);
        long j2 = 0;
        for (int i3 = 0; i3 < i2; i3++) {
            j2 += ((long) (bArr[i3] & 255)) << (i3 * 8);
        }
        return j2;
    }

    public static int readUInt16(@NonNull InputStream inputStream) {
        return (int) readUInt(inputStream, 2);
    }

    public static long readUInt32(@NonNull InputStream inputStream) {
        return readUInt(inputStream, 4);
    }

    public static int readUInt8(@NonNull InputStream inputStream) {
        return (int) readUInt(inputStream, 1);
    }

    public static int utf8Length(@NonNull String str) {
        return str.getBytes(StandardCharsets.UTF_8).length;
    }

    public static void writeAll(@NonNull InputStream inputStream, @NonNull OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[512];
        while (true) {
            int i2 = inputStream.read(bArr);
            if (i2 <= 0) {
                return;
            } else {
                outputStream.write(bArr, 0, i2);
            }
        }
    }

    public static void writeCompressed(@NonNull OutputStream outputStream, byte[] bArr) throws IOException {
        writeUInt32(outputStream, bArr.length);
        byte[] bArrCompress = compress(bArr);
        writeUInt32(outputStream, bArrCompress.length);
        outputStream.write(bArrCompress);
    }

    public static void writeString(@NonNull OutputStream outputStream, @NonNull String str) throws IOException {
        outputStream.write(str.getBytes(StandardCharsets.UTF_8));
    }

    public static void writeUInt(@NonNull OutputStream outputStream, long j2, int i2) throws IOException {
        byte[] bArr = new byte[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            bArr[i3] = (byte) ((j2 >> (i3 * 8)) & 255);
        }
        outputStream.write(bArr);
    }

    public static void writeUInt16(@NonNull OutputStream outputStream, int i2) throws IOException {
        writeUInt(outputStream, i2, 2);
    }

    public static void writeUInt32(@NonNull OutputStream outputStream, long j2) throws IOException {
        writeUInt(outputStream, j2, 4);
    }

    public static void writeUInt8(@NonNull OutputStream outputStream, int i2) throws IOException {
        writeUInt(outputStream, i2, 1);
    }
}
