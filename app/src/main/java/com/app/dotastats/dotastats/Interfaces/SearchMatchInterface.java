package com.app.dotastats.dotastats.Interfaces;

import android.app.FragmentManager;
import android.view.View;
import android.widget.ListView;

import com.app.dotastats.dotastats.MatchStat;
import java.util.ArrayList;

/**
 * Created by Alexis on 22/03/2018.
 */

public interface SearchMatchInterface {

    void setIdMatch(String s);
    void setFragmentManager(FragmentManager fm);
    void setListView1(ListView l);
    void setListView2(ListView l);
    void setMatch(MatchStat ms);
}
