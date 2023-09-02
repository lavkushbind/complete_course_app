package com.example.advncd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.advncd.databinding.ActivityProBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

public class ProActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    ActivityProBinding binding;
    String ReceiversUID, ReceiversName;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        auth= FirebaseAuth.getInstance();

        ReceiversUID = intent.getStringExtra("name");


     //   Bundle bundle= getIntent().getExtras();

       // ReceiversName = bundle.getString("name");
         // binding.Username.setText(getIntent().getStringExtra("name"));

        binding = ActivityProBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database  = FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();
      /*  database.getReference().child("Users")
                .child(ReceiversUID)
                .child("followerCount")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot :snapshot.getChildren()){
                            Users users = dataSnapshot.getValue(Users.class);

                            binding.followerid.setText(users.getFollowercount()+"");
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                  */
        database.getReference()
                           .child("Users")
                           .child(ReceiversUID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Users user= snapshot.getValue(Users.class);
                            Picasso.get().load(user.getCoverpic())
                                    .placeholder(R.drawable.profileuser)
                                    .into(binding.coverpic);
                            Picasso.get().load(user.getProfilepic())
                                    .placeholder(R.drawable.profileuser)
                                    .into(binding.profilepic);
                           binding.Username.setText(user.getName());
                            binding.followerid.setText(user.getFollowercount()+"");

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
        binding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent( ProActivity.this,ChatAA.class);
                intent.putExtra("uid",ReceiversUID);
                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


    }

}