package com.gmail.expencive.kidslearningsigns.Adapter;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.gmail.expencive.kidslearningsigns.AutoLogosActivity;
import com.gmail.expencive.kidslearningsigns.Interface.ItemClickListener;
import com.gmail.expencive.kidslearningsigns.MainActivity;
import com.gmail.expencive.kidslearningsigns.Model.KidsLearning;
import com.gmail.expencive.kidslearningsigns.R;

import java.util.List;
import java.util.Locale;

import static com.gmail.expencive.kidslearningsigns.MainActivity.speakText;

class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView image;
    public TextView text;

    private ItemClickListener itemClickListener;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        image = (ImageView) itemView.findViewById(R.id.ex_img);
        text = (TextView) itemView.findViewById(R.id.ex_name);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition());
    }
}

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    public List<KidsLearning> kidsLearningList;
    public Context context;
    public static TextToSpeech mTTS;
    public static String mTextRecieve;




    public RecyclerViewAdapter(List<KidsLearning> kidsLearningList, Context context) {
        this.kidsLearningList = kidsLearningList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_kidslearning, parent, false);

        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        holder.image.setImageResource(kidsLearningList.get(position).getImage_id());
        holder.text.setText(kidsLearningList.get(position).getName());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {



                speakText(kidsLearningList.get(position).getName());


            }
        });

    }


    @Override
    public int getItemCount() {
        return kidsLearningList.size();
    }





}

