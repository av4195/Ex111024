package com.example.ex111024;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * @author Adi Waizman
 * @version 1.0
 * @since 22/03/2026
 * <p>
 * This class represents the credits screen of the app.
 * It displays information about the developers and the app's creation.
 */
public class credits extends AppCompatActivity {

    /**
     * Initializes the credits activity, setting the content view and the toolbar.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * Inflates the menu for this activity.
     *
     * @param menu The menu object to inflate into.
     * @return true if successful.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Handles item selection in the options menu and performs navigation.
     *
     * @param item The selected menu item.
     * @return true if the selection was handled.
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
