package com.example.jun.vocamaster.beans;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.jun.vocamaster.R;
import com.example.jun.vocamaster.view.VocabularyDetailActivity;

import java.util.ArrayList;

/**
 * Created by jun on 8/12/14.
 */
public class VocabularyAdapter extends ArrayAdapter<Vocabulary> {
    private final Context context;
    private final ArrayList<Vocabulary> vocabularies;
    private final int chapterIndex;

    VMDatabaseHelper helper;
    ArrayList<Vocabulary> favouriteVocabularies;

    public VocabularyAdapter(Context context, ArrayList<Vocabulary> vocabularies, int chapterIndex) {
        super(context, R.layout.fragment_vocabulary_single, vocabularies);
        this.context = context;
        this.vocabularies = vocabularies;
        this.chapterIndex = chapterIndex;

        helper = new VMDatabaseHelper(context);
        favouriteVocabularies = helper.getVocabularies(chapterIndex);
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.fragment_vocabulary_single, parent, false);
        TextView tv_word = (TextView) rowView.findViewById(R.id.tv_list_word);
        tv_word.setText(vocabularies.get(position).getWord());

        final ToggleButton btn_favourite = (ToggleButton) rowView.findViewById(R.id.toggleButton);
        btn_favourite.setBackgroundResource(R.drawable.ic_star_silver_48);
        btn_favourite.setChecked(false);

        for(int i = 0; i < favouriteVocabularies.size(); i++) {
            if (favouriteVocabularies.get(i).getWord().equals(vocabularies.get(position).getWord())) {
                btn_favourite.setBackgroundResource(R.drawable.ic_star_48);
                btn_favourite.setChecked(true);
            }
        }


        btn_favourite.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if(btn_favourite.isChecked()) {
                    btn_favourite.setBackgroundResource(R.drawable.ic_star_48);
                    helper.addVocabulary(vocabularies.get(position).getWord(), chapterIndex, position);
                }
                else {
                    btn_favourite.setBackgroundResource(R.drawable.ic_star_silver_48);
                    helper.deleteWord(vocabularies.get(position).getWord());
                }
                favouriteVocabularies = helper.getVocabularies(chapterIndex);
            }
        });

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VocabularyDetailActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("chapterIndex", chapterIndex);

                context.startActivity(intent);
            }
        });
        notifyDataSetChanged();
        return rowView;
    }
}
