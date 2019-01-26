package com.gmail.expencive.kidslearningsigns;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView imageView_autoLogos, imageView_numbers, imageView_letters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView_autoLogos = findViewById(R.id.image_view_auto_logos);
        imageView_autoLogos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AutoLogosActivity.class);
                startActivity(intent);
            }
        });

        imageView_numbers = findViewById(R.id.image_view_numbers);
        imageView_numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NumbersActivity.class);
                startActivity(intent);
            }
        });

        imageView_letters = findViewById(R.id.image_view_letters);
        imageView_letters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LettersActivity.class);
                startActivity(intent);
            }
        });

    }
}
