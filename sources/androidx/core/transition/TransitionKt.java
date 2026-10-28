package androidx.core.transition;

import android.transition.Transition;
import g.i;
import kotlin.jvm.internal.l;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class TransitionKt {

    /* JADX INFO: renamed from: androidx.core.transition.TransitionKt$addListener$1, reason: invalid class name */
    public static final class AnonymousClass1 extends l implements p.l {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        public AnonymousClass1() {
            super(1);
        }

        @Override // p.l
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((Transition) obj);
            return i.f423a;
        }

        public final void invoke(Transition transition) {
        }
    }

    /* JADX INFO: renamed from: androidx.core.transition.TransitionKt$addListener$2, reason: invalid class name */
    public static final class AnonymousClass2 extends l implements p.l {
        public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

        public AnonymousClass2() {
            super(1);
        }

        @Override // p.l
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((Transition) obj);
            return i.f423a;
        }

        public final void invoke(Transition transition) {
        }
    }

    /* JADX INFO: renamed from: androidx.core.transition.TransitionKt$addListener$3, reason: invalid class name */
    public static final class AnonymousClass3 extends l implements p.l {
        public static final AnonymousClass3 INSTANCE = new AnonymousClass3();

        public AnonymousClass3() {
            super(1);
        }

        @Override // p.l
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((Transition) obj);
            return i.f423a;
        }

        public final void invoke(Transition transition) {
        }
    }

    /* JADX INFO: renamed from: androidx.core.transition.TransitionKt$addListener$4, reason: invalid class name */
    public static final class AnonymousClass4 extends l implements p.l {
        public static final AnonymousClass4 INSTANCE = new AnonymousClass4();

        public AnonymousClass4() {
            super(1);
        }

        @Override // p.l
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((Transition) obj);
            return i.f423a;
        }

        public final void invoke(Transition transition) {
        }
    }

    /* JADX INFO: renamed from: androidx.core.transition.TransitionKt$addListener$5, reason: invalid class name */
    public static final class AnonymousClass5 extends l implements p.l {
        public static final AnonymousClass5 INSTANCE = new AnonymousClass5();

        public AnonymousClass5() {
            super(1);
        }

        @Override // p.l
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((Transition) obj);
            return i.f423a;
        }

        public final void invoke(Transition transition) {
        }
    }

    public static final Transition.TransitionListener addListener(Transition transition, p.l lVar, p.l lVar2, p.l lVar3, p.l lVar4, p.l lVar5) {
        TransitionKt$addListener$listener$1 transitionKt$addListener$listener$1 = new TransitionKt$addListener$listener$1(lVar, lVar4, lVar5, lVar3, lVar2);
        transition.addListener(transitionKt$addListener$listener$1);
        return transitionKt$addListener$listener$1;
    }

    public static /* synthetic */ Transition.TransitionListener addListener$default(Transition transition, p.l lVar, p.l lVar2, p.l lVar3, p.l lVar4, p.l lVar5, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            lVar = AnonymousClass1.INSTANCE;
        }
        if ((i2 & 2) != 0) {
            lVar2 = AnonymousClass2.INSTANCE;
        }
        p.l lVar6 = lVar2;
        if ((i2 & 4) != 0) {
            lVar3 = AnonymousClass3.INSTANCE;
        }
        p.l lVar7 = lVar3;
        if ((i2 & 8) != 0) {
            lVar4 = AnonymousClass4.INSTANCE;
        }
        if ((i2 & 16) != 0) {
            lVar5 = AnonymousClass5.INSTANCE;
        }
        TransitionKt$addListener$listener$1 transitionKt$addListener$listener$1 = new TransitionKt$addListener$listener$1(lVar, lVar4, lVar5, lVar7, lVar6);
        transition.addListener(transitionKt$addListener$listener$1);
        return transitionKt$addListener$listener$1;
    }

    public static final Transition.TransitionListener doOnCancel(Transition transition, final p.l lVar) {
        Transition.TransitionListener transitionListener = new Transition.TransitionListener() { // from class: androidx.core.transition.TransitionKt$doOnCancel$$inlined$addListener$default$1
            @Override // android.transition.Transition.TransitionListener
            public void onTransitionCancel(Transition transition2) {
                lVar.invoke(transition2);
            }

            @Override // android.transition.Transition.TransitionListener
            public void onTransitionEnd(Transition transition2) {
            }

            @Override // android.transition.Transition.TransitionListener
            public void onTransitionPause(Transition transition2) {
            }

            @Override // android.transition.Transition.TransitionListener
            public void onTransitionResume(Transition transition2) {
            }

            @Override // android.transition.Transition.TransitionListener
            public void onTransitionStart(Transition transition2) {
            }
        };
        transition.addListener(transitionListener);
        return transitionListener;
    }

    public static final Transition.TransitionListener doOnEnd(Transition transition, final p.l lVar) {
        Transition.TransitionListener transitionListener = new Transition.TransitionListener() { // from class: androidx.core.transition.TransitionKt$doOnEnd$$inlined$addListener$default$1
            @Override // android.transition.Transition.TransitionListener
            public void onTransitionCancel(Transition transition2) {
            }

            @Override // android.transition.Transition.TransitionListener
            public void onTransitionEnd(Transition transition2) {
                lVar.invoke(transition2);
            }

            @Override // android.transition.Transition.TransitionListener
            public void onTransitionPause(Transition transition2) {
            }

            @Override // android.transition.Transition.TransitionListener
            public void onTransitionResume(Transition transition2) {
            }

            @Override // android.transition.Transition.TransitionListener
            public void onTransitionStart(Transition transition2) {
            }
        };
        transition.addListener(transitionListener);
        return transitionListener;
    }

    public static final Transition.TransitionListener doOnPause(Transition transition, final p.l lVar) {
        Transition.TransitionListener transitionListener = new Transition.TransitionListener() { // from class: androidx.core.transition.TransitionKt$doOnPause$$inlined$addListener$default$1
            @Override // android.transition.Transition.TransitionListener
            public void onTransitionCancel(Transition transition2) {
            }

            @Override // android.transition.Transition.TransitionListener
            public void onTransitionEnd(Transition transition2) {
            }

            @Override // android.transition.Transition.TransitionListener
            public void onTransitionPause(Transition transition2) {
                lVar.invoke(transition2);
            }

            @Override // android.transition.Transition.TransitionListener
            public void onTransitionResume(Transition transition2) {
            }

            @Override // android.transition.Transition.TransitionListener
            public void onTransitionStart(Transition transition2) {
            }
        };
        transition.addListener(transitionListener);
        return transitionListener;
    }

    public static final Transition.TransitionListener doOnResume(Transition transition, final p.l lVar) {
        Transition.TransitionListener transitionListener = new Transition.TransitionListener() { // from class: androidx.core.transition.TransitionKt$doOnResume$$inlined$addListener$default$1
            @Override // android.transition.Transition.TransitionListener
            public void onTransitionCancel(Transition transition2) {
            }

            @Override // android.transition.Transition.TransitionListener
            public void onTransitionEnd(Transition transition2) {
            }

            @Override // android.transition.Transition.TransitionListener
            public void onTransitionPause(Transition transition2) {
            }

            @Override // android.transition.Transition.TransitionListener
            public void onTransitionResume(Transition transition2) {
                lVar.invoke(transition2);
            }

            @Override // android.transition.Transition.TransitionListener
            public void onTransitionStart(Transition transition2) {
            }
        };
        transition.addListener(transitionListener);
        return transitionListener;
    }

    public static final Transition.TransitionListener doOnStart(Transition transition, final p.l lVar) {
        Transition.TransitionListener transitionListener = new Transition.TransitionListener() { // from class: androidx.core.transition.TransitionKt$doOnStart$$inlined$addListener$default$1
            @Override // android.transition.Transition.TransitionListener
            public void onTransitionCancel(Transition transition2) {
            }

            @Override // android.transition.Transition.TransitionListener
            public void onTransitionEnd(Transition transition2) {
            }

            @Override // android.transition.Transition.TransitionListener
            public void onTransitionPause(Transition transition2) {
            }

            @Override // android.transition.Transition.TransitionListener
            public void onTransitionResume(Transition transition2) {
            }

            @Override // android.transition.Transition.TransitionListener
            public void onTransitionStart(Transition transition2) {
                lVar.invoke(transition2);
            }
        };
        transition.addListener(transitionListener);
        return transitionListener;
    }
}
