package com.dataflair.smartonlineexam.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dataflair.smartonlineexam.Model.Model;
import com.dataflair.smartonlineexam.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ScoreAdapter extends FirebaseRecyclerAdapter<Model, ScoreAdapter.Viewholder> {

    public ScoreAdapter(@NonNull FirebaseRecyclerOptions<Model> options) {

        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull ScoreAdapter.Viewholder holder, int position, @NonNull Model model) {


        //for loading restaurant name into recycler view
        holder.studentName.setText(model.getName());

        //for loading restaurant discription into recyclerview
        holder.studentMarks.setText(model.getMarks());


    }


    @NonNull
    @Override
    public ScoreAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //the data ojects are inflated into the xml file single_data_item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_notificaion_file, parent, false);
        return new ScoreAdapter.Viewholder(view);
    }

    //we need view holder to hold each objet form recyclerview and to show it in recyclerview
    class Viewholder extends RecyclerView.ViewHolder {


        TextView studentName;
        TextView studentMarks;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            //asssiginig the address of the materials to show the notificaion title and description
            studentName = (TextView) itemView.findViewById(R.id.Title);
            studentMarks = (TextView) itemView.findViewById(R.id.Desc);
        }
    }

}

