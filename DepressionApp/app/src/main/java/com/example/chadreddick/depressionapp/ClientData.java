package com.example.chadreddick.depressionapp;

/**
 * Created by chadreddick on 3/11/16.
 */
public class ClientData
{
    private int _id;
    private String _username;
    private String _password;

    public ClientData()
    {

    }

    public ClientData(int id, String username, String password)
    {
        _id = id;
        _username = username;
        _password = password;
    }

    public int getID()
    {
        return _id;
    }

    public void setID(int id)
    {
        _id = id;
    }

    public String getUsername()
    {
        return _username;
    }

    public void setUsername(String username)
    {
        _username = username;
    }

    public String getPassword()
    {
        return _password;
    }

    public void setPassword(String password)
    {
        _password = password;
    }
}
