package com.leevin.www.swipedemo;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by  Leevin
 * on 2016/6/12 ,21:22.
 */
public class SwipeLayout extends FrameLayout {

    private ViewDragHelper mDragHelper;
    private ViewDragHelper.Callback mCallBack = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == contentView) {
                if (left > 0) {
                    left = 0;
                } else if (left < -rightViewWidth) {
                    left = -rightViewWidth;
                }
            } else if (child == rightView) {
                if (left < contentViewWidth - rightViewWidth) {
                    left =  contentViewWidth - rightViewWidth;
                } else if (left > contentViewWidth) {
                    left = contentViewWidth;
                }
            }
            return left;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (changedView == contentView) {
                rightView.offsetLeftAndRight(dx);
            } else if (changedView == rightView) {
                contentView.offsetLeftAndRight(dx);
            }
            // 为了兼容4.0以前的版本
            updateState();
            invalidate();
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (xvel == 0) {
                int currentLeft = rightView.getLeft();
                if (currentLeft > contentViewWidth - rightViewWidth / 2 ) {
                    // 关闭
                    close();
                } else {
                    // 打开
                    open();
                }
            } else if (xvel > 0) {
                // 关闭
                close();
            } else if (xvel < 0) {
                // open
                open();
            }
        }
    };

    public void open() {
        mDragHelper.smoothSlideViewTo(rightView, contentViewWidth - rightViewWidth, 0);
        invalidate();
    }

    public void close() {
        mDragHelper.smoothSlideViewTo(rightView, contentViewWidth, 0);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    private View contentView;
    private View rightView;
    private int contentViewWidth;
    private int rightViewWidth;
    private int cellHeight;

    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mDragHelper = ViewDragHelper.create(this, mCallBack);
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return mDragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        contentViewWidth = contentView.getMeasuredWidth();
        rightViewWidth = rightView.getMeasuredWidth();
        cellHeight = contentView.getMeasuredHeight();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() < 2) {
            throw new RuntimeException("must have two child elements");
        }
        contentView = getChildAt(0);
        rightView = getChildAt(1);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        rightView.layout(contentViewWidth,0,contentViewWidth + rightViewWidth,cellHeight);
    }


    private void updateState() {
         int currentLeft = rightView.getLeft();
        DragState preState = mCurrentState;
        if (currentLeft == contentViewWidth) {
            mCurrentState = DragState.CLOSED;
        } else if (currentLeft == contentViewWidth - rightViewWidth) {
            mCurrentState = DragState.OPENED;
        } else {
            mCurrentState = DragState.DRAGING;
        }
        // 更新状态
        if (preState != mCurrentState && mListener != null) {
            if (mCurrentState == DragState.CLOSED) {
                mListener.onClosed(this);
            } else if (mCurrentState == DragState.OPENED) {
                mListener.onOpened(this);
            } else  {
                if (preState == DragState.OPENED) {
                    mListener.onStartClose(this);
                } else if (preState == DragState.CLOSED) {
                    mListener.onStartOpen(this);
                }
            }
        }
    }

    private DragState mCurrentState = DragState.CLOSED;
    public enum DragState{
        OPENED ,CLOSED,DRAGING
    }

    public interface DragListener{
        public void onOpened(SwipeLayout layout);

        public void onClosed(SwipeLayout layout);

        public void onStartDrag(SwipeLayout layout);

        public void onStartOpen(SwipeLayout layout);

        public void onStartClose(SwipeLayout layout);
    }

    private DragListener mListener;
    public void setDragListener(DragListener listener) {
        this.mListener = listener;
    }
}
