package com.example.administrator.day27project.dao;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/10/24.
 */
public class SharePrefrenceUtils {
    private static SharePrefrenceUtils instance;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private static final String SHARE_NAME="saveuserInfo";
    private static final String SHARE_KEY_UER_NAME="share_key_user_name";

   public  SharePrefrenceUtils(Context context){
       mSharedPreferences = context.getSharedPreferences(SHARE_NAME, context.MODE_PRIVATE);
       mEditor=mSharedPreferences.edit();
   }
    public static SharePrefrenceUtils getInstance(Context context){
        if(instance==null){
            instance=new SharePrefrenceUtils(context);
        }
        return instance;
    }
  public void saveUser(String username){
      mEditor.putString(SHARE_KEY_UER_NAME,username);
      mEditor.commit();
  }
    public String getUser(){
        return mSharedPreferences.getString(SHARE_KEY_UER_NAME,null);

    }
    public void removeUser(){
        mEditor.remove(SHARE_KEY_UER_NAME);
        mEditor.commit();
    }

}
