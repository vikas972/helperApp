package com.example.vikasmaurya.helperapp;


import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.view.View.INVISIBLE;
import static android.widget.Toast.LENGTH_LONG;


public class SosActivity extends AppCompatActivity {

    private LocationManager locationMangaer = null;
    private LocationListener locationListener = null;

    int i;

    private Button btnGetLocation = null;
    private EditText editLocation = null;
    private ProgressBar pb = null;

    private static final String TAG = "Debug";
    private Boolean flag = false;

    String s;

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        pb = findViewById(R.id.progressBar1);
        pb.setVisibility(INVISIBLE);
        editLocation = findViewById(R.id.editTextLocation);

        btnGetLocation = findViewById(R.id.btnLocation);
        //  btnGetLocation.setOnClickListener(this);

        locationMangaer = (LocationManager) getSystemService(Context.LOCATION_SERVICE);



        //Location Permission
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }

        // Sms Permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }



    }

    /*@Override
    public void onClick(View view) {

        flag = displaySpsStatus();

        if (flag) {

            editLocation.setText("Please!! move your device to" + " see the changes in coordinates." + "\nWait..");
            pb.setVisibility(View.VISIBLE);
            locationListener = new MyLocationListener();

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }

          //  locationMangaer.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            locationMangaer.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } else {
            alertbox("GPS status!!","Your GPS is: OFF" );

        }
    }*/

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN) {

            i++;
            if (i == 3) {

                flag = displaySpsStatus();

                if (flag) {

                    editLocation.setText("Please!! move your device to" + " see the changes in coordinates." + "\nWait..");
                    pb.setVisibility(View.VISIBLE);
                    locationListener = new MyLocationListener();

                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }

                    //  locationMangaer.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    locationMangaer.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                } else {
                    alertbox("GPS status!!","Your GPS is: OFF" );

                }

            } else if (i > 3){
                i = 0;

            }
        }
        return super.onKeyDown(keyCode, event);
    }









    private  Boolean displaySpsStatus(){
        ContentResolver contentResolver = getBaseContext().getContentResolver();
        boolean gpsStatus = Settings.Secure.isLocationProviderEnabled(contentResolver,LocationManager.GPS_PROVIDER);
        if(gpsStatus){
            return true;
        }
        else{
            return false;
        }
    }
    protected void alertbox(String title, String mymessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your Device's GPS is Disable").setCancelable(false).setTitle("** Gps Status **")
                .setPositiveButton("Gps On", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // finish the current activity
                        // AlertBoxAdvance.this.finish();
                        Intent myIntent = new Intent(
                                Settings.ACTION_SECURITY_SETTINGS);
                        startActivity(myIntent);
                        dialog.cancel();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // cancel the dialog box
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /*----------Listener class to get coordinates ------------- */
    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location loc) {

            editLocation.setText("");
            pb.setVisibility(View.INVISIBLE);
            // Toast.makeText(getBaseContext(),"Location changed : Lat: " + loc.getLatitude()+ " Lng: " + loc.getLongitude(),Toast.LENGTH_SHORT).show();
            String longitude = "Longitude: " +loc.getLongitude();
            Log.v(TAG, longitude);
            String latitude = "Latitude: " +loc.getLatitude();
            Log.v(TAG, latitude);

            /*----------to get City-Name from coordinates ------------- */
            String cityName=null;
            Geocoder gcd = new Geocoder(getBaseContext(),
                    Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(loc.getLatitude(), loc
                        .getLongitude(), 1);
                if (addresses.size() > 0)
                    System.out.println(addresses.get(0).getAddressLine(0)+", "+ addresses.get(0).getAddressLine(1)+", "+addresses.get(0).getAddressLine(2));
                cityName=addresses.get(0).getAddressLine(0);
            } catch (Exception e) {
                e.printStackTrace();
            }

            s = longitude+"\n"+latitude + "\n\nMy Current City is: "+cityName;
            editLocation.setText(s);
            Toast.makeText(getApplicationContext(),"this is working",Toast.LENGTH_SHORT).show();
            sendSMS("8424886778", "i need help .\n im here \n"+s);
            locationMangaer.removeUpdates(locationListener);


        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(SosActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onStatusChanged(String provider,int status, Bundle extras) {
            // TODO Auto-generated method stub
        }
    }




        @Override
        public void onRequestPermissionsResult ( int requestCode, String permissions[],
        int[] grantResults){
            switch (requestCode) {
                case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage("5535", null, s, null, null);
                        Toast.makeText(getApplicationContext(), "SMS sent.", LENGTH_LONG).show();
                        Log.v("sms", s);


                    } else {
                        Toast.makeText(getApplicationContext(), "SMS failed, please try again.", LENGTH_LONG).show();
                        return;
                    }
                }

            }

        }

    private void sendSMS(String phoneNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
        Log.v("sms",message);
        Toast.makeText(getApplicationContext(), "SMS sent.", LENGTH_LONG).show();
    }

}

