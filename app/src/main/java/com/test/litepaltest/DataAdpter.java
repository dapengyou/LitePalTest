package com.test.litepaltest;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by lady_zhou on 2017/8/25.
 */

public class DataAdpter extends BaseQuickAdapter<BookBean, BaseViewHolder> {
    public DataAdpter(@Nullable List<BookBean> data) {
        super(R.layout.item_bookadpter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BookBean item) {
        helper.setText(R.id.tv_bookname, item.getBookName());
        helper.setText(R.id.tv_authorname, item.getAuthorName());
    }
}
