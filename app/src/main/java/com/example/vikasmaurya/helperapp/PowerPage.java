package com.example.vikasmaurya.helperapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PowerPage extends AppCompatActivity {

    private DatabaseReference myRef;
    private ListView listview;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_page);
        myRef = FirebaseDatabase.getInstance().getReference();
        listview =(ListView)findViewById(R.id.list_view);
        TextView power = (TextView)findViewById(R.id.power);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);

        listview.setAdapter(adapter);
        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton);
      /*  final int tog=1;*/
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    // The toggle is enabled
                    myRef.child("Toggle").setValue("1");

                } else {
                    // The toggle is disabled
                    myRef.child("Toggle").setValue("0");

                }
            }
        });

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

               /* Float string = dataSnapshot.getValue(Float.class);
                arrayList.add(new String(String.valueOf(string)));
                adapter.notifyDataSetChanged();*/
                    String string = dataSnapshot.getValue(String.class);
                arrayList.add(string);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                String string = dataSnapshot.getValue(String.class);
                arrayList.remove(string);
                adapter.notifyDataSetChanged();

              /*  String string = dataSnapshot.getValue(String.class);
                arrayList.add(string);
                adapter.notifyDataSetChanged();*/


            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    public void btnRefresh(View view) {
        Intent i = new Intent(PowerPage.this, PowerPage.class);
        finish();
        overridePendingTransition(0, 0);
        startActivity(i);
        overridePendingTransition(0, 0);
    }
}
