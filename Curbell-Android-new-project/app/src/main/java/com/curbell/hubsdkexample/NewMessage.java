package com.curbell.hubsdkexample;


        import android.bluetooth.le.ScanResult;


/**
 * Created by jsherman on 7/27/17.
 */

public class NewMessage {

    private String currentMACAddress;
    private String hex;
    private char buttonState;




    public NewMessage(){
        this.currentMACAddress = "";

    }

    public String getCurrentMACAddress() {
        return currentMACAddress;
    }

    public void setCurrentMACAddress(String currentMACAddress) {
        this.currentMACAddress = currentMACAddress;
    }

    public void initFromScanResult(ScanResult result){

    }


}
