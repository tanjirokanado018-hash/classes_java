package androidx.transition;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.graphics.PathParser;
import org.xmlpull.v1.XmlPullParser;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class PatternPathMotion extends PathMotion {
    private Path mOriginalPatternPath;

    @NonNull
    private final Path mPatternPath;
    private final Matrix mTempMatrix;

    public PatternPathMotion() {
        Path path = new Path();
        this.mPatternPath = path;
        this.mTempMatrix = new Matrix();
        path.lineTo(1.0f, 0.0f);
        this.mOriginalPatternPath = path;
    }

    private static float distance(float f2, float f3) {
        return (float) Math.sqrt((f3 * f3) + (f2 * f2));
    }

    @Override // androidx.transition.PathMotion
    @NonNull
    public Path getPath(float f2, float f3, float f4, float f5) {
        float f6 = f4 - f2;
        float f7 = f5 - f3;
        float fDistance = distance(f6, f7);
        double dAtan2 = Math.atan2(f7, f6);
        this.mTempMatrix.setScale(fDistance, fDistance);
        this.mTempMatrix.postRotate((float) Math.toDegrees(dAtan2));
        this.mTempMatrix.postTranslate(f2, f3);
        Path path = new Path();
        this.mPatternPath.transform(this.mTempMatrix, path);
        return path;
    }

    @NonNull
    public Path getPatternPath() {
        return this.mOriginalPatternPath;
    }

    public void setPatternPath(@NonNull Path path) {
        PathMeasure pathMeasure = new PathMeasure(path, false);
        float[] fArr = new float[2];
        pathMeasure.getPosTan(pathMeasure.getLength(), fArr, null);
        float f2 = fArr[0];
        float f3 = fArr[1];
        pathMeasure.getPosTan(0.0f, fArr, null);
        float f4 = fArr[0];
        float f5 = fArr[1];
        if (f4 == f2 && f5 == f3) {
            throw new IllegalArgumentException("pattern must not end at the starting point");
        }
        this.mTempMatrix.setTranslate(-f4, -f5);
        float f6 = f2 - f4;
        float f7 = f3 - f5;
        float fDistance = 1.0f / distance(f6, f7);
        this.mTempMatrix.postScale(fDistance, fDistance);
        this.mTempMatrix.postRotate((float) Math.toDegrees(-Math.atan2(f7, f6)));
        path.transform(this.mTempMatrix, this.mPatternPath);
        this.mOriginalPatternPath = path;
    }

    public PatternPathMotion(@NonNull Context context, @NonNull AttributeSet attributeSet) {
        this.mPatternPath = new Path();
        this.mTempMatrix = new Matrix();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Styleable.PATTERN_PATH_MOTION);
        try {
            String namedString = TypedArrayUtils.getNamedString(typedArrayObtainStyledAttributes, (XmlPullParser) attributeSet, "patternPathData", 0);
            if (namedString == null) {
                throw new RuntimeException("pathData must be supplied for patternPathMotion");
            }
            setPatternPath(PathParser.createPathFromPathData(namedString));
            typedArrayObtainStyledAttributes.recycle();
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes.recycle();
            throw th;
        }
    }

    public PatternPathMotion(@NonNull Path path) {
        this.mPatternPath = new Path();
        this.mTempMatrix = new Matrix();
        setPatternPath(path);
    }
}
