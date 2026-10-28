package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.core.app.SharedElementCallback;
import androidx.core.os.CancellationSignal;
import androidx.core.util.Preconditions;
import androidx.core.view.OneShotPreDrawListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewGroupCompat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
class DefaultSpecialEffectsController extends SpecialEffectsController {

    /* JADX INFO: renamed from: androidx.fragment.app.DefaultSpecialEffectsController$10, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass10 {
        static final /* synthetic */ int[] $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State;

        static {
            int[] iArr = new int[SpecialEffectsController.Operation.State.values().length];
            $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State = iArr;
            try {
                iArr[SpecialEffectsController.Operation.State.GONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State[SpecialEffectsController.Operation.State.INVISIBLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State[SpecialEffectsController.Operation.State.REMOVED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State[SpecialEffectsController.Operation.State.VISIBLE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public static class AnimationInfo extends SpecialEffectsInfo {

        @Nullable
        private FragmentAnim.AnimationOrAnimator mAnimation;
        private boolean mIsPop;
        private boolean mLoadedAnim;

        public AnimationInfo(@NonNull SpecialEffectsController.Operation operation, @NonNull CancellationSignal cancellationSignal, boolean z2) {
            super(operation, cancellationSignal);
            this.mLoadedAnim = false;
            this.mIsPop = z2;
        }

        @Nullable
        public FragmentAnim.AnimationOrAnimator getAnimation(@NonNull Context context) {
            if (this.mLoadedAnim) {
                return this.mAnimation;
            }
            FragmentAnim.AnimationOrAnimator animationOrAnimatorLoadAnimation = FragmentAnim.loadAnimation(context, getOperation().getFragment(), getOperation().getFinalState() == SpecialEffectsController.Operation.State.VISIBLE, this.mIsPop);
            this.mAnimation = animationOrAnimatorLoadAnimation;
            this.mLoadedAnim = true;
            return animationOrAnimatorLoadAnimation;
        }
    }

    public static class SpecialEffectsInfo {

        @NonNull
        private final SpecialEffectsController.Operation mOperation;

        @NonNull
        private final CancellationSignal mSignal;

        public SpecialEffectsInfo(@NonNull SpecialEffectsController.Operation operation, @NonNull CancellationSignal cancellationSignal) {
            this.mOperation = operation;
            this.mSignal = cancellationSignal;
        }

        public void completeSpecialEffect() {
            this.mOperation.completeSpecialEffect(this.mSignal);
        }

        @NonNull
        public SpecialEffectsController.Operation getOperation() {
            return this.mOperation;
        }

        @NonNull
        public CancellationSignal getSignal() {
            return this.mSignal;
        }

        public boolean isVisibilityUnchanged() {
            SpecialEffectsController.Operation.State state;
            SpecialEffectsController.Operation.State stateFrom = SpecialEffectsController.Operation.State.from(this.mOperation.getFragment().mView);
            SpecialEffectsController.Operation.State finalState = this.mOperation.getFinalState();
            return stateFrom == finalState || !(stateFrom == (state = SpecialEffectsController.Operation.State.VISIBLE) || finalState == state);
        }
    }

    public static class TransitionInfo extends SpecialEffectsInfo {
        private final boolean mOverlapAllowed;

        @Nullable
        private final Object mSharedElementTransition;

        @Nullable
        private final Object mTransition;

        public TransitionInfo(@NonNull SpecialEffectsController.Operation operation, @NonNull CancellationSignal cancellationSignal, boolean z2, boolean z3) {
            boolean allowReturnTransitionOverlap;
            Object sharedElementReturnTransition;
            super(operation, cancellationSignal);
            if (operation.getFinalState() == SpecialEffectsController.Operation.State.VISIBLE) {
                Fragment fragment = operation.getFragment();
                this.mTransition = z2 ? fragment.getReenterTransition() : fragment.getEnterTransition();
                Fragment fragment2 = operation.getFragment();
                allowReturnTransitionOverlap = z2 ? fragment2.getAllowReturnTransitionOverlap() : fragment2.getAllowEnterTransitionOverlap();
            } else {
                Fragment fragment3 = operation.getFragment();
                this.mTransition = z2 ? fragment3.getReturnTransition() : fragment3.getExitTransition();
                allowReturnTransitionOverlap = true;
            }
            this.mOverlapAllowed = allowReturnTransitionOverlap;
            if (z3) {
                Fragment fragment4 = operation.getFragment();
                sharedElementReturnTransition = z2 ? fragment4.getSharedElementReturnTransition() : fragment4.getSharedElementEnterTransition();
            } else {
                sharedElementReturnTransition = null;
            }
            this.mSharedElementTransition = sharedElementReturnTransition;
        }

        @Nullable
        public FragmentTransitionImpl getHandlingImpl() {
            FragmentTransitionImpl handlingImpl = getHandlingImpl(this.mTransition);
            FragmentTransitionImpl handlingImpl2 = getHandlingImpl(this.mSharedElementTransition);
            if (handlingImpl == null || handlingImpl2 == null || handlingImpl == handlingImpl2) {
                return handlingImpl != null ? handlingImpl : handlingImpl2;
            }
            throw new IllegalArgumentException("Mixing framework transitions and AndroidX transitions is not allowed. Fragment " + getOperation().getFragment() + " returned Transition " + this.mTransition + " which uses a different Transition  type than its shared element transition " + this.mSharedElementTransition);
        }

        @Nullable
        public Object getSharedElementTransition() {
            return this.mSharedElementTransition;
        }

        @Nullable
        public Object getTransition() {
            return this.mTransition;
        }

        public boolean hasSharedElementTransition() {
            return this.mSharedElementTransition != null;
        }

        public boolean isOverlapAllowed() {
            return this.mOverlapAllowed;
        }

        @Nullable
        private FragmentTransitionImpl getHandlingImpl(Object obj) {
            if (obj == null) {
                return null;
            }
            FragmentTransitionImpl fragmentTransitionImpl = FragmentTransition.PLATFORM_IMPL;
            if (fragmentTransitionImpl != null && fragmentTransitionImpl.canHandle(obj)) {
                return fragmentTransitionImpl;
            }
            FragmentTransitionImpl fragmentTransitionImpl2 = FragmentTransition.SUPPORT_IMPL;
            if (fragmentTransitionImpl2 != null && fragmentTransitionImpl2.canHandle(obj)) {
                return fragmentTransitionImpl2;
            }
            throw new IllegalArgumentException("Transition " + obj + " for fragment " + getOperation().getFragment() + " is not a valid framework Transition or AndroidX Transition");
        }
    }

    public DefaultSpecialEffectsController(@NonNull ViewGroup viewGroup) {
        super(viewGroup);
    }

    /* JADX WARN: Code duplicated, block: B:39:0x00cd  */
    private void startAnimations(@NonNull List<AnimationInfo> list, @NonNull List<SpecialEffectsController.Operation> list2, boolean z2, @NonNull Map<SpecialEffectsController.Operation, Boolean> map) {
        int i2;
        FragmentAnim.AnimationOrAnimator animation;
        final ViewGroup container = getContainer();
        Context context = container.getContext();
        ArrayList<AnimationInfo> arrayList = new ArrayList();
        Iterator<AnimationInfo> it = list.iterator();
        boolean z3 = false;
        while (true) {
            i2 = 2;
            if (!it.hasNext()) {
                break;
            }
            final AnimationInfo next = it.next();
            if (next.isVisibilityUnchanged() || (animation = next.getAnimation(context)) == null) {
                next.completeSpecialEffect();
            } else {
                final Animator animator = animation.animator;
                if (animator == null) {
                    arrayList.add(next);
                } else {
                    final SpecialEffectsController.Operation operation = next.getOperation();
                    Fragment fragment = operation.getFragment();
                    if (Boolean.TRUE.equals(map.get(operation))) {
                        if (FragmentManager.isLoggingEnabled(2)) {
                            Objects.toString(fragment);
                        }
                        next.completeSpecialEffect();
                    } else {
                        final boolean z4 = operation.getFinalState() == SpecialEffectsController.Operation.State.GONE;
                        if (z4) {
                            list2.remove(operation);
                        }
                        final View view = fragment.mView;
                        container.startViewTransition(view);
                        animator.addListener(new AnimatorListenerAdapter() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.2
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public void onAnimationEnd(Animator animator2) {
                                container.endViewTransition(view);
                                if (z4) {
                                    operation.getFinalState().applyState(view);
                                }
                                next.completeSpecialEffect();
                                if (FragmentManager.isLoggingEnabled(2)) {
                                    Objects.toString(operation);
                                }
                            }
                        });
                        animator.setTarget(view);
                        animator.start();
                        if (FragmentManager.isLoggingEnabled(2)) {
                            operation.toString();
                        }
                        next.getSignal().setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.3
                            @Override // androidx.core.os.CancellationSignal.OnCancelListener
                            public void onCancel() {
                                animator.end();
                                if (FragmentManager.isLoggingEnabled(2)) {
                                    Objects.toString(operation);
                                }
                            }
                        });
                        z3 = true;
                    }
                }
            }
        }
        for (final AnimationInfo animationInfo : arrayList) {
            final SpecialEffectsController.Operation operation2 = animationInfo.getOperation();
            Fragment fragment2 = operation2.getFragment();
            if (z2) {
                if (FragmentManager.isLoggingEnabled(i2)) {
                    Objects.toString(fragment2);
                }
                animationInfo.completeSpecialEffect();
            } else if (z3) {
                if (FragmentManager.isLoggingEnabled(i2)) {
                    Objects.toString(fragment2);
                }
                animationInfo.completeSpecialEffect();
            } else {
                final View view2 = fragment2.mView;
                Animation animation2 = (Animation) Preconditions.checkNotNull(((FragmentAnim.AnimationOrAnimator) Preconditions.checkNotNull(animationInfo.getAnimation(context))).animation);
                if (operation2.getFinalState() != SpecialEffectsController.Operation.State.REMOVED) {
                    view2.startAnimation(animation2);
                    animationInfo.completeSpecialEffect();
                } else {
                    container.startViewTransition(view2);
                    FragmentAnim.EndViewTransitionAnimation endViewTransitionAnimation = new FragmentAnim.EndViewTransitionAnimation(animation2, container, view2);
                    endViewTransitionAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.4
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation3) {
                            container.post(new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.4.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    AnonymousClass4 anonymousClass4 = AnonymousClass4.this;
                                    container.endViewTransition(view2);
                                    animationInfo.completeSpecialEffect();
                                }
                            });
                            if (FragmentManager.isLoggingEnabled(2)) {
                                Objects.toString(operation2);
                            }
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation3) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation3) {
                            if (FragmentManager.isLoggingEnabled(2)) {
                                Objects.toString(operation2);
                            }
                        }
                    });
                    view2.startAnimation(endViewTransitionAnimation);
                    i2 = 2;
                    if (FragmentManager.isLoggingEnabled(2)) {
                        operation2.toString();
                    }
                }
                animationInfo.getSignal().setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.5
                    @Override // androidx.core.os.CancellationSignal.OnCancelListener
                    public void onCancel() {
                        view2.clearAnimation();
                        container.endViewTransition(view2);
                        animationInfo.completeSpecialEffect();
                        if (FragmentManager.isLoggingEnabled(2)) {
                            Objects.toString(operation2);
                        }
                    }
                });
            }
        }
    }

    @NonNull
    private Map<SpecialEffectsController.Operation, Boolean> startTransitions(@NonNull List<TransitionInfo> list, @NonNull List<SpecialEffectsController.Operation> list2, final boolean z2, @Nullable final SpecialEffectsController.Operation operation, @Nullable final SpecialEffectsController.Operation operation2) {
        Iterator<TransitionInfo> it;
        View view;
        Object obj;
        ArrayList<View> arrayList;
        Object obj2;
        ArrayList<View> arrayList2;
        SpecialEffectsController.Operation operation3;
        SpecialEffectsController.Operation operation4;
        View view2;
        Object objMergeTransitionsTogether;
        ArrayList<View> arrayList3;
        DefaultSpecialEffectsController defaultSpecialEffectsController;
        SpecialEffectsController.Operation operation5;
        SpecialEffectsController.Operation operation6;
        View view3;
        SharedElementCallback enterTransitionCallback;
        SharedElementCallback exitTransitionCallback;
        ArrayList<String> arrayList4;
        final View view4;
        String strFindKeyForValue;
        ArrayList<String> arrayList5;
        DefaultSpecialEffectsController defaultSpecialEffectsController2 = this;
        SpecialEffectsController.Operation operation7 = operation;
        SpecialEffectsController.Operation operation8 = operation2;
        HashMap map = new HashMap();
        final FragmentTransitionImpl fragmentTransitionImpl = null;
        for (TransitionInfo transitionInfo : list) {
            if (!transitionInfo.isVisibilityUnchanged()) {
                FragmentTransitionImpl handlingImpl = transitionInfo.getHandlingImpl();
                if (fragmentTransitionImpl == null) {
                    fragmentTransitionImpl = handlingImpl;
                } else if (handlingImpl != null && fragmentTransitionImpl != handlingImpl) {
                    throw new IllegalArgumentException("Mixing framework transitions and AndroidX transitions is not allowed. Fragment " + transitionInfo.getOperation().getFragment() + " returned Transition " + transitionInfo.getTransition() + " which uses a different Transition  type than other Fragments.");
                }
            }
        }
        if (fragmentTransitionImpl == null) {
            for (TransitionInfo transitionInfo2 : list) {
                map.put(transitionInfo2.getOperation(), Boolean.FALSE);
                transitionInfo2.completeSpecialEffect();
            }
            return map;
        }
        View view5 = new View(getContainer().getContext());
        final Rect rect = new Rect();
        ArrayList<View> arrayList6 = new ArrayList<>();
        ArrayList<View> arrayList7 = new ArrayList<>();
        ArrayMap arrayMap = new ArrayMap();
        Object obj3 = null;
        View view6 = null;
        boolean z3 = false;
        for (TransitionInfo transitionInfo3 : list) {
            if (!transitionInfo3.hasSharedElementTransition() || operation7 == null || operation8 == null) {
                arrayList3 = arrayList7;
                defaultSpecialEffectsController = defaultSpecialEffectsController2;
                operation5 = operation7;
                operation6 = operation8;
                view3 = view5;
                view6 = view6;
            } else {
                Object objWrapTransitionInSet = fragmentTransitionImpl.wrapTransitionInSet(fragmentTransitionImpl.cloneTransition(transitionInfo3.getSharedElementTransition()));
                ArrayList<String> sharedElementSourceNames = operation2.getFragment().getSharedElementSourceNames();
                ArrayList<String> sharedElementSourceNames2 = operation.getFragment().getSharedElementSourceNames();
                ArrayList<String> sharedElementTargetNames = operation.getFragment().getSharedElementTargetNames();
                View view7 = view6;
                int i2 = 0;
                while (i2 < sharedElementTargetNames.size()) {
                    int iIndexOf = sharedElementSourceNames.indexOf(sharedElementTargetNames.get(i2));
                    ArrayList<String> arrayList8 = sharedElementTargetNames;
                    if (iIndexOf != -1) {
                        sharedElementSourceNames.set(iIndexOf, sharedElementSourceNames2.get(i2));
                    }
                    i2++;
                    sharedElementTargetNames = arrayList8;
                }
                ArrayList<String> sharedElementTargetNames2 = operation2.getFragment().getSharedElementTargetNames();
                Fragment fragment = operation.getFragment();
                if (z2 == 0) {
                    enterTransitionCallback = fragment.getExitTransitionCallback();
                    exitTransitionCallback = operation2.getFragment().getEnterTransitionCallback();
                } else {
                    enterTransitionCallback = fragment.getEnterTransitionCallback();
                    exitTransitionCallback = operation2.getFragment().getExitTransitionCallback();
                }
                int i3 = 0;
                for (int size = sharedElementSourceNames.size(); i3 < size; size = size) {
                    arrayMap.put(sharedElementSourceNames.get(i3), sharedElementTargetNames2.get(i3));
                    i3++;
                }
                if (FragmentManager.isLoggingEnabled(2)) {
                    for (String str : sharedElementTargetNames2) {
                    }
                    for (String str2 : sharedElementSourceNames) {
                    }
                }
                ArrayMap<String, View> arrayMap2 = new ArrayMap<>();
                defaultSpecialEffectsController2.findNamedViews(arrayMap2, operation.getFragment().mView);
                arrayMap2.retainAll(sharedElementSourceNames);
                if (enterTransitionCallback != null) {
                    if (FragmentManager.isLoggingEnabled(2)) {
                        operation.toString();
                    }
                    enterTransitionCallback.onMapSharedElements(sharedElementSourceNames, arrayMap2);
                    int size2 = sharedElementSourceNames.size() - 1;
                    while (size2 >= 0) {
                        String str3 = sharedElementSourceNames.get(size2);
                        View view8 = arrayMap2.get(str3);
                        if (view8 == null) {
                            arrayMap.remove(str3);
                            arrayList5 = sharedElementSourceNames;
                        } else {
                            arrayList5 = sharedElementSourceNames;
                            if (!str3.equals(ViewCompat.getTransitionName(view8))) {
                                arrayMap.put(ViewCompat.getTransitionName(view8), (String) arrayMap.remove(str3));
                            }
                        }
                        size2--;
                        sharedElementSourceNames = arrayList5;
                    }
                    arrayList4 = sharedElementSourceNames;
                } else {
                    arrayList4 = sharedElementSourceNames;
                    arrayMap.retainAll(arrayMap2.keySet());
                }
                final ArrayMap<String, View> arrayMap3 = new ArrayMap<>();
                defaultSpecialEffectsController2.findNamedViews(arrayMap3, operation2.getFragment().mView);
                arrayMap3.retainAll(sharedElementTargetNames2);
                arrayMap3.retainAll(arrayMap.values());
                if (exitTransitionCallback != null) {
                    if (FragmentManager.isLoggingEnabled(2)) {
                        operation2.toString();
                    }
                    exitTransitionCallback.onMapSharedElements(sharedElementTargetNames2, arrayMap3);
                    for (int size3 = sharedElementTargetNames2.size() - 1; size3 >= 0; size3--) {
                        String str4 = sharedElementTargetNames2.get(size3);
                        View view9 = arrayMap3.get(str4);
                        if (view9 == null) {
                            String strFindKeyForValue2 = FragmentTransition.findKeyForValue(arrayMap, str4);
                            if (strFindKeyForValue2 != null) {
                                arrayMap.remove(strFindKeyForValue2);
                            }
                        } else if (!str4.equals(ViewCompat.getTransitionName(view9)) && (strFindKeyForValue = FragmentTransition.findKeyForValue(arrayMap, str4)) != null) {
                            arrayMap.put(strFindKeyForValue, ViewCompat.getTransitionName(view9));
                        }
                    }
                } else {
                    FragmentTransition.retainValues(arrayMap, arrayMap3);
                }
                defaultSpecialEffectsController2.retainMatchingViews(arrayMap2, arrayMap.keySet());
                defaultSpecialEffectsController2.retainMatchingViews(arrayMap3, arrayMap.values());
                if (arrayMap.isEmpty()) {
                    arrayList6.clear();
                    arrayList7.clear();
                    operation5 = operation;
                    arrayList3 = arrayList7;
                    defaultSpecialEffectsController = defaultSpecialEffectsController2;
                    view3 = view5;
                    view6 = view7;
                    obj3 = null;
                    operation6 = operation2;
                } else {
                    FragmentTransition.callSharedElementStartEnd(operation2.getFragment(), operation.getFragment(), z2, arrayMap2, true);
                    ArrayList<String> arrayList9 = arrayList4;
                    HashMap map2 = map;
                    View view10 = view5;
                    ArrayList<View> arrayList10 = arrayList7;
                    OneShotPreDrawListener.add(getContainer(), new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.6
                        @Override // java.lang.Runnable
                        public void run() {
                            FragmentTransition.callSharedElementStartEnd(operation2.getFragment(), operation.getFragment(), z2, arrayMap3, false);
                        }
                    });
                    arrayList6.addAll(arrayMap2.values());
                    if (arrayList9.isEmpty()) {
                        view6 = view7;
                    } else {
                        view6 = arrayMap2.get(arrayList9.get(0));
                        fragmentTransitionImpl.setEpicenter(objWrapTransitionInSet, view6);
                    }
                    arrayList3 = arrayList10;
                    arrayList3.addAll(arrayMap3.values());
                    if (sharedElementTargetNames2.isEmpty() || (view4 = arrayMap3.get(sharedElementTargetNames2.get(0))) == null) {
                        defaultSpecialEffectsController = this;
                    } else {
                        defaultSpecialEffectsController = this;
                        OneShotPreDrawListener.add(getContainer(), new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.7
                            @Override // java.lang.Runnable
                            public void run() {
                                fragmentTransitionImpl.getBoundsOnScreen(view4, rect);
                            }
                        });
                        z3 = true;
                    }
                    fragmentTransitionImpl.setSharedElementTargets(objWrapTransitionInSet, view10, arrayList6);
                    view3 = view10;
                    fragmentTransitionImpl.scheduleRemoveTargets(objWrapTransitionInSet, null, null, null, null, objWrapTransitionInSet, arrayList3);
                    Boolean bool = Boolean.TRUE;
                    operation5 = operation;
                    map = map2;
                    map.put(operation5, bool);
                    operation6 = operation2;
                    map.put(operation6, bool);
                    obj3 = objWrapTransitionInSet;
                }
            }
            arrayList6 = arrayList6;
            defaultSpecialEffectsController2 = defaultSpecialEffectsController;
            rect = rect;
            view5 = view3;
            operation8 = operation6;
            arrayMap = arrayMap;
            arrayList7 = arrayList3;
            operation7 = operation5;
            fragmentTransitionImpl = fragmentTransitionImpl;
        }
        View view11 = view6;
        ArrayMap arrayMap4 = arrayMap;
        ArrayList<View> arrayList11 = arrayList7;
        DefaultSpecialEffectsController defaultSpecialEffectsController3 = defaultSpecialEffectsController2;
        SpecialEffectsController.Operation operation9 = operation7;
        ArrayList<View> arrayList12 = arrayList6;
        Rect rect2 = rect;
        FragmentTransitionImpl fragmentTransitionImpl2 = fragmentTransitionImpl;
        SpecialEffectsController.Operation operation10 = operation8;
        View view12 = view5;
        ArrayList arrayList13 = new ArrayList();
        Iterator<TransitionInfo> it2 = list.iterator();
        Object obj4 = null;
        Object objMergeTransitionsTogether2 = null;
        while (it2.hasNext()) {
            TransitionInfo next = it2.next();
            if (next.isVisibilityUnchanged()) {
                map.put(next.getOperation(), Boolean.FALSE);
                next.completeSpecialEffect();
            } else {
                Object objCloneTransition = fragmentTransitionImpl2.cloneTransition(next.getTransition());
                SpecialEffectsController.Operation operation11 = next.getOperation();
                boolean z4 = obj3 != null && (operation11 == operation9 || operation11 == operation10);
                if (objCloneTransition == null) {
                    if (!z4) {
                        map.put(operation11, Boolean.FALSE);
                        next.completeSpecialEffect();
                    }
                    arrayList2 = arrayList11;
                    arrayList = arrayList12;
                    it = it2;
                    view = view12;
                    objMergeTransitionsTogether = obj4;
                    operation3 = operation10;
                    view2 = view11;
                } else {
                    it = it2;
                    final ArrayList<View> arrayList14 = new ArrayList<>();
                    Object obj5 = obj4;
                    defaultSpecialEffectsController3.captureTransitioningViews(arrayList14, operation11.getFragment().mView);
                    if (z4) {
                        if (operation11 == operation9) {
                            arrayList14.removeAll(arrayList12);
                        } else {
                            arrayList14.removeAll(arrayList11);
                        }
                    }
                    if (arrayList14.isEmpty()) {
                        fragmentTransitionImpl2.addTarget(objCloneTransition, view12);
                        arrayList2 = arrayList11;
                        arrayList = arrayList12;
                        view = view12;
                        operation4 = operation11;
                        obj2 = objMergeTransitionsTogether2;
                        operation3 = operation10;
                        obj = obj5;
                    } else {
                        fragmentTransitionImpl2.addTargets(objCloneTransition, arrayList14);
                        view = view12;
                        obj = obj5;
                        arrayList = arrayList12;
                        obj2 = objMergeTransitionsTogether2;
                        arrayList2 = arrayList11;
                        operation3 = operation10;
                        fragmentTransitionImpl2.scheduleRemoveTargets(objCloneTransition, objCloneTransition, arrayList14, null, null, null, null);
                        if (operation11.getFinalState() == SpecialEffectsController.Operation.State.GONE) {
                            operation4 = operation11;
                            list2.remove(operation4);
                            ArrayList<View> arrayList15 = new ArrayList<>(arrayList14);
                            arrayList15.remove(operation4.getFragment().mView);
                            fragmentTransitionImpl2.scheduleHideFragmentView(objCloneTransition, operation4.getFragment().mView, arrayList15);
                            OneShotPreDrawListener.add(getContainer(), new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.8
                                @Override // java.lang.Runnable
                                public void run() {
                                    FragmentTransition.setViewVisibility(arrayList14, 4);
                                }
                            });
                        } else {
                            operation4 = operation11;
                        }
                    }
                    if (operation4.getFinalState() == SpecialEffectsController.Operation.State.VISIBLE) {
                        arrayList13.addAll(arrayList14);
                        if (z3) {
                            fragmentTransitionImpl2.setEpicenter(objCloneTransition, rect2);
                        }
                        view2 = view11;
                    } else {
                        view2 = view11;
                        fragmentTransitionImpl2.setEpicenter(objCloneTransition, view2);
                    }
                    map.put(operation4, Boolean.TRUE);
                    if (next.isOverlapAllowed()) {
                        objMergeTransitionsTogether2 = fragmentTransitionImpl2.mergeTransitionsTogether(obj2, objCloneTransition, null);
                        objMergeTransitionsTogether = obj;
                    } else {
                        objMergeTransitionsTogether = fragmentTransitionImpl2.mergeTransitionsTogether(obj, objCloneTransition, null);
                        objMergeTransitionsTogether2 = obj2;
                    }
                }
                operation10 = operation3;
                obj4 = objMergeTransitionsTogether;
                view11 = view2;
                view12 = view;
                arrayList12 = arrayList;
                arrayList11 = arrayList2;
                it2 = it;
            }
        }
        ArrayList<View> arrayList16 = arrayList11;
        ArrayList<View> arrayList17 = arrayList12;
        SpecialEffectsController.Operation operation12 = operation10;
        Object objMergeTransitionsInSequence = fragmentTransitionImpl2.mergeTransitionsInSequence(objMergeTransitionsTogether2, obj4, obj3);
        if (objMergeTransitionsInSequence == null) {
            return map;
        }
        for (final TransitionInfo transitionInfo4 : list) {
            if (!transitionInfo4.isVisibilityUnchanged()) {
                Object transition = transitionInfo4.getTransition();
                final SpecialEffectsController.Operation operation13 = transitionInfo4.getOperation();
                boolean z5 = obj3 != null && (operation13 == operation9 || operation13 == operation12);
                if (transition != null || z5) {
                    if (ViewCompat.isLaidOut(getContainer())) {
                        fragmentTransitionImpl2.setListenerForTransitionEnd(transitionInfo4.getOperation().getFragment(), objMergeTransitionsInSequence, transitionInfo4.getSignal(), new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.9
                            @Override // java.lang.Runnable
                            public void run() {
                                transitionInfo4.completeSpecialEffect();
                                if (FragmentManager.isLoggingEnabled(2)) {
                                    Objects.toString(operation13);
                                }
                            }
                        });
                    } else {
                        if (FragmentManager.isLoggingEnabled(2)) {
                            Objects.toString(getContainer());
                            Objects.toString(operation13);
                        }
                        transitionInfo4.completeSpecialEffect();
                    }
                }
            }
        }
        if (!ViewCompat.isLaidOut(getContainer())) {
            return map;
        }
        FragmentTransition.setViewVisibility(arrayList13, 4);
        ArrayList<String> arrayListPrepareSetNameOverridesReordered = fragmentTransitionImpl2.prepareSetNameOverridesReordered(arrayList16);
        if (FragmentManager.isLoggingEnabled(2)) {
            for (View view13 : arrayList17) {
                Objects.toString(view13);
                ViewCompat.getTransitionName(view13);
            }
            for (View view14 : arrayList16) {
                Objects.toString(view14);
                ViewCompat.getTransitionName(view14);
            }
        }
        fragmentTransitionImpl2.beginDelayedTransition(getContainer(), objMergeTransitionsInSequence);
        fragmentTransitionImpl2.setNameOverridesReordered(getContainer(), arrayList17, arrayList16, arrayListPrepareSetNameOverridesReordered, arrayMap4);
        FragmentTransition.setViewVisibility(arrayList13, 0);
        fragmentTransitionImpl2.swapSharedElementTargets(obj3, arrayList17, arrayList16);
        return map;
    }

    private void syncAnimations(@NonNull List<SpecialEffectsController.Operation> list) {
        Fragment fragment = list.get(list.size() - 1).getFragment();
        for (SpecialEffectsController.Operation operation : list) {
            operation.getFragment().mAnimationInfo.mEnterAnim = fragment.mAnimationInfo.mEnterAnim;
            operation.getFragment().mAnimationInfo.mExitAnim = fragment.mAnimationInfo.mExitAnim;
            operation.getFragment().mAnimationInfo.mPopEnterAnim = fragment.mAnimationInfo.mPopEnterAnim;
            operation.getFragment().mAnimationInfo.mPopExitAnim = fragment.mAnimationInfo.mPopExitAnim;
        }
    }

    public void applyContainerChanges(@NonNull SpecialEffectsController.Operation operation) {
        operation.getFinalState().applyState(operation.getFragment().mView);
    }

    public void captureTransitioningViews(ArrayList<View> arrayList, View view) {
        if (!(view instanceof ViewGroup)) {
            if (arrayList.contains(view)) {
                return;
            }
            arrayList.add(view);
            return;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        if (ViewGroupCompat.isTransitionGroup(viewGroup)) {
            if (arrayList.contains(view)) {
                return;
            }
            arrayList.add(viewGroup);
            return;
        }
        int childCount = viewGroup.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = viewGroup.getChildAt(i2);
            if (childAt.getVisibility() == 0) {
                captureTransitioningViews(arrayList, childAt);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:29:0x0090  */
    @Override // androidx.fragment.app.SpecialEffectsController
    public void executeOperations(@NonNull List<SpecialEffectsController.Operation> list, boolean z2) {
        SpecialEffectsController.Operation operation = null;
        SpecialEffectsController.Operation operation2 = null;
        for (SpecialEffectsController.Operation operation3 : list) {
            SpecialEffectsController.Operation.State stateFrom = SpecialEffectsController.Operation.State.from(operation3.getFragment().mView);
            int i2 = AnonymousClass10.$SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State[operation3.getFinalState().ordinal()];
            if (i2 == 1 || i2 == 2 || i2 == 3) {
                if (stateFrom == SpecialEffectsController.Operation.State.VISIBLE && operation == null) {
                    operation = operation3;
                }
            } else if (i2 == 4 && stateFrom != SpecialEffectsController.Operation.State.VISIBLE) {
                operation2 = operation3;
            }
        }
        if (FragmentManager.isLoggingEnabled(2)) {
            Objects.toString(operation);
            Objects.toString(operation2);
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        final ArrayList arrayList3 = new ArrayList(list);
        syncAnimations(list);
        for (final SpecialEffectsController.Operation operation4 : list) {
            CancellationSignal cancellationSignal = new CancellationSignal();
            operation4.markStartedSpecialEffect(cancellationSignal);
            arrayList.add(new AnimationInfo(operation4, cancellationSignal, z2));
            CancellationSignal cancellationSignal2 = new CancellationSignal();
            operation4.markStartedSpecialEffect(cancellationSignal2);
            boolean z3 = false;
            if (z2) {
                if (operation4 == operation) {
                    z3 = true;
                }
            } else if (operation4 == operation2) {
                z3 = true;
            }
            arrayList2.add(new TransitionInfo(operation4, cancellationSignal2, z2, z3));
            operation4.addCompletionListener(new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.1
                @Override // java.lang.Runnable
                public void run() {
                    if (arrayList3.contains(operation4)) {
                        arrayList3.remove(operation4);
                        DefaultSpecialEffectsController.this.applyContainerChanges(operation4);
                    }
                }
            });
        }
        Map<SpecialEffectsController.Operation, Boolean> mapStartTransitions = startTransitions(arrayList2, arrayList3, z2, operation, operation2);
        startAnimations(arrayList, arrayList3, mapStartTransitions.containsValue(Boolean.TRUE), mapStartTransitions);
        Iterator it = arrayList3.iterator();
        while (it.hasNext()) {
            applyContainerChanges((SpecialEffectsController.Operation) it.next());
        }
        arrayList3.clear();
        if (FragmentManager.isLoggingEnabled(2)) {
            Objects.toString(operation);
            Objects.toString(operation2);
        }
    }

    public void findNamedViews(Map<String, View> map, @NonNull View view) {
        String transitionName = ViewCompat.getTransitionName(view);
        if (transitionName != null) {
            map.put(transitionName, view);
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = viewGroup.getChildAt(i2);
                if (childAt.getVisibility() == 0) {
                    findNamedViews(map, childAt);
                }
            }
        }
    }

    public void retainMatchingViews(@NonNull ArrayMap<String, View> arrayMap, @NonNull Collection<String> collection) {
        Iterator<Map.Entry<String, View>> it = arrayMap.entrySet().iterator();
        while (it.hasNext()) {
            if (!collection.contains(ViewCompat.getTransitionName(it.next().getValue()))) {
                it.remove();
            }
        }
    }
}
