package com.peryite.familybudget.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.sql.Timestamp;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "item",
        foreignKeys = @ForeignKey(entity = BudgetCategory.class, parentColumns = "_id", childColumns = "category_id", onDelete = CASCADE),
        indices = @Index(value = "category_id"))
public class CategoryItem {
    @PrimaryKey
    @ColumnInfo(name = "_id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "price")
    private Double price;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "item_date")
    private Timestamp date;

    @ColumnInfo(name = "category_id")
    private int categoryId;

    public CategoryItem() {
    }

    @Ignore
    public CategoryItem(int id, String name, Double price, String description, Timestamp date, int categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.date = date;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
