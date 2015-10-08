package com.example.jun.vocamaster.beans;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by jun on 8/18/14.
 */
public class Test {
    private ArrayList<Vocabulary> vocabularies;
    private ArrayList<Question> questions;
    private int mNumOfQuestions;
    Random rand = new Random();

    public Test(ArrayList<Vocabulary> vocabularies, String type, int numOfQuestions) {
        mNumOfQuestions = numOfQuestions;
        this.vocabularies = vocabularies;
        questions = new ArrayList<Question>();

        if(type.equals("meaning")) {
            //test meaning
            testMeaning();
        }
        else {
            //test synonym
            testSynonym();
        }
    }

    private void testMeaning() {
        int size = vocabularies.size();
        int[] questionIndexes = getQuestionIndexes(size);
        int answerIndex;

        for(int i = 0; i < mNumOfQuestions; i++) {
            answerIndex = getAnswerIndex();
            Question question = getMeaningQuestion(questionIndexes[i], answerIndex, size);
            questions.add(question);
        }
    }

    private void testSynonym() {
        int size = vocabularies.size();
        int[] questionIndexes = getQuestionIndexes(size);
        int answerIndex;

        for(int i = 0; i < mNumOfQuestions; i++) {
            answerIndex = getAnswerIndex();
            Question question = getSynonymQuestion(questionIndexes[i], answerIndex, size);
            questions.add(question);
        }
    }

    private int getAnswerIndex() {
        return rand.nextInt(4);
    }

    private int[] getQuestionIndexes(int size) {
        int[] questionIndexes = new int[mNumOfQuestions];

        ArrayList<Integer> list = new ArrayList<Integer>(size);
        for(int i = 0; i < size; i++) {
            list.add(i);
        }

        for(int i = 0; i < mNumOfQuestions; i++) {
            int listIndex = rand.nextInt(list.size());
            questionIndexes[i] = list.remove(listIndex);
        }

        return questionIndexes;
    }

    private Question getMeaningQuestion(int questionIndex, int answerIndex, int size) {
        int q1 = questionIndex;
        int q2 = questionIndex;
        int q3 = questionIndex;
        int arraySize;
        Question question = new Question();
        Vocabulary vocabulary = vocabularies.get(questionIndex);

        while(q1 == questionIndex)
            q1 = rand.nextInt(size);

        while(q2 == questionIndex || q2 == q1)
            q2 = rand.nextInt(size);

        while(q3 == questionIndex || q3 == q2 || q3 == q1)
            q3 = rand.nextInt(size);

        switch(answerIndex) {
            case 0:
                question.setWord(vocabulary.getWord());

                arraySize = vocabulary.getMeanings().size();
                question.setQuestion1(vocabulary.getMeanings().get(rand.nextInt(arraySize)));

                arraySize = vocabularies.get(q1).getMeanings().size();
                question.setQuestion2(vocabularies.get(q1).getMeanings().get(rand.nextInt(arraySize)));

                arraySize = vocabularies.get(q2).getMeanings().size();
                question.setQuestion3(vocabularies.get(q2).getMeanings().get(rand.nextInt(arraySize)));

                arraySize = vocabularies.get(q3).getMeanings().size();
                question.setQuestion4(vocabularies.get(q3).getMeanings().get(rand.nextInt(arraySize)));

                question.setAnswerIndex(answerIndex);
                break;
            case 1:
                question.setWord(vocabulary.getWord());

                arraySize = vocabularies.get(q1).getMeanings().size();
                question.setQuestion1(vocabularies.get(q1).getMeanings().get(rand.nextInt(arraySize)));

                arraySize = vocabulary.getMeanings().size();
                question.setQuestion2(vocabulary.getMeanings().get(rand.nextInt(arraySize)));

                arraySize = vocabularies.get(q2).getMeanings().size();
                question.setQuestion3(vocabularies.get(q2).getMeanings().get(rand.nextInt(arraySize)));

                arraySize = vocabularies.get(q3).getMeanings().size();
                question.setQuestion4(vocabularies.get(q3).getMeanings().get(rand.nextInt(arraySize)));

                question.setAnswerIndex(answerIndex);
                break;
            case 2:
                question.setWord(vocabulary.getWord());

                arraySize = vocabularies.get(q1).getMeanings().size();
                question.setQuestion1(vocabularies.get(q1).getMeanings().get(rand.nextInt(arraySize)));

                arraySize = vocabularies.get(q2).getMeanings().size();
                question.setQuestion2(vocabularies.get(q2).getMeanings().get(rand.nextInt(arraySize)));

                arraySize = vocabulary.getMeanings().size();
                question.setQuestion3(vocabulary.getMeanings().get(rand.nextInt(arraySize)));

                arraySize = vocabularies.get(q3).getMeanings().size();
                question.setQuestion4(vocabularies.get(q3).getMeanings().get(rand.nextInt(arraySize)));

                question.setAnswerIndex(answerIndex);
                break;
            case 3:
                question.setWord(vocabulary.getWord());

                arraySize = vocabularies.get(q1).getMeanings().size();
                question.setQuestion1(vocabularies.get(q1).getMeanings().get(rand.nextInt(arraySize)));

                arraySize = vocabularies.get(q2).getMeanings().size();
                question.setQuestion2(vocabularies.get(q2).getMeanings().get(rand.nextInt(arraySize)));

                arraySize = vocabularies.get(q3).getMeanings().size();
                question.setQuestion3(vocabularies.get(q3).getMeanings().get(rand.nextInt(arraySize)));

                arraySize = vocabulary.getMeanings().size();
                question.setQuestion4(vocabulary.getMeanings().get(rand.nextInt(arraySize)));

                question.setAnswerIndex(answerIndex);
                break;
        }
        return question;
    }

