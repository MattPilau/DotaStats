package com.app.dotastats.dotastats;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Alexis on 28/03/2018.
 */

public class TeamAdapter extends ArrayAdapter<PlayerStat> {
    public TeamAdapter(Context context, List<PlayerStat> team){
        super(context, 0, team);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.team,parent, false);
        }

        TeamHolder teamHolder = (TeamHolder) convertView.getTag();
        if(teamHolder == null){
            teamHolder = new TeamHolder();
            teamHolder.image = (ImageView) convertView.findViewById(R.id.playerImage);
            teamHolder.playerName = (TextView) convertView.findViewById(R.id.playerName);
            convertView.setTag(teamHolder);
        }

        PlayerStat player = getItem(position);

        teamHolder.playerName.setText(player.getPlayerName());
        teamHolder.image.setImageBitmap(player.getImage());

        return convertView;
    }

    private class TeamHolder {
        public ImageView image;
        public TextView playerName;
    }
}
