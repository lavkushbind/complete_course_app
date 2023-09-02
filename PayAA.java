package com.example.advncd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.advncd.notification.NotificationModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.shreyaspatil.EasyUpiPayment.EasyUpiPayment;
import com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener;
import com.shreyaspatil.EasyUpiPayment.model.TransactionDetails;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PayAA extends AppCompatActivity implements PaymentStatusListener {
    Intent intent;
    String name;
    String postid;

    private EditText nameEdt;
            EditText desct;
    private TextView transactionDetailsTV, paytext;
    String uppi= "7007605074@okbizicici";
    //String desc ;
String price;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_aa);

        intent = getIntent();
        postid= intent.getStringExtra("postid");
        Bundle bundle= getIntent().getExtras();
        price= bundle.getString("price");
//        name = intent.getStringExtra("postedBy");

        desct= findViewById(R.id.desct);

        nameEdt= findViewById(R.id.namepay);
        paytext = findViewById(R.id.paytext);
        Button makePaymentBtn = findViewById(R.id.idBtnMakePayment);
        transactionDetailsTV = findViewById(R.id.idTVTransactionDetails);
        paytext.setText(getIntent().getStringExtra("price"));

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault());
        String transcId = df.format(c);

        makePaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   String amount = amountEdt.getText().toString();
               // String upi = upiEdt.getText().toString();
               String name = nameEdt.getText().toString();

                String desc = desct.getText().toString();
                if (TextUtils.isEmpty(name)  && TextUtils.isEmpty(transcId)) {
                    Toast.makeText(PayAA.this, "Please enter all the details..", Toast.LENGTH_SHORT).show();
                } else {
                    makePayment( transcId,uppi,price, name, desc);
                }
            }
        });
    }



    private void makePayment( String transactionId ,String uppi,String price, String name, String desc) {
        final EasyUpiPayment easyUpiPayment = new EasyUpiPayment.Builder()
                .with(this)
               .setPayeeVpa(uppi)
                .setPayeeName(name)
                .setTransactionId(transactionId)
                .setTransactionRefId(transactionId)
                .setDescription(desc)
                .setAmount(price)
                .build();
        easyUpiPayment.startPayment();
        easyUpiPayment.setPaymentStatusListener(this);
    }

    @Override
    public void onTransactionCompleted(TransactionDetails transactionDetails) {
        String transcDetails = transactionDetails.getStatus().toString() + "\n" + "Transaction ID : " + transactionDetails.getTransactionId();
        transactionDetailsTV.setVisibility(View.VISIBLE);
        transactionDetailsTV.setText(transcDetails);
    }

    @Override
    public void onTransactionSuccess() {
        Toast.makeText(this, "Transaction successfully completed..", Toast.LENGTH_SHORT).show();
        NotificationModel notificationModel = new NotificationModel();
        notificationModel.setNotificationBy(FirebaseAuth.getInstance().getUid());
        notificationModel.setNotificationAt(new Date().getTime());
        notificationModel.setType("folow");
        FirebaseDatabase.getInstance().getReference()
                .child("notification")
                .child(name)
                .push()
                .setValue(notificationModel);
    }

    @Override
    public void onTransactionSubmitted() {
        Log.e("TAG", "TRANSACTION SUBMIT");
    }

    @Override
    public void onTransactionFailed() {
        Toast.makeText(this, "Failed to complete transaction", Toast.LENGTH_SHORT).show();
        NotificationModel notificationModel = new NotificationModel();
        notificationModel.setNotificationBy(FirebaseAuth.getInstance().getUid());
        notificationModel.setNotificationAt(new Date().getTime());
        notificationModel.setType("folow");
        FirebaseDatabase.getInstance().getReference()
                .child("notification")
                .child(name)
                .push()
                .setValue(notificationModel);
    }

    @Override
    public void onTransactionCancelled() {
        Toast.makeText(this, "Transaction cancelled..", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onAppNotFound() {
        Toast.makeText(this, "No app found for making transaction..", Toast.LENGTH_SHORT).show();
    }
}
