package com.app.dotastats.dotastats.Beans;

import com.app.dotastats.dotastats.utils.UtilsHttp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class MostPlayedHeroes {

    private ArrayList<MostPlayedHero> mostPlayedHeroes = new ArrayList<>();

    public ArrayList<MostPlayedHero> getMostPlayedHeroes() {
        return mostPlayedHeroes;
    }

    public MostPlayedHeroes(){
    }

    public void addNewHeroes(JSONArray array){
        for (int i=0; i < 10; i++)
        {
            try {
                JSONObject temp = array.getJSONObject(i);

                MostPlayedHero m = new MostPlayedHero();
                m.setIdHero(temp.getInt("hero_id"));
                m.setWins(temp.getInt("win"));
                m.setGames(temp.getInt("games"));
                m.setDefeats(m.getGames() - m.getWins());
                m.setName(" - ");

                mostPlayedHeroes.add(m);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateHeroes(JSONArray array){
        for (int i=0; i < mostPlayedHeroes.size(); i++)
        {
            try {
                MostPlayedHero mostPlayedHero = mostPlayedHeroes.get(i);
                int index = mostPlayedHero.getIdHero()-1;
                if(index > 23)
                    index--; // problem in the API; the JSON sent by the API doesn't contain any hero with id = 24
                             // it probably was removed from the game
                JSONObject temp = array.getJSONObject(index);
                mostPlayedHero.setName(temp.getString("localized_name"));
                mostPlayedHero.setIcon(UtilsHttp.getHeroImage(temp.getString("img")));
                mostPlayedHero.setPrimaryAttribute(temp.getString("primary_attr"));
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }
    }


}
