package com.example.ex111024;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;


public class addQ extends AppCompatActivity {

    EditText editTextText;
    EditText editTextText3;
    EditText editTextText4;
    EditText editTextText6;
    EditText editTextText5;
    Button button6;
    private final String FILENAME = "pq.txt";


    /**
     * Initializes the activity, sets the content view, and sets up the toolbar and UI elements.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_q);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextText = findViewById(R.id.editTextText);
        editTextText3 = findViewById(R.id.editTextText3);
        editTextText4 = findViewById(R.id.editTextText4);
        editTextText6 = findViewById(R.id.editTextText6);
        editTextText5 = findViewById(R.id.editTextText5);
        button6 = findViewById(R.id.button6);
    }

    /**
     * Collects the question data from the UI and appends it to the internal file.
     * Navigates back to MainActivity upon completion.
     *
     * @param view The view that was clicked.
     */
    public void addQuestion(View view){
        StringBuilder sb = new StringBuilder();
        sb.append(editTextText.getText().toString());
        sb.append("\n");
        sb.append(editTextText3.getText().toString());
        sb.append("\n");
        sb.append(editTextText4.getText().toString());
        sb.append("\n");
        sb.append(editTextText6.getText().toString());
        sb.append("\n");
        sb.append(editTextText5.getText().toString());
        sb.append("\n");
        sb.append(1);
        sb.append("\n");


        try {
            FileOutputStream fOS = openFileOutput(FILENAME, MODE_PRIVATE);
            OutputStreamWriter oSW = new OutputStreamWriter(fOS);
            BufferedWriter bW = new BufferedWriter(oSW);
            bW.write(sb.toString());
            bW.close();
            oSW.close();
            fOS.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // go to a fresh main activity - destroy the other ones
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


    /**
     * Inflates the menu resource into the existing menu.
     *
     * @param menu The options menu in which you place your items.
     * @return true for the menu to be displayed.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Handles selection of menu items for navigation between activities.
     *
     * @param item The menu item that was selected.
     * @return true to consume the selection here.
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
