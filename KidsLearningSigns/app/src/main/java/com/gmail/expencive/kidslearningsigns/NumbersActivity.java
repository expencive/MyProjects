package com.gmail.expencive.kidslearningsigns;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gmail.expencive.kidslearningsigns.Adapter.RecyclerViewAdapter;
import com.gmail.expencive.kidslearningsigns.Model.KidsLearning;

import java.util.ArrayList;
import java.util.List;

public class NumbersActivity extends AppCompatActivity {

    List<KidsLearning> kidsLearningList = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        initData();

        recyclerView = (RecyclerView) findViewById(R.id.list_ex);
        adapter = new RecyclerViewAdapter(kidsLearningList, getBaseContext());
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {

        kidsLearningList.add(new KidsLearning(R.drawable.bmw, "БМВ"));
        kidsLearningList.add(new KidsLearning(R.drawable.bmw, "ХОНДА"));
        kidsLearningList.add(new KidsLearning(R.drawable.bmw, "ШЕВРОЛЕ"));

    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
