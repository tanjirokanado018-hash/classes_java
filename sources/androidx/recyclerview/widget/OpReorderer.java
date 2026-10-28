package androidx.recyclerview.widget;

import java.util.List;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
class OpReorderer {
    final Callback mCallback;

    public interface Callback {
        AdapterHelper.UpdateOp obtainUpdateOp(int i2, int i3, int i4, Object obj);

        void recycleUpdateOp(AdapterHelper.UpdateOp updateOp);
    }

    public OpReorderer(Callback callback) {
        this.mCallback = callback;
    }

    private int getLastMoveOutOfOrder(List<AdapterHelper.UpdateOp> list) {
        boolean z2 = false;
        for (int size = list.size() - 1; size >= 0; size--) {
            if (list.get(size).cmd != 8) {
                z2 = true;
            } else if (z2) {
                return size;
            }
        }
        return -1;
    }

    private void swapMoveAdd(List<AdapterHelper.UpdateOp> list, int i2, AdapterHelper.UpdateOp updateOp, int i3, AdapterHelper.UpdateOp updateOp2) {
        int i4 = updateOp.itemCount;
        int i5 = updateOp2.positionStart;
        int i6 = i4 < i5 ? -1 : 0;
        int i7 = updateOp.positionStart;
        if (i7 < i5) {
            i6++;
        }
        if (i5 <= i7) {
            updateOp.positionStart = i7 + updateOp2.itemCount;
        }
        int i8 = updateOp2.positionStart;
        if (i8 <= i4) {
            updateOp.itemCount = i4 + updateOp2.itemCount;
        }
        updateOp2.positionStart = i8 + i6;
        list.set(i2, updateOp2);
        list.set(i3, updateOp);
    }

    private void swapMoveOp(List<AdapterHelper.UpdateOp> list, int i2, int i3) {
        AdapterHelper.UpdateOp updateOp = list.get(i2);
        AdapterHelper.UpdateOp updateOp2 = list.get(i3);
        int i4 = updateOp2.cmd;
        if (i4 == 1) {
            swapMoveAdd(list, i2, updateOp, i3, updateOp2);
        } else if (i4 == 2) {
            swapMoveRemove(list, i2, updateOp, i3, updateOp2);
        } else {
            if (i4 != 4) {
                return;
            }
            swapMoveUpdate(list, i2, updateOp, i3, updateOp2);
        }
    }

