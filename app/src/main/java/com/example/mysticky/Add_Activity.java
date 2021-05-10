package com.example.mysticky;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

public class Add_Activity extends AppCompatActivity {

    EditText titles, descriptions;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_);

        titles = findViewById(R.id.titles);
        descriptions = findViewById(R.id.desc);
        toolbar=findViewById(R.id.toolbars);

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);



        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            setTitle("Edit Note");
            titles.setText(intent.getStringExtra("title"));
            descriptions.setText(intent.getStringExtra("desc"));
        } else {
            setTitle("Add Note");
        }
    }

    private void saveNote() {
        String title = titles.getText().toString();
        String descs = descriptions.getText().toString();

        Intent data = new Intent();
        data.putExtra("title", title);
        data.putExtra("desc", descs);
        int id = getIntent().getIntExtra("id", -1);

        if (id != -1) {
            data.putExtra("id", id);
        }

        setResult(RESULT_OK, data);
        finish();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}