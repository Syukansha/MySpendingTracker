package com.javacodegeeks.myspendingtracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;


public class FourthFragment extends Fragment {
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String NAME_KEY = "NAME_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";

    public static final String USERID_KEY = "userID_key";

    SharedPreferences sharedpreferences;
    String name,password;
    TextView username,email,budget;
    Button update,logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fourth, container, false);
        sharedpreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        name = sharedpreferences.getString(NAME_KEY, null);
        password = sharedpreferences.getString(PASSWORD_KEY, null);
        DatabaseHandler db = new DatabaseHandler(getActivity());

        username = (TextView) view.findViewById(R.id.profileName);
        email = (TextView) view.findViewById(R.id.profileEmail);
        budget = (TextView) view.findViewById(R.id.profileBudget);

        Users users = db.getUserProfile(name);

        username.setText(users.getName());
        email.setText(users.getEmail());
        budget.setText(String.valueOf(users.getBudget()));
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(USERID_KEY, String.valueOf(users.getID()));
        editor.apply();

        update = (Button) view.findViewById(R.id.updateProfile);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(getActivity(), UpdateUserProfile.class);
                startActivity(j);
                /*Fragment fragment = new FifthFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //fragmentTransaction.replace(R.id.frameAccount, new UpdateProfileFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                 */
            }
        });

        logout = (Button) view.findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getActivity().getSharedPreferences("loginPrefs",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();

                editor.apply();
                
                Intent j = new Intent(getActivity(), login_user.class);
                startActivity(j);
                getActivity().finish();
            }
        });


        return view;
    }
}