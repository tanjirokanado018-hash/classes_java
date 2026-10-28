package androidx.core.graphics;

import android.graphics.Picture;
import p.l;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class PictureKt {
    public static final Picture record(Picture picture, int i2, int i3, l lVar) {
        try {
            lVar.invoke(picture.beginRecording(i2, i3));
            return picture;
        } finally {
            picture.endRecording();
        }
    }
}
