package com.app.dotastats.dotastats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Matt on 04/03/2018.
 */

public class PlayerAdapter extends ArrayAdapter<Player>{

    public PlayerAdapter(Context context, List<Player> players) {
        super(context, 0, players);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_players,parent, false);
        }

        ImageHolder imageHolder = (ImageHolder) convertView.getTag();
        if(imageHolder == null){
            imageHolder = new ImageHolder();
            imageHolder.name = (TextView) convertView.findViewById(R.id.namePlayer);
            imageHolder.lastPlayed = (TextView) convertView.findViewById(R.id.lastPlayed);
            imageHolder.image = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(imageHolder);
        }

        Player player = getItem(position);

        imageHolder.name.setText(player.getName());
        imageHolder.lastPlayed.setText(player.getLastPlayed());
        imageHolder.image.setImageBitmap(player.getAvatar());

        return convertView;
    }

    private class ImageHolder {
        public TextView name;
        public TextView lastPlayed;
        public ImageView image;
    }
}
