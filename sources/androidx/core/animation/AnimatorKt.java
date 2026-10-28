package androidx.core.animation;

import android.animation.Animator;
import g.i;
import kotlin.jvm.internal.l;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class AnimatorKt {

    /* JADX INFO: renamed from: androidx.core.animation.AnimatorKt$addListener$1, reason: invalid class name */
    public static final class AnonymousClass1 extends l implements p.l {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        public AnonymousClass1() {
            super(1);
        }

        @Override // p.l
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((Animator) obj);
            return i.f423a;
        }

        public final void invoke(Animator animator) {
        }
    }

    /* JADX INFO: renamed from: androidx.core.animation.AnimatorKt$addListener$2, reason: invalid class name */
    public static final class AnonymousClass2 extends l implements p.l {
        public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

        public AnonymousClass2() {
            super(1);
        }

        @Override // p.l
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((Animator) obj);
            return i.f423a;
        }

        public final void invoke(Animator animator) {
        }
    }

    /* JADX INFO: renamed from: androidx.core.animation.AnimatorKt$addListener$3, reason: invalid class name */
    public static final class AnonymousClass3 extends l implements p.l {
        public static final AnonymousClass3 INSTANCE = new AnonymousClass3();

        public AnonymousClass3() {
            super(1);
        }

        @Override // p.l
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((Animator) obj);
            return i.f423a;
        }

        public final void invoke(Animator animator) {
        }
    }

    /* JADX INFO: renamed from: androidx.core.animation.AnimatorKt$addListener$4, reason: invalid class name */
    public static final class AnonymousClass4 extends l implements p.l {
        public static final AnonymousClass4 INSTANCE = new AnonymousClass4();

        public AnonymousClass4() {
            super(1);
        }

        @Override // p.l
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((Animator) obj);
            return i.f423a;
        }

        public final void invoke(Animator animator) {
        }
    }

    /* JADX INFO: renamed from: androidx.core.animation.AnimatorKt$addPauseListener$1, reason: invalid class name and case insensitive filesystem */
    public static final class C00051 extends l implements p.l {
        public static final C00051 INSTANCE = new C00051();

        public C00051() {
            super(1);
        }

        @Override // p.l
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((Animator) obj);
            return i.f423a;
        }

        public final void invoke(Animator animator) {
        }
    }

    /* JADX INFO: renamed from: androidx.core.animation.AnimatorKt$addPauseListener$2, reason: invalid class name and case insensitive filesystem */
    public static final class C00062 extends l implements p.l {
        public static final C00062 INSTANCE = new C00062();

        public C00062() {
            super(1);
        }

        @Override // p.l
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((Animator) obj);
            return i.f423a;
        }

        public final void invoke(Animator animator) {
        }
    }

    public static final Animator.AnimatorListener addListener(Animator animator, p.l lVar, p.l lVar2, p.l lVar3, p.l lVar4) {
        AnimatorKt$addListener$listener$1 animatorKt$addListener$listener$1 = new AnimatorKt$addListener$listener$1(lVar4, lVar, lVar3, lVar2);
        animator.addListener(animatorKt$addListener$listener$1);
        return animatorKt$addListener$listener$1;
    }

    public static /* synthetic */ Animator.AnimatorListener addListener$default(Animator animator, p.l lVar, p.l lVar2, p.l lVar3, p.l lVar4, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            lVar = AnonymousClass1.INSTANCE;
        }
        if ((i2 & 2) != 0) {
            lVar2 = AnonymousClass2.INSTANCE;
        }
        if ((i2 & 4) != 0) {
            lVar3 = AnonymousClass3.INSTANCE;
        }
        if ((i2 & 8) != 0) {
            lVar4 = AnonymousClass4.INSTANCE;
        }
        AnimatorKt$addListener$listener$1 animatorKt$addListener$listener$1 = new AnimatorKt$addListener$listener$1(lVar4, lVar, lVar3, lVar2);
        animator.addListener(animatorKt$addListener$listener$1);
        return animatorKt$addListener$listener$1;
    }

    public static final Animator.AnimatorPauseListener addPauseListener(Animator animator, final p.l lVar, final p.l lVar2) {
        Animator.AnimatorPauseListener animatorPauseListener = new Animator.AnimatorPauseListener() { // from class: androidx.core.animation.AnimatorKt$addPauseListener$listener$1
            @Override // android.animation.Animator.AnimatorPauseListener
            public void onAnimationPause(Animator animator2) {
                lVar2.invoke(animator2);
            }

            @Override // android.animation.Animator.AnimatorPauseListener
            public void onAnimationResume(Animator animator2) {
                lVar.invoke(animator2);
            }
        };
        animator.addPauseListener(animatorPauseListener);
        return animatorPauseListener;
    }

    public static /* synthetic */ Animator.AnimatorPauseListener addPauseListener$default(Animator animator, p.l lVar, p.l lVar2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            lVar = C00051.INSTANCE;
        }
        if ((i2 & 2) != 0) {
            lVar2 = C00062.INSTANCE;
        }
        return addPauseListener(animator, lVar, lVar2);
    }

    public static final Animator.AnimatorListener doOnCancel(Animator animator, final p.l lVar) {
        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() { // from class: androidx.core.animation.AnimatorKt$doOnCancel$$inlined$addListener$default$1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator2) {
                lVar.invoke(animator2);
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator2) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator2) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator2) {
            }
        };
        animator.addListener(animatorListener);
        return animatorListener;
    }

    public static final Animator.AnimatorListener doOnEnd(Animator animator, final p.l lVar) {
        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() { // from class: androidx.core.animation.AnimatorKt$doOnEnd$$inlined$addListener$default$1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator2) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator2) {
                lVar.invoke(animator2);
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator2) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator2) {
            }
        };
        animator.addListener(animatorListener);
        return animatorListener;
    }

    public static final Animator.AnimatorPauseListener doOnPause(Animator animator, p.l lVar) {
        return addPauseListener$default(animator, null, lVar, 1, null);
    }

    public static final Animator.AnimatorListener doOnRepeat(Animator animator, final p.l lVar) {
        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() { // from class: androidx.core.animation.AnimatorKt$doOnRepeat$$inlined$addListener$default$1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator2) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator2) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator2) {
                lVar.invoke(animator2);
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator2) {
            }
        };
        animator.addListener(animatorListener);
        return animatorListener;
    }

    public static final Animator.AnimatorPauseListener doOnResume(Animator animator, p.l lVar) {
        return addPauseListener$default(animator, lVar, null, 2, null);
    }

    public static final Animator.AnimatorListener doOnStart(Animator animator, final p.l lVar) {
        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() { // from class: androidx.core.animation.AnimatorKt$doOnStart$$inlined$addListener$default$1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator2) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator2) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator2) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator2) {
                lVar.invoke(animator2);
            }
        };
        animator.addListener(animatorListener);
        return animatorListener;
    }
}
