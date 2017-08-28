package com.test.litepaltest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lady_zhou on 2017/8/25.
 */

public class DataListActivity extends AppCompatActivity {
    public static String DATALIST = "datalist";
    private RecyclerView mRecyclerView;
    private DataAdpter mDataAdpter;

    private List<BookBean> bookBeanList = new ArrayList<>();

    private TextView mTvHeader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datalist);

        mRecyclerView = (RecyclerView) findViewById(R.id.rl_list);

        mTvHeader = (TextView) findViewById(R.id.tv_sticky_header_view);

        bookBeanList = (List<BookBean>) this.getIntent().getSerializableExtra(DATALIST);
//        for (BookBean book : bookBeanList) {
//            Log.d("888", "id" + book.getId());
//            Log.d("888", "bookname" + book.getBookName());
//            Log.d("888", "authorname" + book.getAuthorName());
//        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDataAdpter = new DataAdpter(bookBeanList);
        mRecyclerView.setAdapter(mDataAdpter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // 找到RecyclerView的item中，和RecyclerView的getTop 向下相距5个像素的那个item
                // (尝试2、3个像素位置都找不到，所以干脆用了5个像素)，
                // 我们根据这个item，来更新吸顶布局的内容，
                // 因为我们的StickyLayout展示的信息肯定是最上面的那个item的信息.
                View view = recyclerView.findChildViewUnder(mTvHeader.getMeasuredWidth() / 2, 5);
                if (view != null && view.getContentDescription() != null) {
                    mTvHeader.setText(String.valueOf(view.getContentDescription()));
                }

                // 找到固定在屏幕上方那个FakeStickyLayout下面一个像素位置的RecyclerView的item，
                // 我们根据这个item来更新假的StickyLayout要translate多少距离.
                // 并且只处理HAS_STICKY_VIEW和NONE_STICKY_VIEW这两种tag，
                // 因为第一个item的StickyLayout虽然展示，但是一定不会引起FakeStickyLayout的滚动.
                View transInfoView = recyclerView.findChildViewUnder(
                        mTvHeader.getMeasuredWidth() / 2, mTvHeader.getMeasuredHeight() + 1);

                if (transInfoView != null && transInfoView.getTag() != null) {
                    int transViewStatus = (int) transInfoView.getTag();
                    int dealtY = transInfoView.getTop() - mTvHeader.getMeasuredHeight();

                    // 如果当前item需要展示StickyLayout，
                    // 那么根据这个item的getTop和FakeStickyLayout的高度相差的距离来滚动FakeStickyLayout.
                    // 这里有一处需要注意，如果这个item的getTop已经小于0，也就是滚动出了屏幕，
                    // 那么我们就要把假的StickyLayout恢复原位，来覆盖住这个item对应的吸顶信息.
                    if (transViewStatus == mDataAdpter.HAS_STICKY_VIEW) {
                        if (transInfoView.getTop() > 0) {
                            mTvHeader.setTranslationY(dealtY);
                        } else {
                            mTvHeader.setTranslationY(0);
                        }
                    } else if (transViewStatus == mDataAdpter.NONE_STICKY_VIEW) {
                        // 如果当前item不需要展示StickyLayout，那么就不会引起FakeStickyLayout的滚动.
                        mTvHeader.setTranslationY(0);
                    }
                }
            }
        });

    }
}
