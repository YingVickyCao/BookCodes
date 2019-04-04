/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.hack41.data.table;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * A simple Article object annotated for use with OrmLite.
 */
@DatabaseTable(tableName = Article.TABLE_NAME)
public class Article {

    static final String TABLE_NAME = "articles";
    static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_TEXT = "text";
    private static final String COLUMN_PUBLISHED_DATE = "publishedDate";

    @DatabaseField(generatedId = true, columnName = COLUMN_ID)
    private int id;

    @DatabaseField(canBeNull = false, columnName = COLUMN_TITLE)
    private String title;

    @DatabaseField(canBeNull = false, columnName = COLUMN_TEXT)
    private String text;

    @DatabaseField(canBeNull = false, columnName = COLUMN_PUBLISHED_DATE)
    private Date publishedDate;

    /*
     * No column will be created on the articles table, but the ORM will help us
     * populate this collection from a different table.
     */
    @ForeignCollectionField(eager = true)
    ForeignCollection<Comment> comments;

    public Article() {
        // OrmLite requires a parameterless constructor with at least default
        // access.
    }

    public Article(Date publishedDate, String text, String title) {
        this.publishedDate = publishedDate;
        this.text = text;
        this.title = title;
    }

    public Article(Date publishedDate, int id, String text, String title) {
        this.publishedDate = publishedDate;
        this.id = id;
        this.text = text;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ForeignCollection<Comment> getComments() {
        return comments;
    }
}
