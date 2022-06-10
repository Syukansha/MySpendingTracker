package com.javacodegeeks.myspendingtracker;

public class Expenses {
    int _expenseID;
    Double _amount;
    String _note, _date, _username,_categoryName;

    public Expenses(){}
    public Expenses(String username, int expenseID,String categoryName, String note, String date, Double amount){
        this._username = username;
        this._expenseID = expenseID;
        this._categoryName = categoryName;
        this._amount = amount;
        this._note = note;
        this._date = date;
    }
    public Expenses(String username,String categoryName, String note, String date, Double amount){
        this._username = username;
        this._categoryName = categoryName;
        this._amount = amount;
        this._note = note;
        this._date = date;
    }
    public String getUsername(){
        return this._username;
    }

    public void setUsername(String username){
        this._username = username;
    }

    public int getExpenseID(){
        return this._expenseID;
    }

    public void setExpenseID(int expenseID){
        this._expenseID = expenseID;
    }

    public String getCategoryName(){
        return this._categoryName;
    }

    public void setCategoryName(String categoryName){
        this._categoryName = categoryName;
    }

    public String getNote(){
        return this._note;
    }

    public void setNote(String note){
        this._note = note;
    }

    public String getDate(){
        return this._date;
    }

    public void setDate(String date){
        this._date = date;
    }

    public Double getAmount(){
        return this._amount;
    }

    public void setAmount(Double amount){
        this._amount = amount;
    }
}
