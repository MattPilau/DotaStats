package com.app.dotastats.dotastats.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.app.dotastats.dotastats.Player;

import java.util.ArrayList;

/**
 * Created by Matt on 08/03/2018.
 */

public class UtilsPreferences {

    public void addNewPlayerToListFavorite(Context context,Player player){
        SharedPreferences sharedPref = context.getSharedPreferences("favoritePlayers", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        int nb = sharedPref.getInt("nbFavoritePlayers",0);
        editor.putString("FavoritePlayer"+Integer.toString(nb+1)+"Name", player.getName());
        editor.putString("FavoritePlayer"+Integer.toString(nb+1)+"id", player.getId());
        editor.putString("FavoritePlayer"+Integer.toString(nb+1)+"lastPlayed", player.getLastPlayed());
        editor.putString("FavoritePlayer"+Integer.toString(nb+1)+"idLastGame", player.getIdLastGame());
        editor.putInt("nbFavoritePlayers",nb+1);

        editor.apply();
    }

    public void removePlayerFromListFavorite(Context context,Player player){
        SharedPreferences sharedPref = context.getSharedPreferences("favoritePlayers", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        ArrayList<Player> players = getAllFavoritePlayers(context);
        if(players.size() > 0){
            for(int i = 0 ; i < players.size(); i++){
                if(player.getId().equals(players.get(i).getId())){
                    editor.putString("FavoritePlayer"+Integer.toString(i+1)+"Name", null);
                    editor.putString("FavoritePlayer"+Integer.toString(i+1)+"id", null);
                    editor.putString("FavoritePlayer"+Integer.toString(i+1)+"lastPlayed", null);
                    editor.putString("FavoritePlayer"+Integer.toString(i+1)+"idLastGame", null);
                    editor.putInt("nbFavoritePlayers",players.size()-1);

                    editor.apply();
                    break;
                }
            }
        }
    }

    public ArrayList<Player> getAllFavoritePlayers(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("favoritePlayers", Context.MODE_PRIVATE);

        int nb = sharedPref.getInt("nbFavoritePlayers",0);
        ArrayList<Player> players = new ArrayList<>();

        for(int i = 1;i <= nb;i++){
            Player p = new Player();
            p.setName(sharedPref.getString("FavoritePlayer"+Integer.toString(i)+"Name",""));
            p.setId(sharedPref.getString("FavoritePlayer"+Integer.toString(i)+"id",""));
            p.setIdLastGame(sharedPref.getString("FavoritePlayer"+Integer.toString(i)+"idLastGame",""));
            p.setLastPlayed(sharedPref.getString("FavoritePlayer"+Integer.toString(i)+"lastPlayed",""));
            players.add(p);
        }
        return players;
    }

    public void clearFavoritePlayers(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("favoritePlayers", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.clear();
        editor.apply();
    }

    public boolean isPlayerAFavorite(Context context, String id){
        ArrayList<Player> players = getAllFavoritePlayers(context);
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getId().equals(id))
                return true;
        }
        return false;
    }
}
