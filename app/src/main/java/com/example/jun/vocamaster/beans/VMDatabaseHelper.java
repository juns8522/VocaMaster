package com.example.jun.vocamaster.beans;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by jun on 8/14/14.
 */
public class VMDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "myDatabase.db";
    private static final String DATABASE_TABLE = "Vocabularies";
    private static final String DATABASE_TABLE2 = "MyVocabulary";
    private static final String WORD = "word";
    private static final String CHAPTER = "chapter";
    private static final String POSITION = "position";
    private static final String MEANING = "meaning";
    private static final String SYNONYM = "synonym";

    private final static int DATABASE_VERSION = 1;
    private final static ArrayList<Vocabulary> mVocabularies = new ArrayList<Vocabulary>();
    private Context context;

    public VMDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

        getWritableDatabase().execSQL("CREATE TABLE if not exists " + DATABASE_TABLE +
                "( _id INTEGER primary key autoincrement," +
                "word TEXT," + "chapter INTEGER," + "position INTEGER);");

        getWritableDatabase().execSQL("CREATE TABLE if not exists " + DATABASE_TABLE2 +
                "( _id INTEGER primary key autoincrement," +
                "word TEXT," + "meaning TEXT," + "synonym TEXT);");
    }

    @Override
    /**
     * Create the database
     */
    public void onCreate(SQLiteDatabase database)
    {
        database.execSQL("CREATE TABLE " + DATABASE_TABLE +
                "( _id INTEGER primary key autoincrement," +
                "word TEXT," + "chapter INTEGER," + "position INTEGER);");

        database.execSQL("CREATE TABLE " + DATABASE_TABLE2 +
                "( _id INTEGER primary key autoincrement," +
                "word TEXT," + "meaning TEXT," + "synonym TEXT);");
    }

    /*
     *  This section is for table Vocabularies
     */
    public void addVocabulary(String word, int chapter, int position)
    {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WORD, word);
        contentValues.put(CHAPTER, chapter);
        contentValues.put(POSITION, position);

        // insert into database
        database.insert(DATABASE_TABLE, null, contentValues);
        database.close();

        //tag: database, text: inserted spelling
        //Log.i(SyncStateContract.Constants.TAG1, Constants.TAG1VAL1 + Constants.SPACE + word.getSpelling());
    }

    public void deleteWord(String word)
    {
        SQLiteDatabase database = getWritableDatabase();

        // delete into database. delete from dictionary where  word = ' ';
        database.delete(DATABASE_TABLE, WORD + " = '" + word + "'", null);
        database.close();

        //tag: database, text: deleted spelling
        //Log.i(Constants.TAG1, Constants.TAG1VAL2 + Constants.SPACE + word.getSpelling());
    }

    public ArrayList<Vocabulary> getVocabularies()
    {
        // clear mWordRows
        mVocabularies.clear();

        // receive values from database and create the instance of Word.
        // Finally add it to mWordRows
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = null;

        try {
            cursor = database.query(DATABASE_TABLE, null, null, null, null, null, null);
            if (cursor.moveToFirst())
            {
                VocabularyHelper helper;
                do
                {
                    String word = cursor.getString(cursor.getColumnIndex("word"));
                    int chapter = cursor.getInt(2);
                    int index = cursor.getInt(3);

                    helper = new VocabularyHelper(context.getResources(), chapter);
                    Vocabulary vocabulary = helper.getVocabulary(index);
                    mVocabularies.add(vocabulary);
                } while (cursor.moveToNext());
            }
        } finally {
            if(cursor != null)
                cursor.close();
        }
        // close the database
        database.close();

        sortAlphabetical();

        return mVocabularies;
    }

    public ArrayList<Vocabulary> getVocabularies(int chapter)
    {
        // clear mWordRows
        mVocabularies.clear();

        // receive values from database and create the instance of Word.
        // Finally add it to mWordRows
        SQLiteDatabase database = getWritableDatabase();
        VocabularyHelper helper;
        helper = new VocabularyHelper(context.getResources(), chapter);

        Cursor cursor = null;

        try {
            cursor = database.query(DATABASE_TABLE, new String[] {"_id", WORD, CHAPTER, POSITION}, CHAPTER + "=" + chapter, null, null, null, null);
            if (cursor.moveToFirst())
            {

                do
                {
                    //Toast.makeText(context, cursor.getString(cursor.getColumnIndex("word")), Toast.LENGTH_SHORT).show();
                    String word = cursor.getString(cursor.getColumnIndex("word"));
                    //int chapter = cursor.getInt(2);
                    int index = cursor.getInt(3);
                    //Toast.makeText(context, ""+index, Toast.LENGTH_SHORT).show();

                    Vocabulary vocabulary = helper.getVocabulary(index);
                    mVocabularies.add(vocabulary);
                } while (cursor.moveToNext());
            }
        } finally {
            if(cursor != null)
                cursor.close();
        }
        // close the database
        database.close();

        sortAlphabetical();

        return mVocabularies;
    }

    public void sortAlphabetical() {
        Collections.sort(mVocabularies, new Comparator<Vocabulary>() {
            @Override
            public int compare(Vocabulary vocabulary1, Vocabulary vocabulary2) {
                return vocabulary1.getWord().compareToIgnoreCase(vocabulary2.getWord());
            }
        });
    }

    public boolean isFavorite(Vocabulary vocabulary)
    {
        //get all words from the database
        ArrayList<Vocabulary> vocabularies = getVocabularies();

        //compare the parameter word with words in a list
        for(int index = 0; index < vocabularies.size(); index++)
            // if yes, return true, otherwise return false
            if(vocabulary.getWord().equals(vocabularies.get(index).getWord()))
                return true;
        return false;
    }

    public boolean isVocabulariesEmpty() {
        ArrayList<Vocabulary> vocabularies = getVocabularies();

        return vocabularies.size() == 0;
    }

    public void removeAllWords()
    {
        mVocabularies.clear();
        SQLiteDatabase database = getWritableDatabase();
        database.delete(DATABASE_TABLE, null, null);
        database.close();

        //tag: database, text: delete all data inside the database
        //Log.i(Constants.TAG1, Constants.TAG1VAL3);
    }

    /*
     *  This section is for table MyVocabulary
     */
    public void addMyVocabulary(String word, String meaning, String synonym)
    {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WORD, word);
        contentValues.put(MEANING, meaning);
        contentValues.put(SYNONYM, synonym);

        // insert into database
        database.insert(DATABASE_TABLE2, null, contentValues);
        database.close();

        //notifyAll();
        //tag: database, text: inserted spelling
        //Log.i(SyncStateContract.Constants.TAG1, Constants.TAG1VAL1 + Constants.SPACE + word.getSpelling());
    }

    public void deleteMyWord(String word)
    {
        SQLiteDatabase database = getWritableDatabase();

        // delete into database. delete from dictionary where  word = ' ';
        database.delete(DATABASE_TABLE2, WORD + " = '" + word + "'", null);
        database.close();

        //tag: database, text: deleted spelling
        //Log.i(Constants.TAG1, Constants.TAG1VAL2 + Constants.SPACE + word.getSpelling());
    }

    public ArrayList<Vocabulary> getMyVocabularies()
    {
        // clear mWordRows
        mVocabularies.clear();

        // receive values from database and create the instance of Word.
        // Finally add it to mWordRows
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = null;

        try {
            cursor = database.query(DATABASE_TABLE2, null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    String word = cursor.getString(cursor.getColumnIndex("word"));
                    String meaning = cursor.getString(2);
                    String synonym = cursor.getString(3);

                    //helper = new VocabularyHelper(context.getResources(), chapter);
                    Vocabulary vocabulary = new Vocabulary();
                    vocabulary.setChapterNumber(0);
                    vocabulary.setWord(word);
                    vocabulary.setMeanings(meaning);
                    vocabulary.setSynonyms(synonym);
                    mVocabularies.add(vocabulary);
                } while (cursor.moveToNext());
            }
        }
        finally {
            if(cursor != null)
                cursor.close();
        }

        // close the database
        database.close();

        return mVocabularies;
    }

    public void removeAllMyWords()
    {
        mVocabularies.clear();
        SQLiteDatabase database = getWritableDatabase();
        database.delete(DATABASE_TABLE2, null, null);
        database.close();

        //tag: database, text: delete all data inside the database
        //Log.i(Constants.TAG1, Constants.TAG1VAL3);
    }

    public boolean isExist(String word)
    {
        //get all words from the database
        ArrayList<Vocabulary> vocabularies = getMyVocabularies();

        //compare the parameter word with words in a list
        for(int index = 0; index < vocabularies.size(); index++)
            // if yes, return true, otherwise return false
            if(word.equals(vocabularies.get(index).getWord()))
                return true;
        return false;
    }

    public boolean isMyVocabulariesEmpty() {
        ArrayList<Vocabulary> vocabularies = getMyVocabularies();

        return vocabularies.size() == 0;
    }

    public void editWord(String word, String meaning, String synonym) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MEANING, meaning);
        contentValues.put(SYNONYM, synonym);
        database.update(DATABASE_TABLE2, contentValues, WORD + "='" + word +"'", null);
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
