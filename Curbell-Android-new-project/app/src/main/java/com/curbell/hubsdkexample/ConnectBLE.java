package com.curbell.hubsdkexample;

        import android.bluetooth.BluetoothAdapter;
        import android.bluetooth.BluetoothManager;
        import android.bluetooth.le.BluetoothLeScanner;
        import android.bluetooth.le.ScanCallback;
        import android.bluetooth.le.ScanFilter;
        import android.bluetooth.le.ScanResult;
        import android.bluetooth.le.ScanSettings;
        import android.content.Context;
        import android.os.SystemClock;

        import java.util.ArrayList;


public class ConnectBLE extends ScanCallback {

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothManager mBluetoothManager;
    private BluetoothLeScanner mBluetoothLEScanner;
    private ScanSettings mSettings;
    private ConnectBLECallBack mConnectBLECallBack;
    private NewMessage message;
    private Boolean connectCondition = false;
    private long elapsedTimeScanning;
    private long timeStartedScanning;

    public interface ConnectBLECallBack {
        void connected(String MAC);
        void scanFailed();
        void bluetoothNotWorking();
    }

    public ConnectBLE(Context context) {
        // grab BLE scanner
        mBluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        mBluetoothLEScanner = mBluetoothAdapter.getBluetoothLeScanner();



        // set settings to LOW LATENCY
        mSettings = new ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                .build();
    }

    public void init() {

        // create a new event message object
        message = new NewMessage();
        timeStartedScanning = SystemClock.uptimeMillis();


        mBluetoothLEScanner.startScan(new ArrayList<ScanFilter>(), mSettings, this);
    }

    @Override
    public void onScanResult(int callbackType, ScanResult result) {
        super.onScanResult(callbackType, result);


        // parse the scan result
        message.initFromScanResult(result);

        elapsedTimeScanning = (SystemClock.uptimeMillis() - timeStartedScanning) / 1000;
        // if the elapsed time scanning exceeds 3 seconds, then stop scanning
        if (elapsedTimeScanning >= 3) {
            // notify the controller that the scan failed
            mConnectBLECallBack.scanFailed();
            mBluetoothLEScanner.stopScan(this);
            System.out.println("done scanning");

        } else {
            System.out.println("scanning...");

        }
            if (connectCondition) {

                mConnectBLECallBack.connected(result.getDevice().getAddress());
                // set the MAC address of the laser

                mBluetoothLEScanner.stopScan(this);

            }

            /* if there is no connection yet, and the beef message is preset,
            and the laser time < 3 seconds,
            and its atleast 3 seconds since another laser has advertised,
            accept this new scan record as a potential new laser
            */

        }


    public void setOnRealTimeListener(ConnectBLECallBack callback) {
        try {
            mConnectBLECallBack = callback;
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()
                    + " must implement AutoConnectBLECallBack");
        }
    }

    public BluetoothLeScanner getBluetoothLEScanner() {
        return mBluetoothLEScanner;
    }

    public void setBluetoothLEScanner(BluetoothLeScanner mBluetoothLEScanner) {
        this.mBluetoothLEScanner = mBluetoothLEScanner;
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return mBluetoothAdapter;
    }

    public void setBluetoothAdapter(BluetoothAdapter mBluetoothAdapter) {
        this.mBluetoothAdapter = mBluetoothAdapter;
    }

}
