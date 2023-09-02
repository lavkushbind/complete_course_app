package com.example.advncd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.advncd.databinding.FragmentEditBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class EditFragment extends Fragment {

    FragmentEditBinding binding;
    FirebaseAuth auth;
    FirebaseStorage storage;
    FirebaseDatabase database;

    public EditFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditBinding.inflate(inflater, container, false);

        database.getReference()
                .child("Users")
                .child(auth.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Users user = snapshot.getValue(Users.class);
                            binding.Username.setText(user.getName());
                            binding.bio.setText(user.getBio());
                        }    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

        binding.Updatepro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String update = binding.profesiontext.getText().toString();
                binding.profesiontext.setText("");
                database.getReference().child("Users")
                        .child(auth.getUid()).child("profesion").
                        setValue(update).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                            }
                        });
            }
        });
        binding.Updatename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String update = binding.nametext.getText().toString();
                binding.nametext.setText("");
                database.getReference().child("Users")
                        .child(auth.getUid()).child("name").
                        setValue(update).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                            }
                        });
            }
        });

        binding.Updateemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String update = binding.emailtex.getText().toString();
                binding.emailtex.setText("");
                database.getReference().child("Users")
                        .child(auth.getUid()).child("email").
                        setValue(update).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                            }
                        });
            }
        });

        binding.Update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String update = binding.biotext.getText().toString();
                        binding.biotext.setText("");
                        database.getReference().child("Users")
                                .child(auth.getUid()).child("bio").
                                setValue(update).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                    }
                                });
                    }
                });
                return binding.getRoot();


            }


        }