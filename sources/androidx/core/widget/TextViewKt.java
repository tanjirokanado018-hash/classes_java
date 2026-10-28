package androidx.core.widget;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import g.i;
import kotlin.jvm.internal.l;
import p.r;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class TextViewKt {

    /* JADX INFO: renamed from: androidx.core.widget.TextViewKt$addTextChangedListener$1, reason: invalid class name */
    public static final class AnonymousClass1 extends l implements r {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        public AnonymousClass1() {
            super(4);
        }

        @Override // p.r
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
            invoke((CharSequence) obj, ((Number) obj2).intValue(), ((Number) obj3).intValue(), ((Number) obj4).intValue());
            return i.f423a;
        }

        public final void invoke(CharSequence charSequence, int i2, int i3, int i4) {
        }
    }

    /* JADX INFO: renamed from: androidx.core.widget.TextViewKt$addTextChangedListener$2, reason: invalid class name */
    public static final class AnonymousClass2 extends l implements r {
        public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

        public AnonymousClass2() {
            super(4);
        }

        @Override // p.r
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
            invoke((CharSequence) obj, ((Number) obj2).intValue(), ((Number) obj3).intValue(), ((Number) obj4).intValue());
            return i.f423a;
        }

        public final void invoke(CharSequence charSequence, int i2, int i3, int i4) {
        }
    }

    /* JADX INFO: renamed from: androidx.core.widget.TextViewKt$addTextChangedListener$3, reason: invalid class name */
    public static final class AnonymousClass3 extends l implements p.l {
        public static final AnonymousClass3 INSTANCE = new AnonymousClass3();

        public AnonymousClass3() {
            super(1);
        }

        @Override // p.l
        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((Editable) obj);
            return i.f423a;
        }

        public final void invoke(Editable editable) {
        }
    }

    public static final TextWatcher addTextChangedListener(TextView textView, r rVar, r rVar2, p.l lVar) {
        TextViewKt$addTextChangedListener$textWatcher$1 textViewKt$addTextChangedListener$textWatcher$1 = new TextViewKt$addTextChangedListener$textWatcher$1(lVar, rVar, rVar2);
        textView.addTextChangedListener(textViewKt$addTextChangedListener$textWatcher$1);
        return textViewKt$addTextChangedListener$textWatcher$1;
    }

    public static /* synthetic */ TextWatcher addTextChangedListener$default(TextView textView, r rVar, r rVar2, p.l lVar, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            rVar = AnonymousClass1.INSTANCE;
        }
        if ((i2 & 2) != 0) {
            rVar2 = AnonymousClass2.INSTANCE;
        }
        if ((i2 & 4) != 0) {
            lVar = AnonymousClass3.INSTANCE;
        }
        TextViewKt$addTextChangedListener$textWatcher$1 textViewKt$addTextChangedListener$textWatcher$1 = new TextViewKt$addTextChangedListener$textWatcher$1(lVar, rVar, rVar2);
        textView.addTextChangedListener(textViewKt$addTextChangedListener$textWatcher$1);
        return textViewKt$addTextChangedListener$textWatcher$1;
    }

    public static final TextWatcher doAfterTextChanged(TextView textView, final p.l lVar) {
        TextWatcher textWatcher = new TextWatcher() { // from class: androidx.core.widget.TextViewKt$doAfterTextChanged$$inlined$addTextChangedListener$default$1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                lVar.invoke(editable);
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }
        };
        textView.addTextChangedListener(textWatcher);
        return textWatcher;
    }

    public static final TextWatcher doBeforeTextChanged(TextView textView, final r rVar) {
        TextWatcher textWatcher = new TextWatcher() { // from class: androidx.core.widget.TextViewKt$doBeforeTextChanged$$inlined$addTextChangedListener$default$1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                rVar.invoke(charSequence, Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4));
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }
        };
        textView.addTextChangedListener(textWatcher);
        return textWatcher;
    }

    public static final TextWatcher doOnTextChanged(TextView textView, final r rVar) {
        TextWatcher textWatcher = new TextWatcher() { // from class: androidx.core.widget.TextViewKt$doOnTextChanged$$inlined$addTextChangedListener$default$1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                rVar.invoke(charSequence, Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4));
            }
        };
        textView.addTextChangedListener(textWatcher);
        return textWatcher;
    }
}
