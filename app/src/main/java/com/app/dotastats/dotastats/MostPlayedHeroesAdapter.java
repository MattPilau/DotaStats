package com.app.dotastats.dotastats;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Matt on 15/03/2018.
 */

public class MostPlayedHeroesAdapter extends ArrayAdapter<MostPlayedHero>{

    MostPlayedHeroesAdapter(Context context, List<MostPlayedHero> mostPlayedHeroes) {
        super(context, 0, mostPlayedHeroes);
    }

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
        heroHolder.attribute.setText(mostPlayedHero.getPrimaryAttribute());
        heroHolder.nameHero.setText(mostPlayedHero.getName());
        heroHolder.wins.setText(Integer.toString(mostPlayedHero.getWins()) + " wins");
        heroHolder.defeats.setText(Integer.toString(mostPlayedHero.getDefeats()) + " defeats");
        heroHolder.ratio.setText(Double.toString(mostPlayedHero.getRatio()) + "%");
        heroHolder.games.setText(Integer.toString(mostPlayedHero.getGames()));
        heroHolder.icon.setImageBitmap(mostPlayedHero.getIcon());

        if(mostPlayedHero.getRatio() > 50.0)
            heroHolder.ratio.setTextColor(Color.parseColor("#64d176"));
        else
            heroHolder.ratio.setTextColor(Color.parseColor("#b20c0c"));

        if(mostPlayedHero.getPrimaryAttribute().equals("str"))
            heroHolder.attribute.setTextColor(Color.parseColor("#960f0f"));
        else if (mostPlayedHero.getPrimaryAttribute().equals("agi"))
            heroHolder.attribute.setTextColor(Color.parseColor("#218436"));
        else
            heroHolder.attribute.setTextColor(Color.parseColor("#493bcc"));

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
