package com.example.dean.pokemonev;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Dean on 27/04/2016.
 */
public class MenuAdapter extends BaseAdapter {

    private static ArrayList<Pkmn> searchArrayList;
    private LayoutInflater mInflater;
    int arr_images[] = {
            R.drawable.charmander, R.drawable.squirtle, R.drawable.bulbasaur,
            R.drawable.fennekin, R.drawable.froakie, R.drawable.chespin,
            R.drawable.torchic, R.drawable.mudkip, R.drawable.treecko};

    String[] items = new String[]{"Charmander", "Squirtle", "Bulbasaur",
            "Fennekin", "Froakie", "Chespin", "Torchic", "Mudkip", "Treecko"};

    public MenuAdapter(Context context, ArrayList<Pkmn> results) {
        searchArrayList = results;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return searchArrayList.size();
    }

    public Object getItem(int position) {
        return searchArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_row_view, null);
            holder = new ViewHolder();
            holder.imgPkmn = (ImageView) convertView.findViewById(R.id.image);
            holder.txtNickname = (TextView) convertView.findViewById(R.id.nName);
            holder.txtOwner = (TextView) convertView.findViewById(R.id.owner);
            holder.txtSpecies = (TextView) convertView.findViewById(R.id.species);
            holder.txtEVs = (TextView) convertView.findViewById(R.id.totalEV);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtNickname.setText(searchArrayList.get(position).nname());
        holder.txtOwner.setText(searchArrayList.get(position).own());
        String sp = searchArrayList.get(position).pid();
        holder.txtSpecies.setText(sp);
        int c = 0;
        boolean found = false;
        while(c<items.length && found == false)
        {
            if(sp.equals(items[c])) {
                holder.imgPkmn.setImageResource(arr_images[c]);
                found = true;
            }
            c++;
        }

        holder.txtEVs.setText(searchArrayList.get(position).ev() + "/510");


        return convertView;
    }

    static class ViewHolder {
        TextView txtNickname;
        TextView txtOwner;
        TextView txtSpecies;
        TextView txtEVs;
        ImageView imgPkmn;
    }
}

