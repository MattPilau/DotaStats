package com.app.dotastats.dotastats;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.dotastats.dotastats.Adapters.MostPlayedHeroesAdapter;
import com.app.dotastats.dotastats.Beans.MostPlayedHeroes;

/**
 * Created by Matt on 11/03/2018.
 */

public class LastHeroesFragment extends ListFragment {

    MostPlayedHeroes mostPlayedHeroes;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_last_matches,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MostPlayedHeroesAdapter adapter = new MostPlayedHeroesAdapter(getActivity().getBaseContext(),mostPlayedHeroes.getMostPlayedHeroes());
        setListAdapter(adapter);
    }

    public void setHeroes(MostPlayedHeroes h){
        mostPlayedHeroes = h;
    }
}
