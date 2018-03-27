package com.app.dotastats.dotastats.Interfaces;

import android.widget.Button;
import android.widget.ProgressBar;

import com.app.dotastats.dotastats.Adapters.PlayerAdapter;
import com.app.dotastats.dotastats.Beans.Players;


public interface SearchPlayerInterface {

    Players getPlayers();
    void setName(String s);
    void setProgressBar(ProgressBar pBar);
    void setAdapter(PlayerAdapter a);
    void setButton(Button b1,Button b2);
}
