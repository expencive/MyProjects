package com.gmail.expencive.kidslearningsigns;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.gmail.expencive.kidslearningsigns.Adapter.RecyclerViewAdapter;
import com.gmail.expencive.kidslearningsigns.Model.KidsLearning;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AutoLogosActivity extends AppCompatActivity {



    List<KidsLearning> kidsLearningList = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_logos);



        initData();

        recyclerView = (RecyclerView) findViewById(R.id.list_ex);
        adapter = new RecyclerViewAdapter(kidsLearningList, getBaseContext());
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    private void initData() {

        kidsLearningList.add(new KidsLearning(R.drawable.m01acura, "АКУРА"));
        kidsLearningList.add(new KidsLearning(R.drawable.m02alfaromeo, "АЛЬФА-РОМЕО"));
        kidsLearningList.add(new KidsLearning(R.drawable.m03audi, "АУДИ"));
        kidsLearningList.add(new KidsLearning(R.drawable.m04bmw, "БМВ"));
        kidsLearningList.add(new KidsLearning(R.drawable.m05cadillac, "КАДИЛЛАК"));
        kidsLearningList.add(new KidsLearning(R.drawable.m06cherry, "ЧЕРРИ"));
        kidsLearningList.add(new KidsLearning(R.drawable.m07chevrolet, "ШЕВРОЛЕ"));
        kidsLearningList.add(new KidsLearning(R.drawable.m08citroen, "СИТРОЕН"));
        kidsLearningList.add(new KidsLearning(R.drawable.m09daewoo, "ДЭУ"));
        kidsLearningList.add(new KidsLearning(R.drawable.m10ferrari, "ФЕРРАРИ"));
        kidsLearningList.add(new KidsLearning(R.drawable.m11fiat, "ФИАТ"));
        kidsLearningList.add(new KidsLearning(R.drawable.m12ford, "ФОРД"));
        kidsLearningList.add(new KidsLearning(R.drawable.m14hyundai, "ХЮНДАЙ"));
        kidsLearningList.add(new KidsLearning(R.drawable.m15infiniti, "ИНФИНИТИ"));
        kidsLearningList.add(new KidsLearning(R.drawable.m16jaguar, "ЯГУАР"));
        kidsLearningList.add(new KidsLearning(R.drawable.m17jeep, "ДЖИП"));
        kidsLearningList.add(new KidsLearning(R.drawable.m18kia, "КИА"));
        kidsLearningList.add(new KidsLearning(R.drawable.m19landrover, "ЛЭНД-РОВЕР"));
        kidsLearningList.add(new KidsLearning(R.drawable.m20lexus, "ЛЕКСУС"));
        kidsLearningList.add(new KidsLearning(R.drawable.m21lada, "ЛАДА"));
        kidsLearningList.add(new KidsLearning(R.drawable.m22mazda, "МАЗДА"));
        kidsLearningList.add(new KidsLearning(R.drawable.m23mersedes, "МЕРСЕДЕС"));
        kidsLearningList.add(new KidsLearning(R.drawable.m24mini, "МИНИ"));
        kidsLearningList.add(new KidsLearning(R.drawable.m25mitsubishi, "МИТСУБИСИ"));
        kidsLearningList.add(new KidsLearning(R.drawable.m26nissan, "НИССАН"));
        kidsLearningList.add(new KidsLearning(R.drawable.m27opel, "ОПЕЛЬ"));
        kidsLearningList.add(new KidsLearning(R.drawable.m28pegeot, "ПЕЖО"));
        kidsLearningList.add(new KidsLearning(R.drawable.m29porcshe, "ПОРШЕ"));
        kidsLearningList.add(new KidsLearning(R.drawable.m30renault, "РЕНО"));
        kidsLearningList.add(new KidsLearning(R.drawable.m31scoda, "ШКОДА"));
        kidsLearningList.add(new KidsLearning(R.drawable.m32subaru, "СУБАРУ"));
        kidsLearningList.add(new KidsLearning(R.drawable.m33suzuki, "СУЗУКИ"));
        kidsLearningList.add(new KidsLearning(R.drawable.m34toyota, "ТОЙОТА"));
        kidsLearningList.add(new KidsLearning(R.drawable.m35volkswagen, "ФОЛЬКСВАГЕН"));
        kidsLearningList.add(new KidsLearning(R.drawable.m36volvo, "ВОЛЬВО"));
        kidsLearningList.add(new KidsLearning(R.drawable.m37uaz, "УАЗ"));



    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
