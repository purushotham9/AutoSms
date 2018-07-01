package nnk.com.android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Setting extends Activity implements OnClickListener {
    Button save, cancel;
    EditText tv;
    SharedPreferences mysh;
    SharedPreferences.Editor edit;
    Intent it;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msgsetting);
        mysh = this.getSharedPreferences("details", MODE_PRIVATE);
        edit = mysh.edit();
        save = (Button) findViewById(R.id.save);
        cancel = (Button) findViewById(R.id.cancel);
        tv = (EditText) findViewById(R.id.msgtosend);
        tv.setText(mysh.getString("msg", "busy").toString());
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.save:
                if (!tv.getText().toString().equals("")) {
                    edit.putString("msg", tv.getText().toString());
                    edit.commit();
                    Toast.makeText(getApplicationContext(), " MESSAGE SAVED",
                            Toast.LENGTH_SHORT).show();
                    it = new Intent(Setting.this, Config.class);
                    it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(it);
                } else {

                    tv.setText("");
                    Toast.makeText(getApplicationContext(), "ENTER MESSAGE",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.cancel:
                it = new Intent(Setting.this, Config.class);
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(it);
                break;

            default:
                break;
        }

    }
}
