package com.example.jun.vocamaster.view;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jun.vocamaster.R;
import com.example.jun.vocamaster.beans.VMDatabaseHelper;

public class AddActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        Intent intent = getIntent();
        if(intent.getStringExtra("word") != null) {
            String word = intent.getStringExtra("word");
            EditText editText_word = (EditText) findViewById(R.id.editText_word);
            editText_word.setText(word);
        }
        if(intent.getStringExtra("meaning") != null) {
            String meaning = intent.getStringExtra("meaning");
            String synonym = intent.getStringExtra("synonym");

            EditText editText_meaning = (EditText) findViewById(R.id.editText_meaning);
            EditText editText_synonym = (EditText) findViewById(R.id.editText_synonym);

            editText_meaning.setText(meaning);
            editText_synonym.setText(synonym);
        }

        setupUI(findViewById(R.id.parent));
    }

    public void setupUI(View view) {
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(AddActivity.this);
                    return false;
                }
            });
        }
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
        getMenuInflater().inflate(R.menu.add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
                super. onBackPressed();
                return true;
            case R.id.action_search:
                search();
                return true;
            case R.id.action_accept:
                add();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void add() {
        EditText editText_word = (EditText) findViewById(R.id.editText_word);
        EditText editText_meaning = (EditText) findViewById(R.id.editText_meaning);
        EditText editText_synonym = (EditText) findViewById(R.id.editText_synonym);

        String word = editText_word.getText().toString().toLowerCase().trim();
        String meaning = editText_meaning.getText().toString().toLowerCase().trim();
        String synonym = editText_synonym.getText().toString().toLowerCase().trim();

        VMDatabaseHelper helper = new VMDatabaseHelper(this);

        if(word.equals("")) {
            Toast.makeText(this, "You must fill out WORD EditText box", Toast.LENGTH_SHORT).show();
        }
        else if(!helper.isExist(word)) {
            helper.addMyVocabulary(word, meaning, synonym);
            Toast.makeText(this, "Word \"" + word + "\" added", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        else {
            Toast.makeText(this, "The word exists in My Vocabulary List", Toast.LENGTH_SHORT).show();
        }
    }

    private void search() {
        EditText editText_word = (EditText) findViewById(R.id.editText_word);
        EditText editText_meaning = (EditText) findViewById(R.id.editText_meaning);
        EditText editText_synonym = (EditText) findViewById(R.id.editText_synonym);

        String word = editText_word.getText().toString().toLowerCase().trim();
        String meaning = editText_meaning.getText().toString().toLowerCase().trim();
        String synonym = editText_synonym.getText().toString().toLowerCase().trim();

        Intent intent = new Intent(this, WebSearchActivity.class);
        intent.putExtra("word", word);
        intent.putExtra("meaning", meaning);
        intent.putExtra("synonym", synonym);
        startActivity(intent);
        finish();
    }
}
