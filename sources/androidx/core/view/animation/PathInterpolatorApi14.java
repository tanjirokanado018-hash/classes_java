package androidx.core.view.animation;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.animation.Interpolator;
import androidx.appcompat.graphics.drawable.a;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
class PathInterpolatorApi14 implements Interpolator {
    private static final float PRECISION = 0.002f;
    private final float[] mX;
    private final float[] mY;

    public PathInterpolatorApi14(float f2, float f3) {
        this(createQuad(f2, f3));
    }

    private static Path createCubic(float f2, float f3, float f4, float f5) {
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.cubicTo(f2, f3, f4, f5, 1.0f, 1.0f);
        return path;
    }

    private static Path createQuad(float f2, float f3) {
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.quadTo(f2, f3, 1.0f, 1.0f);
        return path;
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f2) {
        if (f2 <= 0.0f) {
            return 0.0f;
        }
        if (f2 >= 1.0f) {
            return 1.0f;
        }
        int length = this.mX.length - 1;
        int i2 = 0;
        while (length - i2 > 1) {
            int i3 = (i2 + length) / 2;
            if (f2 < this.mX[i3]) {
                length = i3;
            } else {
                i2 = i3;
            }
        }
        float[] fArr = this.mX;
        float f3 = fArr[length];
        float f4 = fArr[i2];
        float f5 = f3 - f4;
        if (f5 == 0.0f) {
            return this.mY[i2];
        }
        float f6 = (f2 - f4) / f5;
        float[] fArr2 = this.mY;
        float f7 = fArr2[i2];
        return a.a(fArr2[length], f7, f6, f7);
    }

    public PathInterpolatorApi14(float f2, float f3, float f4, float f5) {
        this(createCubic(f2, f3, f4, f5));
    }

    public PathInterpolatorApi14(Path path) {
        PathMeasure pathMeasure = new PathMeasure(path, false);
        float length = pathMeasure.getLength();
        int i2 = (int) (length / 0.002f);
        int i3 = i2 + 1;
        this.mX = new float[i3];
        this.mY = new float[i3];
        float[] fArr = new float[2];
        for (int i4 = 0; i4 < i3; i4++) {
            pathMeasure.getPosTan((i4 * length) / i2, fArr, null);
            this.mX[i4] = fArr[0];
            this.mY[i4] = fArr[1];
        }
    }
}
