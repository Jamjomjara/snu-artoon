package com.baryberri.a12_2_broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView currentStatus;
    TextView adapterStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentStatus = (TextView)findViewById(R.id.currentStatus);
        adapterStatus = (TextView)findViewById(R.id.adapterStatus);
    }

    // The broadcast receiver catches the broadcast.
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
                int remain = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                String remainString = Integer.toString(remain) + "%";
                currentStatus.setText(remainString);

                int plugIn = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
                switch (plugIn) {
                    case 0:
                        adapterStatus.setText("No Connection");
                        break;

                    case BatteryManager.BATTERY_PLUGGED_AC:
                        adapterStatus.setText("Adapter Connected");
                        break;

                    case BatteryManager.BATTERY_PLUGGED_USB:
                        adapterStatus.setText("USB Connected");
                        break;
                }
            }
        }
    };

    // Register which broadcast receiver this app should listen to.


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(broadcastReceiver, intentFilter);
    }
}
