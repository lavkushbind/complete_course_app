package com.example.advncd;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.advncd.databinding.UserSampleBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;


public class  Useradapter extends RecyclerView.Adapter<Useradapter.viewHolder>{

    ArrayList<Users> list;
    android.content.Context context;

    public Useradapter(ArrayList<Users> list, android.content.Context context) {
        this.list = list;
        this.context = context;
    }

    public Useradapter(android.content.Context context, ArrayList<Users> list) {
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

View view = LayoutInflater.from(context).inflate(R.layout.user_sample,parent,false);
   return  new viewHolder(view);

        //return new viewHolder(LayoutInflater.from(context).inflate(R.layout.user_sample, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Users users =list.get(position);
        Picasso.get()
                .load(users.getProfilepic())
                .placeholder(R.drawable.profileuser)
                .into(holder.binding.profilepic);
        holder.binding.usernme.setText(users.getName());

      /*  holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, ChatAA.class);
                intent.putExtra("name",users.getName());
                intent.putExtra("ReceiversImage", users.getProfilepic());
                intent.putExtra("uid",users.getUid());
                context.startActivity(intent);
            }
        });*/
  /*      holder.binding.buttonfollw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Followmodel followmodel= new Followmodel();
                followmodel.setFollowedBy(FirebaseAuth.getInstance().getUid());
                followmodel.setFollowedAt(new Date().getTime());
                FirebaseDatabase.getInstance().getReference()
                        .child("Users")
                        .child(users.getUserID())
                        .child("followers")
                        .child(FirebaseAuth.getInstance().getUid())
                        .setValue(followmodel).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                FirebaseDatabase.getInstance().getReference()
                                        .child("Users")
                                        .child(users.getUserID())
                                        .child("followerCount")
                                        .setValue(users.getFollowercount()+1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                // holder.binding.buttonfollw.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.glogo));
                                                holder.binding.buttonfollw.setBackgroundColor(context.getResources().getColor(R.color.purple_700));
                                                holder.binding.buttonfollw.setTextColor(context.getResources().getColor(R.color.black));
                                                holder.binding.buttonfollw.setEnabled(false);
                                                Toast.makeText(context, "you followed" + users.getName(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        });
            }
        });
*/

        holder.binding.buttonfollw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Followmodel followmodel= new Followmodel();
                followmodel.setFollowedBy(FirebaseAuth.getInstance().getUid());
                followmodel.setFollowedAt(new Date().getTime());
          FirebaseDatabase.getInstance().getReference()
                  .child("Users")
                  .child(users.getUserID())
                  .child("followers")
                        .child(FirebaseAuth.getInstance().getUid())
                        .setValue(followmodel).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                FirebaseDatabase.getInstance().getReference()
                                        .child("Users")
                                        .child(users.getUserID())
                                        .child("followerCount")
                                        .setValue(users.getFollowercount()+1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(context, "you followed" + users.getName(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();

    }


    public class viewHolder extends RecyclerView.ViewHolder{
        UserSampleBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding =UserSampleBinding.bind(itemView);

        }
    }
}

