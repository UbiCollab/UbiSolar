package com.sintef_energy.ubisolar.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.sintef_energy.ubisolar.R;
import com.sintef_energy.ubisolar.activities.DrawerActivity;

/**
 * Created by perok on 2/11/14.
 */
public class SocialFragment extends DefaultTabFragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String TAG = SocialFragment.class.getName();

    private SimpleCursorAdapter adapter;
    private String [] friends;
    private View view;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SocialFragment newInstance(int sectionNumber) {
        SocialFragment fragment = new SocialFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public SocialFragment() {
    }

    /**
     * The first call to a created fragment
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //Callback to activity
        ((DrawerActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_compare_friend, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //final ListView listview = (ListView)getActivity().findViewById(R.id.socialList);
        friends = new String[] {"Tor-Håkon", "Pia",
                "Beate", "Per-Øyvind", "Håvard", "Lars Erik"};

        /**adapter = new SimpleCursorAdapter();*/

        //listview.setAdapter(adapter);

        if (savedInstanceState != null) {
            // Restore last state for checked position.
        }
    }

    /*End lifecycle*/
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putInt("curChoice", mCurCheckPosition);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}