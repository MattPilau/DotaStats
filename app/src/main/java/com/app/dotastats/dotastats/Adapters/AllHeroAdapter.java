package com.app.dotastats.dotastats.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.dotastats.dotastats.Beans.Hero;
import com.app.dotastats.dotastats.R;

import java.util.List;



public class AllHeroAdapter extends ArrayAdapter<Hero>{

    public AllHeroAdapter(Context context, List<Hero> heroes) {
        super(context, 0, heroes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_heroes,parent, false);
        }

        ImageHolder imageHolder = (ImageHolder) convertView.getTag();
        if(imageHolder == null){
            imageHolder = new ImageHolder();
            imageHolder.Name = (TextView) convertView.findViewById(R.id.Name);
            imageHolder.Image = (ImageView) convertView.findViewById(R.id.Image);
            convertView.setTag(imageHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Hero hero = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        imageHolder.Name.setText(hero.getName());
        imageHolder.Image.setImageBitmap(hero.getImage());

        return convertView;
    }

    private class ImageHolder {
        public TextView Name;
        public ImageView Image;
    }
}