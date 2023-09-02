package com.example.advncd;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.advncd.databinding.FragmentPostBinding;
import com.example.advncd.notification.NotificationModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;
public class PostFragment extends Fragment {
FragmentPostBinding binding;
Uri uri;
Context context;
  //  String postid;

FirebaseAuth auth;
FirebaseDatabase database;
ProgressDialog dialog;
FirebaseStorage storage;
    public PostFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth= FirebaseAuth.getInstance();
        //postid=auth.getTenantId();

        database= FirebaseDatabase.getInstance();
        storage= FirebaseStorage.getInstance();
        dialog = new ProgressDialog(getContext());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
         binding =FragmentPostBinding.inflate(inflater, container, false);
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
                  //  binding.postbtn.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.alarm));
                  binding.postbtn.setBackgroundColor(getContext().getResources().getColor(R.color.purple_700));

                  binding.postbtn.setTextColor(getContext().getResources().getColor(R.color.black));
                  binding.postbtn.setEnabled(true);
              }else{
                  binding.postbtn.setBackgroundDrawable(ContextCompat.getDrawable(getContext(),R.drawable.amp_logo));
                  binding.postbtn.setTextColor(getContext().getResources().getColor(R.color.amp_gray));
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
         binding.imgP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,22);
            }
        });
         binding.addimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("video/*");
                startActivityForResult(intent,10);

            }
        });
        binding.postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  dialog.show();
                final StorageReference reference = storage.getReference().child("posts")
                        .child(FirebaseAuth.getInstance().getUid())
                        .child(new Date().getTime() + "");
                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                postmodel postmodel = new postmodel();
                                postmodel.setPostImage(uri.toString());
                                postmodel.setPostVideo(uri.toString());
                                postmodel.setAbout(binding.aboutP.getText().toString());
                                postmodel.setPostedBy(FirebaseAuth.getInstance().getUid());
                                postmodel.setPrice(binding.price.getText().toString());
                                postmodel.setTime(binding.timeP.getText().toString());
                                postmodel.setLanguage(binding.languageP.getText().toString());
                                postmodel.setDuration(binding.duration.getText().toString());
                                postmodel.setPostdescription(binding.postdisc.getText().toString());//postmodel.setPostedAt(String.valueOf(new Date().getTime()));

                                database.getReference().child("posts").push()
                                        .setValue(postmodel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                NotificationModel notificationModel = new NotificationModel();
                                                notificationModel.setNotificationBy(FirebaseAuth.getInstance().getUid());
                                                notificationModel.setNotificationAt(new Date().getTime());
                                             //   notificationModel.setPostID(postid);

                                                notificationModel.setType("post");
                                                FirebaseDatabase.getInstance().getReference()
                                                        .child("notification")
                                                        .child(FirebaseAuth.getInstance().getUid())
                                                        .push()
                                                        .setValue(notificationModel);

                                                Toast.makeText(getContext(), "posted", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        });
                    }
                })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float per=(100*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                       // dialog.setMessage("Upload:"+(int)per+"%");
                    }
                });

            }

            });
        return  binding.getRoot();
}

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10) {

            if (data.getData() != null) {
                Uri uri = data.getData();
               // binding.imgP.setImageURI(uri);
                final StorageReference reference = storage.getReference().child("coverpic").child(FirebaseAuth.getInstance().getUid());
                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                       // Toast.makeText(getContext(), "Cover pic saved", Toast.LENGTH_SHORT).show();
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                database.getReference().child("postss").push().child("postImage").setValue(uri.toString());

                            }
                        });
                    }
                });
            }
        } else {

            if
            (data.getData() != null) {
                uri = data.getData();


                //  binding.postimg.setImageURI(uri);
                // binding.postimg.setVisibility(View.VISIBLE);
                binding.postbtn.setBackgroundColor(getContext().getResources().getColor(R.color.purple_700));

                //    binding.postbtn.setTextColor(getContext().getResources().getColor(R.color.black));

                //   binding.postbtn.setBackgroundDrawable(ContextCompat.getDrawable(getContext(),R.drawable.alarm));
                binding.postbtn.setTextColor(getContext().getResources().getColor(R.color.black));
                binding.postbtn.setEnabled(true);
            }

        }


    }}