package com.leevin.www.swipedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> data;
    private MyListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initViews();
    }

    private void initData() {
        data = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            data.add("item" + i);
        }
    }

    private void initViews() {
        listView = (MyListView) findViewById(R.id.list);
        listView.setAdapter(new MyAdapter());

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    closeAll();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        if (openedLayout.size() > 0) {
            listView.setIsIntercept(true,openedLayout);
        } else {
            listView.setIsIntercept(false,openedLayout);
        }
    }

    public void open(View view) {
        Intent intent = new Intent(this, SwipeLayoutActivity.class);
        startActivity(intent);
    }




    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_swipe, null);
                holder = new ViewHolder();
                holder.tvName = (TextView) convertView.findViewById(R.id.tvname);
                holder.swipeLayout = (SwipeLayout) convertView.findViewById(R.id.swipe_layout);
                holder.tvcall = (TextView) convertView.findViewById(R.id.tvcall);
                holder.tvdelete = (TextView) convertView.findViewById(R.id.tvdelete);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvcall.setOnClickListener(clickListener);
            holder.tvcall.setTag(data.get(position));
            holder.swipeLayout.setDragListener(mDragListener);
            return convertView;
        }
    }

    class ViewHolder {
        TextView tvName;
        SwipeLayout swipeLayout;
        TextView tvdelete;
        TextView tvcall;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this, "" +v.getTag(), Toast.LENGTH_SHORT).show();
        }
    };

    public boolean hasOpened() {
      return openedLayout.size() > 0;
    }
    private ArrayList<SwipeLayout> openedLayout = new ArrayList<SwipeLayout>();
    private SwipeLayout.DragListener mDragListener = new SwipeLayout.DragListener() {
        @Override
        public void onOpened(SwipeLayout layout) {
            openedLayout.add(layout);
            if (openedLayout.size() > 0) {
                listView.setIsIntercept(true,openedLayout);
            } else {
                listView.setIsIntercept(false,openedLayout);
            }
        }

        @Override
        public void onClosed(SwipeLayout layout) {
            openedLayout.remove(layout);
            if (openedLayout.size() > 0) {
                listView.setIsIntercept(true,openedLayout);
            } else {
                listView.setIsIntercept(false,openedLayout);
            }
        }

        @Override
        public void onStartDrag(SwipeLayout layout) {

        }

        @Override
        public void onStartOpen(SwipeLayout layout) {
            closeAll();
        }

        @Override
        public void onStartClose(SwipeLayout layout) {

        }
    };

    private void closeAll() {
        for (int i = 0; i < openedLayout.size(); i++) {
            SwipeLayout swipeLayout = openedLayout.get(i);
            swipeLayout.close();
        }
    }
}
