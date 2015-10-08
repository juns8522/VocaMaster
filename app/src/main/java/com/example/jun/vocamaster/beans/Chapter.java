package com.example.jun.vocamaster.beans;

import java.util.ArrayList;

/**
 * Created by jun on 8/11/14.
 */
public class Chapter {
    private ArrayList<Vocabulary> vocabularies = new ArrayList<Vocabulary>();

    public ArrayList<Vocabulary> getVocabularies() {
        return vocabularies;
    }

    public void setVocabularies(ArrayList<Vocabulary> vocabularies) {
        this.vocabularies = vocabularies;
    }
}
