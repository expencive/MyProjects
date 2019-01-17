package com.gmail.expencive.loancalculator;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.expencive.loancalculator.Database.DatabaseHelper;


public class GraphPaymentsExperemental extends AppCompatActivity {
    Button btnSave;
    DatabaseHelper myDb;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_payments_experemental);

        myDb = new DatabaseHelper(this);


        LinearLayout linLayout = (LinearLayout) findViewById(R.id.linLayout);
        btnSave = (Button) findViewById(R.id.btnSave);

        LayoutInflater ltInflater = getLayoutInflater();

        double loanAmount = Double.parseDouble(getIntent().getStringExtra("mLoanAmount"));
        double interestRate = Double.parseDouble(getIntent().getStringExtra("mInterestRate"));
        double loanPeriod = Double.parseDouble(getIntent().getStringExtra("mLoanPeriod"));
        double r = interestRate / 1200;
        double r1 = Math.pow(r + 1, loanPeriod);

        double monthlyPayment = (double) ((r + (r / (r1 - 1))) * loanAmount);


        for (int i = 1; i <= loanPeriod; i++) {

            CardView item = (CardView) ltInflater.inflate(R.layout.item_cardview, linLayout, false);
            TextView tvNomer = (TextView) item.findViewById(R.id.tvNomer);
            tvNomer.setText("№: " + (String.valueOf(i)));

            double monthlyPercent = loanAmount * interestRate / 12 / 100;
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

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final EditText taskEditText = new EditText(GraphPaymentsExperemental.this);
                AlertDialog dialog = new AlertDialog.Builder(GraphPaymentsExperemental.this)
                        .setTitle("Название кредита")
                        .setView(taskEditText)
                        .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                boolean isInserted = myDb.insertData(taskEditText.getText().toString(), getIntent().getStringExtra("mLoanAmount"),
                                        getIntent().getStringExtra("mInterestRate"), getIntent().getStringExtra("mLoanPeriod"));
                                if(isInserted == true)
                                    Toast.makeText(GraphPaymentsExperemental.this,"Запись сохранена",Toast.LENGTH_LONG).show();
                                else
                                    Toast.makeText(GraphPaymentsExperemental.this,"Запись не сохранилась",Toast.LENGTH_LONG).show();


                            }
                        })

                        .setNegativeButton("Отмена", null)
                        .create();
                dialog.show();
                dialog.getButton(DialogInterface.BUTTON_POSITIVE)
                        .setTextColor(getResources().getColor(R.color.colorHoloGreenDark));
                dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
                        .setTextColor(getResources().getColor(R.color.colorAccent));
            }
        });
    }

}
