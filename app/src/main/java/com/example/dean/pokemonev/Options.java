package com.example.dean.pokemonev;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Options extends AppCompatActivity implements OnClickListener {
    DBHelp mydb;
    int uid = 0, h = 0, a = 0, d = 0, sa = 0, sd = 0, sp = 0, totalEVs = 0, leftover = 0, spec = 0;
    int arr_images[] = {
            R.drawable.charmander, R.drawable.squirtle, R.drawable.bulbasaur,
            R.drawable.fennekin, R.drawable.froakie, R.drawable.chespin,
            R.drawable.torchic, R.drawable.mudkip, R.drawable.treecko};
    TextView hT, aT, dT, saT, sdT, spT, totalT, tText;
    String title = "", owner = "", output = "", species = "", nName = "";
    Button erase, reset, guide, aB, dB, hB, saB, sdB, spB, listB;
    ImageButton bB;
    ImageView img;
    Toast t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);


        mydb = new DBHelp(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.bar_custom4, null);
        tText = (TextView) mCustomView.findViewById(R.id.nname2);
        img = (ImageView) mCustomView.findViewById(R.id.imageViewN);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            uid = extras.getInt("UniqueID");
            spec = extras.getInt("Species") - 1;

            if (uid > 0) {
                Cursor rs = mydb.getData(uid);
                rs.moveToFirst();

                h = rs.getInt(rs.getColumnIndex(DBHelp.EVS_COLUMN_HP));
                a = rs.getInt(rs.getColumnIndex(DBHelp.EVS_COLUMN_A));
                d = rs.getInt(rs.getColumnIndex(DBHelp.EVS_COLUMN_D));
                sa = rs.getInt(rs.getColumnIndex(DBHelp.EVS_COLUMN_SA));
                sd = rs.getInt(rs.getColumnIndex(DBHelp.EVS_COLUMN_SD));
                sp = rs.getInt(rs.getColumnIndex(DBHelp.EVS_COLUMN_SP));
                title = rs.getString(rs.getColumnIndex(DBHelp.EVS_COLUMN_NICKNAME));
                species = rs.getString(rs.getColumnIndex(DBHelp.EVS_COLUMN_SPECIES));
                owner = rs.getString(rs.getColumnIndex(DBHelp.EVS_COLUMN_OWNER));
                nName = rs.getString(rs.getColumnIndex(DBHelp.EVS_COLUMN_NICKNAME));

                totalEVs = h + a + d + sa + sd + sp;

                android.support.v7.app.ActionBar ab = getSupportActionBar();
                tText.setText(title + " - " + owner);
                img.setImageResource(arr_images[spec]);
                ab.setDisplayShowHomeEnabled(false);
                ab.setDisplayShowTitleEnabled(false);
                ab.setCustomView(mCustomView);
                ab.setDisplayShowCustomEnabled(true);


                if (!rs.isClosed()) {
                    rs.close();
                }
            }
        }

        bB = (ImageButton) mCustomView.findViewById(R.id.imageButtonN);
        bB.setOnClickListener(this);

        totalT = (TextView) findViewById(R.id.textView12);
        totalT.setText("Total EV's: " + totalEVs + "/510");

        hT = (TextView) findViewById(R.id.textView2);
        hT.setText("HP: " + h + "/252");

        aT = (TextView) findViewById(R.id.textView3);
        aT.setText("Attack: " + a + "/252");

        dT = (TextView) findViewById(R.id.textView4);
        dT.setText("Defense: " + d + "/252");

        saT = (TextView) findViewById(R.id.textView5);
        saT.setText("Sp. Atk: " + sa + "/252");

        sdT = (TextView) findViewById(R.id.textView10);
        sdT.setText("Sp. Def: " + sd + "/252");

        spT = (TextView) findViewById(R.id.textView11);
        spT.setText("Speed: " + sp + "/252");

        listB = (Button) findViewById(R.id.button7);
        listB.setOnClickListener(this);

        erase = (Button) findViewById(R.id.button14);
        erase.setOnClickListener(this);

        reset = (Button) findViewById(R.id.button11);
        reset.setOnClickListener(this);


        guide = (Button) findViewById(R.id.button17);
        guide.setOnClickListener(this);

        aB = (Button) findViewById(R.id.button2V);
        aB.setOnClickListener(this);

        hB = (Button) findViewById(R.id.button5V);
        hB.setOnClickListener(this);

        dB = (Button) findViewById(R.id.button3V);
        dB.setOnClickListener(this);

        saB = (Button) findViewById(R.id.buttonV);
        saB.setOnClickListener(this);

        sdB = (Button) findViewById(R.id.button4V);
        sdB.setOnClickListener(this);

        spB = (Button) findViewById(R.id.button6V);
        spB.setOnClickListener(this);


    }

    public void onClick(View v) {

        switch (v.getId()) {

            case (R.id.buttonV):
                output = "";
                if (sa < 100 && totalEVs < 510) {
                    sa += 10;
                    totalEVs += 10;
                    if (totalEVs > 510) {
                        leftover = (totalEVs - 510);
                        totalEVs = 510;
                        sa -= leftover;
                    }
                    mydb.updateEVs("SA", sa, uid);
                    saT.setText("Sp. Atk: " + sa + "/252");
                    updateTotal();
                    if (totalEVs == 510) finished();
                    output += "Sp. Attack: " + sa + "/252\nTotal EV's: " + totalEVs + "/510";
                } else if (sa >= 100 && totalEVs != 510)
                    output += "Vitamins can only be used on stats with EV's below 100.";
                if (t != null)
                    t.cancel();
                if (output != null && !output.isEmpty()) {
                    t = Toast.makeText(Options.this, output, Toast.LENGTH_LONG);
                    t.show();
                }

                break;

            case (R.id.button5V):
                output = "";
                if (h < 100 && totalEVs < 510) {
                    h += 10;
                    totalEVs += 10;
                    if (totalEVs > 510) {
                        leftover = (totalEVs - 510);
                        totalEVs = 510;
                        h -= leftover;
                    }
                    mydb.updateEVs("HP", h, uid);
                    hT.setText("HP: " + h + "/252");
                    updateTotal();
                    if (totalEVs == 510) finished();
                    output += "HP: " + h + "/252\nTotal EV's: " + totalEVs + "/510";
                } else if (h >= 100 && totalEVs != 510)
                    output += "Vitamins can only be used on stats with EV's below 100.";
                if (t != null)
                    t.cancel();
                if (output != null && !output.isEmpty()) {
                    t = Toast.makeText(Options.this, output, Toast.LENGTH_LONG);
                    t.show();
                }

                break;

            case (R.id.button4V):
                output = "";
                if (sd < 100 && totalEVs < 510) {
                    sd += 10;
                    totalEVs += 10;
                    if (totalEVs > 510) {
                        leftover = (totalEVs - 510);
                        totalEVs = 510;
                        sd -= leftover;
                    }
                    mydb.updateEVs("SD", sd, uid);
                    sdT.setText("Sp. Def: " + sd + "/252");
                    updateTotal();
                    if (totalEVs == 510) finished();
                    output += "Sp. Defense: " + sd + "/252\nTotal EV's: " + totalEVs + "/510";
                } else if (sd >= 100 && totalEVs != 510)
                    output += "Vitamins can only be used on stats with EV's below 100.";
                if (t != null)
                    t.cancel();
                if (output != null && !output.isEmpty()) {
                    t = Toast.makeText(Options.this, output, Toast.LENGTH_LONG);
                    t.show();
                }

                break;

            case (R.id.button6V):
                output = "";
                if (sp < 100 && totalEVs < 510) {
                    sp += 10;
                    totalEVs += 10;
                    if (totalEVs > 510) {
                        leftover = (totalEVs - 510);
                        totalEVs = 510;
                        sp -= leftover;
                    }
                    mydb.updateEVs("SP", sp, uid);
                    spT.setText("Speed: " + sp + "/252");
                    updateTotal();
                    if (totalEVs == 510) finished();
                    output += "Speed: " + sp + "/252\nTotal EV's: " + totalEVs + "/510";
                } else if (sp >= 100 && totalEVs != 510)
                    output += "Vitamins can only be used on stats with EV's below 100.";
                if (t != null)
                    t.cancel();
                if (output != null && !output.isEmpty()) {
                    t = Toast.makeText(Options.this, output, Toast.LENGTH_LONG);
                    t.show();
                }

                break;

            case (R.id.button3V):
                output = "";
                if (d < 100 && totalEVs < 510) {
                    d += 10;
                    totalEVs += 10;
                    if (totalEVs > 510) {
                        leftover = (totalEVs - 510);
                        totalEVs = 510;
                        d -= leftover;
                    }
                    mydb.updateEVs("D", d, uid);
                    dT.setText("Defense: " + d + "/252");
                    updateTotal();
                    if (totalEVs == 510) finished();
                    output += "Defense: " + d + "/252\nTotal EV's: " + totalEVs + "/510";
                } else if (d >= 100 && totalEVs != 510)
                    output += "Vitamins can only be used on stats with EV's below 100.";
                if (t != null)
                    t.cancel();
                if (output != null && !output.isEmpty()) {
                    t = Toast.makeText(Options.this, output, Toast.LENGTH_LONG);
                    t.show();
                }

                break;

            case (R.id.button2V):
                String output = "";
                if (a < 100 && totalEVs < 510) {
                    a = a + 10;
                    totalEVs += 10;
                    if (totalEVs > 510) {
                        leftover = (totalEVs - 510);
                        totalEVs = 510;
                        a -= leftover;
                    }
                    mydb.updateEVs("A", a, uid);
                    aT.setText("Attack: " + a + "/252");
                    updateTotal();
                    if (totalEVs == 510) finished();
                    output += "Attack: " + a + "/252\nTotal EV's: " + totalEVs + "/510";
                } else if (a >= 100 && totalEVs != 510)
                    output += "Vitamins can only be used on stats with EV's below 100.";
                if (t != null)
                    t.cancel();
                if (output != null && !output.isEmpty()) {
                    t = Toast.makeText(Options.this, output, Toast.LENGTH_LONG);
                    t.show();
                }

                break;


            case (R.id.button11):

                AlertDialog.Builder EVErase = new AlertDialog.Builder(Options.this);

                EVErase.setTitle("Confirm EV Reset");
                EVErase.setMessage("Are you sure you want to reset all EV's for " + nName + "?");

                EVErase.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        mydb.resetEVs(uid);
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "EV's were successfully reset.", Toast.LENGTH_SHORT).show();
                        Intent backIntent = new Intent(Options.this, EVManager.class);
                        backIntent.putExtra("UniqueID", uid);
                        startActivity(backIntent);
                    }

                });

                EVErase.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog a2 = EVErase.create();
                a2.show();
                break;

            case (R.id.button14):

                AlertDialog.Builder PkmnErase = new AlertDialog.Builder(Options.this);

                PkmnErase.setTitle("Confirm Pokemon Delete");
                PkmnErase.setMessage("Are you sure you want to delete " + nName + "?");

                PkmnErase.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        mydb.deletePkmn(uid);
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "The Pokemon was successfully deleted.", Toast.LENGTH_SHORT).show();
                        Intent menuIntent = new Intent(Options.this, MainMenu.class);
                        startActivity(menuIntent);
                    }

                });

                PkmnErase.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog a1 = PkmnErase.create();
                a1.show();
                break;
            case (R.id.imageButtonN):
                Intent evIntent = new Intent(Options.this, EVManager.class);
                evIntent.putExtra("UniqueID", uid);
                startActivity(evIntent);
                break;

            case (R.id.button17):
                Uri uriGuide = Uri.parse("http://www.azurilland.com/forums/pokemon-6th-generation/user-guides-6th-gen/450081-wigglers-guide-to-horde-ev-training-x-y-or-as");
                Intent guide = new Intent(Intent.ACTION_VIEW, uriGuide);
                startActivity(guide);
                break;

            case (R.id.button7):
                Uri uriList = Uri.parse("http://bulbapedia.bulbagarden.net/wiki/List_of_Pok%C3%A9mon_by_effort_value_yield");
                Intent list = new Intent(Intent.ACTION_VIEW, uriList);
                startActivity(list);
                break;

        }
    }

    public void updateTotal() {
        totalT.setText("Total EV's: " + totalEVs + "/510");
    }

    public void finished() {
        AlertDialog.Builder fin = new AlertDialog.Builder(Options.this);

        fin.setTitle("Pokemon Fully Trained!");
        fin.setMessage("Congratulations, " + owner + "! Your " + species + ", " + nName + ", has been fully EV trained! Keep it up!");

        fin.setPositiveButton("Okay", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                dialog.dismiss();
            }

        });


        AlertDialog a = fin.create();
        a.show();
    }

    @Override
    public void onBackPressed() {
    }
}
