package com.app.dotastats.dotastats.Interfaces;

import android.app.FragmentManager;
import android.view.View;

import com.app.dotastats.dotastats.Beans.Matches;
import com.app.dotastats.dotastats.Beans.MostPlayedHeroes;
import com.app.dotastats.dotastats.Beans.Player;

import java.util.ArrayList;


public interface PlayerProfileInterface {
    void setPlayer(Player p);
    void setViews(ArrayList<View> views);
    void setMatches(Matches m);
    void setMostPlayerHeroes(MostPlayedHeroes h);
    void setFragmentManager(FragmentManager fm);
}
