package com.example.jun.vocamaster.beans;

import java.util.ArrayList;

/**
 * Created by jun on 8/24/14.
 */
public class Result {
    private Vocabulary vocabulary;
    private int chapter;
    private int index;
    private int numOfCorrect;
    private int numOfWrong;

    public Result() {
        vocabulary = new Vocabulary();
        numOfCorrect = 0;
        numOfWrong = 0;
    }

    public void setVocabulary(Vocabulary vocabulary) {
        this.vocabulary = vocabulary;
    }

    public void setChapter(int chapter) { this.chapter = chapter; }

    public void setIndex(int index) { this.index = index; }

    public void setNumOfCorrect(int numOfCorrect) {
        this.numOfCorrect = numOfCorrect;
    }

    public void setNumOfWrong(int numOfWrong) {
        this.numOfWrong = numOfWrong;
    }

    public Vocabulary getVocabulary() {
        return vocabulary;
    }

    public int getChapter() { return  chapter; }

    public int getIndex() { return  index; }

    public int getNumOfCorrect() {
        return numOfCorrect;
    }

    public int getNumOfWrong() {
        return numOfWrong;
    }
}
