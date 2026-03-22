package com.example.ex111024;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * @author Adi Waizman
 * @version 1.0
 * @since 22/03/2026
 * <p>
 * This class handles the settings for the quiz game, including
 * resetting the high score and setting the user's name.
 */
public class settings extends AppCompatActivity {

    /** EditText for entering the user's name. */
    EditText setUserName;

    /**
     * Initializes the settings activity and its UI components.
     *
     * @param savedInstanceState A Bundle containing the data most recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUserName = findViewById(R.id.setUserName);
    }

    /**
     * Resets the high score to zero in SharedPreferences.
     *
     * @param view The view that was clicked (the "Reset Highscore" button).
     */
    public void resetHighscore(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("highScore", 0);
        editor.apply();
        Toast.makeText(this, "Highscore reset", Toast.LENGTH_SHORT).show();
    }

    /**
     * Saves the username provided in the EditText field to SharedPreferences.
     *
     * @param view The view that was clicked (the "Save User Name" button).
     */
    public void setUsrName(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userName", setUserName.getText().toString());
        editor.apply();
        Toast.makeText(this, "Username set", Toast.LENGTH_SHORT).show();
    }

    /**
     * Inflates the menu options for this activity.
     *
     * @param menu The menu object to inflate.
     * @return true if the menu was created.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Handles the selection of a menu item.
     *
     * @param item The menu item selected.
     * @return true if handled.
     */
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
