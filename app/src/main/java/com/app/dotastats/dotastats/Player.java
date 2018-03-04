package com.app.dotastats.dotastats;

import android.graphics.Bitmap;

/**
 * Created by Mamie_Chiffons77 on 04/03/2018.
 */

public class Player {
    private String name;
    private String lastPlayed;
    private Bitmap avatar;

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
