package com.app.dotastats.dotastats;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import com.app.dotastats.dotastats.Activity.PlayerProfileActivity;
import com.app.dotastats.dotastats.Beans.Player;
import com.app.dotastats.dotastats.utils.UtilsHttp;
import com.app.dotastats.dotastats.utils.UtilsPreferences;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static android.app.Notification.DEFAULT_VIBRATE;

public class FavoritePlayerLastGameService extends Service {

    TimerTask doAsynchronousTask;
    int i = 0;

    public FavoritePlayerLastGameService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        final Handler handler = new Handler();
        Timer timer = new Timer();
        doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            int nbPlayers = UtilsPreferences.getNumberFavoritePlayers(getBaseContext());
                            if(nbPlayers > 0){
                                i = (i+1)%nbPlayers;

                                BackgroundRequestTask backgroundRequestTask = new BackgroundRequestTask();
                                backgroundRequestTask.execute();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 20000);

        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        doAsynchronousTask.cancel();
    }

    @SuppressLint("StaticFieldLeak")
    private class BackgroundRequestTask extends AsyncTask<Void, Void, Void> {

        String id,idLastGame,name;
        Boolean newGame = false,internetError = false;

        private BackgroundRequestTask(){
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            Toast.makeText(getBaseContext(), id, Toast.LENGTH_SHORT).show();

            if(newGame)
                sendNotification();

            if(internetError)
                Toast.makeText(getBaseContext(), "No internet ! ", Toast.LENGTH_SHORT).show();
        }

        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected Void doInBackground(Void ...params) {

            NetworkInfo info = ((ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if(info == null){
                internetError = true;
                return null;
            }

            Player p = UtilsPreferences.getSpecificPlayer(getBaseContext(),i);
            id = p.getId();
            idLastGame = p.getIdLastGame();
            name = p.getName();

            String data = UtilsHttp.getInfoFromAPI("https://api.opendota.com/api/players/" + id + "/recentMatches");

            try {
                JSONArray array = new JSONArray(data);

                String lastGame = array.getJSONObject(0).getString("match_id");

                if(!lastGame.equals(idLastGame)){
                    newGame = true;

                    long unixSeconds = array.getJSONObject(0).getLong("start_time");
                    Date date = new java.util.Date(unixSeconds*1000L);
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                    String lastPlayed = sdf.format(date);

                    UtilsPreferences.updateLastGame(getBaseContext(),i,lastGame,lastPlayed);
                }
                idLastGame = lastGame;

            } catch (JSONException | NullPointerException e) {
                e.printStackTrace();
            }

            return null;
        }

        void sendNotification(){
            Intent intent = new Intent(getApplicationContext(), PlayerProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("playerIndex",i);

            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getBaseContext(),"DotaStats")
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle("New Game ! ")
                    .setContentText(name  + " just finished a new game ! ")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setDefaults(DEFAULT_VIBRATE)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

            notificationManager.notify(i, mBuilder.build());
        }

    }
}
