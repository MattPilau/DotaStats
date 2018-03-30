package com.app.dotastats.dotastats.Beans;

import android.graphics.Bitmap;

public class PlayerStat {

    private String playerName;

    private int heroId;

    private int kills;
    private int deaths;
    private int assists;

    private int gpm;
    private int xppm;

    private int heroDamage;
    private int heroHealing;

    private Bitmap image;

    PlayerStat() {}

    public PlayerStat(PlayerStat player){

        this.playerName = player.getPlayerName();
        this.heroId = player.getHeroId();
        this.kills = player.getKills();
        this.deaths = player.getDeaths();
        this.assists = player.getAssists();
        this.gpm = player.getGpm();
        this.xppm = player.getXppm();
        this.heroDamage = player.getHeroDamage();
        this.heroHealing = player.getHeroHealing();
        this.image = player.getImage();
    }

    public void setImage(Bitmap image) { this.image = image; }
    public Bitmap getImage() {return  image; }

    void setPlayerName(String name) { this.playerName = name; }
    public String getPlayerName() { return playerName; }

    void setHeroId(int heroId) { this.heroId = heroId; }
    int getHeroId() {return heroId; }

    void setKills(int kills) { this.kills = kills; }
    public int getKills() {return kills; }

    void setDeaths(int deaths) { this.deaths = deaths; }
    public int getDeaths() {return deaths; }

    void setAssists(int assists) { this.assists = assists; }
    public int getAssists() {return assists; }

    void setGpm(int gpm) { this.gpm = gpm; }
    public int getGpm() {return gpm; }

    void setXppm(int xppm) { this.xppm = xppm; }
    public int getXppm() {return xppm; }

    void setHeroDamage(int heroDamage) { this.heroDamage = heroDamage; }
    public int getHeroDamage() {return heroDamage; }

    void setHeroHealing(int heroHealing) { this.heroHealing = heroHealing; }
    public int getHeroHealing() {return heroHealing; }
}
