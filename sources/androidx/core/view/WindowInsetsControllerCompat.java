package androidx.core.view;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.CancellationSignal;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsAnimationControlListener;
import android.view.WindowInsetsAnimationController;
import android.view.WindowInsetsController;
import android.view.animation.Interpolator;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.collection.SimpleArrayMap;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.accessibility.AccessibilityEventCompat;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class WindowInsetsControllerCompat {
    public static final int BEHAVIOR_DEFAULT = 1;

    @Deprecated
    public static final int BEHAVIOR_SHOW_BARS_BY_SWIPE = 1;

    @Deprecated
    public static final int BEHAVIOR_SHOW_BARS_BY_TOUCH = 0;
    public static final int BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE = 2;
    private final Impl mImpl;

    public static class Impl {
        public void addOnControllableInsetsChangedListener(OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
        }

        public void controlWindowInsetsAnimation(int i2, long j2, Interpolator interpolator, CancellationSignal cancellationSignal, WindowInsetsAnimationControlListenerCompat windowInsetsAnimationControlListenerCompat) {
        }

        public int getSystemBarsBehavior() {
            return 0;
        }

        public void hide(int i2) {
        }

        public boolean isAppearanceLightNavigationBars() {
            return false;
        }

        public boolean isAppearanceLightStatusBars() {
            return false;
        }

        public void removeOnControllableInsetsChangedListener(@NonNull OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
        }

        public void setAppearanceLightNavigationBars(boolean z2) {
        }

        public void setAppearanceLightStatusBars(boolean z2) {
        }

        public void setSystemBarsBehavior(int i2) {
        }

        public void show(int i2) {
        }
    }

    @RequiresApi(20)
    public static class Impl20 extends Impl {

        @NonNull
        private final SoftwareKeyboardControllerCompat mSoftwareKeyboardControllerCompat;

        @NonNull
        protected final Window mWindow;

        public Impl20(@NonNull Window window, @NonNull SoftwareKeyboardControllerCompat softwareKeyboardControllerCompat) {
            this.mWindow = window;
            this.mSoftwareKeyboardControllerCompat = softwareKeyboardControllerCompat;
        }

        private void hideForType(int i2) {
            if (i2 == 1) {
                setSystemUiFlag(4);
            } else if (i2 == 2) {
                setSystemUiFlag(2);
            } else {
                if (i2 != 8) {
                    return;
                }
                this.mSoftwareKeyboardControllerCompat.hide();
            }
        }

        private void showForType(int i2) {
            if (i2 == 1) {
                unsetSystemUiFlag(4);
                unsetWindowFlag(1024);
            } else if (i2 == 2) {
                unsetSystemUiFlag(2);
            } else {
                if (i2 != 8) {
                    return;
                }
                this.mSoftwareKeyboardControllerCompat.show();
            }
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public void addOnControllableInsetsChangedListener(OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public void controlWindowInsetsAnimation(int i2, long j2, Interpolator interpolator, CancellationSignal cancellationSignal, WindowInsetsAnimationControlListenerCompat windowInsetsAnimationControlListenerCompat) {
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public int getSystemBarsBehavior() {
            return 0;
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public void hide(int i2) {
            for (int i3 = 1; i3 <= 256; i3 <<= 1) {
                if ((i2 & i3) != 0) {
                    hideForType(i3);
                }
            }
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public void removeOnControllableInsetsChangedListener(@NonNull OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public void setSystemBarsBehavior(int i2) {
            if (i2 == 0) {
                unsetSystemUiFlag(6144);
                return;
            }
            if (i2 == 1) {
                unsetSystemUiFlag(4096);
                setSystemUiFlag(2048);
            } else {
                if (i2 != 2) {
                    return;
                }
                unsetSystemUiFlag(2048);
                setSystemUiFlag(4096);
            }
        }

        public void setSystemUiFlag(int i2) {
            View decorView = this.mWindow.getDecorView();
            decorView.setSystemUiVisibility(i2 | decorView.getSystemUiVisibility());
        }

        public void setWindowFlag(int i2) {
            this.mWindow.addFlags(i2);
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public void show(int i2) {
            for (int i3 = 1; i3 <= 256; i3 <<= 1) {
                if ((i2 & i3) != 0) {
                    showForType(i3);
                }
            }
        }

        public void unsetSystemUiFlag(int i2) {
            View decorView = this.mWindow.getDecorView();
            decorView.setSystemUiVisibility((~i2) & decorView.getSystemUiVisibility());
        }

        public void unsetWindowFlag(int i2) {
            this.mWindow.clearFlags(i2);
        }
    }

    @RequiresApi(23)
    public static class Impl23 extends Impl20 {
        public Impl23(@NonNull Window window, @NonNull SoftwareKeyboardControllerCompat softwareKeyboardControllerCompat) {
            super(window, softwareKeyboardControllerCompat);
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public boolean isAppearanceLightStatusBars() {
            return (this.mWindow.getDecorView().getSystemUiVisibility() & 8192) != 0;
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public void setAppearanceLightStatusBars(boolean z2) {
            if (!z2) {
                unsetSystemUiFlag(8192);
                return;
            }
            unsetWindowFlag(AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL);
            setWindowFlag(Integer.MIN_VALUE);
            setSystemUiFlag(8192);
        }
    }

    @RequiresApi(26)
    public static class Impl26 extends Impl23 {
        public Impl26(@NonNull Window window, @NonNull SoftwareKeyboardControllerCompat softwareKeyboardControllerCompat) {
            super(window, softwareKeyboardControllerCompat);
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public boolean isAppearanceLightNavigationBars() {
            return (this.mWindow.getDecorView().getSystemUiVisibility() & 16) != 0;
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public void setAppearanceLightNavigationBars(boolean z2) {
            if (!z2) {
                unsetSystemUiFlag(16);
                return;
            }
            unsetWindowFlag(134217728);
            setWindowFlag(Integer.MIN_VALUE);
            setSystemUiFlag(16);
        }
    }

    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_BIAS)
    public static class Impl30 extends Impl {
        final WindowInsetsControllerCompat mCompatController;
        final WindowInsetsController mInsetsController;
        private final SimpleArrayMap<OnControllableInsetsChangedListener, WindowInsetsController.OnControllableInsetsChangedListener> mListeners;
        final SoftwareKeyboardControllerCompat mSoftwareKeyboardControllerCompat;
        protected Window mWindow;

        public Impl30(@NonNull Window window, @NonNull WindowInsetsControllerCompat windowInsetsControllerCompat, @NonNull SoftwareKeyboardControllerCompat softwareKeyboardControllerCompat) {
            this(window.getInsetsController(), windowInsetsControllerCompat, softwareKeyboardControllerCompat);
            this.mWindow = window;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$addOnControllableInsetsChangedListener$0(OnControllableInsetsChangedListener onControllableInsetsChangedListener, WindowInsetsController windowInsetsController, int i2) {
            if (this.mInsetsController == windowInsetsController) {
                onControllableInsetsChangedListener.onControllableInsetsChanged(this.mCompatController, i2);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v2, types: [androidx.core.view.q, java.lang.Object] */
        /* JADX WARN: Type inference fix 'apply assigned field type' failed
        java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
        	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
        	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
        	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
         */
        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public void addOnControllableInsetsChangedListener(@NonNull final OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
            if (this.mListeners.containsKey(onControllableInsetsChangedListener)) {
                return;
            }
            ?? r0 = new WindowInsetsController.OnControllableInsetsChangedListener() { // from class: androidx.core.view.q
                @Override // android.view.WindowInsetsController.OnControllableInsetsChangedListener
                public final void onControllableInsetsChanged(WindowInsetsController windowInsetsController, int i2) {
                    this.f59a.lambda$addOnControllableInsetsChangedListener$0(onControllableInsetsChangedListener, windowInsetsController, i2);
                }
            };
            this.mListeners.put(onControllableInsetsChangedListener, r0);
            this.mInsetsController.addOnControllableInsetsChangedListener(r0);
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public void controlWindowInsetsAnimation(int i2, long j2, @Nullable Interpolator interpolator, @Nullable CancellationSignal cancellationSignal, @NonNull final WindowInsetsAnimationControlListenerCompat windowInsetsAnimationControlListenerCompat) {
            this.mInsetsController.controlWindowInsetsAnimation(i2, j2, interpolator, cancellationSignal, new WindowInsetsAnimationControlListener() { // from class: androidx.core.view.WindowInsetsControllerCompat.Impl30.1
                private WindowInsetsAnimationControllerCompat mCompatAnimController = null;

                @Override // android.view.WindowInsetsAnimationControlListener
                public void onCancelled(@Nullable WindowInsetsAnimationController windowInsetsAnimationController) {
                    windowInsetsAnimationControlListenerCompat.onCancelled(windowInsetsAnimationController == null ? null : this.mCompatAnimController);
                }

                @Override // android.view.WindowInsetsAnimationControlListener
                public void onFinished(@NonNull WindowInsetsAnimationController windowInsetsAnimationController) {
                    windowInsetsAnimationControlListenerCompat.onFinished(this.mCompatAnimController);
                }

                @Override // android.view.WindowInsetsAnimationControlListener
                public void onReady(@NonNull WindowInsetsAnimationController windowInsetsAnimationController, int i3) {
                    WindowInsetsAnimationControllerCompat windowInsetsAnimationControllerCompat = new WindowInsetsAnimationControllerCompat(windowInsetsAnimationController);
                    this.mCompatAnimController = windowInsetsAnimationControllerCompat;
                    windowInsetsAnimationControlListenerCompat.onReady(windowInsetsAnimationControllerCompat, i3);
                }
            });
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        @SuppressLint({"WrongConstant"})
        public int getSystemBarsBehavior() {
            return this.mInsetsController.getSystemBarsBehavior();
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public void hide(int i2) {
            if ((i2 & 8) != 0) {
                this.mSoftwareKeyboardControllerCompat.hide();
            }
            this.mInsetsController.hide(i2 & (-9));
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public boolean isAppearanceLightNavigationBars() {
            this.mInsetsController.setSystemBarsAppearance(0, 0);
            return (this.mInsetsController.getSystemBarsAppearance() & 16) != 0;
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public boolean isAppearanceLightStatusBars() {
            this.mInsetsController.setSystemBarsAppearance(0, 0);
            return (this.mInsetsController.getSystemBarsAppearance() & 8) != 0;
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public void removeOnControllableInsetsChangedListener(@NonNull OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
            WindowInsetsController.OnControllableInsetsChangedListener onControllableInsetsChangedListenerF = n.f(this.mListeners.remove(onControllableInsetsChangedListener));
            if (onControllableInsetsChangedListenerF != null) {
                this.mInsetsController.removeOnControllableInsetsChangedListener(onControllableInsetsChangedListenerF);
            }
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public void setAppearanceLightNavigationBars(boolean z2) {
            if (z2) {
                if (this.mWindow != null) {
                    setSystemUiFlag(16);
                }
                this.mInsetsController.setSystemBarsAppearance(16, 16);
            } else {
                if (this.mWindow != null) {
                    unsetSystemUiFlag(16);
                }
                this.mInsetsController.setSystemBarsAppearance(0, 16);
            }
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public void setAppearanceLightStatusBars(boolean z2) {
            if (z2) {
                if (this.mWindow != null) {
                    setSystemUiFlag(8192);
                }
                this.mInsetsController.setSystemBarsAppearance(8, 8);
            } else {
                if (this.mWindow != null) {
                    unsetSystemUiFlag(8192);
                }
                this.mInsetsController.setSystemBarsAppearance(0, 8);
            }
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public void setSystemBarsBehavior(int i2) {
            this.mInsetsController.setSystemBarsBehavior(i2);
        }

        public void setSystemUiFlag(int i2) {
            View decorView = this.mWindow.getDecorView();
            decorView.setSystemUiVisibility(i2 | decorView.getSystemUiVisibility());
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public void show(int i2) {
            if ((i2 & 8) != 0) {
                this.mSoftwareKeyboardControllerCompat.show();
            }
            this.mInsetsController.show(i2 & (-9));
        }

        public void unsetSystemUiFlag(int i2) {
            View decorView = this.mWindow.getDecorView();
            decorView.setSystemUiVisibility((~i2) & decorView.getSystemUiVisibility());
        }

        public Impl30(@NonNull WindowInsetsController windowInsetsController, @NonNull WindowInsetsControllerCompat windowInsetsControllerCompat, @NonNull SoftwareKeyboardControllerCompat softwareKeyboardControllerCompat) {
            this.mListeners = new SimpleArrayMap<>();
            this.mInsetsController = windowInsetsController;
            this.mCompatController = windowInsetsControllerCompat;
            this.mSoftwareKeyboardControllerCompat = softwareKeyboardControllerCompat;
        }
    }

    public interface OnControllableInsetsChangedListener {
        void onControllableInsetsChanged(@NonNull WindowInsetsControllerCompat windowInsetsControllerCompat, int i2);
    }

    public WindowInsetsControllerCompat(@NonNull Window window, @NonNull View view) {
        SoftwareKeyboardControllerCompat softwareKeyboardControllerCompat = new SoftwareKeyboardControllerCompat(view);
        int i2 = Build.VERSION.SDK_INT;
        this.mImpl = i2 >= 30 ? new Impl30(window, this, softwareKeyboardControllerCompat) : i2 >= 26 ? new Impl26(window, softwareKeyboardControllerCompat) : i2 >= 23 ? new Impl23(window, softwareKeyboardControllerCompat) : new Impl20(window, softwareKeyboardControllerCompat);
    }

    @NonNull
    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_BIAS)
    @Deprecated
    public static WindowInsetsControllerCompat toWindowInsetsControllerCompat(@NonNull WindowInsetsController windowInsetsController) {
        return new WindowInsetsControllerCompat(windowInsetsController);
    }

    public void addOnControllableInsetsChangedListener(@NonNull OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
        this.mImpl.addOnControllableInsetsChangedListener(onControllableInsetsChangedListener);
    }

    public void controlWindowInsetsAnimation(int i2, long j2, @Nullable Interpolator interpolator, @Nullable CancellationSignal cancellationSignal, @NonNull WindowInsetsAnimationControlListenerCompat windowInsetsAnimationControlListenerCompat) {
        this.mImpl.controlWindowInsetsAnimation(i2, j2, interpolator, cancellationSignal, windowInsetsAnimationControlListenerCompat);
    }

    @SuppressLint({"WrongConstant"})
    public int getSystemBarsBehavior() {
        return this.mImpl.getSystemBarsBehavior();
    }

    public void hide(int i2) {
        this.mImpl.hide(i2);
    }

    public boolean isAppearanceLightNavigationBars() {
        return this.mImpl.isAppearanceLightNavigationBars();
    }

    public boolean isAppearanceLightStatusBars() {
        return this.mImpl.isAppearanceLightStatusBars();
    }

    public void removeOnControllableInsetsChangedListener(@NonNull OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
        this.mImpl.removeOnControllableInsetsChangedListener(onControllableInsetsChangedListener);
    }

    public void setAppearanceLightNavigationBars(boolean z2) {
        this.mImpl.setAppearanceLightNavigationBars(z2);
    }

    public void setAppearanceLightStatusBars(boolean z2) {
        this.mImpl.setAppearanceLightStatusBars(z2);
    }

    public void setSystemBarsBehavior(int i2) {
        this.mImpl.setSystemBarsBehavior(i2);
    }

    public void show(int i2) {
        this.mImpl.show(i2);
    }

    @RequiresApi(ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_BIAS)
    @Deprecated
    private WindowInsetsControllerCompat(@NonNull WindowInsetsController windowInsetsController) {
        this.mImpl = new Impl30(windowInsetsController, this, new SoftwareKeyboardControllerCompat(windowInsetsController));
    }
}
