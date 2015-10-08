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
import com.example.jun.vocamaster.view.StatisticsVocaDetailActivity;

import java.util.ArrayList;

/**
 * Created by jun on 8/25/14.
 */
public class StatisticsDetailAdapter extends ArrayAdapter {
    private final Context context;
    private final ArrayList<Result> results;
    private final String displayType;
    private final int chapter;

    public StatisticsDetailAdapter(Context context, ArrayList<Result> results, int chapter, String displayType) {
        super(context, R.layout.activity_statistics_detail_single, results);
        this.results = results;
        this.context = context;
        this.chapter = chapter;
        this.displayType = displayType;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_statistics_detail_single, parent, false);

        TextView tv_word = (TextView) rowView.findViewById(R.id.tv_word);
        TextView tv_correct = (TextView) rowView.findViewById(R.id.tv_correct);
        TextView tv_wrong = (TextView) rowView.findViewById(R.id.tv_wrong);

        tv_word.setText(results.get(position).getVocabulary().getWord());
        tv_correct.setText(context.getString(R.string.correct_) + results.get(position).getNumOfCorrect());
        tv_wrong.setText(context.getString(R.string.wrong_) + results.get(position).getNumOfWrong());

        final ToggleButton btn_favourite = (ToggleButton) rowView.findViewById(R.id.toggleButton);

        if(isFavorite(results.get(position).getVocabulary())) {
            btn_favourite.setBackgroundResource(R.drawable.ic_star_48);
            btn_favourite.setChecked(true);
        }
        else {
            btn_favourite.setBackgroundResource(R.drawable.ic_star_silver_48);
            btn_favourite.setChecked(false);
        }

        btn_favourite.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                VMDatabaseHelper helper = new VMDatabaseHelper(context);

                if(btn_favourite.isChecked()) {
                    btn_favourite.setBackgroundResource(R.drawable.ic_star_48);
                    helper.addVocabulary(results.get(position).getVocabulary().getWord(), results.get(position).getChapter(), results.get(position).getIndex());
                }
                else {
                    btn_favourite.setBackgroundResource(R.drawable.ic_star_silver_48);
                    helper.deleteWord(results.get(position).getVocabulary().getWord());
                }
                notifyDataSetChanged();
            }
        });

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StatisticsVocaDetailActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("chapter", chapter);
                intent.putExtra("displayType", displayType);
                context.startActivity(intent);
        }
        });

        return rowView;
    }

    private boolean isFavorite(Vocabulary vocabulary) {
        VMDatabaseHelper helper = new VMDatabaseHelper(context);
        ArrayList<Vocabulary> favoriteVocabularies = helper.getVocabularies(chapter);

        for(int i = 0; i < favoriteVocabularies.size(); i++)
            if(favoriteVocabularies.get(i).getWord().equals(vocabulary.getWord()))
                return true;
        return false;
    }
}
