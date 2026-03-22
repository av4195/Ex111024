package com.example.ex111024;

public class Question {
    public String question;
    public String answer1;
    public String answer2;
    public String answer3;
    public String answer4;
    public int correctAnswer;

    public Question(String question, String answer1, String answer2, String answer3, String answer4, int correctAnswer) {
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.correctAnswer = correctAnswer;
    }

    public boolean checkAnswer(int userAnswer) {
        if (userAnswer == correctAnswer) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkAnswer(String userAnswer) {
        String correctText = "";

        // Determine which string corresponds to the correct integer index
        switch (correctAnswer) {
            case 1: correctText = answer1; break;
            case 2: correctText = answer2; break;
            case 3: correctText = answer3; break;
            case 4: correctText = answer4; break;
        }

        return userAnswer.equals(correctText);
    }


    public String getAnswer1() {
        return answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public String getQuestion() {
        return question;
    }
}

