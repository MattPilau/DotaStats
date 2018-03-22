package com.app.dotastats.dotastats;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
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

                                BackgroundRequestTask backgroundRequestTask = new BackgroundRequestTask(i);
                                backgroundRequestTask.execute();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 60000);

        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        doAsynchronousTask.cancel();
    }

    private class BackgroundRequestTask extends AsyncTask<Void, Void, Void> {

        int i;
        String id,idLastGame;
        Boolean newGame = false;

        private BackgroundRequestTask(int player){
            this.i = player;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if(newGame){
                sendNotification();
                Toast.makeText(getBaseContext(), idLastGame + " " + id + " -- " + i, Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(getBaseContext(), "Pas de nouvelles parties ! "+ " " + id + " -- " + i, Toast.LENGTH_SHORT).show();
        }

        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected Void doInBackground(Void ...params) {

            Player p = UtilsPreferences.getSpecificPlayer(getBaseContext(),i);
            id = p.getId();
            idLastGame = p.getIdLastGame();

            String data = UtilsHttp.getInfoFromAPI("https://api.opendota.com/api/players/" + id + "/recentMatches");
            try {
                JSONArray array = new JSONArray(data);

                String lastGame = array.getJSONObject(0).getString("match_id");
                if(!lastGame.equals(idLastGame)){
                    newGame = true;
                    UtilsPreferences.updateLastGame(getBaseContext(),i,idLastGame);
                }
                idLastGame = lastGame;

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        void sendNotification(){
            Intent intent = new Intent(getApplicationContext(), PlayerProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("playerIndex",i);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getBaseContext(),"DotaStats")
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle("New Game ! ")
                    .setContentText("The player " + i + " just finished a new game ! ")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setDefaults(DEFAULT_VIBRATE);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

            notificationManager.notify(i, mBuilder.build());
        }

    }
}
