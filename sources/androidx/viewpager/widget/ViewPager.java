package androidx.viewpager.widget;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.Scroller;
import androidx.annotation.CallSuper;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import androidx.core.content.ContextCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.view.AbsSavedState;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class ViewPager extends ViewGroup {
    private static final int CLOSE_ENOUGH = 2;
    private static final boolean DEBUG = false;
    private static final int DEFAULT_GUTTER_SIZE = 16;
    private static final int DEFAULT_OFFSCREEN_PAGES = 1;
    private static final int DRAW_ORDER_DEFAULT = 0;
    private static final int DRAW_ORDER_FORWARD = 1;
    private static final int DRAW_ORDER_REVERSE = 2;
    private static final int INVALID_POINTER = -1;
    private static final int MAX_SETTLE_DURATION = 600;
    private static final int MIN_DISTANCE_FOR_FLING = 25;
    private static final int MIN_FLING_VELOCITY = 400;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    private static final String TAG = "ViewPager";
    private static final boolean USE_CACHE = false;
    private int mActivePointerId;
    PagerAdapter mAdapter;
    private List<OnAdapterChangeListener> mAdapterChangeListeners;
    private int mBottomPageBounds;
    private boolean mCalledSuper;
    private int mChildHeightMeasureSpec;
    private int mChildWidthMeasureSpec;
    private int mCloseEnough;
    int mCurItem;
    private int mDecorChildCount;
    private int mDefaultGutterSize;
    private int mDrawingOrder;
    private ArrayList<View> mDrawingOrderedChildren;
    private final Runnable mEndScrollRunnable;
    private int mExpectedAdapterCount;
    private long mFakeDragBeginTime;
    private boolean mFakeDragging;
    private boolean mFirstLayout;
    private float mFirstOffset;
    private int mFlingDistance;
    private int mGutterSize;
    private boolean mInLayout;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private OnPageChangeListener mInternalPageChangeListener;
    private boolean mIsBeingDragged;
    private boolean mIsScrollStarted;
    private boolean mIsUnableToDrag;
    private final ArrayList<ItemInfo> mItems;
    private float mLastMotionX;
    private float mLastMotionY;
    private float mLastOffset;
    private EdgeEffect mLeftEdge;
    private Drawable mMarginDrawable;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private boolean mNeedCalculatePageOffsets;
    private PagerObserver mObserver;
    private int mOffscreenPageLimit;
    private OnPageChangeListener mOnPageChangeListener;
    private List<OnPageChangeListener> mOnPageChangeListeners;
    private int mPageMargin;
    private PageTransformer mPageTransformer;
    private int mPageTransformerLayerType;
    private boolean mPopulatePending;
    private Parcelable mRestoredAdapterState;
    private ClassLoader mRestoredClassLoader;
    private int mRestoredCurItem;
    private EdgeEffect mRightEdge;
    private int mScrollState;
    private Scroller mScroller;
    private boolean mScrollingCacheEnabled;
    private final ItemInfo mTempItem;
    private final Rect mTempRect;
    private int mTopPageBounds;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;
    static final int[] LAYOUT_ATTRS = {R.attr.layout_gravity};
    private static final Comparator<ItemInfo> COMPARATOR = new Comparator<ItemInfo>() { // from class: androidx.viewpager.widget.ViewPager.1
        @Override // java.util.Comparator
        public int compare(ItemInfo itemInfo, ItemInfo itemInfo2) {
            return itemInfo.position - itemInfo2.position;
        }
    };
    private static final Interpolator sInterpolator = new Interpolator() { // from class: androidx.viewpager.widget.ViewPager.2
        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f2) {
            float f3 = f2 - 1.0f;
            return (f3 * f3 * f3 * f3 * f3) + 1.0f;
        }
    };
    private static final ViewPositionComparator sPositionComparator = new ViewPositionComparator();

    @Target({ElementType.TYPE})
    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DecorView {
    }

    public static class ItemInfo {
        Object object;
        float offset;
        int position;
        boolean scrolling;
        float widthFactor;
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {
        int childIndex;
        public int gravity;
        public boolean isDecor;
        boolean needsMeasure;
        int position;
        float widthFactor;

        public LayoutParams() {
            super(-1, -1);
            this.widthFactor = 0.0f;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.widthFactor = 0.0f;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ViewPager.LAYOUT_ATTRS);
            this.gravity = typedArrayObtainStyledAttributes.getInteger(0, 48);
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public class MyAccessibilityDelegate extends AccessibilityDelegateCompat {
        public MyAccessibilityDelegate() {
        }

        private boolean canScroll() {
            PagerAdapter pagerAdapter = ViewPager.this.mAdapter;
            return pagerAdapter != null && pagerAdapter.getCount() > 1;
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            PagerAdapter pagerAdapter;
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName(ViewPager.class.getName());
            accessibilityEvent.setScrollable(canScroll());
            if (accessibilityEvent.getEventType() != 4096 || (pagerAdapter = ViewPager.this.mAdapter) == null) {
                return;
            }
            accessibilityEvent.setItemCount(pagerAdapter.getCount());
            accessibilityEvent.setFromIndex(ViewPager.this.mCurItem);
            accessibilityEvent.setToIndex(ViewPager.this.mCurItem);
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            accessibilityNodeInfoCompat.setClassName(ViewPager.class.getName());
            accessibilityNodeInfoCompat.setScrollable(canScroll());
            if (ViewPager.this.canScrollHorizontally(1)) {
                accessibilityNodeInfoCompat.addAction(4096);
            }
            if (ViewPager.this.canScrollHorizontally(-1)) {
                accessibilityNodeInfoCompat.addAction(8192);
            }
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public boolean performAccessibilityAction(View view, int i2, Bundle bundle) {
            ViewPager viewPager;
            int i3;
            if (super.performAccessibilityAction(view, i2, bundle)) {
                return true;
            }
            if (i2 != 4096) {
                if (i2 != 8192 || !ViewPager.this.canScrollHorizontally(-1)) {
                    return false;
                }
                viewPager = ViewPager.this;
                i3 = viewPager.mCurItem - 1;
            } else {
                if (!ViewPager.this.canScrollHorizontally(1)) {
                    return false;
                }
                viewPager = ViewPager.this;
                i3 = viewPager.mCurItem + 1;
            }
            viewPager.setCurrentItem(i3);
            return true;
        }
    }

    public interface OnAdapterChangeListener {
        void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter pagerAdapter, @Nullable PagerAdapter pagerAdapter2);
    }

    public interface OnPageChangeListener {
        void onPageScrollStateChanged(int i2);

        void onPageScrolled(int i2, float f2, @Px int i3);

        void onPageSelected(int i2);
    }

    public interface PageTransformer {
        void transformPage(@NonNull View view, float f2);
    }

    public class PagerObserver extends DataSetObserver {
        public PagerObserver() {
        }

        @Override // android.database.DataSetObserver
        public void onChanged() {
            ViewPager.this.dataSetChanged();
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() {
            ViewPager.this.dataSetChanged();
        }
    }

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: androidx.viewpager.widget.ViewPager.SavedState.1
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i2) {
                return new SavedState[i2];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.ClassLoaderCreator
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }
        };
        Parcelable adapterState;
        ClassLoader loader;
        int position;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            classLoader = classLoader == null ? getClass().getClassLoader() : classLoader;
            this.position = parcel.readInt();
            this.adapterState = parcel.readParcelable(classLoader);
            this.loader = classLoader;
        }

        public String toString() {
            return "FragmentPager.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " position=" + this.position + "}";
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeInt(this.position);
            parcel.writeParcelable(this.adapterState, i2);
        }

        public SavedState(@NonNull Parcelable parcelable) {
            super(parcelable);
        }
    }

    public static class SimpleOnPageChangeListener implements OnPageChangeListener {
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i2) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int i2, float f2, int i3) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int i2) {
        }
    }

    public static class ViewPositionComparator implements Comparator<View> {
        @Override // java.util.Comparator
        public int compare(View view, View view2) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            LayoutParams layoutParams2 = (LayoutParams) view2.getLayoutParams();
            boolean z2 = layoutParams.isDecor;
            if (z2 != layoutParams2.isDecor) {
                return z2 ? 1 : -1;
            }
            return layoutParams.position - layoutParams2.position;
        }
    }

    public ViewPager(@NonNull Context context) {
        super(context);
        this.mItems = new ArrayList<>();
        this.mTempItem = new ItemInfo();
        this.mTempRect = new Rect();
        this.mRestoredCurItem = -1;
        this.mRestoredAdapterState = null;
        this.mRestoredClassLoader = null;
        this.mFirstOffset = -3.4028235E38f;
        this.mLastOffset = Float.MAX_VALUE;
        this.mOffscreenPageLimit = 1;
        this.mActivePointerId = -1;
        this.mFirstLayout = true;
        this.mNeedCalculatePageOffsets = false;
        this.mEndScrollRunnable = new Runnable() { // from class: androidx.viewpager.widget.ViewPager.3
            @Override // java.lang.Runnable
            public void run() {
                ViewPager.this.setScrollState(0);
                ViewPager.this.populate();
            }
        };
        this.mScrollState = 0;
        initViewPager();
    }

    private void calculatePageOffsets(ItemInfo itemInfo, int i2, ItemInfo itemInfo2) {
        int i3;
        int i4;
        ItemInfo itemInfo3;
        ItemInfo itemInfo4;
        int count = this.mAdapter.getCount();
        int clientWidth = getClientWidth();
        float f2 = clientWidth > 0 ? this.mPageMargin / clientWidth : 0.0f;
        if (itemInfo2 != null) {
            int i5 = itemInfo2.position;
            int i6 = itemInfo.position;
            if (i5 < i6) {
                float pageWidth = itemInfo2.offset + itemInfo2.widthFactor + f2;
                int i7 = i5 + 1;
                int i8 = 0;
                while (i7 <= itemInfo.position && i8 < this.mItems.size()) {
                    while (true) {
                        itemInfo4 = this.mItems.get(i8);
                        if (i7 <= itemInfo4.position || i8 >= this.mItems.size() - 1) {
                            break;
                        } else {
                            i8++;
                        }
                    }
                    while (i7 < itemInfo4.position) {
                        pageWidth += this.mAdapter.getPageWidth(i7) + f2;
                        i7++;
                    }
                    itemInfo4.offset = pageWidth;
                    pageWidth += itemInfo4.widthFactor + f2;
                    i7++;
                }
            } else if (i5 > i6) {
                int size = this.mItems.size() - 1;
                float pageWidth2 = itemInfo2.offset;
                while (true) {
                    i5--;
                    if (i5 < itemInfo.position || size < 0) {
                        break;
                    }
                    while (true) {
                        itemInfo3 = this.mItems.get(size);
                        if (i5 >= itemInfo3.position || size <= 0) {
                            break;
                        } else {
                            size--;
                        }
                    }
                    while (i5 > itemInfo3.position) {
                        pageWidth2 -= this.mAdapter.getPageWidth(i5) + f2;
                        i5--;
                    }
                    pageWidth2 -= itemInfo3.widthFactor + f2;
                    itemInfo3.offset = pageWidth2;
                }
            }
        }
        int size2 = this.mItems.size();
        float pageWidth3 = itemInfo.offset;
        int i9 = itemInfo.position;
        int i10 = i9 - 1;
        this.mFirstOffset = i9 == 0 ? pageWidth3 : -3.4028235E38f;
        int i11 = count - 1;
        this.mLastOffset = i9 == i11 ? (itemInfo.widthFactor + pageWidth3) - 1.0f : Float.MAX_VALUE;
        int i12 = i2 - 1;
        while (i12 >= 0) {
            ItemInfo itemInfo5 = this.mItems.get(i12);
            while (true) {
                i4 = itemInfo5.position;
                if (i10 <= i4) {
                    break;
                }
                pageWidth3 -= this.mAdapter.getPageWidth(i10) + f2;
                i10--;
            }
            pageWidth3 -= itemInfo5.widthFactor + f2;
            itemInfo5.offset = pageWidth3;
            if (i4 == 0) {
                this.mFirstOffset = pageWidth3;
            }
            i12--;
            i10--;
        }
        float pageWidth4 = itemInfo.offset + itemInfo.widthFactor + f2;
        int i13 = itemInfo.position + 1;
        int i14 = i2 + 1;
        while (i14 < size2) {
            ItemInfo itemInfo6 = this.mItems.get(i14);
            while (true) {
                i3 = itemInfo6.position;
                if (i13 >= i3) {
                    break;
                }
                pageWidth4 += this.mAdapter.getPageWidth(i13) + f2;
                i13++;
            }
            if (i3 == i11) {
                this.mLastOffset = (itemInfo6.widthFactor + pageWidth4) - 1.0f;
            }
            itemInfo6.offset = pageWidth4;
            pageWidth4 += itemInfo6.widthFactor + f2;
            i14++;
            i13++;
        }
        this.mNeedCalculatePageOffsets = false;
    }

    private void completeScroll(boolean z2) {
        boolean z3 = this.mScrollState == 2;
        if (z3) {
            setScrollingCacheEnabled(false);
            if (!this.mScroller.isFinished()) {
                this.mScroller.abortAnimation();
                int scrollX = getScrollX();
                int scrollY = getScrollY();
                int currX = this.mScroller.getCurrX();
                int currY = this.mScroller.getCurrY();
                if (scrollX != currX || scrollY != currY) {
                    scrollTo(currX, currY);
                    if (currX != scrollX) {
                        pageScrolled(currX);
                    }
                }
            }
        }
        this.mPopulatePending = false;
        for (int i2 = 0; i2 < this.mItems.size(); i2++) {
            ItemInfo itemInfo = this.mItems.get(i2);
            if (itemInfo.scrolling) {
                itemInfo.scrolling = false;
                z3 = true;
            }
        }
        if (z3) {
            if (z2) {
                ViewCompat.postOnAnimation(this, this.mEndScrollRunnable);
            } else {
                this.mEndScrollRunnable.run();
            }
        }
    }

    private int determineTargetPage(int i2, float f2, int i3, int i4) {
        if (Math.abs(i4) <= this.mFlingDistance || Math.abs(i3) <= this.mMinimumVelocity) {
            i2 += (int) (f2 + (i2 >= this.mCurItem ? 0.4f : 0.6f));
        } else if (i3 <= 0) {
            i2++;
        }
        if (this.mItems.size() <= 0) {
            return i2;
        }
        ItemInfo itemInfo = this.mItems.get(0);
        ArrayList<ItemInfo> arrayList = this.mItems;
        return Math.max(itemInfo.position, Math.min(i2, arrayList.get(arrayList.size() - 1).position));
    }

    private void dispatchOnPageScrolled(int i2, float f2, int i3) {
        OnPageChangeListener onPageChangeListener = this.mOnPageChangeListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrolled(i2, f2, i3);
        }
        List<OnPageChangeListener> list = this.mOnPageChangeListeners;
        if (list != null) {
            int size = list.size();
            for (int i4 = 0; i4 < size; i4++) {
                OnPageChangeListener onPageChangeListener2 = this.mOnPageChangeListeners.get(i4);
                if (onPageChangeListener2 != null) {
                    onPageChangeListener2.onPageScrolled(i2, f2, i3);
                }
            }
        }
        OnPageChangeListener onPageChangeListener3 = this.mInternalPageChangeListener;
        if (onPageChangeListener3 != null) {
            onPageChangeListener3.onPageScrolled(i2, f2, i3);
        }
    }

    private void dispatchOnPageSelected(int i2) {
        OnPageChangeListener onPageChangeListener = this.mOnPageChangeListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageSelected(i2);
        }
        List<OnPageChangeListener> list = this.mOnPageChangeListeners;
        if (list != null) {
            int size = list.size();
            for (int i3 = 0; i3 < size; i3++) {
                OnPageChangeListener onPageChangeListener2 = this.mOnPageChangeListeners.get(i3);
                if (onPageChangeListener2 != null) {
                    onPageChangeListener2.onPageSelected(i2);
                }
            }
        }
        OnPageChangeListener onPageChangeListener3 = this.mInternalPageChangeListener;
        if (onPageChangeListener3 != null) {
            onPageChangeListener3.onPageSelected(i2);
        }
    }

    private void dispatchOnScrollStateChanged(int i2) {
        OnPageChangeListener onPageChangeListener = this.mOnPageChangeListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrollStateChanged(i2);
        }
        List<OnPageChangeListener> list = this.mOnPageChangeListeners;
        if (list != null) {
            int size = list.size();
            for (int i3 = 0; i3 < size; i3++) {
                OnPageChangeListener onPageChangeListener2 = this.mOnPageChangeListeners.get(i3);
                if (onPageChangeListener2 != null) {
                    onPageChangeListener2.onPageScrollStateChanged(i2);
                }
            }
        }
        OnPageChangeListener onPageChangeListener3 = this.mInternalPageChangeListener;
        if (onPageChangeListener3 != null) {
            onPageChangeListener3.onPageScrollStateChanged(i2);
        }
    }

    private void enableLayers(boolean z2) {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            getChildAt(i2).setLayerType(z2 ? this.mPageTransformerLayerType : 0, null);
        }
    }

    private void endDrag() {
        this.mIsBeingDragged = false;
        this.mIsUnableToDrag = false;
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private Rect getChildRectInPagerCoordinates(Rect rect, View view) {
        if (rect == null) {
            rect = new Rect();
        }
        if (view == null) {
            rect.set(0, 0, 0, 0);
            return rect;
        }
        rect.left = view.getLeft();
        rect.right = view.getRight();
        rect.top = view.getTop();
        rect.bottom = view.getBottom();
        while (true) {
            ViewParent parent = view.getParent();
            if (!(parent instanceof ViewGroup) || parent == this) {
                break;
            }
            view = (ViewGroup) parent;
            rect.left = view.getLeft() + rect.left;
            rect.right = view.getRight() + rect.right;
            rect.top = view.getTop() + rect.top;
            rect.bottom = view.getBottom() + rect.bottom;
        }
        return rect;
    }

    private int getClientWidth() {
        return (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
    }

    private ItemInfo infoForCurrentScrollPosition() {
        int i2;
        int clientWidth = getClientWidth();
        float f2 = 0.0f;
        float scrollX = clientWidth > 0 ? getScrollX() / clientWidth : 0.0f;
        float f3 = clientWidth > 0 ? this.mPageMargin / clientWidth : 0.0f;
        ItemInfo itemInfo = null;
        float f4 = 0.0f;
        int i3 = -1;
        int i4 = 0;
        boolean z2 = true;
        while (i4 < this.mItems.size()) {
            ItemInfo itemInfo2 = this.mItems.get(i4);
            if (!z2 && itemInfo2.position != (i2 = i3 + 1)) {
                itemInfo2 = this.mTempItem;
                itemInfo2.offset = f2 + f4 + f3;
                itemInfo2.position = i2;
                itemInfo2.widthFactor = this.mAdapter.getPageWidth(i2);
                i4--;
            }
            ItemInfo itemInfo3 = itemInfo2;
            f2 = itemInfo3.offset;
            float f5 = itemInfo3.widthFactor + f2 + f3;
            if (!z2 && scrollX < f2) {
                return itemInfo;
            }
            if (scrollX < f5 || i4 == this.mItems.size() - 1) {
                return itemInfo3;
            }
            int i5 = itemInfo3.position;
            float f6 = itemInfo3.widthFactor;
            i4++;
            z2 = false;
            i3 = i5;
            f4 = f6;
            itemInfo = itemInfo3;
        }
        return itemInfo;
    }

    private static boolean isDecorView(@NonNull View view) {
        return view.getClass().getAnnotation(DecorView.class) != null;
    }

    private boolean isGutterDrag(float f2, float f3) {
        return (f2 < ((float) this.mGutterSize) && f3 > 0.0f) || (f2 > ((float) (getWidth() - this.mGutterSize)) && f3 < 0.0f);
    }

    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.mActivePointerId) {
            int i2 = actionIndex == 0 ? 1 : 0;
            this.mLastMotionX = motionEvent.getX(i2);
            this.mActivePointerId = motionEvent.getPointerId(i2);
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.clear();
            }
        }
    }

    private boolean pageScrolled(int i2) {
        if (this.mItems.size() == 0) {
            if (this.mFirstLayout) {
                return false;
            }
            this.mCalledSuper = false;
            onPageScrolled(0, 0.0f, 0);
            if (this.mCalledSuper) {
                return false;
            }
            throw new IllegalStateException("onPageScrolled did not call superclass implementation");
        }
        ItemInfo itemInfoInfoForCurrentScrollPosition = infoForCurrentScrollPosition();
        int clientWidth = getClientWidth();
        int i3 = this.mPageMargin;
        int i4 = clientWidth + i3;
        float f2 = clientWidth;
        int i5 = itemInfoInfoForCurrentScrollPosition.position;
        float f3 = ((i2 / f2) - itemInfoInfoForCurrentScrollPosition.offset) / (itemInfoInfoForCurrentScrollPosition.widthFactor + (i3 / f2));
        this.mCalledSuper = false;
        onPageScrolled(i5, f3, (int) (i4 * f3));
        if (this.mCalledSuper) {
            return true;
        }
        throw new IllegalStateException("onPageScrolled did not call superclass implementation");
    }

    private boolean performDrag(float f2) {
        boolean z2;
        boolean z3;
        float f3 = this.mLastMotionX - f2;
        this.mLastMotionX = f2;
        float scrollX = getScrollX() + f3;
        float clientWidth = getClientWidth();
        float f4 = this.mFirstOffset * clientWidth;
        float f5 = this.mLastOffset * clientWidth;
        boolean z4 = false;
        ItemInfo itemInfo = this.mItems.get(0);
        ArrayList<ItemInfo> arrayList = this.mItems;
        ItemInfo itemInfo2 = arrayList.get(arrayList.size() - 1);
        if (itemInfo.position != 0) {
            f4 = itemInfo.offset * clientWidth;
            z2 = false;
        } else {
            z2 = true;
        }
        if (itemInfo2.position != this.mAdapter.getCount() - 1) {
            f5 = itemInfo2.offset * clientWidth;
            z3 = false;
        } else {
            z3 = true;
        }
        if (scrollX < f4) {
            if (z2) {
                this.mLeftEdge.onPull(Math.abs(f4 - scrollX) / clientWidth);
                z4 = true;
            }
            scrollX = f4;
        } else if (scrollX > f5) {
            if (z3) {
                this.mRightEdge.onPull(Math.abs(scrollX - f5) / clientWidth);
                z4 = true;
            }
            scrollX = f5;
        }
        int i2 = (int) scrollX;
        this.mLastMotionX = (scrollX - i2) + this.mLastMotionX;
        scrollTo(i2, getScrollY());
        pageScrolled(i2);
        return z4;
    }

    private void recomputeScrollPosition(int i2, int i3, int i4, int i5) {
        int iMin;
        if (i3 <= 0 || this.mItems.isEmpty()) {
            ItemInfo itemInfoInfoForPosition = infoForPosition(this.mCurItem);
            iMin = (int) ((itemInfoInfoForPosition != null ? Math.min(itemInfoInfoForPosition.offset, this.mLastOffset) : 0.0f) * ((i2 - getPaddingLeft()) - getPaddingRight()));
            if (iMin == getScrollX()) {
                return;
            } else {
                completeScroll(false);
            }
        } else if (!this.mScroller.isFinished()) {
            this.mScroller.setFinalX(getCurrentItem() * getClientWidth());
            return;
        } else {
            iMin = (int) ((getScrollX() / (((i3 - getPaddingLeft()) - getPaddingRight()) + i5)) * (((i2 - getPaddingLeft()) - getPaddingRight()) + i4));
        }
        scrollTo(iMin, getScrollY());
    }

    private void removeNonDecorViews() {
        int i2 = 0;
        while (i2 < getChildCount()) {
            if (!((LayoutParams) getChildAt(i2).getLayoutParams()).isDecor) {
                removeViewAt(i2);
                i2--;
            }
            i2++;
        }
    }

    private void requestParentDisallowInterceptTouchEvent(boolean z2) {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(z2);
        }
    }

    private boolean resetTouch() {
        this.mActivePointerId = -1;
        endDrag();
        this.mLeftEdge.onRelease();
        this.mRightEdge.onRelease();
        return this.mLeftEdge.isFinished() || this.mRightEdge.isFinished();
    }

    private void scrollToItem(int i2, boolean z2, int i3, boolean z3) {
        int iMax;
        ItemInfo itemInfoInfoForPosition = infoForPosition(i2);
        if (itemInfoInfoForPosition != null) {
            iMax = (int) (Math.max(this.mFirstOffset, Math.min(itemInfoInfoForPosition.offset, this.mLastOffset)) * getClientWidth());
        } else {
            iMax = 0;
        }
        if (z2) {
            smoothScrollTo(iMax, 0, i3);
            if (z3) {
                dispatchOnPageSelected(i2);
                return;
            }
            return;
        }
        if (z3) {
            dispatchOnPageSelected(i2);
        }
        completeScroll(false);
        scrollTo(iMax, 0);
        pageScrolled(iMax);
    }

    private void setScrollingCacheEnabled(boolean z2) {
        if (this.mScrollingCacheEnabled != z2) {
            this.mScrollingCacheEnabled = z2;
        }
    }

    private void sortChildDrawingOrder() {
        if (this.mDrawingOrder != 0) {
            ArrayList<View> arrayList = this.mDrawingOrderedChildren;
            if (arrayList == null) {
                this.mDrawingOrderedChildren = new ArrayList<>();
            } else {
                arrayList.clear();
            }
            int childCount = getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                this.mDrawingOrderedChildren.add(getChildAt(i2));
            }
            Collections.sort(this.mDrawingOrderedChildren, sPositionComparator);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void addFocusables(ArrayList<View> arrayList, int i2, int i3) {
        ItemInfo itemInfoInfoForChild;
        int size = arrayList.size();
        int descendantFocusability = getDescendantFocusability();
        if (descendantFocusability != 393216) {
            for (int i4 = 0; i4 < getChildCount(); i4++) {
                View childAt = getChildAt(i4);
                if (childAt.getVisibility() == 0 && (itemInfoInfoForChild = infoForChild(childAt)) != null && itemInfoInfoForChild.position == this.mCurItem) {
                    childAt.addFocusables(arrayList, i2, i3);
                }
            }
        }
        if ((descendantFocusability != 262144 || size == arrayList.size()) && isFocusable()) {
            if ((i3 & 1) == 1 && isInTouchMode() && !isFocusableInTouchMode()) {
                return;
            }
            arrayList.add(this);
        }
    }

    public ItemInfo addNewItem(int i2, int i3) {
        ItemInfo itemInfo = new ItemInfo();
        itemInfo.position = i2;
        itemInfo.object = this.mAdapter.instantiateItem((ViewGroup) this, i2);
        itemInfo.widthFactor = this.mAdapter.getPageWidth(i2);
        if (i3 < 0 || i3 >= this.mItems.size()) {
            this.mItems.add(itemInfo);
        } else {
            this.mItems.add(i3, itemInfo);
        }
        return itemInfo;
    }

    public void addOnAdapterChangeListener(@NonNull OnAdapterChangeListener onAdapterChangeListener) {
        if (this.mAdapterChangeListeners == null) {
            this.mAdapterChangeListeners = new ArrayList();
        }
        this.mAdapterChangeListeners.add(onAdapterChangeListener);
    }

    public void addOnPageChangeListener(@NonNull OnPageChangeListener onPageChangeListener) {
        if (this.mOnPageChangeListeners == null) {
            this.mOnPageChangeListeners = new ArrayList();
        }
        this.mOnPageChangeListeners.add(onPageChangeListener);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void addTouchables(ArrayList<View> arrayList) {
        ItemInfo itemInfoInfoForChild;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() == 0 && (itemInfoInfoForChild = infoForChild(childAt)) != null && itemInfoInfoForChild.position == this.mCurItem) {
                childAt.addTouchables(arrayList);
            }
        }
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i2, ViewGroup.LayoutParams layoutParams) {
        if (!checkLayoutParams(layoutParams)) {
            layoutParams = generateLayoutParams(layoutParams);
        }
        LayoutParams layoutParams2 = (LayoutParams) layoutParams;
        boolean zIsDecorView = layoutParams2.isDecor | isDecorView(view);
        layoutParams2.isDecor = zIsDecorView;
        if (!this.mInLayout) {
            super.addView(view, i2, layoutParams);
        } else {
            if (zIsDecorView) {
                throw new IllegalStateException("Cannot add pager decor view during layout");
            }
            layoutParams2.needsMeasure = true;
            addViewInLayout(view, i2, layoutParams);
        }
    }

    /* JADX WARN: Code duplicated, block: B:38:0x0097  */
    /* JADX WARN: Code duplicated, block: B:39:0x0099  */
    /* JADX WARN: Code duplicated, block: B:40:0x009e  */
    public boolean arrowScroll(int i2) {
        boolean zPageRight;
        View viewFindFocus = findFocus();
        if (viewFindFocus == this) {
            viewFindFocus = null;
            break;
        }
        if (viewFindFocus != null) {
            ViewParent parent = viewFindFocus.getParent();
            while (true) {
                if (!(parent instanceof ViewGroup)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(viewFindFocus.getClass().getSimpleName());
                    for (ViewParent parent2 = viewFindFocus.getParent(); parent2 instanceof ViewGroup; parent2 = parent2.getParent()) {
                        sb.append(" => ");
                        sb.append(parent2.getClass().getSimpleName());
                    }
                    viewFindFocus = null;
                    break;
                }
                if (parent == this) {
                    break;
                }
                parent = parent.getParent();
            }
        }
        View viewFindNextFocus = FocusFinder.getInstance().findNextFocus(this, viewFindFocus, i2);
        if (viewFindNextFocus != null && viewFindNextFocus != viewFindFocus) {
            if (i2 == 17) {
                int i3 = getChildRectInPagerCoordinates(this.mTempRect, viewFindNextFocus).left;
                int i4 = getChildRectInPagerCoordinates(this.mTempRect, viewFindFocus).left;
                if (viewFindFocus != null && i3 >= i4) {
                    zPageRight = pageLeft();
                }
            } else if (i2 == 66) {
                int i5 = getChildRectInPagerCoordinates(this.mTempRect, viewFindNextFocus).left;
                int i6 = getChildRectInPagerCoordinates(this.mTempRect, viewFindFocus).left;
                if (viewFindFocus != null && i5 <= i6) {
                    zPageRight = pageRight();
                }
            } else {
                zPageRight = false;
            }
            zPageRight = viewFindNextFocus.requestFocus();
        } else if (i2 == 17 || i2 == 1) {
            zPageRight = pageLeft();
        } else if (i2 == 66 || i2 == 2) {
            zPageRight = pageRight();
        } else {
            zPageRight = false;
        }
        if (zPageRight) {
            playSoundEffect(SoundEffectConstants.getContantForFocusDirection(i2));
        }
        return zPageRight;
    }

    public boolean beginFakeDrag() {
        if (this.mIsBeingDragged) {
            return false;
        }
        this.mFakeDragging = true;
        setScrollState(1);
        this.mLastMotionX = 0.0f;
        this.mInitialMotionX = 0.0f;
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        } else {
            velocityTracker.clear();
        }
        long jUptimeMillis = SystemClock.uptimeMillis();
        MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 0, 0.0f, 0.0f, 0);
        this.mVelocityTracker.addMovement(motionEventObtain);
        motionEventObtain.recycle();
        this.mFakeDragBeginTime = jUptimeMillis;
        return true;
    }

    public boolean canScroll(View view, boolean z2, int i2, int i3, int i4) {
        int i5;
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int scrollX = view.getScrollX();
            int scrollY = view.getScrollY();
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                int i6 = i3 + scrollX;
                if (i6 >= childAt.getLeft() && i6 < childAt.getRight() && (i5 = i4 + scrollY) >= childAt.getTop() && i5 < childAt.getBottom() && canScroll(childAt, true, i2, i6 - childAt.getLeft(), i5 - childAt.getTop())) {
                    return true;
                }
            }
        }
        return z2 && view.canScrollHorizontally(-i2);
    }

    @Override // android.view.View
    public boolean canScrollHorizontally(int i2) {
        if (this.mAdapter == null) {
            return false;
        }
        int clientWidth = getClientWidth();
        int scrollX = getScrollX();
        if (i2 < 0) {
            return scrollX > ((int) (((float) clientWidth) * this.mFirstOffset));
        }
        return i2 > 0 && scrollX < ((int) (((float) clientWidth) * this.mLastOffset));
    }

    @Override // android.view.ViewGroup
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && super.checkLayoutParams(layoutParams);
    }

    public void clearOnPageChangeListeners() {
        List<OnPageChangeListener> list = this.mOnPageChangeListeners;
        if (list != null) {
            list.clear();
        }
    }

    @Override // android.view.View
    public void computeScroll() {
        this.mIsScrollStarted = true;
        if (this.mScroller.isFinished() || !this.mScroller.computeScrollOffset()) {
            completeScroll(true);
            return;
        }
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int currX = this.mScroller.getCurrX();
        int currY = this.mScroller.getCurrY();
        if (scrollX != currX || scrollY != currY) {
            scrollTo(currX, currY);
            if (!pageScrolled(currX)) {
                this.mScroller.abortAnimation();
                scrollTo(0, currY);
            }
        }
        ViewCompat.postInvalidateOnAnimation(this);
    }

    public void dataSetChanged() {
        int count = this.mAdapter.getCount();
        this.mExpectedAdapterCount = count;
        boolean z2 = this.mItems.size() < (this.mOffscreenPageLimit * 2) + 1 && this.mItems.size() < count;
        int iMax = this.mCurItem;
        int i2 = 0;
        boolean z3 = false;
        while (i2 < this.mItems.size()) {
            ItemInfo itemInfo = this.mItems.get(i2);
            int itemPosition = this.mAdapter.getItemPosition(itemInfo.object);
            if (itemPosition != -1) {
                if (itemPosition == -2) {
                    this.mItems.remove(i2);
                    i2--;
                    if (!z3) {
                        this.mAdapter.startUpdate((ViewGroup) this);
                        z3 = true;
                    }
                    this.mAdapter.destroyItem((ViewGroup) this, itemInfo.position, itemInfo.object);
                    int i3 = this.mCurItem;
                    if (i3 == itemInfo.position) {
                        iMax = Math.max(0, Math.min(i3, count - 1));
                    }
                } else {
                    int i4 = itemInfo.position;
                    if (i4 != itemPosition) {
                        if (i4 == this.mCurItem) {
                            iMax = itemPosition;
                        }
                        itemInfo.position = itemPosition;
                    }
                }
                z2 = true;
            }
            i2++;
        }
        if (z3) {
            this.mAdapter.finishUpdate((ViewGroup) this);
        }
        Collections.sort(this.mItems, COMPARATOR);
        if (z2) {
            int childCount = getChildCount();
            for (int i5 = 0; i5 < childCount; i5++) {
                LayoutParams layoutParams = (LayoutParams) getChildAt(i5).getLayoutParams();
                if (!layoutParams.isDecor) {
                    layoutParams.widthFactor = 0.0f;
                }
            }
            setCurrentItemInternal(iMax, false, true);
            requestLayout();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent) || executeKeyEvent(keyEvent);
    }

    @Override // android.view.View
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        ItemInfo itemInfoInfoForChild;
        if (accessibilityEvent.getEventType() == 4096) {
            return super.dispatchPopulateAccessibilityEvent(accessibilityEvent);
        }
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() == 0 && (itemInfoInfoForChild = infoForChild(childAt)) != null && itemInfoInfoForChild.position == this.mCurItem && childAt.dispatchPopulateAccessibilityEvent(accessibilityEvent)) {
                return true;
            }
        }
        return false;
    }

    public float distanceInfluenceForSnapDuration(float f2) {
        return (float) Math.sin((f2 - 0.5f) * 0.47123894f);
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        boolean zDraw;
        PagerAdapter pagerAdapter;
        super.draw(canvas);
        int overScrollMode = getOverScrollMode();
        if (overScrollMode != 0 && (overScrollMode != 1 || (pagerAdapter = this.mAdapter) == null || pagerAdapter.getCount() <= 1)) {
            this.mLeftEdge.finish();
            this.mRightEdge.finish();
            return;
        }
        if (this.mLeftEdge.isFinished()) {
            zDraw = false;
        } else {
            int iSave = canvas.save();
            int height = (getHeight() - getPaddingTop()) - getPaddingBottom();
            int width = getWidth();
            canvas.rotate(270.0f);
            canvas.translate(getPaddingTop() + (-height), this.mFirstOffset * width);
            this.mLeftEdge.setSize(height, width);
            zDraw = this.mLeftEdge.draw(canvas);
            canvas.restoreToCount(iSave);
        }
        if (!this.mRightEdge.isFinished()) {
            int iSave2 = canvas.save();
            int width2 = getWidth();
            int height2 = (getHeight() - getPaddingTop()) - getPaddingBottom();
            canvas.rotate(90.0f);
            canvas.translate(-getPaddingTop(), (-(this.mLastOffset + 1.0f)) * width2);
            this.mRightEdge.setSize(height2, width2);
            zDraw |= this.mRightEdge.draw(canvas);
            canvas.restoreToCount(iSave2);
        }
        if (zDraw) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.mMarginDrawable;
        if (drawable == null || !drawable.isStateful()) {
            return;
        }
        drawable.setState(getDrawableState());
    }

    public void endFakeDrag() {
        if (!this.mFakeDragging) {
            throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
        }
        if (this.mAdapter != null) {
            VelocityTracker velocityTracker = this.mVelocityTracker;
            velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
            int xVelocity = (int) velocityTracker.getXVelocity(this.mActivePointerId);
            this.mPopulatePending = true;
            int clientWidth = getClientWidth();
            int scrollX = getScrollX();
            ItemInfo itemInfoInfoForCurrentScrollPosition = infoForCurrentScrollPosition();
            setCurrentItemInternal(determineTargetPage(itemInfoInfoForCurrentScrollPosition.position, ((scrollX / clientWidth) - itemInfoInfoForCurrentScrollPosition.offset) / itemInfoInfoForCurrentScrollPosition.widthFactor, xVelocity, (int) (this.mLastMotionX - this.mInitialMotionX)), true, true, xVelocity);
        }
        endDrag();
        this.mFakeDragging = false;
    }

    public boolean executeKeyEvent(@NonNull KeyEvent keyEvent) {
        int i2;
        if (keyEvent.getAction() == 0) {
            int keyCode = keyEvent.getKeyCode();
            if (keyCode != 21) {
                if (keyCode != 22) {
                    if (keyCode == 61) {
                        if (keyEvent.hasNoModifiers()) {
                            return arrowScroll(2);
                        }
                        if (keyEvent.hasModifiers(1)) {
                            return arrowScroll(1);
                        }
                    }
                } else {
                    if (keyEvent.hasModifiers(2)) {
                        return pageRight();
                    }
                    i2 = 66;
                }
            } else {
                if (keyEvent.hasModifiers(2)) {
                    return pageLeft();
                }
                i2 = 17;
            }
            return arrowScroll(i2);
        }
        return false;
    }

    public void fakeDragBy(float f2) {
        if (!this.mFakeDragging) {
            throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
        }
        if (this.mAdapter == null) {
            return;
        }
        this.mLastMotionX += f2;
        float scrollX = getScrollX() - f2;
        float clientWidth = getClientWidth();
        float f3 = this.mFirstOffset * clientWidth;
        float f4 = this.mLastOffset * clientWidth;
        ItemInfo itemInfo = this.mItems.get(0);
        ArrayList<ItemInfo> arrayList = this.mItems;
        ItemInfo itemInfo2 = arrayList.get(arrayList.size() - 1);
        if (itemInfo.position != 0) {
            f3 = itemInfo.offset * clientWidth;
        }
        if (itemInfo2.position != this.mAdapter.getCount() - 1) {
            f4 = itemInfo2.offset * clientWidth;
        }
        if (scrollX < f3) {
            scrollX = f3;
        } else if (scrollX > f4) {
            scrollX = f4;
        }
        int i2 = (int) scrollX;
        this.mLastMotionX = (scrollX - i2) + this.mLastMotionX;
        scrollTo(i2, getScrollY());
        pageScrolled(i2);
        MotionEvent motionEventObtain = MotionEvent.obtain(this.mFakeDragBeginTime, SystemClock.uptimeMillis(), 2, this.mLastMotionX, 0.0f, 0);
        this.mVelocityTracker.addMovement(motionEventObtain);
        motionEventObtain.recycle();
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Nullable
    public PagerAdapter getAdapter() {
        return this.mAdapter;
    }

    @Override // android.view.ViewGroup
    public int getChildDrawingOrder(int i2, int i3) {
        if (this.mDrawingOrder == 2) {
            i3 = (i2 - 1) - i3;
        }
        return ((LayoutParams) this.mDrawingOrderedChildren.get(i3).getLayoutParams()).childIndex;
    }

    public int getCurrentItem() {
        return this.mCurItem;
    }

    public int getOffscreenPageLimit() {
        return this.mOffscreenPageLimit;
    }

    public int getPageMargin() {
        return this.mPageMargin;
    }

    public ItemInfo infoForAnyChild(View view) {
        while (true) {
            Object parent = view.getParent();
            if (parent == this) {
                return infoForChild(view);
            }
            if (parent == null || !(parent instanceof View)) {
                return null;
            }
            view = (View) parent;
        }
    }

    public ItemInfo infoForChild(View view) {
        for (int i2 = 0; i2 < this.mItems.size(); i2++) {
            ItemInfo itemInfo = this.mItems.get(i2);
            if (this.mAdapter.isViewFromObject(view, itemInfo.object)) {
                return itemInfo;
            }
        }
        return null;
    }

    public ItemInfo infoForPosition(int i2) {
        for (int i3 = 0; i3 < this.mItems.size(); i3++) {
            ItemInfo itemInfo = this.mItems.get(i3);
            if (itemInfo.position == i2) {
                return itemInfo;
            }
        }
        return null;
    }

    public void initViewPager() {
        setWillNotDraw(false);
        setDescendantFocusability(262144);
        setFocusable(true);
        Context context = getContext();
        this.mScroller = new Scroller(context, sInterpolator);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        float f2 = context.getResources().getDisplayMetrics().density;
        this.mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
        this.mMinimumVelocity = (int) (400.0f * f2);
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mLeftEdge = new EdgeEffect(context);
        this.mRightEdge = new EdgeEffect(context);
        this.mFlingDistance = (int) (25.0f * f2);
        this.mCloseEnough = (int) (2.0f * f2);
        this.mDefaultGutterSize = (int) (f2 * 16.0f);
        ViewCompat.setAccessibilityDelegate(this, new MyAccessibilityDelegate());
        if (ViewCompat.getImportantForAccessibility(this) == 0) {
            ViewCompat.setImportantForAccessibility(this, 1);
        }
        ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener() { // from class: androidx.viewpager.widget.ViewPager.4
            private final Rect mTempRect = new Rect();

            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                WindowInsetsCompat windowInsetsCompatOnApplyWindowInsets = ViewCompat.onApplyWindowInsets(view, windowInsetsCompat);
                if (windowInsetsCompatOnApplyWindowInsets.isConsumed()) {
                    return windowInsetsCompatOnApplyWindowInsets;
                }
                Rect rect = this.mTempRect;
                rect.left = windowInsetsCompatOnApplyWindowInsets.getSystemWindowInsetLeft();
                rect.top = windowInsetsCompatOnApplyWindowInsets.getSystemWindowInsetTop();
                rect.right = windowInsetsCompatOnApplyWindowInsets.getSystemWindowInsetRight();
                rect.bottom = windowInsetsCompatOnApplyWindowInsets.getSystemWindowInsetBottom();
                int childCount = ViewPager.this.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    WindowInsetsCompat windowInsetsCompatDispatchApplyWindowInsets = ViewCompat.dispatchApplyWindowInsets(ViewPager.this.getChildAt(i2), windowInsetsCompatOnApplyWindowInsets);
                    rect.left = Math.min(windowInsetsCompatDispatchApplyWindowInsets.getSystemWindowInsetLeft(), rect.left);
                    rect.top = Math.min(windowInsetsCompatDispatchApplyWindowInsets.getSystemWindowInsetTop(), rect.top);
                    rect.right = Math.min(windowInsetsCompatDispatchApplyWindowInsets.getSystemWindowInsetRight(), rect.right);
                    rect.bottom = Math.min(windowInsetsCompatDispatchApplyWindowInsets.getSystemWindowInsetBottom(), rect.bottom);
                }
                return windowInsetsCompatOnApplyWindowInsets.replaceSystemWindowInsets(rect.left, rect.top, rect.right, rect.bottom);
            }
        });
    }

    public boolean isFakeDragging() {
        return this.mFakeDragging;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        removeCallbacks(this.mEndScrollRunnable);
        Scroller scroller = this.mScroller;
        if (scroller != null && !scroller.isFinished()) {
            this.mScroller.abortAnimation();
        }
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        int i2;
        float f2;
        super.onDraw(canvas);
        if (this.mPageMargin <= 0 || this.mMarginDrawable == null || this.mItems.size() <= 0 || this.mAdapter == null) {
            return;
        }
        int scrollX = getScrollX();
        int width = getWidth();
        float f3 = width;
        float f4 = this.mPageMargin / f3;
        int i3 = 0;
        ItemInfo itemInfo = this.mItems.get(0);
        float f5 = itemInfo.offset;
        int size = this.mItems.size();
        int i4 = itemInfo.position;
        int i5 = this.mItems.get(size - 1).position;
        while (i4 < i5) {
            while (true) {
                i2 = itemInfo.position;
                if (i4 <= i2 || i3 >= size) {
                    break;
                }
                i3++;
                itemInfo = this.mItems.get(i3);
            }
            if (i4 == i2) {
                float f6 = itemInfo.offset;
                float f7 = itemInfo.widthFactor;
                f2 = (f6 + f7) * f3;
                f5 = f6 + f7 + f4;
            } else {
                float pageWidth = this.mAdapter.getPageWidth(i4);
                f2 = (f5 + pageWidth) * f3;
                f5 = pageWidth + f4 + f5;
            }
            if (this.mPageMargin + f2 > scrollX) {
                this.mMarginDrawable.setBounds(Math.round(f2), this.mTopPageBounds, Math.round(this.mPageMargin + f2), this.mBottomPageBounds);
                this.mMarginDrawable.draw(canvas);
            }
            if (f2 > scrollX + width) {
                return;
            }
            i4++;
            f4 = f4;
        }
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (action == 3 || action == 1) {
            resetTouch();
            return false;
        }
        if (action != 0) {
            if (this.mIsBeingDragged) {
                return true;
            }
            if (this.mIsUnableToDrag) {
                return false;
            }
        }
        if (action == 0) {
            float x2 = motionEvent.getX();
            this.mInitialMotionX = x2;
            this.mLastMotionX = x2;
            float y2 = motionEvent.getY();
            this.mInitialMotionY = y2;
            this.mLastMotionY = y2;
            this.mActivePointerId = motionEvent.getPointerId(0);
            this.mIsUnableToDrag = false;
            this.mIsScrollStarted = true;
            this.mScroller.computeScrollOffset();
            if (this.mScrollState != 2 || Math.abs(this.mScroller.getFinalX() - this.mScroller.getCurrX()) <= this.mCloseEnough) {
                completeScroll(false);
                this.mIsBeingDragged = false;
            } else {
                this.mScroller.abortAnimation();
                this.mPopulatePending = false;
                populate();
                this.mIsBeingDragged = true;
                requestParentDisallowInterceptTouchEvent(true);
                setScrollState(1);
            }
        } else if (action == 2) {
            int i2 = this.mActivePointerId;
            if (i2 != -1) {
                int iFindPointerIndex = motionEvent.findPointerIndex(i2);
                float x3 = motionEvent.getX(iFindPointerIndex);
                float f2 = x3 - this.mLastMotionX;
                float fAbs = Math.abs(f2);
                float y3 = motionEvent.getY(iFindPointerIndex);
                float fAbs2 = Math.abs(y3 - this.mInitialMotionY);
                if (f2 != 0.0f && !isGutterDrag(this.mLastMotionX, f2) && canScroll(this, false, (int) f2, (int) x3, (int) y3)) {
                    this.mLastMotionX = x3;
                    this.mLastMotionY = y3;
                    this.mIsUnableToDrag = true;
                    return false;
                }
                int i3 = this.mTouchSlop;
                if (fAbs > i3 && fAbs * 0.5f > fAbs2) {
                    this.mIsBeingDragged = true;
                    requestParentDisallowInterceptTouchEvent(true);
                    setScrollState(1);
                    float f3 = this.mInitialMotionX;
                    float f4 = this.mTouchSlop;
                    this.mLastMotionX = f2 > 0.0f ? f3 + f4 : f3 - f4;
                    this.mLastMotionY = y3;
                    setScrollingCacheEnabled(true);
                } else if (fAbs2 > i3) {
                    this.mIsUnableToDrag = true;
                }
                if (this.mIsBeingDragged && performDrag(x3)) {
                    ViewCompat.postInvalidateOnAnimation(this);
                }
            }
        } else if (action == 6) {
            onSecondaryPointerUp(motionEvent);
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        return this.mIsBeingDragged;
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0072  */
    /* JADX WARN: Code duplicated, block: B:24:0x0076  */
    /* JADX WARN: Code duplicated, block: B:26:0x007a  */
    /* JADX WARN: Code duplicated, block: B:27:0x007c  */
    /* JADX WARN: Code duplicated, block: B:29:0x008e  */
    /* JADX WARN: Code duplicated, block: B:30:0x0094  */
    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        boolean z3;
        ItemInfo itemInfoInfoForChild;
        int iMax;
        int measuredWidth;
        int iMax2;
        int measuredHeight;
        int childCount = getChildCount();
        int i6 = i4 - i2;
        int i7 = i5 - i3;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int scrollX = getScrollX();
        int i8 = 0;
        for (int i9 = 0; i9 < childCount; i9++) {
            View childAt = getChildAt(i9);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.isDecor) {
                    int i10 = layoutParams.gravity;
                    int i11 = i10 & 7;
                    int i12 = i10 & 112;
                    if (i11 != 1) {
                        if (i11 == 3) {
                            measuredWidth = childAt.getMeasuredWidth() + paddingLeft;
                        } else if (i11 != 5) {
                            measuredWidth = paddingLeft;
                        } else {
                            iMax = (i6 - paddingRight) - childAt.getMeasuredWidth();
                            paddingRight += childAt.getMeasuredWidth();
                        }
                        if (i12 != 16) {
                            if (i12 != 48) {
                                measuredHeight = childAt.getMeasuredHeight() + paddingTop;
                            } else if (i12 != 80) {
                                measuredHeight = paddingTop;
                            } else {
                                iMax2 = (i7 - paddingBottom) - childAt.getMeasuredHeight();
                                paddingBottom += childAt.getMeasuredHeight();
                            }
                            int i13 = paddingLeft + scrollX;
                            childAt.layout(i13, paddingTop, childAt.getMeasuredWidth() + i13, childAt.getMeasuredHeight() + paddingTop);
                            i8++;
                            paddingTop = measuredHeight;
                            paddingLeft = measuredWidth;
                        } else {
                            iMax2 = Math.max((i7 - childAt.getMeasuredHeight()) / 2, paddingTop);
                        }
                        int i14 = iMax2;
                        measuredHeight = paddingTop;
                        paddingTop = i14;
                        int i15 = paddingLeft + scrollX;
                        childAt.layout(i15, paddingTop, childAt.getMeasuredWidth() + i15, childAt.getMeasuredHeight() + paddingTop);
                        i8++;
                        paddingTop = measuredHeight;
                        paddingLeft = measuredWidth;
                    } else {
                        iMax = Math.max((i6 - childAt.getMeasuredWidth()) / 2, paddingLeft);
                    }
                    int i16 = iMax;
                    measuredWidth = paddingLeft;
                    paddingLeft = i16;
                    if (i12 != 16) {
                        if (i12 != 48) {
                            measuredHeight = childAt.getMeasuredHeight() + paddingTop;
                        } else if (i12 != 80) {
                            measuredHeight = paddingTop;
                        } else {
                            iMax2 = (i7 - paddingBottom) - childAt.getMeasuredHeight();
                            paddingBottom += childAt.getMeasuredHeight();
                        }
                        int i17 = paddingLeft + scrollX;
                        childAt.layout(i17, paddingTop, childAt.getMeasuredWidth() + i17, childAt.getMeasuredHeight() + paddingTop);
                        i8++;
                        paddingTop = measuredHeight;
                        paddingLeft = measuredWidth;
                    } else {
                        iMax2 = Math.max((i7 - childAt.getMeasuredHeight()) / 2, paddingTop);
                    }
                    int i18 = iMax2;
                    measuredHeight = paddingTop;
                    paddingTop = i18;
                    int i19 = paddingLeft + scrollX;
                    childAt.layout(i19, paddingTop, childAt.getMeasuredWidth() + i19, childAt.getMeasuredHeight() + paddingTop);
                    i8++;
                    paddingTop = measuredHeight;
                    paddingLeft = measuredWidth;
                }
            }
        }
        int i20 = (i6 - paddingLeft) - paddingRight;
        for (int i21 = 0; i21 < childCount; i21++) {
            View childAt2 = getChildAt(i21);
            if (childAt2.getVisibility() != 8) {
                LayoutParams layoutParams2 = (LayoutParams) childAt2.getLayoutParams();
                if (!layoutParams2.isDecor && (itemInfoInfoForChild = infoForChild(childAt2)) != null) {
                    float f2 = i20;
                    int i22 = ((int) (itemInfoInfoForChild.offset * f2)) + paddingLeft;
                    if (layoutParams2.needsMeasure) {
                        layoutParams2.needsMeasure = false;
                        childAt2.measure(View.MeasureSpec.makeMeasureSpec((int) (f2 * layoutParams2.widthFactor), BasicMeasure.EXACTLY), View.MeasureSpec.makeMeasureSpec((i7 - paddingTop) - paddingBottom, BasicMeasure.EXACTLY));
                    }
                    childAt2.layout(i22, paddingTop, childAt2.getMeasuredWidth() + i22, childAt2.getMeasuredHeight() + paddingTop);
                }
            }
        }
        this.mTopPageBounds = paddingTop;
        this.mBottomPageBounds = i7 - paddingBottom;
        this.mDecorChildCount = i8;
        if (this.mFirstLayout) {
            z3 = false;
            scrollToItem(this.mCurItem, false, 0, false);
        } else {
            z3 = false;
        }
        this.mFirstLayout = z3;
    }

    /* JADX WARN: Code duplicated, block: B:32:0x0081 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:34:0x0086  */
    /* JADX WARN: Code duplicated, block: B:35:0x0088  */
    /* JADX WARN: Code duplicated, block: B:38:0x008d A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:40:0x0090  */
    /* JADX WARN: Code duplicated, block: B:41:0x0092  */
    /* JADX WARN: Code duplicated, block: B:44:0x00a1  */
    /* JADX WARN: Code duplicated, block: B:45:0x00a7 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:46:0x00a9  */
    /* JADX WARN: Code duplicated, block: B:65:0x00ae A[SYNTHETIC] */
    @Override // android.view.View
    public void onMeasure(int i2, int i3) {
        LayoutParams layoutParams;
        LayoutParams layoutParams2;
        int i4;
        int i5;
        int i6;
        setMeasuredDimension(View.getDefaultSize(0, i2), View.getDefaultSize(0, i3));
        int measuredWidth = getMeasuredWidth();
        this.mGutterSize = Math.min(measuredWidth / 10, this.mDefaultGutterSize);
        int paddingLeft = (measuredWidth - getPaddingLeft()) - getPaddingRight();
        int measuredHeight = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
        int childCount = getChildCount();
        int i7 = 0;
        while (true) {
            boolean z2 = true;
            int i8 = BasicMeasure.EXACTLY;
            if (i7 >= childCount) {
                break;
            }
            View childAt = getChildAt(i7);
            if (childAt.getVisibility() != 8 && (layoutParams2 = (LayoutParams) childAt.getLayoutParams()) != null && layoutParams2.isDecor) {
                int i9 = layoutParams2.gravity;
                int i10 = i9 & 7;
                int i11 = i9 & 112;
                boolean z3 = i11 == 48 || i11 == 80;
                if (i10 != 3 && i10 != 5) {
                    z2 = false;
                }
                int i12 = Integer.MIN_VALUE;
                if (z3) {
                    i12 = BasicMeasure.EXACTLY;
                } else {
                    i4 = z2 ? BasicMeasure.EXACTLY : Integer.MIN_VALUE;
                    i5 = ((ViewGroup.LayoutParams) layoutParams2).width;
                    if (i5 != -2) {
                        if (i5 == -1) {
                            i5 = paddingLeft;
                        }
                        i12 = BasicMeasure.EXACTLY;
                    } else {
                        i5 = paddingLeft;
                    }
                    i6 = ((ViewGroup.LayoutParams) layoutParams2).height;
                    if (i6 != -2) {
                        i6 = measuredHeight;
                        i8 = i4;
                    } else if (i6 == -1) {
                        i6 = measuredHeight;
                    }
                    childAt.measure(View.MeasureSpec.makeMeasureSpec(i5, i12), View.MeasureSpec.makeMeasureSpec(i6, i8));
                    if (z3) {
                        measuredHeight -= childAt.getMeasuredHeight();
                    } else if (z2) {
                        paddingLeft -= childAt.getMeasuredWidth();
                    }
                }
                i5 = ((ViewGroup.LayoutParams) layoutParams2).width;
                if (i5 != -2) {
                    if (i5 == -1) {
                        i5 = paddingLeft;
                    }
                    i12 = BasicMeasure.EXACTLY;
                } else {
                    i5 = paddingLeft;
                }
                i6 = ((ViewGroup.LayoutParams) layoutParams2).height;
                if (i6 != -2) {
                    i6 = measuredHeight;
                    i8 = i4;
                } else if (i6 == -1) {
                    i6 = measuredHeight;
                }
                childAt.measure(View.MeasureSpec.makeMeasureSpec(i5, i12), View.MeasureSpec.makeMeasureSpec(i6, i8));
                if (z3) {
                    measuredHeight -= childAt.getMeasuredHeight();
                } else if (z2) {
                    paddingLeft -= childAt.getMeasuredWidth();
                }
            }
            i7++;
        }
        this.mChildWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(paddingLeft, BasicMeasure.EXACTLY);
        this.mChildHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(measuredHeight, BasicMeasure.EXACTLY);
        this.mInLayout = true;
        populate();
        this.mInLayout = false;
        int childCount2 = getChildCount();
        for (int i13 = 0; i13 < childCount2; i13++) {
            View childAt2 = getChildAt(i13);
            if (childAt2.getVisibility() != 8 && ((layoutParams = (LayoutParams) childAt2.getLayoutParams()) == null || !layoutParams.isDecor)) {
                childAt2.measure(View.MeasureSpec.makeMeasureSpec((int) (paddingLeft * layoutParams.widthFactor), BasicMeasure.EXACTLY), this.mChildHeightMeasureSpec);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0065  */
    @CallSuper
    public void onPageScrolled(int i2, float f2, int i3) {
        int iMax;
        int width;
        int left;
        if (this.mDecorChildCount > 0) {
            int scrollX = getScrollX();
            int paddingLeft = getPaddingLeft();
            int paddingRight = getPaddingRight();
            int width2 = getWidth();
            int childCount = getChildCount();
            for (int i4 = 0; i4 < childCount; i4++) {
                View childAt = getChildAt(i4);
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.isDecor) {
                    int i5 = layoutParams.gravity & 7;
                    if (i5 != 1) {
                        if (i5 == 3) {
                            width = childAt.getWidth() + paddingLeft;
                        } else if (i5 != 5) {
                            width = paddingLeft;
                        } else {
                            iMax = (width2 - paddingRight) - childAt.getMeasuredWidth();
                            paddingRight += childAt.getMeasuredWidth();
                        }
                        left = (paddingLeft + scrollX) - childAt.getLeft();
                        if (left != 0) {
                            childAt.offsetLeftAndRight(left);
                        }
                        paddingLeft = width;
                    } else {
                        iMax = Math.max((width2 - childAt.getMeasuredWidth()) / 2, paddingLeft);
                    }
                    int i6 = iMax;
                    width = paddingLeft;
                    paddingLeft = i6;
                    left = (paddingLeft + scrollX) - childAt.getLeft();
                    if (left != 0) {
                        childAt.offsetLeftAndRight(left);
                    }
                    paddingLeft = width;
                }
            }
        }
        dispatchOnPageScrolled(i2, f2, i3);
        if (this.mPageTransformer != null) {
            int scrollX2 = getScrollX();
            int childCount2 = getChildCount();
            for (int i7 = 0; i7 < childCount2; i7++) {
                View childAt2 = getChildAt(i7);
                if (!((LayoutParams) childAt2.getLayoutParams()).isDecor) {
                    this.mPageTransformer.transformPage(childAt2, (childAt2.getLeft() - scrollX2) / getClientWidth());
                }
            }
        }
        this.mCalledSuper = true;
    }

    @Override // android.view.ViewGroup
    public boolean onRequestFocusInDescendants(int i2, Rect rect) {
        int i3;
        int i4;
        int i5;
        ItemInfo itemInfoInfoForChild;
        int childCount = getChildCount();
        if ((i2 & 2) != 0) {
            i4 = childCount;
            i3 = 0;
            i5 = 1;
        } else {
            i3 = childCount - 1;
            i4 = -1;
            i5 = -1;
        }
        while (i3 != i4) {
            View childAt = getChildAt(i3);
            if (childAt.getVisibility() == 0 && (itemInfoInfoForChild = infoForChild(childAt)) != null && itemInfoInfoForChild.position == this.mCurItem && childAt.requestFocus(i2, rect)) {
                return true;
            }
            i3 += i5;
        }
        return false;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        PagerAdapter pagerAdapter = this.mAdapter;
        if (pagerAdapter != null) {
            pagerAdapter.restoreState(savedState.adapterState, savedState.loader);
            setCurrentItemInternal(savedState.position, false, true);
        } else {
            this.mRestoredCurItem = savedState.position;
            this.mRestoredAdapterState = savedState.adapterState;
            this.mRestoredClassLoader = savedState.loader;
        }
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.position = this.mCurItem;
        PagerAdapter pagerAdapter = this.mAdapter;
        if (pagerAdapter != null) {
            savedState.adapterState = pagerAdapter.saveState();
        }
        return savedState;
    }

    @Override // android.view.View
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        if (i2 != i4) {
            int i6 = this.mPageMargin;
            recomputeScrollPosition(i2, i4, i6, i6);
        }
    }

    /* JADX WARN: Code duplicated, block: B:60:0x0132  */
    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        PagerAdapter pagerAdapter;
        int pointerId;
        boolean zResetTouch;
        if (this.mFakeDragging) {
            return true;
        }
        if ((motionEvent.getAction() == 0 && motionEvent.getEdgeFlags() != 0) || (pagerAdapter = this.mAdapter) == null || pagerAdapter.getCount() == 0) {
            return false;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        int action = motionEvent.getAction() & 255;
        if (action != 0) {
            if (action != 1) {
                if (action == 2) {
                    if (!this.mIsBeingDragged) {
                        int iFindPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                        if (iFindPointerIndex == -1) {
                            zResetTouch = resetTouch();
                        } else {
                            float x2 = motionEvent.getX(iFindPointerIndex);
                            float fAbs = Math.abs(x2 - this.mLastMotionX);
                            float y2 = motionEvent.getY(iFindPointerIndex);
                            float fAbs2 = Math.abs(y2 - this.mLastMotionY);
                            if (fAbs > this.mTouchSlop && fAbs > fAbs2) {
                                this.mIsBeingDragged = true;
                                requestParentDisallowInterceptTouchEvent(true);
                                float f2 = this.mInitialMotionX;
                                this.mLastMotionX = x2 - f2 > 0.0f ? f2 + this.mTouchSlop : f2 - this.mTouchSlop;
                                this.mLastMotionY = y2;
                                setScrollState(1);
                                setScrollingCacheEnabled(true);
                                ViewParent parent = getParent();
                                if (parent != null) {
                                    parent.requestDisallowInterceptTouchEvent(true);
                                }
                            }
                        }
                        if (zResetTouch) {
                            ViewCompat.postInvalidateOnAnimation(this);
                        }
                    }
                    if (this.mIsBeingDragged) {
                        zResetTouch = performDrag(motionEvent.getX(motionEvent.findPointerIndex(this.mActivePointerId)));
                        if (zResetTouch) {
                            ViewCompat.postInvalidateOnAnimation(this);
                        }
                    }
                } else if (action != 3) {
                    if (action == 5) {
                        int actionIndex = motionEvent.getActionIndex();
                        this.mLastMotionX = motionEvent.getX(actionIndex);
                        pointerId = motionEvent.getPointerId(actionIndex);
                    } else if (action == 6) {
                        onSecondaryPointerUp(motionEvent);
                        this.mLastMotionX = motionEvent.getX(motionEvent.findPointerIndex(this.mActivePointerId));
                    }
                } else if (this.mIsBeingDragged) {
                    scrollToItem(this.mCurItem, true, 0, false);
                    zResetTouch = resetTouch();
                    if (zResetTouch) {
                        ViewCompat.postInvalidateOnAnimation(this);
                    }
                }
            } else if (this.mIsBeingDragged) {
                VelocityTracker velocityTracker = this.mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
                int xVelocity = (int) velocityTracker.getXVelocity(this.mActivePointerId);
                this.mPopulatePending = true;
                int clientWidth = getClientWidth();
                int scrollX = getScrollX();
                ItemInfo itemInfoInfoForCurrentScrollPosition = infoForCurrentScrollPosition();
                float f3 = clientWidth;
                setCurrentItemInternal(determineTargetPage(itemInfoInfoForCurrentScrollPosition.position, ((scrollX / f3) - itemInfoInfoForCurrentScrollPosition.offset) / (itemInfoInfoForCurrentScrollPosition.widthFactor + (this.mPageMargin / f3)), xVelocity, (int) (motionEvent.getX(motionEvent.findPointerIndex(this.mActivePointerId)) - this.mInitialMotionX)), true, true, xVelocity);
                zResetTouch = resetTouch();
                if (zResetTouch) {
                    ViewCompat.postInvalidateOnAnimation(this);
                }
            }
            return true;
        }
        this.mScroller.abortAnimation();
        this.mPopulatePending = false;
        populate();
        float x3 = motionEvent.getX();
        this.mInitialMotionX = x3;
        this.mLastMotionX = x3;
        float y3 = motionEvent.getY();
        this.mInitialMotionY = y3;
        this.mLastMotionY = y3;
        pointerId = motionEvent.getPointerId(0);
        this.mActivePointerId = pointerId;
        return true;
    }

    public boolean pageLeft() {
        int i2 = this.mCurItem;
        if (i2 <= 0) {
            return false;
        }
        setCurrentItem(i2 - 1, true);
        return true;
    }

    public boolean pageRight() {
        PagerAdapter pagerAdapter = this.mAdapter;
        if (pagerAdapter == null || this.mCurItem >= pagerAdapter.getCount() - 1) {
            return false;
        }
        setCurrentItem(this.mCurItem + 1, true);
        return true;
    }

    public void populate() {
        populate(this.mCurItem);
    }

    public void removeOnAdapterChangeListener(@NonNull OnAdapterChangeListener onAdapterChangeListener) {
        List<OnAdapterChangeListener> list = this.mAdapterChangeListeners;
        if (list != null) {
            list.remove(onAdapterChangeListener);
        }
    }

    public void removeOnPageChangeListener(@NonNull OnPageChangeListener onPageChangeListener) {
        List<OnPageChangeListener> list = this.mOnPageChangeListeners;
        if (list != null) {
            list.remove(onPageChangeListener);
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public void removeView(View view) {
        if (this.mInLayout) {
            removeViewInLayout(view);
        } else {
            super.removeView(view);
        }
    }

    public void setAdapter(@Nullable PagerAdapter pagerAdapter) {
        PagerAdapter pagerAdapter2 = this.mAdapter;
        if (pagerAdapter2 != null) {
            pagerAdapter2.setViewPagerObserver(null);
            this.mAdapter.startUpdate((ViewGroup) this);
            for (int i2 = 0; i2 < this.mItems.size(); i2++) {
                ItemInfo itemInfo = this.mItems.get(i2);
                this.mAdapter.destroyItem((ViewGroup) this, itemInfo.position, itemInfo.object);
            }
            this.mAdapter.finishUpdate((ViewGroup) this);
            this.mItems.clear();
            removeNonDecorViews();
            this.mCurItem = 0;
            scrollTo(0, 0);
        }
        PagerAdapter pagerAdapter3 = this.mAdapter;
        this.mAdapter = pagerAdapter;
        this.mExpectedAdapterCount = 0;
        if (pagerAdapter != null) {
            if (this.mObserver == null) {
                this.mObserver = new PagerObserver();
            }
            this.mAdapter.setViewPagerObserver(this.mObserver);
            this.mPopulatePending = false;
            boolean z2 = this.mFirstLayout;
            this.mFirstLayout = true;
            this.mExpectedAdapterCount = this.mAdapter.getCount();
            if (this.mRestoredCurItem >= 0) {
                this.mAdapter.restoreState(this.mRestoredAdapterState, this.mRestoredClassLoader);
                setCurrentItemInternal(this.mRestoredCurItem, false, true);
                this.mRestoredCurItem = -1;
                this.mRestoredAdapterState = null;
                this.mRestoredClassLoader = null;
            } else if (z2) {
                requestLayout();
            } else {
                populate();
            }
        }
        List<OnAdapterChangeListener> list = this.mAdapterChangeListeners;
        if (list == null || list.isEmpty()) {
            return;
        }
        int size = this.mAdapterChangeListeners.size();
        for (int i3 = 0; i3 < size; i3++) {
            this.mAdapterChangeListeners.get(i3).onAdapterChanged(this, pagerAdapter3, pagerAdapter);
        }
    }

    public void setCurrentItem(int i2) {
        this.mPopulatePending = false;
        setCurrentItemInternal(i2, !this.mFirstLayout, false);
    }

    public void setCurrentItemInternal(int i2, boolean z2, boolean z3) {
        setCurrentItemInternal(i2, z2, z3, 0);
    }

    public OnPageChangeListener setInternalPageChangeListener(OnPageChangeListener onPageChangeListener) {
        OnPageChangeListener onPageChangeListener2 = this.mInternalPageChangeListener;
        this.mInternalPageChangeListener = onPageChangeListener;
        return onPageChangeListener2;
    }

    public void setOffscreenPageLimit(int i2) {
        if (i2 < 1) {
            i2 = 1;
        }
        if (i2 != this.mOffscreenPageLimit) {
            this.mOffscreenPageLimit = i2;
            populate();
        }
    }

    @Deprecated
    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.mOnPageChangeListener = onPageChangeListener;
    }

    public void setPageMargin(int i2) {
        int i3 = this.mPageMargin;
        this.mPageMargin = i2;
        int width = getWidth();
        recomputeScrollPosition(width, width, i2, i3);
        requestLayout();
    }

    public void setPageMarginDrawable(@DrawableRes int i2) {
        setPageMarginDrawable(ContextCompat.getDrawable(getContext(), i2));
    }

    public void setPageTransformer(boolean z2, @Nullable PageTransformer pageTransformer) {
        setPageTransformer(z2, pageTransformer, 2);
    }

    public void setScrollState(int i2) {
        if (this.mScrollState == i2) {
            return;
        }
        this.mScrollState = i2;
        if (this.mPageTransformer != null) {
            enableLayers(i2 != 0);
        }
        dispatchOnScrollStateChanged(i2);
    }

    public void smoothScrollTo(int i2, int i3) {
        smoothScrollTo(i2, i3, 0);
    }

    @Override // android.view.View
    public boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mMarginDrawable;
    }

    public ViewPager(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mItems = new ArrayList<>();
        this.mTempItem = new ItemInfo();
        this.mTempRect = new Rect();
        this.mRestoredCurItem = -1;
        this.mRestoredAdapterState = null;
        this.mRestoredClassLoader = null;
        this.mFirstOffset = -3.4028235E38f;
        this.mLastOffset = Float.MAX_VALUE;
        this.mOffscreenPageLimit = 1;
        this.mActivePointerId = -1;
        this.mFirstLayout = true;
        this.mNeedCalculatePageOffsets = false;
        this.mEndScrollRunnable = new Runnable() { // from class: androidx.viewpager.widget.ViewPager.3
            @Override // java.lang.Runnable
            public void run() {
                ViewPager.this.setScrollState(0);
                ViewPager.this.populate();
            }
        };
        this.mScrollState = 0;
        initViewPager();
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return generateDefaultLayoutParams();
    }

    /* JADX WARN: Code duplicated, block: B:54:0x00c2 A[PHI: r7 r10 r14
      0x00c2: PHI (r7v9 int) = (r7v5 int), (r7v4 int), (r7v10 int) binds: [B:64:0x00e7, B:61:0x00d9, B:53:0x00c0] A[DONT_GENERATE, DONT_INLINE]
      0x00c2: PHI (r10v12 int) = (r10v1 int), (r10v8 int), (r10v13 int) binds: [B:64:0x00e7, B:61:0x00d9, B:53:0x00c0] A[DONT_GENERATE, DONT_INLINE]
      0x00c2: PHI (r14v9 float) = (r14v4 float), (r14v5 float), (r14v3 float) binds: [B:64:0x00e7, B:61:0x00d9, B:53:0x00c0] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:55:0x00cb A[PHI: r7 r10 r14
      0x00cb: PHI (r7v6 int) = (r7v5 int), (r7v4 int), (r7v10 int) binds: [B:64:0x00e7, B:61:0x00d9, B:53:0x00c0] A[DONT_GENERATE, DONT_INLINE]
      0x00cb: PHI (r10v9 int) = (r10v1 int), (r10v8 int), (r10v13 int) binds: [B:64:0x00e7, B:61:0x00d9, B:53:0x00c0] A[DONT_GENERATE, DONT_INLINE]
      0x00cb: PHI (r14v6 float) = (r14v4 float), (r14v5 float), (r14v3 float) binds: [B:64:0x00e7, B:61:0x00d9, B:53:0x00c0] A[DONT_GENERATE, DONT_INLINE]] */
    public void populate(int i2) {
        ItemInfo itemInfoInfoForPosition;
        String hexString;
        ItemInfo itemInfoAddNewItem;
        ItemInfo itemInfoInfoForChild;
        ItemInfo itemInfo;
        int i3 = this.mCurItem;
        if (i3 != i2) {
            itemInfoInfoForPosition = infoForPosition(i3);
            this.mCurItem = i2;
        } else {
            itemInfoInfoForPosition = null;
        }
        if (this.mAdapter == null) {
            sortChildDrawingOrder();
            return;
        }
        if (this.mPopulatePending) {
            sortChildDrawingOrder();
            return;
        }
        if (getWindowToken() == null) {
            return;
        }
        this.mAdapter.startUpdate((ViewGroup) this);
        int i4 = this.mOffscreenPageLimit;
        int iMax = Math.max(0, this.mCurItem - i4);
        int count = this.mAdapter.getCount();
        int iMin = Math.min(count - 1, this.mCurItem + i4);
        if (count != this.mExpectedAdapterCount) {
            try {
                hexString = getResources().getResourceName(getId());
            } catch (Resources.NotFoundException unused) {
                hexString = Integer.toHexString(getId());
            }
            throw new IllegalStateException("The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: " + this.mExpectedAdapterCount + ", found: " + count + " Pager id: " + hexString + " Pager class: " + getClass() + " Problematic adapter: " + this.mAdapter.getClass());
        }
        int i5 = 0;
        while (true) {
            if (i5 < this.mItems.size()) {
                itemInfoAddNewItem = this.mItems.get(i5);
                int i6 = itemInfoAddNewItem.position;
                int i7 = this.mCurItem;
                if (i6 >= i7) {
                    if (i6 != i7) {
                        break;
                    } else {
                        break;
                    }
                }
                i5++;
            }
            itemInfoAddNewItem = null;
            break;
        }
        if (itemInfoAddNewItem == null && count > 0) {
            itemInfoAddNewItem = addNewItem(this.mCurItem, i5);
        }
        if (itemInfoAddNewItem != null) {
            int i8 = i5 - 1;
            ItemInfo itemInfo2 = i8 >= 0 ? this.mItems.get(i8) : null;
            int clientWidth = getClientWidth();
            float paddingLeft = clientWidth <= 0 ? 0.0f : (getPaddingLeft() / clientWidth) + (2.0f - itemInfoAddNewItem.widthFactor);
            float f2 = 0.0f;
            for (int i9 = this.mCurItem - 1; i9 >= 0; i9--) {
                if (f2 < paddingLeft || i9 >= iMax) {
                    if (itemInfo2 == null || i9 != itemInfo2.position) {
                        f2 += addNewItem(i9, i8 + 1).widthFactor;
                        i5++;
                        if (i8 >= 0) {
                            itemInfo = this.mItems.get(i8);
                        } else {
                            itemInfo = null;
                        }
                    } else {
                        f2 += itemInfo2.widthFactor;
                        i8--;
                        if (i8 >= 0) {
                            itemInfo = this.mItems.get(i8);
                        } else {
                            itemInfo = null;
                        }
                    }
                    itemInfo2 = itemInfo;
                } else {
                    if (itemInfo2 == null) {
                        break;
                    }
                    if (i9 == itemInfo2.position && !itemInfo2.scrolling) {
                        this.mItems.remove(i8);
                        this.mAdapter.destroyItem((ViewGroup) this, i9, itemInfo2.object);
                        i8--;
                        i5--;
                        if (i8 >= 0) {
                            itemInfo = this.mItems.get(i8);
                        } else {
                            itemInfo = null;
                        }
                        itemInfo2 = itemInfo;
                    }
                }
            }
            float f3 = itemInfoAddNewItem.widthFactor;
            int i10 = i5 + 1;
            if (f3 < 2.0f) {
                ItemInfo itemInfo3 = i10 < this.mItems.size() ? this.mItems.get(i10) : null;
                float paddingRight = clientWidth <= 0 ? 0.0f : (getPaddingRight() / clientWidth) + 2.0f;
                int i11 = this.mCurItem;
                while (true) {
                    i11++;
                    if (i11 >= count) {
                        break;
                    }
                    if (f3 >= paddingRight && i11 > iMin) {
                        if (itemInfo3 == null) {
                            break;
                        }
                        if (i11 == itemInfo3.position && !itemInfo3.scrolling) {
                            this.mItems.remove(i10);
                            this.mAdapter.destroyItem((ViewGroup) this, i11, itemInfo3.object);
                            if (i10 < this.mItems.size()) {
                            }
                        }
                    } else if (itemInfo3 == null || i11 != itemInfo3.position) {
                        ItemInfo itemInfoAddNewItem2 = addNewItem(i11, i10);
                        i10++;
                        f3 += itemInfoAddNewItem2.widthFactor;
                        itemInfo3 = i10 < this.mItems.size() ? this.mItems.get(i10) : null;
                    } else {
                        f3 += itemInfo3.widthFactor;
                        i10++;
                        if (i10 < this.mItems.size()) {
                        }
                    }
                }
            }
            calculatePageOffsets(itemInfoAddNewItem, i5, itemInfoInfoForPosition);
            this.mAdapter.setPrimaryItem((ViewGroup) this, this.mCurItem, itemInfoAddNewItem.object);
        }
        this.mAdapter.finishUpdate((ViewGroup) this);
        int childCount = getChildCount();
        for (int i12 = 0; i12 < childCount; i12++) {
            View childAt = getChildAt(i12);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            layoutParams.childIndex = i12;
            if (!layoutParams.isDecor && layoutParams.widthFactor == 0.0f && (itemInfoInfoForChild = infoForChild(childAt)) != null) {
                layoutParams.widthFactor = itemInfoInfoForChild.widthFactor;
                layoutParams.position = itemInfoInfoForChild.position;
            }
        }
        sortChildDrawingOrder();
        if (hasFocus()) {
            View viewFindFocus = findFocus();
            ItemInfo itemInfoInfoForAnyChild = viewFindFocus != null ? infoForAnyChild(viewFindFocus) : null;
            if (itemInfoInfoForAnyChild == null || itemInfoInfoForAnyChild.position != this.mCurItem) {
                for (int i13 = 0; i13 < getChildCount(); i13++) {
                    View childAt2 = getChildAt(i13);
                    ItemInfo itemInfoInfoForChild2 = infoForChild(childAt2);
                    if (itemInfoInfoForChild2 != null && itemInfoInfoForChild2.position == this.mCurItem && childAt2.requestFocus(2)) {
                        return;
                    }
                }
            }
        }
    }

    public void setCurrentItem(int i2, boolean z2) {
        this.mPopulatePending = false;
        setCurrentItemInternal(i2, z2, false);
    }

    public void setCurrentItemInternal(int i2, boolean z2, boolean z3, int i3) {
        PagerAdapter pagerAdapter = this.mAdapter;
        if (pagerAdapter == null || pagerAdapter.getCount() <= 0) {
            setScrollingCacheEnabled(false);
            return;
        }
        if (!z3 && this.mCurItem == i2 && this.mItems.size() != 0) {
            setScrollingCacheEnabled(false);
            return;
        }
        if (i2 < 0) {
            i2 = 0;
        } else if (i2 >= this.mAdapter.getCount()) {
            i2 = this.mAdapter.getCount() - 1;
        }
        int i4 = this.mOffscreenPageLimit;
        int i5 = this.mCurItem;
        if (i2 > i5 + i4 || i2 < i5 - i4) {
            for (int i6 = 0; i6 < this.mItems.size(); i6++) {
                this.mItems.get(i6).scrolling = true;
            }
        }
        boolean z4 = this.mCurItem != i2;
        if (!this.mFirstLayout) {
            populate(i2);
            scrollToItem(i2, z2, i3, z4);
        } else {
            this.mCurItem = i2;
            if (z4) {
                dispatchOnPageSelected(i2);
            }
            requestLayout();
        }
    }

    public void setPageMarginDrawable(@Nullable Drawable drawable) {
        this.mMarginDrawable = drawable;
        if (drawable != null) {
            refreshDrawableState();
        }
        setWillNotDraw(drawable == null);
        invalidate();
    }

    public void setPageTransformer(boolean z2, @Nullable PageTransformer pageTransformer, int i2) {
        boolean z3 = pageTransformer != null;
        boolean z4 = z3 != (this.mPageTransformer != null);
        this.mPageTransformer = pageTransformer;
        setChildrenDrawingOrderEnabled(z3);
        if (z3) {
            this.mDrawingOrder = z2 ? 2 : 1;
            this.mPageTransformerLayerType = i2;
        } else {
            this.mDrawingOrder = 0;
        }
        if (z4) {
            populate();
        }
    }

    public void smoothScrollTo(int i2, int i3, int i4) {
        int scrollX;
        int iAbs;
        if (getChildCount() == 0) {
            setScrollingCacheEnabled(false);
            return;
        }
        Scroller scroller = this.mScroller;
        if (scroller == null || scroller.isFinished()) {
            scrollX = getScrollX();
        } else {
            scrollX = this.mIsScrollStarted ? this.mScroller.getCurrX() : this.mScroller.getStartX();
            this.mScroller.abortAnimation();
            setScrollingCacheEnabled(false);
        }
        int i5 = scrollX;
        int scrollY = getScrollY();
        int i6 = i2 - i5;
        int i7 = i3 - scrollY;
        if (i6 == 0 && i7 == 0) {
            completeScroll(false);
            populate();
            setScrollState(0);
            return;
        }
        setScrollingCacheEnabled(true);
        setScrollState(2);
        int clientWidth = getClientWidth();
        int i8 = clientWidth / 2;
        float f2 = clientWidth;
        float f3 = i8;
        float fDistanceInfluenceForSnapDuration = (distanceInfluenceForSnapDuration(Math.min(1.0f, (Math.abs(i6) * 1.0f) / f2)) * f3) + f3;
        int iAbs2 = Math.abs(i4);
        if (iAbs2 > 0) {
            iAbs = Math.round(Math.abs(fDistanceInfluenceForSnapDuration / iAbs2) * 1000.0f) * 4;
        } else {
            iAbs = (int) (((Math.abs(i6) / ((this.mAdapter.getPageWidth(this.mCurItem) * f2) + this.mPageMargin)) + 1.0f) * 100.0f);
        }
        int iMin = Math.min(iAbs, MAX_SETTLE_DURATION);
        this.mIsScrollStarted = false;
        this.mScroller.startScroll(i5, scrollY, i6, i7, iMin);
        ViewCompat.postInvalidateOnAnimation(this);
    }
}
