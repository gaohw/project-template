package com.ctsi.android.lib.im.enums;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.StringDef;

/**
 * Class : Def
 * Create by GaoHW at 2022-12-30 8:36.
 * Description:
 */
public class Def {

    public static final String TYPE_TEXT = "text";
    public static final String TYPE_IMAGE = "image";
    public static final String TYPE_FILE = "file";

    @StringDef({TYPE_TEXT, TYPE_IMAGE, TYPE_FILE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MessageType {
    }
}
