package nnk.com.android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AutoSmsActivity extends Activity implements OnClickListener {
    Intent it;
    ToggleButton service;
    Button settings;
    SharedPreferences mysh;
    SharedPreferences.Editor edit;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mysh = this.getSharedPreferences("details", MODE_PRIVATE);
        edit = mysh.edit();

        if (mysh.getString("set", "ok").toString().equals("ok")) {
            edit.putString("set", "set");
            edit.putString("msg", "busy");
            edit.putBoolean("rcall", false);
            edit.putBoolean("rsms", true);
            edit.putBoolean("selcon", true);
            edit.putBoolean("allcon", false);
            edit.commit();
        }

        service = (ToggleButton) findViewById(R.id.service);
        service.setChecked(mysh.getBoolean("service", false));

        settings = (Button) findViewById(R.id.settings);
        service.setOnClickListener(this);
        settings.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.service:
                if (service.getText().equals("ACTIVE")) {
                    edit.putBoolean("service", false);
                    Toast.makeText(AutoSmsActivity.this, "DE-ACTIVATED",
                            Toast.LENGTH_SHORT).show();
                } else {
                    edit.putBoolean("service", true);
                    Toast.makeText(AutoSmsActivity.this, "ACTIVATED",
                            Toast.LENGTH_SHORT).show();

                }

                edit.commit();
                break;
            case R.id.settings:
                it = new Intent(AutoSmsActivity.this, Config.class);

                startActivity(it);
                break;
            default:
                break;
        }
    }
}
