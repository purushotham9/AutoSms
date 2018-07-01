package nnk.com.android;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import nnk.com.android.data.dataadapter;

public class Contacts extends Activity implements OnClickListener {
    Button addcon, selcon, ok, back;
    dataadapter dap;
    ArrayList<String> name;

    ArrayList<String> data;
    ListView lv;
    adapter adp;
    ArrayAdapter<String> aadp;
    Intent it;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);

        // database adapter
        dap = new dataadapter(Contacts.this);

        // objects for buttons
        addcon = (Button) findViewById(R.id.contacts);
        ok = (Button) findViewById(R.id.save);
        selcon = (Button) findViewById(R.id.selected);
        back = (Button) findViewById(R.id.back);

        // action to buttons
        addcon.setOnClickListener(this);
        selcon.setOnClickListener(this);
        ok.setOnClickListener(this);
        back.setOnClickListener(this);

        // array list declaration
        name = new ArrayList<String>();

        data = new ArrayList<String>();
        data = dap.getData();

        // contacts
        ContentResolver cr = this.getContentResolver();
        // object cursor for contacts
        Cursor cur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        if (cur.moveToFirst()) {
            do {

                name.add(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)).toString());// contacts name it will return to
                // arraylist

            } while (cur.moveToNext());
        }
        // setting list view
        lv = (ListView) findViewById(R.id.listView1);

        // selected contacts
        aadp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);

        lv.setAdapter(aadp);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.contacts:
                // add or edit
                adp = new adapter();
                lv.setAdapter(adp);
                break;
            case R.id.back:
                // back to main screen

                it = new Intent(Contacts.this, Config.class);
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(it);
                break;
            case R.id.selected:
                // selected contacts
                aadp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);

                lv.setAdapter(aadp);

                break;
            case R.id.save:
                // saving contacts in dataabase
                ArrayList<String> num = new ArrayList<String>();
                ContentResolver cr = getContentResolver();
                Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
                String id, Name, ph = "";

                if (cur.moveToFirst()) {
                    do {
                        id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                        Name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        int hasno = cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        for (int i = 0; i < data.size(); i++) {

                            if (Name.equalsIgnoreCase(data.get(i).toString())) {

                                if (hasno == 1) {
                                    Cursor pcur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{id}, null);
                                    if (pcur.moveToFirst()) {
                                        ph = pcur.getString(pcur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                        String[] s1 = ph.split("-");
                                        ph = "";
                                        for (int j = 0; j < s1.length; j++) {

                                            ph += s1[j].trim();
                                        }
                                        num.add(ph);
                                    }
                                }

                            }

                        }

                    } while (cur.moveToNext());
                }

                dap.insert(data, num);

                Toast.makeText(Contacts.this, "CONTACTS SAVED", Toast.LENGTH_SHORT).show();
                it = new Intent(Contacts.this, Config.class);
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(it);

                break;

            default:
                break;
        }

    }

    class adapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return name.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            LayoutInflater lf = getLayoutInflater();
            convertView = lf.inflate(R.layout.checklist, null);
            final TextView tv = (TextView) convertView.findViewById(R.id.cname);
            final CheckBox cb = (CheckBox) convertView.findViewById(R.id.check);
            tv.setText(name.get(position));
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).equals(tv.getText())) {
                    cb.setChecked(true);

                }

            }

            cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String s = (String) tv.getText();
                    // TODO Auto-generated method stub
                    if (cb.isChecked()) {

                        data.add(s);

                    } else {

                        for (int i = 0; i < data.size(); i++) {

                            if (s.equals(data.get(i))) {
                                data.remove(i);

                            }
                        }
                    }
                }
            });
            return convertView;
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        dap.close();
    }

}
