package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.whatsapp.data.model.UserModel;
import com.example.whatsapp.ui.MainActivity;
import com.example.whatsapp.ui.Settings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class SignUp extends AppCompatActivity {

    Button sendSMS_btn,Verify_btn,anonymous;
    FirebaseAuth auth;
    EditText phoneNumberTxt,CodeTxt, MyNametxt;
    String VerificationID, code ;
    FrameLayout BarFrame;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
    UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth=FirebaseAuth.getInstance();
        sendSMS_btn=findViewById(R.id.Send_SMS);
        Verify_btn=findViewById(R.id.Verify);
       // anonymous=findViewById(R.id.anonymous);
        phoneNumberTxt=findViewById(R.id.Phone_Number);
        MyNametxt=findViewById(R.id.My_User_Name);
        CodeTxt=findViewById(R.id.Code);
        BarFrame=findViewById(R.id.frame_layout);



        checkUser();
        sendSMS_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(phoneNumberTxt.getText().toString())&&TextUtils.isEmpty(MyNametxt.getText().toString()))
                    Toast.makeText(SignUp.this, "Enter Phone Number And UserName", Toast.LENGTH_SHORT).show();
                else {
                    String Number = phoneNumberTxt.getText().toString();
                    BarFrame.setVisibility(View.VISIBLE);
                    SendVerificationCode(Number);
                    sendSMS_btn.setEnabled(false);
                    }
               // MyNametxt.setText("");

            }
        });

        Verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               code=CodeTxt.getText().toString();
                if(code == null){
                    Toast.makeText(SignUp.this, "Enter The Code", Toast.LENGTH_SHORT).show();

                }
                else
                  verifyCode(code);

            }

        });

     /*anonymous.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             SignInAnonymously();
         }
     });*/


    }

   private void SignInAnonymously() {
        auth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(SignUp.this,MainActivity.class));
                }
                else
                {
                    Toast.makeText(SignUp.this, ""+task.getException().getLocalizedMessage()
                            , Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void addUser() {

        String key = databaseReference.child("Users").push().getKey();
        if (key !=null)
            databaseReference.child("Users")
                    .child(key)
                    .setValue(new UserModel(key,"https://firebasestorage.googleapis.com/v0/b/whatsapp-4c03f.appspot.com/o/Screenshot_2021-06-02-18-43-57-38.jpg?alt=media&token=fe32838f-dc8c-4f18-b759-e6436d85d8d6",""));
    }

    private void PassingMyData(String phoneNumber, String MyName) {
        SharedPreferences sharedPreferences = getSharedPreferences("share",MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString("name",MyName);
        editor.putString("phone",phoneNumber);
        editor.apply();
    }

    private void checkUser() {
        FirebaseUser currentUser=auth.getCurrentUser();

        if(currentUser==null) {
            Toast.makeText(this, "No Users Exists", Toast.LENGTH_SHORT).show();
           }
        else{
            startActivity(new Intent(SignUp.this,MainActivity.class));
        }
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(VerificationID,code);
        signIndyCredentials(credential);
    }

    private void signIndyCredentials(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                         if(task.isSuccessful()){
                             Toast.makeText(SignUp.this, "Login Successful", Toast.LENGTH_SHORT).show();
                             Log.d("sssss",""+MyNametxt.getText().toString());
                             PassingMyData(phoneNumberTxt.getText().toString(), MyNametxt.getText().toString());
                             startActivity(new Intent(SignUp.this,MainActivity.class));
                             //addUser();
                         }
                    }
                });
    }

    private void SendVerificationCode(String phone) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber("+20"+phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
    mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
           final String code = credential.getSmsCode();
           if(code !=null){
               verifyCode(code);
           }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            Toast.makeText(SignUp.this, "Verification Failed", Toast.LENGTH_SHORT).show();
            sendSMS_btn.setEnabled(true);
        }

        @Override
        public void onCodeSent(@NonNull String s,
                @NonNull PhoneAuthProvider.ForceResendingToken token) {

            super.onCodeSent(s,token);


            VerificationID = s;

            Toast.makeText(SignUp.this, "Code Sent", Toast.LENGTH_SHORT).show();
            Verify_btn.setEnabled(true);
            BarFrame.setVisibility(View.INVISIBLE);
        }
    };


}