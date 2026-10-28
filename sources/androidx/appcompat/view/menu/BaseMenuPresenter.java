package androidx.appcompat.view.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.RestrictTo;
import java.util.ArrayList;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public abstract class BaseMenuPresenter implements MenuPresenter {
    private MenuPresenter.Callback mCallback;
    protected Context mContext;
    private int mId;
    protected LayoutInflater mInflater;
    private int mItemLayoutRes;
    protected MenuBuilder mMenu;
    private int mMenuLayoutRes;
    protected MenuView mMenuView;
    protected Context mSystemContext;
    protected LayoutInflater mSystemInflater;

    public BaseMenuPresenter(Context context, int i2, int i3) {
        this.mSystemContext = context;
        this.mSystemInflater = LayoutInflater.from(context);
        this.mMenuLayoutRes = i2;
        this.mItemLayoutRes = i3;
    }

    public void addItemView(View view, int i2) {
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(view);
        }
        ((ViewGroup) this.mMenuView).addView(view, i2);
    }

    public abstract void bindItemView(MenuItemImpl menuItemImpl, MenuView.ItemView itemView);

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public boolean collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false;
    }

    public MenuView.ItemView createItemView(ViewGroup viewGroup) {
        return (MenuView.ItemView) this.mSystemInflater.inflate(this.mItemLayoutRes, viewGroup, false);
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public boolean expandItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false;
    }

    public boolean filterLeftoverView(ViewGroup viewGroup, int i2) {
        viewGroup.removeViewAt(i2);
        return true;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public boolean flagActionItems() {
        return false;
    }

    public MenuPresenter.Callback getCallback() {
        return this.mCallback;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public int getId() {
        return this.mId;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public View getItemView(MenuItemImpl menuItemImpl, View view, ViewGroup viewGroup) {
        MenuView.ItemView itemViewCreateItemView = view instanceof MenuView.ItemView ? (MenuView.ItemView) view : createItemView(viewGroup);
        bindItemView(menuItemImpl, itemViewCreateItemView);
        return (View) itemViewCreateItemView;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public MenuView getMenuView(ViewGroup viewGroup) {
        if (this.mMenuView == null) {
            MenuView menuView = (MenuView) this.mSystemInflater.inflate(this.mMenuLayoutRes, viewGroup, false);
            this.mMenuView = menuView;
            menuView.initialize(this.mMenu);
            updateMenuView(true);
        }
        return this.mMenuView;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void initForMenu(Context context, MenuBuilder menuBuilder) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mMenu = menuBuilder;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void onCloseMenu(MenuBuilder menuBuilder, boolean z2) {
        MenuPresenter.Callback callback = this.mCallback;
        if (callback != null) {
            callback.onCloseMenu(menuBuilder, z2);
        }
    }

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
    @Override // androidx.appcompat.view.menu.MenuPresenter
    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        MenuPresenter.Callback callback = this.mCallback;
        MenuBuilder menuBuilder = subMenuBuilder;
        if (callback == null) {
            return false;
        }
        if (subMenuBuilder == null) {
            menuBuilder = this.mMenu;
        }
        return callback.onOpenSubMenu(menuBuilder);
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void setCallback(MenuPresenter.Callback callback) {
        this.mCallback = callback;
    }

    public void setId(int i2) {
        this.mId = i2;
    }

    public boolean shouldIncludeItem(int i2, MenuItemImpl menuItemImpl) {
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void updateMenuView(boolean z2) {
        ViewGroup viewGroup = (ViewGroup) this.mMenuView;
        if (viewGroup == null) {
            return;
        }
        MenuBuilder menuBuilder = this.mMenu;
        int i2 = 0;
        if (menuBuilder != null) {
            menuBuilder.flagActionItems();
            ArrayList<MenuItemImpl> visibleItems = this.mMenu.getVisibleItems();
            int size = visibleItems.size();
            int i3 = 0;
            for (int i4 = 0; i4 < size; i4++) {
                MenuItemImpl menuItemImpl = visibleItems.get(i4);
                if (shouldIncludeItem(i3, menuItemImpl)) {
                    View childAt = viewGroup.getChildAt(i3);
                    MenuItemImpl itemData = childAt instanceof MenuView.ItemView ? ((MenuView.ItemView) childAt).getItemData() : null;
                    View itemView = getItemView(menuItemImpl, childAt, viewGroup);
                    if (menuItemImpl != itemData) {
                        itemView.setPressed(false);
                        itemView.jumpDrawablesToCurrentState();
                    }
                    if (itemView != childAt) {
                        addItemView(itemView, i3);
                    }
                    i3++;
                }
            }
            i2 = i3;
        }
        while (i2 < viewGroup.getChildCount()) {
            if (!filterLeftoverView(viewGroup, i2)) {
                i2++;
            }
        }
    }
}
