package com.example.mysticky;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity   {

    public static final int ADD_NOTE_REQUEST = 1;
    private UserViewModel noteViewModel;
    DrawerLayout drawerLayout;
    SwipeRefreshLayout swipeRefreshLayout;
    public static final int EDIT_NOTE_REQUEST = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        drawerLayout=findViewById(R.id.drawer_layout);


        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Add_Activity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);

            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final UserAdapter adapter = new UserAdapter();
        recyclerView.setAdapter(adapter);
        noteViewModel= ViewModelProviders.of(this).get(UserViewModel.class);
        noteViewModel.getAllUsers().observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(@Nullable List<Notes> notes) {
                adapter.setNotes(notes);
            }
        });



        adapter.onLongClickListn(new UserAdapter.onLongClickListener() {
            @Override
            public void onDeleteItem(final Notes notes) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                noteViewModel.delete(notes);

                                Toast.makeText(MainActivity.this, "Todo deleted",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setCancelable(true).show();
            }
        });



        adapter.setOnItemClickListener(new UserAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Notes userData) {
                Intent intent=new Intent(MainActivity.this,Add_Activity.class);
                intent.putExtra("id",userData.getId());
                intent.putExtra("title",userData.getTitle());
                intent.putExtra("desc",userData.getDesc());
                startActivityForResult(intent,EDIT_NOTE_REQUEST);
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra("title");
            String description = data.getStringExtra("desc");
           noteViewModel.insert(new Notes(title,description));

            Toast.makeText(this, " saved", Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK){

            String title = data.getStringExtra("title");
            String description = data.getStringExtra("desc");
            int note_id = data.getIntExtra("id",0);

            Notes userData = new Notes(title,description);
            userData.setId(note_id);
            noteViewModel.update(userData);

        }

        else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }






    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }

    private static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);


    }
    public void clickLogo(View view){
        closeDrawer(drawerLayout);
    }

    private static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }

    }

    public void create(View view){
        startActivity(new Intent(MainActivity.this,Add_Activity.class));
    }
    public void notes(View view){
        startActivity(new Intent(MainActivity.this,MainActivity.class));
    }
}