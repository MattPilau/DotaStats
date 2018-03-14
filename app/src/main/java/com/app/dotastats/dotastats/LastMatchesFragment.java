package com.app.dotastats.dotastats;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Matt on 11/03/2018.
 */

public class LastMatchesFragment extends ListFragment {

    Matches matches;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /** Inflating the layout for this fragment **/
        view = inflater.inflate(R.layout.fragment_last_matches,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LastMatchesAdapter adapter = new LastMatchesAdapter(getActivity().getBaseContext(), matches.getMatches());
        setListAdapter(adapter);
    }

    public void setMatches(Matches m){
        matches = m;
    }

}
