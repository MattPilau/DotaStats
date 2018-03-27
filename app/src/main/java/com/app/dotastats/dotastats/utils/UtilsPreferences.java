package com.app.dotastats.dotastats.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.app.dotastats.dotastats.Beans.Player;

import java.util.ArrayList;

public class UtilsPreferences {

    public static void addNewPlayerToListFavorite(Context context,Player player){
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

    public static void removePlayerFromListFavorite(Context context,Player player){
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

    public static ArrayList<Player> getAllFavoritePlayers(Context context){
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

    public static void clearFavoritePlayers(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("favoritePlayers", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.clear();
        editor.apply();
    }

    public static boolean isPlayerAFavorite(Context context, String id){
        ArrayList<Player> players = getAllFavoritePlayers(context);
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getId().equals(id))
                return true;
        }
        return false;
    }

    public static int getNumberFavoritePlayers(Context context){
        return context.getSharedPreferences("favoritePlayers", Context.MODE_PRIVATE).getInt("nbFavoritePlayers",0);
    }

    public static Player getSpecificPlayer(Context context, int i){
        return getAllFavoritePlayers(context).get(i);
    }

    public static void updateLastGame(Context context, int index,String id, String lastPlayed){
        SharedPreferences sharedPref = context.getSharedPreferences("favoritePlayers", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        int nb = sharedPref.getInt("nbFavoritePlayers",0);
        editor.putString("FavoritePlayer"+Integer.toString(index+1)+"idLastGame",id);
        editor.putString("FavoritePlayer"+Integer.toString(index+1)+"lastPlayed", lastPlayed);


        editor.apply();
    }

    public static Boolean filtersEnabled(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean("enableFilters",false);
    }

    public static ArrayList<String> askedFilters(Context context){

        ArrayList<String> roles = new ArrayList<>();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        if(sharedPreferences.getBoolean("carry",true))
            roles.add("Carry");
        if(sharedPreferences.getBoolean("escape",true))
            roles.add("Escape");
        if(sharedPreferences.getBoolean("nuker",true))
            roles.add("Nuker");
        if(sharedPreferences.getBoolean("initiator",true))
            roles.add("Initiator");
        if(sharedPreferences.getBoolean("durable",true))
            roles.add("Durable");
        if(sharedPreferences.getBoolean("disabler",true))
            roles.add("Disabler");
        if(sharedPreferences.getBoolean("jungler",true))
            roles.add("Jungler");
        if(sharedPreferences.getBoolean("support",true))
            roles.add("Support");
        if(sharedPreferences.getBoolean("pusher",true))
            roles.add("Pusher");

        return roles;
    }
}
