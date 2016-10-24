package com.example.administrator.day27project.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.administrator.day27project.utils.L;


/**
 * Created by Administrator on 2016/10/24.
 */
public class DBOpenHelper extends SQLiteOpenHelper{
    private static final String DB_NAME="fls.db";
    private static final String FULICENTER_CREATE="create table "
            +UserDao.USER_TABLE_NAME+" ("
            +UserDao.USER_NAME+" text primary key, "
            +UserDao.USER_NICK+" text, "
            +UserDao.USER_AVATAR_ID+" integer, "
            +UserDao.USER_AVATAR_TYPE+" integer, "
            +UserDao.USER_AVATAR_PATH+" text, "
            +UserDao.USER_AVATAR_SUFFIX+" text, "
            +UserDao.USER_AVATAR_LASTUPDATE_TIME+" text);";

    private static DBOpenHelper mHelper;

    public static DBOpenHelper onInit(Context context){
        if(mHelper==null){
            mHelper=new DBOpenHelper(context.getApplicationContext());
            L.e("onInit");
        }
        return mHelper;
    }


    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        L.e("DBCREATE1");
        db.execSQL(FULICENTER_CREATE);
        L.e("DBCREATE2");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void closeDB(){
        if(mHelper!=null){
            SQLiteDatabase db = getWritableDatabase();
            db.close();
            mHelper=null;
        }
    }
}
