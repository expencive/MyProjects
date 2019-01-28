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

        kidsLearningList.add(new KidsLearning(R.drawable.z1, "1"));
        kidsLearningList.add(new KidsLearning(R.drawable.z2, "2"));
        kidsLearningList.add(new KidsLearning(R.drawable.z3, "3"));
        kidsLearningList.add(new KidsLearning(R.drawable.z4, "4"));
        kidsLearningList.add(new KidsLearning(R.drawable.z5, "5"));
        kidsLearningList.add(new KidsLearning(R.drawable.z6, "6"));
        kidsLearningList.add(new KidsLearning(R.drawable.z7, "7"));
        kidsLearningList.add(new KidsLearning(R.drawable.z8, "8"));
        kidsLearningList.add(new KidsLearning(R.drawable.z9, "9"));
        kidsLearningList.add(new KidsLearning(R.drawable.z010, "0"));

    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
