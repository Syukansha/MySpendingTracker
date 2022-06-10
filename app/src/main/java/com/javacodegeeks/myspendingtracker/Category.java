package com.javacodegeeks.myspendingtracker;

public class Category {
    int _id;
    String _name;
    public Category(){}
    public Category(int id, String name){
        this._id = id;
        this._name = name;

    }
    public Category(String name){
        this._name = name;
    }
    public int getID(){return this._id;}
    public void setID(int id){
        this._id = id;
    }
    public String getName(){return this._name;}
    public void setName(String name){this._name=name;}
}
