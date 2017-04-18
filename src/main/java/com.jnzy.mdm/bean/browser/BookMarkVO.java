package com.jnzy.mdm.bean.browser;

/**
 * Created by hardy on 2017/2/24.
 */
public class BookMarkVO {
    private String bookmarkName;
    private String bookmarkUrl;
    private Integer bookmarkIdent;
    private Integer level;
    private Integer userId;

    public String getBookmarkName() {
        return bookmarkName;
    }

    public void setBookmarkName(String bookmarkName) {
        this.bookmarkName = bookmarkName;
    }

    public String getBookmarkUrl() {
        return bookmarkUrl;
    }

    public void setBookmarkUrl(String bookmarkUrl) {
        this.bookmarkUrl = bookmarkUrl;
    }

    public Integer getBookmarkIdent() {
        return bookmarkIdent;
    }

    public void setBookmarkIdent(Integer bookmarkIdent) {
        this.bookmarkIdent = bookmarkIdent;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
