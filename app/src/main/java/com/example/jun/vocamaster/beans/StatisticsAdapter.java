package com.example.jun.vocamaster.beans;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jun.vocamaster.R;
import com.example.jun.vocamaster.view.StatisticsActivity;
import com.example.jun.vocamaster.view.StatisticsDetailActivity;

import java.util.ArrayList;

/**
 * Created by jun on 8/24/14.
 */
public class StatisticsAdapter extends ArrayAdapter<String>{
    private final Context context;
    private final ArrayList<String> chapters;

    public StatisticsAdapter(Context context, ArrayList<String> chapters) {
        super(context, R.layout.fragment_statistics_single, chapters);
        this.context = context;
        this.chapters = chapters;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.fragment_statistics_single, parent, false);

        TextView tv_list_chapter = (TextView) rowView.findViewById(R.id.tv_list_chapter);
        TextView tv_average = (TextView) rowView.findViewById(R.id.tv_average);

        int average = getAverage(position + 1);

        tv_list_chapter.setText(chapters.get(position));
        tv_average.setText(Integer.toString(average) + "%");

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent((StatisticsActivity)context, StatisticsDetailActivity.class);
                intent.putExtra("chapterIndex", position);
                context.startActivity(intent);
            }
        });
        return rowView;
    }

    private int getAverage(int chapter) {
        int average = 0;
        double correct = 0;
        double wrong = 0;

        VMStatHelper statHelper = new VMStatHelper(context);
        ArrayList<Result> results = statHelper.getResultAlphabetical(chapter);

        for(int i = 0; i < results.size(); i++) {
            correct += results.get(i).getNumOfCorrect();
            wrong += results.get(i).getNumOfWrong();
        }

        if((correct + wrong) != 0)
            average = (int) (((correct) / (correct + wrong)) * 100);

        return average;
    }
}
