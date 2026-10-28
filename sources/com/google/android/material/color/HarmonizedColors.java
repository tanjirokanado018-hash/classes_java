package com.google.android.material.color;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build;
import android.view.ContextThemeWrapper;
import androidx.annotation.ChecksSdkIntAtLeast;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import com.google.android.material.R;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class HarmonizedColors {
    private static final String TAG = "HarmonizedColors";

    private HarmonizedColors() {
    }

    @RequiresApi(api = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_BIAS)
    private static void addHarmonizedColorAttributesToReplacementMap(@NonNull Map<Integer, Integer> map, @NonNull TypedArray typedArray, @Nullable TypedArray typedArray2, @ColorInt int i2) {
        if (typedArray2 == null) {
            typedArray2 = typedArray;
        }
        for (int i3 = 0; i3 < typedArray.getIndexCount(); i3++) {
            int resourceId = typedArray2.getResourceId(i3, 0);
            if (resourceId != 0 && typedArray.hasValue(i3) && ResourcesLoaderUtils.isColorResource(typedArray.getType(i3))) {
                map.put(Integer.valueOf(resourceId), Integer.valueOf(MaterialColors.harmonize(typedArray.getColor(i3, 0), i2)));
            }
        }
    }

    @NonNull
    public static void applyToContextIfAvailable(@NonNull Context context, @NonNull HarmonizedColorsOptions harmonizedColorsOptions) {
        if (isHarmonizedColorAvailable()) {
            Map<Integer, Integer> mapCreateHarmonizedColorReplacementMap = createHarmonizedColorReplacementMap(context, harmonizedColorsOptions);
            int themeOverlayResourceId = harmonizedColorsOptions.getThemeOverlayResourceId(0);
            if (!ResourcesLoaderUtils.addResourcesLoaderToContext(context, mapCreateHarmonizedColorReplacementMap) || themeOverlayResourceId == 0) {
                return;
            }
            ThemeUtils.applyThemeOverlay(context, themeOverlayResourceId);
        }
    }

    @RequiresApi(api = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_BIAS)
    private static Map<Integer, Integer> createHarmonizedColorReplacementMap(Context context, HarmonizedColorsOptions harmonizedColorsOptions) {
        HashMap map = new HashMap();
        int color = MaterialColors.getColor(context, harmonizedColorsOptions.getColorAttributeToHarmonizeWith(), TAG);
        for (int i2 : harmonizedColorsOptions.getColorResourceIds()) {
            map.put(Integer.valueOf(i2), Integer.valueOf(MaterialColors.harmonize(ContextCompat.getColor(context, i2), color)));
        }
        HarmonizedColorAttributes colorAttributes = harmonizedColorsOptions.getColorAttributes();
        if (colorAttributes != null) {
            int[] attributes = colorAttributes.getAttributes();
            if (attributes.length > 0) {
                int themeOverlay = colorAttributes.getThemeOverlay();
                TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributes);
                TypedArray typedArrayObtainStyledAttributes2 = themeOverlay != 0 ? new ContextThemeWrapper(context, themeOverlay).obtainStyledAttributes(attributes) : null;
                addHarmonizedColorAttributesToReplacementMap(map, typedArrayObtainStyledAttributes, typedArrayObtainStyledAttributes2, color);
                typedArrayObtainStyledAttributes.recycle();
                if (typedArrayObtainStyledAttributes2 != null) {
                    typedArrayObtainStyledAttributes2.recycle();
                }
            }
        }
        return map;
    }

    @ChecksSdkIntAtLeast(api = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_BIAS)
    public static boolean isHarmonizedColorAvailable() {
        return Build.VERSION.SDK_INT >= 30;
    }

    @NonNull
    public static Context wrapContextIfAvailable(@NonNull Context context, @NonNull HarmonizedColorsOptions harmonizedColorsOptions) {
        if (!isHarmonizedColorAvailable()) {
            return context;
        }
        Map<Integer, Integer> mapCreateHarmonizedColorReplacementMap = createHarmonizedColorReplacementMap(context, harmonizedColorsOptions);
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, harmonizedColorsOptions.getThemeOverlayResourceId(R.style.ThemeOverlay_Material3_HarmonizedColors_Empty));
        contextThemeWrapper.applyOverrideConfiguration(new Configuration());
        return ResourcesLoaderUtils.addResourcesLoaderToContext(contextThemeWrapper, mapCreateHarmonizedColorReplacementMap) ? contextThemeWrapper : context;
    }
}
