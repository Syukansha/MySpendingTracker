package com.javacodegeeks.myspendingtracker;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewCategory extends Activity {
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String NAME_KEY = "NAME_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";

    SharedPreferences sharedpreferences;
    String name,password, categoryName;
    Button addCategory;
    EditText newCategory;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_category);
        DatabaseHandler db = new DatabaseHandler(this);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        name = sharedpreferences.getString(NAME_KEY, null);
        password = sharedpreferences.getString(PASSWORD_KEY, null);

        addCategory = (Button) findViewById(R.id.addCategory);
        newCategory = (EditText) findViewById(R.id.newCategory);

        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = newCategory.getText().toString();

                db.addCategory(new Category(category));
                Toast.makeText(AddNewCategory.this,"Successfully Add new Category", Toast.LENGTH_LONG).show();
            }
        });

    }
}
