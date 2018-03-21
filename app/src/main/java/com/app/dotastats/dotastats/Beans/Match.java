package com.app.dotastats.dotastats.Beans;

/**
 * Created by Matt on 14/03/2018.
 */

public class Match {
    private String matchId;
    private Boolean win;
    private String duration;
    private int heroId;
    private int kills;
    private int deaths;
    private int assists;
    private int xpMin;
    private int goldMin;

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public Boolean getWin() {
        return win;
    }

    public void setWin(Boolean win) {
        this.win = win;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getHeroId() {
        return heroId;
    }

    public void setHeroId(int heroId) {
        this.heroId = heroId;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getXpMin() {
        return xpMin;
    }

    public void setXpMin(int xpMin) {
        this.xpMin = xpMin;
    }

    public int getGoldMin() {
        return goldMin;
    }

    public void setGoldMin(int goldMin) {
        this.goldMin = goldMin;
    }
}
