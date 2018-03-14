package com.app.dotastats.dotastats.Interfaces;

import android.view.View;

import com.app.dotastats.dotastats.Hero;

import java.util.ArrayList;

/**
 * Created by Alexis on 10/03/2018.
 */

public interface SearchHeroInterface {
    void setHeroName(String s);
    void setHero(Hero h);
    void setViews(ArrayList<View> views);
}
