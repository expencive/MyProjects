package com.gmail.expencive.loancalculator;


import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.expencive.loancalculator.Database.DatabaseHelper;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    public static String EXTRA_LOAN_AMOUNT = "mLoanAmount";
    public static String EXTRA_INTEREST_RATE = "mInterestRate";
    public static String EXTRA_LOAN_PERIOD = "mLoanPeriod";

    private TextInputLayout mLoanAmount, mInterestRate, mLoanPeriod;
    TextView mMontlyPaymentResult, mTotalPaymentsResult, mTotalOverpayResult;
    DatabaseHelper myDb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);


        mLoanAmount = findViewById(R.id.text_input_loan_amount);
        mInterestRate = findViewById(R.id.text_input_interest_rate);
        mLoanPeriod = findViewById(R.id.text_input_loan_period);
        mMontlyPaymentResult = (TextView)findViewById(R.id.monthly_payment_result);
        mTotalPaymentsResult = (TextView)findViewById(R.id.total_payments_result);
        mTotalOverpayResult = (TextView) findViewById(R.id.total_overpay_result);

    }

    private boolean validateLoanAmount() {
        String loanAmountInput = mLoanAmount.getEditText().getText().toString().trim();

        if (loanAmountInput.isEmpty()) {
            mLoanAmount.setError("Заполните строку");
            return false;
        } else {
            mLoanAmount.setError(null);
            return true;
        }
    }

    private boolean validateInterestRate() {
        String interestRateInput = mInterestRate.getEditText().getText().toString().trim();

        if (interestRateInput.isEmpty()) {
            mInterestRate.setError("Заполните строку");
            return false;
        } else {
            mInterestRate.setError(null);
            return true;
        }
    }

    private boolean validateLoanPeriod() {
        String loanPeriodInput = mLoanPeriod.getEditText().getText().toString().trim();

        if (loanPeriodInput.isEmpty()) {
            mLoanPeriod.setError("Заполните строку");
            return false;
        } else {
            mLoanPeriod.setError(null);
            return true;
        }
    }

    public void confirmInput(View v) {
        if (!validateLoanAmount() | !validateInterestRate() | !validateLoanPeriod()) {
            return;
        }

        double loanAmount = Double.parseDouble(mLoanAmount.getEditText().getText().toString());
        double interestRate = Double.parseDouble(mInterestRate.getEditText().getText().toString());
        double loanPeriod = Double.parseDouble(mLoanPeriod.getEditText().getText().toString());
        double r = interestRate / 1200;
        double r1 = Math.pow(r + 1, loanPeriod);

        double monthlyPayment = (double) ((r + (r / (r1 - 1))) * loanAmount);
        double totalPayment = monthlyPayment * loanPeriod;
        double totalOverpayResult = totalPayment - loanAmount;

        mMontlyPaymentResult.setText("Месячный платеж: " + (new DecimalFormat("##.##").format(monthlyPayment)));
        mTotalPaymentsResult.setText("Суммарный платеж: " + (new DecimalFormat("##.##").format(totalPayment)));
        mTotalOverpayResult.setText("Сумма переплаты: " + (new DecimalFormat("##.##").format(totalOverpayResult)));

        Button buttonGraphPayment = findViewById(R.id.button_graphPayment);

        buttonGraphPayment.setVisibility(View.VISIBLE);

    }

    public void onClickGraph(View clickedButton) {
        if (!validateLoanAmount() | !validateInterestRate() | !validateLoanPeriod()) {
            return;
        }

        String et1 = mLoanAmount.getEditText().getText().toString();
        String et2 = mInterestRate.getEditText().getText().toString();
        String et3 = mLoanPeriod.getEditText().getText().toString();

        Intent intent = new Intent(this, GraphPaymentsExperemental.class);
        intent.putExtra(EXTRA_LOAN_AMOUNT, et1);
        intent.putExtra(EXTRA_INTEREST_RATE, et2);
        intent.putExtra(EXTRA_LOAN_PERIOD, et3);
        startActivity(intent);

    }

    public void onClickSavedCredits(View v) {
        Intent intent = new Intent(this, SavedCreditsList.class);
        startActivity(intent);


    }


}