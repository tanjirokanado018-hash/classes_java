package com.google.android.material.resources;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import androidx.annotation.AttrRes;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.RestrictTo;
import com.google.android.material.R;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class MaterialAttributes {
    @Nullable
    public static TypedValue resolve(@NonNull Context context, @AttrRes int i2) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(i2, typedValue, true)) {
            return typedValue;
        }
        return null;
    }

    public static boolean resolveBoolean(@NonNull Context context, @AttrRes int i2, boolean z2) {
        TypedValue typedValueResolve = resolve(context, i2);
        if (typedValueResolve == null || typedValueResolve.type != 18) {
            return z2;
        }
        return typedValueResolve.data != 0;
    }

    public static boolean resolveBooleanOrThrow(@NonNull Context context, @AttrRes int i2, @NonNull String str) {
        return resolveOrThrow(context, i2, str) != 0;
    }

    @Px
    public static int resolveDimension(@NonNull Context context, @AttrRes int i2, @DimenRes int i3) {
        TypedValue typedValueResolve = resolve(context, i2);
        return (int) ((typedValueResolve == null || typedValueResolve.type != 5) ? context.getResources().getDimension(i3) : typedValueResolve.getDimension(context.getResources().getDisplayMetrics()));
    }

    public static int resolveInteger(@NonNull Context context, @AttrRes int i2, int i3) {
        TypedValue typedValueResolve = resolve(context, i2);
        return (typedValueResolve == null || typedValueResolve.type != 16) ? i3 : typedValueResolve.data;
    }

    @Px
    public static int resolveMinimumAccessibleTouchTarget(@NonNull Context context) {
        return resolveDimension(context, R.attr.minTouchTargetSize, R.dimen.mtrl_min_touch_target_size);
    }

    public static int resolveOrThrow(@NonNull Context context, @AttrRes int i2, @NonNull String str) {
        return resolveTypedValueOrThrow(context, i2, str).data;
    }

    @NonNull
    public static TypedValue resolveTypedValueOrThrow(@NonNull Context context, @AttrRes int i2, @NonNull String str) {
        TypedValue typedValueResolve = resolve(context, i2);
        if (typedValueResolve != null) {
            return typedValueResolve;
        }
        throw new IllegalArgumentException(String.format("%1$s requires a value for the %2$s attribute to be set in your app theme. You can either set the attribute in your theme or update your theme to inherit from Theme.MaterialComponents (or a descendant).", str, context.getResources().getResourceName(i2)));
    }

    public static int resolveOrThrow(@NonNull View view, @AttrRes int i2) {
        return resolveTypedValueOrThrow(view, i2).data;
    }

    @NonNull
    public static TypedValue resolveTypedValueOrThrow(@NonNull View view, @AttrRes int i2) {
        return resolveTypedValueOrThrow(view.getContext(), i2, view.getClass().getCanonicalName());
    }
}