    public void reorderOps(List<AdapterHelper.UpdateOp> list) {
        while (true) {
            int lastMoveOutOfOrder = getLastMoveOutOfOrder(list);
            if (lastMoveOutOfOrder == -1) {
                return;
            } else {
                swapMoveOp(list, lastMoveOutOfOrder, lastMoveOutOfOrder + 1);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:47:0x009f A[PHI: r0
      0x009f: PHI (r0v13 int) = (r0v6 int), (r0v16 int) binds: [B:59:0x00cc, B:46:0x009d] A[DONT_GENERATE, DONT_INLINE]] */
    public void swapMoveRemove(List<AdapterHelper.UpdateOp> list, int i2, AdapterHelper.UpdateOp updateOp, int i3, AdapterHelper.UpdateOp updateOp2) {
        boolean z2;
        int i4;
        int i5 = updateOp.positionStart;
        int i6 = updateOp.itemCount;
        boolean z3 = false;
        int i7 = updateOp2.positionStart;
        if (i5 < i6) {
            if (i7 == i5 && updateOp2.itemCount == i6 - i5) {
                z2 = false;
                z3 = true;
            } else {
                z2 = false;
            }
        } else if (i7 == i6 + 1 && updateOp2.itemCount == i5 - i6) {
            z2 = true;
            z3 = true;
        } else {
            z2 = true;
        }
        int i8 = updateOp2.positionStart;
        if (i6 < i8) {
            updateOp2.positionStart = i8 - 1;
        } else {
            int i9 = updateOp2.itemCount;
            if (i6 < i8 + i9) {
                updateOp2.itemCount = i9 - 1;
                updateOp.cmd = 2;
                updateOp.itemCount = 1;
                if (updateOp2.itemCount == 0) {
                    list.remove(i3);
                    this.mCallback.recycleUpdateOp(updateOp2);
                    return;
                }
                return;
            }
        }
        int i10 = updateOp.positionStart;
        int i11 = updateOp2.positionStart;
        AdapterHelper.UpdateOp updateOpObtainUpdateOp = null;
        if (i10 <= i11) {
            updateOp2.positionStart = i11 + 1;
        } else {
            int i12 = updateOp2.itemCount;
            if (i10 < i11 + i12) {
                updateOpObtainUpdateOp = this.mCallback.obtainUpdateOp(2, i10 + 1, (i11 + i12) - i10, null);
                updateOp2.itemCount = updateOp.positionStart - updateOp2.positionStart;
            }
        }
        if (z3) {
            list.set(i2, updateOp2);
            list.remove(i3);
            this.mCallback.recycleUpdateOp(updateOp);
            return;
        }
        if (z2) {
            if (updateOpObtainUpdateOp != null) {
                int i13 = updateOp.positionStart;
                if (i13 > updateOpObtainUpdateOp.positionStart) {
                    updateOp.positionStart = i13 - updateOpObtainUpdateOp.itemCount;
                }
                int i14 = updateOp.itemCount;
                if (i14 > updateOpObtainUpdateOp.positionStart) {
                    updateOp.itemCount = i14 - updateOpObtainUpdateOp.itemCount;
                }
            }
            int i15 = updateOp.positionStart;
            if (i15 > updateOp2.positionStart) {
                updateOp.positionStart = i15 - updateOp2.itemCount;
            }
            i4 = updateOp.itemCount;
            if (i4 > updateOp2.positionStart) {
                updateOp.itemCount = i4 - updateOp2.itemCount;
            }
        } else {
            if (updateOpObtainUpdateOp != null) {
                int i16 = updateOp.positionStart;
                if (i16 >= updateOpObtainUpdateOp.positionStart) {
                    updateOp.positionStart = i16 - updateOpObtainUpdateOp.itemCount;
                }
                int i17 = updateOp.itemCount;
                if (i17 >= updateOpObtainUpdateOp.positionStart) {
                    updateOp.itemCount = i17 - updateOpObtainUpdateOp.itemCount;
                }
            }
            int i18 = updateOp.positionStart;
            if (i18 >= updateOp2.positionStart) {
                updateOp.positionStart = i18 - updateOp2.itemCount;
            }
            i4 = updateOp.itemCount;
            if (i4 >= updateOp2.positionStart) {
                updateOp.itemCount = i4 - updateOp2.itemCount;
            }
        }
        list.set(i2, updateOp2);
        if (updateOp.positionStart != updateOp.itemCount) {
            list.set(i3, updateOp);
        } else {
            list.remove(i3);
        }
        if (updateOpObtainUpdateOp != null) {
            list.add(i2, updateOpObtainUpdateOp);
        }
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0027  */
    /* JADX WARN: Code duplicated, block: B:12:0x002b  */
    /* JADX WARN: Code duplicated, block: B:14:0x0031  */
    /* JADX WARN: Code duplicated, block: B:17:0x0048  */
    /* JADX WARN: Code duplicated, block: B:18:0x004c  */
    /* JADX WARN: Code duplicated, block: B:20:0x0056  */
    /* JADX WARN: Code duplicated, block: B:22:0x005b  */
    /* JADX WARN: Code duplicated, block: B:24:? A[RETURN, SYNTHETIC] */
    public void swapMoveUpdate(List<AdapterHelper.UpdateOp> list, int i2, AdapterHelper.UpdateOp updateOp, int i3, AdapterHelper.UpdateOp updateOp2) {
        AdapterHelper.UpdateOp updateOpObtainUpdateOp;
        int i4;
        int i5;
        int i6;
        int i7 = updateOp.itemCount;
        int i8 = updateOp2.positionStart;
        AdapterHelper.UpdateOp updateOpObtainUpdateOp2 = null;
        if (i7 >= i8) {
            int i9 = updateOp2.itemCount;
            if (i7 < i8 + i9) {
                updateOp2.itemCount = i9 - 1;
                updateOpObtainUpdateOp = this.mCallback.obtainUpdateOp(4, updateOp.positionStart, 1, updateOp2.payload);
            }
            i4 = updateOp.positionStart;
            i5 = updateOp2.positionStart;
            if (i4 <= i5) {
                updateOp2.positionStart = i5 + 1;
            } else {
                i6 = updateOp2.itemCount;
                if (i4 < i5 + i6) {
                    int i10 = (i5 + i6) - i4;
                    updateOpObtainUpdateOp2 = this.mCallback.obtainUpdateOp(4, i4 + 1, i10, updateOp2.payload);
                    updateOp2.itemCount -= i10;
                }
            }
            list.set(i3, updateOp);
            if (updateOp2.itemCount > 0) {
                list.set(i2, updateOp2);
            } else {
                list.remove(i2);
                this.mCallback.recycleUpdateOp(updateOp2);
            }
            if (updateOpObtainUpdateOp != null) {
                list.add(i2, updateOpObtainUpdateOp);
            }
            if (updateOpObtainUpdateOp2 != null) {
                list.add(i2, updateOpObtainUpdateOp2);
            }
        }
        updateOp2.positionStart = i8 - 1;
        updateOpObtainUpdateOp = null;
        i4 = updateOp.positionStart;
        i5 = updateOp2.positionStart;
        if (i4 <= i5) {
            updateOp2.positionStart = i5 + 1;
        } else {
            i6 = updateOp2.itemCount;
            if (i4 < i5 + i6) {
                int i11 = (i5 + i6) - i4;
                updateOpObtainUpdateOp2 = this.mCallback.obtainUpdateOp(4, i4 + 1, i11, updateOp2.payload);
                updateOp2.itemCount -= i11;
            }
        }
        list.set(i3, updateOp);
        if (updateOp2.itemCount > 0) {
            list.set(i2, updateOp2);
        } else {
            list.remove(i2);
            this.mCallback.recycleUpdateOp(updateOp2);
        }
        if (updateOpObtainUpdateOp != null) {
            list.add(i2, updateOpObtainUpdateOp);
        }
        if (updateOpObtainUpdateOp2 != null) {
            list.add(i2, updateOpObtainUpdateOp2);
        }
    }
}
