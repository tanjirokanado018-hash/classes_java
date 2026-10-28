package androidx.recyclerview.widget;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.material.transformation.FabTransformationScrimBehavior;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class DiffUtil {
    private static final Comparator<Snake> SNAKE_COMPARATOR = new Comparator<Snake>() { // from class: androidx.recyclerview.widget.DiffUtil.1
        @Override // java.util.Comparator
        public int compare(Snake snake, Snake snake2) {
            int i2 = snake.f76x - snake2.f76x;
            return i2 == 0 ? snake.f77y - snake2.f77y : i2;
        }
    };

    public static abstract class Callback {
        public abstract boolean areContentsTheSame(int i2, int i3);

        public abstract boolean areItemsTheSame(int i2, int i3);

        @Nullable
        public Object getChangePayload(int i2, int i3) {
            return null;
        }

        public abstract int getNewListSize();

        public abstract int getOldListSize();
    }

    public static class DiffResult {
        private static final int FLAG_CHANGED = 2;
        private static final int FLAG_IGNORE = 16;
        private static final int FLAG_MASK = 31;
        private static final int FLAG_MOVED_CHANGED = 4;
        private static final int FLAG_MOVED_NOT_CHANGED = 8;
        private static final int FLAG_NOT_CHANGED = 1;
        private static final int FLAG_OFFSET = 5;
        public static final int NO_POSITION = -1;
        private final Callback mCallback;
        private final boolean mDetectMoves;
        private final int[] mNewItemStatuses;
        private final int mNewListSize;
        private final int[] mOldItemStatuses;
        private final int mOldListSize;
        private final List<Snake> mSnakes;

        public DiffResult(Callback callback, List<Snake> list, int[] iArr, int[] iArr2, boolean z2) {
            this.mSnakes = list;
            this.mOldItemStatuses = iArr;
            this.mNewItemStatuses = iArr2;
            Arrays.fill(iArr, 0);
            Arrays.fill(iArr2, 0);
            this.mCallback = callback;
            this.mOldListSize = callback.getOldListSize();
            this.mNewListSize = callback.getNewListSize();
            this.mDetectMoves = z2;
            addRootSnake();
            findMatchingItems();
        }

        private void addRootSnake() {
            Snake snake = this.mSnakes.isEmpty() ? null : this.mSnakes.get(0);
            if (snake != null && snake.f76x == 0 && snake.f77y == 0) {
                return;
            }
            Snake snake2 = new Snake();
            snake2.f76x = 0;
            snake2.f77y = 0;
            snake2.removal = false;
            snake2.size = 0;
            snake2.reverse = false;
            this.mSnakes.add(0, snake2);
        }

        private void dispatchAdditions(List<PostponedUpdate> list, ListUpdateCallback listUpdateCallback, int i2, int i3, int i4) {
            if (!this.mDetectMoves) {
                listUpdateCallback.onInserted(i2, i3);
                return;
            }
            for (int i5 = i3 - 1; i5 >= 0; i5--) {
                int i6 = i4 + i5;
                int i7 = this.mNewItemStatuses[i6];
                int i8 = i7 & 31;
                if (i8 == 0) {
                    listUpdateCallback.onInserted(i2, 1);
                    Iterator<PostponedUpdate> it = list.iterator();
                    while (it.hasNext()) {
                        it.next().currentPos++;
                    }
                } else if (i8 == 4 || i8 == 8) {
                    int i9 = i7 >> 5;
                    listUpdateCallback.onMoved(removePostponedUpdate(list, i9, true).currentPos, i2);
                    if (i8 == 4) {
                        listUpdateCallback.onChanged(i2, 1, this.mCallback.getChangePayload(i9, i6));
                    }
                } else {
                    if (i8 != 16) {
                        throw new IllegalStateException("unknown flag for pos " + i6 + " " + Long.toBinaryString(i8));
                    }
                    list.add(new PostponedUpdate(i6, i2, false));
                }
            }
        }

        private void dispatchRemovals(List<PostponedUpdate> list, ListUpdateCallback listUpdateCallback, int i2, int i3, int i4) {
            if (!this.mDetectMoves) {
                listUpdateCallback.onRemoved(i2, i3);
                return;
            }
            for (int i5 = i3 - 1; i5 >= 0; i5--) {
                int i6 = i4 + i5;
                int i7 = this.mOldItemStatuses[i6];
                int i8 = i7 & 31;
                if (i8 == 0) {
                    listUpdateCallback.onRemoved(i2 + i5, 1);
                    Iterator<PostponedUpdate> it = list.iterator();
                    while (it.hasNext()) {
                        it.next().currentPos--;
                    }
                } else if (i8 == 4 || i8 == 8) {
                    int i9 = i7 >> 5;
                    PostponedUpdate postponedUpdateRemovePostponedUpdate = removePostponedUpdate(list, i9, false);
                    listUpdateCallback.onMoved(i2 + i5, postponedUpdateRemovePostponedUpdate.currentPos - 1);
                    if (i8 == 4) {
                        listUpdateCallback.onChanged(postponedUpdateRemovePostponedUpdate.currentPos - 1, 1, this.mCallback.getChangePayload(i6, i9));
                    }
                } else {
                    if (i8 != 16) {
                        throw new IllegalStateException("unknown flag for pos " + i6 + " " + Long.toBinaryString(i8));
                    }
                    list.add(new PostponedUpdate(i6, i2 + i5, true));
                }
            }
        }

        private void findAddition(int i2, int i3, int i4) {
            if (this.mOldItemStatuses[i2 - 1] != 0) {
                return;
            }
            findMatchingItem(i2, i3, i4, false);
        }

        private boolean findMatchingItem(int i2, int i3, int i4, boolean z2) {
            int i5;
            int i6;
            int i7;
            if (z2) {
                i3--;
                i6 = i2;
                i5 = i3;
            } else {
                i5 = i2 - 1;
                i6 = i5;
            }
            while (i4 >= 0) {
                Snake snake = this.mSnakes.get(i4);
                int i8 = snake.f76x;
                int i9 = snake.size;
                int i10 = i8 + i9;
                int i11 = snake.f77y + i9;
                if (z2) {
                    for (int i12 = i6 - 1; i12 >= i10; i12--) {
                        if (this.mCallback.areItemsTheSame(i12, i5)) {
                            i7 = this.mCallback.areContentsTheSame(i12, i5) ? 8 : 4;
                            this.mNewItemStatuses[i5] = (i12 << 5) | 16;
                            this.mOldItemStatuses[i12] = (i5 << 5) | i7;
                            return true;
                        }
                    }
                } else {
                    for (int i13 = i3 - 1; i13 >= i11; i13--) {
                        if (this.mCallback.areItemsTheSame(i5, i13)) {
                            i7 = this.mCallback.areContentsTheSame(i5, i13) ? 8 : 4;
                            int i14 = i2 - 1;
                            this.mOldItemStatuses[i14] = (i13 << 5) | 16;
                            this.mNewItemStatuses[i13] = (i14 << 5) | i7;
                            return true;
                        }
                    }
                }
                i6 = snake.f76x;
                i3 = snake.f77y;
                i4--;
            }
            return false;
        }

        private void findMatchingItems() {
            int i2 = this.mOldListSize;
            int i3 = this.mNewListSize;
            for (int size = this.mSnakes.size() - 1; size >= 0; size--) {
                Snake snake = this.mSnakes.get(size);
                int i4 = snake.f76x;
                int i5 = snake.size;
                int i6 = i4 + i5;
                int i7 = snake.f77y + i5;
                if (this.mDetectMoves) {
                    while (i2 > i6) {
                        findAddition(i2, i3, size);
                        i2--;
                    }
                    while (i3 > i7) {
                        findRemoval(i2, i3, size);
                        i3--;
                    }
                }
                for (int i8 = 0; i8 < snake.size; i8++) {
                    int i9 = snake.f76x + i8;
                    int i10 = snake.f77y + i8;
                    int i11 = this.mCallback.areContentsTheSame(i9, i10) ? 1 : 2;
                    this.mOldItemStatuses[i9] = (i10 << 5) | i11;
                    this.mNewItemStatuses[i10] = (i9 << 5) | i11;
                }
                i2 = snake.f76x;
                i3 = snake.f77y;
            }
        }

        private void findRemoval(int i2, int i3, int i4) {
            if (this.mNewItemStatuses[i3 - 1] != 0) {
                return;
            }
            findMatchingItem(i2, i3, i4, true);
        }

        private static PostponedUpdate removePostponedUpdate(List<PostponedUpdate> list, int i2, boolean z2) {
            int size = list.size() - 1;
            while (size >= 0) {
                PostponedUpdate postponedUpdate = list.get(size);
                if (postponedUpdate.posInOwnerList == i2 && postponedUpdate.removal == z2) {
                    list.remove(size);
                    while (size < list.size()) {
                        list.get(size).currentPos += z2 ? 1 : -1;
                        size++;
                    }
                    return postponedUpdate;
                }
                size--;
            }
            return null;
        }

        public int convertNewPositionToOld(@IntRange(from = FabTransformationScrimBehavior.COLLAPSE_DELAY) int i2) {
            if (i2 >= 0 && i2 < this.mNewListSize) {
                int i3 = this.mNewItemStatuses[i2];
                if ((i3 & 31) == 0) {
                    return -1;
                }
                return i3 >> 5;
            }
            throw new IndexOutOfBoundsException("Index out of bounds - passed position = " + i2 + ", new list size = " + this.mNewListSize);
        }

        public int convertOldPositionToNew(@IntRange(from = FabTransformationScrimBehavior.COLLAPSE_DELAY) int i2) {
            if (i2 >= 0 && i2 < this.mOldListSize) {
                int i3 = this.mOldItemStatuses[i2];
                if ((i3 & 31) == 0) {
                    return -1;
                }
                return i3 >> 5;
            }
            throw new IndexOutOfBoundsException("Index out of bounds - passed position = " + i2 + ", old list size = " + this.mOldListSize);
        }

        public void dispatchUpdatesTo(@NonNull ListUpdateCallback listUpdateCallback) {
            BatchingListUpdateCallback batchingListUpdateCallback = listUpdateCallback instanceof BatchingListUpdateCallback ? (BatchingListUpdateCallback) listUpdateCallback : new BatchingListUpdateCallback(listUpdateCallback);
            List<PostponedUpdate> arrayList = new ArrayList<>();
            int i2 = this.mOldListSize;
            int i3 = this.mNewListSize;
            for (int size = this.mSnakes.size() - 1; size >= 0; size--) {
                Snake snake = this.mSnakes.get(size);
                int i4 = snake.size;
                int i5 = snake.f76x + i4;
                int i6 = snake.f77y + i4;
                if (i5 < i2) {
                    dispatchRemovals(arrayList, batchingListUpdateCallback, i5, i2 - i5, i5);
                }
                if (i6 < i3) {
                    dispatchAdditions(arrayList, batchingListUpdateCallback, i5, i3 - i6, i6);
                }
                for (int i7 = i4 - 1; i7 >= 0; i7--) {
                    int[] iArr = this.mOldItemStatuses;
                    int i8 = snake.f76x;
                    if ((iArr[i8 + i7] & 31) == 2) {
                        batchingListUpdateCallback.onChanged(i8 + i7, 1, this.mCallback.getChangePayload(i8 + i7, snake.f77y + i7));
                    }
                }
                i2 = snake.f76x;
                i3 = snake.f77y;
            }
            batchingListUpdateCallback.dispatchLastEvent();
        }

        @VisibleForTesting
        public List<Snake> getSnakes() {
            return this.mSnakes;
        }

        public void dispatchUpdatesTo(@NonNull RecyclerView.Adapter adapter) {
            dispatchUpdatesTo(new AdapterListUpdateCallback(adapter));
        }
    }

    public static abstract class ItemCallback<T> {
        public abstract boolean areContentsTheSame(@NonNull T t2, @NonNull T t3);

        public abstract boolean areItemsTheSame(@NonNull T t2, @NonNull T t3);

        @Nullable
        public Object getChangePayload(@NonNull T t2, @NonNull T t3) {
            return null;
        }
    }

    public static class PostponedUpdate {
        int currentPos;
        int posInOwnerList;
        boolean removal;

        public PostponedUpdate(int i2, int i3, boolean z2) {
            this.posInOwnerList = i2;
            this.currentPos = i3;
            this.removal = z2;
        }
    }

    public static class Range {
        int newListEnd;
        int newListStart;
        int oldListEnd;
        int oldListStart;

        public Range() {
        }

        public Range(int i2, int i3, int i4, int i5) {
            this.oldListStart = i2;
            this.oldListEnd = i3;
            this.newListStart = i4;
            this.newListEnd = i5;
        }
    }

    public static class Snake {
        boolean removal;
        boolean reverse;
        int size;

        /* JADX INFO: renamed from: x, reason: collision with root package name */
        int f76x;

        /* JADX INFO: renamed from: y, reason: collision with root package name */
        int f77y;
    }

    private DiffUtil() {
    }

    @NonNull
    public static DiffResult calculateDiff(@NonNull Callback callback) {
        return calculateDiff(callback, true);
    }

    /* JADX WARN: Code duplicated, block: B:20:0x004d  */
    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not found exit edge by exit block: B:52:0x00ca
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.checkLoopExits(LoopRegionMaker.java:272)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeLoopRegion(LoopRegionMaker.java:237)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:80)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:111)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:162)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:162)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:117)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:49)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    private static androidx.recyclerview.widget.DiffUtil.Snake diffPartial(androidx.recyclerview.widget.DiffUtil.Callback r19, int r20, int r21, int r22, int r23, int[] r24, int[] r25, int r26) {
        /*
            Method dump skipped, instruction units count: 299
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.DiffUtil.diffPartial(androidx.recyclerview.widget.DiffUtil$Callback, int, int, int, int, int[], int[], int):androidx.recyclerview.widget.DiffUtil$Snake");
    }

    /* JADX WARN: Code duplicated, block: B:25:0x00ae  */
    /* JADX WARN: Code duplicated, block: B:27:0x00b2  */
    /* JADX WARN: Code duplicated, block: B:30:0x00c1  */
    /* JADX WARN: Code duplicated, block: B:31:0x00ce  */
    @NonNull
    public static DiffResult calculateDiff(@NonNull Callback callback, boolean z2) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int oldListSize = callback.getOldListSize();
        int newListSize = callback.getNewListSize();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new Range(0, oldListSize, 0, newListSize));
        int iAbs = Math.abs(oldListSize - newListSize) + oldListSize + newListSize;
        int i7 = iAbs * 2;
        int[] iArr = new int[i7];
        int[] iArr2 = new int[i7];
        ArrayList arrayList3 = new ArrayList();
        while (!arrayList2.isEmpty()) {
            Range range = (Range) arrayList2.remove(arrayList2.size() - 1);
            Snake snakeDiffPartial = diffPartial(callback, range.oldListStart, range.oldListEnd, range.newListStart, range.newListEnd, iArr, iArr2, iAbs);
            if (snakeDiffPartial != null) {
                if (snakeDiffPartial.size > 0) {
                    arrayList.add(snakeDiffPartial);
                }
                snakeDiffPartial.f76x += range.oldListStart;
                snakeDiffPartial.f77y += range.newListStart;
                Range range2 = arrayList3.isEmpty() ? new Range() : (Range) arrayList3.remove(arrayList3.size() - 1);
                range2.oldListStart = range.oldListStart;
                range2.newListStart = range.newListStart;
                if (snakeDiffPartial.reverse) {
                    i3 = snakeDiffPartial.f76x;
                } else {
                    if (snakeDiffPartial.removal) {
                        i3 = snakeDiffPartial.f76x - 1;
                    } else {
                        range2.oldListEnd = snakeDiffPartial.f76x;
                        i2 = snakeDiffPartial.f77y - 1;
                    }
                    range2.newListEnd = i2;
                    arrayList2.add(range2);
                    if (snakeDiffPartial.reverse) {
                        if (snakeDiffPartial.removal) {
                            int i8 = snakeDiffPartial.f76x;
                            i4 = snakeDiffPartial.size;
                            i5 = i8 + i4 + 1;
                        } else {
                            int i9 = snakeDiffPartial.f76x;
                            int i10 = snakeDiffPartial.size;
                            range.oldListStart = i9 + i10;
                            i6 = snakeDiffPartial.f77y + i10 + 1;
                        }
                        range.newListStart = i6;
                        arrayList2.add(range);
                    } else {
                        int i11 = snakeDiffPartial.f76x;
                        i4 = snakeDiffPartial.size;
                        i5 = i11 + i4;
                    }
                    range.oldListStart = i5;
                    i6 = snakeDiffPartial.f77y + i4;
                    range.newListStart = i6;
                    arrayList2.add(range);
                }
                range2.oldListEnd = i3;
                i2 = snakeDiffPartial.f77y;
                range2.newListEnd = i2;
                arrayList2.add(range2);
                if (snakeDiffPartial.reverse) {
                    if (snakeDiffPartial.removal) {
                        int i12 = snakeDiffPartial.f76x;
                        i4 = snakeDiffPartial.size;
                        i5 = i12 + i4 + 1;
                    } else {
                        int i13 = snakeDiffPartial.f76x;
                        int i14 = snakeDiffPartial.size;
                        range.oldListStart = i13 + i14;
                        i6 = snakeDiffPartial.f77y + i14 + 1;
                    }
                    range.newListStart = i6;
                    arrayList2.add(range);
                } else {
                    int i15 = snakeDiffPartial.f76x;
                    i4 = snakeDiffPartial.size;
                    i5 = i15 + i4;
                }
                range.oldListStart = i5;
                i6 = snakeDiffPartial.f77y + i4;
                range.newListStart = i6;
                arrayList2.add(range);
            } else {
                arrayList3.add(range);
            }
        }
        Collections.sort(arrayList, SNAKE_COMPARATOR);
        return new DiffResult(callback, arrayList, iArr, iArr2, z2);
    }
}
