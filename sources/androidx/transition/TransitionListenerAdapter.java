package androidx.transition;

import androidx.annotation.NonNull;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class TransitionListenerAdapter implements Transition.TransitionListener {
    @Override // androidx.transition.Transition.TransitionListener
    public void onTransitionCancel(@NonNull Transition transition) {
    }

    @Override // androidx.transition.Transition.TransitionListener
    public void onTransitionEnd(@NonNull Transition transition) {
    }

    @Override // androidx.transition.Transition.TransitionListener
    public void onTransitionPause(@NonNull Transition transition) {
    }

    @Override // androidx.transition.Transition.TransitionListener
    public void onTransitionResume(@NonNull Transition transition) {
    }

    @Override // androidx.transition.Transition.TransitionListener
    public void onTransitionStart(@NonNull Transition transition) {
    }

    @Override // androidx.transition.Transition.TransitionListener
    public final /* synthetic */ void onTransitionEnd(Transition transition, boolean z2) {
        c.a(this, transition, z2);
    }

    @Override // androidx.transition.Transition.TransitionListener
    public final /* synthetic */ void onTransitionStart(Transition transition, boolean z2) {
        c.b(this, transition, z2);
    }
}
