package com.google.android.material.progressindicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Px;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.color.utilities.Contrast;
import com.google.android.material.math.MathUtils;
import com.google.android.material.transformation.FabTransformationScrimBehavior;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
final class CircularDrawingDelegate extends DrawingDelegate<CircularProgressIndicatorSpec> {
    private static final float ROUND_CAP_RAMP_DOWN_THRESHHOLD = 0.01f;
    private float adjustedRadius;
    private float displayedCornerRadius;
    private float displayedTrackThickness;

    @FloatRange(from = 0.0d, to = Contrast.RATIO_MIN)
    private float totalTrackLengthFraction;
    private boolean useStrokeCap;

    public CircularDrawingDelegate(@NonNull CircularProgressIndicatorSpec circularProgressIndicatorSpec) {
        super(circularProgressIndicatorSpec);
    }

    private void drawArc(@NonNull Canvas canvas, @NonNull Paint paint, float f2, float f3, @ColorInt int i2, @Px int i3, @Px int i4) {
        float f4 = f3 >= f2 ? f3 - f2 : (f3 + 1.0f) - f2;
        float f5 = f2 % 1.0f;
        if (this.totalTrackLengthFraction < 1.0f) {
            float f6 = f5 + f4;
            if (f6 > 1.0f) {
                drawArc(canvas, paint, f5, 1.0f, i2, i3, 0);
                drawArc(canvas, paint, 1.0f, f6, i2, 0, i4);
                return;
            }
        }
        float degrees = (float) Math.toDegrees(this.displayedCornerRadius / this.adjustedRadius);
        if (f5 == 0.0f && f4 >= 0.99f) {
            f4 += (((degrees * 2.0f) / 360.0f) * (f4 - 0.99f)) / ROUND_CAP_RAMP_DOWN_THRESHHOLD;
        }
        float fLerp = MathUtils.lerp(1.0f - this.totalTrackLengthFraction, 1.0f, f5);
        float fLerp2 = MathUtils.lerp(0.0f, this.totalTrackLengthFraction, f4);
        float degrees2 = (float) Math.toDegrees(i3 / this.adjustedRadius);
        float degrees3 = ((fLerp2 * 360.0f) - degrees2) - ((float) Math.toDegrees(i4 / this.adjustedRadius));
        float f7 = (fLerp * 360.0f) + degrees2;
        if (degrees3 <= 0.0f) {
            return;
        }
        paint.setAntiAlias(true);
        paint.setColor(i2);
        paint.setStrokeWidth(this.displayedTrackThickness);
        float f8 = degrees * 2.0f;
        if (degrees3 < f8) {
            float f9 = degrees3 / f8;
            paint.setStyle(Paint.Style.FILL);
            drawRoundedBlock(canvas, paint, (degrees * f9) + f7, this.displayedCornerRadius * 2.0f, this.displayedTrackThickness, f9);
            return;
        }
        float f10 = this.adjustedRadius;
        RectF rectF = new RectF(-f10, -f10, f10, f10);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(this.useStrokeCap ? Paint.Cap.ROUND : Paint.Cap.BUTT);
        float f11 = f7 + degrees;
        canvas.drawArc(rectF, f11, degrees3 - f8, false, paint);
        if (this.useStrokeCap || this.displayedCornerRadius <= 0.0f) {
            return;
        }
        paint.setStyle(Paint.Style.FILL);
        drawRoundedBlock(canvas, paint, f11, this.displayedCornerRadius * 2.0f, this.displayedTrackThickness);
        drawRoundedBlock(canvas, paint, (f7 + degrees3) - degrees, this.displayedCornerRadius * 2.0f, this.displayedTrackThickness);
    }

    private void drawRoundedBlock(@NonNull Canvas canvas, @NonNull Paint paint, float f2, float f3, float f4) {
        drawRoundedBlock(canvas, paint, f2, f3, f4, 1.0f);
    }

