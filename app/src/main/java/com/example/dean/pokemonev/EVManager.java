package com.example.dean.pokemonev;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.util.ArrayList;

public class EVManager extends AppCompatActivity implements OnClickListener {
    int h = 0, a = 0, d = 0, sa = 0, sd = 0, sp = 0, i = 1, j = 0, totalEVs = 0, leftover = 0, c = 0;
    private Button hB, aB, dB, saB, sdB, spB, hB2, aB2, dB2, saB2, sdB2, spB2, rusB, undoB, mbB;
    String[] pkmnS;
    int arr_images[] = {
            R.drawable.charmander, R.drawable.squirtle, R.drawable.bulbasaur,
            R.drawable.fennekin, R.drawable.froakie, R.drawable.chespin,
            R.drawable.torchic, R.drawable.mudkip, R.drawable.treecko};
    boolean hI, aI, dI, saI, sdI, spI, rus, mbI, itemActive;
    String evT;
    int i2;
    ImageView img;
    ImageButton bB, iB, shareB;
    private ProgressBar hBar, aBar, dBar, saBar, sdBar, spBar;
    private String title = "", owner = "", species = "", nName = "";
    private ArrayList<EVLog> log;
    DBHelp mydb;
    private ListView obj;
    Spinner numEV;
    int Value = 0;
    private ShareDialog shD;
    TextView tText;
    Toast t;

