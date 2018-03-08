package com.app.dotastats.dotastats;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;

import comp.app.dotastats.utils.UtilsHttp;

public class PlayerProfileService extends Service {

    TaskProfile myTask;
    TimerTask task;
    Player player;
    ArrayList<View> views;

    private final IBinder binder = new MonServiceBinder();

    public PlayerProfileService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        final Handler handler = new Handler();

        // displays 'plop'
        task = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        Toast.makeText(getBaseContext(), "Request made !" + player.getId(), Toast.LENGTH_SHORT).show();
                        myTask = new TaskProfile(player,views);
                        myTask.execute();
                    } });
            }};
        task.run();
        return START_STICKY;
    }

    public void onDestroy() { // Destruction du service
        Toast.makeText(getBaseContext(), "DESTRUCTION", Toast.LENGTH_SHORT).show();
        task.cancel();
    }

    private static class TaskProfile extends AsyncTask<Void, Void, Void> {
        Player player;
        ArrayList<View> views;

        TaskProfile(Player p,ArrayList<View> v){
            player = p;
            views = v;
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
        }

        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected Void doInBackground(Void ...params) {

            String dataCleaned = new UtilsHttp().getInfoFromAPI("https://api.opendota.com/api/players/" + player.getId());

            try {
                JSONObject data = new JSONObject(dataCleaned);
                player.editProfilePlayer(data);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class MonServiceBinder extends Binder implements PlayerProfileInterface  {

        public void setPlayer(Player p){
            player = p;
        }
        public void setViews(ArrayList<View> v){
            views = v;
        }
    }
}
