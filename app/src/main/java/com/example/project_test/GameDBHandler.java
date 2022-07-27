package com.example.project_test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class GameDBHandler extends SQLiteOpenHelper {
    private static final int DB_Version = 1;
    private static final String DB_Name = "Games.db";
    public GameDBHandler(Context context) {
        super(context,DB_Name,null,DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {

        MyDB.execSQL("create Table games(GameName TEXT , username TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Boolean insertData(String gamename, String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("GameName", gamename);
        contentValues.put("username", username);
        long result = MyDB.insert("games", null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }

    public Boolean checkgamename(String gamename) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from games where GameName = ?", new String[]{gamename});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public ArrayList<String> dispdata(String username)
    {
        ArrayList<String> tabnames = new ArrayList<String>();

        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor c = MyDB.rawQuery("Select * from games where username = ?", new String[]{username});
        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                System.out.println(c.getString(0));
                tabnames.add(c.getString(0));
                c.moveToNext();
            }
        }
        return tabnames;

    }

    public Boolean deltablelink(String gamename)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("games","GameName=?",new String[]{gamename});

        return checkgamename(gamename);
    }

    public void vd()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name!='android_metadata'", null);

        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                System.out.println(c.getString(0));
                c.moveToNext();
            }
        }
    }


}
