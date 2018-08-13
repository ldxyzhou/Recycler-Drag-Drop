package com.hunter.dragdrop.itemtouchhelp;

/**
 * @author：xinyu.zhou
 * @version: 2016/12/5 10:16
 * @ClassName:
 * @Description: ${todo}(这里用一句话描述这个类的作用)
 */
public interface ItemTouchAdapterHelper {

    boolean onSwapItem(int fromPosition, int toPosition);


    void onNextItem(int toPosition);


    void onConvert(int fromPosition, int toPosition);

}
