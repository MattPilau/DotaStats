package com.app.dotastats.dotastats.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.dotastats.dotastats.Beans.MostPlayedHero;
import com.app.dotastats.dotastats.R;

import java.util.List;

public class MostPlayedHeroesAdapter extends ArrayAdapter<MostPlayedHero>{

    public MostPlayedHeroesAdapter(Context context, List<MostPlayedHero> mostPlayedHeroes) {
        super(context, 0, mostPlayedHeroes);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_most_played_heroes,parent, false);
        }

        HeroHolder heroHolder = (HeroHolder) convertView.getTag();
        if(heroHolder == null){
            heroHolder = new HeroHolder();
            heroHolder.attribute = convertView.findViewById(R.id.attribute);
            heroHolder.nameHero = convertView.findViewById(R.id.nameHero);
            heroHolder.wins = convertView.findViewById(R.id.wins);
            heroHolder.defeats = convertView.findViewById(R.id.defeats);
            heroHolder.ratio = convertView.findViewById(R.id.ratio);
            heroHolder.games = convertView.findViewById(R.id.games);
            heroHolder.icon = convertView.findViewById(R.id.iconHero);
            convertView.setTag(heroHolder);
        }

        MostPlayedHero mostPlayedHero = getItem(position);
        if (mostPlayedHero != null) {
            heroHolder.attribute.setText(mostPlayedHero.getPrimaryAttribute());
        }
        if (mostPlayedHero != null) {
            heroHolder.nameHero.setText(mostPlayedHero.getName());
        }
        if (mostPlayedHero != null) {
            heroHolder.wins.setText(String.format("%s wins", Integer.toString(mostPlayedHero.getWins())));
        }
        if (mostPlayedHero != null) {
            heroHolder.defeats.setText(Integer.toString(mostPlayedHero.getDefeats()) + " defeats");
        }
        if (mostPlayedHero != null) {
            heroHolder.ratio.setText(Double.toString(mostPlayedHero.getRatio()) + "%");
        }
        if (mostPlayedHero != null) {
            heroHolder.games.setText(Integer.toString(mostPlayedHero.getGames()));
        }
        if (mostPlayedHero != null) {
            heroHolder.icon.setImageBitmap(mostPlayedHero.getIcon());
        }

        if (mostPlayedHero != null) {
            if(mostPlayedHero.getRatio() > 50.0)
                heroHolder.ratio.setTextColor(Color.parseColor("#64d176"));
            else
                heroHolder.ratio.setTextColor(Color.parseColor("#b20c0c"));
        }

        if (mostPlayedHero != null) {
            switch (mostPlayedHero.getPrimaryAttribute()) {
                case "str":
                    heroHolder.attribute.setTextColor(Color.parseColor("#960f0f"));
                    break;
                case "agi":
                    heroHolder.attribute.setTextColor(Color.parseColor("#218436"));
                    break;
                default:
                    heroHolder.attribute.setTextColor(Color.parseColor("#493bcc"));
                    break;
            }
        }

        return convertView;
    }

    private class HeroHolder {
        private TextView nameHero;
        private TextView attribute;
        private TextView games;
        private TextView wins;
        private TextView defeats;
        private TextView ratio;
        private ImageView icon;
    }
}
