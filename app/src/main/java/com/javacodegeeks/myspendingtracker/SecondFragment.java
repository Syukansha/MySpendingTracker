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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;


public class SecondFragment extends Fragment {
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String NAME_KEY = "NAME_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";

    public static final String EXPENSE_ID_KEY = "expense_id_key";

    public static final String USER_ID_KEY = "user_id_key";

    SharedPreferences sharedpreferences;
    String name,password;
    ListView expList ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        sharedpreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        name = sharedpreferences.getString(NAME_KEY, null);
        password = sharedpreferences.getString(PASSWORD_KEY, null);

        DatabaseHandler db = new DatabaseHandler(getActivity());
        List list = db.getExpensesName(name);
        List<Expenses> expensesList = db.getExpensesName3(name);
        List dummy = new ArrayList();
        List expID = new ArrayList();
        for(Expenses exp : expensesList){
            dummy.add("ID: "+exp.getExpenseID()+" | Note: "+ exp.getNote()+"\n Date: "+ exp.getDate()+" | Amount: RM"+ exp.getAmount());
            expID.add(exp.getExpenseID());
        }

        expList = (ListView) view.findViewById(R.id.simpleListView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.listview, R.id.expenseView,dummy);
        expList.setAdapter(arrayAdapter);

        expList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int listSize = dummy.size();

                for(int i=0;i<=dummy.size();i++){
                    if(position==i){
                        String exp_id = (String) String.valueOf(expID.get(i));

                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(EXPENSE_ID_KEY, exp_id);
                        editor.apply();

                        Intent j = new Intent(getActivity(), updateExpense.class);
                        startActivity(j);

                    }
                }

            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}