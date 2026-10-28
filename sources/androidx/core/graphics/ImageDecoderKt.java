package androidx.core.graphics;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.Drawable;
import androidx.annotation.RequiresApi;
import p.q;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@SuppressLint({"ClassVerificationFailure"})
public final class ImageDecoderKt {
    @RequiresApi(28)
    public static final Bitmap decodeBitmap(ImageDecoder.Source source, q qVar) {
        return ImageDecoder.decodeBitmap(source, new ImageDecoder.OnHeaderDecodedListener(qVar) { // from class: androidx.core.graphics.ImageDecoderKt.decodeBitmap.1
            final /* synthetic */ q $action;

            @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
            public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source2) {
                throw null;
            }
        });
    }

    @RequiresApi(28)
    public static final Drawable decodeDrawable(ImageDecoder.Source source, q qVar) {
        return ImageDecoder.decodeDrawable(source, new ImageDecoder.OnHeaderDecodedListener(qVar) { // from class: androidx.core.graphics.ImageDecoderKt.decodeDrawable.1
            final /* synthetic */ q $action;

            @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
            public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source2) {
                throw null;
            }
        });
    }
}
