package com.example.jun.vocamaster.beans;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by jun on 8/24/14.
 */
public class VMStatHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "myDatabase.db";
    private static final String DATABASE_TABLE = "Results";
    private static final String WORD = "word";
    private static final String CHAPTER = "chapter";
    private static final String POSITION = "position";
    private static final String CORRECT = "correct";
    private static final String WRONG = "wrong";
    private static final ArrayList<Result> mResults = new ArrayList<Result>();

    private final static int DATABASE_VERSION = 1;
    private Context context;

    public VMStatHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

        getWritableDatabase().execSQL("CREATE TABLE if not exists " + DATABASE_TABLE +
                "( _id INTEGER primary key autoincrement," +
                "word TEXT," + "chapter INTEGER," + "position INTEGER," + "correct INTEGER," + "wrong INTEGER);");
    }

    @Override
    /**
     * Create the database
     */
    public void onCreate(SQLiteDatabase database)
    {
        database.execSQL("CREATE TABLE " + DATABASE_TABLE +
                "( _id INTEGER primary key autoincrement," +
                "word TEXT," + "chapter INTEGER," + "position INTEGER," + "correct INTEGER," + "wrong INTEGER);");
    }

    public void addResult(String word, int chapter, int position, int correct, int wrong) {
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = null;

        try{
            cursor = database.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE word = '" + word + "';", null);

            if(cursor.moveToFirst()) {
                if(correct == 1) {
                    int corrects = cursor.getInt(4);
                    ContentValues newValues = new ContentValues();
                    newValues.put("correct", corrects + 1);
                    database.update(DATABASE_TABLE, newValues, "word = '" + word + "'", null);
                }
                else {
                    int wrongs = cursor.getInt(5);
                    ContentValues newValues = new ContentValues();
                    newValues.put("wrong", wrongs + 1);
                    database.update(DATABASE_TABLE, newValues, "word = '" + word + "'", null);
                }
            }
            else {
                ContentValues contentValues = new ContentValues();
                contentValues.put(WORD, word);
                contentValues.put(CHAPTER, chapter);
                contentValues.put(POSITION, position);
                contentValues.put(CORRECT, correct);
                contentValues.put(WRONG, wrong);

                // insert into database
                database.insert(DATABASE_TABLE, null, contentValues);
            }
        }
        finally {
            if(cursor != null)
                cursor.close();
        }
        database.close();
    }

    public void deleteResult(int chapter)
    {
        SQLiteDatabase database = getWritableDatabase();

        // delete into database. delete from dictionary where  word = ' ';
        database.delete(DATABASE_TABLE, CHAPTER + " = " + chapter, null);
        database.close();

        //tag: database, text: deleted spelling
        //Log.i(Constants.TAG1, Constants.TAG1VAL2 + Constants.SPACE + word.getSpelling());
    }

    public void deleteAll()
    {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(DATABASE_TABLE, null, null);
    }

    public void getResult(int chapter) {
        mResults.clear();

        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = null;

        try{
            cursor = database.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE chapter = " + chapter + ";", null);

            if (cursor.moveToFirst())
            {
                VocabularyHelper helper = new VocabularyHelper(context.getResources(), chapter);

                do
                {
                    String word = cursor.getString(cursor.getColumnIndex("word"));
                    int index = cursor.getInt(3);
                    int correct = cursor.getInt(4);
                    int wrong = cursor.getInt(5);

                    Result result = new Result();
                    Vocabulary vocabulary = helper.getVocabulary(index);
                    result.setVocabulary(vocabulary);
                    result.setChapter(chapter);
                    result.setIndex(index);
                    result.setNumOfCorrect(correct);
                    result.setNumOfWrong(wrong);
                    mResults.add(result);
                } while (cursor.moveToNext());
            }
        }
        finally {
            if(cursor != null)
                cursor.close();
        }
        // close the database
        database.close();
    }

    public ArrayList<Result> getResultAlphabetical(int chapter) {
        getResult(chapter);

        Collections.sort(mResults, new Comparator<Result>() {
            @Override
            public int compare(Result result1, Result result2) {
                return result1.getVocabulary().getWord().compareToIgnoreCase(result2.getVocabulary().getWord());
            }
        });

        return mResults;
    }

    public ArrayList<Result> getResultCorrect(int chapter) {
        getResult(chapter);

        Collections.sort(mResults, new Comparator<Result>() {
            @Override
            public int compare(Result result1, Result result2) {
                return result1.getVocabulary().getWord().compareToIgnoreCase(result2.getVocabulary().getWord());
            }
        });

        Collections.sort(mResults, new Comparator<Result>() {
            @Override
            public int compare(Result result1, Result result2) {
                return Integer.toString(result1.getNumOfWrong()).compareTo(Integer.toString(result2.getNumOfWrong()));
            }
        });

        Collections.sort(mResults, new Comparator<Result>() {
            @Override
            public int compare(Result result1, Result result2) {
                return Integer.toString(result2.getNumOfCorrect()).compareTo(Integer.toString(result1.getNumOfCorrect()));
            }
        });

        return mResults;
    }

    public ArrayList<Result> getResultWrong(int chapter) {
        getResult(chapter);

        Collections.sort(mResults, new Comparator<Result>() {
            @Override
            public int compare(Result result1, Result result2) {
                return result1.getVocabulary().getWord().compareToIgnoreCase(result2.getVocabulary().getWord());
            }
        });

        Collections.sort(mResults, new Comparator<Result>() {
            @Override
            public int compare(Result result1, Result result2) {
                return Integer.toString(result1.getNumOfCorrect()).compareTo(Integer.toString(result2.getNumOfCorrect()));
            }
        });

        Collections.sort(mResults, new Comparator<Result>() {
            @Override
            public int compare(Result result1, Result result2) {
                return Integer.toString(result2.getNumOfWrong()).compareTo(Integer.toString(result1.getNumOfWrong()));
            }
        });

        return mResults;
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
    {
        switch (oldVersion)
        {
            case 0:
                database.execSQL("ALTER TABLE " + DATABASE_TABLE + "ADD COLUMN hobbies TEXT;");

            case 1:
                // for future changes
                break;
        }
    }
}
