package com.app.dotastats.dotastats.Interfaces;

import android.widget.ListView;
import android.widget.ProgressBar;

import com.app.dotastats.dotastats.Players;

/**
 * Created by Mamie_Chiffons77 on 04/03/2018.
 */

public interface SearchPlayerInterface {

    Players getPlayers();
    void setName(String s);
    void setListView(ListView l);
    void setProgressBar(ProgressBar pBar);
}
