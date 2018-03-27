package com.app.dotastats.dotastats.Beans;

import android.graphics.Bitmap;

public class MostPlayedHero{
    private int idHero;
    private String primaryAttribute;
    private int games;
    private int wins;
    private int defeats;
    private String Name;
    private Bitmap icon;

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getRatio(){
        double x = ((double) wins / (double) games)* 10000;
        int d = (int) x;
        if((x-d) >= 0.5)
            d++;
        return d/100.0;
    }

    public int getGames() {
        return games;
    }

    void setGames(int games) {
        this.games = games;
    }

    public int getWins() {
        return wins;
    }

    void setWins(int wins) {
        this.wins = wins;
    }

    public int getDefeats() {
        return defeats;
    }

    void setDefeats(int defeats) {
        this.defeats = defeats;
    }

    int getIdHero() {
        return idHero;
    }

    void setIdHero(int idHero) {
        this.idHero = idHero;
    }

    public String getPrimaryAttribute() {
        return primaryAttribute;
    }

    void setPrimaryAttribute(String primaryAttribute) {
        this.primaryAttribute = primaryAttribute;
    }
}