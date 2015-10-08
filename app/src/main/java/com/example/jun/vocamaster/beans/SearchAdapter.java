package com.example.jun.vocamaster.beans;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jun.vocamaster.R;
import com.example.jun.vocamaster.view.SearchDetailActivity;

import java.util.ArrayList;

/**
 * Created by jun on 8/22/14.
 */
public class SearchAdapter extends ArrayAdapter<Vocabulary> {
    private final Context context;
    private final ArrayList<Vocabulary> vocabularies;

    public SearchAdapter(Context context, ArrayList<Vocabulary> vocabularies) {
        super(context, R.layout.fragment_search_single, vocabularies);
        this.context = context;
        this.vocabularies = vocabularies;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.fragment_search_single, parent, false);

        TextView tv_word = (TextView) rowView.findViewById(R.id.tv_list_word);
        tv_word.setText(vocabularies.get(position).getWord());

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Vocabulary vocabulary = vocabularies.get(position);
                int chapterNumber = vocabulary.getChapterNumber();
                String word = vocabulary.getWord();
                String meaning = "";
                String synonym = "";

                if(vocabulary.getMeanings().size() == 1)
                    meaning += vocabulary.getMeanings().get(0);
                else {
                    for (int i = 0; i < vocabulary.getMeanings().size(); i++) {
                        meaning += (i+1) + ". " + vocabulary.getMeanings().get(i) + "\n";
                    }
                    meaning.trim();
                }

                if(vocabulary.getSynonyms().size() == 1)
                    synonym += vocabulary.getSynonyms().get(0);
                else {
                    for (int i = 0; i < vocabulary.getSynonyms().size(); i++) {
                        synonym += (i+1) + ". " + vocabulary.getSynonyms().get(i) + "\n";
                    }
                    synonym.trim();
                }

                Intent intent = new Intent(context, SearchDetailActivity.class);

                intent.putExtra("chapterNumber", chapterNumber);
                intent.putExtra("word", word);
                intent.putExtra("meaning", meaning);
                intent.putExtra("synonym", synonym);

                context.startActivity(intent);
            }
        });

        return rowView;
    }
}
