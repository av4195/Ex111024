package com.example.ex111024;

import android.os.Bundle;
import android.util.Log;
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


        showQuestion(0);

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
}