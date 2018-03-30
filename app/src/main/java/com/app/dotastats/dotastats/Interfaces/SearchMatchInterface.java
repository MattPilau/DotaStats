package com.app.dotastats.dotastats.Interfaces;

import android.app.FragmentManager;
import android.widget.ListView;

import com.app.dotastats.dotastats.Beans.MatchStat;

public interface SearchMatchInterface {

    void setIdMatch(String s);
    void setFragmentManager(FragmentManager fm);
    void setListView1(ListView l);
    void setListView2(ListView l);
    void setMatch(MatchStat ms);
}
