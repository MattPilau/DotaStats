package com.app.dotastats.dotastats.Interfaces;

import android.view.View;

import com.app.dotastats.dotastats.Matches;
import com.app.dotastats.dotastats.Player;

import java.util.ArrayList;

/**
 * Created by Matt on 06/03/2018.
 */

public interface PlayerProfileInterface {
    void setPlayer(Player p);
    void setViews(ArrayList<View> views);
    void setMatches(Matches m);
}
