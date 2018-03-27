package com.app.dotastats.dotastats.Beans;

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

    void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public Boolean getWin() {
        return win;
    }

    void setWin(Boolean win) {
        this.win = win;
    }

    public String getDuration() {
        return duration;
    }

    void setDuration(String duration) {
        this.duration = duration;
    }

    public int getHeroId() {
        return heroId;
    }

    void setHeroId(int heroId) {
        this.heroId = heroId;
    }

    public int getKills() {
        return kills;
    }

    void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getAssists() {
        return assists;
    }

    void setAssists(int assists) {
        this.assists = assists;
    }

    public int getXpMin() {
        return xpMin;
    }

    void setXpMin(int xpMin) {
        this.xpMin = xpMin;
    }

    public int getGoldMin() {
        return goldMin;
    }

    void setGoldMin(int goldMin) {
        this.goldMin = goldMin;
    }
}
