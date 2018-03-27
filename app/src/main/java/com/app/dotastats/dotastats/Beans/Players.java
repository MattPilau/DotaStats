package com.app.dotastats.dotastats.Beans;

import android.graphics.Bitmap;

import com.app.dotastats.dotastats.utils.UtilsHttp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class Players {

    private ArrayList<Player> players;

    public Players(){
        players = new ArrayList<>();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    // add each received players in the list
    private void addIntoList(String name, String lastPlayed, Bitmap avatar, String id){
        Player p = new Player();
        p.setName(name);
        p.setLastPlayed(lastPlayed);
        p.setAvatar(avatar);
        p.setId(id);
        players.add(p);
    }

    // displays some information about the received players
    public void display(){
        for(int i = 0; i < players.size();i++){
            System.out.println(players.get(i).getName() + " - " + players.get(i).getLastPlayed() + " - " + players.get(i).getId());
        }
    }

    // parse the JSON Array and add each player in the list
    public void addAllValues(JSONArray array) throws JSONException {
        for (int i=0; i < array.length(); i++)
        {
            if(i > 24)
                break;
            try {
                JSONObject temp = array.getJSONObject(i);
                String name = temp.getString("personaname");
                String lastPlayed = temp.getString("last_match_time");
                StringBuilder s = new StringBuilder();
                for(int j = 0; j < lastPlayed.length();j++){
                    if(j < 10 || (j > 10 && j < 19)) {
                        s.append(lastPlayed.charAt(j));
                    }
                    else if(j == 10)
                        s.append("  ");
                }
                lastPlayed = s.toString();
                String id = temp.getString("account_id");

                String url = temp.getString("avatarfull");
                Bitmap img = UtilsHttp.getPicture(url);


                addIntoList(name,lastPlayed,img,id);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
