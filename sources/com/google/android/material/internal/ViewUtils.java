package com.google.android.material.internal;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import com.google.android.material.drawable.DrawableUtils;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class ViewUtils {

    @RequiresApi(16)
    public static final int EDGE_TO_EDGE_FLAGS = 768;

    public interface OnApplyWindowInsetsListener {
        WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat, RelativePadding relativePadding);
    }

    public static class RelativePadding {
        public int bottom;
        public int end;
        public int start;
        public int top;

        public RelativePadding(int i2, int i3, int i4, int i5) {
            this.start = i2;
            this.top = i3;
            this.end = i4;
            this.bottom = i5;
        }

        public void applyToView(View view) {
            ViewCompat.setPaddingRelative(view, this.start, this.top, this.end, this.bottom);
        }

        public RelativePadding(@NonNull RelativePadding relativePadding) {
            this.start = relativePadding.start;
            this.top = relativePadding.top;
            this.end = relativePadding.end;
            this.bottom = relativePadding.bottom;
        }
    }

    private ViewUtils() {
    }

    public static void addOnGlobalLayoutListener(@Nullable View view, @NonNull ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        if (view != null) {
            view.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
        }
    }

    @NonNull
    public static Rect calculateOffsetRectFromBounds(@NonNull View view, @NonNull View view2) {
        int[] iArr = new int[2];
        view2.getLocationOnScreen(iArr);
        int i2 = iArr[0];
        int i3 = iArr[1];
        int[] iArr2 = new int[2];
        view.getLocationOnScreen(iArr2);
        int i4 = i2 - iArr2[0];
        int i5 = i3 - iArr2[1];
        return new Rect(i4, i5, view2.getWidth() + i4, view2.getHeight() + i5);
    }

    @NonNull
    public static Rect calculateRectFromBounds(@NonNull View view) {
        return calculateRectFromBounds(view, 0);
    }

    public static void doOnApplyWindowInsets(@NonNull View view, @Nullable AttributeSet attributeSet, int i2, int i3) {
        doOnApplyWindowInsets(view, attributeSet, i2, i3, null);
    }

    public static float dpToPx(@NonNull Context context, @Dimension(unit = 0) int i2) {
        return TypedValue.applyDimension(1, i2, context.getResources().getDisplayMetrics());
    }

    @Nullable
    public static Integer getBackgroundColor(@NonNull View view) {
        ColorStateList colorStateListOrNull = DrawableUtils.getColorStateListOrNull(view.getBackground());
        if (colorStateListOrNull != null) {
            return Integer.valueOf(colorStateListOrNull.getDefaultColor());
        }
        return null;
    }

    @NonNull
    public static List<View> getChildren(@Nullable View view) {
        ArrayList arrayList = new ArrayList();
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
                arrayList.add(viewGroup.getChildAt(i2));
            }
        }
        return arrayList;
    }

    @Nullable
    public static ViewGroup getContentView(@Nullable View view) {
        if (view == null) {
            return null;
        }
        View rootView = view.getRootView();
        ViewGroup viewGroup = (ViewGroup) rootView.findViewById(R.id.content);
        if (viewGroup != null) {
            return viewGroup;
        }
        if (rootView == view || !(rootView instanceof ViewGroup)) {
            return null;
        }
        return (ViewGroup) rootView;
    }

    @Nullable
    public static ViewOverlayImpl getContentViewOverlay(@NonNull View view) {
        return getOverlay(getContentView(view));
    }

    @Nullable
    private static InputMethodManager getInputMethodManager(@NonNull View view) {
        return (InputMethodManager) ContextCompat.getSystemService(view.getContext(), InputMethodManager.class);
    }

    @Nullable
    public static ViewOverlayImpl getOverlay(@Nullable View view) {
        if (view == null) {
            return null;
        }
        return new ViewOverlayApi18(view);
    }

    public static float getParentAbsoluteElevation(@NonNull View view) {
        float elevation = 0.0f;
        for (ViewParent parent = view.getParent(); parent instanceof View; parent = parent.getParent()) {
            elevation += ViewCompat.getElevation((View) parent);
        }
        return elevation;
    }

    public static void hideKeyboard(@NonNull View view) {
        hideKeyboard(view, true);
    }

    public static boolean isLayoutRtl(View view) {
        return ViewCompat.getLayoutDirection(view) == 1;
    }

    public static PorterDuff.Mode parseTintMode(int i2, PorterDuff.Mode mode) {
        if (i2 == 3) {
            return PorterDuff.Mode.SRC_OVER;
        }
        if (i2 == 5) {
            return PorterDuff.Mode.SRC_IN;
        }
        if (i2 == 9) {
            return PorterDuff.Mode.SRC_ATOP;
        }
        switch (i2) {
            case 14:
                return PorterDuff.Mode.MULTIPLY;
            case 15:
                return PorterDuff.Mode.SCREEN;
            case 16:
                return PorterDuff.Mode.ADD;
            default:
                return mode;
        }
    }

    public static void removeOnGlobalLayoutListener(@Nullable View view, @NonNull ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        if (view != null) {
            removeOnGlobalLayoutListener(view.getViewTreeObserver(), onGlobalLayoutListener);
        }
    }

    public static void requestApplyInsetsWhenAttached(@NonNull View view) {
        if (ViewCompat.isAttachedToWindow(view)) {
            ViewCompat.requestApplyInsets(view);
        } else {
            view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.google.android.material.internal.ViewUtils.3
                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewAttachedToWindow(@NonNull View view2) {
                    view2.removeOnAttachStateChangeListener(this);
                    ViewCompat.requestApplyInsets(view2);
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewDetachedFromWindow(View view2) {
                }
            });
        }
    }

    public static void requestFocusAndShowKeyboard(@NonNull View view) {
        requestFocusAndShowKeyboard(view, true);
    }

    public static void setBoundsFromRect(@NonNull View view, @NonNull Rect rect) {
        view.setLeft(rect.left);
        view.setTop(rect.top);
        view.setRight(rect.right);
        view.setBottom(rect.bottom);
    }

    public static void showKeyboard(@NonNull View view) {
        showKeyboard(view, true);
    }

    @NonNull
    public static Rect calculateRectFromBounds(@NonNull View view, int i2) {
        return new Rect(view.getLeft(), view.getTop() + i2, view.getRight(), view.getBottom() + i2);
    }

    public static void doOnApplyWindowInsets(@NonNull View view, @Nullable AttributeSet attributeSet, int i2, int i3, @Nullable final OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
        TypedArray typedArrayObtainStyledAttributes = view.getContext().obtainStyledAttributes(attributeSet, com.google.android.material.R.styleable.Insets, i2, i3);
        final boolean z2 = typedArrayObtainStyledAttributes.getBoolean(com.google.android.material.R.styleable.Insets_paddingBottomSystemWindowInsets, false);
        final boolean z3 = typedArrayObtainStyledAttributes.getBoolean(com.google.android.material.R.styleable.Insets_paddingLeftSystemWindowInsets, false);
        final boolean z4 = typedArrayObtainStyledAttributes.getBoolean(com.google.android.material.R.styleable.Insets_paddingRightSystemWindowInsets, false);
        typedArrayObtainStyledAttributes.recycle();
        doOnApplyWindowInsets(view, new OnApplyWindowInsetsListener() { // from class: com.google.android.material.internal.ViewUtils.1
            @Override // com.google.android.material.internal.ViewUtils.OnApplyWindowInsetsListener
            @NonNull
            public WindowInsetsCompat onApplyWindowInsets(View view2, @NonNull WindowInsetsCompat windowInsetsCompat, @NonNull RelativePadding relativePadding) {
                if (z2) {
                    relativePadding.bottom = windowInsetsCompat.getSystemWindowInsetBottom() + relativePadding.bottom;
                }
                boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(view2);
                if (z3) {
                    if (zIsLayoutRtl) {
                        relativePadding.end = windowInsetsCompat.getSystemWindowInsetLeft() + relativePadding.end;
                    } else {
                        relativePadding.start = windowInsetsCompat.getSystemWindowInsetLeft() + relativePadding.start;
                    }
                }
                if (z4) {
                    if (zIsLayoutRtl) {
                        relativePadding.start = windowInsetsCompat.getSystemWindowInsetRight() + relativePadding.start;
                    } else {
                        relativePadding.end = windowInsetsCompat.getSystemWindowInsetRight() + relativePadding.end;
                    }
                }
                relativePadding.applyToView(view2);
                OnApplyWindowInsetsListener onApplyWindowInsetsListener2 = onApplyWindowInsetsListener;
                return onApplyWindowInsetsListener2 != null ? onApplyWindowInsetsListener2.onApplyWindowInsets(view2, windowInsetsCompat, relativePadding) : windowInsetsCompat;
            }
        });
    }

    public static void hideKeyboard(@NonNull View view, boolean z2) {
        WindowInsetsControllerCompat windowInsetsController;
        if (z2 && (windowInsetsController = ViewCompat.getWindowInsetsController(view)) != null) {
            windowInsetsController.hide(WindowInsetsCompat.Type.ime());
            return;
        }
        InputMethodManager inputMethodManager = getInputMethodManager(view);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void removeOnGlobalLayoutListener(@NonNull ViewTreeObserver viewTreeObserver, @NonNull ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    public static void requestFocusAndShowKeyboard(@NonNull final View view, final boolean z2) {
        view.requestFocus();
        view.post(new Runnable() { // from class: com.google.android.material.internal.c
            @Override // java.lang.Runnable
            public final void run() {
                ViewUtils.showKeyboard(view, z2);
            }
        });
    }

    public static void showKeyboard(@NonNull View view, boolean z2) {
        WindowInsetsControllerCompat windowInsetsController;
        if (!z2 || (windowInsetsController = ViewCompat.getWindowInsetsController(view)) == null) {
            getInputMethodManager(view).showSoftInput(view, 1);
        } else {
            windowInsetsController.show(WindowInsetsCompat.Type.ime());
        }
    }

    public static void doOnApplyWindowInsets(@NonNull View view, @NonNull final OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
        final RelativePadding relativePadding = new RelativePadding(ViewCompat.getPaddingStart(view), view.getPaddingTop(), ViewCompat.getPaddingEnd(view), view.getPaddingBottom());
        ViewCompat.setOnApplyWindowInsetsListener(view, new androidx.core.view.OnApplyWindowInsetsListener() { // from class: com.google.android.material.internal.ViewUtils.2
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat) {
                return onApplyWindowInsetsListener.onApplyWindowInsets(view2, windowInsetsCompat, new RelativePadding(relativePadding));
            }
        });
        requestApplyInsetsWhenAttached(view);
    }
}
