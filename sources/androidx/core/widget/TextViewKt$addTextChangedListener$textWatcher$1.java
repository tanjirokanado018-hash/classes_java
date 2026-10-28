package androidx.core.widget;

import android.text.Editable;
import android.text.TextWatcher;
import p.l;
import p.r;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class TextViewKt$addTextChangedListener$textWatcher$1 implements TextWatcher {
    final /* synthetic */ l $afterTextChanged;
    final /* synthetic */ r $beforeTextChanged;
    final /* synthetic */ r $onTextChanged;

    public TextViewKt$addTextChangedListener$textWatcher$1(l lVar, r rVar, r rVar2) {
        this.$afterTextChanged = lVar;
        this.$beforeTextChanged = rVar;
        this.$onTextChanged = rVar2;
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
        this.$afterTextChanged.invoke(editable);
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
        this.$beforeTextChanged.invoke(charSequence, Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4));
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
        this.$onTextChanged.invoke(charSequence, Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4));
    }
}
