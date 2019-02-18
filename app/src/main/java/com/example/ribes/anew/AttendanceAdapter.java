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

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.MyViewHolder> {

    private ArrayList<UserModel> dataSet;
    ArrayList<Boolean> present;
    ArrayList<String> ids;
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

    public AttendanceAdapter(ArrayList<UserModel> data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        present = new ArrayList<>();
        ids = new ArrayList<>();
        for(int i=0;i<dataSet.size();i++)
        {
            ids.add(dataSet.get(i).getId());
            present.add(false);
        }
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
        textViewName.setText(dataSet.get(listPosition).getName());

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                present.set(listPosition, checkBox.isChecked());
            }
        });

        //textViewName.setText(dataSet.get(listPosition).getName());
        //textViewVersion.setText(dataSet.get(listPosition).getVersion());
        //imageView.setImageResource(dataSet.get(listPosition).getImage());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    public ArrayList getPresent()
    {
        return present;
    }

    public ArrayList getIds()
    {
        return ids;
    }
}

