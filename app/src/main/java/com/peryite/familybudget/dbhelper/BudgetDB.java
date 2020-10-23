package com.peryite.familybudget.dbhelper;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.peryite.familybudget.dbhelper.dao.CategoryDAO;
import com.peryite.familybudget.dbhelper.dao.ItemDAO;
import com.peryite.familybudget.entities.BudgetCategory;
import com.peryite.familybudget.entities.CategoryItem;

@Database(entities = {BudgetCategory.class, CategoryItem.class},
        exportSchema = false, version = DBConfig.DATABASE_VERSION)
@TypeConverters({Converter.class})
public abstract class BudgetDB extends RoomDatabase {
    private static BudgetDB instance;

    public static synchronized BudgetDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), BudgetDB.class, DBConfig.DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract CategoryDAO categoryDAO();

    public abstract ItemDAO itemDAO();
}
