package com.example.a2davaa02.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    Sensor accel, magneticField;
    float[] accelValues,magFieldValues,orientationMatrix,orientaitons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SensorManager sMgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accel = sMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magneticField=sMgr.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        sMgr.registerListener(this, accel, SensorManager.SENSOR_DELAY_UI);
        sMgr.registerListener(this, magneticField, SensorManager.SENSOR_DELAY_UI);

        accelValues=new float[3];
        magFieldValues=new float[3];
        orientationMatrix=new float[16];
        orientaitons=new float[3];

    }

    public void onSensorChanged(SensorEvent ev)
    {
        //update ui with the new sensor
        DecimalFormat df=new DecimalFormat("#.##");

        TextView xacc=(TextView)findViewById(R.id.xacc);
        TextView yacc=(TextView)findViewById(R.id.yacc);
        TextView zacc=(TextView)findViewById(R.id.zacc);
        /*
        xacc.setText(df.format(ev.values[0]));
        yacc.setText(df.format(ev.values[1]));
        zacc.setText(df.format(ev.values[2]));
        */
        if( ev.sensor == accel)
        {
            for(int i=0;i<3;i++) {
                accelValues[i] = ev.values[i];
            }
        }
        else if(ev.sensor == magneticField)
        {
            for(int i=0;i<3;i++) {
                magFieldValues[i] = ev.values[i];
            }
        }
        SensorManager.getRotationMatrix(orientationMatrix,null,accelValues,magFieldValues);
        SensorManager.getOrientation(orientationMatrix,orientaitons);

        xacc.setText(df.format(orientaitons[0]*(180/Math.PI))); //AZIMUTH -COMPASS BEARING - X INCLINATION
        yacc.setText(df.format(orientaitons[1]*(180/Math.PI))); //PITCH -Y INCLINATION
        zacc.setText(df.format(orientaitons[2]*(180/Math.PI))); //ROLL  -Z INCLINATION
    }
    public void onAccuracyChanged(Sensor sensor,int acc)
    {

    }
}
