package com.app.dotastats.dotastats.Beans;

import android.graphics.Bitmap;
import android.util.Log;

import com.app.dotastats.dotastats.utils.UtilsHttp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MatchStat {

    private String idMatch;
    private String duration;
    private ArrayList<PlayerStat> players;

    public MatchStat() {}

    public String getIdMatch() { return idMatch; }
    private void setIdMatch(String idMatch) { this.idMatch = idMatch; }

    public String getDuration() { return duration; }
    private void setDuration(String duration) { this.duration = duration; }

    public ArrayList<PlayerStat> getPlayers() { return players; }
    public void setPlayers(ArrayList<PlayerStat> players) { this.players = players; }

    public ArrayList<PlayerStat> getTeam(int teamChoice){
        ArrayList<PlayerStat> team = new ArrayList<>();

        switch (teamChoice){
            case 1:
                for (int i = 0; i < 5; i++){
                    team.add(players.get(i));
                }
                return team;
            case 2:
                for (int i = 5; i < 10; i++){
                    team.add(players.get(i));
                }
                return team;
        }
        return team;
    }

    public void addMatchStat(JSONObject object) throws JSONException{
        Log.i("Buggg", "something 3");

        this.setDuration(object.getString("duration"));
        this.setIdMatch(object.getString("match_id"));

        JSONArray gamePlayers = object.getJSONArray("players");
        ArrayList<PlayerStat> newPlayers = new ArrayList<>();
        Log.i("Buggg", "array length" + gamePlayers.length());

        for (int i = 0; i < gamePlayers.length(); i++){

            try{
                JSONObject temp = gamePlayers.getJSONObject(i);

                PlayerStat newPlayer = new PlayerStat();

                try {
                    newPlayer.setPlayerName(temp.getString("personaname"));
                }catch(JSONException e){
                    newPlayer.setPlayerName("Unknown");
                }
                Log.i("MatchStat Init", "person name : " + newPlayer.getPlayerName());

                newPlayer.setHeroId(temp.getInt("hero_id"));
                newPlayer.setKills(temp.getInt("kills"));
                newPlayer.setDeaths(temp.getInt("deaths"));
                newPlayer.setAssists(temp.getInt("assists"));
                newPlayer.setGpm(temp.getInt("gold_per_min"));
                newPlayer.setXppm(temp.getInt("xp_per_min"));
                newPlayer.setHeroDamage(temp.getInt("hero_damage"));
                newPlayer.setHeroHealing(temp.getInt("hero_healing"));

                newPlayers.add(newPlayer);
            } catch(JSONException e){
                e.printStackTrace();

            }

        }
        Log.i("MatchStat Init", "Players size : " + newPlayers.size());
        this.setPlayers(newPlayers);
    }

    public void addImages(JSONArray array){
        for(int i = 0; i < array.length(); i++){
            try {
                JSONObject temp = array.getJSONObject(i);
                for(int j = 0; j < 10; j++){
                    if(temp.getInt("id") == this.getPlayers().get(j).getHeroId()){
                        String url = temp.getString("img");
                        Bitmap img = UtilsHttp.getHeroImage(url);
                        this.getPlayers().get(j).setImage(img);
                    }
                }
            }
            catch (JSONException|IOException e){
                e.printStackTrace();
            }
        }
    }
}
