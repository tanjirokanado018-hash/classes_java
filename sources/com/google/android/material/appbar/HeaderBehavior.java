package com.google.android.material.appbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
abstract class HeaderBehavior<V extends View> extends ViewOffsetBehavior<V> {
    private static final int INVALID_POINTER = -1;
    private int activePointerId;

    @Nullable
    private Runnable flingRunnable;
    private boolean isBeingDragged;
    private int lastMotionY;
    OverScroller scroller;
    private int touchSlop;

    @Nullable
    private VelocityTracker velocityTracker;

    public class FlingRunnable implements Runnable {
        private final V layout;
        private final CoordinatorLayout parent;

        public FlingRunnable(CoordinatorLayout coordinatorLayout, V v2) {
            this.parent = coordinatorLayout;
            this.layout = v2;
        }

        @Override // java.lang.Runnable
        public void run() {
            OverScroller overScroller;
            if (this.layout == null || (overScroller = HeaderBehavior.this.scroller) == null) {
                return;
            }
            if (!overScroller.computeScrollOffset()) {
                HeaderBehavior.this.onFlingFinished(this.parent, this.layout);
                return;
            }
            HeaderBehavior headerBehavior = HeaderBehavior.this;
            headerBehavior.setHeaderTopBottomOffset(this.parent, this.layout, headerBehavior.scroller.getCurrY());
            ViewCompat.postOnAnimation(this.layout, this);
        }
    }

    public HeaderBehavior() {
        this.activePointerId = -1;
        this.touchSlop = -1;
    }

    private void ensureVelocityTracker() {
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
    }

    public boolean canDragView(V v2) {
        return false;
    }

    public final boolean fling(CoordinatorLayout coordinatorLayout, @NonNull V v2, int i2, int i3, float f2) {
        Runnable runnable = this.flingRunnable;
        if (runnable != null) {
            v2.removeCallbacks(runnable);
            this.flingRunnable = null;
        }
        if (this.scroller == null) {
            this.scroller = new OverScroller(v2.getContext());
        }
        this.scroller.fling(0, getTopAndBottomOffset(), 0, Math.round(f2), 0, 0, i2, i3);
        if (!this.scroller.computeScrollOffset()) {
            onFlingFinished(coordinatorLayout, v2);
            return false;
        }
        FlingRunnable flingRunnable = new FlingRunnable(coordinatorLayout, v2);
        this.flingRunnable = flingRunnable;
        ViewCompat.postOnAnimation(v2, flingRunnable);
        return true;
    }

    public int getMaxDragOffset(@NonNull V v2) {
        return -v2.getHeight();
    }

    public int getScrollRangeForDragFling(@NonNull V v2) {
        return v2.getHeight();
    }

    public int getTopBottomOffsetForScrollingSibling() {
        return getTopAndBottomOffset();
    }

