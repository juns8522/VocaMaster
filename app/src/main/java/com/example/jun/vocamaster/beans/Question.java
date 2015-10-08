package com.example.jun.vocamaster.beans;

/**
 * Created by jun on 8/18/14.
 */
public class Question {
    private String word;
    private String question1;
    private String question2;
    private String question3;
    private String question4;
    private int answerIndex;

    public Question()
    {}

    public void setWord(String word) {
        this.word = word;
    }

    public void setQuestion1(String question1) {
        this.question1 = question1;
    }

    public void setQuestion2(String question2) {
        this.question2 = question2;
    }

    public void setQuestion3(String question3) {
        this.question3 = question3;
    }

    public void setQuestion4(String question4) {
        this.question4 = question4;
    }

    public void setAnswerIndex(int answerIndex) {
        this.answerIndex = answerIndex;
    }

    public String getWord() { return word; }

    public String getQuestion1() {
        return question1;
    }

    public String getQuestion2() {
        return question2;
    }

    public String getQuestion3() {
        return question3;
    }

    public String getQuestion4() {
        return question4;
    }

    public int getAnswerIndex() { return answerIndex; }
}
