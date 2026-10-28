package androidx.recyclerview.widget;

import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
class ChildHelper {
    private static final boolean DEBUG = false;
    private static final String TAG = "ChildrenHelper";
    final Callback mCallback;
    final Bucket mBucket = new Bucket();
    final List<View> mHiddenViews = new ArrayList();

    public static class Bucket {
        static final int BITS_PER_WORD = 64;
        static final long LAST_BIT = Long.MIN_VALUE;
        long mData = 0;
        Bucket mNext;

        private void ensureNext() {
            if (this.mNext == null) {
                this.mNext = new Bucket();
            }
        }

        public void clear(int i2) {
            if (i2 < 64) {
                this.mData &= ~(1 << i2);
                return;
            }
            Bucket bucket = this.mNext;
            if (bucket != null) {
                bucket.clear(i2 - 64);
            }
        }

        public int countOnesBefore(int i2) {
            long j2;
            Bucket bucket = this.mNext;
            if (bucket == null) {
                if (i2 >= 64) {
                    j2 = this.mData;
                }
                return Long.bitCount(j2);
            }
            if (i2 >= 64) {
                return Long.bitCount(this.mData) + bucket.countOnesBefore(i2 - 64);
            }
            j2 = this.mData & ((1 << i2) - 1);
            return Long.bitCount(j2);
        }

        public boolean get(int i2) {
            if (i2 < 64) {
                return (this.mData & (1 << i2)) != 0;
            }
            ensureNext();
            return this.mNext.get(i2 - 64);
        }

        public void insert(int i2, boolean z2) {
            if (i2 >= 64) {
                ensureNext();
                this.mNext.insert(i2 - 64, z2);
                return;
            }
            long j2 = this.mData;
            boolean z3 = (LAST_BIT & j2) != 0;
            long j3 = (1 << i2) - 1;
            this.mData = ((j2 & (~j3)) << 1) | (j2 & j3);
            if (z2) {
                set(i2);
            } else {
                clear(i2);
            }
            if (z3 || this.mNext != null) {
                ensureNext();
                this.mNext.insert(0, z3);
            }
        }

        public boolean remove(int i2) {
            if (i2 >= 64) {
                ensureNext();
                return this.mNext.remove(i2 - 64);
            }
            long j2 = 1 << i2;
            long j3 = this.mData;
            boolean z2 = (j3 & j2) != 0;
            long j4 = j3 & (~j2);
            this.mData = j4;
            long j5 = j2 - 1;
            this.mData = (j4 & j5) | Long.rotateRight((~j5) & j4, 1);
            Bucket bucket = this.mNext;
            if (bucket != null) {
                if (bucket.get(0)) {
                    set(63);
                }
                this.mNext.remove(0);
            }
            return z2;
        }

        public void reset() {
            this.mData = 0L;
            Bucket bucket = this.mNext;
            if (bucket != null) {
                bucket.reset();
            }
        }

        public void set(int i2) {
            if (i2 < 64) {
                this.mData |= 1 << i2;
            } else {
                ensureNext();
                this.mNext.set(i2 - 64);
            }
        }

        public String toString() {
            if (this.mNext == null) {
                return Long.toBinaryString(this.mData);
            }
            return this.mNext.toString() + "xx" + Long.toBinaryString(this.mData);
        }
    }

    public interface Callback {
        void addView(View view, int i2);

        void attachViewToParent(View view, int i2, ViewGroup.LayoutParams layoutParams);

        void detachViewFromParent(int i2);

        View getChildAt(int i2);

        int getChildCount();

        RecyclerView.ViewHolder getChildViewHolder(View view);

        int indexOfChild(View view);

        void onEnteredHiddenState(View view);

        void onLeftHiddenState(View view);

        void removeAllViews();

        void removeViewAt(int i2);
    }

    public ChildHelper(Callback callback) {
        this.mCallback = callback;
    }

    private int getOffset(int i2) {
        if (i2 < 0) {
            return -1;
        }
        int childCount = this.mCallback.getChildCount();
        int i3 = i2;
        while (i3 < childCount) {
            int iCountOnesBefore = i2 - (i3 - this.mBucket.countOnesBefore(i3));
            if (iCountOnesBefore == 0) {
                while (this.mBucket.get(i3)) {
                    i3++;
                }
                return i3;
            }
            i3 += iCountOnesBefore;
        }
        return -1;
    }

    private void hideViewInternal(View view) {
        this.mHiddenViews.add(view);
        this.mCallback.onEnteredHiddenState(view);
    }

    private boolean unhideViewInternal(View view) {
        if (!this.mHiddenViews.remove(view)) {
            return false;
        }
        this.mCallback.onLeftHiddenState(view);
        return true;
    }

