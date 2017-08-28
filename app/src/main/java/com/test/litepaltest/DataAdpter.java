package com.test.litepaltest;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by lady_zhou on 2017/8/25.
 */

public class DataAdpter extends BaseQuickAdapter<BookBean, BaseViewHolder> {
    // RecyclerView 的第一个item，肯定是展示StickyLayout的.
    public static final int FIRST_STICKY_VIEW = 1;
    // RecyclerView 除了第一个item以外，要展示StickyLayout的.
    public static final int HAS_STICKY_VIEW = 2;
    // RecyclerView 的不展示StickyLayout的item.
    public static final int NONE_STICKY_VIEW = 3;

    private List<BookBean> mBookList;

    public DataAdpter(@Nullable List<BookBean> data) {
        super(R.layout.item_bookadpter, data);
        mBookList = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, BookBean item) {
        //第一种方法
//        helper.setText(R.id.tv_sticky_header_view,String.valueOf(item.getId()));
        helper.setText(R.id.tv_bookname, item.getBookName());
        helper.setText(R.id.tv_authorname, item.getAuthorName());


    }
    //第一种方法
//    @Override
//    public void onBindViewHolder(BaseViewHolder holder, int position, List<Object> payloads) {
//        super.onBindViewHolder(holder, position, payloads);
//        if (position == 0) {
//            holder.getConvertView().findViewById(R.id.tv_sticky_header_view).setVisibility(View.GONE);
////            holder.setText(R.id.tv_sticky_header_view, String.valueOf(mBookList.get(0).getId()));
////            // 第一个item的吸顶信息肯定是展示的，并且标记tag为FIRST_STICKY_VIEW
////            holder.itemView.setTag(FIRST_STICKY_VIEW);
//
//        } else {
//            // 之后的item都会和前一个item要展示的吸顶信息进行比较，不相同就展示，并且标记tag为HAS_STICKY_VIEW
//            if (!TextUtils.equals(String.valueOf(mBookList.get(position).getId()), String.valueOf(mBookList.get(position - 1).getId()))) {
//                holder.getConvertView().findViewById(R.id.tv_sticky_header_view).setVisibility(View.VISIBLE);
//                holder.setText(R.id.tv_sticky_header_view, String.valueOf(mBookList.get(position).getId()));
//                holder.itemView.setTag(HAS_STICKY_VIEW);
//
//            } else {
//                // 相同就不展示，并且标记tag为NONE_STICKY_VIEW
//                holder.setVisible(R.id.tv_sticky_header_view, true);
//                holder.itemView.setTag(NONE_STICKY_VIEW);
//
//            }
//        }
//// ContentDescription 用来记录并获取要吸顶展示的信息
//        holder.itemView.setContentDescription(String.valueOf(mBookList.get(position).getId()));
//
//    }
}
