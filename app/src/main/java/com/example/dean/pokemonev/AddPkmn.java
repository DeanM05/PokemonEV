package com.example.dean.pokemonev;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class AddPkmn extends AppCompatActivity implements OnClickListener {
    private DBHelp mydb;
    String nickname = "", owner = "";
    Button switchB;
    ImageButton back;
    EditText nT, oT;
    Spinner dropdown;
    String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mydb = new DBHelp(this);
        setContentView(R.layout.activity_add_pkmn);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.bar_custom2, null);

        back = (ImageButton) mCustomView.findViewById(R.id.back2);
        back.setOnClickListener(this);

        android.support.v7.app.ActionBar ab = getSupportActionBar();

        ab.setDisplayShowHomeEnabled(false);
        ab.setDisplayShowTitleEnabled(false);
        ab.setCustomView(mCustomView);
        ab.setDisplayShowCustomEnabled(true);

        dropdown = (Spinner) findViewById(R.id.spinner);
        Resources res = getResources();
        items = res.getStringArray(R.array.PkmnNames);
        dropdown.setAdapter(new SpinnerAdapter(AddPkmn.this, R.layout.spinner_custom, items));

        nT = (EditText) findViewById(R.id.editText);
        oT = (EditText) findViewById(R.id.editText2);
        switchB = (Button) findViewById(R.id.button8);
        switchB.setOnClickListener(this);
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case (R.id.button8):

                nickname = nT.getText().toString().trim();
                owner = oT.getText().toString().trim();

                String species = dropdown.getSelectedItem().toString();
                int uid = mydb.getNewID();
                if (owner != null && nickname != null && !owner.isEmpty() && !nickname.isEmpty()) {
                    if (owner.length() > 12 || nickname.length() > 12)
                        Toast.makeText(getApplicationContext(), "Invalid input. Nickname and Owner must be 12 characters or smaller.", Toast.LENGTH_SHORT).show();
                    else {
                        if (mydb.newPkmn(species, owner, nickname, uid) == true) {
                            Toast.makeText(getApplicationContext(), "Pokemon was successfully added.", Toast.LENGTH_SHORT).show();
                            Intent testIntent = new Intent(this, EVManager.class);
                            testIntent.putExtra("UniqueID", uid);
                            startActivity(testIntent);
                        } else {
                            Toast.makeText(getApplicationContext(), "There was an error. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid input. Please enter a nickname and owner.", Toast.LENGTH_SHORT).show();


                }
                break;

            case (R.id.back2):
                Intent menuIntent = new Intent(this, MainMenu.class);
                startActivity(menuIntent);

                break;
        }

    }

    @Override
    public void onBackPressed() {
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
