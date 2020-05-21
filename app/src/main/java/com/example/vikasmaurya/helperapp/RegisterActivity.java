package com.example.vikasmaurya.helperapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtEmailAddress;
    private EditText txtPassword;
    TextView btnLogin,btnForgotPass;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtEmailAddress = (EditText) findViewById(R.id.signup_email);
        txtPassword =(EditText) findViewById(R.id.signup_password);
        btnLogin = (TextView)findViewById(R.id.signup_btn_login);
        btnForgotPass = (TextView)findViewById(R.id.signup_btn_forgot_pass);




        btnLogin.setOnClickListener(this);
        btnForgotPass.setOnClickListener(this);
        firebaseAuth =FirebaseAuth.getInstance();
    }
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.signup_btn_login){
            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            finish();
        }
        else if(view.getId() == R.id.signup_btn_forgot_pass){
            startActivity(new Intent(RegisterActivity.this,ForgotPassword.class));
            finish();
        }

    }
    public void btnRegistrationUser (View view){

        final ProgressDialog progressDialog= ProgressDialog.show(RegisterActivity.this,"Please Wait...","Processing...",true);
        ( firebaseAuth.createUserWithEmailAndPassword(txtEmailAddress.getText().toString(),txtPassword.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               progressDialog.dismiss();
               if(task.isSuccessful()){
                   Toast.makeText(RegisterActivity.this,"Registration Successfull",Toast.LENGTH_LONG).show();
                   Intent intent =new Intent(RegisterActivity.this,LoginActivity.class);
                   startActivity(intent);
               }
               else{
                     Log.e("Error",task.getException().toString());
                   Toast.makeText(RegisterActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();


               }
            }
        });

    }
}
