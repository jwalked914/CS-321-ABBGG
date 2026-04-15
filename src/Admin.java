import java.util.ArrayList;
public class Admin extends User
{

    public Admin(String username, String password, ArrayList<UserCollection> collections)
    {
        super(username,password,true,collections);
    }
  
    public void deleteUser(UserDatabase userDB, String username)
    {
        if (!getIsAdmin()) throw new SecurityException("Not an admin");
        userDB.deleteUser(username);
    }

  
    
}
