package com.google.android.material.shadow;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.appcompat.graphics.drawable.DrawableWrapperCompat;
import androidx.core.content.ContextCompat;
import com.google.android.material.R;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@Deprecated
public class ShadowDrawableWrapper extends DrawableWrapperCompat {
    static final double COS_45 = Math.cos(Math.toRadians(45.0d));
    static final float SHADOW_BOTTOM_SCALE = 1.0f;
    static final float SHADOW_HORIZ_SCALE = 0.5f;
    static final float SHADOW_MULTIPLIER = 1.5f;
    static final float SHADOW_TOP_SCALE = 0.25f;
    private boolean addPaddingForCorners;

    @NonNull
    final RectF contentBounds;
    float cornerRadius;

    @NonNull
    final Paint cornerShadowPaint;
    Path cornerShadowPath;
    private boolean dirty;

    @NonNull
    final Paint edgeShadowPaint;
    float maxShadowSize;
    private boolean printedShadowClipWarning;
    float rawMaxShadowSize;
    float rawShadowSize;
    private float rotation;
    private final int shadowEndColor;
    private final int shadowMiddleColor;
    float shadowSize;
    private final int shadowStartColor;

    public ShadowDrawableWrapper(Context context, Drawable drawable, float f2, float f3, float f4) {
        super(drawable);
        this.dirty = true;
        this.addPaddingForCorners = true;
        this.printedShadowClipWarning = false;
        this.shadowStartColor = ContextCompat.getColor(context, R.color.design_fab_shadow_start_color);
        this.shadowMiddleColor = ContextCompat.getColor(context, R.color.design_fab_shadow_mid_color);
        this.shadowEndColor = ContextCompat.getColor(context, R.color.design_fab_shadow_end_color);
        Paint paint = new Paint(5);
        this.cornerShadowPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        this.cornerRadius = Math.round(f2);
        this.contentBounds = new RectF();
        Paint paint2 = new Paint(paint);
        this.edgeShadowPaint = paint2;
        paint2.setAntiAlias(false);
        setShadowSize(f3, f4);
    }

