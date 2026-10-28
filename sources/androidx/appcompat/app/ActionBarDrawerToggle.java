package androidx.appcompat.app;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public class ActionBarDrawerToggle implements DrawerLayout.DrawerListener {
    private final Delegate mActivityImpl;
    private final int mCloseDrawerContentDescRes;
    boolean mDrawerIndicatorEnabled;
    private final DrawerLayout mDrawerLayout;
    private boolean mDrawerSlideAnimationEnabled;
    private boolean mHasCustomUpIndicator;
    private Drawable mHomeAsUpIndicator;
    private final int mOpenDrawerContentDescRes;
    private DrawerArrowDrawable mSlider;
    View.OnClickListener mToolbarNavigationClickListener;
    private boolean mWarnedForDisplayHomeAsUp;

    public interface Delegate {
        Context getActionBarThemedContext();

        Drawable getThemeUpIndicator();

        boolean isNavigationVisible();

        void setActionBarDescription(@StringRes int i2);

        void setActionBarUpIndicator(Drawable drawable, @StringRes int i2);
    }

    public interface DelegateProvider {
        @Nullable
        Delegate getDrawerToggleDelegate();
    }

    public static class FrameworkActionBarDelegate implements Delegate {
        private final Activity mActivity;

        public FrameworkActionBarDelegate(Activity activity) {
            this.mActivity = activity;
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public Context getActionBarThemedContext() {
            android.app.ActionBar actionBar = this.mActivity.getActionBar();
            return actionBar != null ? actionBar.getThemedContext() : this.mActivity;
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public Drawable getThemeUpIndicator() {
            TypedArray typedArrayObtainStyledAttributes = getActionBarThemedContext().obtainStyledAttributes(null, new int[]{R.attr.homeAsUpIndicator}, R.attr.actionBarStyle, 0);
            Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(0);
            typedArrayObtainStyledAttributes.recycle();
            return drawable;
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public boolean isNavigationVisible() {
            android.app.ActionBar actionBar = this.mActivity.getActionBar();
            return (actionBar == null || (actionBar.getDisplayOptions() & 4) == 0) ? false : true;
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public void setActionBarDescription(int i2) {
            android.app.ActionBar actionBar = this.mActivity.getActionBar();
            if (actionBar != null) {
                actionBar.setHomeActionContentDescription(i2);
            }
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public void setActionBarUpIndicator(Drawable drawable, int i2) {
            android.app.ActionBar actionBar = this.mActivity.getActionBar();
            if (actionBar != null) {
                actionBar.setHomeAsUpIndicator(drawable);
                actionBar.setHomeActionContentDescription(i2);
            }
        }
    }

    public static class ToolbarCompatDelegate implements Delegate {
        final CharSequence mDefaultContentDescription;
        final Drawable mDefaultUpIndicator;
        final Toolbar mToolbar;

        public ToolbarCompatDelegate(Toolbar toolbar) {
            this.mToolbar = toolbar;
            this.mDefaultUpIndicator = toolbar.getNavigationIcon();
            this.mDefaultContentDescription = toolbar.getNavigationContentDescription();
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public Context getActionBarThemedContext() {
            return this.mToolbar.getContext();
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public Drawable getThemeUpIndicator() {
            return this.mDefaultUpIndicator;
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public boolean isNavigationVisible() {
            return true;
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public void setActionBarDescription(@StringRes int i2) {
            if (i2 == 0) {
                this.mToolbar.setNavigationContentDescription(this.mDefaultContentDescription);
            } else {
                this.mToolbar.setNavigationContentDescription(i2);
            }
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public void setActionBarUpIndicator(Drawable drawable, @StringRes int i2) {
            this.mToolbar.setNavigationIcon(drawable);
            setActionBarDescription(i2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ActionBarDrawerToggle(Activity activity, Toolbar toolbar, DrawerLayout drawerLayout, DrawerArrowDrawable drawerArrowDrawable, @StringRes int i2, @StringRes int i3) {
        this.mDrawerSlideAnimationEnabled = true;
        this.mDrawerIndicatorEnabled = true;
        this.mWarnedForDisplayHomeAsUp = false;
        if (toolbar != null) {
            this.mActivityImpl = new ToolbarCompatDelegate(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() { // from class: androidx.appcompat.app.ActionBarDrawerToggle.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ActionBarDrawerToggle actionBarDrawerToggle = ActionBarDrawerToggle.this;
                    if (actionBarDrawerToggle.mDrawerIndicatorEnabled) {
                        actionBarDrawerToggle.toggle();
                        return;
                    }
                    View.OnClickListener onClickListener = actionBarDrawerToggle.mToolbarNavigationClickListener;
                    if (onClickListener != null) {
                        onClickListener.onClick(view);
                    }
                }
            });
        } else if (activity instanceof DelegateProvider) {
            this.mActivityImpl = ((DelegateProvider) activity).getDrawerToggleDelegate();
        } else {
            this.mActivityImpl = new FrameworkActionBarDelegate(activity);
        }
        this.mDrawerLayout = drawerLayout;
        this.mOpenDrawerContentDescRes = i2;
        this.mCloseDrawerContentDescRes = i3;
        if (drawerArrowDrawable == null) {
            this.mSlider = new DrawerArrowDrawable(this.mActivityImpl.getActionBarThemedContext());
        } else {
            this.mSlider = drawerArrowDrawable;
        }
        this.mHomeAsUpIndicator = getThemeUpIndicator();
    }

    private void setPosition(float f2) {
        DrawerArrowDrawable drawerArrowDrawable;
        boolean z2;
        if (f2 != 1.0f) {
            if (f2 == 0.0f) {
                drawerArrowDrawable = this.mSlider;
                z2 = false;
            }
            this.mSlider.setProgress(f2);
        }
        drawerArrowDrawable = this.mSlider;
        z2 = true;
        drawerArrowDrawable.setVerticalMirror(z2);
        this.mSlider.setProgress(f2);
    }

    @NonNull
    public DrawerArrowDrawable getDrawerArrowDrawable() {
        return this.mSlider;
    }

    public Drawable getThemeUpIndicator() {
        return this.mActivityImpl.getThemeUpIndicator();
    }

    public View.OnClickListener getToolbarNavigationClickListener() {
        return this.mToolbarNavigationClickListener;
    }

    public boolean isDrawerIndicatorEnabled() {
        return this.mDrawerIndicatorEnabled;
    }

    public boolean isDrawerSlideAnimationEnabled() {
        return this.mDrawerSlideAnimationEnabled;
    }

    public void onConfigurationChanged(Configuration configuration) {
        if (!this.mHasCustomUpIndicator) {
            this.mHomeAsUpIndicator = getThemeUpIndicator();
        }
        syncState();
    }

    @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
    public void onDrawerClosed(View view) {
        setPosition(0.0f);
        if (this.mDrawerIndicatorEnabled) {
            setActionBarDescription(this.mOpenDrawerContentDescRes);
        }
    }

    @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
    public void onDrawerOpened(View view) {
        setPosition(1.0f);
        if (this.mDrawerIndicatorEnabled) {
            setActionBarDescription(this.mCloseDrawerContentDescRes);
        }
    }

    @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
    public void onDrawerSlide(View view, float f2) {
        if (this.mDrawerSlideAnimationEnabled) {
            setPosition(Math.min(1.0f, Math.max(0.0f, f2)));
        } else {
            setPosition(0.0f);
        }
    }

    @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
    public void onDrawerStateChanged(int i2) {
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem == null || menuItem.getItemId() != 16908332 || !this.mDrawerIndicatorEnabled) {
            return false;
        }
        toggle();
        return true;
    }

    public void setActionBarDescription(int i2) {
        this.mActivityImpl.setActionBarDescription(i2);
    }

    public void setActionBarUpIndicator(Drawable drawable, int i2) {
        if (!this.mWarnedForDisplayHomeAsUp && !this.mActivityImpl.isNavigationVisible()) {
            this.mWarnedForDisplayHomeAsUp = true;
        }
        this.mActivityImpl.setActionBarUpIndicator(drawable, i2);
    }

    public void setDrawerArrowDrawable(@NonNull DrawerArrowDrawable drawerArrowDrawable) {
        this.mSlider = drawerArrowDrawable;
        syncState();
    }

    public void setDrawerIndicatorEnabled(boolean z2) {
        Drawable drawable;
        int i2;
        if (z2 != this.mDrawerIndicatorEnabled) {
            if (z2) {
                drawable = this.mSlider;
                i2 = this.mDrawerLayout.isDrawerOpen(GravityCompat.START) ? this.mCloseDrawerContentDescRes : this.mOpenDrawerContentDescRes;
            } else {
                drawable = this.mHomeAsUpIndicator;
                i2 = 0;
            }
            setActionBarUpIndicator(drawable, i2);
            this.mDrawerIndicatorEnabled = z2;
        }
    }

    public void setDrawerSlideAnimationEnabled(boolean z2) {
        this.mDrawerSlideAnimationEnabled = z2;
        if (z2) {
            return;
        }
        setPosition(0.0f);
    }

    public void setHomeAsUpIndicator(int i2) {
        setHomeAsUpIndicator(i2 != 0 ? this.mDrawerLayout.getResources().getDrawable(i2) : null);
    }

    public void setToolbarNavigationClickListener(View.OnClickListener onClickListener) {
        this.mToolbarNavigationClickListener = onClickListener;
    }

    public void syncState() {
        setPosition(this.mDrawerLayout.isDrawerOpen(GravityCompat.START) ? 1.0f : 0.0f);
        if (this.mDrawerIndicatorEnabled) {
            setActionBarUpIndicator(this.mSlider, this.mDrawerLayout.isDrawerOpen(GravityCompat.START) ? this.mCloseDrawerContentDescRes : this.mOpenDrawerContentDescRes);
        }
    }

    public void toggle() {
        int drawerLockMode = this.mDrawerLayout.getDrawerLockMode(GravityCompat.START);
        if (this.mDrawerLayout.isDrawerVisible(GravityCompat.START) && drawerLockMode != 2) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (drawerLockMode != 1) {
            this.mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    public ActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, @StringRes int i2, @StringRes int i3) {
        this(activity, null, drawerLayout, null, i2, i3);
    }

    public void setHomeAsUpIndicator(Drawable drawable) {
        if (drawable == null) {
            this.mHomeAsUpIndicator = getThemeUpIndicator();
            this.mHasCustomUpIndicator = false;
        } else {
            this.mHomeAsUpIndicator = drawable;
            this.mHasCustomUpIndicator = true;
        }
        if (this.mDrawerIndicatorEnabled) {
            return;
        }
        setActionBarUpIndicator(this.mHomeAsUpIndicator, 0);
    }

    public ActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar, @StringRes int i2, @StringRes int i3) {
        this(activity, toolbar, drawerLayout, null, i2, i3);
    }
}
