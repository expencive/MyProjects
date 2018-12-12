package comexpencive.vk.autologostospeak;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import comexpencive.vk.autologostospeak.Adapter.RecyclerViewAdapter;

import comexpencive.vk.autologostospeak.Model.Exercise;

public class MainActivity extends AppCompatActivity {
    public static TextToSpeech mTTS;

    List<Exercise> exerciseList = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    public static String mTextRecieve;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_exercises);

        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    if (mTTS.isLanguageAvailable(new Locale(Locale.getDefault().getLanguage()))
                            == TextToSpeech.LANG_AVAILABLE) {
                        mTTS.setLanguage(new Locale(Locale.getDefault().getLanguage()));
                        mTTS.setPitch(1.0f);
                        mTTS.setSpeechRate(1.2f);
                    } else {
                        mTTS.setLanguage(Locale.US);
                    }
                }else{
                    Log.e("TTS", "Попытка не удалась");
                }
            }
        });

        initData();

        recyclerView = (RecyclerView) findViewById(R.id.list_ex);
        adapter = new RecyclerViewAdapter(exerciseList, getBaseContext());
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    private void initData() {

        exerciseList.add(new Exercise(R.drawable.acura, "АКУРА"));
        exerciseList.add(new Exercise(R.drawable.alfa, "АЛЬФА-РОМЕО"));
        exerciseList.add(new Exercise(R.drawable.audi, "АУДИ"));
        exerciseList.add(new Exercise(R.drawable.bmw, "БМВ"));
        exerciseList.add(new Exercise(R.drawable.cadillac, "КАДИЛЛАК"));
        exerciseList.add(new Exercise(R.drawable.chery, "ЧЕРРИ"));
        exerciseList.add(new Exercise(R.drawable.chevrolet, "ШЕВРОЛЕ"));
        exerciseList.add(new Exercise(R.drawable.citroen, "СИТРОЕН"));
        exerciseList.add(new Exercise(R.drawable.daewoo, "ДЭУ"));
        exerciseList.add(new Exercise(R.drawable.ferrari, "ФЕРРАРИ"));
        exerciseList.add(new Exercise(R.drawable.fiat, "ФИАТ"));
        exerciseList.add(new Exercise(R.drawable.ford, "ФОРД"));
        exerciseList.add(new Exercise(R.drawable.honda, "ХОНДА"));
        exerciseList.add(new Exercise(R.drawable.infiniti, "ИНФИНИТИ"));
        exerciseList.add(new Exercise(R.drawable.jaguar, "ЯГУАР"));
        exerciseList.add(new Exercise(R.drawable.jeep, "ДЖИП"));
        exerciseList.add(new Exercise(R.drawable.kia, "КИА"));
        exerciseList.add(new Exercise(R.drawable.land, "ЛЭНД-РОВЕР"));
        exerciseList.add(new Exercise(R.drawable.lexus, "ЛЕКСУС"));
        exerciseList.add(new Exercise(R.drawable.mazda, "МАЗДА"));
        exerciseList.add(new Exercise(R.drawable.mercedes, "МЕРСЕДЕС"));
        exerciseList.add(new Exercise(R.drawable.mini, "МИНИ"));
        exerciseList.add(new Exercise(R.drawable.mitsubishi, "МИТСУБИСИ"));
        exerciseList.add(new Exercise(R.drawable.nissan, "НИССАН"));
        exerciseList.add(new Exercise(R.drawable.opel, "ОПЕЛЬ"));
        exerciseList.add(new Exercise(R.drawable.peugeot, "ПЕЖО"));
        exerciseList.add(new Exercise(R.drawable.porsche, "ПОРШЕ"));
        exerciseList.add(new Exercise(R.drawable.renault, "РЕНО"));
        exerciseList.add(new Exercise(R.drawable.scoda, "ШКОДА"));
        exerciseList.add(new Exercise(R.drawable.subaru, "СУБАРУ"));
        exerciseList.add(new Exercise(R.drawable.suzuki, "СУЗУКИ"));
        exerciseList.add(new Exercise(R.drawable.toyota, "ТОЙОТА"));
        exerciseList.add(new Exercise(R.drawable.volkswagen, "ФОЛЬКСВАГЕН"));
        exerciseList.add(new Exercise(R.drawable.volvo, "ВОЛЬВО"));
        exerciseList.add(new Exercise(R.drawable.z1, "1"));
        exerciseList.add(new Exercise(R.drawable.z2, "2"));
        exerciseList.add(new Exercise(R.drawable.z3, "3"));
        exerciseList.add(new Exercise(R.drawable.z4, "4"));
        exerciseList.add(new Exercise(R.drawable.z5, "5"));
        exerciseList.add(new Exercise(R.drawable.z6, "6"));
        exerciseList.add(new Exercise(R.drawable.z7, "7"));
        exerciseList.add(new Exercise(R.drawable.z8, "8"));
        exerciseList.add(new Exercise(R.drawable.z9, "9"));
        exerciseList.add(new Exercise(R.drawable.z10, "10"));


    }



    public static void speakText(String textRecieve) {

        mTextRecieve = textRecieve;

        mTTS.speak(textRecieve, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy() {

        if (mTTS !=null) {
            mTTS.stop();
            mTTS.shutdown();
        }

        super.onDestroy();
    }

}
