package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class ChangeClipBounds extends Transition {
    private static final String PROPNAME_BOUNDS = "android:clipBounds:bounds";
    private static final String PROPNAME_CLIP = "android:clipBounds:clip";
    private static final String[] sTransitionProperties = {PROPNAME_CLIP};
    static final Rect NULL_SENTINEL = new Rect();

    public static class Listener extends AnimatorListenerAdapter implements Transition.TransitionListener {
        private final Rect mEnd;
        private final Rect mStart;
        private final View mView;

        public Listener(View view, Rect rect, Rect rect2) {
            this.mView = view;
            this.mStart = rect;
            this.mEnd = rect2;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            onAnimationEnd(animator, false);
        }

        @Override // androidx.transition.Transition.TransitionListener
        public void onTransitionCancel(@NonNull Transition transition) {
        }

        @Override // androidx.transition.Transition.TransitionListener
        public void onTransitionEnd(@NonNull Transition transition) {
        }

        @Override // androidx.transition.Transition.TransitionListener
        public void onTransitionPause(@NonNull Transition transition) {
            Rect clipBounds = this.mView.getClipBounds();
            if (clipBounds == null) {
                clipBounds = ChangeClipBounds.NULL_SENTINEL;
            }
            this.mView.setTag(R.id.transition_clip, clipBounds);
            this.mView.setClipBounds(this.mEnd);
        }

        @Override // androidx.transition.Transition.TransitionListener
        public void onTransitionResume(@NonNull Transition transition) {
            this.mView.setClipBounds((Rect) this.mView.getTag(R.id.transition_clip));
            this.mView.setTag(R.id.transition_clip, null);
        }

        @Override // androidx.transition.Transition.TransitionListener
        public void onTransitionStart(@NonNull Transition transition) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator, boolean z2) {
            this.mView.setClipBounds(!z2 ? this.mEnd : this.mStart);
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

    public ChangeClipBounds() {
    }

    private void captureValues(TransitionValues transitionValues, boolean z2) {
        View view = transitionValues.view;
        if (view.getVisibility() == 8) {
            return;
        }
        Rect clipBounds = z2 ? (Rect) view.getTag(R.id.transition_clip) : null;
        if (clipBounds == null) {
            clipBounds = view.getClipBounds();
        }
        Rect rect = clipBounds != NULL_SENTINEL ? clipBounds : null;
        transitionValues.values.put(PROPNAME_CLIP, rect);
        if (rect == null) {
            transitionValues.values.put(PROPNAME_BOUNDS, new Rect(0, 0, view.getWidth(), view.getHeight()));
        }
    }

    @Override // androidx.transition.Transition
    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        captureValues(transitionValues, false);
    }

    @Override // androidx.transition.Transition
    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        captureValues(transitionValues, true);
    }

    @Override // androidx.transition.Transition
    @Nullable
    public Animator createAnimator(@NonNull ViewGroup viewGroup, @Nullable TransitionValues transitionValues, @Nullable TransitionValues transitionValues2) {
        if (transitionValues == null || transitionValues2 == null || !transitionValues.values.containsKey(PROPNAME_CLIP) || !transitionValues2.values.containsKey(PROPNAME_CLIP)) {
            return null;
        }
        Rect rect = (Rect) transitionValues.values.get(PROPNAME_CLIP);
        Rect rect2 = (Rect) transitionValues2.values.get(PROPNAME_CLIP);
        if (rect == null && rect2 == null) {
            return null;
        }
        Rect rect3 = rect == null ? (Rect) transitionValues.values.get(PROPNAME_BOUNDS) : rect;
        Rect rect4 = rect2 == null ? (Rect) transitionValues2.values.get(PROPNAME_BOUNDS) : rect2;
        if (rect3.equals(rect4)) {
            return null;
        }
        transitionValues2.view.setClipBounds(rect);
        ObjectAnimator objectAnimatorOfObject = ObjectAnimator.ofObject(transitionValues2.view, (Property<View, V>) ViewUtils.CLIP_BOUNDS, (TypeEvaluator) new RectEvaluator(new Rect()), (Object[]) new Rect[]{rect3, rect4});
        Listener listener = new Listener(transitionValues2.view, rect, rect2);
        objectAnimatorOfObject.addListener(listener);
        addListener(listener);
        return objectAnimatorOfObject;
    }

    @Override // androidx.transition.Transition
    @NonNull
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    @Override // androidx.transition.Transition
    public boolean isSeekingSupported() {
        return true;
    }

    public ChangeClipBounds(@NonNull Context context, @NonNull AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
