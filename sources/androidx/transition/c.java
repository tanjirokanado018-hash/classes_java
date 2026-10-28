package androidx.transition;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract /* synthetic */ class c {
    public static void a(Transition.TransitionListener transitionListener, Transition transition, boolean z2) {
        transitionListener.onTransitionEnd(transition);
    }

    public static void b(Transition.TransitionListener transitionListener, Transition transition, boolean z2) {
        transitionListener.onTransitionStart(transition);
    }
}
