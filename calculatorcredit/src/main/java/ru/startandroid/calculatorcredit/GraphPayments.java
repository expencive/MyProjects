package ru.startandroid.calculatorcredit;

import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GraphPayments extends AppCompatActivity {

    String[] name = { "01.", "02.", "03.", "04.", "05.", "06.",
            "07.", "08." };
    String[] position = { "Основной долг: 19378", "Основной долг: 19000", "Основной долг: 18600",
            "Основной долг: 18000", "Основной долг: 17580", "Основной долг: 16300", "Основной долг: 1500", "Основной долг: 14590" };
    int salary[] = { 13000, 10000, 13000, 13000, 10000, 15000, 13000, 8000 };

    int[] colors = new int[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_payments);

        colors[0] = Color.parseColor("#FFECEDF6");
        colors[1] = Color.parseColor("#FFECEDF6");



        LinearLayout linLayout = (LinearLayout) findViewById(R.id.linLayout);

        LayoutInflater ltInflater = getLayoutInflater();

        double loanAmount = Integer.parseInt(getIntent().getStringExtra("mLoanAmount"));
        double interestRate = Integer.parseInt(getIntent().getStringExtra("mInterestRate"));
        double loanPeriod = Integer.parseInt(getIntent().getStringExtra("mLoanPeriod"));
        double r = interestRate/1200;
        double r1 = Math.pow(r+1,loanPeriod);

        double monthlyPayment = (double) ((r+(r/(r1-1))) * loanAmount);







        for (int i = 1; i <= loanPeriod; i++) {

            View item = ltInflater.inflate(R.layout.item, linLayout, false);
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
            item.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            item.setBackgroundColor(colors[i % 2]);
            linLayout.addView(item);
        }
    }
}