    private Bitmap image;

    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.activity_test);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.bar_custom, null);
        tText = (TextView) mCustomView.findViewById(R.id.nname);
        img = (ImageView) mCustomView.findViewById(R.id.imageView1);


        mydb = new DBHelp(this);
        log = new ArrayList<EVLog>();
        String[] items = new String[]{"1 EV", "2 EV's", "3 EV's"};

        Resources res = getResources();
        pkmnS = res.getStringArray(R.array.PkmnNames);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Value = extras.getInt("UniqueID");

            if (Value > 0) {
                Cursor rs = mydb.getData(Value);
                rs.moveToFirst();

                h = rs.getInt(rs.getColumnIndex(DBHelp.EVS_COLUMN_HP));
                a = rs.getInt(rs.getColumnIndex(DBHelp.EVS_COLUMN_A));
                d = rs.getInt(rs.getColumnIndex(DBHelp.EVS_COLUMN_D));
                sa = rs.getInt(rs.getColumnIndex(DBHelp.EVS_COLUMN_SA));
                sd = rs.getInt(rs.getColumnIndex(DBHelp.EVS_COLUMN_SD));
                sp = rs.getInt(rs.getColumnIndex(DBHelp.EVS_COLUMN_SP));
                title = rs.getString(rs.getColumnIndex(DBHelp.EVS_COLUMN_NICKNAME));
                owner = rs.getString(rs.getColumnIndex(DBHelp.EVS_COLUMN_OWNER));
                nName = rs.getString(rs.getColumnIndex(DBHelp.EVS_COLUMN_NICKNAME));
                species = rs.getString(rs.getColumnIndex(DBHelp.EVS_COLUMN_SPECIES));
                android.support.v7.app.ActionBar ab = getSupportActionBar();


                tText.setText(title + " - " + owner);

                c = 0;
                boolean found = false;
                while (c < pkmnS.length && found == false) {
                    if (species.equals(pkmnS[c])) {
                        img.setImageResource(arr_images[c]);
                        found = true;
                    }
                    c++;
                }
                ab.setDisplayShowHomeEnabled(false);
                ab.setDisplayShowTitleEnabled(false);
                ab.setCustomView(mCustomView);
                ab.setDisplayShowCustomEnabled(true);

                if (!rs.isClosed()) {
                    rs.close();
                }
            }
        }


        hBar = (ProgressBar) findViewById(R.id.hBar);
        aBar = (ProgressBar) findViewById(R.id.aBar);
        dBar = (ProgressBar) findViewById(R.id.dBar);
        saBar = (ProgressBar) findViewById(R.id.saBar);
        sdBar = (ProgressBar) findViewById(R.id.sdBar);
        spBar = (ProgressBar) findViewById(R.id.spBar);


        hBar.setMax(252);
        hBar.setProgress(h);
        hBar.setVisibility(View.VISIBLE);
        hBar.getProgressDrawable().setColorFilter(
                Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);


        aBar.setMax(252);
        aBar.setProgress(a);
        aBar.setVisibility(View.VISIBLE);
        aBar.getProgressDrawable().setColorFilter(
                Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);


        dBar.setMax(252);
        dBar.setProgress(d);
        dBar.setVisibility(View.VISIBLE);
        dBar.getProgressDrawable().setColorFilter(
                Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);


        saBar.setMax(252);
        saBar.setProgress(sa);
        saBar.setVisibility(View.VISIBLE);
        saBar.getProgressDrawable().setColorFilter(
                Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);


        sdBar.setMax(252);
        sdBar.setProgress(sd);
        sdBar.setVisibility(View.VISIBLE);
        sdBar.getProgressDrawable().setColorFilter(
                Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);

        spBar.setMax(252);
        spBar.setProgress(sp);
        spBar.setVisibility(View.VISIBLE);
        spBar.getProgressDrawable().setColorFilter(
                Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);


        mbB = (Button) findViewById(R.id.button15);
        mbB.setOnClickListener(this);

        aB = (Button) findViewById(R.id.button2);
        aB.setOnClickListener(this);
        aB.setText("ATK\n(" + a + "/252)");

        hB = (Button) findViewById(R.id.button5);
        hB.setOnClickListener(this);
        hB.setText("HP\n(" + h + "/252)");

        dB = (Button) findViewById(R.id.button3);
        dB.setOnClickListener(this);
        dB.setText("DEF\n(" + d + "/252)");

        saB = (Button) findViewById(R.id.button);
        saB.setOnClickListener(this);
        saB.setText("SP. ATK\n(" + sa + "/252)");

        sdB = (Button) findViewById(R.id.button4);
        sdB.setOnClickListener(this);
        sdB.setText("SP. DEF\n(" + sd + "/252)");

        spB = (Button) findViewById(R.id.button6);
        spB.setOnClickListener(this);
        spB.setText("SPEED\n(" + sp + "/252)");

        aB2 = (Button) findViewById(R.id.a2);
        aB2.setOnClickListener(this);

        hB2 = (Button) findViewById(R.id.h2);
        hB2.setOnClickListener(this);

        dB2 = (Button) findViewById(R.id.d2);
        dB2.setOnClickListener(this);

        saB2 = (Button) findViewById(R.id.sa2);
        saB2.setOnClickListener(this);

        sdB2 = (Button) findViewById(R.id.sd2);
        sdB2.setOnClickListener(this);

        spB2 = (Button) findViewById(R.id.sp2);
        spB2.setOnClickListener(this);

        rusB = (Button) findViewById(R.id.button13);
        rusB.setOnClickListener(this);

        totalEVs = h + a + d + sa + sd + sp;

        shD = new ShareDialog(this);

        undoB = (Button) findViewById(R.id.button18);
        undoB.setOnClickListener(this);

        numEV = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_ev, items);
        numEV.setAdapter(adapter);

        iB = (ImageButton) mCustomView.findViewById(R.id.imageButton);
        iB.setOnClickListener(this);

        bB = (ImageButton) mCustomView.findViewById(R.id.imageButton2);
        bB.setOnClickListener(this);

        shareB = (ImageButton) mCustomView.findViewById(R.id.share_btn);
        shareB.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button2:
                if (totalEVs < 510) {
                    evT = "";
                    String out = "";
                    i2 = 0;
                    i = getNumEV();
                    if (itemActive == true && mbI == false) {
                        EVLog e2 = powerI();
                        evT = e2.ev();
                        i2 = e2.val();
                        if(evT!="A")
                        out = getOut(evT);
                    }
                    else if (mbI == true)
                        i = i * 2;
                    if (rus == true)
                        i = i * 2;

                    totalEVs = totalEVs + i;
                    a += i;
                    if (a > 252) {
                        leftover = (a - 252);
                        a = 252;
                        totalEVs = totalEVs - leftover;
                    }
                    if (totalEVs > 510) {
                        leftover = (totalEVs - 510);
                        totalEVs = 510;
                        a = a - leftover;
                    }
                    aU(true, out);
                    log.add(new EVLog("A", i, evT, i2));
                    if (totalEVs == 510) finished();

                }
                break;

            case R.id.button5:
                if (totalEVs < 510) {
                    evT = "";
                    String out = "";
                    i2 = 0;
                    i = getNumEV();
                    if (itemActive == true && mbI == false) {
                        EVLog e2 = powerI();
                        evT = e2.ev();
                        i2 = e2.val();
                        if(evT!="HP")
                        out = getOut(evT);
                    }
                    else if (mbI == true)
                        i = i * 2;
                    if (rus == true)
                        i = i * 2;
                    totalEVs = totalEVs + i;
                    h += i;
                    if (h > 252) {
                        leftover = (h - 252);
                        h = 252;
                        totalEVs = totalEVs - leftover;
                    }
                    if (totalEVs > 510) {
                        leftover = (totalEVs - 510);
                        totalEVs = 510;
                        h = h - leftover;
                    }
                    hU(true, out);
                    log.add(new EVLog("HP", i, evT, i2));
                    if (totalEVs == 510) finished();
                }
                break;

            case R.id.button3:
                if (totalEVs < 510) {
                    evT = "";
                    String out = "";
                    i2 = 0;
                    i = getNumEV();
                    if (itemActive == true && mbI == false) {
                        EVLog e2 = powerI();
                        evT = e2.ev();
                        i2 = e2.val();
                        if(evT!="D")
                        out = getOut(evT);
                    }
                    else if (mbI == true)
                        i = i * 2;
                    if (rus == true)
                        i = i * 2;
                    totalEVs = totalEVs + i;
                    d += i;
                    if (d > 252) {
                        leftover = (d - 252);
                        d = 252;
                        totalEVs = totalEVs - leftover;
                    }
                    if (totalEVs > 510) {
                        leftover = (totalEVs - 510);
                        totalEVs = 510;
                        d = d - leftover;
                    }
                    dU(true, out);
                    log.add(new EVLog("D", i, evT, i2));
                    if (totalEVs == 510) finished();
                }
                break;

            case R.id.button:
                if (totalEVs < 510) {
                    evT = "";
                    String out = "";
                    i2 = 0;
                    i = getNumEV();
                    if (itemActive == true && mbI == false) {
                        EVLog e2 = powerI();
                        evT = e2.ev();
                        i2 = e2.val();
                        if(evT!="SA")
                        out = getOut(evT);
                    }
                    else if (mbI == true)
                        i = i * 2;
                    if (rus == true)
                        i = i * 2;
                    totalEVs = totalEVs + i;
                    sa += i;
                    if (sa > 252) {
                        leftover = (sa - 252);
                        sa = 252;
                        totalEVs = totalEVs - leftover;
                    }
                    if (totalEVs > 510) {
                        leftover = (totalEVs - 510);
                        totalEVs = 510;
                        sa = sa - leftover;
                    }
                    saU(true, out);
                    log.add(new EVLog("SA", i, evT, i2));
                    if (totalEVs == 510) finished();
                }
                break;

            case R.id.button4:
                if (totalEVs < 510) {
                    evT = "";
                    String out = "";
                    i2 = 0;
                    i = getNumEV();
                    if (itemActive == true && mbI == false) {
                        EVLog e2 = powerI();
                        evT = e2.ev();
                        i2 = e2.val();
                        if(evT!="SD")
                        out = getOut(evT);
                    }
                    else if (mbI == true)
                        i = i * 2;
                    if (rus == true)
                        i = i * 2;
                    totalEVs = totalEVs + i;
                    sd += i;
                    if (sd > 252) {
                        leftover = (sd - 252);
                        sd = 252;
                        totalEVs = totalEVs - leftover;
                    }
                    if (totalEVs > 510) {
                        leftover = (totalEVs - 510);
                        totalEVs = 510;
                        sd = sd - leftover;
                    }
                    sdU(true, out);
                    log.add(new EVLog("SD", i, evT, i2));
                    if (totalEVs == 510) finished();
                }
                break;

            case R.id.button6:
                if (totalEVs < 510) {
                    evT = "";
                    String out = "";
                    i2 = 0;
                    i = getNumEV();
                    if (itemActive == true && mbI == false) {
                        EVLog e2 = powerI();
                        evT = e2.ev();
                        i2 = e2.val();
                        if(evT!="SP")
                        out = getOut(evT);
                    }
                    else if (mbI == true)
                        i = i * 2;
                    if (rus == true)
                        i = i * 2;
                    totalEVs = totalEVs + i;
                    j = 0;
                    sp += i;
                    if (sp > 252) {
                        leftover = (sp - 252);
                        sp = 252;
                        totalEVs = totalEVs - leftover;
                    }
                    if (totalEVs > 510) {
                        leftover = (totalEVs - 510);
                        totalEVs = 510;
                        sp = sp - leftover;
                    }
                    spU(true, out);
                    log.add(new EVLog("SP", i, evT, i2));
                    if (totalEVs == 510) finished();

                }
                break;

            case R.id.button15:
                if (mbI == false && itemActive == false) {
                    mbI = true;
                    mbB.setBackgroundColor(Color.parseColor("#979797"));
                    mbB.setTextColor(Color.parseColor("#FFFFFF"));
                    itemActive = true;
                } else if (mbI == true && itemActive == true) {
                    mbI = false;
                    mbB.setBackgroundColor(Color.parseColor("#86979797"));
                    mbB.setTextColor(Color.parseColor("#000000"));
                    itemActive = false;
                }
                break;

            case R.id.a2:
                if (aI == false && itemActive == false) {
                    aI = true;
                    aB2.setBackgroundColor(Color.parseColor("#ed2626"));
                    aB2.setTextColor(Color.parseColor("#FFFFFF"));
                    itemActive = true;
                } else if (aI == true && itemActive == true) {
                    aI = false;
                    aB2.setBackgroundColor(Color.parseColor("#86ed2626"));
                    aB2.setTextColor(Color.parseColor("#000000"));
                    itemActive = false;
                }
                break;
            case R.id.h2:
                if (hI == false && itemActive == false) {
                    hI = true;
                    hB2.setBackgroundColor(Color.parseColor("#05ff37"));
                    hB2.setTextColor(Color.parseColor("#FFFFFF"));
                    itemActive = true;
                } else if (hI == true && itemActive == true) {
                    hI = false;
                    hB2.setBackgroundColor(Color.parseColor("#8605ff37"));
                    hB2.setTextColor(Color.parseColor("#000000"));
                    itemActive = false;
                }
                break;
            case R.id.d2:
                if (dI == false && itemActive == false) {
                    dI = true;
                    dB2.setBackgroundColor(Color.parseColor("#ffe600"));
                    dB2.setTextColor(Color.parseColor("#FFFFFF"));
                    itemActive = true;
                } else if (dI == true && itemActive == true) {
                    dI = false;
                    dB2.setBackgroundColor(Color.parseColor("#86ffe600"));
                    dB2.setTextColor(Color.parseColor("#000000"));
                    itemActive = false;
                }
                break;
            case R.id.sa2:
                if (saI == false && itemActive == false) {
                    saI = true;
                    saB2.setBackgroundColor(Color.parseColor("#d11fc8"));
                    saB2.setTextColor(Color.parseColor("#FFFFFF"));
                    itemActive = true;
                } else if (saI == true && itemActive == true) {
                    saI = false;
                    saB2.setBackgroundColor(Color.parseColor("#86d11fc8"));
                    saB2.setTextColor(Color.parseColor("#000000"));
                    itemActive = false;
                }
                break;
            case R.id.sd2:
                if (sdI == false && itemActive == false) {
                    sdI = true;
                    sdB2.setBackgroundColor(Color.parseColor("#ff8400"));
                    sdB2.setTextColor(Color.parseColor("#FFFFFF"));
                    itemActive = true;
                } else if (sdI == true && itemActive == true) {
                    sdI = false;
                    sdB2.setBackgroundColor(Color.parseColor("#86ff8400"));
                    sdB2.setTextColor(Color.parseColor("#000000"));
                    itemActive = false;
                }
                break;
            case R.id.sp2:
                if (spI == false && itemActive == false) {
                    spI = true;
                    spB2.setBackgroundColor(Color.parseColor("#1cfff7"));
                    spB2.setTextColor(Color.parseColor("#FFFFFF"));
                    itemActive = true;
                } else if (spI == true && itemActive == true) {
                    spI = false;
                    spB2.setBackgroundColor(Color.parseColor("#861cfff7"));
                    spB2.setTextColor(Color.parseColor("#000000"));
                    itemActive = false;
                }
                break;
            case R.id.button13:
                if (rus == false) {
                    rus = true;
                    rusB.setBackgroundColor(Color.parseColor("#8641ff"));
                    rusB.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    rus = false;
                    rusB.setBackgroundColor(Color.parseColor("#728641ff"));
                    rusB.setTextColor(Color.parseColor("#000000"));
                }
                break;
            case R.id.imageButton2:
                Intent menuIntent = new Intent(this, MainMenu.class);
                startActivity(menuIntent);
                break;

            case R.id.imageButton:
                Intent optionIntent = new Intent(this, Options.class);
                Bundle b = new Bundle();
                b.putInt("UniqueID", Value);
                b.putInt("Species", c);
                optionIntent.putExtras(b);
                startActivity(optionIntent);

                break;
            case R.id.button18:

                if (!(log.size() == 0)) {
                    EVLog e = log.get(log.size() - 1);
                    String evType = e.ev();
                    String evType2 = e.ev2();
                    String out = "";
                    int val = e.val();
                    int val2 = e.val2();
                    totalEVs = totalEVs - val;
                    totalEVs = totalEVs - val2;
                    if(evType2!=null) {
                        switch(evType2)
                        {
                            case "HP":
                                h -= val2;
                                if(evType!="HP")
                                out = "HP: " + h + "/252\n";
                                hU(false, "");
                                break;
                            case "A":
                                a -= val2;
                                if(evType!="A")
                                out = "Attack: " + a + "/252\n";
                                aU(false, "");
                                break;
                            case "D":
                                d -= val2;
                                if(evType!="D")
                                out = "Defense: " + d + "/252\n";
                                dU(false, "");
                                break;
                            case "SA":
                                sa -= val2;
                                if(evType!="SA")
                                out = "Sp. Attack: " + sa + "/252\n";
                                saU(false, "");
                                break;
                            case "SD":
                                sd -= val2;
                                if(evType!="SD")
                                out = "Sp. Defense: " + sd + "/252\n";
                                sdU(false, "");
                                break;
                            case "SP":
                                sp -= val2;
                                if(evType!="SP")
                                out = "Speed: " + sp + "/252\n";
                                spU(false, "");
                                break;
                        }
                    }
                    switch (evType) {
                        case "HP":
                            h = h - val;
                            hU(true, out);
                            break;
                        case "A":
                            a = a - val;
                            aU(true, out);
                            break;
                        case "D":
                            d = d - val;
                            dU(true, out);
                            break;
                        case "SA":
                            sa = sa - val;
                            saU(true, out);
                            break;
                        case "SD":
                            sd = sd - val;
                            sdU(true, out);
                            break;
                        case "SP":
                            sp = sp - val;
                            spU(true, out);
                            break;

                    }


                    log.remove(log.size() - 1);
                }
                break;
            case (R.id.share_btn):
                postPicture();
                break;
            default:
                break;
        }

    }

    public void hU(boolean tB, String ex) {
        hBar.setProgress(h);
        mydb.updateEVs("HP", h, Value);
        hB.setText("HP\n(" + h + "/252)");
        if (tB == true) {
            if (t != null)
                t.cancel();
            t = Toast.makeText(EVManager.this, "HP: " + h + "/252\n" + ex + "Total EV's: " + totalEVs + "/510", Toast.LENGTH_LONG);
            t.show();
        }
    }

    public void aU(boolean tB, String ex) {
        aBar.setProgress(a);
        mydb.updateEVs("A", a, Value);
        aB.setText("ATK\n(" + a + "/252)");
        if (tB == true) {
            if (t != null)
                t.cancel();
            t = Toast.makeText(EVManager.this, "Attack: " + a + "/252\n" + ex + "Total EV's: " + totalEVs + "/510", Toast.LENGTH_LONG);
            t.show();
        }
    }
    public void dU(boolean tB, String ex) {
        dBar.setProgress(d);
        mydb.updateEVs("D", d, Value);
        dB.setText("DEF\n(" + d + "/252)");
        if (tB == true) {
            if (t != null)
                t.cancel();
            t = Toast.makeText(EVManager.this, "Defense: " + d + "/252\n" + ex + "Total EV's: " + totalEVs + "/510", Toast.LENGTH_LONG);
            t.show();
        }
    }
    public void saU(boolean tB, String ex) {
        saBar.setProgress(sa);
        mydb.updateEVs("SA", sa, Value);
        saB.setText("SP. ATK\n(" + sa + "/252)");
        if (tB == true) {
            if (t != null)
                t.cancel();
            t = Toast.makeText(EVManager.this, "Sp. Attack: " + sa + "/252\n" + ex + "Total EV's: " + totalEVs + "/510", Toast.LENGTH_LONG);
            t.show();
        }
    }

    public void sdU(boolean tB, String ex) {
        sdBar.setProgress(sd);
        mydb.updateEVs("SD", sd, Value);
        sdB.setText("SP. DEF\n(" + sd + "/252)");
        if (tB == true) {
            if (t != null)
                t.cancel();
            t = Toast.makeText(EVManager.this, "Sp. Defense: " + sd + "/252\n" + ex + "Total EV's: " + totalEVs + "/510", Toast.LENGTH_LONG);
            t.show();
        }
    }

    public void spU(boolean tB, String ex) {
        spBar.setProgress(sp);
        mydb.updateEVs("SP", sp, Value);
        spB.setText("SPEED\n(" + sp + "/252)");
        if (tB == true) {
            if (t != null)
                t.cancel();
            t = Toast.makeText(EVManager.this, "Speed: " + sp + "/252\n" + ex + "Total EV's: " + totalEVs + "/510", Toast.LENGTH_LONG);
            t.show();
        }
    }

    public void postPicture() {
        if (counter == 0) {

            AlertDialog.Builder shareDialog = new AlertDialog.Builder(this);
            shareDialog.setTitle("Share Screenshot To Facebook");
            shareDialog.setMessage("Would you like to share a screenshot of " + nName + "'s progress to Facebook?");
            shareDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    sharePhoto();
                }
            });
            shareDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog shareD = shareDialog.create();
            shareD.show();
        } else {
            counter = 0;
            shD.show(null);
        }
    }

    @Override
    public void onBackPressed() {
    }

    public int getNumEV() {
        String temp = numEV.getSelectedItem().toString();
        int num = 1;
        switch (temp) {
            case "1 EV":
                num = 1;
                break;
            case "2 EV's":
                num = 2;
                break;
            case "3 EV's":
                num = 3;
                break;
        }
        return num;
    }

    public void finished() {
        AlertDialog.Builder fin = new AlertDialog.Builder(EVManager.this);

        fin.setTitle("Pokemon Fully Trained!");
        fin.setMessage("Congratulations, " + owner + "! Your " + species + ", " + nName + ", has been fully EV trained!\n\nWould you like to share a screenshot of " + nName + "'s EV's to Facebook?");

        fin.setPositiveButton("Share", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                sharePhoto();
            }

        });

        fin.setNegativeButton("Return", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog a = fin.create();
        a.show();
    }

    public void sharePhoto() {

        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        image = Bitmap.createBitmap(rootView.getDrawingCache());
        rootView.destroyDrawingCache();

        SharePhoto photo = new SharePhoto.Builder().setBitmap(image).build();
        SharePhotoContent content = new SharePhotoContent.Builder().addPhoto(photo).build();

        shD.show(content);
        counter = 1;
        shareB.performClick();
    }

    public EVLog powerI() {
        int i = 4;
        String evT = "";
        if (rus == true)
            i = 8;
        if (aI == true) {
            a += i;
            totalEVs = totalEVs + i;
            if (a > 252) {
                leftover = (a - 252);
                a = 252;
                totalEVs = totalEVs - leftover;
            }
            if (totalEVs > 510) {
                leftover = (totalEVs - 510);
                totalEVs = 510;
                a = a - leftover;
            }
            aU(false, "");
            evT = "A";
        } else if (hI == true) {
            h += i;
            totalEVs = totalEVs + i;
            if (h > 252) {
                leftover = (h - 252);
                h = 252;
                totalEVs = totalEVs - leftover;
            }
            if (totalEVs > 510) {
                leftover = (totalEVs - 510);
                totalEVs = 510;
                h = h - leftover;
            }
            hU(false, "");
            evT = "HP";
        } else if (dI == true) {
            d += i;
            totalEVs = totalEVs + i;
            if (d > 252) {
                leftover = (d - 252);
                d = 252;
                totalEVs = totalEVs - leftover;
            }
            if (totalEVs > 510) {
                leftover = (totalEVs - 510);
                totalEVs = 510;
                d = d - leftover;
            }
            dU(false, "");
            evT = "D";
        } else if (saI == true) {
            sa += i;
            totalEVs = totalEVs + i;
            if (sa > 252) {
                leftover = (sa - 252);
                sa = 252;
                totalEVs = totalEVs - leftover;
            }
            if (totalEVs > 510) {
                leftover = (totalEVs - 510);
                totalEVs = 510;
                sa = sa - leftover;
            }
            saU(false, "");
            evT = "SA";
        } else if (sdI == true) {
            sd += i;
            totalEVs = totalEVs + i;
            if (sd > 252) {
                leftover = (sd - 252);
                sd = 252;
                totalEVs = totalEVs - leftover;
            }
            if (totalEVs > 510) {
                leftover = (totalEVs - 510);
                totalEVs = 510;
                sd = sd - leftover;
            }
            sdU(false, "");
            evT = "SD";
        } else if (spI == true) {
            sp += i;
            totalEVs = totalEVs + i;
            if (sp > 252) {
                leftover = (sp - 252);
                sp = 252;
                totalEVs = totalEVs - leftover;
            }
            if (totalEVs > 510) {
                leftover = (totalEVs - 510);
                totalEVs = 510;
                sp = sp - leftover;
            }
            spU(false, "");
            evT = "SP";
        }
        EVLog e = new EVLog(evT, i, "", 0);
        return e;
    }
public String getOut(String evT2)
{
    String out = "";
    switch(evT2)
    {
        case "HP":
            out = "HP: " + h + "/252\n";
            break;
        case "A":
            out = "Attack: " + a + "/252\n";
            break;
        case "D":
            out = "Defense: " + d + "/252\n";
            break;
        case "SA":
            out = "Sp. Attack: " + sa + "/252\n";
            break;
        case "SD":
            out = "Sp. Defense: " + sd + "/252\n";
            break;
        case "SP":
            out = "Speed: " + sp + "/252\n";
            break;
    }
    return out;
}
}





