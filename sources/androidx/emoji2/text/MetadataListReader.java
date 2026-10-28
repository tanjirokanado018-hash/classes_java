package androidx.emoji2.text;

import android.content.res.AssetManager;
import androidx.annotation.AnyThread;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.emoji2.text.flatbuffer.MetadataList;
import com.google.android.material.transformation.FabTransformationScrimBehavior;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@AnyThread
@RequiresApi(19)
@RestrictTo({RestrictTo.Scope.LIBRARY})
class MetadataListReader {
    private static final int EMJI_TAG = 1164798569;
    private static final int EMJI_TAG_DEPRECATED = 1701669481;
    private static final int META_TABLE_NAME = 1835365473;

    public static class ByteBufferReader implements OpenTypeReader {

        @NonNull
        private final ByteBuffer mByteBuffer;

        public ByteBufferReader(@NonNull ByteBuffer byteBuffer) {
            this.mByteBuffer = byteBuffer;
            byteBuffer.order(ByteOrder.BIG_ENDIAN);
        }

        @Override // androidx.emoji2.text.MetadataListReader.OpenTypeReader
        public long getPosition() {
            return this.mByteBuffer.position();
        }

        @Override // androidx.emoji2.text.MetadataListReader.OpenTypeReader
        public int readTag() {
            return this.mByteBuffer.getInt();
        }

        @Override // androidx.emoji2.text.MetadataListReader.OpenTypeReader
        public long readUnsignedInt() {
            return MetadataListReader.toUnsignedInt(this.mByteBuffer.getInt());
        }

        @Override // androidx.emoji2.text.MetadataListReader.OpenTypeReader
        public int readUnsignedShort() {
            return MetadataListReader.toUnsignedShort(this.mByteBuffer.getShort());
        }

        @Override // androidx.emoji2.text.MetadataListReader.OpenTypeReader
        public void skip(int i2) {
            ByteBuffer byteBuffer = this.mByteBuffer;
            byteBuffer.position(byteBuffer.position() + i2);
        }
    }

    public static class InputStreamOpenTypeReader implements OpenTypeReader {

        @NonNull
        private final byte[] mByteArray;

        @NonNull
        private final ByteBuffer mByteBuffer;

        @NonNull
        private final InputStream mInputStream;
        private long mPosition = 0;

        public InputStreamOpenTypeReader(@NonNull InputStream inputStream) {
            this.mInputStream = inputStream;
            byte[] bArr = new byte[4];
            this.mByteArray = bArr;
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
            this.mByteBuffer = byteBufferWrap;
            byteBufferWrap.order(ByteOrder.BIG_ENDIAN);
        }

        private void read(@IntRange(from = FabTransformationScrimBehavior.COLLAPSE_DELAY, to = 4) int i2) throws IOException {
            if (this.mInputStream.read(this.mByteArray, 0, i2) != i2) {
                throw new IOException("read failed");
            }
            this.mPosition += (long) i2;
        }

        @Override // androidx.emoji2.text.MetadataListReader.OpenTypeReader
        public long getPosition() {
            return this.mPosition;
        }

        @Override // androidx.emoji2.text.MetadataListReader.OpenTypeReader
        public int readTag() throws IOException {
            this.mByteBuffer.position(0);
            read(4);
            return this.mByteBuffer.getInt();
        }

        @Override // androidx.emoji2.text.MetadataListReader.OpenTypeReader
        public long readUnsignedInt() throws IOException {
            this.mByteBuffer.position(0);
            read(4);
            return MetadataListReader.toUnsignedInt(this.mByteBuffer.getInt());
        }

        @Override // androidx.emoji2.text.MetadataListReader.OpenTypeReader
        public int readUnsignedShort() throws IOException {
            this.mByteBuffer.position(0);
            read(2);
            return MetadataListReader.toUnsignedShort(this.mByteBuffer.getShort());
        }

        @Override // androidx.emoji2.text.MetadataListReader.OpenTypeReader
        public void skip(int i2) throws IOException {
            while (i2 > 0) {
                int iSkip = (int) this.mInputStream.skip(i2);
                if (iSkip < 1) {
                    throw new IOException("Skip didn't move at least 1 byte forward");
                }
                i2 -= iSkip;
                this.mPosition += (long) iSkip;
            }
        }
    }

