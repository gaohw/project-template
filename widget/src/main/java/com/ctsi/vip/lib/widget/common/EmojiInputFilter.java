package com.ctsi.vip.lib.widget.common;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by : liqq
 * Time on :2022/4/18 16:18.
 * Description :
 * 过滤表情符号
 */
public class EmojiInputFilter implements InputFilter {

    String emojiEx = "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Pattern p1 = Pattern.compile(emojiEx);
        Matcher m1 = p1.matcher(source);
        if (m1.find()) {
            return "";
        }
        if (source.equals(" ")) {
            return "";
        }
        return source;
    }

}