package com.app.dotastats.dotastats.Interfaces;

import android.widget.Button;
import android.widget.ProgressBar;

import com.app.dotastats.dotastats.Adapters.AllHeroAdapter;
import com.app.dotastats.dotastats.Beans.Heroes;

public interface AllHeroesInterface {
    void setBar(ProgressBar bar);
    void setAdapter(AllHeroAdapter adapter);
    void setHeroes(Heroes h);
    void setButton(Button b);
}
