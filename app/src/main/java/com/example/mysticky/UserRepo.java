package com.example.mysticky;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UserRepo {

    private UserDao userDao;


    private LiveData<List<Notes>> allUsers;


    public UserRepo(Application application) {
        UserDb userDb = UserDb.getInstance(application);
        userDao=userDb.userDao();
        allUsers=userDao.getAllData();

    }
    public void insert(Notes notes) {
        new InsertNoteAsyncTask(userDao).execute(notes);
    }

    public void delete(Notes notes) {
        new DeleteNoteAsyncTask(userDao).execute(notes);
    }

    public void update(Notes notes) {
        new UpdateNoteAsyncTask(userDao).execute(notes);
    }
    public LiveData<List<Notes>> getAllUsers(){
        return allUsers;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Notes,Void,Void> {
        private UserDao userDao;


        private InsertNoteAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }
        @Override
        protected Void doInBackground(Notes... userData) {

            userDao.insert(userData[0]);
            return null;
        }


    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Notes,Void,Void>{

        private UserDao userDao;
        private UpdateNoteAsyncTask(UserDao userDao){
            this.userDao=userDao;
        }


        @Override
        protected Void doInBackground(Notes... userData) {
            userDao.update(userData[0]);
            return null;
        }
    }



    private static class DeleteNoteAsyncTask extends AsyncTask<Notes,Void,Void>{

        private UserDao userDao;
        private DeleteNoteAsyncTask(UserDao userDao){
            this.userDao=userDao;
        }


        @Override
        protected Void doInBackground(Notes... userData) {
            userDao.delete(userData[0]);
            return null;
        }
    }
}
