package com.example.jun.vocamaster.beans;

import java.util.ArrayList;

/**
 * Created by jun on 8/11/14.
 */
public class Vocabulary {
    private int chapterNumber;
    private String word;
    private ArrayList<String> meanings = new ArrayList<String>();
    private ArrayList<String> synonyms = new ArrayList<String>();

    public void setChapterNumber(int chapterNumber) { this.chapterNumber = chapterNumber; }

    public void setWord(String word) {
        this.word = word;
    }

    public void setMeanings(String meaning) {
        meanings.add(meaning);
    }

    public void setSynonyms(String synonym) {
        synonyms.add(synonym);
    }

    public int getChapterNumber() { return chapterNumber; }

    public String getWord() {
        return word;
    }

    public ArrayList<String> getMeanings() {
        return meanings;
    }

    public ArrayList<String> getSynonyms() { return synonyms; }
}
