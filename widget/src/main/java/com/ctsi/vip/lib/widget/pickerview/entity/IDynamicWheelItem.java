package com.ctsi.vip.lib.widget.pickerview.entity;

import com.ctsi.vip.lib.widget.wheelview.interfaces.IWheelItem;

/**
 * <p>
 * 动态添加 Wheel 时使用
 * </p>
 *
 * @author skymxc
 * <p>
 * date: 2020/11/12
 */
public interface IDynamicWheelItem extends IWheelItem {
    /**
     * @return 是否需要加载下一级
     */
    boolean isLoadNext();
}
