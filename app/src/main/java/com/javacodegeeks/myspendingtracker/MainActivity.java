package com.javacodegeeks.myspendingtracker;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.widget.Switch;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.widget.CompoundButton;
import androidx.appcompat.app.AppCompatDelegate;
import android.widget.Switch;



public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String NAME_KEY = "NAME_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";

    SharedPreferences sharedpreferences;
    String name,password;

    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHandler db = new DatabaseHandler(this);


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        bottomNavigationView.setSelectedItemId(R.id.home);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        name = sharedpreferences.getString(NAME_KEY, null);
        password = sharedpreferences.getString(PASSWORD_KEY, null);

        //Test DB
        /*Log.d("Insert: ", "Inserting ..");
        db.addUser(new Users("Ravi", "newpassword", "ravi@gmail.com"));
        db.addUser(new Users("Srinivas", "newpassword", "sri@gamil.com"));
        db.addUser(new Users("Tommy", "newpassword","tom@gamil.com"));
        db.addUser(new Users("Karthik", "newpassword","kar@gmail.com"));


        db.addCategory(new Category("Shopping"));
        db.addCategory(new Category("Clothes"));
        db.addCategory(new Category("Eating Out"));
        db.addCategory(new Category("Entertainment"));


        db.addExpense(new Expenses(name, "Shopping", "Duit sewa rumah", "6 January",500.0));

        db.addExpense(new Expenses(name,"Shopping","shopping", "5 Jan", 25.0));
        */
        if(db.checkCategory()==false){
            db.addCategory(new Category("Shopping"));
            db.addCategory(new Category("Clothes"));
            db.addCategory(new Category("Eating Out"));
            db.addCategory(new Category("Entertainment"));
        }
        
        Log.d("Reading: ", "Reading all Category..");
        List<Category> categories  = db.getAllCategory();
        for (Category catg : categories) {
            String log = "Id: " + catg.getID() + " ,Name: " + catg.getName();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }

        Log.d("Reading: ", "Reading all users..");
        List<Users> users = db.getAllUsers();
        for (Users us : users) {
            String log = "Id: " + us.getID() + " ,Name: " + us.getName() + " ,Password: " +
                    us.getPassword() + "Email: " + us.getEmail();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }

        Log.d("Reading: ", "Reading all expenses..");
        List<Expenses> expensesList = db.getAllExpenses();
        for (Expenses exp : expensesList) {
            String log = "Expense ID: " + exp.getExpenseID() + " ,Amount: " + exp.getAmount() + " ,Note: " +
                    exp.getNote() + " ,Date: " + exp.getDate() + " ,Category: " + exp.getCategoryName() + " ,User: " + exp.getUsername() ;
            // Writing Contacts to log
            Log.d("Name: ", log);
        }




    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.home:
                            selectedFragment = new FirstFragment();
                            break;
                        case R.id.transaction:
                            selectedFragment = new SecondFragment();
                            break;
                        case R.id.categories:
                            selectedFragment = new ThirdFragment();
                            break;
                        case R.id.account:
                            selectedFragment = new FourthFragment();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, selectedFragment).commit();

                    return true;

                }
            };
}
