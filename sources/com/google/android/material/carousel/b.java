package com.google.android.material.carousel;

import android.text.Editable;
import com.google.android.material.shape.CornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.textfield.TextInputLayout;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final /* synthetic */ class b implements ShapeAppearanceModel.CornerSizeUnaryOperator, TextInputLayout.LengthCounter {
    @Override // com.google.android.material.shape.ShapeAppearanceModel.CornerSizeUnaryOperator
    public final CornerSize apply(CornerSize cornerSize) {
        return MaskableFrameLayout.lambda$setShapeAppearanceModel$0(cornerSize);
    }

    @Override // com.google.android.material.textfield.TextInputLayout.LengthCounter
    public final int countLength(Editable editable) {
        return TextInputLayout.lambda$new$0(editable);
    }
}
