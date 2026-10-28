package com.google.android.material.search;

import android.animation.Animator;
import androidx.core.view.accessibility.AccessibilityManagerCompat;
import com.google.android.material.animation.AnimatableView;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class a implements AccessibilityManagerCompat.TouchExplorationStateChangeListener, AnimatableView.Listener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ Object f113a;

    public /* synthetic */ a(Object obj) {
        this.f113a = obj;
    }

    @Override // com.google.android.material.animation.AnimatableView.Listener
    public final void onAnimationEnd() {
        ((Animator) this.f113a).start();
    }

    @Override // androidx.core.view.accessibility.AccessibilityManagerCompat.TouchExplorationStateChangeListener
    public final void onTouchExplorationStateChanged(boolean z2) {
        ((SearchBar) this.f113a).lambda$new$0(z2);
    }
}
