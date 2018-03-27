package com.app.dotastats.dotastats.Beans;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.app.dotastats.dotastats.utils.UtilsHttp;

import java.io.IOException;

public class Hero {

    private int id;
    private String name;

    private String attack_type;

    private Bitmap image;

    private double base_health;
    private double base_mana;

    private double base_armor;
    private double base_mr;
    private double base_str;
    private double base_agi;
    private double base_int;

    private double str_gain;
    private double agi_gain;
    private double int_gain;

    private double attack_range;
    private double attack_rate;
    private double move_speed;

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    private String getAttack_Type() {return attack_type;}
    private void setAttack_Type(String attack_type) {this.attack_type = attack_type;}

    public Bitmap getImage() {return image;}
    public void setImage(Bitmap image) {this.image = image;}

    public double getBase_health() {return base_health;}
    private void setBase_health(double base_health) {this.base_health = base_health;}

    public double getBase_mana() {return base_mana;}
    private void setBase_mana(double base_mana) {this.base_mana = base_mana;}

    public double getBase_armor() {return base_armor;}
    private void setBase_armor(double base_armor) {this.base_armor = base_armor;}

    public double getBase_mr() {return base_mr;}
    private void setBase_mr(double base_mr) {this.base_mr = base_mr;}

    public double getBase_str() {return base_str;}
    private void setBase_str(double base_str) {this.base_str = base_str;}

    public double getBase_agi() {return base_agi;}
    private void setBase_agi(double base_agi) {this.base_agi = base_agi;}

    public double getBase_int() {return base_int;}
    private void setBase_int(double base_int) {this.base_int = base_int;}

    public double getStr_gain() {return str_gain;}
    private void setStr_gain(double str_gain) {this.str_gain = str_gain;}

    public double getAgi_gain() {return agi_gain;}
    private void setAgi_gain(double agi_gain) {this.agi_gain = agi_gain;}

    public double getInt_gain() {return int_gain;}
    private void setInt_gain(double int_gain) {this.int_gain = int_gain;}

    public double getAttack_range() {return attack_range;}
    private void setAttack_range(double attack_range) {this.attack_range = attack_range;}

    public double getAttack_rate() {return attack_rate;}
    private void setAttack_rate(double attack_rate) {this.attack_rate = attack_rate;}

    public double getMove_speed() {return move_speed;}
    private void setMove_speed(double move_speed) {this.move_speed = move_speed;}

    public void displayHero(){
        System.out.println(name);
        System.out.println(Double.toString(base_health));
        System.out.println(Double.toString(base_mana));
    }



    public Boolean addHeroStats(JSONArray array, String desiredName) throws JSONException {

        for(int i = 0; i < array.length(); i++){
            try {
                JSONObject temp = array.getJSONObject(i);
                if (temp.getString("localized_name").equals(desiredName)){


                    System.out.println("CONDITION OK");

                    int id = temp.getInt("id");
                    //newHero.setId(id);
                    this.setId(id);
                    String name = temp.getString("localized_name");
                    //newHero.setName(name);
                    this.setName(name);
                    System.out.println(name);
                    System.out.println(this.getBase_health());

                    String attack_type = temp.getString("attack_type");
                    //newHero.setAttack_Type(attack_type);
                    this.setAttack_Type(attack_type);
                    System.out.println(attack_type);
                    System.out.println(this.getAttack_Type());

                    String url = temp.getString("img");
                    try{
                        Bitmap img = UtilsHttp.getHeroImage(url);
                        this.setImage(img);
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }


                    double base_health = temp.getDouble("base_health");
                    //newHero.setBase_health(base_health);
                    this.setBase_health(base_health);
                    double base_mana = temp.getDouble("base_mana");
                    //newHero.setBase_mana(base_mana);
                    this.setBase_mana(base_mana);

                    double base_armor= temp.getDouble("base_armor");
                    //newHero.setBase_armor(base_armor);
                    this.setBase_armor(base_armor);
                    double base_mr = temp.getDouble("base_mr");
                    //newHero.setBase_mr(base_mr);
                    this.setBase_mr(base_mr);
                    double base_str = temp.getDouble("base_str");
                    //newHero.setBase_str(base_str);
                    this.setBase_str(base_str);
                    double base_agi = temp.getDouble("base_agi");
                    //newHero.setBase_agi(base_agi);
                    this.setBase_agi(base_agi);
                    double base_int = temp.getDouble("base_int");
                    //newHero.setBase_int(base_int);
                    this.setBase_int(base_int);

                    double str_gain = temp.getDouble("str_gain");
                    //newHero.setStr_gain(str_gain);
                    this.setStr_gain(str_gain);
                    double agi_gain = temp.getDouble("agi_gain");
                    //newHero.setAgi_gain(agi_gain);
                    this.setAgi_gain(agi_gain);
                    double int_gain = temp.getDouble("int_gain");
                    //newHero.setInt_gain(int_gain);
                    this.setInt_gain(int_gain);

                    double attack_range = temp.getDouble("attack_range");
                    //newHero.setAttack_range(attack_range);
                    this.setAttack_range(attack_range);
                    double attack_rate = temp.getDouble("attack_rate");
                    //newHero.setAttack_rate(attack_rate);
                    this.setAttack_rate(attack_rate);
                    double move_speed = temp.getDouble("move_speed");
                    //newHero.setMove_speed(move_speed);
                    this.setMove_speed(move_speed);

                    i = array.length();

                    return true;
                }
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }



}
