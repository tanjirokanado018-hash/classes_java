package androidx.core.app;

import android.content.Context;
import android.util.Xml;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: /home/ubuntu/upload/classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class AppLocalesStorageHelper {
    static final String APPLICATION_LOCALES_RECORD_FILE = "androidx.appcompat.app.AppCompatDelegate.application_locales_record_file";
    static final boolean DEBUG = false;
    static final String LOCALE_RECORD_ATTRIBUTE_TAG = "application_locales";
    static final String LOCALE_RECORD_FILE_TAG = "locales";
    static final String TAG = "AppLocalesStorageHelper";
    private static final Object sAppLocaleStorageSync = new Object();

    private AppLocalesStorageHelper() {
    }

    public static void persistLocales(@NonNull Context context, @NonNull String str) {
        synchronized (sAppLocaleStorageSync) {
            if (str.equals("")) {
                context.deleteFile(APPLICATION_LOCALES_RECORD_FILE);
                return;
            }
            try {
                FileOutputStream fileOutputStreamOpenFileOutput = context.openFileOutput(APPLICATION_LOCALES_RECORD_FILE, 0);
                XmlSerializer xmlSerializerNewSerializer = Xml.newSerializer();
                try {
                    xmlSerializerNewSerializer.setOutput(fileOutputStreamOpenFileOutput, null);
                    xmlSerializerNewSerializer.startDocument("UTF-8", Boolean.TRUE);
                    xmlSerializerNewSerializer.startTag(null, LOCALE_RECORD_FILE_TAG);
                    xmlSerializerNewSerializer.attribute(null, LOCALE_RECORD_ATTRIBUTE_TAG, str);
                    xmlSerializerNewSerializer.endTag(null, LOCALE_RECORD_FILE_TAG);
                    xmlSerializerNewSerializer.endDocument();
                    if (fileOutputStreamOpenFileOutput != null) {
                        try {
                            fileOutputStreamOpenFileOutput.close();
                        } catch (IOException unused) {
                        }
                    }
                } catch (Exception unused2) {
                    if (fileOutputStreamOpenFileOutput != null) {
                        fileOutputStreamOpenFileOutput.close();
                    }
                } catch (Throwable th) {
                    if (fileOutputStreamOpenFileOutput != null) {
                        try {
                            fileOutputStreamOpenFileOutput.close();
                        } catch (IOException unused3) {
                        }
                    }
                    throw th;
                }
            } catch (FileNotFoundException unused4) {
            }
        }
    }

    @NonNull
    public static String readLocales(@NonNull Context context) {
        String attributeValue;
        synchronized (sAppLocaleStorageSync) {
            attributeValue = "";
            try {
                FileInputStream fileInputStreamOpenFileInput = context.openFileInput(APPLICATION_LOCALES_RECORD_FILE);
                try {
                    XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
                    xmlPullParserNewPullParser.setInput(fileInputStreamOpenFileInput, "UTF-8");
                    int depth = xmlPullParserNewPullParser.getDepth();
                    while (true) {
                        int next = xmlPullParserNewPullParser.next();
                        if (next != 1 && (next != 3 || xmlPullParserNewPullParser.getDepth() > depth)) {
                            if (next != 3 && next != 4 && xmlPullParserNewPullParser.getName().equals(LOCALE_RECORD_FILE_TAG)) {
                                attributeValue = xmlPullParserNewPullParser.getAttributeValue(null, LOCALE_RECORD_ATTRIBUTE_TAG);
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                    if (fileInputStreamOpenFileInput != null) {
                        try {
                            fileInputStreamOpenFileInput.close();
                        } catch (IOException unused) {
                        }
                    }
                } catch (IOException | XmlPullParserException unused2) {
                    if (fileInputStreamOpenFileInput != null) {
                        fileInputStreamOpenFileInput.close();
                    }
                } catch (Throwable th) {
                    if (fileInputStreamOpenFileInput != null) {
                        try {
                            fileInputStreamOpenFileInput.close();
                        } catch (IOException unused3) {
                        }
                    }
                    throw th;
                }
                if (attributeValue.isEmpty()) {
                    context.deleteFile(APPLICATION_LOCALES_RECORD_FILE);
                }
            } catch (FileNotFoundException unused4) {
                return "";
            }
        }
        return attributeValue;
    }
}
