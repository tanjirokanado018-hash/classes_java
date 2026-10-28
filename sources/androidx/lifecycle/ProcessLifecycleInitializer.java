package androidx.lifecycle;

import android.content.Context;
import androidx.startup.AppInitializer;
import androidx.startup.Initializer;
import java.util.List;
import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class ProcessLifecycleInitializer implements Initializer<LifecycleOwner> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.startup.Initializer
    public LifecycleOwner create(Context context) {
        k.g(context, "context");
        AppInitializer appInitializer = AppInitializer.getInstance(context);
        k.f(appInitializer, "getInstance(context)");
        if (!appInitializer.isEagerlyInitialized(ProcessLifecycleInitializer.class)) {
            throw new IllegalStateException("ProcessLifecycleInitializer cannot be initialized lazily.\n               Please ensure that you have:\n               <meta-data\n                   android:name='androidx.lifecycle.ProcessLifecycleInitializer'\n                   android:value='androidx.startup' />\n               under InitializationProvider in your AndroidManifest.xml".toString());
        }
        LifecycleDispatcher.init(context);
        ProcessLifecycleOwner.Companion companion = ProcessLifecycleOwner.Companion;
        companion.init$lifecycle_process_release(context);
        return companion.get();
    }

    @Override // androidx.startup.Initializer
    public List<Class<? extends Initializer<?>>> dependencies() {
        return h.g.b;
    }
}
