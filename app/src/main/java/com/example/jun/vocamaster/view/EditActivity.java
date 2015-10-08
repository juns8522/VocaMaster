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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jun.vocamaster.R;
import com.example.jun.vocamaster.beans.VMDatabaseHelper;
import com.example.jun.vocamaster.util.Constants;

public class EditActivity extends Activity {

    private TextView tv_word;
    private EditText editText_meaning;
    private EditText editText_synonym;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        setupUI(findViewById(R.id.parent));

        Intent intent = getIntent();
        String word = intent.getExtras().getString("word");
        String meaning = intent.getExtras().getString("meaning");
        String synonym = intent.getExtras().getString("synonym");
        position = intent.getExtras().getInt("position");

        tv_word = (TextView) findViewById(R.id.tv_word);
        editText_meaning = (EditText) findViewById(R.id.editText_meaning);
        editText_synonym = (EditText) findViewById(R.id.editText_synonym);

        tv_word.setText(word);
        editText_meaning.setText(meaning);
        editText_synonym.setText(synonym);
    }

    public void setupUI(View view) {
        if(!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(EditActivity.this);
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
        getMenuInflater().inflate(R.menu.edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        int id = item.getItemId();
        switch(id) {
            case R.id.action_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_accept:
                editWord();
                return true;
            case R.id.action_search:
                search();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void editWord() {
        VMDatabaseHelper helper = new VMDatabaseHelper(this);
        String word = tv_word.getText().toString().trim();
        String meaning = editText_meaning.getText().toString().trim();
        String synonym = editText_synonym.getText().toString().trim();

        helper.editWord(word, meaning, synonym);
        Toast.makeText(getApplicationContext(), "Word Edited", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, FavouriteDetailActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("type", Constants.FAVORITE_TYPE2);
        startActivity(intent);
        finish();
    }

    private void search() {
        Intent intent = new Intent(this, WebSearchActivity.class);
        intent.putExtra("word", tv_word.getText().toString());
        intent.putExtra("meaning", editText_meaning.getText().toString());
        intent.putExtra("synonym", editText_synonym.getText().toString());
        intent.putExtra("position", position);
        startActivity(intent);
        finish();
    }
}
