package ru.startandroid.yogaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.startandroid.yogaapp.Adapter.RecyclerViewAdapter;
import ru.startandroid.yogaapp.Model.Exercise;

public class ListExercises extends AppCompatActivity {

    List<Exercise> exerciseList = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_exercises);

        initData();

        recyclerView = (RecyclerView) findViewById(R.id.list_ex);
        adapter = new RecyclerViewAdapter(exerciseList, getBaseContext());
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {

        exerciseList.add(new Exercise(R.drawable.easy_pose, "Простая поза"));
        exerciseList.add(new Exercise(R.drawable.cobra_pose, "Поза кобры"));
        exerciseList.add(new Exercise(R.drawable.downward_facing_dog, "Поза плуга"));
        exerciseList.add(new Exercise(R.drawable.boat_pose, "Поза лодки"));
        exerciseList.add(new Exercise(R.drawable.half_pigeon, "Поза голубя"));
        exerciseList.add(new Exercise(R.drawable.low_lunge, "Низкий прогиб"));
        exerciseList.add(new Exercise(R.drawable.upward_bow, "Обратный поклон"));
        exerciseList.add(new Exercise(R.drawable.crescent_lunge, "Прогиб крест"));
        exerciseList.add(new Exercise(R.drawable.warrior_pose, "Поза воина"));
        exerciseList.add(new Exercise(R.drawable.bow_pose, "Лук"));
        exerciseList.add(new Exercise(R.drawable.warrior_pose_2, "Поза флюгер"));
    }
}
