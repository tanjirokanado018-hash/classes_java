package androidx.core.text;

import java.nio.CharBuffer;
import java.util.Locale;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public final class TextDirectionHeuristicsCompat {
    public static final TextDirectionHeuristicCompat ANYRTL_LTR;
    public static final TextDirectionHeuristicCompat FIRSTSTRONG_LTR;
    public static final TextDirectionHeuristicCompat FIRSTSTRONG_RTL;
    public static final TextDirectionHeuristicCompat LOCALE;
    public static final TextDirectionHeuristicCompat LTR = new TextDirectionHeuristicInternal(null, false);
    public static final TextDirectionHeuristicCompat RTL = new TextDirectionHeuristicInternal(null, true);
    private static final int STATE_FALSE = 1;
    private static final int STATE_TRUE = 0;
    private static final int STATE_UNKNOWN = 2;

    public static class AnyStrong implements TextDirectionAlgorithm {
        static final AnyStrong INSTANCE_RTL = new AnyStrong(true);
        private final boolean mLookForRtl;

        private AnyStrong(boolean z2) {
            this.mLookForRtl = z2;
        }

        @Override // androidx.core.text.TextDirectionHeuristicsCompat.TextDirectionAlgorithm
        public int checkRtl(CharSequence charSequence, int i2, int i3) {
            int i4 = i3 + i2;
            boolean z2 = false;
            while (i2 < i4) {
                int iIsRtlText = TextDirectionHeuristicsCompat.isRtlText(Character.getDirectionality(charSequence.charAt(i2)));
                if (iIsRtlText != 0) {
                    if (iIsRtlText != 1) {
                        continue;
                    } else if (!this.mLookForRtl) {
                        return 1;
                    }
                    i2++;
                } else if (this.mLookForRtl) {
                    return 0;
                }
                z2 = true;
                i2++;
            }
            if (z2) {
                return this.mLookForRtl ? 1 : 0;
            }
            return 2;
        }
    }

    public static class FirstStrong implements TextDirectionAlgorithm {
        static final FirstStrong INSTANCE = new FirstStrong();

        private FirstStrong() {
        }

        @Override // androidx.core.text.TextDirectionHeuristicsCompat.TextDirectionAlgorithm
        public int checkRtl(CharSequence charSequence, int i2, int i3) {
            int i4 = i3 + i2;
            int iIsRtlTextOrFormat = 2;
            while (i2 < i4 && iIsRtlTextOrFormat == 2) {
                iIsRtlTextOrFormat = TextDirectionHeuristicsCompat.isRtlTextOrFormat(Character.getDirectionality(charSequence.charAt(i2)));
                i2++;
            }
            return iIsRtlTextOrFormat;
        }
    }

    public interface TextDirectionAlgorithm {
        int checkRtl(CharSequence charSequence, int i2, int i3);
    }

    public static abstract class TextDirectionHeuristicImpl implements TextDirectionHeuristicCompat {
        private final TextDirectionAlgorithm mAlgorithm;

        public TextDirectionHeuristicImpl(TextDirectionAlgorithm textDirectionAlgorithm) {
            this.mAlgorithm = textDirectionAlgorithm;
        }

        private boolean doCheck(CharSequence charSequence, int i2, int i3) {
            int iCheckRtl = this.mAlgorithm.checkRtl(charSequence, i2, i3);
            if (iCheckRtl == 0) {
                return true;
            }
            if (iCheckRtl != 1) {
                return defaultIsRtl();
            }
            return false;
        }

        public abstract boolean defaultIsRtl();

        @Override // androidx.core.text.TextDirectionHeuristicCompat
        public boolean isRtl(CharSequence charSequence, int i2, int i3) {
            if (charSequence == null || i2 < 0 || i3 < 0 || charSequence.length() - i3 < i2) {
                throw new IllegalArgumentException();
            }
            return this.mAlgorithm == null ? defaultIsRtl() : doCheck(charSequence, i2, i3);
        }

        @Override // androidx.core.text.TextDirectionHeuristicCompat
        public boolean isRtl(char[] cArr, int i2, int i3) {
            return isRtl(CharBuffer.wrap(cArr), i2, i3);
        }
    }

    public static class TextDirectionHeuristicInternal extends TextDirectionHeuristicImpl {
        private final boolean mDefaultIsRtl;

        public TextDirectionHeuristicInternal(TextDirectionAlgorithm textDirectionAlgorithm, boolean z2) {
            super(textDirectionAlgorithm);
            this.mDefaultIsRtl = z2;
        }

        @Override // androidx.core.text.TextDirectionHeuristicsCompat.TextDirectionHeuristicImpl
        public boolean defaultIsRtl() {
            return this.mDefaultIsRtl;
        }
    }

    public static class TextDirectionHeuristicLocale extends TextDirectionHeuristicImpl {
        static final TextDirectionHeuristicLocale INSTANCE = new TextDirectionHeuristicLocale();

        public TextDirectionHeuristicLocale() {
            super(null);
        }

        @Override // androidx.core.text.TextDirectionHeuristicsCompat.TextDirectionHeuristicImpl
        public boolean defaultIsRtl() {
            return TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
        }
    }

    static {
        FirstStrong firstStrong = FirstStrong.INSTANCE;
        FIRSTSTRONG_LTR = new TextDirectionHeuristicInternal(firstStrong, false);
        FIRSTSTRONG_RTL = new TextDirectionHeuristicInternal(firstStrong, true);
        ANYRTL_LTR = new TextDirectionHeuristicInternal(AnyStrong.INSTANCE_RTL, false);
        LOCALE = TextDirectionHeuristicLocale.INSTANCE;
    }

    private TextDirectionHeuristicsCompat() {
    }

    public static int isRtlText(int i2) {
        if (i2 != 0) {
            return (i2 == 1 || i2 == 2) ? 0 : 2;
        }
        return 1;
    }

    public static int isRtlTextOrFormat(int i2) {
        if (i2 != 0) {
            if (i2 == 1 || i2 == 2) {
                return 0;
            }
            switch (i2) {
                case 14:
                case 15:
                    break;
                case 16:
                case 17:
                    return 0;
                default:
                    return 2;
            }
        }
        return 1;
    }
}
