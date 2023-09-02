package com.example.advncd.notification;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.advncd.GroupChat;
import com.example.advncd.R;
import com.example.advncd.Users;
import com.example.advncd.databinding.Notification2sampleBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class NotificationAdapter extends  RecyclerView.Adapter<NotificationAdapter.viewholder> {
    ArrayList<NotificationModel> list;
    Context contextl;
    public NotificationAdapter(ArrayList<NotificationModel> list, Context contextl) {
        this.list = list;
        this.contextl = contextl;
    }
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(contextl).inflate(R.layout.notification2sample,parent,false);
        return new viewholder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        NotificationModel model=list.get(position);
      String type = model.getType();
        FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(model.getNotificationBy())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Users users = snapshot.getValue(Users.class);
                        Picasso.get().load(users.getProfilepic())
                              .placeholder(R.drawable.profileuser)
                                .into(holder.binding.profileImg);
                           if(type.equals("folow")){
                           holder.binding.notification.setText(  "your c");
                         }

                        if(type.equals("follow")){
                            holder.binding.notification.setText(  "your cr");}
                           else  {
                          holder.binding.notification.setText( "your new cr");
                            }
                    }


                    
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        holder.binding.openNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  if(!type.equals("follow"))
                {
                    FirebaseDatabase.getInstance().getReference()
                            .child("notification")
                            .child(model.getNotificationBy())
                            .child(model.getNotificationId())
                            .child("checkopen")
                            .setValue(true);

                Intent intent= new Intent(contextl, GroupChat.class);
               intent.putExtra("postId",model.getPostID());
                intent.putExtra("postedBy",model.getNotificationBy());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.binding.openNotification.setBackgroundColor(contextl.getResources().getColor(R.color.white));
                contextl.startActivity(intent);
            }}
        });
        Boolean checkOpen = model.isCheckOpen();
        if (checkOpen == true){
            holder.binding.openNotification.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else {

        }




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class viewholder extends RecyclerView.ViewHolder{
        Notification2sampleBinding binding;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            binding =Notification2sampleBinding.bind(itemView);
        }
    }
}
