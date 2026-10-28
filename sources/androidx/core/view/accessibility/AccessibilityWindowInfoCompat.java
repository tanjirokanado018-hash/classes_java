package androidx.core.view.accessibility;

import android.graphics.Rect;
import android.graphics.Region;
import android.os.Build;
import android.os.LocaleList;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.os.LocaleListCompat;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class AccessibilityWindowInfoCompat {
    public static final int TYPE_ACCESSIBILITY_OVERLAY = 4;
    public static final int TYPE_APPLICATION = 1;
    public static final int TYPE_INPUT_METHOD = 2;
    public static final int TYPE_MAGNIFICATION_OVERLAY = 6;
    public static final int TYPE_SPLIT_SCREEN_DIVIDER = 5;
    public static final int TYPE_SYSTEM = 3;
    private static final int UNDEFINED = -1;
    private final Object mInfo;

    @RequiresApi(21)
    public static class Api21Impl {
        private Api21Impl() {
        }

        @DoNotInline
        public static void getBoundsInScreen(AccessibilityWindowInfo accessibilityWindowInfo, Rect rect) {
            accessibilityWindowInfo.getBoundsInScreen(rect);
        }

        @DoNotInline
        public static AccessibilityWindowInfo getChild(AccessibilityWindowInfo accessibilityWindowInfo, int i2) {
            return accessibilityWindowInfo.getChild(i2);
        }

        @DoNotInline
        public static int getChildCount(AccessibilityWindowInfo accessibilityWindowInfo) {
            return accessibilityWindowInfo.getChildCount();
        }

        @DoNotInline
        public static int getId(AccessibilityWindowInfo accessibilityWindowInfo) {
            return accessibilityWindowInfo.getId();
        }

        @DoNotInline
        public static int getLayer(AccessibilityWindowInfo accessibilityWindowInfo) {
            return accessibilityWindowInfo.getLayer();
        }

        @DoNotInline
        public static AccessibilityWindowInfo getParent(AccessibilityWindowInfo accessibilityWindowInfo) {
            return accessibilityWindowInfo.getParent();
        }

        @DoNotInline
        public static AccessibilityNodeInfo getRoot(AccessibilityWindowInfo accessibilityWindowInfo) {
            return accessibilityWindowInfo.getRoot();
        }

        @DoNotInline
        public static int getType(AccessibilityWindowInfo accessibilityWindowInfo) {
            return accessibilityWindowInfo.getType();
        }

        @DoNotInline
        public static boolean isAccessibilityFocused(AccessibilityWindowInfo accessibilityWindowInfo) {
            return accessibilityWindowInfo.isAccessibilityFocused();
        }

        @DoNotInline
        public static boolean isActive(AccessibilityWindowInfo accessibilityWindowInfo) {
            return accessibilityWindowInfo.isActive();
        }

        @DoNotInline
        public static boolean isFocused(AccessibilityWindowInfo accessibilityWindowInfo) {
            return accessibilityWindowInfo.isFocused();
        }

        @DoNotInline
        public static AccessibilityWindowInfo obtain() {
            return AccessibilityWindowInfo.obtain();
        }

        @DoNotInline
        public static AccessibilityWindowInfo obtain(AccessibilityWindowInfo accessibilityWindowInfo) {
            return AccessibilityWindowInfo.obtain(accessibilityWindowInfo);
        }
    }

    @RequiresApi(24)
    public static class Api24Impl {
        private Api24Impl() {
        }

        @DoNotInline
        public static AccessibilityNodeInfo getAnchor(AccessibilityWindowInfo accessibilityWindowInfo) {
            return accessibilityWindowInfo.getAnchor();
        }

        @DoNotInline
        public static CharSequence getTitle(AccessibilityWindowInfo accessibilityWindowInfo) {
            return accessibilityWindowInfo.getTitle();
        }
    }

    @RequiresApi(26)
    public static class Api26Impl {
        private Api26Impl() {
        }

        @DoNotInline
        public static boolean isInPictureInPictureMode(AccessibilityWindowInfo accessibilityWindowInfo) {
            return accessibilityWindowInfo.isInPictureInPictureMode();
        }
    }

    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_BIAS)
    public static class Api30Impl {
        private Api30Impl() {
        }

        @DoNotInline
        public static AccessibilityWindowInfo instantiateAccessibilityWindowInfo() {
            return new AccessibilityWindowInfo();
        }
    }

    @RequiresApi(33)
    public static class Api33Impl {
        private Api33Impl() {
        }

        @DoNotInline
        public static int getDisplayId(AccessibilityWindowInfo accessibilityWindowInfo) {
            return accessibilityWindowInfo.getDisplayId();
        }

        @DoNotInline
        public static void getRegionInScreen(AccessibilityWindowInfo accessibilityWindowInfo, Region region) {
            accessibilityWindowInfo.getRegionInScreen(region);
        }

        @DoNotInline
        public static AccessibilityNodeInfoCompat getRoot(Object obj, int i2) {
            return AccessibilityNodeInfoCompat.wrapNonNullInstance(((AccessibilityWindowInfo) obj).getRoot(i2));
        }
    }

    @RequiresApi(34)
    public static class Api34Impl {
        private Api34Impl() {
        }

        @DoNotInline
        public static LocaleList getLocales(AccessibilityWindowInfo accessibilityWindowInfo) {
            return accessibilityWindowInfo.getLocales();
        }

        @DoNotInline
        public static long getTransitionTimeMillis(AccessibilityWindowInfo accessibilityWindowInfo) {
            return accessibilityWindowInfo.getTransitionTimeMillis();
        }
    }

    public AccessibilityWindowInfoCompat() {
        this.mInfo = Build.VERSION.SDK_INT >= 30 ? Api30Impl.instantiateAccessibilityWindowInfo() : null;
    }

    @Nullable
    public static AccessibilityWindowInfoCompat obtain() {
        return wrapNonNullInstance(Api21Impl.obtain());
    }

    private static String typeToString(int i2) {
        if (i2 == 1) {
            return "TYPE_APPLICATION";
        }
        if (i2 == 2) {
            return "TYPE_INPUT_METHOD";
        }
        if (i2 != 3) {
            return i2 != 4 ? "<UNKNOWN>" : "TYPE_ACCESSIBILITY_OVERLAY";
        }
        return "TYPE_SYSTEM";
    }

    public static AccessibilityWindowInfoCompat wrapNonNullInstance(Object obj) {
        if (obj != null) {
            return new AccessibilityWindowInfoCompat(obj);
        }
        return null;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AccessibilityWindowInfoCompat)) {
            return false;
        }
        Object obj2 = this.mInfo;
        Object obj3 = ((AccessibilityWindowInfoCompat) obj).mInfo;
        if (obj2 == null) {
            return obj3 == null;
        }
        return obj2.equals(obj3);
    }

    @Nullable
    public AccessibilityNodeInfoCompat getAnchor() {
        if (Build.VERSION.SDK_INT >= 24) {
            return AccessibilityNodeInfoCompat.wrapNonNullInstance(Api24Impl.getAnchor((AccessibilityWindowInfo) this.mInfo));
        }
        return null;
    }

    public void getBoundsInScreen(@NonNull Rect rect) {
        Api21Impl.getBoundsInScreen((AccessibilityWindowInfo) this.mInfo, rect);
    }

    @Nullable
    public AccessibilityWindowInfoCompat getChild(int i2) {
        return wrapNonNullInstance(Api21Impl.getChild((AccessibilityWindowInfo) this.mInfo, i2));
    }

    public int getChildCount() {
        return Api21Impl.getChildCount((AccessibilityWindowInfo) this.mInfo);
    }

    public int getDisplayId() {
        if (Build.VERSION.SDK_INT >= 33) {
            return Api33Impl.getDisplayId((AccessibilityWindowInfo) this.mInfo);
        }
        return 0;
    }

    public int getId() {
        return Api21Impl.getId((AccessibilityWindowInfo) this.mInfo);
    }

    public int getLayer() {
        return Api21Impl.getLayer((AccessibilityWindowInfo) this.mInfo);
    }

    @NonNull
    public LocaleListCompat getLocales() {
        return Build.VERSION.SDK_INT >= 34 ? LocaleListCompat.wrap(Api34Impl.getLocales((AccessibilityWindowInfo) this.mInfo)) : LocaleListCompat.getEmptyLocaleList();
    }

    @Nullable
    public AccessibilityWindowInfoCompat getParent() {
        return wrapNonNullInstance(Api21Impl.getParent((AccessibilityWindowInfo) this.mInfo));
    }

    public void getRegionInScreen(@NonNull Region region) {
        if (Build.VERSION.SDK_INT >= 33) {
            Api33Impl.getRegionInScreen((AccessibilityWindowInfo) this.mInfo, region);
            return;
        }
        Rect rect = new Rect();
        Api21Impl.getBoundsInScreen((AccessibilityWindowInfo) this.mInfo, rect);
        region.set(rect);
    }

    @Nullable
    public AccessibilityNodeInfoCompat getRoot() {
        return AccessibilityNodeInfoCompat.wrapNonNullInstance(Api21Impl.getRoot((AccessibilityWindowInfo) this.mInfo));
    }

    @Nullable
    public CharSequence getTitle() {
        if (Build.VERSION.SDK_INT >= 24) {
            return Api24Impl.getTitle((AccessibilityWindowInfo) this.mInfo);
        }
        return null;
    }

    public long getTransitionTimeMillis() {
        if (Build.VERSION.SDK_INT >= 34) {
            return Api34Impl.getTransitionTimeMillis((AccessibilityWindowInfo) this.mInfo);
        }
        return 0L;
    }

    public int getType() {
        return Api21Impl.getType((AccessibilityWindowInfo) this.mInfo);
    }

    public int hashCode() {
        Object obj = this.mInfo;
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }

    public boolean isAccessibilityFocused() {
        return Api21Impl.isAccessibilityFocused((AccessibilityWindowInfo) this.mInfo);
    }

    public boolean isActive() {
        return Api21Impl.isActive((AccessibilityWindowInfo) this.mInfo);
    }

    public boolean isFocused() {
        return Api21Impl.isFocused((AccessibilityWindowInfo) this.mInfo);
    }

    public boolean isInPictureInPictureMode() {
        if (Build.VERSION.SDK_INT >= 26) {
            return Api26Impl.isInPictureInPictureMode((AccessibilityWindowInfo) this.mInfo);
        }
        return false;
    }

    @Deprecated
    public void recycle() {
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("AccessibilityWindowInfo[id=");
        Rect rect = new Rect();
        getBoundsInScreen(rect);
        sb.append(getId());
        sb.append(", type=");
        sb.append(typeToString(getType()));
        sb.append(", layer=");
        sb.append(getLayer());
        sb.append(", bounds=");
        sb.append(rect);
        sb.append(", focused=");
        sb.append(isFocused());
        sb.append(", active=");
        sb.append(isActive());
        sb.append(", hasParent=");
        sb.append(getParent() != null);
        sb.append(", hasChildren=");
        sb.append(getChildCount() > 0);
        sb.append(", transitionTime=");
        sb.append(getTransitionTimeMillis());
        sb.append(", locales=");
        sb.append(getLocales());
        sb.append(']');
        return sb.toString();
    }

    @Nullable
    public AccessibilityWindowInfo unwrap() {
        return (AccessibilityWindowInfo) this.mInfo;
    }

    private AccessibilityWindowInfoCompat(Object obj) {
        this.mInfo = obj;
    }

    @Nullable
    public static AccessibilityWindowInfoCompat obtain(@Nullable AccessibilityWindowInfoCompat accessibilityWindowInfoCompat) {
        if (accessibilityWindowInfoCompat == null) {
            return null;
        }
        return wrapNonNullInstance(Api21Impl.obtain((AccessibilityWindowInfo) accessibilityWindowInfoCompat.mInfo));
    }

    @Nullable
    public AccessibilityNodeInfoCompat getRoot(int i2) {
        return Build.VERSION.SDK_INT >= 33 ? Api33Impl.getRoot(this.mInfo, i2) : getRoot();
    }
}
