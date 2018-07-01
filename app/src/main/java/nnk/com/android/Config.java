package nnk.com.android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

public class Config extends Activity implements OnClickListener {
    CheckBox rcall, rsms;
    Button save, cancel, msgset, conset;
    RadioButton selcon, allcon;
    Intent it;
    SharedPreferences mysh;
    SharedPreferences.Editor edit;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config);

        // sharedperf obj
        mysh = this.getSharedPreferences("details", MODE_PRIVATE);
        edit = mysh.edit();

        // objects of item on iu
        rcall = (CheckBox) findViewById(R.id.callonly);
        rsms = (CheckBox) findViewById(R.id.smsonly);
        msgset = (Button) findViewById(R.id.msgset);
        conset = (Button) findViewById(R.id.conset);
        save = (Button) findViewById(R.id.save);
        cancel = (Button) findViewById(R.id.cancel);
        selcon = (RadioButton) findViewById(R.id.selcon);
        allcon = (RadioButton) findViewById(R.id.allcon);

        // actions to buttons
        msgset.setOnClickListener(this);
        conset.setOnClickListener(this);
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);

        // settings for checks and radios
        rcall.setChecked(mysh.getBoolean("rcall", false));
        rsms.setChecked(mysh.getBoolean("rsms", true));
        selcon.setChecked(mysh.getBoolean("selcon", true));
        allcon.setChecked(mysh.getBoolean("allcon", false));
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.msgset:
                sets();
                it = new Intent(Config.this, Setting.class);
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(it);
                break;
            case R.id.save:
                sets();

                Toast.makeText(Config.this, "SETTINGS SAVED", Toast.LENGTH_SHORT)
                        .show();
                it = new Intent(Config.this, AutoSmsActivity.class);
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(it);
                break;
            case R.id.cancel:
                it = new Intent(Config.this, AutoSmsActivity.class);
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(it);
                break;
            case R.id.conset:
                sets();
                it = new Intent(Config.this, Contacts.class);
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(it);
                break;
            default:
                break;
        }

    }

    public void sets() {
        if (rcall.isChecked()) {
            edit.putBoolean("rcall", true);

        } else {
            edit.putBoolean("rcall", false);
        }
        if (rsms.isChecked()) {
            edit.putBoolean("rsms", true);
        } else {
            edit.putBoolean("rsms", false);
        }
        if (selcon.isChecked()) {
            edit.putBoolean("selcon", true);
            edit.putBoolean("allcon", false);
        } else {
            edit.putBoolean("allcon", true);
            edit.putBoolean("selcon", false);
        }
        edit.commit();

    }
}
