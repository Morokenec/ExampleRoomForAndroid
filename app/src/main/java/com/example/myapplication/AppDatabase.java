package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;

@Database(
        entities = {Product.class},
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
}
