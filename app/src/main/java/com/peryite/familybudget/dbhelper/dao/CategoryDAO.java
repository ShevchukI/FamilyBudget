package com.peryite.familybudget.dbhelper.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.peryite.familybudget.entities.BudgetCategory;

import java.util.List;

@Dao
public interface CategoryDAO {
    @Query("SELECT _id, name, description FROM CATEGORY")
    List<BudgetCategory> getAllEntity();

    @Insert
    long insert(BudgetCategory budgetCategory);

    @Insert
    long[] insert(List<BudgetCategory> budgetCategories);

    @Update
    int update(BudgetCategory budgetCategory);

    @Delete
    int delete(BudgetCategory budgetCategory);

    @Query("DELETE FROM CATEGORY")
    void deleteAll();
}
