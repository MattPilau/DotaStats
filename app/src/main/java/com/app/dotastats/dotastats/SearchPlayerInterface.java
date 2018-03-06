package com.app.dotastats.dotastats;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

/**
 * Created by Mamie_Chiffons77 on 04/03/2018.
 */

public interface SearchPlayerInterface {

    Players getPlayers();
    void setName(String s);
    void setListView(ListView l);
    void setProgressBar(ProgressBar pBar);
}
