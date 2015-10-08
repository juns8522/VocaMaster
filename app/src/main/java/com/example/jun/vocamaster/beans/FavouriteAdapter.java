package com.example.jun.vocamaster.beans;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jun.vocamaster.view.FavouriteActivity;
import com.example.jun.vocamaster.view.FavouriteDetailActivity;
import com.example.jun.vocamaster.R;

import java.util.ArrayList;

/**
 * Created by jun on 8/14/14.
 */
public class FavouriteAdapter extends ArrayAdapter<Vocabulary> {
    private final Context context;
    private final ArrayList<Vocabulary> vocabularies;
    private String type;

    public FavouriteAdapter(Context context, ArrayList<Vocabulary> vocabularies, String type) {
        super(context, R.layout.fragment_favourite_single, vocabularies);
        this.context = context;
        this.vocabularies = vocabularies;
        this.type = type;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.fragment_favourite_single, parent, false);
        final String word = vocabularies.get(position).getWord();

        TextView tv_word = (TextView) rowView.findViewById(R.id.tv_list_word);
        tv_word.setText(word);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FavouriteDetailActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("type", type);
                context.startActivity(intent);
            }
        });

        rowView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ((FavouriteActivity)context).delete(word);
                return true;
            }
        });

        return rowView;
    }
}
