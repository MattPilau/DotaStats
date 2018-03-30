package com.app.dotastats.dotastats.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.app.dotastats.dotastats.Beans.Player;
import com.app.dotastats.dotastats.R;
import com.app.dotastats.dotastats.utils.UtilsPreferences;

import java.util.ArrayList;

// displays each favorite players of the user
public class FavoritePlayersActivity extends AppCompatActivity {

    private ArrayList<Player> players;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference_layout);

        listview = findViewById(R.id.list_favorite_players);
    }

    // Action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:{
                Intent homeIntent = new Intent(this, MainActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                return true;
            }
            case R.id.heart:{
                Intent intent = new Intent(this, FavoritePlayersActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void goHome(){

    }
    public void goFavorite(){

    }

    @Override
    protected void onStart() {
        super.onStart();

        // gets the players from the preferences
        players = UtilsPreferences.getAllFavoritePlayers(getBaseContext());

        ArrayList<String> names = new ArrayList<>();
        for(int i = 0; i < players.size();i++){
            names.add(players.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(),
                R.layout.preference_players, R.id.nameFavoritePlayers, names);
        listview.setAdapter(adapter);

        // allow the user to go directly to the profile of the player
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
