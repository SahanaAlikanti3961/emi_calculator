//<<< NAME: SAHANA ALIKANTI >>>
//<<< COURSE: MOBILE PROGRAMMING ASSIGNMENT 1 >>>
//<<< PROJECT: EMI CALCULATOR >>>


package com.example.sahan.emicalculator;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.IntRange;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import static java.lang.Float.parseFloat;

public class MainActivity extends AppCompatActivity {

    //VIEW OBJECTS FOR UI REFERENCE
    private EditText LoanAmount;
    private EditText InterestRate;
    private TextView In5Y;
    private TextView In10Y;
    private TextView In15Y;
    private TextView In20Y;
    private TextView In25Y;
    private TextView In30Y;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        //REFERENCES FOR OUTPUT ELEMENTS
        In5Y = (TextView) findViewById(R.id.textView7);
        In10Y = (TextView) findViewById(R.id.textView8);
        In15Y = (TextView) findViewById(R.id.textView9);
        In20Y = (TextView) findViewById(R.id.textView10);
        In25Y = (TextView) findViewById(R.id.textView11);
        In30Y = (TextView) findViewById(R.id.textView12);

        //REFERENCES FOR INPUT ELEMENTS
        LoanAmount = (EditText) findViewById(R.id.editText2);
        InterestRate = (EditText) findViewById(R.id.editText1);

        //LISTENER FOR CHANGED TEXT IN INPUT
        LoanAmount.addTextChangedListener(TextWatcher1);
        InterestRate.addTextChangedListener(TextWatcher1);

    }

    protected android.text.TextWatcher TextWatcher1 = new TextWatcher() {
        //All THESE METHODS ARE EXECUTED WHEN TEXT IS CHANGED

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(!s.toString().matches(""))
                CalculateEMI();

        }

        @Override
        public void afterTextChanged(Editable s) {}

        public void CalculateEMI() {

            int Loan = Integer.parseInt(LoanAmount.getText().toString());

            //CATCHES NUMBER FORMAT EXCEPTION
            //IT ONLY ACCEPTS NUMBERS
            Double Rate;
            try {
                Rate = Double.parseDouble(InterestRate.getText().toString());
            } catch (NumberFormatException e) {
                Rate = 1.0;
            }

            //DECLARING ARRAY TO DISPLAY MONTHLY INSTALLMENTS
            double[] EMI = new double[10];

            //AS INTEREST RATE IS COMPOUNDED 12 TIMES IN A YEAR, IT IS DIVIDED BY 12
            Rate = (Rate / 1200);

            //CALCULATES FOR EVERY 5 YEARS - 5,10,15,20,25,30
            for(int l = 5;l<35;l+=5){

                double x = (Rate + 1);
                double y = 12 * l;
                double power = Math.pow(x, (y));

                if (power != 1) {

                    double Data =(Double) (Loan * Rate * (power)) / ((Double)((power) - 1));
                    int n=l/5;
                    EMI[n - 1] = Data;

                }
            }

            //DISPLAYS MONTHLY INSTALLMENTS IN OUTPUT TEXT FIELDS
            In5Y.setText("$" + EMI[0]);

            In10Y.setText("$" + EMI[1]);

            In15Y.setText("$" + EMI[2]);

            In20Y.setText("$" + EMI[3]);

            In25Y.setText("$" + EMI[4]);

            In30Y.setText("$" + EMI[5]);
        }


    };
};


