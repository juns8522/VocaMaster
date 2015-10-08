package com.example.jun.vocamaster.beans;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jun.vocamaster.R;
import com.example.jun.vocamaster.view.ReceiverActivity;

import java.util.ArrayList;

/**
 * Created by jun on 8/27/14.
 */
public class ReceiverAdapter extends ArrayAdapter<Vocabulary> {

    private Context context;
    private ArrayList<Vocabulary> vocabularies;

    public ReceiverAdapter(Context context, ArrayList<Vocabulary> vocabularies) {
        super(context, R.layout.fragment_search_single, vocabularies);
        this.context = context;
        this.vocabularies = vocabularies;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.fragment_receiver_single, parent, false);

        TextView tv_word = (TextView) rowView.findViewById(R.id.tv_list_word);
        tv_word.setText(vocabularies.get(position).getWord());

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ReceiverActivity)context).selectWord(position);
            }
        });
        return rowView;
    }
}
