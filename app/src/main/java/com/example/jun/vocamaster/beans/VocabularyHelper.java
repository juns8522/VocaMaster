package com.example.jun.vocamaster.beans;

import android.content.res.Resources;

import com.example.jun.vocamaster.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.util.ArrayList;

/**
 * Created by jun on 8/11/14.
 */
public class VocabularyHelper {
    private ArrayList<Vocabulary> vocabularies;
    private Vocabulary vocabulary;
    private String text;

    public VocabularyHelper(Resources resources, int chapterNumber) {
        try{
            XmlPullParser parser = getParser(resources, chapterNumber);
            vocabularies = new ArrayList<Vocabulary>();

            int eventType = parser.getEventType();
            while(eventType!=XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if(tagname.equals("item")) {
                            vocabulary = new Vocabulary();
                            vocabulary.setChapterNumber(chapterNumber);
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(tagname.equals("item"))
                            vocabularies.add(vocabulary);
                        else if(tagname.equals("word"))
                            vocabulary.setWord(text);
                        else if(tagname.equals("meaning"))
                            vocabulary.setMeanings(text);
                        else if(tagname.equals("synonym"))
                            vocabulary.setSynonyms(text);
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }

        }catch(XmlPullParserException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<Vocabulary> getVocabularies() {

        return vocabularies;
    }

    public Vocabulary getVocabulary(int index) {
        return vocabularies.get(index);
    }

    public int getPosition(String word) {
        for(int position = 0; position < vocabularies.size(); position++) {
            if (vocabularies.get(position).getWord().equals(word))
                return position;
        }
        return -1;
    }

    private XmlPullParser getParser(Resources resources, int chapterNumber) {
        XmlPullParser parser = null;

        switch(chapterNumber) {
            case 1:
                parser = resources.getXml(R.xml.chapter1);
                break;
            case 2:
                parser = resources.getXml(R.xml.chapter2);
                break;
            case 3:
                parser = resources.getXml(R.xml.chapter3);
                break;
            case 4:
                parser = resources.getXml(R.xml.chapter4);
                break;
            case 5:
                parser = resources.getXml(R.xml.chapter5);
                break;
            case 6:
                parser = resources.getXml(R.xml.chapter6);
                break;
            case 7:
                parser = resources.getXml(R.xml.chapter7);
                break;
            case 8:
                parser = resources.getXml(R.xml.chapter8);
                break;
            case 9:
                parser = resources.getXml(R.xml.chapter9);
                break;
            case 10:
                parser = resources.getXml(R.xml.chapter10);
                break;
            case 11:
                parser = resources.getXml(R.xml.chapter11);
                break;
            case 12:
                parser = resources.getXml(R.xml.chapter12);
                break;
            case 13:
                parser = resources.getXml(R.xml.chapter13);
                break;
            case 14:
                parser = resources.getXml(R.xml.chapter14);
                break;
            case 15:
                parser = resources.getXml(R.xml.chapter15);
                break;
            case 16:
                parser = resources.getXml(R.xml.chapter16);
                break;
            case 17:
                parser = resources.getXml(R.xml.chapter17);
                break;
            case 18:
                parser = resources.getXml(R.xml.chapter18);
                break;
            case 19:
                parser = resources.getXml(R.xml.chapter19);
                break;
            case 20:
                parser = resources.getXml(R.xml.chapter20);
                break;
            case 21:
                parser = resources.getXml(R.xml.chapter21);
                break;
            case 22:
                parser = resources.getXml(R.xml.chapter22);
                break;
            case 23:
                parser = resources.getXml(R.xml.chapter23);
                break;
            case 24:
                parser = resources.getXml(R.xml.chapter24);
                break;
            case 25:
                parser = resources.getXml(R.xml.chapter25);
                break;
            case 26:
                parser = resources.getXml(R.xml.chapter26);
                break;
            case 27:
                parser = resources.getXml(R.xml.chapter27);
                break;
            case 28:
                parser = resources.getXml(R.xml.chapter28);
                break;
            case 29:
                parser = resources.getXml(R.xml.chapter29);
                break;
            case 30:
                parser = resources.getXml(R.xml.chapter30);
                break;
            case 31:
                parser = resources.getXml(R.xml.chapter31);
                break;
            case 32:
                parser = resources.getXml(R.xml.chapter32);
                break;
            case 33:
                parser = resources.getXml(R.xml.chapter33);
                break;
            case 34:
                parser = resources.getXml(R.xml.chapter34);
                break;
            case 35:
                parser = resources.getXml(R.xml.chapter35);
                break;
            case 36:
                parser = resources.getXml(R.xml.chapter36);
                break;
            case 37:
                parser = resources.getXml(R.xml.chapter37);
                break;
            case 38:
                parser = resources.getXml(R.xml.chapter38);
                break;
            case 39:
                parser = resources.getXml(R.xml.chapter39);
                break;
            case 40:
                parser = resources.getXml(R.xml.chapter40);
                break;
            case 41:
                parser = resources.getXml(R.xml.chapter41);
                break;
            case 42:
                parser = resources.getXml(R.xml.chapter42);
                break;
            case 43:
                parser = resources.getXml(R.xml.chapter43);
                break;
            case 44:
                parser = resources.getXml(R.xml.chapter44);
                break;
            case 45:
                parser = resources.getXml(R.xml.chapter45);
                break;
            case 46:
                parser = resources.getXml(R.xml.chapter46);
                break;
            case 47:
                parser = resources.getXml(R.xml.chapter47);
                break;
            case 48:
                parser = resources.getXml(R.xml.chapter48);
                break;
            case 49:
                parser = resources.getXml(R.xml.chapter49);
                break;
            case 50:
                parser = resources.getXml(R.xml.chapter50);
                break;
            case 51:
                parser = resources.getXml(R.xml.chapter51);
                break;
            case 52:
                parser = resources.getXml(R.xml.chapter52);
                break;
            case 53:
                parser = resources.getXml(R.xml.chapter53);
                break;
            case 54:
                parser = resources.getXml(R.xml.chapter54);
                break;
            case 55:
                parser = resources.getXml(R.xml.chapter55);
                break;
            case 56:
                parser = resources.getXml(R.xml.chapter56);
                break;
            case 57:
                parser = resources.getXml(R.xml.chapter57);
                break;
            case 58:
                parser = resources.getXml(R.xml.chapter58);
                break;
            case 59:
                parser = resources.getXml(R.xml.chapter59);
                break;
            case 60:
                parser = resources.getXml(R.xml.chapter60);
                break;
            default:
                break;
        }
        return parser;
    }
}
