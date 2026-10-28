package com.google.android.material.internal;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.UiThread;
import com.google.android.material.internal.MaterialCheckable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
@UiThread
public class CheckableGroup<T extends MaterialCheckable<T>> {
    private final Map<Integer, T> checkables = new HashMap();
    private final Set<Integer> checkedIds = new HashSet();
    private OnCheckedStateChangeListener onCheckedStateChangeListener;
    private boolean selectionRequired;
    private boolean singleSelection;

    public interface OnCheckedStateChangeListener {
        void onCheckedStateChanged(@NonNull Set<Integer> set);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkInternal(@NonNull MaterialCheckable<T> materialCheckable) {
        int id = materialCheckable.getId();
        if (this.checkedIds.contains(Integer.valueOf(id))) {
            return false;
        }
        T t2 = this.checkables.get(Integer.valueOf(getSingleCheckedId()));
        if (t2 != null) {
            uncheckInternal(t2, false);
        }
        boolean zAdd = this.checkedIds.add(Integer.valueOf(id));
        if (!materialCheckable.isChecked()) {
            materialCheckable.setChecked(true);
        }
        return zAdd;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCheckedStateChanged() {
        OnCheckedStateChangeListener onCheckedStateChangeListener = this.onCheckedStateChangeListener;
        if (onCheckedStateChangeListener != null) {
            onCheckedStateChangeListener.onCheckedStateChanged(getCheckedIds());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean uncheckInternal(@NonNull MaterialCheckable<T> materialCheckable, boolean z2) {
        int id = materialCheckable.getId();
        if (!this.checkedIds.contains(Integer.valueOf(id))) {
            return false;
        }
        if (z2 && this.checkedIds.size() == 1 && this.checkedIds.contains(Integer.valueOf(id))) {
            materialCheckable.setChecked(true);
            return false;
        }
        boolean zRemove = this.checkedIds.remove(Integer.valueOf(id));
        if (materialCheckable.isChecked()) {
            materialCheckable.setChecked(false);
        }
        return zRemove;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void addCheckable(T t2) {
        this.checkables.put(Integer.valueOf(t2.getId()), t2);
        if (t2.isChecked()) {
            checkInternal(t2);
        }
        t2.setInternalOnCheckedChangeListener(new MaterialCheckable.OnCheckedChangeListener<T>() { // from class: com.google.android.material.internal.CheckableGroup.1
            @Override // com.google.android.material.internal.MaterialCheckable.OnCheckedChangeListener
            public void onCheckedChanged(T t3, boolean z2) {
                if (!z2) {
                    CheckableGroup checkableGroup = CheckableGroup.this;
                    if (!checkableGroup.uncheckInternal(t3, checkableGroup.selectionRequired)) {
                        return;
                    }
                } else if (!CheckableGroup.this.checkInternal(t3)) {
                    return;
                }
                CheckableGroup.this.onCheckedStateChanged();
            }
        });
    }

    public void check(@IdRes int i2) {
        T t2 = this.checkables.get(Integer.valueOf(i2));
        if (t2 != null && checkInternal(t2)) {
            onCheckedStateChanged();
        }
    }

    public void clearCheck() {
        boolean z2 = !this.checkedIds.isEmpty();
        Iterator<T> it = this.checkables.values().iterator();
        while (it.hasNext()) {
            uncheckInternal(it.next(), false);
        }
        if (z2) {
            onCheckedStateChanged();
        }
    }

    @NonNull
    public Set<Integer> getCheckedIds() {
        return new HashSet(this.checkedIds);
    }

    @NonNull
    public List<Integer> getCheckedIdsSortedByChildOrder(@NonNull ViewGroup viewGroup) {
        Set<Integer> checkedIds = getCheckedIds();
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
            View childAt = viewGroup.getChildAt(i2);
            if ((childAt instanceof MaterialCheckable) && checkedIds.contains(Integer.valueOf(childAt.getId()))) {
                arrayList.add(Integer.valueOf(childAt.getId()));
            }
        }
        return arrayList;
    }

    @IdRes
    public int getSingleCheckedId() {
        if (!this.singleSelection || this.checkedIds.isEmpty()) {
            return -1;
        }
        return this.checkedIds.iterator().next().intValue();
    }

    public boolean isSelectionRequired() {
        return this.selectionRequired;
    }

    public boolean isSingleSelection() {
        return this.singleSelection;
    }

    public void removeCheckable(T t2) {
        t2.setInternalOnCheckedChangeListener(null);
        this.checkables.remove(Integer.valueOf(t2.getId()));
        this.checkedIds.remove(Integer.valueOf(t2.getId()));
    }

    public void setOnCheckedStateChangeListener(@Nullable OnCheckedStateChangeListener onCheckedStateChangeListener) {
        this.onCheckedStateChangeListener = onCheckedStateChangeListener;
    }

    public void setSelectionRequired(boolean z2) {
        this.selectionRequired = z2;
    }

    public void setSingleSelection(boolean z2) {
        if (this.singleSelection != z2) {
            this.singleSelection = z2;
            clearCheck();
        }
    }

    public void uncheck(@IdRes int i2) {
        T t2 = this.checkables.get(Integer.valueOf(i2));
        if (t2 != null && uncheckInternal(t2, this.selectionRequired)) {
            onCheckedStateChanged();
        }
    }
}