    private Question getSynonymQuestion(int questionIndex, int answerIndex, int size) {
        int q1 = questionIndex;
        int q2 = questionIndex;
        int q3 = questionIndex;
        int arraySize;
        Question question = new Question();
        Vocabulary vocabulary = vocabularies.get(questionIndex);

        while(q1 == questionIndex)
            q1 = rand.nextInt(size);

        while(q2 == questionIndex || q2 == q1)
            q2 = rand.nextInt(size);

        while(q3 == questionIndex || q3 == q2 || q3 == q1)
            q3 = rand.nextInt(size);

        switch(answerIndex) {
            case 0:
                question.setWord(vocabulary.getWord());

                arraySize = vocabulary.getSynonyms().size();
                question.setQuestion1(vocabulary.getSynonyms().get(rand.nextInt(arraySize)));

                arraySize = vocabularies.get(q1).getSynonyms().size();
                question.setQuestion2(vocabularies.get(q1).getSynonyms().get(rand.nextInt(arraySize)));

                arraySize = vocabularies.get(q2).getSynonyms().size();
                question.setQuestion3(vocabularies.get(q2).getSynonyms().get(rand.nextInt(arraySize)));

                arraySize = vocabularies.get(q3).getSynonyms().size();
                question.setQuestion4(vocabularies.get(q3).getSynonyms().get(rand.nextInt(arraySize)));

                question.setAnswerIndex(answerIndex);
                break;
            case 1:
                question.setWord(vocabulary.getWord());

                arraySize = vocabularies.get(q1).getSynonyms().size();
                question.setQuestion1(vocabularies.get(q1).getSynonyms().get(rand.nextInt(arraySize)));

                arraySize = vocabulary.getSynonyms().size();
                question.setQuestion2(vocabulary.getSynonyms().get(rand.nextInt(arraySize)));

                arraySize = vocabularies.get(q2).getSynonyms().size();
                question.setQuestion3(vocabularies.get(q2).getSynonyms().get(rand.nextInt(arraySize)));

                arraySize = vocabularies.get(q3).getSynonyms().size();
                question.setQuestion4(vocabularies.get(q3).getSynonyms().get(rand.nextInt(arraySize)));

                question.setAnswerIndex(answerIndex);
                break;
            case 2:
                question.setWord(vocabulary.getWord());

                arraySize = vocabularies.get(q1).getSynonyms().size();
                question.setQuestion1(vocabularies.get(q1).getSynonyms().get(rand.nextInt(arraySize)));

                arraySize = vocabularies.get(q2).getSynonyms().size();
                question.setQuestion2(vocabularies.get(q2).getSynonyms().get(rand.nextInt(arraySize)));

                arraySize = vocabulary.getSynonyms().size();
                question.setQuestion3(vocabulary.getSynonyms().get(rand.nextInt(arraySize)));

                arraySize = vocabularies.get(q3).getSynonyms().size();
                question.setQuestion4(vocabularies.get(q3).getSynonyms().get(rand.nextInt(arraySize)));

                question.setAnswerIndex(answerIndex);
                break;
            case 3:
                question.setWord(vocabulary.getWord());

                arraySize = vocabularies.get(q1).getSynonyms().size();
                question.setQuestion1(vocabularies.get(q1).getSynonyms().get(rand.nextInt(arraySize)));

                arraySize = vocabularies.get(q2).getSynonyms().size();
                question.setQuestion2(vocabularies.get(q2).getSynonyms().get(rand.nextInt(arraySize)));

                arraySize = vocabularies.get(q3).getSynonyms().size();
                question.setQuestion3(vocabularies.get(q3).getSynonyms().get(rand.nextInt(arraySize)));

                arraySize = vocabulary.getSynonyms().size();
                question.setQuestion4(vocabulary.getSynonyms().get(rand.nextInt(arraySize)));

                question.setAnswerIndex(answerIndex);
                break;
        }
        return question;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }
}
