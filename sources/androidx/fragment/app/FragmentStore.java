package androidx.fragment.app;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
class FragmentStore {
    private static final String TAG = "FragmentManager";
    private FragmentManagerViewModel mNonConfig;
    private final ArrayList<Fragment> mAdded = new ArrayList<>();
    private final HashMap<String, FragmentStateManager> mActive = new HashMap<>();
    private final HashMap<String, FragmentState> mSavedState = new HashMap<>();

    public void addFragment(@NonNull Fragment fragment) {
        if (this.mAdded.contains(fragment)) {
            throw new IllegalStateException("Fragment already added: " + fragment);
        }
        synchronized (this.mAdded) {
            this.mAdded.add(fragment);
        }
        fragment.mAdded = true;
    }

    public void burpActive() {
        this.mActive.values().removeAll(Collections.singleton(null));
    }

    public boolean containsActiveFragment(@NonNull String str) {
        return this.mActive.get(str) != null;
    }

    public void dispatchStateChange(int i2) {
        for (FragmentStateManager fragmentStateManager : this.mActive.values()) {
            if (fragmentStateManager != null) {
                fragmentStateManager.setFragmentManagerState(i2);
            }
        }
    }

    public void dump(@NonNull String str, @Nullable FileDescriptor fileDescriptor, @NonNull PrintWriter printWriter, @Nullable String[] strArr) {
        String strE = androidx.appcompat.graphics.drawable.a.e(str, "    ");
        if (!this.mActive.isEmpty()) {
            printWriter.print(str);
            printWriter.println("Active Fragments:");
            for (FragmentStateManager fragmentStateManager : this.mActive.values()) {
                printWriter.print(str);
                if (fragmentStateManager != null) {
                    Fragment fragment = fragmentStateManager.getFragment();
                    printWriter.println(fragment);
                    fragment.dump(strE, fileDescriptor, printWriter, strArr);
                } else {
                    printWriter.println("null");
                }
            }
        }
        int size = this.mAdded.size();
        if (size > 0) {
            printWriter.print(str);
            printWriter.println("Added Fragments:");
            for (int i2 = 0; i2 < size; i2++) {
                Fragment fragment2 = this.mAdded.get(i2);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i2);
                printWriter.print(": ");
                printWriter.println(fragment2.toString());
            }
        }
    }

    @Nullable
    public Fragment findActiveFragment(@NonNull String str) {
        FragmentStateManager fragmentStateManager = this.mActive.get(str);
        if (fragmentStateManager != null) {
            return fragmentStateManager.getFragment();
        }
        return null;
    }

    @Nullable
    public Fragment findFragmentById(@IdRes int i2) {
        for (int size = this.mAdded.size() - 1; size >= 0; size--) {
            Fragment fragment = this.mAdded.get(size);
            if (fragment != null && fragment.mFragmentId == i2) {
                return fragment;
            }
        }
        for (FragmentStateManager fragmentStateManager : this.mActive.values()) {
            if (fragmentStateManager != null) {
                Fragment fragment2 = fragmentStateManager.getFragment();
                if (fragment2.mFragmentId == i2) {
                    return fragment2;
                }
            }
        }
        return null;
    }

    @Nullable
    public Fragment findFragmentByTag(@Nullable String str) {
        if (str != null) {
            for (int size = this.mAdded.size() - 1; size >= 0; size--) {
                Fragment fragment = this.mAdded.get(size);
                if (fragment != null && str.equals(fragment.mTag)) {
                    return fragment;
                }
            }
        }
        if (str == null) {
            return null;
        }
        for (FragmentStateManager fragmentStateManager : this.mActive.values()) {
            if (fragmentStateManager != null) {
                Fragment fragment2 = fragmentStateManager.getFragment();
                if (str.equals(fragment2.mTag)) {
                    return fragment2;
                }
            }
        }
        return null;
    }

    @Nullable
    public Fragment findFragmentByWho(@NonNull String str) {
        Fragment fragmentFindFragmentByWho;
        for (FragmentStateManager fragmentStateManager : this.mActive.values()) {
            if (fragmentStateManager != null && (fragmentFindFragmentByWho = fragmentStateManager.getFragment().findFragmentByWho(str)) != null) {
                return fragmentFindFragmentByWho;
            }
        }
        return null;
    }

    public int findFragmentIndexInContainer(@NonNull Fragment fragment) {
        View view;
        View view2;
        ViewGroup viewGroup = fragment.mContainer;
        if (viewGroup == null) {
            return -1;
        }
        int iIndexOf = this.mAdded.indexOf(fragment);
        for (int i2 = iIndexOf - 1; i2 >= 0; i2--) {
            Fragment fragment2 = this.mAdded.get(i2);
            if (fragment2.mContainer == viewGroup && (view2 = fragment2.mView) != null) {
                return viewGroup.indexOfChild(view2) + 1;
            }
        }
        while (true) {
            iIndexOf++;
            if (iIndexOf >= this.mAdded.size()) {
                return -1;
            }
            Fragment fragment3 = this.mAdded.get(iIndexOf);
            if (fragment3.mContainer == viewGroup && (view = fragment3.mView) != null) {
                return viewGroup.indexOfChild(view);
            }
        }
    }

    public int getActiveFragmentCount() {
        return this.mActive.size();
    }

    @NonNull
    public List<FragmentStateManager> getActiveFragmentStateManagers() {
        ArrayList arrayList = new ArrayList();
        for (FragmentStateManager fragmentStateManager : this.mActive.values()) {
            if (fragmentStateManager != null) {
                arrayList.add(fragmentStateManager);
            }
        }
        return arrayList;
    }

    @NonNull
    public List<Fragment> getActiveFragments() {
        ArrayList arrayList = new ArrayList();
        Iterator<FragmentStateManager> it = this.mActive.values().iterator();
        while (it.hasNext()) {
            FragmentStateManager next = it.next();
            arrayList.add(next != null ? next.getFragment() : null);
        }
        return arrayList;
    }

    @NonNull
    public ArrayList<FragmentState> getAllSavedState() {
        return new ArrayList<>(this.mSavedState.values());
    }

    @Nullable
    public FragmentStateManager getFragmentStateManager(@NonNull String str) {
        return this.mActive.get(str);
    }

    @NonNull
    public List<Fragment> getFragments() {
        ArrayList arrayList;
        if (this.mAdded.isEmpty()) {
            return Collections.emptyList();
        }
        synchronized (this.mAdded) {
            arrayList = new ArrayList(this.mAdded);
        }
        return arrayList;
    }

    public FragmentManagerViewModel getNonConfig() {
        return this.mNonConfig;
    }

    @Nullable
    public FragmentState getSavedState(@NonNull String str) {
        return this.mSavedState.get(str);
    }

    public void makeActive(@NonNull FragmentStateManager fragmentStateManager) {
        Fragment fragment = fragmentStateManager.getFragment();
        if (containsActiveFragment(fragment.mWho)) {
            return;
        }
        this.mActive.put(fragment.mWho, fragmentStateManager);
        if (fragment.mRetainInstanceChangedWhileDetached) {
            if (fragment.mRetainInstance) {
                this.mNonConfig.addRetainedFragment(fragment);
            } else {
                this.mNonConfig.removeRetainedFragment(fragment);
            }
            fragment.mRetainInstanceChangedWhileDetached = false;
        }
        if (FragmentManager.isLoggingEnabled(2)) {
            fragment.toString();
        }
    }

    public void makeInactive(@NonNull FragmentStateManager fragmentStateManager) {
        Fragment fragment = fragmentStateManager.getFragment();
        if (fragment.mRetainInstance) {
            this.mNonConfig.removeRetainedFragment(fragment);
        }
        if (this.mActive.put(fragment.mWho, null) != null && FragmentManager.isLoggingEnabled(2)) {
            fragment.toString();
        }
    }

    public void moveToExpectedState() {
        Iterator<Fragment> it = this.mAdded.iterator();
        while (it.hasNext()) {
            FragmentStateManager fragmentStateManager = this.mActive.get(it.next().mWho);
            if (fragmentStateManager != null) {
                fragmentStateManager.moveToExpectedState();
            }
        }
        for (FragmentStateManager fragmentStateManager2 : this.mActive.values()) {
            if (fragmentStateManager2 != null) {
                fragmentStateManager2.moveToExpectedState();
                Fragment fragment = fragmentStateManager2.getFragment();
                if (fragment.mRemoving && !fragment.isInBackStack()) {
                    if (fragment.mBeingSaved && !this.mSavedState.containsKey(fragment.mWho)) {
                        fragmentStateManager2.saveState();
                    }
                    makeInactive(fragmentStateManager2);
                }
            }
        }
    }

    public void removeFragment(@NonNull Fragment fragment) {
        synchronized (this.mAdded) {
            this.mAdded.remove(fragment);
        }
        fragment.mAdded = false;
    }

    public void resetActiveFragments() {
        this.mActive.clear();
    }

    public void restoreAddedFragments(@Nullable List<String> list) {
        this.mAdded.clear();
        if (list != null) {
            for (String str : list) {
                Fragment fragmentFindActiveFragment = findActiveFragment(str);
                if (fragmentFindActiveFragment == null) {
                    throw new IllegalStateException(androidx.appcompat.graphics.drawable.a.f("No instantiated fragment for (", str, ")"));
                }
                if (FragmentManager.isLoggingEnabled(2)) {
                    fragmentFindActiveFragment.toString();
                }
                addFragment(fragmentFindActiveFragment);
            }
        }
    }

    public void restoreSaveState(@NonNull ArrayList<FragmentState> arrayList) {
        this.mSavedState.clear();
        for (FragmentState fragmentState : arrayList) {
            this.mSavedState.put(fragmentState.mWho, fragmentState);
        }
    }

    @NonNull
    public ArrayList<String> saveActiveFragments() {
        ArrayList<String> arrayList = new ArrayList<>(this.mActive.size());
        for (FragmentStateManager fragmentStateManager : this.mActive.values()) {
            if (fragmentStateManager != null) {
                Fragment fragment = fragmentStateManager.getFragment();
                fragmentStateManager.saveState();
                arrayList.add(fragment.mWho);
                if (FragmentManager.isLoggingEnabled(2)) {
                    fragment.toString();
                    Objects.toString(fragment.mSavedFragmentState);
                }
            }
        }
        return arrayList;
    }

    @Nullable
    public ArrayList<String> saveAddedFragments() {
        synchronized (this.mAdded) {
            try {
                if (this.mAdded.isEmpty()) {
                    return null;
                }
                ArrayList<String> arrayList = new ArrayList<>(this.mAdded.size());
                for (Fragment fragment : this.mAdded) {
                    arrayList.add(fragment.mWho);
                    if (FragmentManager.isLoggingEnabled(2)) {
                        fragment.toString();
                    }
                }
                return arrayList;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void setNonConfig(@NonNull FragmentManagerViewModel fragmentManagerViewModel) {
        this.mNonConfig = fragmentManagerViewModel;
    }

    @Nullable
    public FragmentState setSavedState(@NonNull String str, @Nullable FragmentState fragmentState) {
        return fragmentState != null ? this.mSavedState.put(str, fragmentState) : this.mSavedState.remove(str);
    }
}
