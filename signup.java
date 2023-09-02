package com.example.advncd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.advncd.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {
    FirebaseAuth auth;
    ActivitySignupBinding binding;
    EditText passbtn, emailbtn,namebtn;
    Button signupbtn;
    FirebaseDatabase database;
    TextView textView;
   // private FirebaseDatabase db = FirebaseDatabase.getInstance();
   //private DatabaseReference root = db.getReference().child("Users");


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       setContentView(R.layout.activity_signup);
       binding= ActivitySignupBinding.inflate(getLayoutInflater()) ;
       setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        textView= findViewById(R.id.golog);
        passbtn = findViewById(R.id.pasbtn);
        namebtn =findViewById(R.id .namebtn);
       emailbtn = findViewById(R.id.emailbtn);
       signupbtn = findViewById(R.id.signupbtn);
       binding.golog.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(signup.this, login.class));
           }
       });
       binding.signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
              public void onClick(View v) {
                String email,pass,name;

                email=emailbtn.getText().toString();
                pass=passbtn.getText().toString();
                name=namebtn.getText().toString();

              //  final Users user = new Users(binding.namebtn.getText().toString(), binding.emailbtn.getText().toString(), binding.pasbtn.getText().toString());


             //   user.getEmail(email);
               // user.getPass(pass);
               // user.setName(name);
            auth.createUserWithEmailAndPassword(email,pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        if(task.isSuccessful())  {
                    modelfast users = new modelfast(name,email,pass);
                 String id = task.getResult().getUser().getUid();
                    database.getReference().child("Users").child(id).setValue(users);
                         /*   database.collection("Users").document().set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    HashMap<String, String>userMap= new HashMap<>();
                                    userMap.put("name",name);
                                    userMap.put("email",email);
                                    userMap.put("pass",pass);
                                    root.push().setValue(userMap);
*/
                                    startActivity(new Intent(signup.this, MainActivity.class));
                               //}
                            //});
                        }
                        else{
                            Toast.makeText(signup.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

}