package com.app.dotastats.dotastats;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.dotastats.dotastats.Adapters.LastMatchesAdapter;
import com.app.dotastats.dotastats.Beans.Matches;

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

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        super.onListItemClick(l, v, position, id);

        Intent myIntent = new Intent(view.getContext(), MatchActivity.class);
        String s = matches.getMatch(position).getMatchId();
        Log.i("SelectMatch", "match id : " + s);
        myIntent.putExtra("idMatch", s);
        view.getContext().startActivity(myIntent);
    }

    public void setMatches(Matches m){
        matches = m;
    }

}
