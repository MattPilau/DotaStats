package com.app.dotastats.dotastats.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.dotastats.dotastats.Beans.Match;
import com.app.dotastats.dotastats.R;

import java.util.List;

/**
 * Created by Matt on 14/03/2018.
 */

public class LastMatchesAdapter extends ArrayAdapter<Match>{

    public LastMatchesAdapter(Context context, List<Match> matches) {
        super(context, 0, matches);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_last_matches,parent, false);
        }

        MatchHolder matchHolder = (MatchHolder) convertView.getTag();
        if(matchHolder == null){
            matchHolder = new MatchHolder();
            matchHolder.idGame = convertView.findViewById(R.id.idgame);
            matchHolder.winOrLose = convertView.findViewById(R.id.winorlose);
            matchHolder.duration = convertView.findViewById(R.id.duration);
            matchHolder.kda = convertView.findViewById(R.id.kda);
            matchHolder.xpm= convertView.findViewById(R.id.xpm);
            convertView.setTag(matchHolder);
        }

        Match match = getItem(position);

        Boolean win = match.getWin();
        matchHolder.idGame.setText(match.getMatchId());
        matchHolder.winOrLose.setText(win.toString());
        matchHolder.duration.setText(match.getDuration());

        String kda = Integer.toString(match.getKills()) + " / " + Integer.toString(match.getDeaths()) + " / " + Integer.toString(match.getAssists());
        matchHolder.kda.setText(kda);

        String xpm = Integer.toString(match.getGoldMin()) + " gpm / " + Integer.toString(match.getXpMin()) + " xpm";
        matchHolder.xpm.setText(xpm);

        if(win){
            matchHolder.winOrLose.setTextColor(Color.parseColor("#64d176"));
            matchHolder.winOrLose.setText(R.string.win);
        }
        else{
            matchHolder.winOrLose.setTextColor(Color.parseColor("#b20c0c"));
            matchHolder.winOrLose.setText(R.string.defeat);
        }

        return convertView;
    }

    private class MatchHolder {
        private TextView idGame;
        private TextView winOrLose;
        private TextView duration;
        private TextView kda;
        private TextView xpm;
    }
}
