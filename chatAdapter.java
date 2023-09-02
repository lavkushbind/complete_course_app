package com.example.advncd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class chatAdapter extends  RecyclerView.Adapter {
    ArrayList<chatmodel> list;
    Context contextl;

    int SENDER_TYPE =1;
    int RECEIVER_TYPE = 2;



    public chatAdapter(ArrayList<chatmodel> list, Context contextl) {
        this.list = list;
        this.contextl = contextl;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==SENDER_TYPE)
        {

            View view= LayoutInflater.from(contextl).inflate(R.layout.sender_layout_item,parent,false);
            return new Senderviewholder(view);
        }
        else {
            View view = LayoutInflater.from(contextl).inflate(R.layout.receiver_layout_item, parent, false);
            return new ReciverViewholder(view);
        }


    }
    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getMuid().equals(FirebaseAuth.getInstance().getUid())) {
            return SENDER_TYPE;
        } else {
            return RECEIVER_TYPE;

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
     chatmodel chatmodel= list.get(position);
     if(holder.getClass() == Senderviewholder.class){
         ((Senderviewholder)holder).senderMsg.setText(chatmodel.getMasseg());
     }
     else {
         ((ReciverViewholder)holder).receiverMsg.setText(chatmodel.getMasseg());
     }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    private class Senderviewholder extends RecyclerView.ViewHolder  {
        TextView senderMsg, senderTime;
        public Senderviewholder(@NonNull View itemView) {
            super(itemView);
            senderMsg= itemView.findViewById(R.id.senmsg);
            senderTime= itemView.findViewById(R.id.sentime);

        }
    }
    private class ReciverViewholder extends RecyclerView.ViewHolder  {
        TextView receiverMsg, receiverTime;
        public ReciverViewholder(@NonNull View itemView) {
            super(itemView);

            receiverMsg= itemView.findViewById(R.id.recmsg);
            receiverTime= itemView.findViewById(R.id.rectime);
        }
    }}