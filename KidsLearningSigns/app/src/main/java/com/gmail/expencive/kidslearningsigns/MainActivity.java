package com.gmail.expencive.kidslearningsigns;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static TextToSpeech mTTS;
    public static String mTextRecieve;

    ImageView imageView_autoLogos, imageView_numbers, imageView_letters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    if (mTTS.isLanguageAvailable(new Locale(Locale.getDefault().getLanguage()))
                            == TextToSpeech.LANG_AVAILABLE) {
                        mTTS.setLanguage(new Locale(Locale.getDefault().getLanguage()));
                        mTTS.setPitch(1.4f);
                        mTTS.setSpeechRate(1.0f);
                    } else {
                        mTTS.setLanguage(Locale.US);
                    }
                }else{
                    Log.e("TTS", "Попытка не удалась");
                }
            }
        });

        imageView_autoLogos = findViewById(R.id.image_view_auto_logos);
        imageView_autoLogos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AutoLogosActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        imageView_numbers = findViewById(R.id.image_view_numbers);
        imageView_numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NumbersActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        imageView_letters = findViewById(R.id.image_view_letters);
        imageView_letters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LettersActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

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
