package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.appcompat.R;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class ButtonBarLayout extends LinearLayout {
    private static final int PEEK_BUTTON_DP = 16;
    private boolean mAllowStacking;
    private int mLastWidthSize;
    private boolean mStacked;

    public ButtonBarLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLastWidthSize = -1;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ButtonBarLayout);
        ViewCompat.saveAttributeDataForStyleable(this, context, R.styleable.ButtonBarLayout, attributeSet, typedArrayObtainStyledAttributes, 0, 0);
        this.mAllowStacking = typedArrayObtainStyledAttributes.getBoolean(R.styleable.ButtonBarLayout_allowStacking, true);
        typedArrayObtainStyledAttributes.recycle();
        if (getOrientation() == 1) {
            setStacked(this.mAllowStacking);
        }
    }

    private int getNextVisibleChildIndex(int i2) {
        int childCount = getChildCount();
        while (i2 < childCount) {
            if (getChildAt(i2).getVisibility() == 0) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    private boolean isStacked() {
        return this.mStacked;
    }

    private void setStacked(boolean z2) {
        if (this.mStacked != z2) {
            if (!z2 || this.mAllowStacking) {
                this.mStacked = z2;
                setOrientation(z2 ? 1 : 0);
                setGravity(z2 ? GravityCompat.END : 80);
                View viewFindViewById = findViewById(R.id.spacer);
                if (viewFindViewById != null) {
                    viewFindViewById.setVisibility(z2 ? 8 : 4);
                }
                for (int childCount = getChildCount() - 2; childCount >= 0; childCount--) {
                    bringChildToFront(getChildAt(childCount));
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:27:0x0058  */
    /* JADX WARN: Code duplicated, block: B:29:0x0077  */
    /* JADX WARN: Code duplicated, block: B:31:0x007e  */
    /* JADX WARN: Code duplicated, block: B:32:0x0099  */
    /* JADX WARN: Code duplicated, block: B:33:0x009b  */
    /* JADX WARN: Code duplicated, block: B:36:0x00a7  */
    /* JADX WARN: Code duplicated, block: B:38:0x00ac  */
    /* JADX WARN: Code duplicated, block: B:40:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:41:? A[RETURN, SYNTHETIC] */
    @Override // android.widget.LinearLayout, android.view.View
    public void onMeasure(int i2, int i3) {
        int iMakeMeasureSpec;
        boolean z2;
        int nextVisibleChildIndex;
        int measuredHeight;
        int nextVisibleChildIndex2;
        int size = View.MeasureSpec.getSize(i2);
        int paddingBottom = 0;
        if (this.mAllowStacking) {
            if (size > this.mLastWidthSize && isStacked()) {
                setStacked(false);
            }
            this.mLastWidthSize = size;
        }
        if (isStacked() || View.MeasureSpec.getMode(i2) != 1073741824) {
            iMakeMeasureSpec = i2;
            z2 = false;
        } else {
            iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(size, Integer.MIN_VALUE);
            z2 = true;
        }
        super.onMeasure(iMakeMeasureSpec, i3);
        if (!this.mAllowStacking || isStacked() || (getMeasuredWidthAndState() & ViewCompat.MEASURED_STATE_MASK) != 16777216) {
            if (z2) {
            }
            nextVisibleChildIndex = getNextVisibleChildIndex(0);
            if (nextVisibleChildIndex >= 0) {
                View childAt = getChildAt(nextVisibleChildIndex);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams();
                measuredHeight = childAt.getMeasuredHeight() + getPaddingTop() + layoutParams.topMargin + layoutParams.bottomMargin;
                if (isStacked()) {
                    nextVisibleChildIndex2 = getNextVisibleChildIndex(nextVisibleChildIndex + 1);
                    if (nextVisibleChildIndex2 >= 0) {
                        paddingBottom = getChildAt(nextVisibleChildIndex2).getPaddingTop() + ((int) (getResources().getDisplayMetrics().density * 16.0f)) + measuredHeight;
                    } else {
                        paddingBottom = measuredHeight;
                    }
                } else {
                    paddingBottom = getPaddingBottom() + measuredHeight;
                }
            }
            if (ViewCompat.getMinimumHeight(this) != paddingBottom) {
                setMinimumHeight(paddingBottom);
                if (i3 == 0) {
                    super.onMeasure(i2, i3);
                }
            }
        }
        setStacked(true);
        super.onMeasure(i2, i3);
        nextVisibleChildIndex = getNextVisibleChildIndex(0);
        if (nextVisibleChildIndex >= 0) {
            View childAt2 = getChildAt(nextVisibleChildIndex);
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) childAt2.getLayoutParams();
            measuredHeight = childAt2.getMeasuredHeight() + getPaddingTop() + layoutParams2.topMargin + layoutParams2.bottomMargin;
            if (isStacked()) {
                nextVisibleChildIndex2 = getNextVisibleChildIndex(nextVisibleChildIndex + 1);
                if (nextVisibleChildIndex2 >= 0) {
                    paddingBottom = getChildAt(nextVisibleChildIndex2).getPaddingTop() + ((int) (getResources().getDisplayMetrics().density * 16.0f)) + measuredHeight;
                } else {
                    paddingBottom = measuredHeight;
                }
            } else {
                paddingBottom = getPaddingBottom() + measuredHeight;
            }
        }
        if (ViewCompat.getMinimumHeight(this) != paddingBottom) {
            setMinimumHeight(paddingBottom);
            if (i3 == 0) {
                super.onMeasure(i2, i3);
            }
        }
    }

    public void setAllowStacking(boolean z2) {
        if (this.mAllowStacking != z2) {
            this.mAllowStacking = z2;
            if (!z2 && isStacked()) {
                setStacked(false);
            }
            requestLayout();
        }
    }
}
