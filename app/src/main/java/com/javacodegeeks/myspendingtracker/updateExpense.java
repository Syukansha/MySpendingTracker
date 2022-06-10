package com.javacodegeeks.myspendingtracker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class updateExpense extends Activity {
    private int mYear, mMonth, mDay;
    private Button update;

    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String NAME_KEY = "NAME_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";

    public static final String EXPENSE_ID_KEY = "expense_id_key";

    SharedPreferences sharedpreferences;
    String name, password, expenseID;
    Spinner spinner;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_expense);
        DatabaseHandler db = new DatabaseHandler(this);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        name = sharedpreferences.getString(NAME_KEY, null);
        password = sharedpreferences.getString(PASSWORD_KEY, null);
        expenseID = sharedpreferences.getString(EXPENSE_ID_KEY, null);
        spinner = findViewById(R.id.spinner);
        loadSpinnerData();


        TextView noteInput = (TextView) findViewById(R.id.editNote);
        TextView dateInput = (TextView) findViewById(R.id.date2);
        TextView amountInput = (TextView) findViewById(R.id.editAmount2);
        Expenses exp = db.getExpense(Integer.parseInt(expenseID));
        noteInput.setText(exp.getNote());
        dateInput.setText(exp.getDate());
        String amount = String.valueOf(exp.getAmount());
        amountInput.setText(amount);



        /*List catList = db.getCategoryNames();
        int index = 0;
        for(int i = 0; i < catList.size(); i++){
            if(catList.get(i).equals(exp.getCategoryName())){
                index=i;
                spinner.setSelection(i);
            }
        }

         */


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

        Button pickDate = (Button) findViewById(R.id.pick_date);
        TextView textView = (TextView) findViewById(R.id.date2);
        EditText inputAmount = findViewById(R.id.editAmount);
        EditText inputNote = findViewById(R.id.editNote);

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
                DatePickerDialog dpd = new DatePickerDialog(updateExpense.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox

                                if (year < mYear)
                                    view.updateDate(mYear, mMonth, mDay);

                                if (monthOfYear < mMonth && year == mYear)
                                    view.updateDate(mYear, mMonth, mDay);

                                if (dayOfMonth < mDay && year == mYear && monthOfYear == mMonth)
                                    view.updateDate(mYear, mMonth, mDay);

                                textView.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                dpd.getDatePicker().setMinDate(System.currentTimeMillis());
                dpd.show();

            }
        });
        update  = (Button) findViewById(R.id.updateExpense);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView noteInput = (TextView) findViewById(R.id.editNote);
                TextView dateInput = (TextView) findViewById(R.id.date2);
                TextView amountInput = (TextView) findViewById(R.id.editAmount2);
                String note = noteInput.getText().toString();
                String date = dateInput.getText().toString();
                String amount = amountInput.getText().toString();
                Double newAmount = Double.valueOf(amount);
                String catSpinner = spinner.getSelectedItem().toString();
                db.updateExpense(new Expenses(name,Integer.parseInt(expenseID),catSpinner,note,date,newAmount));
                Toast.makeText(getApplicationContext(),"Successfully update Expense", Toast.LENGTH_LONG).show();

            }
        });

        Button delete = (Button) findViewById(R.id.deleteExpense);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView noteInput = (TextView) findViewById(R.id.editNote);
                TextView dateInput = (TextView) findViewById(R.id.date2);
                TextView amountInput = (TextView) findViewById(R.id.editAmount2);
                String note = noteInput.getText().toString();
                String date = dateInput.getText().toString();
                String amount = amountInput.getText().toString();
                Double newAmount = Double.valueOf(amount);
                String catSpinner = spinner.getSelectedItem().toString();

                db.deleteExpense(new Expenses(name,Integer.parseInt(expenseID),catSpinner,note,date,newAmount));
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                Toast.makeText(updateExpense.this,"Successfully Add Expense", Toast.LENGTH_LONG).show();

            }
        });

    }

    private void loadSpinnerData() {
        DatabaseHandler db = new DatabaseHandler(this);
        List categories = new ArrayList();
        List catList = db.getCategoryNames();


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, catList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner

        spinner.setAdapter(dataAdapter);


    }

}

