package com.example.project_test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class PointDBHandler extends SQLiteOpenHelper {
    private static final int DB_Version = 1;
    private static final String DB_Name = "Point.db";
    private Context c;
    public PointDBHandler(Context context) {
        super(context,DB_Name,null,DB_Version);
        c = context;
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Boolean crtable(String uname,String gamename, int n_players)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();

        String tbname = uname+"_"+gamename;

        String query = "create Table "+tbname+"(round_num INTEGER";

        for(int i=0;i<n_players;i++)
        {
            query = query + ",player"+(i+1)+" INTEGER";
        }

        query += ")";

        GameDBHandler gdb = new GameDBHandler(c);


        ContentValues contentValues= new ContentValues();
        contentValues.put("gamename",gamename);

        Boolean chkgamename = gdb.checkgamename(gamename);
        if(chkgamename==false)
        {
            gdb.insertData(gamename,uname);
            System.out.println("Query : "+query);
            MyDB.execSQL(query);
            return true;
        }
        else
        {
            System.out.println("Exists Mate");
            return false;
        }

    }



    public void insertData(String username, String gamename, ArrayList<Integer> points){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("round_num",colnum(username,gamename));
        for(int i=0;i<points.size();i++)
        {
            //System.out.println("Player "+(i+1)+" : "+points.get(i));
            contentValues.put("player"+(i+1),points.get(i));
        }
        long result = MyDB.insert(username+"_"+gamename, null, contentValues);

        disp_data(username,gamename);

    }

    public ArrayList<ArrayList<Integer>> disp_data(String username, String gamename)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM "+username+"_"+gamename;
        Cursor c = db.rawQuery(Query, null);

        int numcol = c.getColumnCount();

        ArrayList<ArrayList<Integer> > aList =new ArrayList<ArrayList<Integer> >();


        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                ArrayList<Integer> round_points = new ArrayList<Integer>();
                for(int i=0;i<numcol;i++)
                {
                    round_points.add(c.getInt(i));
                }
                aList.add(round_points);
                c.moveToNext();
            }
        }

        ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();

        int C = aList.get(0).size();
        int R = aList.size();
        for (int col = 0 ; col != C ; col++) {
            ArrayList<Integer> a4 = new ArrayList<Integer>();
            for (int r = 0 ; r < R ; r++) {
                a4.add(aList.get(r).get(col));
            }
            res.add(a4);


        }

        return res;
    }

    private int colnum(String username, String gamename)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM "+username+"_"+gamename;
        Cursor c = db.rawQuery(Query, null);
        int num =0;
        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
               num++;
                c.moveToNext();
            }
        }
        return num;

    }

    public void tabledroper(String username, String gamename)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "DROP TABLE IF EXISTS "+username+"_"+gamename;
        db.execSQL(Query);
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
