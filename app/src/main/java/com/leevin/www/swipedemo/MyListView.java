package com.leevin.www.swipedemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by  Leevin
 * on 2016/6/12 ,23:34.
 */
public class MyListView extends ListView {

    private ArrayList<SwipeLayout> openedLayout;

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (isInterCept()) {
                closeAll();
                return true;
            } else {
                return super.onInterceptTouchEvent(ev);
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    private boolean isInterCept() {
        return isInterCept;
    }
    private boolean isInterCept;
    public void setIsIntercept(Boolean s, ArrayList<SwipeLayout> openedLayout ) {
        this.isInterCept = s;
        this.openedLayout = openedLayout;
    }

    private void closeAll() {
        for (int i = 0; i < openedLayout.size(); i++) {
            SwipeLayout swipeLayout = openedLayout.get(i);
            swipeLayout.close();
        }
    }
}
