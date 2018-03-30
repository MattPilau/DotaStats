package com.app.dotastats.dotastats.Adapters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.dotastats.dotastats.Beans.PlayerStat;
import com.app.dotastats.dotastats.R;

import java.util.List;

public class TeamAdapter extends ArrayAdapter<PlayerStat> {
    public TeamAdapter(Context context, List<PlayerStat> team){
        super(context, 0, team);
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.team,parent, false);
        }

        TeamHolder teamHolder = (TeamHolder) convertView.getTag();
        if(teamHolder == null){
            teamHolder = new TeamHolder();
            teamHolder.image = convertView.findViewById(R.id.playerImage);
            teamHolder.playerName = convertView.findViewById(R.id.playerName);
            convertView.setTag(teamHolder);
        }

        PlayerStat player = getItem(position);

        teamHolder.playerName.setText(player.getPlayerName());
        teamHolder.image.setImageBitmap(player.getImage());

        return convertView;
    }

    private class TeamHolder {
        public ImageView image;
        TextView playerName;
    }
}
