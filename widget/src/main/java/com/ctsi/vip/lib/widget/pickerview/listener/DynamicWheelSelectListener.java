package com.ctsi.vip.lib.widget.pickerview.listener;

import com.ctsi.vip.lib.widget.pickerview.entity.IDynamicWheelItem;

/**
 * <p>
 *
 * </p>
 *
 * @author skymxc
 * <p>
 * date: 2020/11/12
 */
public interface DynamicWheelSelectListener<T extends IDynamicWheelItem> extends MultiWheelSelectListener<T> {
    /**
     * 加载下一级的数据
     *
     * @param item      当前数据
     * @param nextLevel 下一级的层级
     */
    void loadNextItems(T item, int nextLevel);
}
