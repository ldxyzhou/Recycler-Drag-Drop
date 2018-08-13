package com.hunter.dragdrop.itemtouchhelp;

import android.graphics.Canvas;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * @author：xinyu.zhou
 * @version: 2016/12/5 10:22
 * @ClassName:
 * @Description: ${todo}(这里用一句话描述这个类的作用)
 */
public class CstToucherHelper extends ItemTouchHelper.Callback {
    private int positiveBoundary = 50;                                        //move边界最小值
    private int negativeBoundary = -50;                                       //move边界最大值
    private int currentPosition;                                              //当前位置
    private int toPositon = 0;                                                //下个一个item位置
    public static int code = 10086;                                                 //如果是当前code则什么都不做
    private static final float ALPHA_FULL = 1.0f;                             //透明度
    private ItemTouchAdapterHelper itemTouchAdapterHelper;                    //适配器实现的callback
    private boolean isConvert;                                                //是否要去对当前item做事件

    public CstToucherHelper(ItemTouchAdapterHelper adapter) {
        itemTouchAdapterHelper = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        if (source.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        // 通知适配器切换
        itemTouchAdapterHelper.onSwapItem(source.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {

    }


    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        currentPosition = viewHolder.getAdapterPosition();

        if (isCurrentlyActive) {
            getToposition(dY);

        } else {
            if (isConvert) {
                isConvert = false;
                itemTouchAdapterHelper.onConvert(currentPosition, toPositon);
            }
        }
        if (actionState != ItemTouchHelper.ACTION_STATE_SWIPE) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }


    public int getToposition(float dY) {
        isConvert = true;
        if ((dY >= positiveBoundary)) {
            toPositon = currentPosition + 1;
            itemTouchAdapterHelper.onNextItem(toPositon);
        } else if (dY <= negativeBoundary) {
            toPositon = currentPosition - 1;
            itemTouchAdapterHelper.onNextItem(toPositon);
        } else if (dY <= positiveBoundary && dY >= negativeBoundary) {
            toPositon = code;
            itemTouchAdapterHelper.onNextItem(toPositon);

        }
        return toPositon;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE && actionState != ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (viewHolder instanceof ItemTouchViewHelper) {
                ((ItemTouchViewHelper) viewHolder).onItemSelected();
                AnimationHelper.toTouchScale(viewHolder);
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setAlpha(ALPHA_FULL);
        if (viewHolder.itemView.getAnimation() != null) {
            AnimationHelper.toClearAnima(viewHolder);
        }
        if (viewHolder instanceof ItemTouchViewHelper) {
            ((ItemTouchViewHelper) viewHolder).onItemClear();


        }
    }
}
