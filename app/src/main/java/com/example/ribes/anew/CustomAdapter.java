package com.example.ribes.anew;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

        private ArrayList<UserModel> dataSet;

        public static class MyViewHolder extends RecyclerView.ViewHolder {

            CardView cardView;
            TextView textViewName;
            TextView textViewVersion;
            ImageView imageViewIcon;

            public MyViewHolder(View itemView) {
                super(itemView);
                this.cardView = itemView.findViewById(R.id.card_view);
                this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
                this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewEmail);
                this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
            }
        }

        public CustomAdapter(ArrayList<UserModel> data) {
            this.dataSet = data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cards_layout, parent, false);



            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

            TextView textViewName = holder.textViewName;
            TextView textViewVersion = holder.textViewVersion;
            ImageView imageView = holder.imageViewIcon;
            textViewName.setText(dataSet.get(listPosition).getName());
            textViewVersion.setText(dataSet.get(listPosition).getEmail());
            holder.itemView.setTransitionName("transition" + listPosition);


            //textViewName.setText(dataSet.get(listPosition).getName());
            //textViewVersion.setText(dataSet.get(listPosition).getVersion());
            //imageView.setImageResource(dataSet.get(listPosition).getImage());
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }
    }
