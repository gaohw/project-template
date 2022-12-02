package com.ctsi.vip.lib.widget.richtext.ig;

import android.widget.TextView;

import com.ctsi.vip.lib.widget.richtext.ImageHolder;
import com.ctsi.vip.lib.widget.richtext.RichTextConfig;
import com.ctsi.vip.lib.widget.richtext.callback.ImageLoadNotify;
import com.ctsi.vip.lib.widget.richtext.drawable.DrawableWrapper;
import com.ctsi.vip.lib.widget.richtext.exceptions.ImageDecodeException;

/**
 * Created by zhou on 2017/2/20.
 * 本地图片加载器
 */

class LocalFileImageLoader extends AbstractImageLoader<String> implements Runnable {

    LocalFileImageLoader(ImageHolder holder, RichTextConfig config, TextView textView, DrawableWrapper drawableWrapper, ImageLoadNotify iln) {
        super(holder, config, textView, drawableWrapper, iln, SourceDecode.LOCAL_FILE_SOURCE_DECODE);
    }

    @Override
    public void run() {
        try {
            doLoadImage(holder.getSource());
        } catch (Exception e) {
            onFailure(new ImageDecodeException(e));
        } catch (OutOfMemoryError error) {
            onFailure(new ImageDecodeException(error));
        }
    }
}
