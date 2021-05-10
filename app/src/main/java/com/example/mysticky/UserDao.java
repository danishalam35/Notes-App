package com.example.mysticky;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {


    @Insert
    public void insert(Notes notes);


    @Update
    public void update(Notes notes);

    @Delete
    public void delete(Notes notes);


    @Query("select * from users_table")
    LiveData<List<Notes>> getAllData();

}
