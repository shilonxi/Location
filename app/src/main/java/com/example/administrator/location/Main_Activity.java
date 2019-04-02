package com.example.administrator.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class Main_Activity extends AppCompatActivity
{
    LocationManager locationManager;
    //定义LocationManager对象
    EditText longitude;
    EditText latitude;
    EditText altitude;
    EditText bearing;
    //定义组件

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        longitude=(EditText)findViewById(R.id.longitude);
        latitude=(EditText)findViewById(R.id.latitude);
        altitude=(EditText)findViewById(R.id.altitude);
        bearing=(EditText)findViewById(R.id.bearing);
        //获取实例
        locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //创建LocationManager对象
        if(ContextCompat.checkSelfPermission(Main_Activity.this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {}
        else
        {
            ActivityCompat.requestPermissions(Main_Activity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        //运行时权限
        if(!locationManager.isProviderEnabled(locationManager.GPS_PROVIDER))
            Toast.makeText(Main_Activity.this,"请打开GPS!",Toast.LENGTH_SHORT).show();
        //监听GPS是否打开
        Location location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //从GPS获取最近的定位信息
        updateView(location);
        //更新显示
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 8, new LocationListener()
        {
            @Override
            public void onLocationChanged(Location location)
            {
                updateView(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras)
            {

            }

            @Override
            public void onProviderEnabled(String provider)
            {
                if(ContextCompat.checkSelfPermission(Main_Activity.this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
                {}
                else
                {
                    ActivityCompat.requestPermissions(Main_Activity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
                }
                //运行时权限
                updateView(locationManager.getLastKnownLocation(provider));
            }

            @Override
            public void onProviderDisabled(String provider)
            {
                updateView(null);
            }
        });
        //每三秒获取一次GPS定位信息
    }

    public void updateView(Location location)
    {
        if(location!=null)
        {
            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append(location.getLatitude());
            latitude.setText(stringBuilder.toString());
            stringBuilder=new StringBuilder();
            stringBuilder.append(location.getLongitude());
            longitude.setText(stringBuilder.toString());
            stringBuilder=new StringBuilder();
            stringBuilder.append(location.getAltitude());
            altitude.setText(stringBuilder.toString());
            stringBuilder=new StringBuilder();
            stringBuilder.append(location.getBearing());
            bearing.setText(stringBuilder.toString());
        }
        else
        {
            latitude.setText("");
            longitude.setText("");
            altitude.setText("");
            bearing.setText("");
        }
    }
    //更新显示
}
