package com.app.dotastats.dotastats.Beans;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import com.app.dotastats.dotastats.utils.UtilsHttp;

public class Player {
    private String name;
    private String lastPlayed;
    private Bitmap avatar;
    private String id;
    private String steamLink;
    private String country;
    private String mmr;
    private String rankTier; // 2 numbers : the first one is the league, the second one is the division
    private String idLastGame;

    public String getIdLastGame() {
        return idLastGame;
    }

    public void setIdLastGame(String idLastGame) {
        this.idLastGame = idLastGame;
    }

    public String getSteamLink() {return steamLink;}
    private void setSteamLink(String steamLink) {this.steamLink = steamLink;}

    public String getCountry() {return country;}
    private void setCountry(String country) {this.country = country;}

    public String getMmr() {return mmr;}
    private void setMmr(String mmr) {this.mmr = mmr;}

    public String getRankTier() {return rankTier;}
    private void setRankTier(String rankTier) {this.rankTier = rankTier;}

    public String getId() {return id;}
    public void setId(String id) {
        this.id = id;
    }

    public Bitmap getAvatar() {
        return avatar;
    }
    void setAvatar(Bitmap avatar) {
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

    public void editProfilePlayer(JSONObject object) throws JSONException, IOException {
        JSONObject profile = object.getJSONObject("profile");
        this.setName(profile.getString("personaname"));
        this.setCountry(profile.getString("loccountrycode"));
        this.setSteamLink(profile.getString("profileurl"));
        this.setMmr(object.getJSONObject("mmr_estimate").getString("estimate"));
        this.setRankTier(object.getString("rank_tier"));
        if(rankTier.equals("null"))
            setRankTier("Unranked");
        this.setAvatar(UtilsHttp.getPicture(profile.getString("avatarfull")));
    }
}
