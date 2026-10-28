package androidx.core.view.accessibility;

import android.os.Build;
import android.os.Bundle;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class AccessibilityNodeProviderCompat {
    public static final int HOST_VIEW_ID = -1;

    @Nullable
    private final Object mProvider;

    public static class AccessibilityNodeProviderApi19 extends AccessibilityNodeProvider {
        final AccessibilityNodeProviderCompat mCompat;

        public AccessibilityNodeProviderApi19(AccessibilityNodeProviderCompat accessibilityNodeProviderCompat) {
            this.mCompat = accessibilityNodeProviderCompat;
        }

        @Override // android.view.accessibility.AccessibilityNodeProvider
        public AccessibilityNodeInfo createAccessibilityNodeInfo(int i2) {
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompatCreateAccessibilityNodeInfo = this.mCompat.createAccessibilityNodeInfo(i2);
            if (accessibilityNodeInfoCompatCreateAccessibilityNodeInfo == null) {
                return null;
            }
            return accessibilityNodeInfoCompatCreateAccessibilityNodeInfo.unwrap();
        }

        @Override // android.view.accessibility.AccessibilityNodeProvider
        public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(String str, int i2) {
            List<AccessibilityNodeInfoCompat> listFindAccessibilityNodeInfosByText = this.mCompat.findAccessibilityNodeInfosByText(str, i2);
            if (listFindAccessibilityNodeInfosByText == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            int size = listFindAccessibilityNodeInfosByText.size();
            for (int i3 = 0; i3 < size; i3++) {
                arrayList.add(listFindAccessibilityNodeInfosByText.get(i3).unwrap());
            }
            return arrayList;
        }

        @Override // android.view.accessibility.AccessibilityNodeProvider
        public AccessibilityNodeInfo findFocus(int i2) {
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompatFindFocus = this.mCompat.findFocus(i2);
            if (accessibilityNodeInfoCompatFindFocus == null) {
                return null;
            }
            return accessibilityNodeInfoCompatFindFocus.unwrap();
        }

        @Override // android.view.accessibility.AccessibilityNodeProvider
        public boolean performAction(int i2, int i3, Bundle bundle) {
            return this.mCompat.performAction(i2, i3, bundle);
        }
    }

    @RequiresApi(26)
    public static class AccessibilityNodeProviderApi26 extends AccessibilityNodeProviderApi19 {
        public AccessibilityNodeProviderApi26(AccessibilityNodeProviderCompat accessibilityNodeProviderCompat) {
            super(accessibilityNodeProviderCompat);
        }

        @Override // android.view.accessibility.AccessibilityNodeProvider
        public void addExtraDataToAccessibilityNodeInfo(int i2, AccessibilityNodeInfo accessibilityNodeInfo, String str, Bundle bundle) {
            this.mCompat.addExtraDataToAccessibilityNodeInfo(i2, AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo), str, bundle);
        }
    }

    public AccessibilityNodeProviderCompat() {
        this.mProvider = Build.VERSION.SDK_INT >= 26 ? new AccessibilityNodeProviderApi26(this) : new AccessibilityNodeProviderApi19(this);
    }

    public void addExtraDataToAccessibilityNodeInfo(int i2, @NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, @NonNull String str, @Nullable Bundle bundle) {
    }

    @Nullable
    public AccessibilityNodeInfoCompat createAccessibilityNodeInfo(int i2) {
        return null;
    }

    @Nullable
    public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByText(@NonNull String str, int i2) {
        return null;
    }

    @Nullable
    public AccessibilityNodeInfoCompat findFocus(int i2) {
        return null;
    }

    @Nullable
    public Object getProvider() {
        return this.mProvider;
    }

    public boolean performAction(int i2, int i3, @Nullable Bundle bundle) {
        return false;
    }

    public AccessibilityNodeProviderCompat(@Nullable Object obj) {
        this.mProvider = obj;
    }
}
