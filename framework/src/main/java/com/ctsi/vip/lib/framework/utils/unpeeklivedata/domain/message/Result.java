package com.ctsi.vip.lib.framework.utils.unpeeklivedata.domain.message;

import com.ctsi.vip.lib.framework.utils.unpeeklivedata.ui.callback.ProtectedUnPeekLiveData;
import com.ctsi.vip.lib.framework.utils.unpeeklivedata.ui.callback.ProtectedUnPeekLiveData;

/**
 * Create by KunMinX at 2022/5/31
 */
public class Result<T> extends ProtectedUnPeekLiveData<T> {

    public Result(T value) {
        super(value);
    }

    public Result() {
        super();
    }

}
