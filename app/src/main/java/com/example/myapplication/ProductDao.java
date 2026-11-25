package com.example.myapplication;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert
    void insert(Product product);

    @Query("SELECT * FROM products WHERE id = :id")
    Product getOne(int id);
    @Update
    void update(Product product);
    @Delete
    void delete(Product product);
    @Query("SELECT * FROM products")
    List<Product> getAll();
    @Query("SELECT * FROM products WHERE price > :minPrice")
    List<Product> getExpensiveProducts(double minPrice);
}
