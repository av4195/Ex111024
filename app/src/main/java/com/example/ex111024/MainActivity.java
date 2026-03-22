package com.example.ex111024;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String FILENAME = "q.txt";
    ArrayList<Question> questions = new ArrayList<>();

    TextView textView;
    Button button;
    Button button2;
    Button button3;
    Button button4;
    TextView textView2;
    Button button5;

    int score = 0;
    int currentQuestionIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Now you can call it directly without try-catch
        loadQuestions();

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
            clickedButton.setBackgroundColor(Color.GREEN);
        } else {
            clickedButton.setBackgroundColor(Color.RED);
        }

        // Disable buttons to prevent multiple clicks during the delay
        setButtonsEnabled(false);

        // Add a delay of 1.5 seconds before moving to the next question
        clickedButton.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Reset color back to original
                clickedButton.setBackgroundColor(Color.parseColor("#2196F3FF"));

                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    showQuestion(currentQuestionIndex);
                } else {
                    textView.setText("Quiz Finished!");
                }
                showScore();
                
                // Re-enable buttons for the next question (if any)
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

        // Try-with-resources handles closing the streams and catching the IOException
        try (InputStream inputStream = this.getResources().openRawResource(resourceId);
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader bR = new BufferedReader(inputStreamReader)) {

            String line;
            // Read the first line (the question) and check if it's null
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
            // Handle exceptions here instead of throwing them to onCreate
            Log.e("LoadQuestions", "Error reading questions file", e);
        }
    }


    private void showScore(){
        textView2.setText("Score: " + score);
    }
}
