package f;

import android.content.DialogInterface;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import com.zenoatom.app.CityRunActivity;
import java.util.LinkedHashMap;

/* JADX INFO: renamed from: f.e, reason: case insensitive filesystem */
/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class ViewOnClickListenerC0025e implements View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f360a;
    public final /* synthetic */ CityRunActivity b;

    public /* synthetic */ ViewOnClickListenerC0025e(CityRunActivity cityRunActivity, int i2) {
        this.f360a = i2;
        this.b = cityRunActivity;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i2 = this.f360a;
        final CityRunActivity cityRunActivity = this.b;
        switch (i2) {
            case 0:
                int i3 = CityRunActivity.f151p;
                cityRunActivity.finish();
                break;
            case 1:
                int i4 = CityRunActivity.f151p;
                cityRunActivity.f153c.stopLoading();
                cityRunActivity.b.setVisibility(8);
                cityRunActivity.f163n = false;
                break;
            case 2:
                LinkedHashMap linkedHashMap = cityRunActivity.f158i;
                if (!linkedHashMap.isEmpty()) {
                    if (linkedHashMap.size() == 1) {
                        cityRunActivity.s((String) linkedHashMap.keySet().iterator().next());
                    } else {
                        final String[] strArr = (String[]) linkedHashMap.keySet().toArray(new String[0]);
                        String[] strArr2 = new String[strArr.length];
                        for (int i5 = 0; i5 < strArr.length; i5++) {
                            r rVar = (r) linkedHashMap.get(strArr[i5]);
                            strArr2[i5] = rVar != null ? CityRunActivity.o(rVar.f385a) : strArr[i5];
                        }
                        new AlertDialog.Builder(cityRunActivity).setTitle("Logout — Account ရွေးပါ").setItems(strArr2, new DialogInterface.OnClickListener() { // from class: f.g
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i6) {
                                int i7 = CityRunActivity.f151p;
                                CityRunActivity cityRunActivity2 = cityRunActivity;
                                cityRunActivity2.getClass();
                                cityRunActivity2.s(strArr[i6]);
                            }
                        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
                    }
                    break;
                }
                break;
            default:
                int i6 = CityRunActivity.f151p;
                cityRunActivity.finish();
                break;
        }
    }
}
