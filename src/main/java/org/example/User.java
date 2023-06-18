package org.example;

public class User
{
    private String name;
    private String password;
    private int login;
    private int id = 0;

    public User(String name, String password)
    {
        this.name = name;
        this.password = password;
        this.login=0;
        setId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLogin() {
        return login;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId() {
        this.id +=1;
    }
}

