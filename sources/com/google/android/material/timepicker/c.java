package com.google.android.material.timepicker;

import com.google.android.material.button.MaterialButtonToggleGroup;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class c implements MaterialButtonToggleGroup.OnButtonCheckedListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f147a;
    public final /* synthetic */ Object b;

    public /* synthetic */ c(int i2, Object obj) {
        this.f147a = i2;
        this.b = obj;
    }

    @Override // com.google.android.material.button.MaterialButtonToggleGroup.OnButtonCheckedListener
    public final void onButtonChecked(MaterialButtonToggleGroup materialButtonToggleGroup, int i2, boolean z2) {
        int i3 = this.f147a;
        Object obj = this.b;
        switch (i3) {
            case 0:
                ((TimePickerTextInputPresenter) obj).lambda$setupPeriodToggle$0(materialButtonToggleGroup, i2, z2);
                break;
            default:
                ((TimePickerView) obj).lambda$new$0(materialButtonToggleGroup, i2, z2);
                break;
        }
    }
}
