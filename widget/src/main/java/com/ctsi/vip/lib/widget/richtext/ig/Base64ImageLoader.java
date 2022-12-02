package com.ctsi.vip.lib.widget.richtext.ig;

import android.widget.TextView;

import com.ctsi.vip.lib.widget.richtext.ImageHolder;
import com.ctsi.vip.lib.widget.richtext.RichTextConfig;
import com.ctsi.vip.lib.widget.richtext.callback.ImageLoadNotify;
import com.ctsi.vip.lib.widget.richtext.drawable.DrawableWrapper;
import com.ctsi.vip.lib.widget.richtext.exceptions.ImageDecodeException;
import com.ctsi.vip.lib.widget.richtext.ext.Base64;

/**
 * Created by zhou on 2016/12/9.
 * Base64格式图片解析器
 */
class Base64ImageLoader extends AbstractImageLoader<byte[]> implements Runnable {

    Base64ImageLoader(ImageHolder holder, RichTextConfig config, TextView textView, DrawableWrapper drawableWrapper, ImageLoadNotify iln) {
        super(holder, config, textView, drawableWrapper, iln, SourceDecode.BASE64_SOURCE_DECODE);
    }

    @Override
    public void run() {
        try {
            byte[] src = Base64.decode(holder.getSource());
            doLoadImage(src);
        } catch (Exception e) {
            onFailure(new ImageDecodeException(e));
        } catch (OutOfMemoryError error) {
            onFailure(new ImageDecodeException(error));
        }
    }

}