package androidx.core.view;

import android.view.View;
import android.view.ViewGroup;
import java.util.Iterator;
import w.y;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@k.e(c = "androidx.core.view.ViewKt$allViews$1", f = "View.kt", l = {409, 411}, m = "invokeSuspend")
public final class ViewKt$allViews$1 extends k.h implements p.p {
    final /* synthetic */ View $this_allViews;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ViewKt$allViews$1(View view, i.e eVar) {
        super(eVar);
        this.$this_allViews = view;
    }

    @Override // k.a
    public final i.e create(Object obj, i.e eVar) {
        ViewKt$allViews$1 viewKt$allViews$1 = new ViewKt$allViews$1(this.$this_allViews, eVar);
        viewKt$allViews$1.L$0 = obj;
        return viewKt$allViews$1;
    }

    @Override // k.a
    public final Object invokeSuspend(Object obj) throws Throwable {
        Object obj2;
        j.a aVar = j.a.b;
        int i2 = this.label;
        if (i2 == 0) {
            y.aj(obj);
            u.i iVar = (u.i) this.L$0;
            View view = this.$this_allViews;
            this.L$0 = iVar;
            this.label = 1;
            u.h hVar = (u.h) iVar;
            hVar.f511c = view;
            hVar.b = 3;
            hVar.f512e = this;
            return aVar;
        }
        g.i iVar2 = g.i.f423a;
        if (i2 == 1) {
            u.i iVar3 = (u.i) this.L$0;
            y.aj(obj);
            View view2 = this.$this_allViews;
            if (view2 instanceof ViewGroup) {
                u.g descendants = ViewGroupKt.getDescendants((ViewGroup) view2);
                this.L$0 = null;
                this.label = 2;
                iVar3.getClass();
                Iterator it = descendants.iterator();
                u.h hVar2 = (u.h) iVar3;
                if (it.hasNext()) {
                    hVar2.d = it;
                    hVar2.b = 2;
                    hVar2.f512e = this;
                    obj2 = aVar;
                } else {
                    obj2 = iVar2;
                }
                if (obj2 != aVar) {
                    obj2 = iVar2;
                }
                if (obj2 == aVar) {
                    return aVar;
                }
            }
        } else {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            y.aj(obj);
        }
        return iVar2;
    }

    @Override // p.p
    public final Object invoke(u.i iVar, i.e eVar) {
        return ((ViewKt$allViews$1) create(iVar, eVar)).invokeSuspend(g.i.f423a);
    }
}
