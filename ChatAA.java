package com.example.advncd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.advncd.databinding.ActivityChatBinding;
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

public class ChatAA extends AppCompatActivity {
    String ReceiversImage;
            String ReceiversUID;
    FirebaseAuth auth;
    ArrayList<chatmodel> list;
    FirebaseDatabase database;
    FirebaseStorage storage;
     ActivityChatBinding binding;
    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       binding.receiversName.setText(getIntent().getStringExtra("name"));
        list = new ArrayList<>();


        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        final String senderId = auth.getUid();
        intent = getIntent();
        ReceiversUID = intent.getStringExtra("uid");
        chatAdapter chatAdapter = new chatAdapter(list, getApplicationContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        binding.messageAdapter.setLayoutManager(layoutManager);
        binding.messageAdapter.setAdapter(chatAdapter);
        final String senderRoom = senderId + ReceiversUID;
        final String receiverRoom = ReceiversUID + senderId;
        database.getReference()
                .child("Users")
                .child(ReceiversUID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Users user= snapshot.getValue(Users.class);
                            Picasso.get().load(user.getProfilepic())
                                    .placeholder(R.drawable.profileuser)
                                    .into(binding.profileImage);
                            binding.receiversName.setText(user.getName());
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
        database.getReference()
                .child("chats")
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


        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChatAA.this, GroupChat.class));

            }
        });
        binding.attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 25);
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


                database.getReference().child("chats").child(senderId).child(ReceiversUID)
                        .child(senderRoom)
                        //   .child("messages")
                        //.child(randomKey)
                        .push()
                        .setValue(messages).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                database.getReference().child("chats").child(ReceiversUID).child(senderId)
                                        .child(receiverRoom)
                                        // .child("messages")
                                        //.child(randomKey)
                                        .push()
                                        .setValue(messages).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                            }
                                        });
                            }
                        });
            }
        });

    }}