    public static class OffsetInfo {
        private final long mLength;
        private final long mStartOffset;

        public OffsetInfo(long j2, long j3) {
            this.mStartOffset = j2;
            this.mLength = j3;
        }

        public long getLength() {
            return this.mLength;
        }

        public long getStartOffset() {
            return this.mStartOffset;
        }
    }

    public interface OpenTypeReader {
        public static final int UINT16_BYTE_COUNT = 2;
        public static final int UINT32_BYTE_COUNT = 4;

        long getPosition();

        int readTag();

        long readUnsignedInt();

        int readUnsignedShort();

        void skip(int i2);
    }

    private MetadataListReader() {
    }

    private static OffsetInfo findOffsetInfo(OpenTypeReader openTypeReader) throws IOException {
        long unsignedInt;
        openTypeReader.skip(4);
        int unsignedShort = openTypeReader.readUnsignedShort();
        if (unsignedShort > 100) {
            throw new IOException("Cannot read metadata.");
        }
        openTypeReader.skip(6);
        int i2 = 0;
        while (true) {
            if (i2 >= unsignedShort) {
                unsignedInt = -1;
                break;
            }
            int tag = openTypeReader.readTag();
            openTypeReader.skip(4);
            unsignedInt = openTypeReader.readUnsignedInt();
            openTypeReader.skip(4);
            if (META_TABLE_NAME == tag) {
                break;
            }
            i2++;
        }
        if (unsignedInt != -1) {
            openTypeReader.skip((int) (unsignedInt - openTypeReader.getPosition()));
            openTypeReader.skip(12);
            long unsignedInt2 = openTypeReader.readUnsignedInt();
            for (int i3 = 0; i3 < unsignedInt2; i3++) {
                int tag2 = openTypeReader.readTag();
                long unsignedInt3 = openTypeReader.readUnsignedInt();
                long unsignedInt4 = openTypeReader.readUnsignedInt();
                if (EMJI_TAG == tag2 || EMJI_TAG_DEPRECATED == tag2) {
                    return new OffsetInfo(unsignedInt3 + unsignedInt, unsignedInt4);
                }
            }
        }
        throw new IOException("Cannot read metadata.");
    }

    public static MetadataList read(AssetManager assetManager, String str) throws IOException {
        InputStream inputStreamOpen = assetManager.open(str);
        try {
            MetadataList metadataList = read(inputStreamOpen);
            if (inputStreamOpen != null) {
                inputStreamOpen.close();
            }
            return metadataList;
        } catch (Throwable th) {
            if (inputStreamOpen != null) {
                try {
                    inputStreamOpen.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static long toUnsignedInt(int i2) {
        return ((long) i2) & 4294967295L;
    }

    public static int toUnsignedShort(short s2) {
        return s2 & 65535;
    }

    public static MetadataList read(InputStream inputStream) throws IOException {
        InputStreamOpenTypeReader inputStreamOpenTypeReader = new InputStreamOpenTypeReader(inputStream);
        OffsetInfo offsetInfoFindOffsetInfo = findOffsetInfo(inputStreamOpenTypeReader);
        inputStreamOpenTypeReader.skip((int) (offsetInfoFindOffsetInfo.getStartOffset() - inputStreamOpenTypeReader.getPosition()));
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate((int) offsetInfoFindOffsetInfo.getLength());
        int i2 = inputStream.read(byteBufferAllocate.array());
        if (i2 == offsetInfoFindOffsetInfo.getLength()) {
            return MetadataList.getRootAsMetadataList(byteBufferAllocate);
        }
        throw new IOException("Needed " + offsetInfoFindOffsetInfo.getLength() + " bytes, got " + i2);
    }

    public static MetadataList read(ByteBuffer byteBuffer) {
        ByteBuffer byteBufferDuplicate = byteBuffer.duplicate();
        byteBufferDuplicate.position((int) findOffsetInfo(new ByteBufferReader(byteBufferDuplicate)).getStartOffset());
        return MetadataList.getRootAsMetadataList(byteBufferDuplicate);
    }
}
