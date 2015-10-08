package com.example.jun.vocamaster.view;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jun.vocamaster.R;
import com.example.jun.vocamaster.beans.FavouriteAdapter;
import com.example.jun.vocamaster.beans.Result;
import com.example.jun.vocamaster.beans.VMDatabaseHelper;
import com.example.jun.vocamaster.beans.Vocabulary;
import com.example.jun.vocamaster.util.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FavouriteActivity extends Activity implements NavigationDrawerFragment_favourite.NavigationDrawerCallbacks {

    private NavigationDrawerFragment_favourite mNavigationDrawerFragment;
    private CharSequence mTitle;
    private int position;
    private VMDatabaseHelper mHelper;
    private PlaceholderFragment mFragment;
    private String mWord;
    private boolean mWasSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        mHelper = new VMDatabaseHelper(this);
        mNavigationDrawerFragment = (NavigationDrawerFragment_favourite)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        mFragment = new PlaceholderFragment();
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(mWasSet) {
            callView();
            mWasSet = false;
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        this.position = position;
        callView();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            if(position == 0)           //VocaMaster
                getMenuInflater().inflate(R.menu.favourite, menu);
            else if (position == 1)     //My Vocabulary
                getMenuInflater().inflate(R.menu.favourite2, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.action_new:
                setCurrentPosition();
                mWasSet = true;
                intent = new Intent(this, AddActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_discard:
                if(!mHelper.isVocabulariesEmpty() && position == 0)
                    new DeleteAllDialogFragment().show(getFragmentManager(), "dialog");
                else if(!mHelper.isMyVocabulariesEmpty() && position == 1)
                    new DeleteAllDialogFragment().show(getFragmentManager(), "dialog");
                else
                    Toast.makeText(this, getResources().getString(R.string.message_no_favorite).toString(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void delete(String word) {
        mWord = word;
        new DeleteDialogFragment().show(getFragmentManager(), "dialog");
    }

    public void deleteVocabulary() {

        setCurrentPosition();

        if(position == 0)
            mHelper.deleteWord(mWord);
        else if (position == 1)
            mHelper.deleteMyWord(mWord);
        callView();
    }

    public void deleteAllWord() {

        setCurrentPosition();

        if(position == 0)
            mHelper.removeAllWords();
        else if (position == 1)
            mHelper.removeAllMyWords();
        callView();
    }


    public void setCurrentPosition() {
        mFragment.setCurrentPosition();
    }

    private void callView() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, mFragment.newInstance(position + 1))
                .commit();
    }

    public static class DeleteAllDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                ((FavouriteActivity)getActivity()).deleteAllWord();
                }
            });
            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.setMessage(R.string.message_delete_all)
                    .setTitle(R.string.delete_all);

            return builder.create();
        }
    }

    public static class DeleteDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ((FavouriteActivity)getActivity()).deleteVocabulary();
                }
            });
            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.setMessage(R.string.message_delete)
                    .setTitle(R.string.delete);

            return builder.create();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment{

        private static final String ARG_SECTION_NUMBER = "section_number";
        private static int currentPosition = 0;
        private static ListView mList;
        private static ArrayList<Vocabulary> mVocabularies = new ArrayList<Vocabulary>();
        
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_favourite, container, false);
                        int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            mList = (ListView) rootView.findViewById(R.id.listViewFavourite);
            VMDatabaseHelper helper = new VMDatabaseHelper(rootView.getContext());

            if(sectionNumber == 1) {
                mVocabularies = helper.getVocabularies();
                FavouriteAdapter adapter = new FavouriteAdapter(rootView.getContext(), mVocabularies, Constants.FAVORITE_TYPE1);
                mList.setAdapter(adapter);
                mList.setSelection(currentPosition);
            }
            else {
                mVocabularies = helper.getMyVocabularies();
                FavouriteAdapter adapter = new FavouriteAdapter(rootView.getContext(), mVocabularies, Constants.FAVORITE_TYPE2);
                mList.setAdapter(adapter);
                mList.setSelection(currentPosition);
            }
            currentPosition = 0;
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((FavouriteActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }


        public static void setCurrentPosition() {
            currentPosition = mList.getFirstVisiblePosition();
        }
    }
}
