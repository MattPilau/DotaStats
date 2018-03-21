package com.app.dotastats.dotastats.Interfaces;

import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.app.dotastats.dotastats.Adapters.PlayerAdapter;
import com.app.dotastats.dotastats.Beans.Players;

/**
 * Created by Mamie_Chiffons77 on 04/03/2018.
 */

public interface SearchPlayerInterface {

    Players getPlayers();
    void setName(String s);
    void setProgressBar(ProgressBar pBar);
    void setAdapter(PlayerAdapter a);
}
