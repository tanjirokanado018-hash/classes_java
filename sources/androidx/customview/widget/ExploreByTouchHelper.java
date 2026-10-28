package androidx.customview.widget;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.SparseArrayCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeProviderCompat;
import androidx.core.view.accessibility.AccessibilityRecordCompat;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class ExploreByTouchHelper extends AccessibilityDelegateCompat {
    private static final String DEFAULT_CLASS_NAME = "android.view.View";
    public static final int HOST_ID = -1;
    public static final int INVALID_ID = Integer.MIN_VALUE;
    private static final Rect INVALID_PARENT_BOUNDS = new Rect(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    private static final FocusStrategy.BoundsAdapter<AccessibilityNodeInfoCompat> NODE_ADAPTER = new FocusStrategy.BoundsAdapter<AccessibilityNodeInfoCompat>() { // from class: androidx.customview.widget.ExploreByTouchHelper.1
        @Override // androidx.customview.widget.FocusStrategy.BoundsAdapter
        public void obtainBounds(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, Rect rect) {
            accessibilityNodeInfoCompat.getBoundsInParent(rect);
        }
    };
    private static final FocusStrategy.CollectionAdapter<SparseArrayCompat<AccessibilityNodeInfoCompat>, AccessibilityNodeInfoCompat> SPARSE_VALUES_ADAPTER = new FocusStrategy.CollectionAdapter<SparseArrayCompat<AccessibilityNodeInfoCompat>, AccessibilityNodeInfoCompat>() { // from class: androidx.customview.widget.ExploreByTouchHelper.2
        @Override // androidx.customview.widget.FocusStrategy.CollectionAdapter
        public AccessibilityNodeInfoCompat get(SparseArrayCompat<AccessibilityNodeInfoCompat> sparseArrayCompat, int i2) {
            return sparseArrayCompat.valueAt(i2);
        }

        @Override // androidx.customview.widget.FocusStrategy.CollectionAdapter
        public int size(SparseArrayCompat<AccessibilityNodeInfoCompat> sparseArrayCompat) {
            return sparseArrayCompat.size();
        }
    };
    private final View mHost;
    private final AccessibilityManager mManager;
    private MyNodeProvider mNodeProvider;
    private final Rect mTempScreenRect = new Rect();
    private final Rect mTempParentRect = new Rect();
    private final Rect mTempVisibleRect = new Rect();
    private final int[] mTempGlobalRect = new int[2];
    int mAccessibilityFocusedVirtualViewId = Integer.MIN_VALUE;
    int mKeyboardFocusedVirtualViewId = Integer.MIN_VALUE;
    private int mHoveredVirtualViewId = Integer.MIN_VALUE;

    public class MyNodeProvider extends AccessibilityNodeProviderCompat {
        public MyNodeProvider() {
        }

        @Override // androidx.core.view.accessibility.AccessibilityNodeProviderCompat
        public AccessibilityNodeInfoCompat createAccessibilityNodeInfo(int i2) {
            return AccessibilityNodeInfoCompat.obtain(ExploreByTouchHelper.this.obtainAccessibilityNodeInfo(i2));
        }

        @Override // androidx.core.view.accessibility.AccessibilityNodeProviderCompat
        public AccessibilityNodeInfoCompat findFocus(int i2) {
            int i3 = i2 == 2 ? ExploreByTouchHelper.this.mAccessibilityFocusedVirtualViewId : ExploreByTouchHelper.this.mKeyboardFocusedVirtualViewId;
            if (i3 == Integer.MIN_VALUE) {
                return null;
            }
            return createAccessibilityNodeInfo(i3);
        }

        @Override // androidx.core.view.accessibility.AccessibilityNodeProviderCompat
        public boolean performAction(int i2, int i3, Bundle bundle) {
            return ExploreByTouchHelper.this.performAction(i2, i3, bundle);
        }
    }

    public ExploreByTouchHelper(@NonNull View view) {
        if (view == null) {
            throw new IllegalArgumentException("View may not be null");
        }
        this.mHost = view;
        this.mManager = (AccessibilityManager) view.getContext().getSystemService("accessibility");
        view.setFocusable(true);
        if (ViewCompat.getImportantForAccessibility(view) == 0) {
            ViewCompat.setImportantForAccessibility(view, 1);
        }
    }

    private boolean clearAccessibilityFocus(int i2) {
        if (this.mAccessibilityFocusedVirtualViewId != i2) {
            return false;
        }
        this.mAccessibilityFocusedVirtualViewId = Integer.MIN_VALUE;
        this.mHost.invalidate();
        sendEventForVirtualView(i2, 65536);
        return true;
    }

    private boolean clickKeyboardFocusedVirtualView() {
        int i2 = this.mKeyboardFocusedVirtualViewId;
        return i2 != Integer.MIN_VALUE && onPerformActionForVirtualView(i2, 16, null);
    }

    private AccessibilityEvent createEvent(int i2, int i3) {
        return i2 != -1 ? createEventForChild(i2, i3) : createEventForHost(i3);
    }

    private AccessibilityEvent createEventForChild(int i2, int i3) {
        AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain(i3);
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompatObtainAccessibilityNodeInfo = obtainAccessibilityNodeInfo(i2);
        accessibilityEventObtain.getText().add(accessibilityNodeInfoCompatObtainAccessibilityNodeInfo.getText());
        accessibilityEventObtain.setContentDescription(accessibilityNodeInfoCompatObtainAccessibilityNodeInfo.getContentDescription());
        accessibilityEventObtain.setScrollable(accessibilityNodeInfoCompatObtainAccessibilityNodeInfo.isScrollable());
        accessibilityEventObtain.setPassword(accessibilityNodeInfoCompatObtainAccessibilityNodeInfo.isPassword());
        accessibilityEventObtain.setEnabled(accessibilityNodeInfoCompatObtainAccessibilityNodeInfo.isEnabled());
        accessibilityEventObtain.setChecked(accessibilityNodeInfoCompatObtainAccessibilityNodeInfo.isChecked());
        onPopulateEventForVirtualView(i2, accessibilityEventObtain);
        if (accessibilityEventObtain.getText().isEmpty() && accessibilityEventObtain.getContentDescription() == null) {
            throw new RuntimeException("Callbacks must add text or a content description in populateEventForVirtualViewId()");
        }
        accessibilityEventObtain.setClassName(accessibilityNodeInfoCompatObtainAccessibilityNodeInfo.getClassName());
        AccessibilityRecordCompat.setSource(accessibilityEventObtain, this.mHost, i2);
        accessibilityEventObtain.setPackageName(this.mHost.getContext().getPackageName());
        return accessibilityEventObtain;
    }

    private AccessibilityEvent createEventForHost(int i2) {
        AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain(i2);
        this.mHost.onInitializeAccessibilityEvent(accessibilityEventObtain);
        return accessibilityEventObtain;
    }

    @NonNull
    private AccessibilityNodeInfoCompat createNodeForChild(int i2) {
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompatObtain = AccessibilityNodeInfoCompat.obtain();
        accessibilityNodeInfoCompatObtain.setEnabled(true);
        accessibilityNodeInfoCompatObtain.setFocusable(true);
        accessibilityNodeInfoCompatObtain.setClassName(DEFAULT_CLASS_NAME);
        Rect rect = INVALID_PARENT_BOUNDS;
        accessibilityNodeInfoCompatObtain.setBoundsInParent(rect);
        accessibilityNodeInfoCompatObtain.setBoundsInScreen(rect);
        accessibilityNodeInfoCompatObtain.setParent(this.mHost);
        onPopulateNodeForVirtualView(i2, accessibilityNodeInfoCompatObtain);
        if (accessibilityNodeInfoCompatObtain.getText() == null && accessibilityNodeInfoCompatObtain.getContentDescription() == null) {
            throw new RuntimeException("Callbacks must add text or a content description in populateNodeForVirtualViewId()");
        }
        accessibilityNodeInfoCompatObtain.getBoundsInParent(this.mTempParentRect);
        if (this.mTempParentRect.equals(rect)) {
            throw new RuntimeException("Callbacks must set parent bounds in populateNodeForVirtualViewId()");
        }
        int actions = accessibilityNodeInfoCompatObtain.getActions();
        if ((actions & 64) != 0) {
            throw new RuntimeException("Callbacks must not add ACTION_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
        }
        if ((actions & 128) != 0) {
            throw new RuntimeException("Callbacks must not add ACTION_CLEAR_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
        }
        accessibilityNodeInfoCompatObtain.setPackageName(this.mHost.getContext().getPackageName());
        accessibilityNodeInfoCompatObtain.setSource(this.mHost, i2);
        if (this.mAccessibilityFocusedVirtualViewId == i2) {
            accessibilityNodeInfoCompatObtain.setAccessibilityFocused(true);
            accessibilityNodeInfoCompatObtain.addAction(128);
        } else {
            accessibilityNodeInfoCompatObtain.setAccessibilityFocused(false);
            accessibilityNodeInfoCompatObtain.addAction(64);
        }
        boolean z2 = this.mKeyboardFocusedVirtualViewId == i2;
        if (z2) {
            accessibilityNodeInfoCompatObtain.addAction(2);
        } else if (accessibilityNodeInfoCompatObtain.isFocusable()) {
            accessibilityNodeInfoCompatObtain.addAction(1);
        }
        accessibilityNodeInfoCompatObtain.setFocused(z2);
        this.mHost.getLocationOnScreen(this.mTempGlobalRect);
        accessibilityNodeInfoCompatObtain.getBoundsInScreen(this.mTempScreenRect);
        if (this.mTempScreenRect.equals(rect)) {
            accessibilityNodeInfoCompatObtain.getBoundsInParent(this.mTempScreenRect);
            if (accessibilityNodeInfoCompatObtain.mParentVirtualDescendantId != -1) {
                AccessibilityNodeInfoCompat accessibilityNodeInfoCompatObtain2 = AccessibilityNodeInfoCompat.obtain();
                for (int i3 = accessibilityNodeInfoCompatObtain.mParentVirtualDescendantId; i3 != -1; i3 = accessibilityNodeInfoCompatObtain2.mParentVirtualDescendantId) {
                    accessibilityNodeInfoCompatObtain2.setParent(this.mHost, -1);
                    accessibilityNodeInfoCompatObtain2.setBoundsInParent(INVALID_PARENT_BOUNDS);
                    onPopulateNodeForVirtualView(i3, accessibilityNodeInfoCompatObtain2);
                    accessibilityNodeInfoCompatObtain2.getBoundsInParent(this.mTempParentRect);
                    Rect rect2 = this.mTempScreenRect;
                    Rect rect3 = this.mTempParentRect;
                    rect2.offset(rect3.left, rect3.top);
                }
                accessibilityNodeInfoCompatObtain2.recycle();
            }
            this.mTempScreenRect.offset(this.mTempGlobalRect[0] - this.mHost.getScrollX(), this.mTempGlobalRect[1] - this.mHost.getScrollY());
        }
        if (this.mHost.getLocalVisibleRect(this.mTempVisibleRect)) {
            this.mTempVisibleRect.offset(this.mTempGlobalRect[0] - this.mHost.getScrollX(), this.mTempGlobalRect[1] - this.mHost.getScrollY());
            if (this.mTempScreenRect.intersect(this.mTempVisibleRect)) {
                accessibilityNodeInfoCompatObtain.setBoundsInScreen(this.mTempScreenRect);
                if (isVisibleToUser(this.mTempScreenRect)) {
                    accessibilityNodeInfoCompatObtain.setVisibleToUser(true);
                }
            }
        }
        return accessibilityNodeInfoCompatObtain;
    }

    @NonNull
    private AccessibilityNodeInfoCompat createNodeForHost() {
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompatObtain = AccessibilityNodeInfoCompat.obtain(this.mHost);
        ViewCompat.onInitializeAccessibilityNodeInfo(this.mHost, accessibilityNodeInfoCompatObtain);
        ArrayList arrayList = new ArrayList();
        getVisibleVirtualViews(arrayList);
        if (accessibilityNodeInfoCompatObtain.getChildCount() > 0 && arrayList.size() > 0) {
            throw new RuntimeException("Views cannot have both real and virtual children");
        }
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            accessibilityNodeInfoCompatObtain.addChild(this.mHost, ((Integer) arrayList.get(i2)).intValue());
        }
        return accessibilityNodeInfoCompatObtain;
    }

    private SparseArrayCompat<AccessibilityNodeInfoCompat> getAllNodes() {
        ArrayList arrayList = new ArrayList();
        getVisibleVirtualViews(arrayList);
        SparseArrayCompat<AccessibilityNodeInfoCompat> sparseArrayCompat = new SparseArrayCompat<>();
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            sparseArrayCompat.put(((Integer) arrayList.get(i2)).intValue(), createNodeForChild(((Integer) arrayList.get(i2)).intValue()));
        }
        return sparseArrayCompat;
    }

    private void getBoundsInParent(int i2, Rect rect) {
        obtainAccessibilityNodeInfo(i2).getBoundsInParent(rect);
    }

    private static Rect guessPreviouslyFocusedRect(@NonNull View view, int i2, @NonNull Rect rect) {
        int width = view.getWidth();
        int height = view.getHeight();
        if (i2 == 17) {
            rect.set(width, 0, width, height);
        } else if (i2 == 33) {
            rect.set(0, height, width, height);
        } else if (i2 == 66) {
            rect.set(-1, 0, -1, height);
        } else {
            if (i2 != 130) {
                throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
            }
            rect.set(0, -1, width, -1);
        }
        return rect;
    }

    private boolean isVisibleToUser(Rect rect) {
        if (rect == null || rect.isEmpty() || this.mHost.getWindowVisibility() != 0) {
            return false;
        }
        View view = this.mHost;
        do {
            Object parent = view.getParent();
            if (!(parent instanceof View)) {
                return parent != null;
            }
            view = (View) parent;
            if (view.getAlpha() <= 0.0f) {
                break;
            }
        } while (view.getVisibility() == 0);
        return false;
    }

    private static int keyToDirection(int i2) {
        if (i2 == 19) {
            return 33;
        }
        if (i2 != 21) {
            return i2 != 22 ? 130 : 66;
        }
        return 17;
    }

    private boolean moveFocus(int i2, @Nullable Rect rect) {
        Object objFindNextFocusInRelativeDirection;
        SparseArrayCompat<AccessibilityNodeInfoCompat> allNodes = getAllNodes();
        int i3 = this.mKeyboardFocusedVirtualViewId;
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = i3 == Integer.MIN_VALUE ? null : allNodes.get(i3);
        if (i2 == 1 || i2 == 2) {
            objFindNextFocusInRelativeDirection = FocusStrategy.findNextFocusInRelativeDirection(allNodes, SPARSE_VALUES_ADAPTER, NODE_ADAPTER, accessibilityNodeInfoCompat, i2, ViewCompat.getLayoutDirection(this.mHost) == 1, false);
        } else {
            if (i2 != 17 && i2 != 33 && i2 != 66 && i2 != 130) {
                throw new IllegalArgumentException("direction must be one of {FOCUS_FORWARD, FOCUS_BACKWARD, FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
            }
            Rect rect2 = new Rect();
            int i4 = this.mKeyboardFocusedVirtualViewId;
            if (i4 != Integer.MIN_VALUE) {
                getBoundsInParent(i4, rect2);
            } else if (rect != null) {
                rect2.set(rect);
            } else {
                guessPreviouslyFocusedRect(this.mHost, i2, rect2);
            }
            objFindNextFocusInRelativeDirection = FocusStrategy.findNextFocusInAbsoluteDirection(allNodes, SPARSE_VALUES_ADAPTER, NODE_ADAPTER, accessibilityNodeInfoCompat, rect2, i2);
        }
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = (AccessibilityNodeInfoCompat) objFindNextFocusInRelativeDirection;
        return requestKeyboardFocusForVirtualView(accessibilityNodeInfoCompat2 != null ? allNodes.keyAt(allNodes.indexOfValue(accessibilityNodeInfoCompat2)) : Integer.MIN_VALUE);
    }

    private boolean performActionForChild(int i2, int i3, Bundle bundle) {
        if (i3 == 1) {
            return requestKeyboardFocusForVirtualView(i2);
        }
        if (i3 == 2) {
            return clearKeyboardFocusForVirtualView(i2);
        }
        if (i3 != 64) {
            return i3 != 128 ? onPerformActionForVirtualView(i2, i3, bundle) : clearAccessibilityFocus(i2);
        }
        return requestAccessibilityFocus(i2);
    }

    private boolean performActionForHost(int i2, Bundle bundle) {
        return ViewCompat.performAccessibilityAction(this.mHost, i2, bundle);
    }

    private boolean requestAccessibilityFocus(int i2) {
        int i3;
        if (!this.mManager.isEnabled() || !this.mManager.isTouchExplorationEnabled() || (i3 = this.mAccessibilityFocusedVirtualViewId) == i2) {
            return false;
        }
        if (i3 != Integer.MIN_VALUE) {
            clearAccessibilityFocus(i3);
        }
        this.mAccessibilityFocusedVirtualViewId = i2;
        this.mHost.invalidate();
        sendEventForVirtualView(i2, 32768);
        return true;
    }

    private void updateHoveredVirtualView(int i2) {
        int i3 = this.mHoveredVirtualViewId;
        if (i3 == i2) {
            return;
        }
        this.mHoveredVirtualViewId = i2;
        sendEventForVirtualView(i2, 128);
        sendEventForVirtualView(i3, 256);
    }

    public final boolean clearKeyboardFocusForVirtualView(int i2) {
        if (this.mKeyboardFocusedVirtualViewId != i2) {
            return false;
        }
        this.mKeyboardFocusedVirtualViewId = Integer.MIN_VALUE;
        onVirtualViewKeyboardFocusChanged(i2, false);
        sendEventForVirtualView(i2, 8);
        return true;
    }

    public final boolean dispatchHoverEvent(@NonNull MotionEvent motionEvent) {
        if (!this.mManager.isEnabled() || !this.mManager.isTouchExplorationEnabled()) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 7 || action == 9) {
            int virtualViewAt = getVirtualViewAt(motionEvent.getX(), motionEvent.getY());
            updateHoveredVirtualView(virtualViewAt);
            return virtualViewAt != Integer.MIN_VALUE;
        }
        if (action != 10 || this.mHoveredVirtualViewId == Integer.MIN_VALUE) {
            return false;
        }
        updateHoveredVirtualView(Integer.MIN_VALUE);
        return true;
    }

    public final boolean dispatchKeyEvent(@NonNull KeyEvent keyEvent) {
        int i2 = 0;
        if (keyEvent.getAction() == 1) {
            return false;
        }
        int keyCode = keyEvent.getKeyCode();
        if (keyCode == 61) {
            if (keyEvent.hasNoModifiers()) {
                return moveFocus(2, null);
            }
            if (keyEvent.hasModifiers(1)) {
                return moveFocus(1, null);
            }
            return false;
        }
        if (keyCode != 66) {
            switch (keyCode) {
                case 19:
                case 20:
                case 21:
                case 22:
                    if (!keyEvent.hasNoModifiers()) {
                        return false;
                    }
                    int iKeyToDirection = keyToDirection(keyCode);
                    int repeatCount = keyEvent.getRepeatCount() + 1;
                    boolean z2 = false;
                    while (i2 < repeatCount && moveFocus(iKeyToDirection, null)) {
                        i2++;
                        z2 = true;
                    }
                    return z2;
                case 23:
                    break;
                default:
                    return false;
            }
        }
        if (!keyEvent.hasNoModifiers() || keyEvent.getRepeatCount() != 0) {
            return false;
        }
        clickKeyboardFocusedVirtualView();
        return true;
    }

    public final int getAccessibilityFocusedVirtualViewId() {
        return this.mAccessibilityFocusedVirtualViewId;
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
        if (this.mNodeProvider == null) {
            this.mNodeProvider = new MyNodeProvider();
        }
        return this.mNodeProvider;
    }

    @Deprecated
    public int getFocusedVirtualView() {
        return getAccessibilityFocusedVirtualViewId();
    }

    public final int getKeyboardFocusedVirtualViewId() {
        return this.mKeyboardFocusedVirtualViewId;
    }

    public abstract int getVirtualViewAt(float f2, float f3);

    public abstract void getVisibleVirtualViews(List<Integer> list);

    public final void invalidateRoot() {
        invalidateVirtualView(-1, 1);
    }

    public final void invalidateVirtualView(int i2) {
        invalidateVirtualView(i2, 0);
    }

    @NonNull
    public AccessibilityNodeInfoCompat obtainAccessibilityNodeInfo(int i2) {
        return i2 == -1 ? createNodeForHost() : createNodeForChild(i2);
    }

    public final void onFocusChanged(boolean z2, int i2, @Nullable Rect rect) {
        int i3 = this.mKeyboardFocusedVirtualViewId;
        if (i3 != Integer.MIN_VALUE) {
            clearKeyboardFocusForVirtualView(i3);
        }
        if (z2) {
            moveFocus(i2, rect);
        }
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(view, accessibilityEvent);
        onPopulateEventForHost(accessibilityEvent);
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
        onPopulateNodeForHost(accessibilityNodeInfoCompat);
    }

    public abstract boolean onPerformActionForVirtualView(int i2, int i3, @Nullable Bundle bundle);

    public void onPopulateEventForHost(@NonNull AccessibilityEvent accessibilityEvent) {
    }

    public void onPopulateEventForVirtualView(int i2, @NonNull AccessibilityEvent accessibilityEvent) {
    }

    public void onPopulateNodeForHost(@NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
    }

    public abstract void onPopulateNodeForVirtualView(int i2, @NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat);

    public void onVirtualViewKeyboardFocusChanged(int i2, boolean z2) {
    }

    public boolean performAction(int i2, int i3, Bundle bundle) {
        return i2 != -1 ? performActionForChild(i2, i3, bundle) : performActionForHost(i3, bundle);
    }

    public final boolean requestKeyboardFocusForVirtualView(int i2) {
        int i3;
        if ((!this.mHost.isFocused() && !this.mHost.requestFocus()) || (i3 = this.mKeyboardFocusedVirtualViewId) == i2) {
            return false;
        }
        if (i3 != Integer.MIN_VALUE) {
            clearKeyboardFocusForVirtualView(i3);
        }
        if (i2 == Integer.MIN_VALUE) {
            return false;
        }
        this.mKeyboardFocusedVirtualViewId = i2;
        onVirtualViewKeyboardFocusChanged(i2, true);
        sendEventForVirtualView(i2, 8);
        return true;
    }

    public final boolean sendEventForVirtualView(int i2, int i3) {
        ViewParent parent;
        if (i2 == Integer.MIN_VALUE || !this.mManager.isEnabled() || (parent = this.mHost.getParent()) == null) {
            return false;
        }
        return parent.requestSendAccessibilityEvent(this.mHost, createEvent(i2, i3));
    }

    public final void invalidateVirtualView(int i2, int i3) {
        ViewParent parent;
        if (i2 == Integer.MIN_VALUE || !this.mManager.isEnabled() || (parent = this.mHost.getParent()) == null) {
            return;
        }
        AccessibilityEvent accessibilityEventCreateEvent = createEvent(i2, 2048);
        AccessibilityEventCompat.setContentChangeTypes(accessibilityEventCreateEvent, i3);
        parent.requestSendAccessibilityEvent(this.mHost, accessibilityEventCreateEvent);
    }
}
