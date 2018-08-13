package com.hunter.dragdrop;

import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.os.Vibrator;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hunter.dragdrop.itemtouchhelp.CstToucherHelper;
import com.hunter.dragdrop.itemtouchhelp.ItemTouchAdapterHelper;
import com.hunter.dragdrop.itemtouchhelp.ItemTouchViewHelper;
import com.hunter.dragdrop.itemtouchhelp.OnStartDragListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author：xinyu.zhou
 * @version: 2016/12/5 10:43
 * @ClassName:
 * @Description: ${todo}(这里用一句话描述这个类的作用)
 */
public class MyDragDropAdapter  extends RecyclerView.Adapter<MyDragDropAdapter.ItemViewHolder> implements ItemTouchAdapterHelper {

    private OnStartDragListener mDragStartListener;
    private View preView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private Vibrator vibrator;
    private Context context;
    private List<String> contextList = new ArrayList<>();

    public MyDragDropAdapter(Context context, RecyclerView view, LinearLayoutManager linearLayoutManager, OnStartDragListener dragStartListener){
        mDragStartListener = dragStartListener;
        this.recyclerView = view;
        this.linearLayoutManager = linearLayoutManager;
        this.context = context;
        vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.touch_item, parent, false);
        return new ItemViewHolder(view);
    }
    public void setData(List<String> list) {
        this.contextList = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        Log.d("MyDragDropAdapter","----------"+contextList.get(position));
        holder.textView.setText(contextList.get(position));
        holder.handleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return contextList == null ? 0 : contextList.size();
    }

    @Override
    public boolean onSwapItem(int fromPosition, int toPosition) {
        Toast.makeText(context,"fromPosition="+fromPosition+"-----------"+"toPosition="+toPosition,Toast.LENGTH_LONG).show();
        Collections.swap(contextList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        recyclerView();
        return true;
    }

    public void recyclerView() {
        if (preView != null) {
            preView.setBackgroundColor(0);
        }
    }
    @Override
    public void onNextItem(int toPosition) {
        recyclerView();
        if (toPosition != CstToucherHelper.code) {
            ItemViewHolder viewHolder = getCurrentHolder(toPosition);
            if (viewHolder != null) {
                View nextview = viewHolder.mainView;
                if (nextview != null) {
                    nextview.setBackgroundColor(Color.LTGRAY);
                    preView = nextview;
                }
            }
        }

    }

    public ItemViewHolder getCurrentHolder(int position) {
        int firstItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        if (position - firstItemPosition >= 0) {
            //得到要更新的item的view
            View view = recyclerView.getChildAt(position - firstItemPosition);
            if (view != null) {
                if (null != recyclerView.getChildViewHolder(view)) {
                    ItemViewHolder viewHolder = (ItemViewHolder) recyclerView.getChildViewHolder(view);
                    return viewHolder;
                }
            }
        }
        return null;
    }

    @Override
    public void onConvert(int fromPosition, int toPosition) {
        Toast.makeText(context,"fromPosition="+fromPosition+"-----------"+"toPosition="+toPosition,Toast.LENGTH_LONG).show();

    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements  ItemTouchViewHelper {
        public final TextView textView;
        public final ImageView handleView;
        public final RelativeLayout mainView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mainView=(RelativeLayout)itemView.findViewById(R.id.mian);
            textView = (TextView) itemView.findViewById(R.id.text);
            handleView = (ImageView) itemView.findViewById(R.id.handle);

        }
        @Override
        public void onItemSelected() {
            vibrator.vibrate(200);
            itemView.setBackground(context.getResources().getDrawable(R.drawable.touch_item_shape));
        }

        @Override
        public void onItemClear() {
            itemView.setBackground(null);
            recyclerView();
        }
    }




}
