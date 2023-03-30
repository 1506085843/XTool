package com.xtool.components.login;

import com.xtool.model.User;

public class LoginServer {

   public static boolean loginCheck(User user){
       if ("haitang".equals(user.getUser()) && "123456".equals(user.getPassword())) {
           return true;
       }
       return false;
    }
}
