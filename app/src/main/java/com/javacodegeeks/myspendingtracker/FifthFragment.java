package com.javacodegeeks.myspendingtracker;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import android.util.Log;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Spinner;
import android.widget.Toast;

public class FifthFragment extends Fragment {
    Spinner spinner;
    private String mParam1;
    private String mParam2;
    private int mYear,mMonth,mDay;
    private Button submit;

    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String NAME_KEY = "NAME_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";

    SharedPreferences sharedpreferences;
    String name,password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fifth, container, false);
        DatabaseHandler db = new DatabaseHandler(getActivity());
        spinner = view.findViewById(R.id.spinner);
        loadSpinnerData();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                String label = parent.getItemAtPosition(position).toString();

                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "You selected: " + label,
                        Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Inflate the layout for this fragment
        sharedpreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        name = sharedpreferences.getString(NAME_KEY, null);
        password = sharedpreferences.getString(PASSWORD_KEY, null);

        Button pickDate = (Button) view.findViewById(R.id.pick_date);
        TextView textView = (TextView) view.findViewById(R.id.date);
        EditText inputAmount = view.findViewById(R.id.editAmount);
        EditText inputNote = view.findViewById(R.id.editNote);


        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                // myCalendar.add(Calendar.DATE, 0);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                textView.setText(sdf.format(myCalendar.getTime()));
            }
        };
        pickDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);



                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox

                                if (year < mYear)
                                    view.updateDate(mYear,mMonth,mDay);

                                if (monthOfYear < mMonth && year == mYear)
                                    view.updateDate(mYear,mMonth,mDay);

                                if (dayOfMonth < mDay && year == mYear && monthOfYear == mMonth)
                                    view.updateDate(mYear,mMonth,mDay);

                                textView.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                dpd.getDatePicker().setMinDate(System.currentTimeMillis());
                dpd.show();

            }
        });



        //untuk direct submit button
        submit = view.findViewById(R.id.submitExpense);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newDate = textView.getText().toString();
                String catSpinner = spinner.getSelectedItem().toString();
                String amount = inputAmount.getText().toString();
                Double newAmount = Double.valueOf(amount);
                String newNote = inputNote.getText().toString();

                db.addExpense(new Expenses(name,catSpinner,newNote,newDate,newAmount));
                Toast.makeText(getActivity(),"Successfully Add Expense", Toast.LENGTH_LONG).show();
                textView.setText("");
                inputAmount.setText("");
                inputNote.setText("");

                /*Fragment fragment = new FifthFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameExpense, new FirstFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/
//                fragmentTransaction.remove(new update());
            }
        });


        return view;
    }
    private void loadSpinnerData() {
        DatabaseHandler db = new DatabaseHandler(getActivity());
        List categories = new ArrayList();
        List catList = db.getCategoryNames();


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, catList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }




}