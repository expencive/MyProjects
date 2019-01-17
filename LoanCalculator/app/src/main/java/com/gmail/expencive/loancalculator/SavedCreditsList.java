package com.gmail.expencive.loancalculator;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.expencive.loancalculator.Database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;


public class SavedCreditsList extends AppCompatActivity {
    DatabaseHelper myDb;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_credits_list);

        myDb = new DatabaseHelper(this);


        LinearLayout linLayout = (LinearLayout) findViewById(R.id.linLayout);
        LayoutInflater ltInflater = getLayoutInflater();


        //Создаем список
        Cursor res = myDb.getAllData();
        if(res.getCount() == 0) {
            // show message
            Toast.makeText(SavedCreditsList.this, "Список пуст", Toast.LENGTH_SHORT).show();
            return;
        }

        while (res.moveToNext()) {
            CardView itemCV = (CardView) ltInflater.inflate(R.layout.item_sc, linLayout, false);

            TextView tvTitle = (TextView) itemCV.findViewById(R.id.tvTitle);
            tvTitle.setText(res.getString(1));

            TextView tvLoanAmount = (TextView) itemCV.findViewById(R.id.tvLoanAmount);
            tvLoanAmount.setText(res.getString(2));

            TextView tvInterestRate = (TextView) itemCV.findViewById(R.id.tvInterestRate);
            tvInterestRate.setText(res.getString(3));

            TextView tvLoanPeriod = (TextView) itemCV.findViewById(R.id.tvLoanPeriod);
            tvLoanPeriod.setText(res.getString(4));

            TextView tvId = (TextView) itemCV.findViewById(R.id.tvId);
            tvId.setText(res.getString(0));



            linLayout.addView(itemCV);





        }




    }

    public void deleteCard(View view) {
        View parent = (View) view.getParent();

        TextView idTextView = (TextView) parent.findViewById(R.id.tvId);
        String idBase = String.valueOf(idTextView.getText());

        Toast.makeText(SavedCreditsList.this, "Запись удалена", Toast.LENGTH_SHORT).show();
        myDb.deleteData(idBase);
        finish();
    }

    public void savedGraph(View view) {
        View parent = (View) view.getParent();


        TextView tvLoanAmount = (TextView) parent.findViewById(R.id.tvLoanAmount);

        TextView tvInterestRate = (TextView) parent.findViewById(R.id.tvInterestRate);

        TextView tvLoanPeriod = (TextView) parent.findViewById(R.id.tvLoanPeriod);

        Intent intent = new Intent(this, GraphPayments.class);
        intent.putExtra("tvLoanAmount", String.valueOf(tvLoanAmount.getText()));
        intent.putExtra("tvInterestRate", String.valueOf(tvInterestRate.getText()));
        intent.putExtra("tvLoanPeriod", String.valueOf(tvLoanPeriod.getText()));
        startActivity(intent);


    }




}
