package com.javacodegeeks.myspendingtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SpendingTracker";
    private static final String TABLE_USER = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_BUDGET = "budget";

    private static final String TABLE_CATEGORY = "category";
    private static final String CATEGORY_ID = "category_id";
    private static final String CATEGORY_NAME = "category_name";

    private static final String TABLE_EXPENSE = "expense";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Category table create query
        String CREATE_ITEM_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME + " TEXT,"+ COLUMN_PASSWORD + " TEXT,"+ COLUMN_EMAIL + " TEXT," + COLUMN_BUDGET + " DOUBLE" +")";
        db.execSQL(CREATE_ITEM_TABLE);

        String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "("
                + CATEGORY_ID + " INTEGER PRIMARY KEY," + CATEGORY_NAME + " TEXT"+")";
        db.execSQL(CREATE_CATEGORY_TABLE);

        String query = "CREATE TABLE expense(expense_id INTEGER PRIMARY KEY AUTOINCREMENT, expense_amount DOUBLE, expense_note TEXT, expense_date TEXT,category_name TEXT, username TEXT, FOREIGN KEY(category_name) REFERENCES category(category_name))";
        db.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
        // Create tables again
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
        onCreate(db);
    }
    public void open()
    {
        SQLiteDatabase db = this.getWritableDatabase();
    }

    void addUser(Users user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName()); // Name
        values.put(COLUMN_PASSWORD, user.getPassword()); //Password
        values.put(COLUMN_EMAIL, user.getEmail());// Email
        values.put(COLUMN_BUDGET, user.getBudget());// Budget

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }
    Users getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER, new String[] { COLUMN_ID,
                        COLUMN_NAME, COLUMN_PASSWORD, COLUMN_EMAIL,COLUMN_BUDGET}, COLUMN_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Users users = new Users(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getDouble(4));
        // return contact
        return users;
    }
    Users getUserProfile(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER, new String[] { COLUMN_ID,
                        COLUMN_NAME, COLUMN_PASSWORD, COLUMN_EMAIL, COLUMN_BUDGET}, COLUMN_NAME + "=?",
                new String[] { String.valueOf(username) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Users users = new Users(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getDouble(4));
        // return contact
        return users;
    }
    public List<Users> getAllUsers() {
        List<Users> contactList = new ArrayList<Users>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Users users = new Users();
                users.setID(Integer.parseInt(cursor.getString(0)));
                users.setName(cursor.getString(1));
                users.setPassword(cursor.getString(2));
                users.setEmail(cursor.getString(3));
                users.setBudget(cursor.getDouble(4));
                // Adding contact to list
                contactList.add(users);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }
    public boolean Login(String username, String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE name=? AND password=?", new String[]{username, password});
        if (mCursor != null) {
            if(mCursor.getCount() > 0)
            {
                return true;
            }
        }
        return false;
    }

    public boolean checkUser(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE name=?", new String[]{name});
        if (mCursor != null) {
            if(mCursor.getCount() > 0)
            {
                return false;
            }
        }
        return true;
    }

    public int getUserID(String username, String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT id FROM " + TABLE_USER + " WHERE name=? AND password=?", new String[]{username, password});
        if (mCursor != null) {
            int userid = mCursor.getInt(0);
            return userid;
        }
        return 0;
    }

    public double displayBudget(String name, String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE name=? AND password=?", new String[]{name, password});

        //Double budget = result.getDouble(4);
        if (result != null) {
            result.moveToFirst();
            Double finalBudget = result.getDouble(4);
            return finalBudget;
        }
        return 0;

    }
    public int updateUser(Users users){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id",users.getID());
        values.put("name",users.getName());
        values.put("password",users.getPassword());
        values.put("email",users.getEmail());
        values.put("budget",users.getBudget());

        ContentValues values2 = new ContentValues();
        Expenses expenses = new Expenses();
        values2.put("username",expenses.getUsername());

        // updating row
        return db.update(TABLE_USER, values, "id" + " = ?",
                new String[] { String.valueOf(users.getID())});
    }
    /*public int updateUsernameExpense(String username){
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        Expenses expenses = new Expenses();
        values.put("username",expenses.getUsername());

        // updating row
        return db.update(TABLE_EXPENSE, values, "username" + " = ?",
                new String[] { username});
    }

     */






    void addCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(CATEGORY_NAME, category.getName()); // Category Name

        // Inserting Row
        db.insert(TABLE_CATEGORY, null, value);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    Category getCategory(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CATEGORY, new String[] { COLUMN_ID,
                        COLUMN_NAME}, COLUMN_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Category category = new Category(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
        // return contact
        return category;
    }
    public boolean checkCategory(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_CATEGORY,null );
        if (mCursor != null) {
            if(mCursor.getCount() > 0)
            {
                return true;
            }
        }
        return false;
    }

    public List<Category> getAllCategory() {
        List<Category> categoryList = new ArrayList<Category>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.setID(Integer.parseInt(cursor.getString(0)));
                category.setName(cursor.getString(1));
                // Adding contact to list
                categoryList.add(category);
            } while (cursor.moveToNext());
        }

        // return contact list
        return categoryList;
    }
    public List getCategoryNames(){
        List categoryList = new ArrayList();
        String selectQuery = "SELECT category_name FROM " + TABLE_CATEGORY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.setName(cursor.getString(0));
                String catName = category.getName();
                // Adding contact to list
                categoryList.add(catName);
            } while (cursor.moveToNext());
        }

        // return contact list
        return categoryList;
    }





    void addExpense(Expenses exp) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put("expense_amount", exp.getAmount()); // Category Name
        value.put("expense_note",exp.getNote());
        value.put("expense_date",exp.getDate());
        value.put("category_name",exp.getCategoryName());
        value.put("username",exp.getUsername());

        // Inserting Row
        db.insert(TABLE_EXPENSE, null, value);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    public List<Expenses> getAllExpenses() {
        List<Expenses> expenseList = new ArrayList<Expenses>();
        // Select All Query

        String selectQuery = "SELECT  * FROM " + TABLE_EXPENSE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery(selectQuery, null);


        // looping through all rows and adding to list
        if (result.moveToFirst()) {
            while (result.moveToNext()){
                Expenses exp = new Expenses();
                exp.setExpenseID(Integer.parseInt(result.getString(0)));
                exp.setAmount(result.getDouble(1));
                exp.setNote(result.getString(2));
                exp.setDate(result.getString(3));
                exp.setCategoryName(result.getString(4));
                exp.setUsername(result.getString(5));
                // Adding contact to list
                expenseList.add(exp);
            }
        }

        // return contact list
        return expenseList;
    }
    public List getExpensesName(String name){
        List expensesList = new ArrayList();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT expense_note, expense_date, expense_amount FROM " + TABLE_EXPENSE + " WHERE username=?", new String[]{name});

        if (cursor.moveToFirst()) {
            do {
                Expenses expenses = new Expenses();
                expenses.setNote(cursor.getString(0));
                String expName = cursor.getString(0) + " | Date: " + cursor.getString(1)+ "\n | Amount: RM" + cursor.getDouble(2);

                // Adding contact to list
                expensesList.add(expName);
            } while (cursor.moveToNext());
        }

        // return contact list
        return expensesList;
    }
    public List getExpensesName2(String username,String catName){
        List expensesList = new ArrayList();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_EXPENSE + " WHERE username=? AND category_name=?", new String[]{username,catName});


        while (result.moveToNext()){
            Expenses exp = new Expenses();
            exp.setExpenseID(Integer.parseInt(result.getString(0)));
            exp.setAmount(result.getDouble(1));
            exp.setNote(result.getString(2));
            exp.setDate(result.getString(3));
            exp.setCategoryName(result.getString(4));
            exp.setUsername(result.getString(5));
            // Adding contact to list
            expensesList.add(exp);
        }

        return expensesList;
    }
    public List<Expenses> getExpensesName3(String name){
        List<Expenses> expenseList = new ArrayList<Expenses>();
        // Select All Query

        //String selectQuery = "SELECT  * FROM " + TABLE_EXPENSE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_EXPENSE + " WHERE username=?", new String[]{name});


        // looping through all rows and adding to list
        while (result.moveToNext()){
            Expenses exp = new Expenses();
            exp.setExpenseID(Integer.parseInt(result.getString(0)));
            exp.setAmount(result.getDouble(1));
            exp.setNote(result.getString(2));
            exp.setDate(result.getString(3));
            exp.setCategoryName(result.getString(4));
            exp.setUsername(result.getString(5));
            // Adding contact to list
            expenseList.add(exp);
        }

        // return contact list
        return expenseList;
    }

    Expenses getExpense(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor2 = db.query(TABLE_EXPENSE, new String[] { "expense_id",
                        "expense_amount", "expense_note", "expense_date", "category_name", "username"}, "expense_id" + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EXPENSE + " WHERE expense_id=?", new String[]{String.valueOf(id)});
        if (cursor != null)
            cursor.moveToFirst();

        Expenses expenses = new Expenses(cursor.getString(0),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getDouble(4));
        // return contact
        return expenses;
    }

    public Double sumAllExpense(String name){
        Double sum = 0.0;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT expense_amount FROM " + TABLE_EXPENSE + " WHERE username=?", new String[]{name});

        while (result.moveToNext()){
            Expenses exp = new Expenses();
            exp.setAmount(result.getDouble(0));
            sum = sum + result.getDouble(0);
        }
        return sum;

    }

    public int updateExpense(Expenses expenses) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("expense_amount", expenses.getAmount());
        values.put("expense_note", expenses.getNote());
        values.put("category_name",expenses.getCategoryName());
        values.put("expense_date",expenses.getDate());

        // updating row
        return db.update(TABLE_EXPENSE, values, "expense_id" + " = ?",
                new String[] { String.valueOf(expenses.getExpenseID())});
    }
    public void deleteExpense(Expenses expenses) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EXPENSE, "expense_id" + " = ?",
                new String[] { String.valueOf(expenses.getExpenseID()) });
        db.close();
    }



}
