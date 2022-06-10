package com.javacodegeeks.myspendingtracker;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.app.Activity;
import com.javacodegeeks.myspendingtracker.databinding.ActivityUpdateUserProfileBinding;
import android.widget.EditText;
import android.widget.Button;
import android.content.SharedPreferences;
import android.content.Context;
import android.widget.Toast;

public class UpdateUserProfile extends Activity {
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String NAME_KEY = "NAME_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";

    public static final String USERID_KEY = "userID_key";

    SharedPreferences sharedpreferences;
    String name,password,userID;
    EditText editName,editEmail,editBudget,editPassword;
    Button updateUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_user_profile);
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        name = sharedpreferences.getString(NAME_KEY, null);
        password = sharedpreferences.getString(PASSWORD_KEY, null);
        userID = sharedpreferences.getString(USERID_KEY, null);
        DatabaseHandler db = new DatabaseHandler(this);


        Users users = db.getUser(Integer.parseInt(userID));
        //editName = (EditText) findViewById(R.id.editProfileName);
        editEmail = (EditText) findViewById(R.id.editProfileEmail);
        editBudget = (EditText) findViewById(R.id.editProfileBudget);
        editPassword = (EditText) findViewById(R.id.editProfilePassword);

        //editName.setText(users.getName());
        editEmail.setText(users.getEmail());
        editBudget.setText(String.valueOf(users.getBudget()));
        editPassword.setText(users.getPassword());

        updateUser = (Button) findViewById(R.id.updateProfileButton);
        updateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String username = editName.getText().toString();
                String email = editEmail.getText().toString();
                String password = editPassword.getText().toString();
                Double budget = Double.valueOf(editBudget.getText().toString());
                int id = users.getID();


                db.updateUser(new Users(id,name,password,email,budget));
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(PASSWORD_KEY, password);
                //editor.putString(USER_ID_KEY, String.valueOf(userid));
                editor.apply();

                Toast.makeText(UpdateUserProfile.this,"Successfully Update Profile", Toast.LENGTH_LONG).show();

            }
        });

    }


}