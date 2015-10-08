package com.example.jun.vocamaster.beans;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.jun.vocamaster.R;
import com.example.jun.vocamaster.view.TestActivity;
import com.example.jun.vocamaster.view.TestActivity2;

import java.util.ArrayList;

/**
 * Created by jun on 8/15/14.
 */
public class TestMenuAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final ArrayList<String> chapters;

    public TestMenuAdapter(Context context, ArrayList<String> chapters) {
        super(context, R.layout.fragment_test_menu_single, chapters);
        this.context = context;
        this.chapters = chapters;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.fragment_test_menu_single, parent, false);

        TextView tv_chapter = (TextView) rowView.findViewById(R.id.tv_chapter);
        Button btn_word = (Button) rowView.findViewById(R.id.btn_word);
        Button btn_meaning = (Button) rowView.findViewById(R.id.btn_meaning);
        Button btn_synonym = (Button) rowView.findViewById(R.id.btn_synonym);

        tv_chapter.setText(chapters.get(position));

        btn_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TestActivity2.class);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });

        btn_meaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TestActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("type", "meaning");
                context.startActivity(intent);
            }
        });

        btn_synonym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TestActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("type", "synonym");
                context.startActivity(intent);
            }
        });

        return rowView;
    }
}
