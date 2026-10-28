package com.google.android.material.shape;

import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import androidx.annotation.NonNull;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class InterpolateOnScrollPositionChangeHelper {
    private ScrollView containingScrollView;
    private MaterialShapeDrawable materialShapeDrawable;
    private View shapedView;
    private final int[] scrollLocation = new int[2];
    private final int[] containerLocation = new int[2];
    private final ViewTreeObserver.OnScrollChangedListener scrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() { // from class: com.google.android.material.shape.InterpolateOnScrollPositionChangeHelper.1
        @Override // android.view.ViewTreeObserver.OnScrollChangedListener
        public void onScrollChanged() {
            InterpolateOnScrollPositionChangeHelper.this.updateInterpolationForScreenPosition();
        }
    };

    public InterpolateOnScrollPositionChangeHelper(View view, MaterialShapeDrawable materialShapeDrawable, ScrollView scrollView) {
        this.shapedView = view;
        this.materialShapeDrawable = materialShapeDrawable;
        this.containingScrollView = scrollView;
    }

    public void setContainingScrollView(ScrollView scrollView) {
        this.containingScrollView = scrollView;
    }

    public void setMaterialShapeDrawable(MaterialShapeDrawable materialShapeDrawable) {
        this.materialShapeDrawable = materialShapeDrawable;
    }

    public void startListeningForScrollChanges(@NonNull ViewTreeObserver viewTreeObserver) {
        viewTreeObserver.addOnScrollChangedListener(this.scrollChangedListener);
    }

    public void stopListeningForScrollChanges(@NonNull ViewTreeObserver viewTreeObserver) {
        viewTreeObserver.removeOnScrollChangedListener(this.scrollChangedListener);
    }

    public void updateInterpolationForScreenPosition() {
        MaterialShapeDrawable materialShapeDrawable;
        float f2;
        ScrollView scrollView = this.containingScrollView;
        if (scrollView == null) {
            return;
        }
        if (scrollView.getChildCount() == 0) {
            throw new IllegalStateException("Scroll bar must contain a child to calculate interpolation.");
        }
        this.containingScrollView.getLocationInWindow(this.scrollLocation);
        this.containingScrollView.getChildAt(0).getLocationInWindow(this.containerLocation);
        int top = (this.shapedView.getTop() - this.scrollLocation[1]) + this.containerLocation[1];
        int height = this.shapedView.getHeight();
        int height2 = this.containingScrollView.getHeight();
        if (top >= 0) {
            int i2 = top + height;
            if (i2 > height2) {
                int i3 = i2 - height2;
                materialShapeDrawable = this.materialShapeDrawable;
                f2 = 1.0f - (i3 / height);
            } else if (this.materialShapeDrawable.getInterpolation() == 1.0f) {
                return;
            } else {
                this.materialShapeDrawable.setInterpolation(1.0f);
            }
            this.shapedView.invalidate();
        }
        materialShapeDrawable = this.materialShapeDrawable;
        f2 = (top / height) + 1.0f;
        materialShapeDrawable.setInterpolation(Math.max(0.0f, Math.min(1.0f, f2)));
        this.shapedView.invalidate();
    }
}
