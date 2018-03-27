package com.app.dotastats.dotastats.Adapters;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.dotastats.dotastats.Activity.PlayerProfileActivity;
import com.app.dotastats.dotastats.Beans.Player;
import com.app.dotastats.dotastats.R;

import java.util.ArrayList;
import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.MyViewHolder> {

    private ArrayList<Player> players ;

    public PlayerAdapter(List<Player> players) {
        super();
        this.players = (ArrayList<Player>) players;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_players, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Player player = players.get(position);
        holder.display(player);
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public void setPlayers(ArrayList<Player> p){
        players = p;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        private TextView lastPlayed;
        public ImageView image;

        private Player p;

        private MyViewHolder(final View itemView) {
            super(itemView);

            name = (itemView.findViewById(R.id.namePlayer));
            lastPlayed = (itemView.findViewById(R.id.lastPlayed));
            image = (itemView.findViewById(R.id.image));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    name.setTextColor(Color.parseColor("#ffffff"));

                    Intent myIntent = new Intent(view.getContext(), PlayerProfileActivity.class);
                    myIntent.putExtra("idPlayer", p.getId());
                    myIntent.putExtra("lastPlayed",lastPlayed.getText().toString());
                    view.getContext().startActivity(myIntent);
                }
            });
        }

        private void display(Player player) {
            p = player;
            name.setText(player.getName());
            lastPlayed.setText(player.getLastPlayed());
            image.setImageBitmap(player.getAvatar());
        }

    }

}
