package nnk.com.android.data;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dataadapter extends SQLiteOpenHelper {
    SQLiteDatabase mydb;
    String table_name = "list";
    static String database_name = "contacts";
    static int version = 1;

    public dataadapter(Context context) {
        super(context, database_name, null, version);
        // TODO Auto-generated constructor stub
        mydb = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("create table if not exists " + table_name
                + "(name varchar,mno varchar)");

    }

    // get mobile nos from data base
    public ArrayList<String> getData() {

        ArrayList<String> name = new ArrayList<String>();
        Cursor c = mydb.rawQuery("select * from " + table_name, null);

        if (c != null) {
            while (c.moveToNext()) {

                String a = c.getString(c.getColumnIndex("name"));

                name.add(a);

            }

        }
        return name;

    }

    public ArrayList<String> getDatanum() {
        // TODO Auto-generated method stub

        ArrayList<String> num = new ArrayList<String>();
        Cursor c = mydb.rawQuery("select * from " + table_name, null);

        if (c != null) {
            while (c.moveToNext()) {

                String a = c.getString(c.getColumnIndex("mno"));

                num.add(a);

            }

        }
        return num;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

    public void insert(ArrayList<String> data, ArrayList<String> num) {
        // TODO Auto-generated method stub
        mydb.execSQL("delete from " + table_name);
        if (data.size() >= 0 && num.size() == data.size()) {

            for (int i = 0; i < data.size(); i++) {

                mydb.execSQL("insert into " + table_name + " values('"
                        + data.get(i) + "','" + num.get(i) + "')");
            }
        } else {
            System.out.print("not equal");
        }
    }

    public void close() {

        mydb.close();

    }

}
