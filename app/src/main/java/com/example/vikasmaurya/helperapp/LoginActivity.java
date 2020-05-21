package com.example.vikasmaurya.helperapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
//import android.support.annotation.NonNull;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtEmailLogin;
    private EditText txtPwd;
    private FirebaseAuth firebaseAuth;
    TextView btnSignup,btnForgotPass;
    RelativeLayout activity_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtEmailLogin =(EditText)findViewById(R.id.emailEditText);
        txtPwd =(EditText) findViewById(R.id.passwordEditText);
        activity_main = (RelativeLayout)findViewById(R.id.activity_main);
        btnForgotPass = (TextView)findViewById(R.id.login_btn_forgot_password);
        btnSignup = (TextView)findViewById(R.id.login_btn_signup);
        firebaseAuth =FirebaseAuth.getInstance();

        btnForgotPass.setOnClickListener(this);
        btnSignup.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.login_btn_forgot_password)
        {
            startActivity(new Intent(LoginActivity.this,ForgotPassword.class));
            finish();
        }
        else if(view.getId() == R.id.login_btn_signup)
        {
            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            finish();
        }

    }

    public void btnLoginUser (View view){
        //final ProgressDialog progressDialog =ProgressDialog.show(LoginActivity.this,"Please Wait....","Processing...",true);

        (firebaseAuth.signInWithEmailAndPassword(txtEmailLogin.getText().toString(),txtPwd.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this,PowerPage.class);
                            startActivity(intent);
                        }else{

                            if(txtPwd.getText().toString().length() < 6)
                            {
                                Snackbar snackBar = Snackbar.make(activity_main,"Password length must be over 6",Snackbar.LENGTH_SHORT);
                                snackBar.show();
                            }
                            Log.e("Error",task.getException().toString());
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();


                        }
                    }
                });
    }
}
