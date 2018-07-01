package nnk.com.android.service;
import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.telephony.gsm.SmsMessage;
import android.widget.Toast;

import nnk.com.android.data.dataadapter;

@SuppressWarnings("deprecation")
public class actionservicesms extends BroadcastReceiver {
    Context context;
    SharedPreferences mysh;
    SharedPreferences.Editor edit;
    String sendnum;
    dataadapter dap;

    Bundle bundle;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        this.context = context;

        // to get latest msg
        bundle = intent.getExtras();
        mysh = context.getSharedPreferences("details", Context.MODE_PRIVATE);
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");

            SmsMessage[] smsMsg = new SmsMessage[pdus.length];
            for (int i = 0; i < smsMsg.length; i++) {
                smsMsg[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);

            }

            sendnum = smsMsg[0].getDisplayOriginatingAddress().toString();

            // active or deactive
            if (mysh.getBoolean("service", false)) {
                // starting for only sms
                if (mysh.getBoolean("rsms", false)) {
                    // for selected contacts
                    if (mysh.getBoolean("selcon", false)) {
                        dap = new dataadapter(context);
                        ArrayList<String> num = new ArrayList<String>();
                        num = dap.getDatanum();
                        dap.close();
                        for (int i = 0; i < num.size(); i++) {
                            if (sendnum.equals(num.get(i))) {
                                sendsms(sendnum);
                            }
                        }

                    } // for all contacts
                    else if (mysh.getBoolean("allcon", true)) {
                        sendsms(sendnum);
                    }
                    // end for sms
                }

            }
        }

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

}

