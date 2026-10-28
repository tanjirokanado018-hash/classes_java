package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class Struct {
    protected ByteBuffer bb;
    protected int bb_pos;

    public void __reset() {
        __reset(0, null);
    }

    public void __reset(int i2, ByteBuffer byteBuffer) {
        this.bb = byteBuffer;
        if (byteBuffer == null) {
            i2 = 0;
        }
        this.bb_pos = i2;
    }
}
