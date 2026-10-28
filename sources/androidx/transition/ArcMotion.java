package androidx.transition;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.core.content.res.TypedArrayUtils;
import org.xmlpull.v1.XmlPullParser;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class ArcMotion extends PathMotion {
    private static final float DEFAULT_MAX_ANGLE_DEGREES = 70.0f;
    private static final float DEFAULT_MAX_TANGENT = (float) Math.tan(Math.toRadians(35.0d));
    private static final float DEFAULT_MIN_ANGLE_DEGREES = 0.0f;
    private float mMaximumAngle;
    private float mMaximumTangent;
    private float mMinimumHorizontalAngle;
    private float mMinimumHorizontalTangent;
    private float mMinimumVerticalAngle;
    private float mMinimumVerticalTangent;

    public ArcMotion() {
        this.mMinimumHorizontalAngle = 0.0f;
        this.mMinimumVerticalAngle = 0.0f;
        this.mMaximumAngle = DEFAULT_MAX_ANGLE_DEGREES;
        this.mMinimumHorizontalTangent = 0.0f;
        this.mMinimumVerticalTangent = 0.0f;
        this.mMaximumTangent = DEFAULT_MAX_TANGENT;
    }

    private static float toTangent(float f2) {
        if (f2 < 0.0f || f2 > 90.0f) {
            throw new IllegalArgumentException("Arc must be between 0 and 90 degrees");
        }
        return (float) Math.tan(Math.toRadians(f2 / 2.0f));
    }

    public float getMaximumAngle() {
        return this.mMaximumAngle;
    }

    public float getMinimumHorizontalAngle() {
        return this.mMinimumHorizontalAngle;
    }

    public float getMinimumVerticalAngle() {
        return this.mMinimumVerticalAngle;
    }

    @Override // androidx.transition.PathMotion
    @NonNull
    public Path getPath(float f2, float f3, float f4, float f5) {
        float fA;
        float fA2;
        float f6;
        Path path = new Path();
        path.moveTo(f2, f3);
        float f7 = f4 - f2;
        float f8 = f5 - f3;
        float f9 = (f8 * f8) + (f7 * f7);
        float f10 = (f2 + f4) / 2.0f;
        float f11 = (f3 + f5) / 2.0f;
        float f12 = 0.25f * f9;
        boolean z2 = f3 > f5;
        if (Math.abs(f7) < Math.abs(f8)) {
            float fAbs = Math.abs(f9 / (f8 * 2.0f));
            if (z2) {
                fA2 = fAbs + f5;
                fA = f4;
            } else {
                fA2 = fAbs + f3;
                fA = f2;
            }
            f6 = this.mMinimumVerticalTangent;
        } else {
            float f13 = f9 / (f7 * 2.0f);
            if (z2) {
                fA2 = f3;
                fA = f13 + f2;
            } else {
                fA = f4 - f13;
                fA2 = f5;
            }
            f6 = this.mMinimumHorizontalTangent;
        }
        float f14 = f12 * f6 * f6;
        float f15 = f10 - fA;
        float f16 = f11 - fA2;
        float f17 = (f16 * f16) + (f15 * f15);
        float f18 = this.mMaximumTangent;
        float f19 = f12 * f18 * f18;
        if (f17 >= f14) {
            f14 = f17 > f19 ? f19 : 0.0f;
        }
        if (f14 != 0.0f) {
            float fSqrt = (float) Math.sqrt(f14 / f17);
            fA = androidx.appcompat.graphics.drawable.a.a(fA, f10, fSqrt, f10);
            fA2 = androidx.appcompat.graphics.drawable.a.a(fA2, f11, fSqrt, f11);
        }
        path.cubicTo((f2 + fA) / 2.0f, (f3 + fA2) / 2.0f, (fA + f4) / 2.0f, (fA2 + f5) / 2.0f, f4, f5);
        return path;
    }

    public void setMaximumAngle(float f2) {
        this.mMaximumAngle = f2;
        this.mMaximumTangent = toTangent(f2);
    }

    public void setMinimumHorizontalAngle(float f2) {
        this.mMinimumHorizontalAngle = f2;
        this.mMinimumHorizontalTangent = toTangent(f2);
    }

    public void setMinimumVerticalAngle(float f2) {
        this.mMinimumVerticalAngle = f2;
        this.mMinimumVerticalTangent = toTangent(f2);
    }

    public ArcMotion(@NonNull Context context, @NonNull AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMinimumHorizontalAngle = 0.0f;
        this.mMinimumVerticalAngle = 0.0f;
        this.mMaximumAngle = DEFAULT_MAX_ANGLE_DEGREES;
        this.mMinimumHorizontalTangent = 0.0f;
        this.mMinimumVerticalTangent = 0.0f;
        this.mMaximumTangent = DEFAULT_MAX_TANGENT;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Styleable.ARC_MOTION);
        XmlPullParser xmlPullParser = (XmlPullParser) attributeSet;
        setMinimumVerticalAngle(TypedArrayUtils.getNamedFloat(typedArrayObtainStyledAttributes, xmlPullParser, "minimumVerticalAngle", 1, 0.0f));
        setMinimumHorizontalAngle(TypedArrayUtils.getNamedFloat(typedArrayObtainStyledAttributes, xmlPullParser, "minimumHorizontalAngle", 0, 0.0f));
        setMaximumAngle(TypedArrayUtils.getNamedFloat(typedArrayObtainStyledAttributes, xmlPullParser, "maximumAngle", 2, DEFAULT_MAX_ANGLE_DEGREES));
        typedArrayObtainStyledAttributes.recycle();
    }
}
