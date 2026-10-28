package androidx.emoji2.text.flatbuffer;

import java.util.Arrays;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class ArrayReadWriteBuf implements ReadWriteBuf {
    private byte[] buffer;
    private int writePos;

    public ArrayReadWriteBuf() {
        this(10);
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadBuf
    public byte[] data() {
        return this.buffer;
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadBuf
    public byte get(int i2) {
        return this.buffer[i2];
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadBuf
    public boolean getBoolean(int i2) {
        return this.buffer[i2] != 0;
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadBuf
    public double getDouble(int i2) {
        return Double.longBitsToDouble(getLong(i2));
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadBuf
    public float getFloat(int i2) {
        return Float.intBitsToFloat(getInt(i2));
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadBuf
    public int getInt(int i2) {
        byte[] bArr = this.buffer;
        return (bArr[i2] & 255) | (bArr[i2 + 3] << 24) | ((bArr[i2 + 2] & 255) << 16) | ((bArr[i2 + 1] & 255) << 8);
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadBuf
    public long getLong(int i2) {
        byte[] bArr = this.buffer;
        int i3 = i2 + 6;
        return (((long) bArr[i2]) & 255) | ((((long) bArr[i2 + 1]) & 255) << 8) | ((((long) bArr[i2 + 2]) & 255) << 16) | ((((long) bArr[i2 + 3]) & 255) << 24) | ((((long) bArr[i2 + 4]) & 255) << 32) | ((((long) bArr[i2 + 5]) & 255) << 40) | ((((long) bArr[i3]) & 255) << 48) | (((long) bArr[i2 + 7]) << 56);
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadBuf
    public short getShort(int i2) {
        byte[] bArr = this.buffer;
        return (short) ((bArr[i2] & 255) | (bArr[i2 + 1] << 8));
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadBuf
    public String getString(int i2, int i3) {
        return Utf8Safe.decodeUtf8Array(this.buffer, i2, i3);
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf, androidx.emoji2.text.flatbuffer.ReadBuf
    public int limit() {
        return this.writePos;
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public void put(byte b) {
        set(this.writePos, b);
        this.writePos++;
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public void putBoolean(boolean z2) {
        setBoolean(this.writePos, z2);
        this.writePos++;
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public void putDouble(double d) {
        setDouble(this.writePos, d);
        this.writePos += 8;
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public void putFloat(float f2) {
        setFloat(this.writePos, f2);
        this.writePos += 4;
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public void putInt(int i2) {
        setInt(this.writePos, i2);
        this.writePos += 4;
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public void putLong(long j2) {
        setLong(this.writePos, j2);
        this.writePos += 8;
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public void putShort(short s2) {
        setShort(this.writePos, s2);
        this.writePos += 2;
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public boolean requestCapacity(int i2) {
        byte[] bArr = this.buffer;
        if (bArr.length > i2) {
            return true;
        }
        int length = bArr.length;
        this.buffer = Arrays.copyOf(bArr, length + (length >> 1));
        return true;
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public void set(int i2, byte b) {
        requestCapacity(i2 + 1);
        this.buffer[i2] = b;
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public void setBoolean(int i2, boolean z2) {
        set(i2, z2 ? (byte) 1 : (byte) 0);
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public void setDouble(int i2, double d) {
        requestCapacity(i2 + 8);
        long jDoubleToRawLongBits = Double.doubleToRawLongBits(d);
        int i3 = (int) jDoubleToRawLongBits;
        byte[] bArr = this.buffer;
        bArr[i2] = (byte) (i3 & 255);
        bArr[i2 + 1] = (byte) ((i3 >> 8) & 255);
        bArr[i2 + 2] = (byte) ((i3 >> 16) & 255);
        bArr[i2 + 3] = (byte) ((i3 >> 24) & 255);
        int i4 = (int) (jDoubleToRawLongBits >> 32);
        bArr[i2 + 4] = (byte) (i4 & 255);
        bArr[i2 + 5] = (byte) ((i4 >> 8) & 255);
        bArr[i2 + 6] = (byte) ((i4 >> 16) & 255);
        bArr[i2 + 7] = (byte) ((i4 >> 24) & 255);
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public void setFloat(int i2, float f2) {
        requestCapacity(i2 + 4);
        int iFloatToRawIntBits = Float.floatToRawIntBits(f2);
        byte[] bArr = this.buffer;
        bArr[i2] = (byte) (iFloatToRawIntBits & 255);
        bArr[i2 + 1] = (byte) ((iFloatToRawIntBits >> 8) & 255);
        bArr[i2 + 2] = (byte) ((iFloatToRawIntBits >> 16) & 255);
        bArr[i2 + 3] = (byte) ((iFloatToRawIntBits >> 24) & 255);
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public void setInt(int i2, int i3) {
        requestCapacity(i2 + 4);
        byte[] bArr = this.buffer;
        bArr[i2] = (byte) (i3 & 255);
        bArr[i2 + 1] = (byte) ((i3 >> 8) & 255);
        bArr[i2 + 2] = (byte) ((i3 >> 16) & 255);
        bArr[i2 + 3] = (byte) ((i3 >> 24) & 255);
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public void setLong(int i2, long j2) {
        requestCapacity(i2 + 8);
        int i3 = (int) j2;
        byte[] bArr = this.buffer;
        bArr[i2] = (byte) (i3 & 255);
        bArr[i2 + 1] = (byte) ((i3 >> 8) & 255);
        bArr[i2 + 2] = (byte) ((i3 >> 16) & 255);
        bArr[i2 + 3] = (byte) ((i3 >> 24) & 255);
        int i4 = (int) (j2 >> 32);
        bArr[i2 + 4] = (byte) (i4 & 255);
        bArr[i2 + 5] = (byte) ((i4 >> 8) & 255);
        bArr[i2 + 6] = (byte) ((i4 >> 16) & 255);
        bArr[i2 + 7] = (byte) ((i4 >> 24) & 255);
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public void setShort(int i2, short s2) {
        requestCapacity(i2 + 2);
        byte[] bArr = this.buffer;
        bArr[i2] = (byte) (s2 & 255);
        bArr[i2 + 1] = (byte) ((s2 >> 8) & 255);
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public int writePosition() {
        return this.writePos;
    }

    public ArrayReadWriteBuf(int i2) {
        this(new byte[i2]);
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public void put(byte[] bArr, int i2, int i3) {
        set(this.writePos, bArr, i2, i3);
        this.writePos += i3;
    }

    @Override // androidx.emoji2.text.flatbuffer.ReadWriteBuf
    public void set(int i2, byte[] bArr, int i3, int i4) {
        requestCapacity((i4 - i3) + i2);
        System.arraycopy(bArr, i3, this.buffer, i2, i4);
    }

    public ArrayReadWriteBuf(byte[] bArr) {
        this.buffer = bArr;
        this.writePos = 0;
    }

    public ArrayReadWriteBuf(byte[] bArr, int i2) {
        this.buffer = bArr;
        this.writePos = i2;
    }
}