    public void onFlingFinished(CoordinatorLayout coordinatorLayout, V v2) {
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v2, @NonNull MotionEvent motionEvent) {
        int iFindPointerIndex;
        if (this.touchSlop < 0) {
            this.touchSlop = ViewConfiguration.get(coordinatorLayout.getContext()).getScaledTouchSlop();
        }
        if (motionEvent.getActionMasked() == 2 && this.isBeingDragged) {
            int i2 = this.activePointerId;
            if (i2 == -1 || (iFindPointerIndex = motionEvent.findPointerIndex(i2)) == -1) {
                return false;
            }
            int y2 = (int) motionEvent.getY(iFindPointerIndex);
            if (Math.abs(y2 - this.lastMotionY) > this.touchSlop) {
                this.lastMotionY = y2;
                return true;
            }
        }
        if (motionEvent.getActionMasked() == 0) {
            this.activePointerId = -1;
            int x2 = (int) motionEvent.getX();
            int y3 = (int) motionEvent.getY();
            boolean z2 = canDragView(v2) && coordinatorLayout.isPointInChildBounds(v2, x2, y3);
            this.isBeingDragged = z2;
            if (z2) {
                this.lastMotionY = y3;
                this.activePointerId = motionEvent.getPointerId(0);
                ensureVelocityTracker();
                OverScroller overScroller = this.scroller;
                if (overScroller != null && !overScroller.isFinished()) {
                    this.scroller.abortAnimation();
                    return true;
                }
            }
        }
        VelocityTracker velocityTracker = this.velocityTracker;
        if (velocityTracker != null) {
            velocityTracker.addMovement(motionEvent);
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:27:0x007b  */
    /* JADX WARN: Code duplicated, block: B:30:0x0085  */
    /* JADX WARN: Code duplicated, block: B:33:0x008c A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:37:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onTouchEvent(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v2, @NonNull MotionEvent motionEvent) {
        boolean z2;
        VelocityTracker velocityTracker;
        VelocityTracker velocityTracker2;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 1) {
            VelocityTracker velocityTracker3 = this.velocityTracker;
            if (velocityTracker3 != null) {
                velocityTracker3.addMovement(motionEvent);
                this.velocityTracker.computeCurrentVelocity(1000);
                fling(coordinatorLayout, v2, -getScrollRangeForDragFling(v2), 0, this.velocityTracker.getYVelocity(this.activePointerId));
                z2 = true;
            }
            this.isBeingDragged = false;
            this.activePointerId = -1;
            velocityTracker = this.velocityTracker;
            if (velocityTracker != null) {
                velocityTracker.recycle();
                this.velocityTracker = null;
            }
            velocityTracker2 = this.velocityTracker;
            if (velocityTracker2 != null) {
                velocityTracker2.addMovement(motionEvent);
            }
            if (this.isBeingDragged) {
                return true;
            }
        }
        if (actionMasked == 2) {
            int iFindPointerIndex = motionEvent.findPointerIndex(this.activePointerId);
            if (iFindPointerIndex == -1) {
                return false;
            }
            int y2 = (int) motionEvent.getY(iFindPointerIndex);
            int i2 = this.lastMotionY - y2;
            this.lastMotionY = y2;
            scroll(coordinatorLayout, v2, i2, getMaxDragOffset(v2), 0);
        } else if (actionMasked != 3) {
            if (actionMasked == 6) {
                int i3 = motionEvent.getActionIndex() == 0 ? 1 : 0;
                this.activePointerId = motionEvent.getPointerId(i3);
                this.lastMotionY = (int) (motionEvent.getY(i3) + 0.5f);
            }
        }
        z2 = false;
        velocityTracker2 = this.velocityTracker;
        if (velocityTracker2 != null) {
            velocityTracker2.addMovement(motionEvent);
        }
        return !this.isBeingDragged || z2;
        z2 = false;
        this.isBeingDragged = false;
        this.activePointerId = -1;
        velocityTracker = this.velocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.velocityTracker = null;
        }
        velocityTracker2 = this.velocityTracker;
        if (velocityTracker2 != null) {
            velocityTracker2.addMovement(motionEvent);
        }
        if (this.isBeingDragged) {
            return true;
        }
    }

    public final int scroll(CoordinatorLayout coordinatorLayout, V v2, int i2, int i3, int i4) {
        return setHeaderTopBottomOffset(coordinatorLayout, v2, getTopBottomOffsetForScrollingSibling() - i2, i3, i4);
    }

    public int setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, V v2, int i2) {
        return setHeaderTopBottomOffset(coordinatorLayout, v2, i2, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public HeaderBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.activePointerId = -1;
        this.touchSlop = -1;
    }

    public int setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, V v2, int i2, int i3, int i4) {
        int iClamp;
        int topAndBottomOffset = getTopAndBottomOffset();
        if (i3 == 0 || topAndBottomOffset < i3 || topAndBottomOffset > i4 || topAndBottomOffset == (iClamp = MathUtils.clamp(i2, i3, i4))) {
            return 0;
        }
        setTopAndBottomOffset(iClamp);
        return topAndBottomOffset - iClamp;
    }
}
