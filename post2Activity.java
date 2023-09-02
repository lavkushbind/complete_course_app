package com.example.advncd;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.advncd.databinding.ActivityPost2Binding;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;
public class post2Activity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseStorage storage;
    FirebaseDatabase database;
    Intent intent;
    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer exoPlayer;
    String videoURL;
    ActivityPost2Binding binding;
    Context context;
   String postid;
   String price;
   String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post2);
        binding = ActivityPost2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intent = getIntent();
        price= intent.getStringExtra("price");
        postid = intent.getStringExtra("postid");
        name = intent.getStringExtra("postedBy");
        videoURL = intent.getStringExtra("video");
        Toast.makeText(this, postid+"show", Toast.LENGTH_SHORT).show();

        auth = FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();
        exoPlayerView = findViewById(R.id.idExoPlayerVIew);
        database.getReference()
                .child("Users")
                .child(name)
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
                            binding.usernm.setText(user.getName());


                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

        database.getReference().child("posts").child(postid)
              .addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
                  postmodel postmodel= snapshot.getValue(postmodel.class);
                  binding.TopicID.setText(postmodel.getPostdescription());
                  binding.AboutID.setText(postmodel.getAbout());
                  binding.DurationID.setText(postmodel.getDuration());
                  binding.LanguageID.setText(postmodel.getLanguage());
                 binding.PriceID.setText(postmodel.getPrice());
                  binding.AboutID.setText(postmodel.getAbout());
                  binding.TimeID.setText(postmodel.getTime());
                   //ssj= postmodel.getPostImage();
          }
          @Override
          public void onCancelled(@NonNull DatabaseError error) {
          }

      });

      try {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            Uri videouri = Uri.parse(videoURL);
            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(videouri, dataSourceFactory, extractorsFactory, null, null);
            exoPlayerView.setPlayer(exoPlayer);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(false);
        } catch (Exception e) {
            // below line is used for
            // handling our errors.
            Log.e("TAG", "Error : " + e.toString());
        }

      binding.profilepic.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent= new Intent(context,ProActivity.class);
             intent.putExtra("postid",postid);
              //intent.putExtra("ReceiversImage", users.getProfilepic());
              intent.putExtra("name",name);
              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              context.startActivity(intent);
          }
      });


        binding.usernm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context,ProActivity.class);
                // intent.putExtra("name",users.getName());
                //intent.putExtra("ReceiversImage", users.getProfilepic());
                intent.putExtra("name",name);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        binding.paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent( post2Activity.this,PayAA.class);
                intent.putExtra("price",price);
                intent.putExtra("postedBy",name);

                //   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               startActivity(intent);
                //startActivity(new Intent(post2Activity.this, PayAA.class));
            }
        });

    }}