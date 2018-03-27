package com.app.dotastats.dotastats.Beans;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Heroes {

    private ArrayList<Hero> heroes;

    public Heroes() {heroes = new ArrayList<>();}

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
