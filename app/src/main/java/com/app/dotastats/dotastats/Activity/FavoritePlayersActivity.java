package com.app.dotastats.dotastats.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.app.dotastats.dotastats.Beans.Player;
import com.app.dotastats.dotastats.R;
import com.app.dotastats.dotastats.utils.UtilsPreferences;

import java.util.ArrayList;

public class FavoritePlayersActivity extends Activity {

    ArrayList<Player> players;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference_layout);

        listview = findViewById(R.id.list_favorite_players);
    }

    @Override
    protected void onStart() {
        super.onStart();

        players = UtilsPreferences.getAllFavoritePlayers(getBaseContext());

        ArrayList<String> names = new ArrayList<>();
        for(int i = 0; i < players.size();i++){
            names.add(players.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(),
                R.layout.preference_players, R.id.nameFavoritePlayers, names);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Player p = players.get(position);

                Intent myIntent = new Intent(listview.getContext(), PlayerProfileActivity.class);
                String s = p.getId();
                String lp = p.getLastPlayed();
                myIntent.putExtra("idPlayer", s);
                myIntent.putExtra("lastPlayed",lp);
                listview.getContext().startActivity(myIntent);
            }

        });
    }
}
