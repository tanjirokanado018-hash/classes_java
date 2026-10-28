package com.google.android.material.shape;

import android.graphics.Outline;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewOutlineProvider;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RequiresApi(22)
class ShapeableDelegateV22 extends ShapeableDelegate {
    private boolean canUseViewOutline = false;
    private float cornerRadius = 0.0f;

    public ShapeableDelegateV22(@NonNull View view) {
        initMaskOutlineProvider(view);
    }

    private float getDefaultCornerRadius() {
        RectF rectF;
        ShapeAppearanceModel shapeAppearanceModel = this.shapeAppearanceModel;
        if (shapeAppearanceModel == null || (rectF = this.maskBounds) == null) {
            return 0.0f;
        }
        return shapeAppearanceModel.topRightCornerSize.getCornerSize(rectF);
    }

    @DoNotInline
    private void initMaskOutlineProvider(View view) {
        view.setOutlineProvider(new ViewOutlineProvider() { // from class: com.google.android.material.shape.ShapeableDelegateV22.1
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view2, Outline outline) {
                ShapeableDelegateV22 shapeableDelegateV22 = ShapeableDelegateV22.this;
                if (shapeableDelegateV22.shapeAppearanceModel == null || shapeableDelegateV22.maskBounds.isEmpty()) {
                    return;
                }
                ShapeableDelegateV22 shapeableDelegateV23 = ShapeableDelegateV22.this;
                RectF rectF = shapeableDelegateV23.maskBounds;
                outline.setRoundRect((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom, shapeableDelegateV23.cornerRadius);
            }
        });
    }

    private boolean isShapeRoundRect() {
        ShapeAppearanceModel shapeAppearanceModel;
        if (this.maskBounds.isEmpty() || (shapeAppearanceModel = this.shapeAppearanceModel) == null) {
            return false;
        }
        return shapeAppearanceModel.isRoundRect(this.maskBounds);
    }

    private boolean offsetZeroCornerEdgeBoundsIfPossible() {
        ShapeAppearanceModel shapeAppearanceModel;
        RectF rectF;
        float f2;
        float f3;
        float f4;
        float f5;
        if (!this.maskBounds.isEmpty() && (shapeAppearanceModel = this.shapeAppearanceModel) != null && this.offsetZeroCornerEdgeBoundsEnabled && !shapeAppearanceModel.isRoundRect(this.maskBounds) && shapeUsesAllRoundedCornerTreatments(this.shapeAppearanceModel)) {
            float cornerSize = this.shapeAppearanceModel.getTopLeftCornerSize().getCornerSize(this.maskBounds);
            float cornerSize2 = this.shapeAppearanceModel.getTopRightCornerSize().getCornerSize(this.maskBounds);
            float cornerSize3 = this.shapeAppearanceModel.getBottomLeftCornerSize().getCornerSize(this.maskBounds);
            float cornerSize4 = this.shapeAppearanceModel.getBottomRightCornerSize().getCornerSize(this.maskBounds);
            if (cornerSize == 0.0f && cornerSize3 == 0.0f && cornerSize2 == cornerSize4) {
                RectF rectF2 = this.maskBounds;
                rectF2.set(rectF2.left - cornerSize2, rectF2.top, rectF2.right, rectF2.bottom);
                this.cornerRadius = cornerSize2;
                return true;
            }
            if (cornerSize == 0.0f && cornerSize2 == 0.0f && cornerSize3 == cornerSize4) {
                RectF rectF3 = this.maskBounds;
                rectF3.set(rectF3.left, rectF3.top - cornerSize3, rectF3.right, rectF3.bottom);
                this.cornerRadius = cornerSize3;
                return true;
            }
            if (cornerSize2 == 0.0f && cornerSize4 == 0.0f && cornerSize == cornerSize3) {
                rectF = this.maskBounds;
                f2 = rectF.left;
                f3 = rectF.top;
                f4 = rectF.right + cornerSize;
                f5 = rectF.bottom;
            } else if (cornerSize3 == 0.0f && cornerSize4 == 0.0f && cornerSize == cornerSize2) {
                rectF = this.maskBounds;
                f2 = rectF.left;
                f3 = rectF.top;
                f4 = rectF.right;
                f5 = rectF.bottom + cornerSize;
            }
            rectF.set(f2, f3, f4, f5);
            this.cornerRadius = cornerSize;
            return true;
        }
        return false;
    }

    private static boolean shapeUsesAllRoundedCornerTreatments(ShapeAppearanceModel shapeAppearanceModel) {
        return (shapeAppearanceModel.getTopLeftCorner() instanceof RoundedCornerTreatment) && (shapeAppearanceModel.getTopRightCorner() instanceof RoundedCornerTreatment) && (shapeAppearanceModel.getBottomLeftCorner() instanceof RoundedCornerTreatment) && (shapeAppearanceModel.getBottomRightCorner() instanceof RoundedCornerTreatment);
    }

    @VisibleForTesting
    public float getCornerRadius() {
        return this.cornerRadius;
    }

    @Override // com.google.android.material.shape.ShapeableDelegate
    public void invalidateClippingMethod(@NonNull View view) {
        this.cornerRadius = getDefaultCornerRadius();
        this.canUseViewOutline = isShapeRoundRect() || offsetZeroCornerEdgeBoundsIfPossible();
        view.setClipToOutline(!shouldUseCompatClipping());
        if (shouldUseCompatClipping()) {
            view.invalidate();
        } else {
            view.invalidateOutline();
        }
    }

    @Override // com.google.android.material.shape.ShapeableDelegate
    public boolean shouldUseCompatClipping() {
        return !this.canUseViewOutline || this.forceCompatClippingEnabled;
    }
}
