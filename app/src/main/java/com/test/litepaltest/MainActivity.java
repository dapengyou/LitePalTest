package com.test.litepaltest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtCreateDatabase;
    private Button mBtAddTable;
    private Button mBtupdateTable, mBtupdateTable2;
    private Button mBtDeleteTable, mBtDeleteTable2;
    private Button mBtQuery;

    BookBean bookBeanUpdate = new BookBean();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtCreateDatabase = (Button) findViewById(R.id.bt_createDatabase);
        mBtAddTable = (Button) findViewById(R.id.bt_addTable);
        mBtupdateTable = (Button) findViewById(R.id.bt_updateTable);
        mBtupdateTable2 = (Button) findViewById(R.id.bt_updateTable2);
        mBtDeleteTable = (Button) findViewById(R.id.bt_deleteTable);
        mBtDeleteTable2 = (Button) findViewById(R.id.bt_deleteTable2);
        mBtQuery = (Button) findViewById(R.id.bt_query);

        mBtCreateDatabase.setOnClickListener(this);
        mBtAddTable.setOnClickListener(this);
        mBtupdateTable.setOnClickListener(this);
        mBtupdateTable2.setOnClickListener(this);
        mBtDeleteTable.setOnClickListener(this);
        mBtDeleteTable2.setOnClickListener(this);
        mBtQuery.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_createDatabase:
                //创建数据库
                Connector.getDatabase();
                break;
            case R.id.bt_addTable:
                BookBean bookBean = new BookBean();
                bookBean.setBookName("百年孤独");
                bookBean.setAuthorName("李三");
                bookBean.save();
                BookBean bookBean2 = new BookBean();
                bookBean2.setBookName("城市集聚区");
                bookBean2.setAuthorName("冯四爷");
                bookBean2.save();
                BookBean bookBean3 = new BookBean();
                bookBean3.setBookName("笑看江湖");
                bookBean3.setAuthorName("烈焰");
                bookBean3.save();
                Toast.makeText(this, "成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_updateTable:
                //修改第二条数据
                bookBeanUpdate.setAuthorName("落日千丈");
                bookBeanUpdate.update(2);
                Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_updateTable2:
                //更改书名是笑看江湖的
                bookBeanUpdate.setBookName("追梦人");
                bookBeanUpdate.updateAll("bookName=?", "笑看江湖");
                Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_deleteTable:
                //删除了第一条
                DataSupport.delete(BookBean.class, 1);
                Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_deleteTable2:
                //有条件的删除
                DataSupport.deleteAll(BookBean.class, "bookName=?", "笑看江湖");
                Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_query:
                List<BookBean> allBooks = DataSupport.findAll(BookBean.class);
                BookBean bookInfo = new BookBean();
                for (BookBean book : allBooks) {
                    bookInfo.setId(book.getId());
                    bookInfo.setAuthorName(book.getAuthorName());
                    bookInfo.setBookName(book.getBookName());
                }
                startActivity(new Intent(this,DataListActivity.class));
                break;

        }
    }
}
