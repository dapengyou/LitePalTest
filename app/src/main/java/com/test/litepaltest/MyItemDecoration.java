package com.test.litepaltest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import java.util.List;

/**
 * 参考文章（http://www.jianshu.com/p/0d49d9f51d2c）
 * 第二种方法
 * Created by lady_zhou on 2017/8/25.
 */

public class MyItemDecoration extends RecyclerView.ItemDecoration {
    private List<BookBean> mDatas;

    private Paint mPaint;
    private Rect mBounds;//用于存放测量文字Rect

    //title的高
    private int mTitleHeight;
    //头标的背景色
    private static int COLOR_TITLE_BG = Color.parseColor("#d0d0dd");
    //头标字体的颜色
    private static int COLOR_TITLE_FONT = Color.parseColor("#FF000000");
    //title字体大小
    private static int mTitleFontSize;

    /**
     * 初始化
     *
     * @param context 上下文
     * @param datas   实体bean list
     */
    public MyItemDecoration(Context context, List<BookBean> datas) {
        super();
        mDatas = datas;
        mPaint = new Paint();
        mBounds = new Rect();

        //TypedValue.applyDimension是一个将各种单位的值转换为像素的方法
        //DisplayMetrics是一个获取屏幕信息的类，density是设备密度。
        // 在此可见applyDemension，第一个参数是指第二个参数值得单位，并将该单位的值转换为px

        mTitleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
        mTitleFontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, context.getResources().getDisplayMetrics());

        mPaint.setTextSize(mTitleFontSize);

        // Paint.setDither()   该方法是设置防抖动

        //该方法作用是抗锯齿
        mPaint.setAntiAlias(true);


    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();//得到子孩子的数量

        for (int i = 0; i < childCount; i++) {
            //返回指定位置的视图
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int position = params.getViewLayoutPosition();
            //我记得Rv的item position在重置时可能为-1.保险点判断一下吧
            if (position > -1) {
                if (position == 0) {//等于0肯定要有title的
                    drawTitleArea(c, left, right, child, params, position);

                } else {//其他的通过判断
                    if (0 != mDatas.get(position).getId() && !String.valueOf(mDatas.get(position).getId()).equals(String.valueOf(mDatas.get(position - 1).getId()))) {
                        //不为空 且跟前一个tag不一样了，说明是新的分类，也要title
                        drawTitleArea(c, left, right, child, params, position);
                    } else {
                        //none
                    }
                }
            }
        }
    }

    /**
     * 绘制Title区域背景和文字的方法
     *
     * @param c
     * @param left
     * @param right
     * @param child
     * @param params
     * @param position
     */
    private void drawTitleArea(Canvas c, int left, int right, View child, RecyclerView.LayoutParams params, int position) {//最先调用，绘制在最下层
        mPaint.setColor(COLOR_TITLE_BG);
        //绘制背景
        c.drawRect(left, child.getTop() - params.topMargin - mTitleHeight, right, child.getTop() - params.topMargin, mPaint);
        mPaint.setColor(COLOR_TITLE_FONT);
/*
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;*/

        mPaint.getTextBounds(String.valueOf(mDatas.get(position).getId()), 0, String.valueOf(mDatas.get(position).getId()).length(), mBounds);
        //child.getPaddingLeft()可以控制字距离左边的位置
        c.drawText(String.valueOf(mDatas.get(position).getId()), child.getPaddingLeft(), child.getTop() - params.topMargin - (mTitleHeight / 2 - mBounds.height() / 2), mPaint);
    }

    /**
     * 产生悬停效果
     *
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {//最后调用 绘制在最上层
        int pos = ((LinearLayoutManager) (parent.getLayoutManager())).findFirstVisibleItemPosition();

        String tag = String.valueOf(mDatas.get(pos).getId());
        //View child = parent.getChildAt(pos);
        View child = parent.findViewHolderForLayoutPosition(pos).itemView;//出现一个奇怪的bug，有时候child为空，所以将 child = parent.getChildAt(i)。-》 parent.findViewHolderForLayoutPosition(pos).itemView
        mPaint.setColor(COLOR_TITLE_BG);
        c.drawRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getRight() - parent.getPaddingRight(), parent.getPaddingTop() + mTitleHeight, mPaint);
        mPaint.setColor(COLOR_TITLE_FONT);
        mPaint.getTextBounds(tag, 0, tag.length(), mBounds);
        c.drawText(tag, child.getPaddingLeft(),
                parent.getPaddingTop() + mTitleHeight - (mTitleHeight / 2 - mBounds.height() / 2),
                mPaint);
    }

    /**
     * 通过parent获取postion信息，通过postion拿到数据里的每个bean里的分类，因为数据集已经有序，
     * 如果与前一个分类不一样，说明是一个新的分类，
     * 则需要绘制头部outRect.set(0, mTitleHeight, 0, 0);，否则不需要outRect.set(0, 0, 0, 0);。
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        //我记得Rv的item position在重置时可能为-1.保险点判断一下吧
        if (position > -1) {
            if (position == 0) {//等于0肯定要有title的
                outRect.set(0, mTitleHeight, 0, 0);
            } else {//其他的通过判断
                if (0 != mDatas.get(position).getId() && !String.valueOf(mDatas.get(position).getId()).equals(String.valueOf(mDatas.get(position - 1).getId()))) {
                    outRect.set(0, mTitleHeight, 0, 0);//不为空 且跟前一个id不一样了，说明是新的分类，也要title
                } else {
                    outRect.set(0, 0, 0, 0);
                }
            }
        }
    }

}
