package com.example.jun.vocamaster.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jun.vocamaster.R;
import com.example.jun.vocamaster.beans.ReceiverAdapter;
import com.example.jun.vocamaster.beans.VMDatabaseHelper;
import com.example.jun.vocamaster.beans.Vocabulary;

import java.util.ArrayList;

public class ReceiverActivity extends Activity  implements AdapterView.OnItemSelectedListener{

    Spinner mSpinner;
    LinearLayout mLayout;
    ArrayList<Vocabulary> mVocabularies;
    TextView tv_word;
    EditText editText_message;
    int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);

        //setting to hide soft keyboard
        setupUI(findViewById(R.id.parent));

        Intent intent = getIntent();
        String message = intent.getStringExtra(Intent.EXTRA_TEXT);

        editText_message = (EditText) findViewById(R.id.editText_message);

        if(message != null) {
            editText_message.setText(message.toLowerCase());
        }

        mSpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.share_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);

        mLayout = (LinearLayout) findViewById(R.id.sub_parent);
        tv_word = (TextView) findViewById(R.id.tv_word);

        ListView list = (ListView) findViewById(R.id.listView);
        mVocabularies = new VMDatabaseHelper(this).getMyVocabularies();
        ReceiverAdapter receiverAdapter = new ReceiverAdapter(this, mVocabularies);
        list.setAdapter(receiverAdapter);
    }

    public void setupUI(View view) {
        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(ReceiverActivity.this);
                    return false;
                }
            });
        }
        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.receiver, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_accept:
                if(isValid()) {
                    proceed();
                    finish();
                    return true;
                }
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        switch(pos) {
            case 0:     //Add a word
                mLayout.setVisibility(View.INVISIBLE);
                tv_word.setText("");
                break;
            case 1:     //Edit meaning
                mLayout.setVisibility(View.VISIBLE);
                break;
            case 2:     //Edit synonym
                mLayout.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void selectWord(int position) {
        mPosition = position;
        String word = mVocabularies.get(position).getWord();
        tv_word.setText(word);
    }

    public boolean isValid() {
        int selectedItemPosition = mSpinner.getSelectedItemPosition();

        if(selectedItemPosition == 0) {
            for(int i = 0; i < mVocabularies.size(); i++) {
                if(mVocabularies.get(i).getWord().equals(editText_message.getText().toString().toLowerCase())) {
                    Toast.makeText(this, "The word exists in My Vocabulary list", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        else {
            if(tv_word.getText().equals("") || tv_word.getText() == null) {
                Toast.makeText(this, "Select a word to edit", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    public void proceed() {
        int selectedItemPosition = mSpinner.getSelectedItemPosition();
        String newWord = editText_message.getText().toString().toLowerCase();
        String message = editText_message.getText().toString().toLowerCase();

        Intent intent;
        if(selectedItemPosition == 0) {
            intent = new Intent(this, AddActivity.class);
            intent.putExtra("word", newWord);
            startActivity(intent);
        }
        else {
            Vocabulary vocabulary = mVocabularies.get(mPosition);

            String word = vocabulary.getWord();
            String meaning = getMeaning(vocabulary);
            String synonym = getSynonym(vocabulary);

            if (selectedItemPosition == 1) {
                intent = new Intent(this, EditActivity.class);
                intent.putExtra("word", word);
                intent.putExtra("meaning", (meaning + "\n" + message).trim());
                intent.putExtra("synonym", synonym);
                intent.putExtra("position", mPosition);
                startActivity(intent);
            } else if (selectedItemPosition == 2) {
                intent = new Intent(this, EditActivity.class);
                intent.putExtra("word", word);
                intent.putExtra("meaning", meaning);
                intent.putExtra("synonym", (synonym + "\n" + message).trim());
                intent.putExtra("position", mPosition);
                startActivity(intent);
            }
        }
    }

    public String getMeaning(Vocabulary vocabulary) {
        String meaning = "";

        if(vocabulary.getMeanings().size() == 1)
            meaning += vocabulary.getMeanings().get(0);
        else {
            for (int i = 0; i < vocabulary.getMeanings().size(); i++) {
                meaning += (i+1) + ". " + vocabulary.getMeanings().get(i) + "\n";
            }
            meaning.trim();
        }
        return meaning;
    }

    public String getSynonym(Vocabulary vocabulary) {
        String synonym = "";

        if(vocabulary.getSynonyms().size() == 1)
            synonym += vocabulary.getSynonyms().get(0);
        else {
            for (int i = 0; i < vocabulary.getSynonyms().size(); i++) {
                synonym += (i+1) + ". " + vocabulary.getSynonyms().get(i) + "\n";
            }
            synonym.trim();
        }
        return synonym;
    }
}
