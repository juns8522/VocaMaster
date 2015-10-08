package com.example.jun.vocamaster.view;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.preference.PreferenceManager;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import com.example.jun.vocamaster.R;
import com.example.jun.vocamaster.beans.Vocabulary;
import com.example.jun.vocamaster.beans.VocabularyHelper;
import com.example.jun.vocamaster.util.Constants;

import java.util.ArrayList;
import java.util.Random;

public class VMAppWidget extends AppWidgetProvider {

    static Random rand = new Random();
    static String PENDING_ACTION = "action";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        for (int i=0; i<N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        Vocabulary vocabulary = getVocabulary(context);

        //CharSequence widgetText = context.getString(R.string.appwidget_text);
        CharSequence word = vocabulary.getWord();
        CharSequence meaning = vocabulary.getMeanings().get(rand.nextInt(vocabulary.getMeanings().size()));
        CharSequence synonym = vocabulary.getSynonyms().get(rand.nextInt(vocabulary.getSynonyms().size()));

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.vmapp_widget);

        views.setOnClickPendingIntent(R.id.btn_refresh, buildButtonPendingIntent(context));

        views.setTextViewText(R.id.appwidget_word, word);
        views.setTextViewText(R.id.appwidget_meaning, meaning);
        views.setTextViewText(R.id.appwidget_synonym, synonym);

        // Instruct the widget manager to update the widget
        ComponentName appWidgetId = new ComponentName(context, VMAppWidget.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
            int appWidgetId) {

        Vocabulary vocabulary = getVocabulary(context);

        CharSequence word = vocabulary.getWord();
        CharSequence meaning = vocabulary.getMeanings().get(0);
        CharSequence synonym = vocabulary.getSynonyms().get(0);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.vmapp_widget);
        views.setOnClickPendingIntent(R.id.btn_refresh, buildButtonPendingIntent(context));
        views.setTextViewText(R.id.appwidget_word, word);
        views.setTextViewText(R.id.appwidget_meaning, meaning);
        views.setTextViewText(R.id.appwidget_synonym, synonym);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static PendingIntent buildButtonPendingIntent(Context context) {
        Intent intent = new Intent(context, VMAppWidget.class);
        intent.setAction(PENDING_ACTION);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    public static Vocabulary getVocabulary(Context context) {
        ArrayList <Vocabulary> vocabularies = new ArrayList<Vocabulary>();
        for(int i = 1; i <= Constants.NUMBER_OF_CHAPTERS; i++) {
            vocabularies.addAll(new VocabularyHelper(context.getResources(), i).getVocabularies());
        }
        int randomIndex = rand.nextInt(vocabularies.size());

        return vocabularies.get(randomIndex);
    }
}
