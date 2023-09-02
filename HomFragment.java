package com.example.advncd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.advncd.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomFragment extends Fragment {
    FirebaseAuth auth;
    FragmentHomeBinding binding;
   ArrayList<postmodel>  list;
   ArrayList<appmodel> lists;
    FirebaseDatabase database;
    public HomFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
database  = FirebaseDatabase.getInstance();
auth= FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding= FragmentHomeBinding.inflate(inflater,container,false);
        list= new ArrayList<>();
        lists= new ArrayList<>();
      AppAdapter appAdapter = new AppAdapter(lists,getContext());

        homeadapter homeadapter= new homeadapter(list,getContext());

       LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
       //,LinearLayoutManager.HORIZONTAL, true);
       binding.AppRV.setLayoutManager(linearLayoutManager);
       binding.AppRV.setAdapter(appAdapter);


        database.getReference()
                .child("Users")
                .child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Users user= snapshot.getValue(Users.class);
                            Picasso.get().load(user.getCoverpic())
                                    .placeholder(R.drawable.profileuser)
                                    .into(binding.profilepic);
                            Picasso.get().load(user.getProfilepic())
                                    .placeholder(R.drawable.profileuser)
                                    .into(binding.profilepic);


                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

        database.getReference().child("App").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               lists.clear();
               for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                   appmodel appmodel = dataSnapshot.getValue(appmodel.class);
                   appmodel.setPhotoid(dataSnapshot.getKey());
                   lists.add(appmodel);

               }
               appAdapter.notifyDataSetChanged();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);
      binding.  postRV.setLayoutManager(layoutManager);
        binding. postRV.setAdapter(homeadapter);
        database.getReference()
                .child("posts")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    postmodel postmodel =dataSnapshot.getValue(postmodel.class);
                    postmodel.setPostid(dataSnapshot.getKey());

                    list.add(postmodel);
                }
                homeadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return binding.getRoot();
    }
}