package com.ctsi.vip.lib.widget.richtext.ig;

import android.widget.TextView;

import com.ctsi.vip.lib.widget.richtext.ImageHolder;
import com.ctsi.vip.lib.widget.richtext.RichTextConfig;
import com.ctsi.vip.lib.widget.richtext.cache.BitmapPool;
import com.ctsi.vip.lib.widget.richtext.callback.ImageLoadNotify;
import com.ctsi.vip.lib.widget.richtext.drawable.DrawableWrapper;
import com.ctsi.vip.lib.widget.richtext.exceptions.ImageDecodeException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zhou on 2016/12/8.
 * 网络图片下载完成后被回调
 */
class CallbackImageLoader extends AbstractImageLoader<InputStream> {

    CallbackImageLoader(ImageHolder holder, RichTextConfig config, TextView textView, DrawableWrapper drawableWrapper, ImageLoadNotify iln) {
        super(holder, config, textView, drawableWrapper, iln, SourceDecode.INPUT_STREAM_DECODE);
    }

    void onImageDownloadFinish(String key, Exception exception) {
        if (exception != null) {
            onFailure(exception);
            return;
        }
        try {
            InputStream inputStream = BitmapPool.getPool().readBitmapFromTemp(key);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            doLoadImage(bufferedInputStream);

            bufferedInputStream.close();
            inputStream.close();
        } catch (IOException e) {
            onFailure(e);
        } catch (OutOfMemoryError error) {
            onFailure(new ImageDecodeException(error));
        }
    }

}

