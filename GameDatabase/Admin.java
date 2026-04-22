package model;

import data.UserDatabase;

import java.util.ArrayList;

public class Admin extends User
{
    public Admin(String username, String password)
    {
        super(username,password,true, new ArrayList<>(),new ArrayList<>());
    }

    public void deleteUser(String username, UserDatabase userDB)
    {
        userDB.deleteUser(username);
    }

    public void giveAdmin(String username, Boolean isAdmin,UserDatabase userDB)
    {
        userDB.setAdmin(username,true);
    }
}
