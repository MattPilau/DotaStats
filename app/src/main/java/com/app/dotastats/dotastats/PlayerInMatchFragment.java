package com.app.dotastats.dotastats;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.dotastats.dotastats.Beans.PlayerStat;


public class PlayerInMatchFragment extends Fragment {

    PlayerStat player;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_player_in_match,container,false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((TextView)view.findViewById(R.id.playerName)).setText(player.getPlayerName());
        ((TextView)view.findViewById(R.id.kills)).setText(String.valueOf(player.getKills()));
        ((TextView)view.findViewById(R.id.deaths)).setText(String.valueOf(player.getDeaths()));
        ((TextView)view.findViewById(R.id.assists)).setText(String.valueOf(player.getAssists()));
        ((TextView)view.findViewById(R.id.gpm)).setText(String.valueOf(player.getGpm()));
        ((TextView)view.findViewById(R.id.xppm)).setText(String.valueOf(player.getXppm()));
        ((TextView)view.findViewById(R.id.heroDamage)).setText(String.valueOf(player.getHeroDamage()));
        ((TextView)view.findViewById(R.id.heroHealing)).setText(String.valueOf(player.getHeroHealing()));
        ((ImageView)view.findViewById(R.id.playerImage)).setImageBitmap(player.getImage());
    }

    public void setPlayer(PlayerStat p){
        player = p;
    }



















}
