package com.app.dotastats.dotastats.Beans;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Matches {
    private ArrayList<Match> matches;

    public Matches(){
        matches = new ArrayList<>();
    }

    public ArrayList<Match> getMatches() {
        return matches;
    }

    public Match getMatch(int position) {return matches.get(position);}

    public void setMatches(ArrayList<Match> matches) {
        this.matches = matches;
    }

    public void editLastMatches(JSONArray array){

        for (int i=0; i < array.length(); i++)
        {
            // limit to 15 games in the list
            if(i > 14)
                break;
            try {
                JSONObject temp = array.getJSONObject(i);
                Match m = new Match();

                m.setMatchId(temp.getString("match_id"));

                int duration = temp.getInt("duration");
                String duration2 = Integer.toString(duration/60) + " : ";
                if((duration%60) < 10)
                    duration2 += "0";
                duration2 +=  Integer.toString(duration%60);
                m.setDuration(duration2);
                m.setHeroId(temp.getInt("hero_id"));

                m.setKills(temp.getInt("kills"));
                m.setAssists(temp.getInt("assists"));
                m.setDeaths(temp.getInt("deaths"));
                m.setGoldMin(temp.getInt("gold_per_min"));
                m.setXpMin(temp.getInt("xp_per_min"));

                if((temp.getInt("player_slot") > 127 && !temp.getBoolean("radiant_win"))
                        || (temp.getInt("player_slot") < 127 && temp.getBoolean("radiant_win")))
                    m.setWin(true);
                else
                    m.setWin(false);

                matches.add(m);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        display();
    }

    private void display(){
        for(int i = 0; i < matches.size(); i++){
            Log.i("test",matches.get(i).getMatchId() + " - " + matches.get(i).getWin());
        }
    }
}
