package com.example.dean.pokemonev; /**
 * Created by Dean on 23/04/2016.
 */
import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

 public class DBHelp extends SQLiteOpenHelper {

     public static final String DATABASE_NAME = "Pkmn";
     public static final String EVS_TABLE_NAME = "PkmnEVs";
     public static final String EVS_COLUMN_UNIQUEID = "UniqueID";
     public static final String EVS_COLUMN_SPECIES = "PkmnSpecies";
     public static final String EVS_COLUMN_OWNER = "Owner";
     public static final String EVS_COLUMN_NICKNAME = "Nickname";
     public static final String EVS_COLUMN_HP = "HP";
     public static final String EVS_COLUMN_A = "A";
     public static final String EVS_COLUMN_D = "D";
     public static final String EVS_COLUMN_SA = "SA";
     public static final String EVS_COLUMN_SD = "SD";
     public static final String EVS_COLUMN_SP = "SP";

     public DBHelp(Context context) {
         super(context, DATABASE_NAME, null, 1);
     }

     @Override
     public void onCreate(SQLiteDatabase db)
     {
         db.execSQL(
                 "create table if not exists PkmnEVs " +
                         "(UniqueID integer primary key, PkmnSpecies text, Owner text, Nickname text, HP integer, A integer, D integer, SA integer, SD integer, SP integer)"
         );
     }

     @Override
     public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
     {
         // TODO Auto-generated method stub
         db.execSQL("DROP TABLE IF EXISTS PkmnEVs");
         onCreate(db);
     }

     public int getNewID()
     {

         SQLiteDatabase db = this.getWritableDatabase();
         boolean exists = true;
         int uid = 0;
         uid = ((int) DatabaseUtils.queryNumEntries(db, EVS_TABLE_NAME) + 1);
         while(exists == true) {
             Cursor c = db.rawQuery(" select 1 from PkmnEVs where UniqueID = ?", new String[]{Integer.toString(uid)});
             exists = c.moveToFirst();
             c.close();
             if(exists == true)
                 uid++;
         }

         return uid;
 }

     public void resetEVs(int uid)
     {
         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues contentValues = new ContentValues();
         contentValues.put("HP", 0);
         contentValues.put("A", 0);
         contentValues.put("D", 0);
         contentValues.put("SA", 0);
         contentValues.put("SD", 0);
         contentValues.put("SP", 0);

         db.update("PkmnEVs", contentValues, "UniqueID = ? ", new String[]{Integer.toString(uid)});
     }

     public boolean newPkmn  (String id, String owner, String nickname, int uid)
     {
         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues contentValues = new ContentValues();
         contentValues.put("UniqueID", uid);
         contentValues.put("PkmnSpecies", id);
         contentValues.put("Owner", owner);
         contentValues.put("Nickname", nickname);
         db.insert("PkmnEVs", null, contentValues);
         return true;
     }

     public void updateEVs (String ev, int value, int uid)
     {
         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues contentValues = new ContentValues();
         contentValues.put(ev, value);
         db.update("PkmnEVs", contentValues, "UniqueID = ? ", new String[]{Integer.toString(uid)});
     }

     public Cursor getData(int id)
     {
         SQLiteDatabase db = this.getReadableDatabase();
         Cursor res =  db.rawQuery( "select * from PkmnEVs where UniqueID="+id+"", null );
         return res;
     }

     public ArrayList<String> getAllIDs()
     {
         ArrayList<String> ar = new ArrayList<String>();
         SQLiteDatabase db = this.getReadableDatabase();
         Cursor res =  db.rawQuery( "select * from PkmnEVs", null );
         res.moveToFirst();

         while(res.isAfterLast() == false){
             ar.add(res.getString(res.getColumnIndex(EVS_COLUMN_UNIQUEID)));
             res.moveToNext();
         }
         res.close();
         return ar;
     }
     public Integer deletePkmn (int uid)
     {
         SQLiteDatabase db = this.getWritableDatabase();
         return db.delete("PkmnEVs",
                 "UniqueID = ? ",
                 new String[] { Integer.toString(uid) });
     }


 }
