package com.javacodegeeks.myspendingtracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;

public class FirstFragment extends Fragment {

    TextView budget;
    TextView text1;
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String NAME_KEY = "NAME_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";

    SharedPreferences sharedpreferences;
    String name,password;

    private Button expense;
    Boolean isOpen = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_first, container, false);
        // Inflate the layout for this fragment
        sharedpreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        name = sharedpreferences.getString(NAME_KEY, null);
        password = sharedpreferences.getString(PASSWORD_KEY, null);

        DatabaseHandler db = new DatabaseHandler(getActivity());
        budget = view.findViewById(R.id.outBudget);
        //text1 = view.findViewById(R.id.textView2);
        //budget.setText("welcome "+name);

        Double budget2 = db.displayBudget(name,password);
        Double expense2 = db.sumAllExpense(name);
        Log.d("Sum of expense: ", String.valueOf(expense2));


        budget.setText("RM "+budget2);
        TextView allExpenses = view.findViewById(R.id.outExpense);
        TextView userBal = view.findViewById(R.id.balance);
        allExpenses.setText("RM "+expense2);
        Double balance = budget2 - expense2;
        userBal.setText("RM "+balance);


        DatabaseHandler dbUser = new DatabaseHandler(getActivity());
        dbUser.open();

        expense = view.findViewById(R.id.btnExpense);

        expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new FifthFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.spendingFrame, new FifthFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
//                fragmentTransaction.remove(new update());
            }
        });

        return view;
    }


}