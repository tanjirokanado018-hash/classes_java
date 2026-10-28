package androidx.recyclerview.widget;

import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.annotation.WorkerThread;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class AsyncListUtil<T> {
    static final boolean DEBUG = false;
    static final String TAG = "AsyncListUtil";
    boolean mAllowScrollHints;
    private final ThreadUtil.BackgroundCallback<T> mBackgroundCallback;
    final ThreadUtil.BackgroundCallback<T> mBackgroundProxy;
    final DataCallback<T> mDataCallback;
    private final ThreadUtil.MainThreadCallback<T> mMainThreadCallback;
    final ThreadUtil.MainThreadCallback<T> mMainThreadProxy;
    final Class<T> mTClass;
    final TileList<T> mTileList;
    final int mTileSize;
    final ViewCallback mViewCallback;
    final int[] mTmpRange = new int[2];
    final int[] mPrevRange = new int[2];
    final int[] mTmpRangeExtended = new int[2];
    private int mScrollHint = 0;
    int mItemCount = 0;
    int mDisplayedGeneration = 0;
    int mRequestedGeneration = 0;
    final SparseIntArray mMissingPositions = new SparseIntArray();

    public static abstract class DataCallback<T> {
        @WorkerThread
        public abstract void fillData(@NonNull T[] tArr, int i2, int i3);

        @WorkerThread
        public int getMaxCachedTiles() {
            return 10;
        }

        @WorkerThread
        public void recycleData(@NonNull T[] tArr, int i2) {
        }

        @WorkerThread
        public abstract int refreshData();
    }

    public static abstract class ViewCallback {
        public static final int HINT_SCROLL_ASC = 2;
        public static final int HINT_SCROLL_DESC = 1;
        public static final int HINT_SCROLL_NONE = 0;

        @UiThread
        public void extendRangeInto(@NonNull int[] iArr, @NonNull int[] iArr2, int i2) {
            int i3 = iArr[1];
            int i4 = iArr[0];
            int i5 = (i3 - i4) + 1;
            int i6 = i5 / 2;
            iArr2[0] = i4 - (i2 == 1 ? i5 : i6);
            if (i2 != 2) {
                i5 = i6;
            }
            iArr2[1] = i3 + i5;
        }

        @UiThread
        public abstract void getItemRangeInto(@NonNull int[] iArr);

        @UiThread
        public abstract void onDataRefresh();

        @UiThread
        public abstract void onItemLoaded(int i2);
    }

    public AsyncListUtil(@NonNull Class<T> cls, int i2, @NonNull DataCallback<T> dataCallback, @NonNull ViewCallback viewCallback) {
        ThreadUtil.MainThreadCallback<T> mainThreadCallback = new ThreadUtil.MainThreadCallback<T>() { // from class: androidx.recyclerview.widget.AsyncListUtil.1
            private boolean isRequestedGeneration(int i3) {
                return i3 == AsyncListUtil.this.mRequestedGeneration;
            }

            private void recycleAllTiles() {
                for (int i3 = 0; i3 < AsyncListUtil.this.mTileList.size(); i3++) {
                    AsyncListUtil asyncListUtil = AsyncListUtil.this;
                    asyncListUtil.mBackgroundProxy.recycleTile(asyncListUtil.mTileList.getAtIndex(i3));
                }
                AsyncListUtil.this.mTileList.clear();
            }

            @Override // androidx.recyclerview.widget.ThreadUtil.MainThreadCallback
            public void addTile(int i3, TileList.Tile<T> tile) {
                if (!isRequestedGeneration(i3)) {
                    AsyncListUtil.this.mBackgroundProxy.recycleTile(tile);
                    return;
                }
                TileList.Tile<T> tileAddOrReplace = AsyncListUtil.this.mTileList.addOrReplace(tile);
                if (tileAddOrReplace != null) {
                    AsyncListUtil.this.mBackgroundProxy.recycleTile(tileAddOrReplace);
                }
                int i4 = tile.mStartPosition + tile.mItemCount;
                int i5 = 0;
                while (i5 < AsyncListUtil.this.mMissingPositions.size()) {
                    int iKeyAt = AsyncListUtil.this.mMissingPositions.keyAt(i5);
                    if (tile.mStartPosition > iKeyAt || iKeyAt >= i4) {
                        i5++;
                    } else {
                        AsyncListUtil.this.mMissingPositions.removeAt(i5);
                        AsyncListUtil.this.mViewCallback.onItemLoaded(iKeyAt);
                    }
                }
            }

            @Override // androidx.recyclerview.widget.ThreadUtil.MainThreadCallback
            public void removeTile(int i3, int i4) {
                TileList.Tile<T> tileRemoveAtPos;
                if (isRequestedGeneration(i3) && (tileRemoveAtPos = AsyncListUtil.this.mTileList.removeAtPos(i4)) != null) {
                    AsyncListUtil.this.mBackgroundProxy.recycleTile(tileRemoveAtPos);
                }
            }

            @Override // androidx.recyclerview.widget.ThreadUtil.MainThreadCallback
            public void updateItemCount(int i3, int i4) {
                if (isRequestedGeneration(i3)) {
                    AsyncListUtil asyncListUtil = AsyncListUtil.this;
                    asyncListUtil.mItemCount = i4;
                    asyncListUtil.mViewCallback.onDataRefresh();
                    AsyncListUtil asyncListUtil2 = AsyncListUtil.this;
                    asyncListUtil2.mDisplayedGeneration = asyncListUtil2.mRequestedGeneration;
                    recycleAllTiles();
                    AsyncListUtil asyncListUtil3 = AsyncListUtil.this;
                    asyncListUtil3.mAllowScrollHints = false;
                    asyncListUtil3.updateRange();
                }
            }
        };
        this.mMainThreadCallback = mainThreadCallback;
        ThreadUtil.BackgroundCallback<T> backgroundCallback = new ThreadUtil.BackgroundCallback<T>() { // from class: androidx.recyclerview.widget.AsyncListUtil.2
            private int mFirstRequiredTileStart;
            private int mGeneration;
            private int mItemCount;
            private int mLastRequiredTileStart;
            final SparseBooleanArray mLoadedTiles = new SparseBooleanArray();
            private TileList.Tile<T> mRecycledRoot;

            private TileList.Tile<T> acquireTile() {
                TileList.Tile<T> tile = this.mRecycledRoot;
                if (tile != null) {
                    this.mRecycledRoot = tile.mNext;
                    return tile;
                }
                AsyncListUtil asyncListUtil = AsyncListUtil.this;
                return new TileList.Tile<>(asyncListUtil.mTClass, asyncListUtil.mTileSize);
            }

            private void addTile(TileList.Tile<T> tile) {
                this.mLoadedTiles.put(tile.mStartPosition, true);
                AsyncListUtil.this.mMainThreadProxy.addTile(this.mGeneration, tile);
            }

            private void flushTileCache(int i3) {
                int maxCachedTiles = AsyncListUtil.this.mDataCallback.getMaxCachedTiles();
                while (this.mLoadedTiles.size() >= maxCachedTiles) {
                    int iKeyAt = this.mLoadedTiles.keyAt(0);
                    SparseBooleanArray sparseBooleanArray = this.mLoadedTiles;
                    int iKeyAt2 = sparseBooleanArray.keyAt(sparseBooleanArray.size() - 1);
                    int i4 = this.mFirstRequiredTileStart - iKeyAt;
                    int i5 = iKeyAt2 - this.mLastRequiredTileStart;
                    if (i4 > 0 && (i4 >= i5 || i3 == 2)) {
                        removeTile(iKeyAt);
                    } else {
                        if (i5 <= 0) {
                            return;
                        }
                        if (i4 >= i5 && i3 != 1) {
                            return;
                        } else {
                            removeTile(iKeyAt2);
                        }
                    }
                }
            }

            private int getTileStart(int i3) {
                return i3 - (i3 % AsyncListUtil.this.mTileSize);
            }

            private boolean isTileLoaded(int i3) {
                return this.mLoadedTiles.get(i3);
            }

            private void log(String str, Object... objArr) {
                String.format(str, objArr);
            }

            private void removeTile(int i3) {
                this.mLoadedTiles.delete(i3);
                AsyncListUtil.this.mMainThreadProxy.removeTile(this.mGeneration, i3);
            }

            private void requestTiles(int i3, int i4, int i5, boolean z2) {
                int i6 = i3;
                while (i6 <= i4) {
                    AsyncListUtil.this.mBackgroundProxy.loadTile(z2 ? (i4 + i3) - i6 : i6, i5);
                    i6 += AsyncListUtil.this.mTileSize;
                }
            }

            @Override // androidx.recyclerview.widget.ThreadUtil.BackgroundCallback
            public void loadTile(int i3, int i4) {
                if (isTileLoaded(i3)) {
                    return;
                }
                TileList.Tile<T> tileAcquireTile = acquireTile();
                tileAcquireTile.mStartPosition = i3;
                int iMin = Math.min(AsyncListUtil.this.mTileSize, this.mItemCount - i3);
                tileAcquireTile.mItemCount = iMin;
                AsyncListUtil.this.mDataCallback.fillData(tileAcquireTile.mItems, tileAcquireTile.mStartPosition, iMin);
                flushTileCache(i4);
                addTile(tileAcquireTile);
            }

            @Override // androidx.recyclerview.widget.ThreadUtil.BackgroundCallback
            public void recycleTile(TileList.Tile<T> tile) {
                AsyncListUtil.this.mDataCallback.recycleData(tile.mItems, tile.mItemCount);
                tile.mNext = this.mRecycledRoot;
                this.mRecycledRoot = tile;
            }

            @Override // androidx.recyclerview.widget.ThreadUtil.BackgroundCallback
            public void refresh(int i3) {
                this.mGeneration = i3;
                this.mLoadedTiles.clear();
                int iRefreshData = AsyncListUtil.this.mDataCallback.refreshData();
                this.mItemCount = iRefreshData;
                AsyncListUtil.this.mMainThreadProxy.updateItemCount(this.mGeneration, iRefreshData);
            }

            @Override // androidx.recyclerview.widget.ThreadUtil.BackgroundCallback
            public void updateRange(int i3, int i4, int i5, int i6, int i7) {
                if (i3 > i4) {
                    return;
                }
                int tileStart = getTileStart(i3);
                int tileStart2 = getTileStart(i4);
                this.mFirstRequiredTileStart = getTileStart(i5);
                int tileStart3 = getTileStart(i6);
                this.mLastRequiredTileStart = tileStart3;
                if (i7 == 1) {
                    requestTiles(this.mFirstRequiredTileStart, tileStart2, i7, true);
                    requestTiles(tileStart2 + AsyncListUtil.this.mTileSize, this.mLastRequiredTileStart, i7, false);
                } else {
                    requestTiles(tileStart, tileStart3, i7, false);
                    requestTiles(this.mFirstRequiredTileStart, tileStart - AsyncListUtil.this.mTileSize, i7, true);
                }
            }
        };
        this.mBackgroundCallback = backgroundCallback;
        this.mTClass = cls;
        this.mTileSize = i2;
        this.mDataCallback = dataCallback;
        this.mViewCallback = viewCallback;
        this.mTileList = new TileList<>(i2);
        MessageThreadUtil messageThreadUtil = new MessageThreadUtil();
        this.mMainThreadProxy = messageThreadUtil.getMainThreadProxy(mainThreadCallback);
        this.mBackgroundProxy = messageThreadUtil.getBackgroundProxy(backgroundCallback);
        refresh();
    }

    private boolean isRefreshPending() {
        return this.mRequestedGeneration != this.mDisplayedGeneration;
    }

    @Nullable
    public T getItem(int i2) {
        if (i2 < 0 || i2 >= this.mItemCount) {
            throw new IndexOutOfBoundsException(i2 + " is not within 0 and " + this.mItemCount);
        }
        T itemAt = this.mTileList.getItemAt(i2);
        if (itemAt == null && !isRefreshPending()) {
            this.mMissingPositions.put(i2, 0);
        }
        return itemAt;
    }

    public int getItemCount() {
        return this.mItemCount;
    }

    public void log(String str, Object... objArr) {
        String.format(str, objArr);
    }

    public void onRangeChanged() {
        if (isRefreshPending()) {
            return;
        }
        updateRange();
        this.mAllowScrollHints = true;
    }

    public void refresh() {
        this.mMissingPositions.clear();
        ThreadUtil.BackgroundCallback<T> backgroundCallback = this.mBackgroundProxy;
        int i2 = this.mRequestedGeneration + 1;
        this.mRequestedGeneration = i2;
        backgroundCallback.refresh(i2);
    }

    /* JADX WARN: Code duplicated, block: B:11:0x001d  */
    public void updateRange() {
        int i2;
        this.mViewCallback.getItemRangeInto(this.mTmpRange);
        int[] iArr = this.mTmpRange;
        int i3 = iArr[0];
        int i4 = iArr[1];
        if (i3 > i4 || i3 < 0 || i4 >= this.mItemCount) {
            return;
        }
        if (this.mAllowScrollHints) {
            int[] iArr2 = this.mPrevRange;
            if (i3 > iArr2[1] || (i2 = iArr2[0]) > i4) {
                this.mScrollHint = 0;
            } else if (i3 < i2) {
                this.mScrollHint = 1;
            } else if (i3 > i2) {
                this.mScrollHint = 2;
            }
        } else {
            this.mScrollHint = 0;
        }
        int[] iArr3 = this.mPrevRange;
        iArr3[0] = i3;
        iArr3[1] = i4;
        this.mViewCallback.extendRangeInto(iArr, this.mTmpRangeExtended, this.mScrollHint);
        int[] iArr4 = this.mTmpRangeExtended;
        iArr4[0] = Math.min(this.mTmpRange[0], Math.max(iArr4[0], 0));
        int[] iArr5 = this.mTmpRangeExtended;
        iArr5[1] = Math.max(this.mTmpRange[1], Math.min(iArr5[1], this.mItemCount - 1));
        ThreadUtil.BackgroundCallback<T> backgroundCallback = this.mBackgroundProxy;
        int[] iArr6 = this.mTmpRange;
        int i5 = iArr6[0];
        int i6 = iArr6[1];
        int[] iArr7 = this.mTmpRangeExtended;
        backgroundCallback.updateRange(i5, i6, iArr7[0], iArr7[1], this.mScrollHint);
    }
}
