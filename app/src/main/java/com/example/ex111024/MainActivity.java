package com.example.ex111024;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String FILENAME = "q.txt";
    private final String PFILENAME = "pq.txt";

    ArrayList<Question> questions = new ArrayList<>();

    TextView textView;
    Button button;
    Button button2;
    Button button3;
    Button button4;
    TextView textView2;
    Button button5;

    int score = 0;
    int highScore = 0;
    String highScoreUserName;
    int currentQuestionIndex = 0;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        highScore = sharedPreferences.getInt("highScore", 0);
        userName = sharedPreferences.getString("userName", "user");
        highScoreUserName = sharedPreferences.getString("highScoreUserName", "user");

        loadQuestions();
        loadQuestionsPrivate();

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        textView2 = findViewById(R.id.textView2);
        button5 = findViewById(R.id.button5);

        showQuestion(currentQuestionIndex);
    }

    public void showQuestion(int index) {
        if (index >= 0 && index < questions.size()) {
            Question question = questions.get(index);
            textView.setText(question.getQuestion());
            button.setText(question.getAnswer1());
            button2.setText(question.getAnswer2());
            button3.setText(question.getAnswer3());
            button4.setText(question.getAnswer4());
        }
    }

    public void buttonClicked(View view) {
        final Button clickedButton = (Button) view;
        String buttonText = clickedButton.getText().toString();
        
        if (currentQuestionIndex >= questions.size()) return;

        Question currentQuestion = questions.get(currentQuestionIndex);
        boolean isCorrect = currentQuestion.checkAnswer(buttonText);

        if (isCorrect) {
            score++;
            clickedButton.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
        } else {
            clickedButton.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        }

        setButtonsEnabled(false);

        clickedButton.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Reset to original blue color
                clickedButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2196F3")));

                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    showQuestion(currentQuestionIndex);
                } else {
                    textView.setText("Quiz Finished!");
                }
                showScore();
                
                if (currentQuestionIndex < questions.size()) {
                    setButtonsEnabled(true);
                }
            }
        }, 1500);
    }

    private void setButtonsEnabled(boolean enabled) {
        button.setEnabled(enabled);
        button2.setEnabled(enabled);
        button3.setEnabled(enabled);
        button4.setEnabled(enabled);
    }

    private void loadQuestions() {
        String fileName = FILENAME.substring(0, FILENAME.length() - 4);
        int resourceId = this.getResources().getIdentifier(fileName, "raw", this.getPackageName());

        if (resourceId == 0) {
            Log.e("LoadQuestions", "File not found: " + fileName);
            return;
        }

        try (InputStream inputStream = this.getResources().openRawResource(resourceId);
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader bR = new BufferedReader(inputStreamReader)) {

            String line;
            while ((line = bR.readLine()) != null) {
                String questionText = line;
                String answer1 = bR.readLine();
                String answer2 = bR.readLine();
                String answer3 = bR.readLine();
                String answer4 = bR.readLine();
                String correctStr = bR.readLine();

                if (correctStr != null) {
                    int correctAnswer = Integer.parseInt(correctStr);
                    questions.add(new Question(questionText, answer1, answer2, answer3, answer4, correctAnswer));
                }
            }
        } catch (Exception e) {
            Log.e("LoadQuestions", "Error reading questions file", e);
        }
    }

    public void loadQuestionsPrivate(){
        try{
            FileInputStream fIS = openFileInput(PFILENAME);
            InputStreamReader iSR = new InputStreamReader(fIS);
            BufferedReader bR = new BufferedReader(iSR);

            String line;
            while ((line = bR.readLine()) != null) {
                String questionText = line;
                String answer1 = bR.readLine();
                String answer2 = bR.readLine();
                String answer3 = bR.readLine();
                String answer4 = bR.readLine();
                String correctStr = bR.readLine();

                if (correctStr != null) {
                    int correctAnswer = Integer.parseInt(correctStr);
                    questions.add(new Question(questionText, answer1, answer2, answer3, answer4, correctAnswer));
                }
            }
            bR.close();
            iSR.close();
            fIS.close();
        }
        catch (Exception e){
            Log.e("LoadQuestions", "Private questions file not found or error reading.");
        }
    }

    private void showScore(){
        if(score > highScore){
            highScore = score;
            highScoreUserName = userName;
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("highScore", highScore);
            editor.putString("highScoreUserName", highScoreUserName);
            editor.apply();
        }

        String st = "Score: " + score + "\nHigh Score: " + highScore + "\nHigh Score User: " + highScoreUserName;
        textView2.setText(st);
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
