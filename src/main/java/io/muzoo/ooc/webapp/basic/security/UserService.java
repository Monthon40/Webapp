package io.muzoo.ooc.webapp.basic.security;

import java.util.HashMap;
import java.util.Map;

public class UserService {

    public Map<String, User> users = new HashMap<>();
    {
        users.put("strickwar", new User("strickwar", "12345"));
        users.put("admin", new User("admin","12345"));
    }
    public Map getUser(){
        return users;
    }


    public User findByUsername(String username) {
        return users.get(username);
    }

    public boolean checkIfUserExists(String username){
        return users.containsKey(username);
    }
}
