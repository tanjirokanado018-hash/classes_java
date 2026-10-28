package com.google.android.material.transition;

import androidx.transition.Transition;
import androidx.transition.c;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
abstract class TransitionListenerAdapter implements Transition.TransitionListener {
    @Override // androidx.transition.Transition.TransitionListener
    public void onTransitionCancel(Transition transition) {
    }

    @Override // androidx.transition.Transition.TransitionListener
    public void onTransitionEnd(Transition transition) {
    }

    @Override // androidx.transition.Transition.TransitionListener
    public void onTransitionPause(Transition transition) {
    }

    @Override // androidx.transition.Transition.TransitionListener
    public void onTransitionResume(Transition transition) {
    }

    @Override // androidx.transition.Transition.TransitionListener
    public void onTransitionStart(Transition transition) {
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
