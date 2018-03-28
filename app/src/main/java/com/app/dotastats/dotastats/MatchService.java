package com.app.dotastats.dotastats;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.os.Handler;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

import com.app.dotastats.dotastats.Interfaces.SearchHeroInterface;
import com.app.dotastats.dotastats.Interfaces.SearchMatchInterface;
import com.app.dotastats.dotastats.utils.UtilsHttp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.TimerTask;

public class MatchService extends Service {

    private final IBinder binder = new MonServiceBinder();

    private TimerTask timerTask;
    private TaskMatch task;
    private String idMatch;
    private MatchStat match;
    private ListView team1;
    private ListView team2;
    private FragmentManager fragmentManager;


    public MatchService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public int onStartCommand(Intent intent, int flags, int startId){

        final Handler handler = new Handler();

        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        Toast.makeText(getBaseContext(), "Request made !", Toast.LENGTH_SHORT).show();
                        task  = new TaskMatch(idMatch, fragmentManager, match);
                        task.execute();
                    } });
            }};
        timerTask.run();
        return START_STICKY;
    }

    public void onDestroy() { // Destruction du service
        Toast.makeText(getBaseContext(), "DESTRUCTION", Toast.LENGTH_SHORT).show();
        //task.cancel();
    }
    private class TaskMatch extends AsyncTask<Void, Void, Void> {

        String idMatch;
        MatchStat match;
        FragmentManager fragmentManager;

        TaskMatch(String s,FragmentManager manager, MatchStat ms){
            idMatch = s;
            match = ms;
            fragmentManager = manager;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            TeamAdapter adapter1 = new TeamAdapter(team1.getContext(), match.getTeam(1));
            team1.setAdapter(adapter1);

            TeamAdapter adapter2 = new TeamAdapter(team1.getContext(), match.getTeam(2));
            team2.setAdapter(adapter2);

            team1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> listView, View itemView, int itemPosition, long itemId)
                {
                    goToPlayerInMatchFragment(itemPosition);
                }
            });

            team2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> listView, View itemView, int itemPosition, long itemId)
                {
                    goToPlayerInMatchFragment(itemPosition + 5);
                }
            });
        }


        protected void onProgressUpdate(Void... values) { }



        @Override
        protected Void doInBackground(Void ...params) {

            String dataCleaned = new UtilsHttp().getInfoFromAPI("https://api.opendota.com/api/matches/" + idMatch);
            Log.i("Buggg", "something");

            try {
                JSONObject data = new JSONObject(dataCleaned);
                match.addMatchStat(data);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            dataCleaned = new UtilsHttp().getInfoFromAPI("https://api.opendota.com/api/heroStats");
            try {
                JSONArray data = new JSONArray(dataCleaned);
                match.addImages(data);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class MonServiceBinder extends Binder implements SearchMatchInterface {

        public void setIdMatch(String s) {
            idMatch = s;
        }

        public void setFragmentManager(FragmentManager fm) {
            fragmentManager = fm;
        }

        public void setListView1(ListView l) {
            team1 = l;
        }

        public void setListView2(ListView l) {
            team2 = l;
        }

        public void setMatch(MatchStat ms){
            match = ms;
        }

    }

    private void goToPlayerInMatchFragment(int index){

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PlayerInMatchFragment playerInMatchFragment = new PlayerInMatchFragment();
        playerInMatchFragment.setPlayer(match.getPlayers().get(index));
        fragmentTransaction.replace(R.id.fragment_container, playerInMatchFragment);
        fragmentTransaction.commit();
    }
}
