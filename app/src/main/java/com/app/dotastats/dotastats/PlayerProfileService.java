package com.app.dotastats.dotastats;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import com.app.dotastats.dotastats.Beans.Matches;
import com.app.dotastats.dotastats.Beans.MostPlayedHeroes;
import com.app.dotastats.dotastats.Beans.Player;
import com.app.dotastats.dotastats.Interfaces.PlayerProfileInterface;
import com.app.dotastats.dotastats.utils.UtilsHttp;

public class PlayerProfileService extends Service {

    TaskProfile myTask;
    TaskHero taskHero;
    Player player;
    ArrayList<View> views;
    Matches matches;
    MostPlayedHeroes mostPlayedHeroes;
    FragmentManager fragmentManager;

    private final IBinder binder = new MonServiceBinder();

    public PlayerProfileService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        Boolean wantsMatches = intent.getBooleanExtra("matches",true);

        final Handler handler = new Handler();

        if(wantsMatches) {
            handler.post(new Runnable() {
                public void run() {
                    Toast.makeText(getBaseContext(), "Request made !" + player.getId(), Toast.LENGTH_SHORT).show();
                    myTask = new TaskProfile(player, views, matches, fragmentManager);
                    myTask.execute();
                }
            });
        }
        else{
            handler.post(new Runnable() {
                public void run() {
                    Toast.makeText(getBaseContext(), "Second Request made ! " , Toast.LENGTH_SHORT).show();
                    taskHero = new TaskHero(player,fragmentManager,mostPlayedHeroes);
                    taskHero.execute();
                }
            });
        }
        return START_STICKY;
    }

    public void onDestroy() { // Destruction du service
        Toast.makeText(getBaseContext(), "DESTRUCTION", Toast.LENGTH_SHORT).show();
    }

    private static class TaskProfile extends AsyncTask<Void, Void, Void> {
        Player player;
        ArrayList<View> views;
        Matches matches;
        FragmentManager fragmentManager;

        TaskProfile(Player p,ArrayList<View> v, Matches m,FragmentManager manager){
            player = p;
            views = v;
            matches = m;
            fragmentManager = manager;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ((TextView) views.get(0)).setText(player.getName()); // name
            ((TextView) views.get(1)).setText(player.getLastPlayed()); // last played
            ((TextView) views.get(2)).setText(player.getCountry()); // country
            ((TextView) views.get(3)).setText(player.getMmr()); // mmr
            ((TextView) views.get(4)).setText(player.getSteamLink()); // steamlink
            ((TextView) views.get(5)).setText(player.getRankTier()); // ranktier
            ((ImageView) views.get(6)).setImageBitmap(player.getAvatar()); // avatar

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            LastMatchesFragment lastMatchesFragment = new LastMatchesFragment();
            lastMatchesFragment.setMatches(matches);
            fragmentTransaction.replace(R.id.fragment_container, lastMatchesFragment);
            fragmentTransaction.commit();
        }

        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected Void doInBackground(Void ...params) {

            String dataCleaned = UtilsHttp.getInfoFromAPI("https://api.opendota.com/api/players/" + player.getId());

            try {
                JSONObject data = new JSONObject(dataCleaned);
                player.editProfilePlayer(data);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }

            dataCleaned = UtilsHttp.getInfoFromAPI("https://api.opendota.com/api/players/"+player.getId()+"/recentMatches");

            try {
                JSONArray data = new JSONArray(dataCleaned);
                matches.editLastMatches(data);
                player.setIdLastGame(matches.getMatches().get(0).getMatchId());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private static class TaskHero extends AsyncTask<Void, Void, Void> {
        MostPlayedHeroes mostPlayedHeroes;
        FragmentManager fragmentManager;
        Player player;

        TaskHero(Player p,FragmentManager f,MostPlayedHeroes m){
            player = p;
            fragmentManager = f;
            mostPlayedHeroes = m;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            LastHeroesFragment lastHeroesFragment = new LastHeroesFragment();
            lastHeroesFragment.setHeroes(mostPlayedHeroes);
            fragmentTransaction.replace(R.id.fragment_container,lastHeroesFragment);
            fragmentTransaction.commit();
        }

        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected Void doInBackground(Void ...params) {

            String dataCleaned = UtilsHttp.getInfoFromAPI("https://api.opendota.com/api/players/" + player.getId() + "/heroes");
            try {
                JSONArray data = new JSONArray(dataCleaned);
                mostPlayedHeroes.addNewHeroes(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            dataCleaned = UtilsHttp.getInfoFromAPI("https://api.opendota.com/api/heroStats");
            try {
                JSONArray data = new JSONArray(dataCleaned);
                mostPlayedHeroes.updateHeroes(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }
    }

    private class MonServiceBinder extends Binder implements PlayerProfileInterface {
        public void setPlayer(Player p){
            player = p;
        }
        public void setViews(ArrayList<View> v){
            views = v;
        }
        public void setMatches(Matches m){ matches = m;}
        public void setMostPlayerHeroes(MostPlayedHeroes h){ mostPlayedHeroes = h;}
        public void setFragmentManager(FragmentManager fm){ fragmentManager = fm;}
    }
}