    private void buildComponents(@NonNull Rect rect) {
        float f2 = this.rawMaxShadowSize;
        float f3 = SHADOW_MULTIPLIER * f2;
        this.contentBounds.set(rect.left + f2, rect.top + f3, rect.right - f2, rect.bottom - f3);
        Drawable drawable = getDrawable();
        RectF rectF = this.contentBounds;
        drawable.setBounds((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
        buildShadowCorners();
    }

    private void buildShadowCorners() {
        float f2 = this.cornerRadius;
        RectF rectF = new RectF(-f2, -f2, f2, f2);
        RectF rectF2 = new RectF(rectF);
        float f3 = this.shadowSize;
        rectF2.inset(-f3, -f3);
        Path path = this.cornerShadowPath;
        if (path == null) {
            this.cornerShadowPath = new Path();
        } else {
            path.reset();
        }
        this.cornerShadowPath.setFillType(Path.FillType.EVEN_ODD);
        this.cornerShadowPath.moveTo(-this.cornerRadius, 0.0f);
        this.cornerShadowPath.rLineTo(-this.shadowSize, 0.0f);
        this.cornerShadowPath.arcTo(rectF2, 180.0f, 90.0f, false);
        this.cornerShadowPath.arcTo(rectF, 270.0f, -90.0f, false);
        this.cornerShadowPath.close();
        float f4 = -rectF2.top;
        if (f4 > 0.0f) {
            float f5 = this.cornerRadius / f4;
            this.cornerShadowPaint.setShader(new RadialGradient(0.0f, 0.0f, f4, new int[]{0, this.shadowStartColor, this.shadowMiddleColor, this.shadowEndColor}, new float[]{0.0f, f5, ((1.0f - f5) / 2.0f) + f5, 1.0f}, Shader.TileMode.CLAMP));
        }
        this.edgeShadowPaint.setShader(new LinearGradient(0.0f, rectF.top, 0.0f, rectF2.top, new int[]{this.shadowStartColor, this.shadowMiddleColor, this.shadowEndColor}, new float[]{0.0f, 0.5f, 1.0f}, Shader.TileMode.CLAMP));
        this.edgeShadowPaint.setAntiAlias(false);
    }

    public static float calculateHorizontalPadding(float f2, float f3, boolean z2) {
        if (!z2) {
            return f2;
        }
        return (float) (((1.0d - COS_45) * ((double) f3)) + ((double) f2));
    }

    public static float calculateVerticalPadding(float f2, float f3, boolean z2) {
        float f4 = f2 * SHADOW_MULTIPLIER;
        if (!z2) {
            return f4;
        }
        return (float) (((1.0d - COS_45) * ((double) f3)) + ((double) f4));
    }

    private void drawShadow(@NonNull Canvas canvas) {
        int iSave = canvas.save();
        canvas.rotate(this.rotation, this.contentBounds.centerX(), this.contentBounds.centerY());
        float f2 = this.cornerRadius;
        float f3 = (-f2) - this.shadowSize;
        float f4 = f2 * 2.0f;
        boolean z2 = this.contentBounds.width() - f4 > 0.0f;
        boolean z3 = this.contentBounds.height() - f4 > 0.0f;
        float f5 = this.rawShadowSize;
        float f6 = f2 / ((f5 - (0.5f * f5)) + f2);
        float f7 = f2 / ((f5 - (SHADOW_TOP_SCALE * f5)) + f2);
        float f8 = f2 / ((f5 - (f5 * 1.0f)) + f2);
        int iSave2 = canvas.save();
        RectF rectF = this.contentBounds;
        canvas.translate(rectF.left + f2, rectF.top + f2);
        canvas.scale(f6, f7);
        canvas.drawPath(this.cornerShadowPath, this.cornerShadowPaint);
        if (z2) {
            canvas.scale(1.0f / f6, 1.0f);
            canvas.drawRect(0.0f, f3, this.contentBounds.width() - f4, -this.cornerRadius, this.edgeShadowPaint);
        }
        canvas.restoreToCount(iSave2);
        int iSave3 = canvas.save();
        RectF rectF2 = this.contentBounds;
        canvas.translate(rectF2.right - f2, rectF2.bottom - f2);
        canvas.scale(f6, f8);
        canvas.rotate(180.0f);
        canvas.drawPath(this.cornerShadowPath, this.cornerShadowPaint);
        if (z2) {
            canvas.scale(1.0f / f6, 1.0f);
            canvas.drawRect(0.0f, f3, this.contentBounds.width() - f4, (-this.cornerRadius) + this.shadowSize, this.edgeShadowPaint);
        }
        canvas.restoreToCount(iSave3);
        int iSave4 = canvas.save();
        RectF rectF3 = this.contentBounds;
        canvas.translate(rectF3.left + f2, rectF3.bottom - f2);
        canvas.scale(f6, r6);
        canvas.rotate(270.0f);
        canvas.drawPath(this.cornerShadowPath, this.cornerShadowPaint);
        if (z3) {
            canvas.scale(1.0f / f8, 1.0f);
            canvas.drawRect(0.0f, f3, this.contentBounds.height() - f4, -this.cornerRadius, this.edgeShadowPaint);
        }
        canvas.restoreToCount(iSave4);
        int iSave5 = canvas.save();
        RectF rectF4 = this.contentBounds;
        canvas.translate(rectF4.right - f2, rectF4.top + f2);
        canvas.scale(f6, f7);
        canvas.rotate(90.0f);
        canvas.drawPath(this.cornerShadowPath, this.cornerShadowPaint);
        if (z3) {
            canvas.scale(1.0f / f7, 1.0f);
            canvas.drawRect(0.0f, f3, this.contentBounds.height() - f4, -this.cornerRadius, this.edgeShadowPaint);
        }
        canvas.restoreToCount(iSave5);
        canvas.restoreToCount(iSave);
    }

    private static int toEven(float f2) {
        int iRound = Math.round(f2);
        return iRound % 2 == 1 ? iRound - 1 : iRound;
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableWrapperCompat, android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        if (this.dirty) {
            buildComponents(getBounds());
            this.dirty = false;
        }
        drawShadow(canvas);
        super.draw(canvas);
    }

    public float getCornerRadius() {
        return this.cornerRadius;
    }

    public float getMaxShadowSize() {
        return this.rawMaxShadowSize;
    }

    public float getMinHeight() {
        float f2 = this.rawMaxShadowSize;
        return (this.rawMaxShadowSize * SHADOW_MULTIPLIER * 2.0f) + (Math.max(f2, ((f2 * SHADOW_MULTIPLIER) / 2.0f) + this.cornerRadius) * 2.0f);
    }

    public float getMinWidth() {
        float f2 = this.rawMaxShadowSize;
        return (this.rawMaxShadowSize * 2.0f) + (Math.max(f2, (f2 / 2.0f) + this.cornerRadius) * 2.0f);
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableWrapperCompat, android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableWrapperCompat, android.graphics.drawable.Drawable
    public boolean getPadding(@NonNull Rect rect) {
        int iCeil = (int) Math.ceil(calculateVerticalPadding(this.rawMaxShadowSize, this.cornerRadius, this.addPaddingForCorners));
        int iCeil2 = (int) Math.ceil(calculateHorizontalPadding(this.rawMaxShadowSize, this.cornerRadius, this.addPaddingForCorners));
        rect.set(iCeil2, iCeil, iCeil2, iCeil);
        return true;
    }

    public float getShadowSize() {
        return this.rawShadowSize;
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableWrapperCompat, android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        this.dirty = true;
    }

    public void setAddPaddingForCorners(boolean z2) {
        this.addPaddingForCorners = z2;
        invalidateSelf();
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableWrapperCompat, android.graphics.drawable.Drawable
    public void setAlpha(int i2) {
        super.setAlpha(i2);
        this.cornerShadowPaint.setAlpha(i2);
        this.edgeShadowPaint.setAlpha(i2);
    }

    public void setCornerRadius(float f2) {
        float fRound = Math.round(f2);
        if (this.cornerRadius == fRound) {
            return;
        }
        this.cornerRadius = fRound;
        this.dirty = true;
        invalidateSelf();
    }

    public void setMaxShadowSize(float f2) {
        setShadowSize(this.rawShadowSize, f2);
    }

    public final void setRotation(float f2) {
        if (this.rotation != f2) {
            this.rotation = f2;
            invalidateSelf();
        }
    }

    public void setShadowSize(float f2) {
        setShadowSize(f2, this.rawMaxShadowSize);
    }

    public void setShadowSize(float f2, float f3) {
        if (f2 < 0.0f || f3 < 0.0f) {
            throw new IllegalArgumentException("invalid shadow size");
        }
        float even = toEven(f2);
        float even2 = toEven(f3);
        if (even > even2) {
            if (!this.printedShadowClipWarning) {
                this.printedShadowClipWarning = true;
            }
            even = even2;
        }
        if (this.rawShadowSize == even && this.rawMaxShadowSize == even2) {
            return;
        }
        this.rawShadowSize = even;
        this.rawMaxShadowSize = even2;
        this.shadowSize = Math.round(even * SHADOW_MULTIPLIER);
        this.maxShadowSize = even2;
        this.dirty = true;
        invalidateSelf();
    }
}
