package com.app.dotastats.dotastats.Adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.dotastats.dotastats.Activity.HeroesActivity;
import com.app.dotastats.dotastats.Beans.Hero;
import com.app.dotastats.dotastats.R;

import java.util.ArrayList;

// Displays a list of all the heroes (depending on the filters chosen by the user)
public class AllHeroAdapter extends RecyclerView.Adapter<AllHeroAdapter.MyViewHolder>{

    private ArrayList<Hero> heroes;

    public AllHeroAdapter(ArrayList<Hero> heroes) {
        super();
        this.heroes = heroes;
    }

    @Override
    public AllHeroAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_heroes, parent, false);
        return new AllHeroAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AllHeroAdapter.MyViewHolder holder, int position) {
        Hero hero = heroes.get(position);
        holder.display(hero);
    }

    @Override
    public int getItemCount() {
        return heroes.size();
    }

    public void setHeroes(ArrayList<Hero> h){
        heroes = h;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public ImageView image;

        private Hero h;

        private MyViewHolder(final View itemView) {
            super(itemView);

            name = (itemView.findViewById(R.id.Name));
            image = (itemView.findViewById(R.id.Image));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myIntent = new Intent(view.getContext(), HeroesActivity.class);
                    myIntent.putExtra("nameHero", h.getName());
                    view.getContext().startActivity(myIntent);
                }
            });
        }

        private void display(Hero hero) {
            h = hero;
            name.setText(h.getName());
            image.setImageBitmap(h.getImage());
        }
    }
}