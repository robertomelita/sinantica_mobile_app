package com.example.lunares;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "Imagenes.db";
    private static final String TABLE_NAME = "images";


    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+ TABLE_NAME +" (id TEXT PRIMARY KEY, password TEXT );");
        Log.e("asd","CRADA BASE DE DATOS DE NUEVO");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //SQLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        //onCreate(SQLiteDatabase);
    }

    public void insertKey(String id, String password){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("password",password);
        contentValues.put("id",id);


        db.insert(TABLE_NAME,null,contentValues);
    }

    public ArrayList<Img> selectAll(){
        ArrayList<Img> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query(TABLE_NAME,null,null,null,null,null,null);

        while(c.moveToNext()){
            String id = c.getString(c.getColumnIndex("id"));
            String password = c.getString(c.getColumnIndex("password"));

            list.add(new Img(id,password));
        }
        return list;
    }
    public void remove(Img img){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME,"id = '" + img.getId() + "';",null);
    }
}
