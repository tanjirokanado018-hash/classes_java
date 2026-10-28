package com.google.android.material.color;

import android.content.Context;
import android.content.res.Configuration;
import android.view.ContextThemeWrapper;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.R;
import java.util.Map;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RequiresApi(api = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_BIAS)
class ResourcesLoaderColorResourcesOverride implements ColorResourcesOverride {

    public static class ResourcesLoaderColorResourcesOverrideSingleton {
        private static final ResourcesLoaderColorResourcesOverride INSTANCE = new ResourcesLoaderColorResourcesOverride();

        private ResourcesLoaderColorResourcesOverrideSingleton() {
        }
    }

    private ResourcesLoaderColorResourcesOverride() {
    }

    public static ColorResourcesOverride getInstance() {
        return ResourcesLoaderColorResourcesOverrideSingleton.INSTANCE;
    }

    @Override // com.google.android.material.color.ColorResourcesOverride
    public boolean applyIfPossible(Context context, Map<Integer, Integer> map) {
        if (!ResourcesLoaderUtils.addResourcesLoaderToContext(context, map)) {
            return false;
        }
        ThemeUtils.applyThemeOverlay(context, R.style.ThemeOverlay_Material3_PersonalizedColors);
        return true;
    }

    @Override // com.google.android.material.color.ColorResourcesOverride
    @NonNull
    public Context wrapContextIfPossible(Context context, Map<Integer, Integer> map) {
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, R.style.ThemeOverlay_Material3_PersonalizedColors);
        contextThemeWrapper.applyOverrideConfiguration(new Configuration());
        return ResourcesLoaderUtils.addResourcesLoaderToContext(contextThemeWrapper, map) ? contextThemeWrapper : context;
    }
}