    private int getSize() {
        S s2 = this.spec;
        return (((CircularProgressIndicatorSpec) s2).indicatorInset * 2) + ((CircularProgressIndicatorSpec) s2).indicatorSize;
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    public void adjustCanvas(@NonNull Canvas canvas, @NonNull Rect rect, @FloatRange(from = 0.0d, to = Contrast.RATIO_MIN) float f2, boolean z2, boolean z3) {
        float fWidth = rect.width() / getPreferredWidth();
        float fHeight = rect.height() / getPreferredHeight();
        S s2 = this.spec;
        float f3 = (((CircularProgressIndicatorSpec) s2).indicatorSize / 2.0f) + ((CircularProgressIndicatorSpec) s2).indicatorInset;
        canvas.translate((f3 * fWidth) + rect.left, (f3 * fHeight) + rect.top);
        canvas.rotate(-90.0f);
        canvas.scale(fWidth, fHeight);
        if (((CircularProgressIndicatorSpec) this.spec).indicatorDirection != 0) {
            canvas.scale(1.0f, -1.0f);
        }
        float f4 = -f3;
        canvas.clipRect(f4, f4, f3, f3);
        S s3 = this.spec;
        this.useStrokeCap = ((CircularProgressIndicatorSpec) s3).trackThickness / 2 <= ((CircularProgressIndicatorSpec) s3).trackCornerRadius;
        this.displayedTrackThickness = ((CircularProgressIndicatorSpec) s3).trackThickness * f2;
        this.displayedCornerRadius = Math.min(((CircularProgressIndicatorSpec) s3).trackThickness / 2, ((CircularProgressIndicatorSpec) s3).trackCornerRadius) * f2;
        S s4 = this.spec;
        float f5 = (((CircularProgressIndicatorSpec) s4).indicatorSize - ((CircularProgressIndicatorSpec) s4).trackThickness) / 2.0f;
        this.adjustedRadius = f5;
        if (z2 || z3) {
            if ((z2 && ((CircularProgressIndicatorSpec) s4).showAnimationBehavior == 2) || (z3 && ((CircularProgressIndicatorSpec) s4).hideAnimationBehavior == 1)) {
                this.adjustedRadius = (((1.0f - f2) * ((CircularProgressIndicatorSpec) s4).trackThickness) / 2.0f) + f5;
            } else if ((z2 && ((CircularProgressIndicatorSpec) s4).showAnimationBehavior == 1) || (z3 && ((CircularProgressIndicatorSpec) s4).hideAnimationBehavior == 2)) {
                this.adjustedRadius = f5 - (((1.0f - f2) * ((CircularProgressIndicatorSpec) s4).trackThickness) / 2.0f);
            }
        }
        if (z3 && ((CircularProgressIndicatorSpec) s4).hideAnimationBehavior == 3) {
            this.totalTrackLengthFraction = f2;
        } else {
            this.totalTrackLengthFraction = 1.0f;
        }
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    public void drawStopIndicator(@NonNull Canvas canvas, @NonNull Paint paint, @ColorInt int i2, @IntRange(from = FabTransformationScrimBehavior.COLLAPSE_DELAY, to = 255) int i3) {
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    public void fillIndicator(@NonNull Canvas canvas, @NonNull Paint paint, @NonNull DrawingDelegate.ActiveIndicator activeIndicator, @IntRange(from = FabTransformationScrimBehavior.COLLAPSE_DELAY, to = 255) int i2) {
        int iCompositeARGBWithAlpha = MaterialColors.compositeARGBWithAlpha(activeIndicator.color, i2);
        float f2 = activeIndicator.startFraction;
        float f3 = activeIndicator.endFraction;
        int i3 = activeIndicator.gapSize;
        drawArc(canvas, paint, f2, f3, iCompositeARGBWithAlpha, i3, i3);
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    public void fillTrack(@NonNull Canvas canvas, @NonNull Paint paint, float f2, float f3, @ColorInt int i2, @IntRange(from = FabTransformationScrimBehavior.COLLAPSE_DELAY, to = 255) int i3, int i4) {
        drawArc(canvas, paint, f2, f3, MaterialColors.compositeARGBWithAlpha(i2, i3), i4, i4);
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    public int getPreferredHeight() {
        return getSize();
    }

    @Override // com.google.android.material.progressindicator.DrawingDelegate
    public int getPreferredWidth() {
        return getSize();
    }

    private void drawRoundedBlock(@NonNull Canvas canvas, @NonNull Paint paint, float f2, float f3, float f4, float f5) {
        float fMin = (int) Math.min(f4, this.displayedTrackThickness);
        float f6 = f3 / 2.0f;
        float fMin2 = Math.min(f6, (this.displayedCornerRadius * fMin) / this.displayedTrackThickness);
        RectF rectF = new RectF((-fMin) / 2.0f, (-f3) / 2.0f, fMin / 2.0f, f6);
        canvas.save();
        double d = f2;
        canvas.translate((float) (Math.cos(Math.toRadians(d)) * ((double) this.adjustedRadius)), (float) (Math.sin(Math.toRadians(d)) * ((double) this.adjustedRadius)));
        canvas.rotate(f2);
        canvas.scale(f5, f5);
        canvas.drawRoundRect(rectF, fMin2, fMin2, paint);
        canvas.restore();
    }
}
