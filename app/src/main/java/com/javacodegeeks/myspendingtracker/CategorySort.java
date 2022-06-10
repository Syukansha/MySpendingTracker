package com.javacodegeeks.myspendingtracker;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;

public class CategorySort extends Activity{

    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String NAME_KEY = "NAME_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";

    public static final String EXPENSE_ID_KEY = "expense_id_key";

    public static final String CATEGORY_KEY = "category_key";

    SharedPreferences sharedpreferences;
    String name,password, categoryName;
    ListView catList ;
    TextView title;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_sort);
        DatabaseHandler db = new DatabaseHandler(this);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        name = sharedpreferences.getString(NAME_KEY, null);
        password = sharedpreferences.getString(PASSWORD_KEY, null);
        categoryName = sharedpreferences.getString(CATEGORY_KEY,null);

        title = (TextView) findViewById(R.id.sortCatg);
        title.setText(categoryName);

        List<Expenses> expensesList = db.getExpensesName2(name,categoryName);
        List dummy = new ArrayList();
        List expID = new ArrayList();
        for(Expenses exp : expensesList){
            dummy.add("ID: "+exp.getExpenseID()+" | Note: "+ exp.getNote()+"\n Date: "+ exp.getDate()+" | Amount: RM"+ exp.getAmount());
        }


        catList = (ListView) findViewById(R.id.categorySortList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.sorted_category, R.id.sortCategory,dummy);
        catList.setAdapter(arrayAdapter);


    }

}
