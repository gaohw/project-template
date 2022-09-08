package com.ctsi.vip.lib.common.utils.unpeeklivedata.domain.message;

import com.ctsi.vip.lib.common.utils.unpeeklivedata.ui.callback.ProtectedUnPeekLiveData;

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
