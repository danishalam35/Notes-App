package com.example.mysticky;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserViewModel  extends AndroidViewModel {

    private UserRepo userRepo;

    private LiveData<List<Notes>> allUsers;

    public UserViewModel(@NonNull Application application) {
        super(application);

        userRepo=new UserRepo(application);
        allUsers=userRepo.getAllUsers();

    }

    public void insert(Notes notes){
        userRepo.insert(notes);
    }
    public void update(Notes notes){
        userRepo.update(notes);
    }
    public void delete(Notes notes){
        userRepo.delete(notes);
    }

    public LiveData<List<Notes>> getAllUsers() {
        return allUsers;
    }
}
