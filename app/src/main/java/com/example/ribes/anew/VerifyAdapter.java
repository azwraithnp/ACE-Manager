package com.example.ribes.anew;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by B50i7D on 4/23/2017.
 */

public class VerifyAdapter extends RecyclerView.Adapter<VerifyAdapter.MyViewHolder> {
    private ArrayList<UserModel> users;
    Boolean visibility = false;
    ImageView img;
    Context context;
    LinearLayout linearLayout;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView header;

        public MyViewHolder(View view) {
            super(view);
            header = (TextView) view.findViewById(R.id.sevice_name);
            linearLayout = (LinearLayout) view.findViewById(R.id.services_item);
            img = view.findViewById(R.id.list_icon);
            //Picasso.with(context).load(downloadUrl.toString()).into(img);
        }
    }

    public VerifyAdapter(ArrayList<UserModel> usersList, Context contxt) {
        this.users = usersList;
        this.context = contxt;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.verify_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    public UserModel getItem(int position) {
        return users.get(position);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        String header = users.get(position).getName();
        holder.header.setText(header);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, Detailed_page.class);
                intent.putExtra("id", users.get(position).getId());
                intent.putExtra("name", users.get(position).getName());
                intent.putExtra("email", users.get(position).getEmail());
                intent.putExtra("address", users.get(position).getAddress());
                intent.putExtra("roll", users.get(position).getRoll());
                intent.putExtra("faculty", users.get(position).getFaculty());
                intent.putExtra("phone_number", users.get(position).getPhone_number());
                intent.putExtra("user_type", users.get(position).getUser_type());
                context.startActivity(intent);



            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}


