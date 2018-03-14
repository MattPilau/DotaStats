package com.app.dotastats.dotastats;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alexis on 10/03/2018.
 */

public class Heroes {

    private Hero hero;

    public Heroes() {hero = new Hero();}

    public Hero getHeroes() {
        return hero;
    }

    public void setHeroes(Hero heroes) {
        this.hero = heroes;
    }

    public Hero addHeroStats(JSONArray array, String desiredName) throws JSONException {
        Hero newHero = new Hero();
        for(int i = 0; i < array.length(); i++){
            try {
                JSONObject temp = array.getJSONObject(i);
                if (temp.getString("localized_name") == desiredName){

                    int id = temp.getInt("id");
                    newHero.setId(id);
                    String name = temp.getString("localized_name");
                    newHero.setName(name);

                    String attack_type = temp.getString("attack_type");
                    newHero.setAttack_Type(attack_type);

                    //String url = temp.getString("img");
                    //Bitmap img = new UtilsHttp().getPicture(url);

                    double base_health = temp.getDouble("base_health");
                    newHero.setBase_health(base_health);
                    double base_mana = temp.getDouble("base_mana");
                    newHero.setBase_mana(base_mana);

                    double base_armor= temp.getDouble("base_armor");
                    newHero.setBase_armor(base_armor);
                    double base_mr = temp.getDouble("base_mr");
                    newHero.setBase_mr(base_mr);
                    double base_str = temp.getDouble("base_str");
                    newHero.setBase_str(base_str);
                    double base_agi = temp.getDouble("base_agi");
                    newHero.setBase_agi(base_agi);
                    double base_int = temp.getDouble("base_int");
                    newHero.setBase_int(base_int);

                    double str_gain = temp.getDouble("str_gain");
                    newHero.setStr_gain(str_gain);
                    double agi_gain = temp.getDouble("agi_gain");
                    newHero.setAgi_gain(agi_gain);
                    double int_gain = temp.getDouble("int_gain");
                    newHero.setInt_gain(int_gain);

                    double attack_range = temp.getDouble("attack_range");
                    newHero.setAttack_range(attack_range);
                    double attack_rate = temp.getDouble("attack_rate");
                    newHero.setAttack_rate(attack_rate);
                    double move_speed = temp.getDouble("move_speed");
                    newHero.setMove_speed(move_speed);

                    i = array.length();
                }
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }
        return newHero;
    }
}
