package com.app.dotastats.dotastats;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.dotastats.dotastats.Adapters.LastMatchesAdapter;
import com.app.dotastats.dotastats.Beans.Matches;

// display the last 15 games of the player
public class LastMatchesFragment extends ListFragment {

    Matches matches;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
