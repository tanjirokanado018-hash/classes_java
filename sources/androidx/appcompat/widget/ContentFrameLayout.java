package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY})
public class ContentFrameLayout extends FrameLayout {
    private OnAttachListener mAttachListener;
    private final Rect mDecorPadding;
    private TypedValue mFixedHeightMajor;
    private TypedValue mFixedHeightMinor;
    private TypedValue mFixedWidthMajor;
    private TypedValue mFixedWidthMinor;
    private TypedValue mMinWidthMajor;
    private TypedValue mMinWidthMinor;

    public interface OnAttachListener {
        void onAttachedFromWindow();

        void onDetachedFromWindow();
    }

    public ContentFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void dispatchFitSystemWindows(Rect rect) {
        fitSystemWindows(rect);
    }

    public TypedValue getFixedHeightMajor() {
        if (this.mFixedHeightMajor == null) {
            this.mFixedHeightMajor = new TypedValue();
        }
        return this.mFixedHeightMajor;
    }

    public TypedValue getFixedHeightMinor() {
        if (this.mFixedHeightMinor == null) {
            this.mFixedHeightMinor = new TypedValue();
        }
        return this.mFixedHeightMinor;
    }

    public TypedValue getFixedWidthMajor() {
        if (this.mFixedWidthMajor == null) {
            this.mFixedWidthMajor = new TypedValue();
        }
        return this.mFixedWidthMajor;
    }

    public TypedValue getFixedWidthMinor() {
        if (this.mFixedWidthMinor == null) {
            this.mFixedWidthMinor = new TypedValue();
        }
        return this.mFixedWidthMinor;
    }

    public TypedValue getMinWidthMajor() {
        if (this.mMinWidthMajor == null) {
            this.mMinWidthMajor = new TypedValue();
        }
        return this.mMinWidthMajor;
    }

    public TypedValue getMinWidthMinor() {
        if (this.mMinWidthMinor == null) {
            this.mMinWidthMinor = new TypedValue();
        }
        return this.mMinWidthMinor;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        OnAttachListener onAttachListener = this.mAttachListener;
        if (onAttachListener != null) {
            onAttachListener.onAttachedFromWindow();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        OnAttachListener onAttachListener = this.mAttachListener;
        if (onAttachListener != null) {
            onAttachListener.onDetachedFromWindow();
        }
    }

    /* JADX WARN: Code duplicated, block: B:21:0x004a  */
    /* JADX WARN: Code duplicated, block: B:22:0x0060  */
    /* JADX WARN: Code duplicated, block: B:37:0x0086  */
    /* JADX WARN: Code duplicated, block: B:54:0x00cc  */
    /* JADX WARN: Code duplicated, block: B:56:0x00d6  */
    /* JADX WARN: Code duplicated, block: B:57:0x00db  */
    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i2, int i3) {
        boolean z2;
        int i4;
        int i5;
        float fraction;
        int i6;
        int i7;
        float fraction2;
        int i8;
        int i9;
        float fraction3;
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        boolean z3 = true;
        boolean z4 = displayMetrics.widthPixels < displayMetrics.heightPixels;
        int mode = View.MeasureSpec.getMode(i2);
        int mode2 = View.MeasureSpec.getMode(i3);
        if (mode != Integer.MIN_VALUE) {
            z2 = false;
        } else {
            TypedValue typedValue = z4 ? this.mFixedWidthMinor : this.mFixedWidthMajor;
            if (typedValue == null || (i8 = typedValue.type) == 0) {
                z2 = false;
            } else {
                if (i8 == 5) {
                    fraction3 = typedValue.getDimension(displayMetrics);
                } else {
                    if (i8 == 6) {
                        int i10 = displayMetrics.widthPixels;
                        fraction3 = typedValue.getFraction(i10, i10);
                    } else {
                        i9 = 0;
                    }
                    if (i9 > 0) {
                        Rect rect = this.mDecorPadding;
                        i2 = View.MeasureSpec.makeMeasureSpec(Math.min(i9 - (rect.left + rect.right), View.MeasureSpec.getSize(i2)), BasicMeasure.EXACTLY);
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                }
                i9 = (int) fraction3;
                if (i9 > 0) {
                    Rect rect2 = this.mDecorPadding;
                    i2 = View.MeasureSpec.makeMeasureSpec(Math.min(i9 - (rect2.left + rect2.right), View.MeasureSpec.getSize(i2)), BasicMeasure.EXACTLY);
                    z2 = true;
                } else {
                    z2 = false;
                }
            }
        }
        if (mode2 == Integer.MIN_VALUE) {
            TypedValue typedValue2 = z4 ? this.mFixedHeightMajor : this.mFixedHeightMinor;
            if (typedValue2 != null && (i6 = typedValue2.type) != 0) {
                if (i6 == 5) {
                    fraction2 = typedValue2.getDimension(displayMetrics);
                } else {
                    if (i6 == 6) {
                        int i11 = displayMetrics.heightPixels;
                        fraction2 = typedValue2.getFraction(i11, i11);
                    } else {
                        i7 = 0;
                    }
                    if (i7 > 0) {
                        Rect rect3 = this.mDecorPadding;
                        i3 = View.MeasureSpec.makeMeasureSpec(Math.min(i7 - (rect3.top + rect3.bottom), View.MeasureSpec.getSize(i3)), BasicMeasure.EXACTLY);
                    }
                }
                i7 = (int) fraction2;
                if (i7 > 0) {
                    Rect rect4 = this.mDecorPadding;
                    i3 = View.MeasureSpec.makeMeasureSpec(Math.min(i7 - (rect4.top + rect4.bottom), View.MeasureSpec.getSize(i3)), BasicMeasure.EXACTLY);
                }
            }
        }
        super.onMeasure(i2, i3);
        int measuredWidth = getMeasuredWidth();
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(measuredWidth, BasicMeasure.EXACTLY);
        if (z2 || mode != Integer.MIN_VALUE) {
            z3 = false;
        } else {
            TypedValue typedValue3 = z4 ? this.mMinWidthMinor : this.mMinWidthMajor;
            if (typedValue3 == null || (i4 = typedValue3.type) == 0) {
                z3 = false;
            } else {
                if (i4 == 5) {
                    fraction = typedValue3.getDimension(displayMetrics);
                } else {
                    if (i4 == 6) {
                        int i12 = displayMetrics.widthPixels;
                        fraction = typedValue3.getFraction(i12, i12);
                    } else {
                        i5 = 0;
                    }
                    if (i5 > 0) {
                        Rect rect5 = this.mDecorPadding;
                        i5 -= rect5.left + rect5.right;
                    }
                    if (measuredWidth < i5) {
                        iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i5, BasicMeasure.EXACTLY);
                    } else {
                        z3 = false;
                    }
                }
                i5 = (int) fraction;
                if (i5 > 0) {
                    Rect rect6 = this.mDecorPadding;
                    i5 -= rect6.left + rect6.right;
                }
                if (measuredWidth < i5) {
                    iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i5, BasicMeasure.EXACTLY);
                } else {
                    z3 = false;
                }
            }
        }
        if (z3) {
            super.onMeasure(iMakeMeasureSpec, i3);
        }
    }

    public void setAttachListener(OnAttachListener onAttachListener) {
        this.mAttachListener = onAttachListener;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void setDecorPadding(int i2, int i3, int i4, int i5) {
        this.mDecorPadding.set(i2, i3, i4, i5);
        if (isLaidOut()) {
            requestLayout();
        }
    }

    public ContentFrameLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ContentFrameLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mDecorPadding = new Rect();
    }
}
