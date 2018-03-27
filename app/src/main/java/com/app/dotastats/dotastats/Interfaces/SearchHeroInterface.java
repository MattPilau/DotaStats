package com.app.dotastats.dotastats.Interfaces;

import android.view.View;

import com.app.dotastats.dotastats.Beans.Hero;

import java.util.ArrayList;

public interface SearchHeroInterface {
    void setHeroName(String s);
    void setHero(Hero h);
    void setViews(ArrayList<View> views);
}
