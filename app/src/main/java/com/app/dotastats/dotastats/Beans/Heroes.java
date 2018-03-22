package com.app.dotastats.dotastats.Beans;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

import java.util.ArrayList;
import android.graphics.Bitmap;

import com.app.dotastats.dotastats.Beans.Hero;
import com.app.dotastats.dotastats.utils.UtilsHttp;



/**
 * Created by Alexis on 10/03/2018.
 */

public class Heroes {

    private ArrayList<Hero> heroes;

    public Heroes() {heroes = new ArrayList<Hero>();}

    public ArrayList<Hero> getHeroes() {return heroes;}


    public void setHeroes(ArrayList<Hero> heroes) {
        this.heroes = heroes;
    }


    public  void addIntoList(String name, Bitmap image){
        Hero hero = new Hero();

        hero.setName(name);
        hero.setImage(image);

        heroes.add(hero);
    }
}
