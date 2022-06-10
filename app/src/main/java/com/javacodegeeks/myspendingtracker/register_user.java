package com.javacodegeeks.myspendingtracker;

import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.List;
import android.app.Activity;

public class register_user extends Activity{
    Button btnRegister;
    EditText input1,input2,input3,input4;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        DatabaseHandler db = new DatabaseHandler(this);
        btnRegister = findViewById(R.id.button2);
        input1 = findViewById(R.id.editText);
        input2 = findViewById(R.id.editText2);
        input3 = findViewById(R.id.editText3);
        input4 = findViewById(R.id.editText4);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = input1.getText().toString();
                String password = input2.getText().toString();
                String email = input3.getText().toString();
                String value = input4.getText().toString();
                Double budget = Double.parseDouble(value);

                if(db.checkUser(name) == true) {
                    db.addUser(new Users(name, password, email, budget));
                    Toast.makeText(register_user.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), login_user.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(register_user.this, "Username has been existed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
