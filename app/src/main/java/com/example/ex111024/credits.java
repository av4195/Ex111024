package com.example.ex111024;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class credits extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        String title = item.getTitle().toString();
        Intent intent = null;

        if (title.equals("game")){
            intent = new Intent(this, MainActivity.class);
        }
        else if (title.equals("Add question")){
            intent = new Intent(this, addQ.class);
        }
        else if (title.equals("Settings")){
            intent = new Intent(this, settings.class);
        }
        else if (title.equals("Credits")){
            intent = new Intent(this, credits.class);
        }

        if (intent != null) {
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
