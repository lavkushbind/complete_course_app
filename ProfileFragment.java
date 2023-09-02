package com.example.advncd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.advncd.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    ImageView coverpic;
    ImageView profilpic;
    ImageView profilchng;
    FragmentProfileBinding binding;
    FirebaseAuth auth;
    FirebaseStorage storage;
    FirebaseDatabase database;
    ImageView chat;
    Button message_btn;
    ArrayList<postmodel> list;

    public ProfileFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        auth = FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
       // recyclerView = view.findViewById(R.id.followRV);
        chat=view.findViewById(R.id.changecp);
        message_btn= view.findViewById(R.id.message_btn);
        profilpic=view.findViewById(R.id.profilepic);
        profilchng= view.findViewById(R.id.cha);
        coverpic=view.findViewById(R.id.coverpic);
        binding=FragmentProfileBinding.inflate(inflater,container,false);

        list= new ArrayList<>();
        homeadapter homeadapter= new homeadapter(list,getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        binding.  followRV.setLayoutManager(layoutManager);

        binding. followRV.setAdapter(homeadapter);
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




     database.getReference()
                .child("Users")
                .child(auth.getUid())
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
                    binding.bio.setText(user.getBio());
                    binding.profesiontext.setText(user.getProfesion());
                    binding.emailtext.setText(user.getEmail());
            }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    binding.messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               FirebaseAuth.getInstance().signOut();
                        Intent intent= new Intent(getActivity(), login.class);
                        startActivity(intent);
            }
        });

    binding.editpro.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Fragment fragment= new EditFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();

            ft.replace(R.id.container, fragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();

        }
    });
        binding.helptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment= new HelpFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();

                ft.replace(R.id.container, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();

            }
        });

    binding.addpost.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Fragment frag = new PostFragment();


            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.container, frag);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
           // ft.addToBackStack(null);
            ft.commit();
        }

    });
        binding.cha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,11);
            }
        });
        binding.changecp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,22);
            }
        });
        return binding.getRoot();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       if(requestCode==22) {
           if (data.getData() != null) {
               Uri uri = data.getData();
               binding.coverpic.setImageURI(uri);
               final StorageReference reference = storage.getReference().child("coverpic").child(FirebaseAuth.getInstance().getUid());
               reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                   @Override
                   public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                       Toast.makeText(getContext(), "Cover pic saved", Toast.LENGTH_SHORT).show();
                       reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                           @Override
                           public void onSuccess(Uri uri) {
                               database.getReference().child("Users").child(auth.getUid()).child("coverpic").setValue(uri.toString());
                           }
                       });
                   }
               });
           }
       }
       else {
           if (data.getData() != null) {
               Uri uri = data.getData();
               binding.profilepic.setImageURI(uri);
               final StorageReference reference = storage.getReference().child("profilepic").child(FirebaseAuth.getInstance().getUid());
               reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                   @Override
                   public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                       Toast.makeText(getContext(), "Cover pic saved", Toast.LENGTH_SHORT).show();
                       reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                           @Override
                           public void onSuccess(Uri uri) {
                               database.getReference().child("Users").child(auth.getUid()).child("profilepic").setValue(uri.toString());
                           }
                       });
                   }
               });
           }
       }
    }
}