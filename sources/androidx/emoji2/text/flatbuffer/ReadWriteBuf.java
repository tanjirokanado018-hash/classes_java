package androidx.emoji2.text.flatbuffer;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
interface ReadWriteBuf extends ReadBuf {
    @Override // androidx.emoji2.text.flatbuffer.ReadBuf
    int limit();

    void put(byte b);

    void put(byte[] bArr, int i2, int i3);

    void putBoolean(boolean z2);

    void putDouble(double d);

    void putFloat(float f2);

    void putInt(int i2);

    void putLong(long j2);

    void putShort(short s2);

    boolean requestCapacity(int i2);

    void set(int i2, byte b);

    void set(int i2, byte[] bArr, int i3, int i4);

    void setBoolean(int i2, boolean z2);

    void setDouble(int i2, double d);

    void setFloat(int i2, float f2);

    void setInt(int i2, int i3);

    void setLong(int i2, long j2);

    void setShort(int i2, short s2);

    int writePosition();
}
