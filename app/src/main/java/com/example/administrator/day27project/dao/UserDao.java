package com.example.administrator.day27project.dao;


import android.content.Context;

import com.example.administrator.day27project.bean.UserAvatar;
import com.example.administrator.day27project.utils.L;


public class UserDao {
     static final String USER_TABLE_NAME="t_fulicenter_user";
     static final String USER_NAME="m_user_name";
    static final String USER_NICK="m_user_nick";
     static final String USER_AVATAR_ID="m_user_avatar_id";
     static final String USER_AVATAR_PATH="m_user_avatar_path";
     static final String USER_AVATAR_SUFFIX="m_user_avatar_suffix";
     static final String USER_AVATAR_TYPE="m_user_avatar_type";
     static final String USER_AVATAR_LASTUPDATE_TIME="m_user_avatar_lastupdate_time";

    public UserDao(Context context){
         DBManager.getInstance().onInit(context);
    }

    public boolean savaUser(UserAvatar user){
        L.e("DBUserDao");
        return DBManager.getInstance().savaUser(user);
    }

    public  UserAvatar getUser(String username){
        return DBManager.getInstance().getUser(username);
    }
    public boolean updateUser(UserAvatar user){
        return DBManager.getInstance().updateUser(user);
    }

}
