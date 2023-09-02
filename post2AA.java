package com.example.advncd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.advncd.databinding.FragmentPostBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;

public class  post2AA extends AppCompatActivity {
    FragmentPostBinding binding;
    Uri uri;
    Intent intent;
    FirebaseDatabase database;
    ProgressDialog dialog;
    FirebaseStorage storage;
    String postid;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentPostBinding.inflate(getLayoutInflater());
        intent = getIntent();
        postid = intent.getStringExtra("postid");
     //   name = intent.getStringExtra("postedBy");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Please wait......");
        dialog.setTitle("Post Uploading");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        binding.postdisc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String description = binding.postdisc.getText().toString();
                if (!description.isEmpty()){
                //    binding.postbtn.setBackgroundColor();
                    //  binding.postbtn.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.alarm));
                   // binding.postbtn.setBackgroundColor(getResources().getColor(R.color.purple_700));
                    binding.postbtn.setBackgroundColor(getResources().getColor(R.color.purple_700));

                    binding.postbtn.setTextColor(getResources().getColor(R.color.black));
                    binding.postbtn.setEnabled(true);
                }else{

                  //  binding.postbtn.setBackgroundDrawable(ContextCompat.getDrawable(R.drawable.amp_logo));
                    binding.postbtn.setTextColor(getResources().getColor(R.color.amp_gray));
                    binding.postbtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.languageP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String language = binding.languageP.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.timeP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String time = binding.timeP.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.aboutP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String about = binding.postdisc.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String price = binding.price.getText().toString();


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.duration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String duration = binding.duration.getText().toString();


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.addimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("video/*");
                startActivityForResult(intent,11);
            }
        });
        binding.postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                final StorageReference reference = storage.getReference().child("posts").child(postid)
                        .child(FirebaseAuth.getInstance().getUid())
                        .child(new Date().getTime() + "");
                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                postmodel postmodel = new postmodel();
                               // postmodel.setPostImage(uri.toString());
                                postmodel.setPostVideo(uri.toString());
                                postmodel.setAbout(binding.aboutP.getText().toString());
                                postmodel.setPostedBy(FirebaseAuth.getInstance().getUid());
                                postmodel.setPrice(binding.price.getText().toString());
                                postmodel.setTime(binding.timeP.getText().toString());
                                postmodel.setLanguage(binding.languageP.getText().toString());
                                postmodel.setDuration(binding.duration.getText().toString());
                                postmodel.setPostdescription(binding.postdisc.getText().toString());
                                //postmodel.setPostedAt(String.valueOf(new Date().getTime()));
                                database.getReference().child("posts").child(postid)
                                       // .push()
                                        .setValue(postmodel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                dialog.dismiss();
                                               // Toast.makeText(getContext(), "posted", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        });
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float per=(100*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                        dialog.setMessage("Upload:"+(int)per+"%");
                    }
                });

            }

        });


    }
}
