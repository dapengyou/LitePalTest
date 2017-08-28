package com.test.litepaltest;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by lady_zhou on 2017/8/23.
 */

public class BookBean extends DataSupport implements Serializable {
    private int id;
    private String bookName; //书名
    private String authorName;//作者名

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
