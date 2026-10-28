package v;

import kotlin.jvm.internal.k;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
public abstract class d extends c {
    public static final int aa(CharSequence charSequence, String str, int i2, boolean z2) {
        char upperCase;
        char upperCase2;
        k.g(charSequence, "<this>");
        if (!z2 && (charSequence instanceof String)) {
            return ((String) charSequence).indexOf(str, i2);
        }
        int length = charSequence.length();
        if (i2 < 0) {
            i2 = 0;
        }
        int length2 = charSequence.length();
        if (length > length2) {
            length = length2;
        }
        s.d dVar = new s.d(i2, length, 1);
        boolean z3 = charSequence instanceof String;
        int i3 = dVar.f499c;
        if (z3) {
            if (i2 <= i3) {
                while (true) {
                    String str2 = (String) charSequence;
                    int length3 = str.length();
                    if (!z2 ? str.regionMatches(0, str2, i2, length3) : str.regionMatches(z2, 0, str2, i2, length3)) {
                        break;
                    }
                    if (i2 != i3) {
                        i2++;
                    }
                }
                return i2;
            }
            return -1;
        }
        if (i2 <= i3) {
            loop1: while (true) {
                int length4 = str.length();
                if (i2 >= 0 && str.length() - length4 >= 0 && i2 <= charSequence.length() - length4) {
                    int i4 = 0;
                    while (true) {
                        if (i4 >= length4) {
                            break loop1;
                        }
                        char cCharAt = str.charAt(i4);
                        char cCharAt2 = charSequence.charAt(i2 + i4);
                        if (cCharAt != cCharAt2 && (!z2 || ((upperCase = Character.toUpperCase(cCharAt)) != (upperCase2 = Character.toUpperCase(cCharAt2)) && Character.toLowerCase(upperCase) != Character.toLowerCase(upperCase2)))) {
                            break;
                        }
                        i4++;
                    }
                }
                if (i2 != i3) {
                    i2++;
                }
            }
            return i2;
        }
        return -1;
    }

    public static String ab(String missingDelimiterValue) {
        k.g(missingDelimiterValue, "<this>");
        k.g(missingDelimiterValue, "missingDelimiterValue");
        int iLastIndexOf = missingDelimiterValue.lastIndexOf(46, missingDelimiterValue.length() - 1);
        if (iLastIndexOf == -1) {
            return missingDelimiterValue;
        }
        String strSubstring = missingDelimiterValue.substring(iLastIndexOf + 1, missingDelimiterValue.length());
        k.f(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
        return strSubstring;
    }
}
