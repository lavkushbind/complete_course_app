package com.example.advncd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.advncd.databinding.ActivityGroupChatBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class GroupChat extends AppCompatActivity {
ActivityGroupChatBinding binding;
    FirebaseAuth auth;
    ArrayList<chatmodel> list;
    FirebaseDatabase database;
    FirebaseStorage storage;
    String postid;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
binding= ActivityGroupChatBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
        list = new ArrayList<>();
        postid = intent.getStringExtra("posteid");

        database.getReference().child("posts").child(postid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postmodel postmodel= snapshot.getValue(postmodel.class);
                Picasso.get().load(postmodel.getPostImage())
                        .placeholder(R.drawable.profileuser)
                        .into(binding.profileImage);
                binding.receiversName.setText(postmodel.getPostdescription());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //   final  ArrayList<chatmodel> chatmodel= new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        final String senderId = auth.getUid();
        chatAdapter chatAdapter = new chatAdapter(list, getApplicationContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        binding.messageAdapter.setLayoutManager(layoutManager);
        binding.messageAdapter.setAdapter(chatAdapter);
        database.getReference().child("Group")
                //.child(postid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            chatmodel model = snapshot1.getValue(chatmodel.class);
                            list.add(model);
                        }
                        chatAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });


        binding.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = binding.edtMessage.getText().toString();
                Date date = new Date();
                chatmodel messages = new chatmodel(senderId, message, date.getTime());
                binding.edtMessage.setText("");
                // String randomKey = database.getReference().push().getKey();


                database.getReference().child("Group").child(postid)
                        .push()
                        .setValue(messages).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                            }
                                        });

            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GroupChat.this, MainActivity.class));

            }
        });

    }


   }