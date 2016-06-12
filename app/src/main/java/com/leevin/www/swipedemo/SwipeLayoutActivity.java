package com.leevin.www.swipedemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by  Leevin
 * on 2016/6/12 ,21:19.
 */
public class SwipeLayoutActivity extends Activity implements SwipeLayout.DragListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_swipe);
        SwipeLayout swipeLayout = (SwipeLayout) findViewById(R.id.swipe_layout);
        swipeLayout.setDragListener(this);
    }


    @Override
    public void onOpened(SwipeLayout layout) {
        Toast.makeText(SwipeLayoutActivity.this, "onOpened", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClosed(SwipeLayout layout) {
        Toast.makeText(SwipeLayoutActivity.this, "onClosed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStartDrag(SwipeLayout layout) {
        Toast.makeText(SwipeLayoutActivity.this, "onStartDrag", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStartOpen(SwipeLayout layout) {
        Toast.makeText(SwipeLayoutActivity.this, "onStartOpen", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStartClose(SwipeLayout layout) {
        Toast.makeText(SwipeLayoutActivity.this, "onStartClose", Toast.LENGTH_SHORT).show();
    }
}
