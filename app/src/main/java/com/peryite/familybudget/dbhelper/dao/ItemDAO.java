package com.peryite.familybudget.dbhelper.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.peryite.familybudget.entities.CategoryItem;

import java.util.List;

@Dao
public interface ItemDAO {
    @Query("SELECT * FROM ITEM")
    List<CategoryItem> getAllEntity();

    @Query("SELECT * FROM ITEM WHERE category_id = :categoryId")
    List<CategoryItem> getAllEntityByCategoryId(int categoryId);

    @Insert
    long insert(CategoryItem categoryItem);

    @Update
    int update(CategoryItem categoryItem);
}
