package com.example.dean.pokemonev;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Dean on 28/04/2016.
 */
public class SpinnerAdapter extends ArrayAdapter<String>{


    String[] items = new String[]{"Charmander", "Squirtle", "Bulbasaur",
            "Fennekin", "Froakie", "Chespin", "Torchic", "Mudkip", "Treecko"};
    int arr_images[] = { R.drawable.charmander, R.drawable.squirtle, R.drawable.bulbasaur,
            R.drawable.fennekin, R.drawable.froakie, R.drawable.chespin,
            R.drawable.torchic, R.drawable.mudkip, R.drawable.treecko};
    LayoutInflater inflater;




    public SpinnerAdapter(Context context, int textViewResourceId,   String[] objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        Context context = parent.getContext();
        inflater = LayoutInflater.from(context);
        View row=inflater.inflate(R.layout.spinner_custom, parent, false);
        TextView label=(TextView)row.findViewById(R.id.choice);
        label.setText(items[position]);

        ImageView icon=(ImageView)row.findViewById(R.id.image);
        icon.setImageResource(arr_images[position]);

        return row;
    }
}

