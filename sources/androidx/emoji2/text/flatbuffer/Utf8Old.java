package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class Utf8Old extends Utf8 {
    private static final ThreadLocal<Cache> CACHE = ThreadLocal.withInitial(new a());

    public static class Cache {
        final CharsetDecoder decoder;
        final CharsetEncoder encoder;
        CharSequence lastInput = null;
        ByteBuffer lastOutput = null;

        public Cache() {
            Charset charset = StandardCharsets.UTF_8;
            this.encoder = charset.newEncoder();
            this.decoder = charset.newDecoder();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Cache lambda$static$0() {
        return new Cache();
    }

    @Override // androidx.emoji2.text.flatbuffer.Utf8
    public String decodeUtf8(ByteBuffer byteBuffer, int i2, int i3) {
        CharsetDecoder charsetDecoder = CACHE.get().decoder;
        charsetDecoder.reset();
        ByteBuffer byteBufferDuplicate = byteBuffer.duplicate();
        byteBufferDuplicate.position(i2);
        byteBufferDuplicate.limit(i2 + i3);
        try {
            return charsetDecoder.decode(byteBufferDuplicate).toString();
        } catch (CharacterCodingException e2) {
            throw new IllegalArgumentException("Bad encoding", e2);
        }
    }

    @Override // androidx.emoji2.text.flatbuffer.Utf8
    public void encodeUtf8(CharSequence charSequence, ByteBuffer byteBuffer) {
        Cache cache = CACHE.get();
        if (cache.lastInput != charSequence) {
            encodedLength(charSequence);
        }
        byteBuffer.put(cache.lastOutput);
    }

    @Override // androidx.emoji2.text.flatbuffer.Utf8
    public int encodedLength(CharSequence charSequence) {
        Cache cache = CACHE.get();
        int iMaxBytesPerChar = (int) (cache.encoder.maxBytesPerChar() * charSequence.length());
        ByteBuffer byteBuffer = cache.lastOutput;
        if (byteBuffer == null || byteBuffer.capacity() < iMaxBytesPerChar) {
            cache.lastOutput = ByteBuffer.allocate(Math.max(128, iMaxBytesPerChar));
        }
        cache.lastOutput.clear();
        cache.lastInput = charSequence;
        CoderResult coderResultEncode = cache.encoder.encode(charSequence instanceof CharBuffer ? (CharBuffer) charSequence : CharBuffer.wrap(charSequence), cache.lastOutput, true);
        if (coderResultEncode.isError()) {
            try {
                coderResultEncode.throwException();
            } catch (CharacterCodingException e2) {
                throw new IllegalArgumentException("bad character encoding", e2);
            }
        }
        cache.lastOutput.flip();
        return cache.lastOutput.remaining();
    }
}
