package org.example;

import java.util.ArrayList;
import java.util.List;

public class UserManager
{
    private List<User> users;
    private int control=0;

    public UserManager() {
        this.users = new ArrayList<>();
    }

    public void addUser(User user)
    {
        this.users.add(user);
    }

    public void esempioUtenti()
    {
        this.users.add(new User("riccardo", "1234"));
        this.users.add(new User("Pippo", "1234"));
    }

    public List viewUser()
    {
        return this.users;
    }

    public int getControl() {
        return control;
    }

    public void setControl(int control) {
        this.control = control;
    }

    public void exitLogin()
    {
        for (User user: users
        ) {
            if(user.getId()==getControl())
            {
                if(user.getLogin()==1)
                {
                    user.setLogin(0);
                }
            }
        }
    }
    public boolean verifyAction()
    {
        for (User user: users
        ) {
            if(user.getId()==getControl())
            {
                if(user.getLogin()==1)
                {
                    return true;
                }
            }
        }
        return false;
    }


    public boolean verify(String name, String password)
    {
        for (User user: users
        ) {
            if(user.getName().equals(name) && user.getPassword().equals(password))
            {
                setControl(user.getId());
                user.setLogin(1);
                return true;
            }
        }
        return false;
    }
}

