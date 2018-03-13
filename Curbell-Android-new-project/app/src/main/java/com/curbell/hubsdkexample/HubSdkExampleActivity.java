package com.curbell.hubsdkexample;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.curbell.services.hub.Hub;
import com.curbell.services.hub.HubBroadcast;
import com.curbell.services.hub.HubException;

/**
 * A class that demonstrates the usage of the {@link Hub} class and the classes
 * contained within the 'com.curbell.service.hub' package in the Curbell Hardware SDK.
 * <p>
 * Copyright (C) 2017 Curbell Medical Products
 *
 * @author Aaron Roberts
 * @version 0.5
 */
public class HubSdkExampleActivity extends AppCompatActivity {
//    /**
//     * The string to identify this module in logcat.
//     */
//    private static final String TAG = HubSdkExampleActivity.class.getName();
//
//    /**
//     * TextView instance for the information/error log box
//     **/
//    private TextView errorText;
//
//    /**
//     * Exit button which will close the app
//     **/
//    private TextView exitButton;
//
//    /**
//     * The {@link Hub} instance to access the hub functionality.
//     */
//    private Hub hub;
//
//    /**
//     * BroadcastReceiver instance
//     **/
//    private BroadcastReceiver receiver;
    private ImageButton androidButton;
    private ConnectBLE BLE;
    private ConnectCallback callback;
    private BluetoothAdapter mBluetoothAdapter;


    public interface ConnectCallback {
        void scanFailed();
        void BLENotWorking();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        BLE = new ConnectBLE(this);

        androidButton = (ImageButton) findViewById(R.id.android_button);
        androidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // initialize the autoconnect BLE
                System.out.println("button clicked");

                BLE.init();

                if (BLE.getBluetoothLEScanner() == null){
                    System.out.println("BLUE TOOTH ADAPTER IS NULL");
                    callback.BLENotWorking();
                } else {
                    if (!BLE.getBluetoothAdapter().isEnabled()) {
                        callback.BLENotWorking();

                    } else {

                        BLE.setOnRealTimeListener(new ConnectBLE.ConnectBLECallBack() {
                            @Override
                            public void connected(String MAC) {


                            }


                            @Override
                            public void scanFailed() {

                            }

                            @Override
                            public void bluetoothNotWorking() {
                                callback.BLENotWorking();
                            }
                        });
                    }
                }
            }
        });


//        errorText = (TextView) this.findViewById(R.id.errorLog);
//        errorText.setMovementMethod(new ScrollingMovementMethod());
//        exitButton = (TextView) this.findViewById(R.id.exitButton);
//        exitButton.setOnClickListener(exitListener);
//
//
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(HubBroadcast.HUB_CONNECTED);
//        filter.addAction(HubBroadcast.HUB_DISCONNECTED);
//        filter.addAction(HubBroadcast.HUB_INFO_UPDATED);
//        filter.addAction(HubBroadcast.ROOM_INFO_UPDATED);
//        filter.addAction(HubBroadcast.TV_INFO_UPDATED);
//
//
//        try {
//            hub = new Hub();
//        } catch (HubException he) {
//            Toast.makeText(this,
//                    "Error creating Hub instance: " + he.getMessage(),
//                    Toast.LENGTH_LONG).show();
//            updateLogArea("Error creating Hub instance: " + he.getMessage());
//            return;
//        }
//
//        /** Setup BroadcastReceiver to be able to receive broadcasts from {@link HubBroadcast} **/
//        receiver = new BroadcastReceiver()
//        {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                // Perform actions when an Intent is received
//                String action = intent.getAction();
//                Log.d(TAG, "Received broadcast: " + intent.getAction());
//
//                switch( intent.getAction() )
//                {
//                    case HubBroadcast.HUB_CONNECTED:
//                        Toast.makeText( HubSdkExampleActivity.this,
//                                        "Hub connected!",
//                                        Toast.LENGTH_LONG ).show();
//                        updateLogArea( "Hub Connected" );
//                        break;
//
//                    case HubBroadcast.HUB_DISCONNECTED:
//                        Toast.makeText( HubSdkExampleActivity.this,
//                                        "Hub disconnected!",
//                                        Toast.LENGTH_LONG ).show();
//                        updateLogArea( "Hub Disconnected" );
//                        break;
//
//                    case HubBroadcast.HUB_INFO_UPDATED:
//                        Toast.makeText( HubSdkExampleActivity.this,
//                                        "Hub info updated!",
//                                        Toast.LENGTH_LONG ).show();
//                        updateLogArea( "Hub info updated" );
//                        handleIntentWithExtras( intent );
//                        break;
//
//                    case HubBroadcast.ROOM_INFO_UPDATED:
//                        Toast.makeText( HubSdkExampleActivity.this,
//                                        "Room info updated!",
//                                        Toast.LENGTH_LONG ).show();
//                        updateLogArea( "Room info updated" );
//                        handleIntentWithExtras( intent );
//                        break;
//
//                    case HubBroadcast.TV_INFO_UPDATED:
//                        Toast.makeText( HubSdkExampleActivity.this,
//                                        "TV info updated!",
//                                        Toast.LENGTH_LONG ).show();
//                        updateLogArea( "TV info updated" );
//                        handleIntentWithExtras( intent );
//                        break;
//                }
//            }
//        };
//
//
//        registerReceiver(receiver, filter);
    }

//    /**
//     * Examines the extras that may be bundled with an {@link Intent}.
//     *
//     * @param intent The {@link Intent} to examine.
//     */
//    private void handleIntentWithExtras( Intent intent )
//    {
//        Bundle extras = intent.getExtras();
//        for ( String extra : HubBroadcast.getIntentExtras( intent.getAction() ) )
//        {
//            if ( extras.containsKey( extra ) )
//            {
//                updateLogArea( "Found key: " + extra + ", value= " + extras.get( extra ).toString() );
//            }
//        }
//    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();


        mBluetoothAdapter = ((BluetoothManager) getSystemService(BLUETOOTH_SERVICE)).getAdapter();
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            //Bluetooth is disabled
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBtIntent);
            finish();
            return;
        }

        /*
         * Check for Bluetooth LE Support.  In production, our manifest entry will keep this
         * from installing on these devices, but this will allow test devices or other
         * sideloads to report whether or not the feature exists.
         */
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "No LE Support.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

    }

//    public View.OnClickListener exitListener = new View.OnClickListener() {
//        public void onClick(View v) {
//            finish();
//        }
//    };


//    /**
//     * Get current time in human-readable form without spaces and special characters.
//     *
//     * @return current time as a string. [7:00:05]
//     */
//    public static String getTimeStamp() {
//        Time now = new Time();
//        now.setToNow();
//        String sTime = now.format("%H:%M:%S");
//
//        return sTime;
//    }
//
//    /**
//     * Updated thes error log area with the error or information passed into as error
//     *
//     * @param error
//     */
//
//    public void updateLogArea(String error) {
//        final String message = "[" + getTimeStamp() + "]: " + error;
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                errorText.append('\n' + message);
//                errorText.invalidate();
//
//            }
//        });
//    }



}

