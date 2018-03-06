package com.app.dotastats.dotastats;

import android.graphics.Bitmap;

/**
 * Created by Mamie_Chiffons77 on 04/03/2018.
 */

public class Player {
    private String name;
    private String lastPlayed;
    private Bitmap avatar;
    private String id;
    private String steamLink;
    private String country;
    private String mmr;
    private String rankTier; // 2 numbers : the first one is the league, the second one is the division

    public String getSteamLink() {return steamLink;}
    public void setSteamLink(String steamLink) {this.steamLink = steamLink;}

    public String getCountry() {return country;}
    public void setCountry(String country) {this.country = country;}

    public String getMmr() {return mmr;}
    public void setMmr(String mmr) {this.mmr = mmr;}

    public String getRankTier() {return rankTier;}
    public void setRankTier(String rankTier) {this.rankTier = rankTier;}

    public String getId() {return id;}
    public void setId(String id) {
        this.id = id;
    }

    public Bitmap getAvatar() {
        return avatar;
    }
    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    public String getLastPlayed() {
        return lastPlayed;
    }
    public void setLastPlayed(String lastPlayed) {
        this.lastPlayed = lastPlayed;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
