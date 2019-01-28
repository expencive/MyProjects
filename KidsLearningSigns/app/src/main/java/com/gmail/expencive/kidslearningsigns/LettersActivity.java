package com.gmail.expencive.kidslearningsigns;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gmail.expencive.kidslearningsigns.Adapter.RecyclerViewAdapter;
import com.gmail.expencive.kidslearningsigns.Model.KidsLearning;

import java.util.ArrayList;
import java.util.List;

public class LettersActivity extends AppCompatActivity {

    List<KidsLearning> kidsLearningList = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letters);

        initData();

        recyclerView = (RecyclerView) findViewById(R.id.list_ex);
        adapter = new RecyclerViewAdapter(kidsLearningList, getBaseContext());
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    private void initData() {

        kidsLearningList.add(new KidsLearning(R.drawable.b01a, "А"));
        kidsLearningList.add(new KidsLearning(R.drawable.b02b, "Б"));
        kidsLearningList.add(new KidsLearning(R.drawable.b03v, "В"));
        kidsLearningList.add(new KidsLearning(R.drawable.b04g, "Г"));
        kidsLearningList.add(new KidsLearning(R.drawable.b05d, "Д"));
        kidsLearningList.add(new KidsLearning(R.drawable.b06e, "Е"));
        kidsLearningList.add(new KidsLearning(R.drawable.b07ee, "Ё"));
        kidsLearningList.add(new KidsLearning(R.drawable.b08j, "Ж"));
        kidsLearningList.add(new KidsLearning(R.drawable.b09z, "З"));
        kidsLearningList.add(new KidsLearning(R.drawable.b10i, "И"));
        kidsLearningList.add(new KidsLearning(R.drawable.b11ii, "Й"));
        kidsLearningList.add(new KidsLearning(R.drawable.b12k, "К"));
        kidsLearningList.add(new KidsLearning(R.drawable.b13l, "Л"));
        kidsLearningList.add(new KidsLearning(R.drawable.b14m, "М"));
        kidsLearningList.add(new KidsLearning(R.drawable.b15n, "Н"));
        kidsLearningList.add(new KidsLearning(R.drawable.b16o, "О"));
        kidsLearningList.add(new KidsLearning(R.drawable.b17p, "П"));
        kidsLearningList.add(new KidsLearning(R.drawable.b18r, "Р"));
        kidsLearningList.add(new KidsLearning(R.drawable.b19s, "С"));
        kidsLearningList.add(new KidsLearning(R.drawable.b20t, "Т"));
        kidsLearningList.add(new KidsLearning(R.drawable.b21u, "У"));
        kidsLearningList.add(new KidsLearning(R.drawable.b22f, "Ф"));
        kidsLearningList.add(new KidsLearning(R.drawable.b23h, "Х"));
        kidsLearningList.add(new KidsLearning(R.drawable.b24c, "Ц"));
        kidsLearningList.add(new KidsLearning(R.drawable.b25che, "Ч"));
        kidsLearningList.add(new KidsLearning(R.drawable.b26sh, "Ш"));
        kidsLearningList.add(new KidsLearning(R.drawable.b27shee, "Щ"));
        kidsLearningList.add(new KidsLearning(R.drawable.b28tverdii_znak, "Ъ"));
        kidsLearningList.add(new KidsLearning(R.drawable.b29iiii, "Ы"));
        kidsLearningList.add(new KidsLearning(R.drawable.b30myg_znak, "Ь"));
        kidsLearningList.add(new KidsLearning(R.drawable.b31e, "Э"));
        kidsLearningList.add(new KidsLearning(R.drawable.b32yu, "Ю"));
        kidsLearningList.add(new KidsLearning(R.drawable.b33ya, "Я"));


    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


}