    public void addView(View view, int i2, boolean z2) {
        int childCount = i2 < 0 ? this.mCallback.getChildCount() : getOffset(i2);
        this.mBucket.insert(childCount, z2);
        if (z2) {
            hideViewInternal(view);
        }
        this.mCallback.addView(view, childCount);
    }

    public void attachViewToParent(View view, int i2, ViewGroup.LayoutParams layoutParams, boolean z2) {
        int childCount = i2 < 0 ? this.mCallback.getChildCount() : getOffset(i2);
        this.mBucket.insert(childCount, z2);
        if (z2) {
            hideViewInternal(view);
        }
        this.mCallback.attachViewToParent(view, childCount, layoutParams);
    }

    public void detachViewFromParent(int i2) {
        int offset = getOffset(i2);
        this.mBucket.remove(offset);
        this.mCallback.detachViewFromParent(offset);
    }

    public View findHiddenNonRemovedView(int i2) {
        int size = this.mHiddenViews.size();
        for (int i3 = 0; i3 < size; i3++) {
            View view = this.mHiddenViews.get(i3);
            RecyclerView.ViewHolder childViewHolder = this.mCallback.getChildViewHolder(view);
            if (childViewHolder.getLayoutPosition() == i2 && !childViewHolder.isInvalid() && !childViewHolder.isRemoved()) {
                return view;
            }
        }
        return null;
    }

    public View getChildAt(int i2) {
        return this.mCallback.getChildAt(getOffset(i2));
    }

    public int getChildCount() {
        return this.mCallback.getChildCount() - this.mHiddenViews.size();
    }

    public View getUnfilteredChildAt(int i2) {
        return this.mCallback.getChildAt(i2);
    }

    public int getUnfilteredChildCount() {
        return this.mCallback.getChildCount();
    }

    public void hide(View view) {
        int iIndexOfChild = this.mCallback.indexOfChild(view);
        if (iIndexOfChild >= 0) {
            this.mBucket.set(iIndexOfChild);
            hideViewInternal(view);
        } else {
            throw new IllegalArgumentException("view is not a child, cannot hide " + view);
        }
    }

    public int indexOfChild(View view) {
        int iIndexOfChild = this.mCallback.indexOfChild(view);
        if (iIndexOfChild == -1 || this.mBucket.get(iIndexOfChild)) {
            return -1;
        }
        return iIndexOfChild - this.mBucket.countOnesBefore(iIndexOfChild);
    }

    public boolean isHidden(View view) {
        return this.mHiddenViews.contains(view);
    }

    public void removeAllViewsUnfiltered() {
        this.mBucket.reset();
        for (int size = this.mHiddenViews.size() - 1; size >= 0; size--) {
            this.mCallback.onLeftHiddenState(this.mHiddenViews.get(size));
            this.mHiddenViews.remove(size);
        }
        this.mCallback.removeAllViews();
    }

    public void removeView(View view) {
        int iIndexOfChild = this.mCallback.indexOfChild(view);
        if (iIndexOfChild < 0) {
            return;
        }
        if (this.mBucket.remove(iIndexOfChild)) {
            unhideViewInternal(view);
        }
        this.mCallback.removeViewAt(iIndexOfChild);
    }

    public void removeViewAt(int i2) {
        int offset = getOffset(i2);
        View childAt = this.mCallback.getChildAt(offset);
        if (childAt == null) {
            return;
        }
        if (this.mBucket.remove(offset)) {
            unhideViewInternal(childAt);
        }
        this.mCallback.removeViewAt(offset);
    }

    public boolean removeViewIfHidden(View view) {
        int iIndexOfChild = this.mCallback.indexOfChild(view);
        if (iIndexOfChild == -1) {
            unhideViewInternal(view);
            return true;
        }
        if (!this.mBucket.get(iIndexOfChild)) {
            return false;
        }
        this.mBucket.remove(iIndexOfChild);
        unhideViewInternal(view);
        this.mCallback.removeViewAt(iIndexOfChild);
        return true;
    }

    public String toString() {
        return this.mBucket.toString() + ", hidden list:" + this.mHiddenViews.size();
    }

    public void unhide(View view) {
        int iIndexOfChild = this.mCallback.indexOfChild(view);
        if (iIndexOfChild < 0) {
            throw new IllegalArgumentException("view is not a child, cannot hide " + view);
        }
        if (this.mBucket.get(iIndexOfChild)) {
            this.mBucket.clear(iIndexOfChild);
            unhideViewInternal(view);
        } else {
            throw new RuntimeException("trying to unhide a view that was not hidden" + view);
        }
    }

    public void addView(View view, boolean z2) {
        addView(view, -1, z2);
    }
}
