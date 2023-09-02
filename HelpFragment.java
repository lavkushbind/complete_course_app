package com.example.advncd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.advncd.databinding.FragmentHelpBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class HelpFragment extends Fragment {
FragmentHelpBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    public HelpFragment() {
    }

  @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      auth= FirebaseAuth.getInstance();
      database= FirebaseDatabase.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHelpBinding.inflate(inflater, container, false);
        binding.sendhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String update= binding.helpid.getText().toString();
                binding.helpid.setText("");
                database.getReference().child("Help").child(auth.getUid()).child("name").setValue(update).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                    }
                });
            }
        });


            return binding.getRoot();

}}