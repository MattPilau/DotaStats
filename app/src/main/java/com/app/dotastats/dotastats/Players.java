package com.app.dotastats.dotastats;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Mamie_Chiffons77 on 04/03/2018.
 */

public class Players {

    private ArrayList<Player> players;

    public Players(){
        players = new ArrayList<Player>();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    // add each received players in the list
    public void addIntoList(String name,String lastPlayed,Bitmap avatar){
        Player p = new Player();
        p.setName(name);
        p.setLastPlayed(lastPlayed);
        p.setAvatar(avatar);
        players.add(p);
    }

    // displays some information about the received players
    public void display(){
        for(int i = 0; i < players.size();i++){
            System.out.println(players.get(i).getName() + " - " + players.get(i).getLastPlayed());
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
                String s = "";
                for(int j = 0; j < lastPlayed.length();j++){
                    if(j < 10 || (j > 10 && j < 19)) {
                        s += lastPlayed.charAt(j);
                    }
                    else if(j == 10)
                        s += "  ";
                }
                lastPlayed = s;

                String url = temp.getString("avatarfull");
                URL url2 = new URL(url);
                HttpURLConnection connection  = (HttpURLConnection) url2.openConnection();
                InputStream is = connection.getInputStream();
                Bitmap img = BitmapFactory.decodeStream(is);

                addIntoList(name,lastPlayed,img);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
