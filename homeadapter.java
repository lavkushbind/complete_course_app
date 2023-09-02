package com.example.advncd;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.advncd.databinding.VideoBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class homeadapter extends RecyclerView.Adapter<homeadapter.viewHolder>  {
    ArrayList<postmodel> list;
    Context context;
    public homeadapter(ArrayList<postmodel> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.video,parent,false);
    return new viewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        postmodel postmodel= list.get(position);
        Picasso.get()
                .load(postmodel.getPostImage())
                .placeholder(R.drawable.profileuser)
                .into(holder.binding.exoplayerview);
      //  holder.binding.nameID.setText(postmodel.getPostedBy());
        holder.binding.priceID.setText(postmodel.getPrice());
        holder.binding.vtitle.setText(postmodel.getPostdescription());
        FirebaseDatabase.getInstance().getReference().child("Users").child(postmodel.getPostedBy()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
    Users user = snapshot.getValue(Users.class);
    holder.binding.nameID.setText(user.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=  new Intent(context,post2Activity.class);
                intent.putExtra("price",postmodel.getPrice());

                intent.putExtra("postid",postmodel .getPostid());
                 intent.putExtra("postedBy",postmodel.getPostedBy());
                 intent.putExtra("video",postmodel.getPostVideo());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 context.startActivity(intent);

            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder {
        VideoBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = VideoBinding.bind(itemView);
        }
    }
    }













