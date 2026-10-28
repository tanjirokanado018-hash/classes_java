package com.google.android.material.color;

import android.content.Context;
import android.content.res.loader.ResourcesLoader;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.Map;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RequiresApi(api = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_BIAS)
final class ResourcesLoaderUtils {
    private ResourcesLoaderUtils() {
    }

    public static boolean addResourcesLoaderToContext(Context context, Map<Integer, Integer> map) throws Throwable {
        ResourcesLoader resourcesLoaderCreate = ColorResourcesLoaderCreator.create(context, map);
        if (resourcesLoaderCreate == null) {
            return false;
        }
        context.getResources().addLoaders(resourcesLoaderCreate);
        return true;
    }

    public static boolean isColorResource(int i2) {
        return 28 <= i2 && i2 <= 31;
    }
}
