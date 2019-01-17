package com.gmail.expencive.loancalculator;

import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GraphPayments extends AppCompatActivity {


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_payments);

        LinearLayout linLayout = (LinearLayout) findViewById(R.id.linLayout);

        LayoutInflater ltInflater = getLayoutInflater();

        double loanAmount = Integer.parseInt(getIntent().getStringExtra("tvLoanAmount"));
        double interestRate = Integer.parseInt(getIntent().getStringExtra("tvInterestRate"));
        double loanPeriod = Integer.parseInt(getIntent().getStringExtra("tvLoanPeriod"));
        double r = interestRate/1200;
        double r1 = Math.pow(r+1,loanPeriod);

        double monthlyPayment = (double) ((r+(r/(r1-1))) * loanAmount);







        for (int i = 1; i <= loanPeriod; i++) {

            CardView item = (CardView) ltInflater.inflate(R.layout.item_cardview, linLayout, false);
            TextView tvNomer = (TextView) item.findViewById(R.id.tvNomer);
            tvNomer.setText("№: " + (String.valueOf(i)));

            double monthlyPercent = loanAmount * interestRate / 12 /100;
            TextView tvProcent = (TextView) item.findViewById(R.id.tvProcent);
            tvProcent.setText("Проценты: " + (String.valueOf(new DecimalFormat("##.##").format(monthlyPercent))));

            double osnDolg = monthlyPayment - monthlyPercent;
            TextView tvOsnDolg = (TextView) item.findViewById(R.id.tvOsnDolg);
            tvOsnDolg.setText("Осн. долг: " + (String.valueOf(new DecimalFormat("##.##").format(osnDolg))));

            TextView tvPayment = (TextView) item.findViewById(R.id.tvPayment);
            tvPayment.setText("Платеж: " + (String.valueOf(new DecimalFormat("##.##").format(monthlyPayment))));
            TextView tvOstatokDolga = (TextView) item.findViewById(R.id.tvOstatokDolga);
            tvOstatokDolga.setText("Остаток долга: " + (String.valueOf(new DecimalFormat("##.##").format(loanAmount))));
            loanAmount = loanAmount - osnDolg;

            linLayout.addView(item);
        }
    }
}