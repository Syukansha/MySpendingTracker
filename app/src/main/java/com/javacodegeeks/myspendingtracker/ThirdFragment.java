package com.javacodegeeks.myspendingtracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


public class ThirdFragment extends Fragment {
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String NAME_KEY = "NAME_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";

    public static final String EXPENSE_ID_KEY = "expense_id_key";

    public static final String CATEGORY_KEY = "category_key";

    SharedPreferences sharedpreferences;
    String name,password;
    ListView catList ;
    Button addCatg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        sharedpreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        name = sharedpreferences.getString(NAME_KEY, null);
        password = sharedpreferences.getString(PASSWORD_KEY, null);

        DatabaseHandler db = new DatabaseHandler(getActivity());
        List categoryList = db.getCategoryNames();

        catList = (ListView) view.findViewById(R.id.categoryView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.categorylist,
                R.id.textCategory,categoryList);
        catList.setAdapter(arrayAdapter);

        catList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int dummy = categoryList.size();
                for(int i=0;i<=dummy;i++){
                    if(position==i){
                        String catName = (String) String.valueOf(categoryList.get(i));

                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(CATEGORY_KEY, catName);
                        editor.apply();
                        Intent j = new Intent(getActivity(), CategorySort.class);
                        startActivity(j);
                    }
                }
            }
        });

        addCatg = (Button) view.findViewById(R.id.buttonAdd);
        addCatg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(getActivity(), AddNewCategory.class);
                startActivity(j);
            }
        });
        return view;
    }
}