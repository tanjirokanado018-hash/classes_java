package com.google.android.material.carousel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.core.graphics.ColorUtils;
import androidx.core.math.MathUtils;
import androidx.core.util.Preconditions;
import androidx.lifecycle.f;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class CarouselLayoutManager extends RecyclerView.LayoutManager implements Carousel, RecyclerView.SmoothScroller.ScrollVectorProvider {
    public static final int ALIGNMENT_CENTER = 1;
    public static final int ALIGNMENT_START = 0;
    public static final int HORIZONTAL = 0;
    private static final String TAG = "CarouselLayoutManager";
    public static final int VERTICAL = 1;
    private int carouselAlignment;

    @NonNull
    private CarouselStrategy carouselStrategy;
    private int currentEstimatedPosition;
    private int currentFillStartPosition;

    @Nullable
    private KeylineState currentKeylineState;
    private final DebugItemDecoration debugItemDecoration;
    private boolean isDebuggingEnabled;

    @Nullable
    private KeylineStateList keylineStateList;

    @Nullable
    private Map<Integer, KeylineState> keylineStatePositionMap;
    private int lastItemCount;

    @VisibleForTesting
    int maxScroll;

    @VisibleForTesting
    int minScroll;
    private CarouselOrientationHelper orientationHelper;
    private final View.OnLayoutChangeListener recyclerViewSizeChangeListener;

    @VisibleForTesting
    int scrollOffset;

    public static final class ChildCalculations {
        final float center;
        final View child;
        final float offsetCenter;
        final KeylineRange range;

        public ChildCalculations(View view, float f2, float f3, KeylineRange keylineRange) {
            this.child = view;
            this.center = f2;
            this.offsetCenter = f3;
            this.range = keylineRange;
        }
    }

    public static class DebugItemDecoration extends RecyclerView.ItemDecoration {
        private List<KeylineState.Keyline> keylines;
        private final Paint linePaint;

        public DebugItemDecoration() {
            Paint paint = new Paint();
            this.linePaint = paint;
            this.keylines = Collections.unmodifiableList(new ArrayList());
            paint.setStrokeWidth(5.0f);
            paint.setColor(-65281);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
            float parentLeft;
            float parentTop;
            float parentRight;
            float parentBottom;
            super.onDrawOver(canvas, recyclerView, state);
            this.linePaint.setStrokeWidth(recyclerView.getResources().getDimension(R.dimen.m3_carousel_debug_keyline_width));
            for (KeylineState.Keyline keyline : this.keylines) {
                this.linePaint.setColor(ColorUtils.blendARGB(-65281, -16776961, keyline.mask));
                if (((CarouselLayoutManager) recyclerView.getLayoutManager()).isHorizontal()) {
                    parentLeft = keyline.locOffset;
                    parentTop = ((CarouselLayoutManager) recyclerView.getLayoutManager()).getParentTop();
                    parentRight = keyline.locOffset;
                    parentBottom = ((CarouselLayoutManager) recyclerView.getLayoutManager()).getParentBottom();
                } else {
                    parentLeft = ((CarouselLayoutManager) recyclerView.getLayoutManager()).getParentLeft();
                    parentTop = keyline.locOffset;
                    parentRight = ((CarouselLayoutManager) recyclerView.getLayoutManager()).getParentRight();
                    parentBottom = keyline.locOffset;
                }
                canvas.drawLine(parentLeft, parentTop, parentRight, parentBottom, this.linePaint);
            }
        }

        public void setKeylines(List<KeylineState.Keyline> list) {
            this.keylines = Collections.unmodifiableList(list);
        }
    }

    public static class KeylineRange {
        final KeylineState.Keyline leftOrTop;
        final KeylineState.Keyline rightOrBottom;

        public KeylineRange(KeylineState.Keyline keyline, KeylineState.Keyline keyline2) {
            Preconditions.checkArgument(keyline.loc <= keyline2.loc);
            this.leftOrTop = keyline;
            this.rightOrBottom = keyline2;
        }
    }

    public static class LayoutDirection {
        private static final int INVALID_LAYOUT = Integer.MIN_VALUE;
        private static final int LAYOUT_END = 1;
        private static final int LAYOUT_START = -1;

        private LayoutDirection() {
        }
    }

    public CarouselLayoutManager() {
        this(new MultiBrowseCarouselStrategy());
    }

    private void addAndLayoutView(View view, int i2, ChildCalculations childCalculations) {
        float itemSize = this.currentKeylineState.getItemSize() / 2.0f;
        addView(view, i2);
        float f2 = childCalculations.offsetCenter;
        this.orientationHelper.layoutDecoratedWithMargins(view, (int) (f2 - itemSize), (int) (f2 + itemSize));
        updateChildMaskForLocation(view, childCalculations.center, childCalculations.range);
    }

    private float addEnd(float f2, float f3) {
        return isLayoutRtl() ? f2 - f3 : f2 + f3;
    }

    private float addStart(float f2, float f3) {
        return isLayoutRtl() ? f2 + f3 : f2 - f3;
    }

    private void addViewAtPosition(@NonNull RecyclerView.Recycler recycler, int i2, int i3) {
        if (i2 < 0 || i2 >= getItemCount()) {
            return;
        }
        ChildCalculations childCalculationsMakeChildCalculations = makeChildCalculations(recycler, calculateChildStartForFill(i2), i2);
        addAndLayoutView(childCalculationsMakeChildCalculations.child, i3, childCalculationsMakeChildCalculations);
    }

    private void addViewsEnd(RecyclerView.Recycler recycler, RecyclerView.State state, int i2) {
        float fCalculateChildStartForFill = calculateChildStartForFill(i2);
        while (i2 < state.getItemCount()) {
            ChildCalculations childCalculationsMakeChildCalculations = makeChildCalculations(recycler, fCalculateChildStartForFill, i2);
            if (isLocOffsetOutOfFillBoundsEnd(childCalculationsMakeChildCalculations.offsetCenter, childCalculationsMakeChildCalculations.range)) {
                return;
            }
            fCalculateChildStartForFill = addEnd(fCalculateChildStartForFill, this.currentKeylineState.getItemSize());
            if (!isLocOffsetOutOfFillBoundsStart(childCalculationsMakeChildCalculations.offsetCenter, childCalculationsMakeChildCalculations.range)) {
                addAndLayoutView(childCalculationsMakeChildCalculations.child, -1, childCalculationsMakeChildCalculations);
            }
            i2++;
        }
    }

    private void addViewsStart(RecyclerView.Recycler recycler, int i2) {
        float fCalculateChildStartForFill = calculateChildStartForFill(i2);
        while (i2 >= 0) {
            ChildCalculations childCalculationsMakeChildCalculations = makeChildCalculations(recycler, fCalculateChildStartForFill, i2);
            if (isLocOffsetOutOfFillBoundsStart(childCalculationsMakeChildCalculations.offsetCenter, childCalculationsMakeChildCalculations.range)) {
                return;
            }
            fCalculateChildStartForFill = addStart(fCalculateChildStartForFill, this.currentKeylineState.getItemSize());
            if (!isLocOffsetOutOfFillBoundsEnd(childCalculationsMakeChildCalculations.offsetCenter, childCalculationsMakeChildCalculations.range)) {
                addAndLayoutView(childCalculationsMakeChildCalculations.child, 0, childCalculationsMakeChildCalculations);
            }
            i2--;
        }
    }

    private float calculateChildOffsetCenterForLocation(View view, float f2, KeylineRange keylineRange) {
        KeylineState.Keyline keyline = keylineRange.leftOrTop;
        float f3 = keyline.locOffset;
        KeylineState.Keyline keyline2 = keylineRange.rightOrBottom;
        float fLerp = AnimationUtils.lerp(f3, keyline2.locOffset, keyline.loc, keyline2.loc, f2);
        if (keylineRange.rightOrBottom != this.currentKeylineState.getFirstKeyline() && keylineRange.leftOrTop != this.currentKeylineState.getLastKeyline()) {
            return fLerp;
        }
        float maskMargins = this.orientationHelper.getMaskMargins((RecyclerView.LayoutParams) view.getLayoutParams()) / this.currentKeylineState.getItemSize();
        KeylineState.Keyline keyline3 = keylineRange.rightOrBottom;
        return fLerp + (((1.0f - keyline3.mask) + maskMargins) * (f2 - keyline3.loc));
    }

    private float calculateChildStartForFill(int i2) {
        return addEnd(getParentStart() - this.scrollOffset, this.currentKeylineState.getItemSize() * i2);
    }

    private int calculateEndScroll(RecyclerView.State state, KeylineStateList keylineStateList) {
        boolean zIsLayoutRtl = isLayoutRtl();
        KeylineState startState = zIsLayoutRtl ? keylineStateList.getStartState() : keylineStateList.getEndState();
        KeylineState.Keyline firstFocalKeyline = zIsLayoutRtl ? startState.getFirstFocalKeyline() : startState.getLastFocalKeyline();
        int itemCount = (int) (((((state.getItemCount() - 1) * startState.getItemSize()) * (zIsLayoutRtl ? -1.0f : 1.0f)) - (firstFocalKeyline.loc - getParentStart())) + (getParentEnd() - firstFocalKeyline.loc) + (zIsLayoutRtl ? -firstFocalKeyline.leftOrTopPaddingShift : firstFocalKeyline.rightOrBottomPaddingShift));
        return zIsLayoutRtl ? Math.min(0, itemCount) : Math.max(0, itemCount);
    }

    private static int calculateShouldScrollBy(int i2, int i3, int i4, int i5) {
        int i6 = i3 + i2;
        if (i6 < i4) {
            return i4 - i3;
        }
        return i6 > i5 ? i5 - i3 : i2;
    }

    private int calculateStartScroll(@NonNull KeylineStateList keylineStateList) {
        boolean zIsLayoutRtl = isLayoutRtl();
        KeylineState endState = zIsLayoutRtl ? keylineStateList.getEndState() : keylineStateList.getStartState();
        return (int) (getParentStart() - addStart((zIsLayoutRtl ? endState.getLastFocalKeyline() : endState.getFirstFocalKeyline()).loc, endState.getItemSize() / 2.0f));
    }

    private int convertFocusDirectionToLayoutDirection(int i2) {
        int orientation = getOrientation();
        if (i2 == 1) {
            return -1;
        }
        if (i2 == 2) {
            return 1;
        }
        if (i2 == 17) {
            if (orientation == 0) {
                return isLayoutRtl() ? 1 : -1;
            }
            return Integer.MIN_VALUE;
        }
        if (i2 == 33) {
            return orientation == 1 ? -1 : Integer.MIN_VALUE;
        }
        if (i2 != 66) {
            return (i2 == 130 && orientation == 1) ? 1 : Integer.MIN_VALUE;
        }
        if (orientation == 0) {
            return isLayoutRtl() ? -1 : 1;
        }
        return Integer.MIN_VALUE;
    }

    private void fill(RecyclerView.Recycler recycler, RecyclerView.State state) {
        removeAndRecycleOutOfBoundsViews(recycler);
        if (getChildCount() == 0) {
            addViewsStart(recycler, this.currentFillStartPosition - 1);
            addViewsEnd(recycler, state, this.currentFillStartPosition);
        } else {
            int position = getPosition(getChildAt(0));
            int position2 = getPosition(getChildAt(getChildCount() - 1));
            addViewsStart(recycler, position - 1);
            addViewsEnd(recycler, state, position2 + 1);
        }
        validateChildOrderIfDebugging();
    }

    private View getChildClosestToEnd() {
        return getChildAt(isLayoutRtl() ? 0 : getChildCount() - 1);
    }

    private View getChildClosestToStart() {
        return getChildAt(isLayoutRtl() ? getChildCount() - 1 : 0);
    }

    private int getContainerSize() {
        return isHorizontal() ? getContainerWidth() : getContainerHeight();
    }

    private float getDecoratedCenterWithMargins(View view) {
        Rect rect = new Rect();
        super.getDecoratedBoundsWithMargins(view, rect);
        return isHorizontal() ? rect.centerX() : rect.centerY();
    }

    private int getItemMargins() {
        int i2;
        int i3;
        if (getChildCount() <= 0) {
            return 0;
        }
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) getChildAt(0).getLayoutParams();
        if (this.orientationHelper.orientation == 0) {
            i2 = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
            i3 = ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
        } else {
            i2 = ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
            i3 = ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        }
        return i2 + i3;
    }

    private KeylineState getKeylineStateForPosition(int i2) {
        KeylineState keylineState;
        Map<Integer, KeylineState> map = this.keylineStatePositionMap;
        return (map == null || (keylineState = map.get(Integer.valueOf(MathUtils.clamp(i2, 0, Math.max(0, getItemCount() + (-1)))))) == null) ? this.keylineStateList.getDefaultState() : keylineState;
    }

    private int getLeftOrTopPaddingForKeylineShift() {
        if (getClipToPadding() || !this.carouselStrategy.isContained()) {
            return 0;
        }
        return getOrientation() == 1 ? getPaddingTop() : getPaddingLeft();
    }

    private float getMaskedItemSizeForLocOffset(float f2, KeylineRange keylineRange) {
        KeylineState.Keyline keyline = keylineRange.leftOrTop;
        float f3 = keyline.maskedItemSize;
        KeylineState.Keyline keyline2 = keylineRange.rightOrBottom;
        return AnimationUtils.lerp(f3, keyline2.maskedItemSize, keyline.locOffset, keyline2.locOffset, f2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getParentBottom() {
        return this.orientationHelper.getParentBottom();
    }

    private int getParentEnd() {
        return this.orientationHelper.getParentEnd();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getParentLeft() {
        return this.orientationHelper.getParentLeft();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getParentRight() {
        return this.orientationHelper.getParentRight();
    }

    private int getParentStart() {
        return this.orientationHelper.getParentStart();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getParentTop() {
        return this.orientationHelper.getParentTop();
    }

    private int getRightOrBottomPaddingForKeylineShift() {
        if (getClipToPadding() || !this.carouselStrategy.isContained()) {
            return 0;
        }
        return getOrientation() == 1 ? getPaddingBottom() : getPaddingRight();
    }

    private int getScrollOffsetForPosition(int i2, KeylineState keylineState) {
        if (isLayoutRtl()) {
            return (int) (((getContainerSize() - keylineState.getLastFocalKeyline().loc) - (i2 * keylineState.getItemSize())) - (keylineState.getItemSize() / 2.0f));
        }
        return (int) ((keylineState.getItemSize() / 2.0f) + ((i2 * keylineState.getItemSize()) - keylineState.getFirstFocalKeyline().loc));
    }

    private int getSmallestScrollOffsetToFocalKeyline(int i2, @NonNull KeylineState keylineState) {
        int i3 = Integer.MAX_VALUE;
        for (KeylineState.Keyline keyline : keylineState.getFocalKeylines()) {
            float itemSize = (keylineState.getItemSize() / 2.0f) + (i2 * keylineState.getItemSize());
            int containerSize = (isLayoutRtl() ? (int) ((getContainerSize() - keyline.loc) - itemSize) : (int) (itemSize - keyline.loc)) - this.scrollOffset;
            if (Math.abs(i3) > Math.abs(containerSize)) {
                i3 = containerSize;
            }
        }
        return i3;
    }

    private static KeylineRange getSurroundingKeylineRange(List<KeylineState.Keyline> list, float f2, boolean z2) {
        float f3 = Float.MAX_VALUE;
        float f4 = Float.MAX_VALUE;
        float f5 = Float.MAX_VALUE;
        float f6 = -3.4028235E38f;
        int i2 = -1;
        int i3 = -1;
        int i4 = -1;
        int i5 = -1;
        for (int i6 = 0; i6 < list.size(); i6++) {
            KeylineState.Keyline keyline = list.get(i6);
            float f7 = z2 ? keyline.locOffset : keyline.loc;
            float fAbs = Math.abs(f7 - f2);
            if (f7 <= f2 && fAbs <= f3) {
                i2 = i6;
                f3 = fAbs;
            }
            if (f7 > f2 && fAbs <= f4) {
                i4 = i6;
                f4 = fAbs;
            }
            if (f7 <= f5) {
                i3 = i6;
                f5 = f7;
            }
            if (f7 > f6) {
                i5 = i6;
                f6 = f7;
            }
        }
        if (i2 == -1) {
            i2 = i3;
        }
        if (i4 == -1) {
            i4 = i5;
        }
        return new KeylineRange(list.get(i2), list.get(i4));
    }

    private boolean isLocOffsetOutOfFillBoundsEnd(float f2, KeylineRange keylineRange) {
        float fAddStart = addStart(f2, getMaskedItemSizeForLocOffset(f2, keylineRange) / 2.0f);
        if (isLayoutRtl()) {
            if (fAddStart >= 0.0f) {
                return false;
            }
        } else if (fAddStart <= getContainerSize()) {
            return false;
        }
        return true;
    }

    private boolean isLocOffsetOutOfFillBoundsStart(float f2, KeylineRange keylineRange) {
        float fAddEnd = addEnd(f2, getMaskedItemSizeForLocOffset(f2, keylineRange) / 2.0f);
        if (isLayoutRtl()) {
            if (fAddEnd <= getContainerSize()) {
                return false;
            }
        } else if (fAddEnd >= 0.0f) {
            return false;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        if (i2 == i6 && i3 == i7 && i4 == i8 && i5 == i9) {
            return;
        }
        view.post(new f(1, this));
    }

    private void logChildrenIfDebugging() {
        if (this.isDebuggingEnabled && Log.isLoggable(TAG, 3)) {
            for (int i2 = 0; i2 < getChildCount(); i2++) {
                View childAt = getChildAt(i2);
                getDecoratedCenterWithMargins(childAt);
                getPosition(childAt);
            }
        }
    }

    private ChildCalculations makeChildCalculations(RecyclerView.Recycler recycler, float f2, int i2) {
        View viewForPosition = recycler.getViewForPosition(i2);
        measureChildWithMargins(viewForPosition, 0, 0);
        float fAddEnd = addEnd(f2, this.currentKeylineState.getItemSize() / 2.0f);
        KeylineRange surroundingKeylineRange = getSurroundingKeylineRange(this.currentKeylineState.getKeylines(), fAddEnd, false);
        return new ChildCalculations(viewForPosition, fAddEnd, calculateChildOffsetCenterForLocation(viewForPosition, fAddEnd, surroundingKeylineRange), surroundingKeylineRange);
    }

    private float offsetChild(View view, float f2, float f3, Rect rect) {
        float fAddEnd = addEnd(f2, f3);
        KeylineRange surroundingKeylineRange = getSurroundingKeylineRange(this.currentKeylineState.getKeylines(), fAddEnd, false);
        float fCalculateChildOffsetCenterForLocation = calculateChildOffsetCenterForLocation(view, fAddEnd, surroundingKeylineRange);
        super.getDecoratedBoundsWithMargins(view, rect);
        updateChildMaskForLocation(view, fAddEnd, surroundingKeylineRange);
        this.orientationHelper.offsetChild(view, rect, f3, fCalculateChildOffsetCenterForLocation);
        return fCalculateChildOffsetCenterForLocation;
    }

    private void recalculateKeylineStateList(RecyclerView.Recycler recycler) {
        View viewForPosition = recycler.getViewForPosition(0);
        measureChildWithMargins(viewForPosition, 0, 0);
        KeylineState keylineStateOnFirstChildMeasuredWithMargins = this.carouselStrategy.onFirstChildMeasuredWithMargins(this, viewForPosition);
        if (isLayoutRtl()) {
            keylineStateOnFirstChildMeasuredWithMargins = KeylineState.reverse(keylineStateOnFirstChildMeasuredWithMargins, getContainerSize());
        }
        this.keylineStateList = KeylineStateList.from(this, keylineStateOnFirstChildMeasuredWithMargins, getItemMargins(), getLeftOrTopPaddingForKeylineShift(), getRightOrBottomPaddingForKeylineShift());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshKeylineState() {
        this.keylineStateList = null;
        requestLayout();
    }

    private void removeAndRecycleOutOfBoundsViews(RecyclerView.Recycler recycler) {
        while (getChildCount() > 0) {
            View childAt = getChildAt(0);
            float decoratedCenterWithMargins = getDecoratedCenterWithMargins(childAt);
            if (!isLocOffsetOutOfFillBoundsStart(decoratedCenterWithMargins, getSurroundingKeylineRange(this.currentKeylineState.getKeylines(), decoratedCenterWithMargins, true))) {
                break;
            } else {
                removeAndRecycleView(childAt, recycler);
            }
        }
        while (getChildCount() - 1 >= 0) {
            View childAt2 = getChildAt(getChildCount() - 1);
            float decoratedCenterWithMargins2 = getDecoratedCenterWithMargins(childAt2);
            if (!isLocOffsetOutOfFillBoundsEnd(decoratedCenterWithMargins2, getSurroundingKeylineRange(this.currentKeylineState.getKeylines(), decoratedCenterWithMargins2, true))) {
                return;
            } else {
                removeAndRecycleView(childAt2, recycler);
            }
        }
    }

    private int scrollBy(int i2, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getChildCount() == 0 || i2 == 0) {
            return 0;
        }
        if (this.keylineStateList == null) {
            recalculateKeylineStateList(recycler);
        }
        int iCalculateShouldScrollBy = calculateShouldScrollBy(i2, this.scrollOffset, this.minScroll, this.maxScroll);
        this.scrollOffset += iCalculateShouldScrollBy;
        updateCurrentKeylineStateForScrollOffset(this.keylineStateList);
        float itemSize = this.currentKeylineState.getItemSize() / 2.0f;
        float fCalculateChildStartForFill = calculateChildStartForFill(getPosition(getChildAt(0)));
        Rect rect = new Rect();
        float f2 = (isLayoutRtl() ? this.currentKeylineState.getLastFocalKeyline() : this.currentKeylineState.getFirstFocalKeyline()).locOffset;
        float f3 = Float.MAX_VALUE;
        for (int i3 = 0; i3 < getChildCount(); i3++) {
            View childAt = getChildAt(i3);
            float fAbs = Math.abs(f2 - offsetChild(childAt, fCalculateChildStartForFill, itemSize, rect));
            if (childAt != null && fAbs < f3) {
                this.currentEstimatedPosition = getPosition(childAt);
                f3 = fAbs;
            }
            fCalculateChildStartForFill = addEnd(fCalculateChildStartForFill, this.currentKeylineState.getItemSize());
        }
        fill(recycler, state);
        return iCalculateShouldScrollBy;
    }

    private void setCarouselAttributes(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.Carousel);
            setCarouselAlignment(typedArrayObtainStyledAttributes.getInt(R.styleable.Carousel_carousel_alignment, 0));
            setOrientation(typedArrayObtainStyledAttributes.getInt(R.styleable.RecyclerView_android_orientation, 0));
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void updateChildMaskForLocation(View view, float f2, KeylineRange keylineRange) {
        if (view instanceof Maskable) {
            KeylineState.Keyline keyline = keylineRange.leftOrTop;
            float f3 = keyline.mask;
            KeylineState.Keyline keyline2 = keylineRange.rightOrBottom;
            float fLerp = AnimationUtils.lerp(f3, keyline2.mask, keyline.loc, keyline2.loc, f2);
            float height = view.getHeight();
            float width = view.getWidth();
            RectF maskRect = this.orientationHelper.getMaskRect(height, width, AnimationUtils.lerp(0.0f, height / 2.0f, 0.0f, 1.0f, fLerp), AnimationUtils.lerp(0.0f, width / 2.0f, 0.0f, 1.0f, fLerp));
            float fCalculateChildOffsetCenterForLocation = calculateChildOffsetCenterForLocation(view, f2, keylineRange);
            RectF rectF = new RectF(fCalculateChildOffsetCenterForLocation - (maskRect.width() / 2.0f), fCalculateChildOffsetCenterForLocation - (maskRect.height() / 2.0f), (maskRect.width() / 2.0f) + fCalculateChildOffsetCenterForLocation, (maskRect.height() / 2.0f) + fCalculateChildOffsetCenterForLocation);
            RectF rectF2 = new RectF(getParentLeft(), getParentTop(), getParentRight(), getParentBottom());
            if (this.carouselStrategy.isContained()) {
                this.orientationHelper.containMaskWithinBounds(maskRect, rectF, rectF2);
            }
            this.orientationHelper.moveMaskOnEdgeOutsideBounds(maskRect, rectF, rectF2);
            ((Maskable) view).setMaskRectF(maskRect);
        }
    }

    private void updateCurrentKeylineStateForScrollOffset(@NonNull KeylineStateList keylineStateList) {
        KeylineState shiftedState;
        int i2 = this.maxScroll;
        int i3 = this.minScroll;
        if (i2 <= i3) {
            shiftedState = isLayoutRtl() ? keylineStateList.getEndState() : keylineStateList.getStartState();
        } else {
            shiftedState = keylineStateList.getShiftedState(this.scrollOffset, i3, i2);
        }
        this.currentKeylineState = shiftedState;
        this.debugItemDecoration.setKeylines(this.currentKeylineState.getKeylines());
    }

    private void updateItemCount() {
        int itemCount = getItemCount();
        int i2 = this.lastItemCount;
        if (itemCount == i2 || this.keylineStateList == null) {
            return;
        }
        if (this.carouselStrategy.shouldRefreshKeylineState(this, i2)) {
            refreshKeylineState();
        }
        this.lastItemCount = itemCount;
    }

    private void validateChildOrderIfDebugging() {
        if (!this.isDebuggingEnabled || getChildCount() < 1) {
            return;
        }
        int i2 = 0;
        while (i2 < getChildCount() - 1) {
            int position = getPosition(getChildAt(i2));
            int i3 = i2 + 1;
            int position2 = getPosition(getChildAt(i3));
            if (position > position2) {
                logChildrenIfDebugging();
                throw new IllegalStateException("Detected invalid child order. Child at index [" + i2 + "] had adapter position [" + position + "] and child at index [" + i3 + "] had adapter position [" + position2 + "].");
            }
            i2 = i3;
        }
    }

    public int calculateScrollDeltaToMakePositionVisible(int i2) {
        return (int) (this.scrollOffset - getScrollOffsetForPosition(i2, getKeylineStateForPosition(i2)));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean canScrollHorizontally() {
        return isHorizontal();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean canScrollVertically() {
        return !isHorizontal();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeHorizontalScrollExtent(@NonNull RecyclerView.State state) {
        if (getChildCount() == 0 || this.keylineStateList == null || getItemCount() <= 1) {
            return 0;
        }
        return (int) (getWidth() * (this.keylineStateList.getDefaultState().getItemSize() / computeHorizontalScrollRange(state)));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeHorizontalScrollOffset(@NonNull RecyclerView.State state) {
        return this.scrollOffset;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeHorizontalScrollRange(@NonNull RecyclerView.State state) {
        return this.maxScroll - this.minScroll;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.SmoothScroller.ScrollVectorProvider
    @Nullable
    public PointF computeScrollVectorForPosition(int i2) {
        if (this.keylineStateList == null) {
            return null;
        }
        int offsetToScrollToPosition = getOffsetToScrollToPosition(i2, getKeylineStateForPosition(i2));
        return isHorizontal() ? new PointF(offsetToScrollToPosition, 0.0f) : new PointF(0.0f, offsetToScrollToPosition);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeVerticalScrollExtent(@NonNull RecyclerView.State state) {
        if (getChildCount() == 0 || this.keylineStateList == null || getItemCount() <= 1) {
            return 0;
        }
        return (int) (getHeight() * (this.keylineStateList.getDefaultState().getItemSize() / computeVerticalScrollRange(state)));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeVerticalScrollOffset(@NonNull RecyclerView.State state) {
        return this.scrollOffset;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeVerticalScrollRange(@NonNull RecyclerView.State state) {
        return this.maxScroll - this.minScroll;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(-2, -2);
    }

    @Override // com.google.android.material.carousel.Carousel
    public int getCarouselAlignment() {
        return this.carouselAlignment;
    }

    @Override // com.google.android.material.carousel.Carousel
    public int getContainerHeight() {
        return getHeight();
    }

    @Override // com.google.android.material.carousel.Carousel
    public int getContainerWidth() {
        return getWidth();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void getDecoratedBoundsWithMargins(@NonNull View view, @NonNull Rect rect) {
        super.getDecoratedBoundsWithMargins(view, rect);
        float fCenterY = rect.centerY();
        if (isHorizontal()) {
            fCenterY = rect.centerX();
        }
        float maskedItemSizeForLocOffset = getMaskedItemSizeForLocOffset(fCenterY, getSurroundingKeylineRange(this.currentKeylineState.getKeylines(), fCenterY, true));
        float fWidth = isHorizontal() ? (rect.width() - maskedItemSizeForLocOffset) / 2.0f : 0.0f;
        float fHeight = isHorizontal() ? 0.0f : (rect.height() - maskedItemSizeForLocOffset) / 2.0f;
        rect.set((int) (rect.left + fWidth), (int) (rect.top + fHeight), (int) (rect.right - fWidth), (int) (rect.bottom - fHeight));
    }

    public int getOffsetToScrollToPosition(int i2, @NonNull KeylineState keylineState) {
        return getScrollOffsetForPosition(i2, keylineState) - this.scrollOffset;
    }

    public int getOffsetToScrollToPositionForSnap(int i2, boolean z2) {
        int offsetToScrollToPosition = getOffsetToScrollToPosition(i2, this.keylineStateList.getShiftedState(this.scrollOffset, this.minScroll, this.maxScroll, true));
        int offsetToScrollToPosition2 = this.keylineStatePositionMap != null ? getOffsetToScrollToPosition(i2, getKeylineStateForPosition(i2)) : offsetToScrollToPosition;
        return (!z2 || Math.abs(offsetToScrollToPosition2) >= Math.abs(offsetToScrollToPosition)) ? offsetToScrollToPosition : offsetToScrollToPosition2;
    }

    public int getOrientation() {
        return this.orientationHelper.orientation;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean isAutoMeasureEnabled() {
        return true;
    }

    @Override // com.google.android.material.carousel.Carousel
    public boolean isHorizontal() {
        return this.orientationHelper.orientation == 0;
    }

    public boolean isLayoutRtl() {
        return isHorizontal() && getLayoutDirection() == 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void measureChildWithMargins(@NonNull View view, int i2, int i3) {
        if (!(view instanceof Maskable)) {
            throw new IllegalStateException("All children of a RecyclerView using CarouselLayoutManager must use MaskableFrameLayout as their root ViewGroup.");
        }
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        Rect rect = new Rect();
        calculateItemDecorationsForChild(view, rect);
        int i4 = rect.left + rect.right + i2;
        int i5 = rect.top + rect.bottom + i3;
        KeylineStateList keylineStateList = this.keylineStateList;
        float itemSize = (keylineStateList == null || this.orientationHelper.orientation != 0) ? ((ViewGroup.MarginLayoutParams) layoutParams).width : keylineStateList.getDefaultState().getItemSize();
        KeylineStateList keylineStateList2 = this.keylineStateList;
        view.measure(RecyclerView.LayoutManager.getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingRight() + getPaddingLeft() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin + i4, (int) itemSize, canScrollHorizontally()), RecyclerView.LayoutManager.getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingBottom() + getPaddingTop() + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin + i5, (int) ((keylineStateList2 == null || this.orientationHelper.orientation != 1) ? ((ViewGroup.MarginLayoutParams) layoutParams).height : keylineStateList2.getDefaultState().getItemSize()), canScrollVertically()));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onAttachedToWindow(RecyclerView recyclerView) {
        super.onAttachedToWindow(recyclerView);
        this.carouselStrategy.initialize(recyclerView.getContext());
        refreshKeylineState();
        recyclerView.addOnLayoutChangeListener(this.recyclerViewSizeChangeListener);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onDetachedFromWindow(RecyclerView recyclerView, RecyclerView.Recycler recycler) {
        super.onDetachedFromWindow(recyclerView, recycler);
        recyclerView.removeOnLayoutChangeListener(this.recyclerViewSizeChangeListener);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    @Nullable
    public View onFocusSearchFailed(@NonNull View view, int i2, @NonNull RecyclerView.Recycler recycler, @NonNull RecyclerView.State state) {
        int iConvertFocusDirectionToLayoutDirection;
        if (getChildCount() == 0 || (iConvertFocusDirectionToLayoutDirection = convertFocusDirectionToLayoutDirection(i2)) == Integer.MIN_VALUE) {
            return null;
        }
        int position = getPosition(view);
        if (iConvertFocusDirectionToLayoutDirection == -1) {
            if (position == 0) {
                return null;
            }
            addViewAtPosition(recycler, getPosition(getChildAt(0)) - 1, 0);
            return getChildClosestToStart();
        }
        if (position == getItemCount() - 1) {
            return null;
        }
        addViewAtPosition(recycler, getPosition(getChildAt(getChildCount() - 1)) + 1, -1);
        return getChildClosestToEnd();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onInitializeAccessibilityEvent(@NonNull AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (getChildCount() > 0) {
            accessibilityEvent.setFromIndex(getPosition(getChildAt(0)));
            accessibilityEvent.setToIndex(getPosition(getChildAt(getChildCount() - 1)));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsAdded(@NonNull RecyclerView recyclerView, int i2, int i3) {
        super.onItemsAdded(recyclerView, i2, i3);
        updateItemCount();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsRemoved(@NonNull RecyclerView recyclerView, int i2, int i3) {
        super.onItemsRemoved(recyclerView, i2, i3);
        updateItemCount();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.getItemCount() <= 0 || getContainerSize() <= 0.0f) {
            removeAndRecycleAllViews(recycler);
            this.currentFillStartPosition = 0;
            return;
        }
        boolean zIsLayoutRtl = isLayoutRtl();
        boolean z2 = this.keylineStateList == null;
        if (z2) {
            recalculateKeylineStateList(recycler);
        }
        int iCalculateStartScroll = calculateStartScroll(this.keylineStateList);
        int iCalculateEndScroll = calculateEndScroll(state, this.keylineStateList);
        this.minScroll = zIsLayoutRtl ? iCalculateEndScroll : iCalculateStartScroll;
        if (zIsLayoutRtl) {
            iCalculateEndScroll = iCalculateStartScroll;
        }
        this.maxScroll = iCalculateEndScroll;
        if (z2) {
            this.scrollOffset = iCalculateStartScroll;
            this.keylineStatePositionMap = this.keylineStateList.getKeylineStateForPositionMap(getItemCount(), this.minScroll, this.maxScroll, isLayoutRtl());
            int i2 = this.currentEstimatedPosition;
            if (i2 != -1) {
                this.scrollOffset = getScrollOffsetForPosition(i2, getKeylineStateForPosition(i2));
            }
        }
        int i3 = this.scrollOffset;
        this.scrollOffset = i3 + calculateShouldScrollBy(0, i3, this.minScroll, this.maxScroll);
        this.currentFillStartPosition = MathUtils.clamp(this.currentFillStartPosition, 0, state.getItemCount());
        updateCurrentKeylineStateForScrollOffset(this.keylineStateList);
        detachAndScrapAttachedViews(recycler);
        fill(recycler, state);
        this.lastItemCount = getItemCount();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        if (getChildCount() == 0) {
            this.currentFillStartPosition = 0;
        } else {
            this.currentFillStartPosition = getPosition(getChildAt(0));
        }
        validateChildOrderIfDebugging();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean requestChildRectangleOnScreen(@NonNull RecyclerView recyclerView, @NonNull View view, @NonNull Rect rect, boolean z2, boolean z3) {
        int smallestScrollOffsetToFocalKeyline;
        if (this.keylineStateList == null || (smallestScrollOffsetToFocalKeyline = getSmallestScrollOffsetToFocalKeyline(getPosition(view), getKeylineStateForPosition(getPosition(view)))) == 0) {
            return false;
        }
        scrollBy(recyclerView, getSmallestScrollOffsetToFocalKeyline(getPosition(view), this.keylineStateList.getShiftedState(this.scrollOffset + calculateShouldScrollBy(smallestScrollOffsetToFocalKeyline, this.scrollOffset, this.minScroll, this.maxScroll), this.minScroll, this.maxScroll)));
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollHorizontallyBy(int i2, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (canScrollHorizontally()) {
            return scrollBy(i2, recycler, state);
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void scrollToPosition(int i2) {
        this.currentEstimatedPosition = i2;
        if (this.keylineStateList == null) {
            return;
        }
        this.scrollOffset = getScrollOffsetForPosition(i2, getKeylineStateForPosition(i2));
        this.currentFillStartPosition = MathUtils.clamp(i2, 0, Math.max(0, getItemCount() - 1));
        updateCurrentKeylineStateForScrollOffset(this.keylineStateList);
        requestLayout();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollVerticallyBy(int i2, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (canScrollVertically()) {
            return scrollBy(i2, recycler, state);
        }
        return 0;
    }

    public void setCarouselAlignment(int i2) {
        this.carouselAlignment = i2;
        refreshKeylineState();
    }

    public void setCarouselStrategy(@NonNull CarouselStrategy carouselStrategy) {
        this.carouselStrategy = carouselStrategy;
        refreshKeylineState();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setDebuggingEnabled(@NonNull RecyclerView recyclerView, boolean z2) {
        this.isDebuggingEnabled = z2;
        recyclerView.removeItemDecoration(this.debugItemDecoration);
        if (z2) {
            recyclerView.addItemDecoration(this.debugItemDecoration);
        }
        recyclerView.invalidateItemDecorations();
    }

    public void setOrientation(int i2) {
        if (i2 != 0 && i2 != 1) {
            throw new IllegalArgumentException(androidx.appcompat.graphics.drawable.a.c("invalid orientation:", i2));
        }
        assertNotInLayoutOrScroll(null);
        CarouselOrientationHelper carouselOrientationHelper = this.orientationHelper;
        if (carouselOrientationHelper == null || i2 != carouselOrientationHelper.orientation) {
            this.orientationHelper = CarouselOrientationHelper.createOrientationHelper(this, i2);
            refreshKeylineState();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int i2) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) { // from class: com.google.android.material.carousel.CarouselLayoutManager.1
            @Override // androidx.recyclerview.widget.LinearSmoothScroller
            public int calculateDxToMakeVisible(View view, int i3) {
                if (CarouselLayoutManager.this.keylineStateList == null || !CarouselLayoutManager.this.isHorizontal()) {
                    return 0;
                }
                CarouselLayoutManager carouselLayoutManager = CarouselLayoutManager.this;
                return carouselLayoutManager.calculateScrollDeltaToMakePositionVisible(carouselLayoutManager.getPosition(view));
            }

            @Override // androidx.recyclerview.widget.LinearSmoothScroller
            public int calculateDyToMakeVisible(View view, int i3) {
                if (CarouselLayoutManager.this.keylineStateList == null || CarouselLayoutManager.this.isHorizontal()) {
                    return 0;
                }
                CarouselLayoutManager carouselLayoutManager = CarouselLayoutManager.this;
                return carouselLayoutManager.calculateScrollDeltaToMakePositionVisible(carouselLayoutManager.getPosition(view));
            }

            @Override // androidx.recyclerview.widget.RecyclerView.SmoothScroller
            @Nullable
            public PointF computeScrollVectorForPosition(int i3) {
                return CarouselLayoutManager.this.computeScrollVectorForPosition(i3);
            }
        };
        linearSmoothScroller.setTargetPosition(i2);
        startSmoothScroll(linearSmoothScroller);
    }

    @SuppressLint({"UnknownNullness"})
    public CarouselLayoutManager(Context context, AttributeSet attributeSet, int i2, int i3) {
        final int i4 = 0;
        this.isDebuggingEnabled = false;
        this.debugItemDecoration = new DebugItemDecoration();
        this.currentFillStartPosition = 0;
        this.recyclerViewSizeChangeListener = new View.OnLayoutChangeListener(this) { // from class: com.google.android.material.carousel.a
            public final /* synthetic */ CarouselLayoutManager b;

            {
                this.b = this;
            }

            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12) {
                switch (i4) {
                    case 0:
                        this.b.lambda$new$0(view, i5, i6, i7, i8, i9, i10, i11, i12);
                        break;
                    default:
                        this.b.lambda$new$0(view, i5, i6, i7, i8, i9, i10, i11, i12);
                        break;
                }
            }
        };
        this.currentEstimatedPosition = -1;
        this.carouselAlignment = 0;
        setCarouselStrategy(new MultiBrowseCarouselStrategy());
        setCarouselAttributes(context, attributeSet);
    }

    private void scrollBy(RecyclerView recyclerView, int i2) {
        if (isHorizontal()) {
            recyclerView.scrollBy(i2, 0);
        } else {
            recyclerView.scrollBy(0, i2);
        }
    }

    public CarouselLayoutManager(@NonNull CarouselStrategy carouselStrategy) {
        this(carouselStrategy, 0);
    }

    public CarouselLayoutManager(@NonNull CarouselStrategy carouselStrategy, int i2) {
        this.isDebuggingEnabled = false;
        this.debugItemDecoration = new DebugItemDecoration();
        this.currentFillStartPosition = 0;
        final int i3 = 1;
        this.recyclerViewSizeChangeListener = new View.OnLayoutChangeListener(this) { // from class: com.google.android.material.carousel.a
            public final /* synthetic */ CarouselLayoutManager b;

            {
                this.b = this;
            }

            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12) {
                switch (i3) {
                    case 0:
                        this.b.lambda$new$0(view, i5, i6, i7, i8, i9, i10, i11, i12);
                        break;
                    default:
                        this.b.lambda$new$0(view, i5, i6, i7, i8, i9, i10, i11, i12);
                        break;
                }
            }
        };
        this.currentEstimatedPosition = -1;
        this.carouselAlignment = 0;
        setCarouselStrategy(carouselStrategy);
        setOrientation(i2);
    }
}
