package com.example.naveedanwar.menusearch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by Naveed Anwar on 24/09/2017.
 */

public class AddContactsActivity extends AppCompatActivity implements  AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {


    private Button btnadd;
    private EditText etname,etstatus;
    private ProgressDialog pg;
    private SQLiteDatabase dbStudents;
    private ArrayList<Contacts> contactsArraylist;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        etname = (EditText)findViewById(R.id.etname);
        etstatus = (EditText)findViewById(R.id.etstatus);
        btnadd = (Button)findViewById(R.id.btnadd);
        pg = new ProgressDialog(this);
        pg.setTitle("Loading");
        pg.setMessage("Saving Your Data...");

        btnadd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String name = etname.getText().toString().trim();
                String status = etstatus.getText().toString().trim();
                if(!name.equals("") && !status.equals("")){
                    pg.show();
                    addtoList(name,status);
                }
                else {
                    Toast.makeText(AddContactsActivity.this,"Name or status field is empty",Toast.LENGTH_LONG).show();
                }

            }
        });
        dbStudents = openOrCreateDatabase("contacts_db", MODE_PRIVATE, null);
        dbStudents.execSQL("CREATE TABLE IF NOT EXISTS contacts" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, status TEXT);");
        contactsArraylist = new ArrayList<Contacts>();
    }

    private void addtoList(String name, String status){
        dbStudents.execSQL("INSERT INTO contacts VALUES(NULL, '"+name+"', '"+status+"');");
         pg.dismiss();
         startActivity(new Intent(AddContactsActivity.this,MainActivity.class));
         finish();
//        Toast.makeText(AddContactsActivity.this,name+" "+ status,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        return false;
    }
}
