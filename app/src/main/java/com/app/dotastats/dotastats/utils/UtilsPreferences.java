package com.app.dotastats.dotastats.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.app.dotastats.dotastats.Beans.Player;

import java.util.ArrayList;

// manages the shared preferences where the data about the favorite players is stored
public class UtilsPreferences {

    // add a new player to the list and increases the total number of registered players
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

    // remove the player and handles the other in order not to lose any information
    public static void removePlayerFromListFavorite(Context context,Player player){
        SharedPreferences sharedPref = context.getSharedPreferences("favoritePlayers", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Boolean alreadyRemoved = false;

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
                    alreadyRemoved = true;
                }
                // needs to fill the blank left by the disappearance of the previous player
                else if(alreadyRemoved){
                    editor.putString("FavoritePlayer"+Integer.toString(i)+"Name", players.get(i).getName());
                    editor.putString("FavoritePlayer"+Integer.toString(i)+"id", players.get(i).getId());
                    editor.putString("FavoritePlayer"+Integer.toString(i)+"lastPlayed", players.get(i).getLastPlayed());
                    editor.putString("FavoritePlayer"+Integer.toString(i)+"idLastGame", players.get(i).getIdLastGame());

                    editor.putString("FavoritePlayer"+Integer.toString(i+1)+"Name", null);
                    editor.putString("FavoritePlayer"+Integer.toString(i+1)+"id", null);
                    editor.putString("FavoritePlayer"+Integer.toString(i+1)+"lastPlayed", null);
                    editor.putString("FavoritePlayer"+Integer.toString(i+1)+"idLastGame", null);

                    editor.apply();
                }
            }
        }
    }

    // get all favorite players and returns them as a list
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

    // remove every player from this list
    // used only to debug, bug could be implemented later
    public static void clearFavoritePlayers(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("favoritePlayers", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.clear();
        editor.apply();
    }

    // checks if the player is already registered as a favorite or not
    public static boolean isPlayerAFavorite(Context context, String id){
        ArrayList<Player> players = getAllFavoritePlayers(context);
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getId().equals(id))
                return true;
        }
        return false;
    }

    // returns the number of favorite players
    public static int getNumberFavoritePlayers(Context context){
        return context.getSharedPreferences("favoritePlayers", Context.MODE_PRIVATE).getInt("nbFavoritePlayers",0);
    }

    // gets the stored information about a specific player
    public static Player getSpecificPlayer(Context context, int i){
        return getAllFavoritePlayers(context).get(i);
    }

    // update the stored data when the player finished a game
    public static void updateLastGame(Context context, int index,String id, String lastPlayed){
        SharedPreferences sharedPref = context.getSharedPreferences("favoritePlayers", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        int nb = sharedPref.getInt("nbFavoritePlayers",0);
        editor.putString("FavoritePlayer"+Integer.toString(index+1)+"idLastGame",id);
        editor.putString("FavoritePlayer"+Integer.toString(index+1)+"lastPlayed", lastPlayed);

        editor.apply();
    }

    // gets the filters used for the "hero search"
    // if it is false, the search will return every hero ; if true, it will only return the hero matching what the player wants (carry, support... )
    public static Boolean filtersEnabled(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean("enableFilters",false);
    }

    // return a list of all filters chosen by the players
    // can return an empty list, but it will just display an empty list of heroes to the player
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
