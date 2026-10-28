package androidx.constraintlayout.helper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.R;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class Layer extends ConstraintHelper {
    private static final String TAG = "Layer";
    private boolean mApplyElevationOnAttach;
    private boolean mApplyVisibilityOnAttach;
    protected float mComputedCenterX;
    protected float mComputedCenterY;
    protected float mComputedMaxX;
    protected float mComputedMaxY;
    protected float mComputedMinX;
    protected float mComputedMinY;
    ConstraintLayout mContainer;
    private float mGroupRotateAngle;
    boolean mNeedBounds;
    private float mRotationCenterX;
    private float mRotationCenterY;
    private float mScaleX;
    private float mScaleY;
    private float mShiftX;
    private float mShiftY;
    View[] mViews;

    public Layer(Context context) {
        super(context);
        this.mRotationCenterX = Float.NaN;
        this.mRotationCenterY = Float.NaN;
        this.mGroupRotateAngle = Float.NaN;
        this.mScaleX = 1.0f;
        this.mScaleY = 1.0f;
        this.mComputedCenterX = Float.NaN;
        this.mComputedCenterY = Float.NaN;
        this.mComputedMaxX = Float.NaN;
        this.mComputedMaxY = Float.NaN;
        this.mComputedMinX = Float.NaN;
        this.mComputedMinY = Float.NaN;
        this.mNeedBounds = true;
        this.mViews = null;
        this.mShiftX = 0.0f;
        this.mShiftY = 0.0f;
    }

    private void reCacheViews() {
        int i2;
        if (this.mContainer == null || (i2 = this.mCount) == 0) {
            return;
        }
        View[] viewArr = this.mViews;
        if (viewArr == null || viewArr.length != i2) {
            this.mViews = new View[i2];
        }
        for (int i3 = 0; i3 < this.mCount; i3++) {
            this.mViews[i3] = this.mContainer.getViewById(this.mIds[i3]);
        }
    }

    private void transform() {
        if (this.mContainer == null) {
            return;
        }
        if (this.mViews == null) {
            reCacheViews();
        }
        calcCenters();
        double radians = Math.toRadians(this.mGroupRotateAngle);
        float fSin = (float) Math.sin(radians);
        float fCos = (float) Math.cos(radians);
        float f2 = this.mScaleX;
        float f3 = f2 * fCos;
        float f4 = this.mScaleY;
        float f5 = (-f4) * fSin;
        float f6 = f2 * fSin;
        float f7 = f4 * fCos;
        for (int i2 = 0; i2 < this.mCount; i2++) {
            View view = this.mViews[i2];
            int right = (view.getRight() + view.getLeft()) / 2;
            int bottom = (view.getBottom() + view.getTop()) / 2;
            float f8 = right - this.mComputedCenterX;
            float f9 = bottom - this.mComputedCenterY;
            float f10 = (((f5 * f9) + (f3 * f8)) - f8) + this.mShiftX;
            float f11 = (((f7 * f9) + (f8 * f6)) - f9) + this.mShiftY;
            view.setTranslationX(f10);
            view.setTranslationY(f11);
            view.setScaleY(this.mScaleY);
            view.setScaleX(this.mScaleX);
            view.setRotation(this.mGroupRotateAngle);
        }
    }

    public void calcCenters() {
        if (this.mContainer == null) {
            return;
        }
        if (this.mNeedBounds || Float.isNaN(this.mComputedCenterX) || Float.isNaN(this.mComputedCenterY)) {
            if (!Float.isNaN(this.mRotationCenterX) && !Float.isNaN(this.mRotationCenterY)) {
                this.mComputedCenterY = this.mRotationCenterY;
                this.mComputedCenterX = this.mRotationCenterX;
                return;
            }
            View[] views = getViews(this.mContainer);
            int left = views[0].getLeft();
            int top = views[0].getTop();
            int right = views[0].getRight();
            int bottom = views[0].getBottom();
            for (int i2 = 0; i2 < this.mCount; i2++) {
                View view = views[i2];
                left = Math.min(left, view.getLeft());
                top = Math.min(top, view.getTop());
                right = Math.max(right, view.getRight());
                bottom = Math.max(bottom, view.getBottom());
            }
            this.mComputedMaxX = right;
            this.mComputedMaxY = bottom;
            this.mComputedMinX = left;
            this.mComputedMinY = top;
            this.mComputedCenterX = Float.isNaN(this.mRotationCenterX) ? (left + right) / 2 : this.mRotationCenterX;
            this.mComputedCenterY = Float.isNaN(this.mRotationCenterY) ? (top + bottom) / 2 : this.mRotationCenterY;
        }
    }

    @Override // androidx.constraintlayout.widget.ConstraintHelper
    public void init(AttributeSet attributeSet) {
        super.init(attributeSet);
        this.mUseViewMeasure = false;
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.ConstraintLayout_Layout);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i2);
                if (index == R.styleable.ConstraintLayout_Layout_android_visibility) {
                    this.mApplyVisibilityOnAttach = true;
                } else if (index == R.styleable.ConstraintLayout_Layout_android_elevation) {
                    this.mApplyElevationOnAttach = true;
                }
            }
        }
    }

    @Override // androidx.constraintlayout.widget.ConstraintHelper, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mContainer = (ConstraintLayout) getParent();
        if (this.mApplyVisibilityOnAttach || this.mApplyElevationOnAttach) {
            int visibility = getVisibility();
            float elevation = getElevation();
            for (int i2 = 0; i2 < this.mCount; i2++) {
                View viewById = this.mContainer.getViewById(this.mIds[i2]);
                if (viewById != null) {
                    if (this.mApplyVisibilityOnAttach) {
                        viewById.setVisibility(visibility);
                    }
                    if (this.mApplyElevationOnAttach && elevation > 0.0f) {
                        viewById.setTranslationZ(viewById.getTranslationZ() + elevation);
                    }
                }
            }
        }
    }

    @Override // android.view.View
    public void setElevation(float f2) {
        super.setElevation(f2);
        applyLayoutFeatures();
    }

    @Override // android.view.View
    public void setPivotX(float f2) {
        this.mRotationCenterX = f2;
        transform();
    }

    @Override // android.view.View
    public void setPivotY(float f2) {
        this.mRotationCenterY = f2;
        transform();
    }

    @Override // android.view.View
    public void setRotation(float f2) {
        this.mGroupRotateAngle = f2;
        transform();
    }

    @Override // android.view.View
    public void setScaleX(float f2) {
        this.mScaleX = f2;
        transform();
    }

    @Override // android.view.View
    public void setScaleY(float f2) {
        this.mScaleY = f2;
        transform();
    }

    @Override // android.view.View
    public void setTranslationX(float f2) {
        this.mShiftX = f2;
        transform();
    }

    @Override // android.view.View
    public void setTranslationY(float f2) {
        this.mShiftY = f2;
        transform();
    }

    @Override // android.view.View
    public void setVisibility(int i2) {
        super.setVisibility(i2);
        applyLayoutFeatures();
    }

    @Override // androidx.constraintlayout.widget.ConstraintHelper
    public void updatePostLayout(ConstraintLayout constraintLayout) {
        reCacheViews();
        this.mComputedCenterX = Float.NaN;
        this.mComputedCenterY = Float.NaN;
        ConstraintWidget constraintWidget = ((ConstraintLayout.LayoutParams) getLayoutParams()).getConstraintWidget();
        constraintWidget.setWidth(0);
        constraintWidget.setHeight(0);
        calcCenters();
        layout(((int) this.mComputedMinX) - getPaddingLeft(), ((int) this.mComputedMinY) - getPaddingTop(), getPaddingRight() + ((int) this.mComputedMaxX), getPaddingBottom() + ((int) this.mComputedMaxY));
        if (Float.isNaN(this.mGroupRotateAngle)) {
            return;
        }
        transform();
    }

    @Override // androidx.constraintlayout.widget.ConstraintHelper
    public void updatePreDraw(ConstraintLayout constraintLayout) {
        this.mContainer = constraintLayout;
        float rotation = getRotation();
        if (rotation == 0.0f && Float.isNaN(this.mGroupRotateAngle)) {
            return;
        }
        this.mGroupRotateAngle = rotation;
    }

    public Layer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRotationCenterX = Float.NaN;
        this.mRotationCenterY = Float.NaN;
        this.mGroupRotateAngle = Float.NaN;
        this.mScaleX = 1.0f;
        this.mScaleY = 1.0f;
        this.mComputedCenterX = Float.NaN;
        this.mComputedCenterY = Float.NaN;
        this.mComputedMaxX = Float.NaN;
        this.mComputedMaxY = Float.NaN;
        this.mComputedMinX = Float.NaN;
        this.mComputedMinY = Float.NaN;
        this.mNeedBounds = true;
        this.mViews = null;
        this.mShiftX = 0.0f;
        this.mShiftY = 0.0f;
    }

    public Layer(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mRotationCenterX = Float.NaN;
        this.mRotationCenterY = Float.NaN;
        this.mGroupRotateAngle = Float.NaN;
        this.mScaleX = 1.0f;
        this.mScaleY = 1.0f;
        this.mComputedCenterX = Float.NaN;
        this.mComputedCenterY = Float.NaN;
        this.mComputedMaxX = Float.NaN;
        this.mComputedMaxY = Float.NaN;
        this.mComputedMinX = Float.NaN;
        this.mComputedMinY = Float.NaN;
        this.mNeedBounds = true;
        this.mViews = null;
        this.mShiftX = 0.0f;
        this.mShiftY = 0.0f;
    }
}
