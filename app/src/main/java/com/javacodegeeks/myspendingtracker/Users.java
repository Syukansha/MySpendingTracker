package com.javacodegeeks.myspendingtracker;

public class Users {
    int _id;
    String _name;
    String _password;
    String _email;
    Double _budget;

    public Users(){}
    public Users(int id, String name, String password, String email, Double budget){
        this._id = id;
        this._name = name;
        this._password = password;
        this._email = email;
        this._budget = budget;
    }
    public Users(String name, String password, String email, Double budget){
        this._name = name;
        this._password = password;
        this._email = email;
        this._budget = budget;
    }
    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }

    public String getName(){
        return this._name;
    }

    public void setName(String name){
        this._name = name;
    }

    public String getPassword(){
        return this._password;
    }

    public void setPassword(String password){
        this._password = password;
    }

    public String getEmail(){
        return this._email;
    }

    public void setEmail(String email){
        this._email = email;
    }

    public Double getBudget(){
        return this._budget;
    }

    public void setBudget(Double budget){
        this._budget = budget;
    }


}
