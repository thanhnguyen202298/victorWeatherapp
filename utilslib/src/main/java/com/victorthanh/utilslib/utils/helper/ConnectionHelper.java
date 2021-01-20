package com.victorthanh.utilslib.utils.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.victorthanh.utilslib.network.NetworkUtils;

public class ConnectionHelper {

    public static final String ACTION_WIFI_CONNECTION = "android.net.wifi.WIFI_STATE_CHANGED";
    public static final String ACTION_CONNECTION = "android.net.conn.CONNECTIVITY_CHANGE";

    private ConnectionReceiver mConnectionReceiver;
    private Context mContext;
    private ShowConnectionListener mListener;

    public interface ShowConnectionListener {
        void onShowConnection(boolean isConnection);
    }

    public ConnectionHelper(Context m_context, ShowConnectionListener listener) {
        this.mContext = m_context;
        this.mListener = listener;
    }

    public void registerBroadcastConnection() {
        if (mConnectionReceiver == null) {
            mConnectionReceiver = new ConnectionReceiver() {
                @Override
                public void handleConnection(int statusConnection) {
                    if (statusConnection == NetworkUtils.NETWORK_STATUS_NOT_CONNECTED) {
                        mListener.onShowConnection(false);
                    } else {
                        mListener.onShowConnection(true);
                    }
                }
            };
        }
        IntentFilter intentFilter = new IntentFilter(ACTION_WIFI_CONNECTION);
        intentFilter.addAction(ACTION_CONNECTION);
        mContext.registerReceiver(mConnectionReceiver, intentFilter);
    }


    public void unregisterBroadcastConnection(Context context) {
        if (mConnectionReceiver != null) {
            context.unregisterReceiver(mConnectionReceiver);
            mConnectionReceiver = null;
        }
    }

    public static class ConnectionReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int statusConnection = NetworkUtils.getConnectivityStatusString(context);
            handleConnection(statusConnection);
        }

        public void handleConnection(int statusConnection) {
        }
    }
}
