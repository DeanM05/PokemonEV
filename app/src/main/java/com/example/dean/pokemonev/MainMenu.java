package com.example.dean.pokemonev;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainMenu extends AppCompatActivity implements OnClickListener {
    private DBHelp mydb;
    ListView pkmnView;
    TextView title;
    Button addB;
    int uid = 0;
    int totalEVs = 0;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        mydb = new DBHelp(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.bar_custom3, null);

        title = (TextView) mCustomView.findViewById(R.id.appTitle);
        title.setText("Pokemon EV Manager");


        android.support.v7.app.ActionBar ab = getSupportActionBar();

        ab.setDisplayShowHomeEnabled(false);
        ab.setDisplayShowTitleEnabled(false);
        ab.setCustomView(mCustomView);
        ab.setDisplayShowCustomEnabled(true);


        addB = (Button) findViewById(R.id.button12);
        addB.setOnClickListener(this);

        ArrayList<String> ar = mydb.getAllIDs();
        ArrayList<Pkmn> pkmnList = new ArrayList<Pkmn>();
        Cursor rs;
        int i = 0;
        while (i < ar.size()) {
            uid = Integer.parseInt(ar.get(i));
            rs = mydb.getData(uid);

            rs.moveToFirst();

            String nname = rs.getString(rs.getColumnIndex(DBHelp.EVS_COLUMN_NICKNAME));
            String owner = rs.getString(rs.getColumnIndex(DBHelp.EVS_COLUMN_OWNER));
            String id = rs.getString(rs.getColumnIndex(DBHelp.EVS_COLUMN_SPECIES));
            totalEVs = (rs.getInt(rs.getColumnIndex(DBHelp.EVS_COLUMN_A)) + rs.getInt(rs.getColumnIndex(DBHelp.EVS_COLUMN_HP))
                    + rs.getInt(rs.getColumnIndex(DBHelp.EVS_COLUMN_D)) + rs.getInt(rs.getColumnIndex(DBHelp.EVS_COLUMN_SA))
                    + rs.getInt(rs.getColumnIndex(DBHelp.EVS_COLUMN_SD)) + rs.getInt(rs.getColumnIndex(DBHelp.EVS_COLUMN_SP)));
            Pkmn temp = new Pkmn(Integer.parseInt(ar.get(i)), nname, owner, id, totalEVs);
            pkmnList.add(temp);
            rs.close();
            i++;

        }

        pkmnView = (ListView) findViewById(R.id.listView);
        pkmnView.setAdapter(new MenuAdapter(this, pkmnList));

        pkmnView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = pkmnView.getItemAtPosition(position);
                Pkmn fullObject = (Pkmn) o;
                Toast.makeText(MainMenu.this, "You have chosen " + fullObject.nname() + ", owned by " + fullObject.own() + ". Enjoy your training!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(v.getContext(), EVManager.class);
                intent.putExtra("UniqueID", fullObject.uniqueid());
                startActivity(intent);

            }
        });
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case (R.id.button12):
                Intent i = new Intent(this, AddPkmn.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public void onBackPressed() {
    }
}