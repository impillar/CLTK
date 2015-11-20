package edu.ntu.cltk.android;

import android.content.Context;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class WifiUtil {

    /**
     * Get the name of the connected wifi
     * Need the permission "android.permission.ACCESS_WIFI_STATE"
     *
     * @param context
     * @return
     */
    public static String getWifiName(Context context) {
        String ssid = "none";
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (WifiInfo.getDetailedStateOf(wifiInfo.getSupplicantState()) == NetworkInfo.DetailedState.CONNECTED) {
            ssid = wifiInfo.getSSID();
        }
        return ssid;
    }

    /**
     * Get the state of wifi
     *
     * @param context
     * @return
     */
    public static int getWifiState(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.getWifiState();
    }

}
