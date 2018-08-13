package com.hunter.dragdrop.itemtouchhelp;

import android.support.v7.widget.RecyclerView;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

/**
 * @author：xinyu.zhou
 * @version: 2016/12/5 10:36
 * @ClassName:
 * @Description: ${todo}(这里用一句话描述这个类的作用)
 */
public class AnimationHelper {

    public static void toTouchScale(RecyclerView.ViewHolder viewHolder){

        final ScaleAnimation animation = new ScaleAnimation(1.0f, 0.9f, 1.0f, 0.9f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(100);
        animation.setFillAfter(true);
        viewHolder.itemView.startAnimation(animation);
    }
    public static void toClearAnima(final RecyclerView.ViewHolder viewHolder){

        final ScaleAnimation animation = new ScaleAnimation(0.9f, 1.0f, 0.9f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(100);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                viewHolder.itemView.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        viewHolder.itemView.startAnimation(animation);
    }
}
