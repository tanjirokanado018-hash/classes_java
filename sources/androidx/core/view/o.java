package androidx.core.view;

import android.view.WindowInsets;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.contentcapture.ContentCaptureSession;
import java.util.Map;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract /* synthetic */ class o {
    public static /* synthetic */ WindowInsets.Builder d() {
        return new WindowInsets.Builder();
    }

    public static /* synthetic */ WindowInsets.Builder e(WindowInsets windowInsets) {
        return new WindowInsets.Builder(windowInsets);
    }

    public static /* synthetic */ AccessibilityNodeInfo.TouchDelegateInfo j(Map map) {
        return new AccessibilityNodeInfo.TouchDelegateInfo(map);
    }

    public static /* bridge */ /* synthetic */ ContentCaptureSession l(Object obj) {
        return (ContentCaptureSession) obj;
    }
}
