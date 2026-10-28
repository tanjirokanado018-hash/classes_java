package androidx.fragment.app;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import androidx.core.app.SharedElementCallback;
import androidx.transition.FragmentTransitionSupport;
import java.util.ArrayList;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
class FragmentTransition {
    static final FragmentTransitionImpl PLATFORM_IMPL = new FragmentTransitionCompat21();
    static final FragmentTransitionImpl SUPPORT_IMPL = resolveSupportImpl();

    private FragmentTransition() {
    }

    public static void callSharedElementStartEnd(Fragment fragment, Fragment fragment2, boolean z2, ArrayMap<String, View> arrayMap, boolean z3) {
        SharedElementCallback enterTransitionCallback = z2 ? fragment2.getEnterTransitionCallback() : fragment.getEnterTransitionCallback();
        if (enterTransitionCallback != null) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            int size = arrayMap == null ? 0 : arrayMap.size();
            for (int i2 = 0; i2 < size; i2++) {
                arrayList2.add(arrayMap.keyAt(i2));
                arrayList.add(arrayMap.valueAt(i2));
            }
            if (z3) {
                enterTransitionCallback.onSharedElementStart(arrayList2, arrayList, null);
            } else {
                enterTransitionCallback.onSharedElementEnd(arrayList2, arrayList, null);
            }
        }
    }

    public static String findKeyForValue(ArrayMap<String, String> arrayMap, String str) {
        int size = arrayMap.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (str.equals(arrayMap.valueAt(i2))) {
                return arrayMap.keyAt(i2);
            }
        }
        return null;
    }

    private static FragmentTransitionImpl resolveSupportImpl() {
        try {
            return (FragmentTransitionImpl) FragmentTransitionSupport.class.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }

    public static void retainValues(@NonNull ArrayMap<String, String> arrayMap, @NonNull ArrayMap<String, View> arrayMap2) {
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            if (!arrayMap2.containsKey(arrayMap.valueAt(size))) {
                arrayMap.removeAt(size);
            }
        }
    }

    public static void setViewVisibility(ArrayList<View> arrayList, int i2) {
        if (arrayList == null) {
            return;
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            arrayList.get(size).setVisibility(i2);
        }
    }

    public static boolean supportsTransition() {
        return (PLATFORM_IMPL == null && SUPPORT_IMPL == null) ? false : true;
    }
}
