package com.example.vikasmaurya.helperapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {
/*    private RewardedVideoAd mRewardedVideoAd;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }




    public void goWelcome (View view){
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
    }
    public void goRegister(View view){
        Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
}
