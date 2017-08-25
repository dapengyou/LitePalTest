package com.test.litepaltest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by lady_zhou on 2017/8/25.
 */

public class DataListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private DataAdpter mDataAdpter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datalist);

        mRecyclerView = (RecyclerView) findViewById(R.id.rl_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDataAdpter = new DataAdpter(new ArrayList<BookBean>());
        mRecyclerView.setAdapter(mDataAdpter);

    }
}
