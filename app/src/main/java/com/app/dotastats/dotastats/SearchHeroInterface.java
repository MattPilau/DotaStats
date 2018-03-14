package com.app.dotastats.dotastats;

import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

/**
 * Created by Alexis on 10/03/2018.
 */

public interface SearchHeroInterface {
    void setHeroName(String s);
    void setHero(Hero h);
    void setViews(ArrayList<View> views);
}
