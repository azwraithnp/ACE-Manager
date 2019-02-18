package com.example.ribes.anew;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AttendanceShowAdapte extends RecyclerView.Adapter<AttendanceShowAdapte.MyViewHolder> {

    private ArrayList<String> dataSet;
    ArrayList<Boolean> present;
    Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        TextView textViewName;
        CheckBox checkBox;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = itemView.findViewById(R.id.student_name);

            this.checkBox = itemView.findViewById(R.id.checkbox);
        }
    }

    public AttendanceShowAdapte(ArrayList<String> data, ArrayList<Boolean> present, Context context) {
        this.dataSet = data;
        this.present = present;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.att_row, parent, false);



        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;
        final CheckBox checkBox = holder.checkBox;
        textViewName.setText(dataSet.get(listPosition));

        checkBox.setChecked(present.get(listPosition));
        checkBox.setEnabled(false);

        //textViewName.setText(dataSet.get(listPosition).getName());
        //textViewVersion.setText(dataSet.get(listPosition).getVersion());
        //imageView.setImageResource(dataSet.get(listPosition).getImage());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

}

