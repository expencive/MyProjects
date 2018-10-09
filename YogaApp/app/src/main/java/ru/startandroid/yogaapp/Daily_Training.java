package ru.startandroid.yogaapp;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import ru.startandroid.yogaapp.Database.YogaDB;
import ru.startandroid.yogaapp.Model.Exercise;
import ru.startandroid.yogaapp.Utils.Common;

public class Daily_Training extends AppCompatActivity {

    Button btnStart;
    ImageView ex_image;
    TextView txtGetReady, txtCountdown, txtTimer, ex_name;
    ProgressBar progressBar;
    LinearLayout layoutGetReady;

    int ex_id=0, limit_time = 0;

    List<Exercise> list = new ArrayList<>();
    YogaDB yogaDb;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily__training);

        initData();

        yogaDb = new YogaDB(this);



        btnStart = (Button) findViewById(R.id.btnStart);

        ex_image = (ImageView) findViewById(R.id.detail_image);
        ex_name = (TextView) findViewById(R.id.title);

        txtCountdown = (TextView) findViewById(R.id.txtCountdown);

        txtGetReady = (TextView) findViewById(R.id.txtGetReady);
        txtTimer = (TextView) findViewById(R.id.timer);

        layoutGetReady = (LinearLayout) findViewById(R.id.layout_get_ready);

        progressBar = (MaterialProgressBar) findViewById(R.id.progressBar);

        //set data
        progressBar.setMax(list.size());

        setExerciseInformation(ex_id);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btnStart.getText().toString().toLowerCase().equals("start"))
                {
                    showGetReady();
                    btnStart.setText("done");
                }
                else if (btnStart.getText().toString().toLowerCase().equals("done"))
                {
                    if (yogaDb.getSettingMode() == 0)
                        exerciseEasyModeCountdown.cancel();
                    else if (yogaDb.getSettingMode() == 1)
                        exerciseMediumModeCountdown.cancel();
                    else if (yogaDb.getSettingMode() == 2)
                        exerciseHardModeCountdown.cancel();
                    restTimeCountdown.cancel();

                    if (ex_id < list.size())
                    {
                        showRestTime();
                        ex_id++;
                        progressBar.setProgress(ex_id);
                        txtTimer.setText("");

                    }

                }
                else
                    showFinished();

            }
        });
    }

    private void showRestTime() {
        ex_image.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.INVISIBLE);
        btnStart.setText("Skip");
        btnStart.setVisibility(View.INVISIBLE);

        layoutGetReady.setVisibility(View.VISIBLE);

        restTimeCountdown.start();

        txtGetReady.setText("REST TIME");
    }

    private void showGetReady() {

        ex_image.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.VISIBLE);

        layoutGetReady.setVisibility(View.VISIBLE);

        txtGetReady.setText("Get Ready");

        new CountDownTimer(6000, 1000)
        {
            @Override
            public void onTick(long l) {
                txtCountdown.setText(""+(l-1000)/1000);

            }

            @Override
            public void onFinish() {
                showExercises();

            }

        }.start();

    }

    private void showExercises() {
        if (ex_id < list.size()) //list size contains all exercises
        {
            ex_image.setVisibility(View.VISIBLE);
            btnStart.setVisibility(View.VISIBLE);
            layoutGetReady.setVisibility(View.INVISIBLE);

            if (yogaDb.getSettingMode() == 0)
            exerciseEasyModeCountdown.start();
            else if (yogaDb.getSettingMode() == 1)
                exerciseMediumModeCountdown.start();
            else if (yogaDb.getSettingMode() == 2)
                exerciseHardModeCountdown.start();

            //set data
            ex_image.setImageResource(list.get(ex_id).getImage_id());
            ex_name.setText(list.get(ex_id).getName());

        }
        else
            showFinished();
    }

    private void showFinished() {
        ex_image.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.INVISIBLE);
        layoutGetReady.setVisibility(View.VISIBLE);

        txtGetReady.setText("Finished!!!");
        txtCountdown.setText("Congratulation!!! \nYou're done with today exercises");
        txtCountdown.setTextSize(20);

        //save workout done to db
        yogaDb.saveDay(""+ Calendar.getInstance().getTimeInMillis());


    }

    CountDownTimer exerciseEasyModeCountdown = new CountDownTimer(Common.TIME_LIMIT_EASY, 1000) {
        @Override
        public void onTick(long l) {
            txtTimer.setText(""+(l/1000));

        }

        @Override
        public void onFinish() {
            if (ex_id < list.size())
            {
                ex_id++;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");
                setExerciseInformation(ex_id);
                btnStart.setText("Start");
            }

        }
    };

    CountDownTimer exerciseMediumModeCountdown = new CountDownTimer(Common.TIME_LIMIT_MEDIUM, 1000) {
        @Override
        public void onTick(long l) {
            txtTimer.setText(""+(l/1000));

        }

        @Override
        public void onFinish() {
            if (ex_id < list.size())
            {
                ex_id++;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");
                setExerciseInformation(ex_id);
                btnStart.setText("Start");
            }

        }
    };

    CountDownTimer exerciseHardModeCountdown = new CountDownTimer(Common.TIME_LIMIT_HARD, 1000) {
        @Override
        public void onTick(long l) {
            txtTimer.setText(""+(l/1000));

        }

        @Override
        public void onFinish() {
            if (ex_id < list.size())
            {
                ex_id++;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");
                setExerciseInformation(ex_id);
                btnStart.setText("Start");
            }

        }
    };

    CountDownTimer restTimeCountdown = new CountDownTimer(10000, 1000) {
        @Override
        public void onTick(long l) {
            txtTimer.setText(""+(l/1000));

        }

        @Override
        public void onFinish() {
            setExerciseInformation(ex_id);
            showExercises();

        }
    };

    private void setExerciseInformation(int id) {

        ex_image.setImageResource(list.get(id).getImage_id());
        ex_name.setText(list.get(id).getName());
        btnStart.setText("Start");
        ex_image.setVisibility(View.VISIBLE);
        btnStart.setVisibility(View.VISIBLE);
        txtTimer.setVisibility(View.VISIBLE);

        layoutGetReady.setVisibility(View.INVISIBLE);
    }

    private void initData() {

        list.add(new Exercise(R.drawable.easy_pose, "Простая поза"));
        list.add(new Exercise(R.drawable.cobra_pose, "Поза кобры"));
        list.add(new Exercise(R.drawable.downward_facing_dog, "Поза плуга"));
        list.add(new Exercise(R.drawable.boat_pose, "Поза лодки"));
        list.add(new Exercise(R.drawable.half_pigeon, "Поза голубя"));
        list.add(new Exercise(R.drawable.low_lunge, "Низкий прогиб"));
        list.add(new Exercise(R.drawable.upward_bow, "Обратный поклон"));
        list.add(new Exercise(R.drawable.crescent_lunge, "Прогиб крест"));
        list.add(new Exercise(R.drawable.warrior_pose, "Поза воина"));
        list.add(new Exercise(R.drawable.bow_pose, "Лук"));
        list.add(new Exercise(R.drawable.warrior_pose_2, "Поза флюгер"));
    }
}
