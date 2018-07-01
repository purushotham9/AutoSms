package nnk.com.android.service;
import java.lang.reflect.Method;
import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsManager;
import android.widget.Toast;

import nnk.com.android.data.dataadapter;
import nnk.com.android.data.internal.telephony.ITelephony;

@SuppressWarnings("deprecation")
public class actionservicecall extends BroadcastReceiver {
    Context context;
    SharedPreferences mysh;
    SharedPreferences.Editor edit;
    dataadapter dap;
    TelephonyManager tm;
    ITelephony tservice;
    Bundle bundle;
    ArrayList<String> nums;
    String phoneNr;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        bundle = intent.getExtras();
        dap = new dataadapter(context);
        phoneNr = bundle.getString("incoming_number");
        mysh = context.getSharedPreferences("details", Context.MODE_PRIVATE);

        // call handling
        if (bundle != null) {
            if (mysh.getBoolean("service", false)) {
                // strating for only call
                if (mysh.getBoolean("rcall", false)) {
                    // getting phone state
                    String state = bundle
                            .getString(TelephonyManager.EXTRA_STATE);

                    if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                        if (mysh.getBoolean("selcon", false)) {
                            nums = new ArrayList<String>();
                            nums = dap.getDatanum();

                            for (int i = 0; i < nums.size(); i++) {
                                if (phoneNr.equals(nums.get(i))) {
                                    // to send sms
                                    sendsms(phoneNr);
                                    // to disconnect the call
                                    disconnect();
                                }
                            }

                        } else if (mysh.getBoolean("allcon", true)) {
                            // to send sms
                            sendsms(phoneNr);
                            // to disconnect the call
                            disconnect();
                        }
                    }
                }
            }
        }
        dap.close();
    }

    // for sending sms
    public void sendsms(String mno) {
        try {
            mysh = context
                    .getSharedPreferences("details", Context.MODE_PRIVATE);
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(mno, null, mysh.getString("msg", "busy")
                    .toString(), null, null);
        } catch (Exception e) {

            Toast.makeText(context, "MSG NOT SEND", Toast.LENGTH_SHORT).show();
        }

    }

    // disconnecting the call
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void disconnect() {
        tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Class c = Class.forName(tm.getClass().getName());
            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            tservice = (ITelephony) m.invoke(tm);
            tservice.endCall();
        } catch (Exception e) {

        }

    }

}
