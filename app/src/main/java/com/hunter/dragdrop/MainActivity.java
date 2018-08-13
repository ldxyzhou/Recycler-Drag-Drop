package com.hunter.dragdrop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.hunter.dragdrop.itemtouchhelp.CstToucherHelper;
import com.hunter.dragdrop.itemtouchhelp.OnStartDragListener;

import java.util.ArrayList;
import java.util.List;
/**
 * @author：xinyu.zhou
 * @version: 2016/12/5 10:43
 * @ClassName:
 * @Description: ${todo}(这里用一句话描述这个类的作用)
 */
public class MainActivity extends AppCompatActivity implements OnStartDragListener {
    private ItemTouchHelper mItemTouchHelper;
    private CstToucherHelper toucherHelper;
    private RecyclerView recyclerView;
    private MyDragDropAdapter dragDropAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private List<String> contextList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initData();
        recyclerView = (RecyclerView) findViewById(R.id.drag_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(this);
        dragDropAdapter = new MyDragDropAdapter(this, recyclerView, mLinearLayoutManager, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(dragDropAdapter);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        dragDropAdapter.setData(contextList);
        toucherHelper = new CstToucherHelper(dragDropAdapter);
        mItemTouchHelper = new ItemTouchHelper(toucherHelper);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
    private void initData(){
        for (int i=0;i<10;i++){
            contextList.add("item "+ i);
        }
    }

}
