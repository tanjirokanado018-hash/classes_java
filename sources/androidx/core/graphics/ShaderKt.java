package androidx.core.graphics;

import android.graphics.Matrix;
import android.graphics.Shader;
import p.l;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ShaderKt {
    public static final void transform(Shader shader, l lVar) {
        Matrix matrix = new Matrix();
        shader.getLocalMatrix(matrix);
        lVar.invoke(matrix);
        shader.setLocalMatrix(matrix);
    }
}
