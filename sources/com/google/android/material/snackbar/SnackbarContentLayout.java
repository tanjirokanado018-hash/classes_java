package com.google.android.material.snackbar;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.motion.MotionUtils;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class SnackbarContentLayout extends LinearLayout implements ContentViewCallback {
    private Button actionView;
    private final TimeInterpolator contentInterpolator;
    private int maxInlineActionWidth;
    private TextView messageView;

    public SnackbarContentLayout(@NonNull Context context) {
        this(context, null);
    }

    private static void updateTopBottomPadding(@NonNull View view, int i2, int i3) {
        if (ViewCompat.isPaddingRelative(view)) {
            ViewCompat.setPaddingRelative(view, ViewCompat.getPaddingStart(view), i2, ViewCompat.getPaddingEnd(view), i3);
        } else {
            view.setPadding(view.getPaddingLeft(), i2, view.getPaddingRight(), i3);
        }
    }

    private boolean updateViewsWithinLayout(int i2, int i3, int i4) {
        boolean z2;
        if (i2 != getOrientation()) {
            setOrientation(i2);
            z2 = true;
        } else {
            z2 = false;
        }
        if (this.messageView.getPaddingTop() == i3 && this.messageView.getPaddingBottom() == i4) {
            return z2;
        }
        updateTopBottomPadding(this.messageView, i3, i4);
        return true;
    }

    @Override // com.google.android.material.snackbar.ContentViewCallback
    public void animateContentIn(int i2, int i3) {
        this.messageView.setAlpha(0.0f);
        long j2 = i3;
        long j3 = i2;
        this.messageView.animate().alpha(1.0f).setDuration(j2).setInterpolator(this.contentInterpolator).setStartDelay(j3).start();
        if (this.actionView.getVisibility() == 0) {
            this.actionView.setAlpha(0.0f);
            this.actionView.animate().alpha(1.0f).setDuration(j2).setInterpolator(this.contentInterpolator).setStartDelay(j3).start();
        }
    }

    @Override // com.google.android.material.snackbar.ContentViewCallback
    public void animateContentOut(int i2, int i3) {
        this.messageView.setAlpha(1.0f);
        long j2 = i3;
        long j3 = i2;
        this.messageView.animate().alpha(0.0f).setDuration(j2).setInterpolator(this.contentInterpolator).setStartDelay(j3).start();
        if (this.actionView.getVisibility() == 0) {
            this.actionView.setAlpha(1.0f);
            this.actionView.animate().alpha(0.0f).setDuration(j2).setInterpolator(this.contentInterpolator).setStartDelay(j3).start();
        }
    }

    public Button getActionView() {
        return this.actionView;
    }

    public TextView getMessageView() {
        return this.messageView;
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.messageView = (TextView) findViewById(R.id.snackbar_text);
        this.actionView = (Button) findViewById(R.id.snackbar_action);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        if (getOrientation() == 1) {
            return;
        }
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.design_snackbar_padding_vertical_2lines);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.design_snackbar_padding_vertical);
        Layout layout = this.messageView.getLayout();
        boolean z2 = layout != null && layout.getLineCount() > 1;
        if (!z2 || this.maxInlineActionWidth <= 0 || this.actionView.getMeasuredWidth() <= this.maxInlineActionWidth) {
            if (!z2) {
                dimensionPixelSize = dimensionPixelSize2;
            }
            if (!updateViewsWithinLayout(0, dimensionPixelSize, dimensionPixelSize)) {
                return;
            }
        } else if (!updateViewsWithinLayout(1, dimensionPixelSize, dimensionPixelSize - dimensionPixelSize2)) {
            return;
        }
        super.onMeasure(i2, i3);
    }

    public void setMaxInlineActionWidth(int i2) {
        this.maxInlineActionWidth = i2;
    }

    public void updateActionTextColorAlphaIfNeeded(float f2) {
        if (f2 != 1.0f) {
            this.actionView.setTextColor(MaterialColors.layer(MaterialColors.getColor(this, R.attr.colorSurface), this.actionView.getCurrentTextColor(), f2));
        }
    }

    public SnackbarContentLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.contentInterpolator = MotionUtils.resolveThemeInterpolator(context, R.attr.motionEasingEmphasizedInterpolator, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
    }
}
