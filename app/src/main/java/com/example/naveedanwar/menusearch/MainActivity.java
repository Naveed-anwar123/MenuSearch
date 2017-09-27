package com.example.naveedanwar.menusearch;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView tvSearch;
    private ListView listView;
    private ContactListAdapter contactListAdapter;
    private ArrayList<Contacts> arrayList ;
    private SQLiteDatabase dbStudents;
    ArrayList<Contacts> arrayListContacts ;
    private ProgressBar spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvSearch = (TextView) findViewById(R.id.tvSearch);
        listView = (ListView)findViewById(R.id.lvdata);
        dbStudents = openOrCreateDatabase("contacts_db", MODE_PRIVATE, null);

        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        arrayList =  new ArrayList<Contacts>();
        Cursor c = dbStudents.rawQuery("SELECT id, name,status from contacts", null);
        c.moveToFirst();

        for (int i = 0; i < c.getCount(); i++) {
            Contacts contacts = new Contacts();
            contacts.id = c.getInt(0);
            contacts.name = c.getString(1);
            contacts.status = c.getString(2);
            arrayList.add(contacts);
            c.moveToNext();
        }

        contactListAdapter = new ContactListAdapter(this, arrayList);
        listView.setAdapter(contactListAdapter);
        contactListAdapter.notifyDataSetChanged();
        arrayListContacts = new ArrayList<Contacts>();
        for (int i = 0 ; i < arrayList.size() ; i++){
            arrayListContacts.add(arrayList.get(i));
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItemCompat.OnActionExpandListener expandListener = new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                if (item.getItemId() == R.id.search) {
                    tvSearch.setText("Search is Stopped...");
                }
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                if (item.getItemId() == R.id.search) {
                    tvSearch.setText("While Editing...");
                }
                return true;
            }
        };
        MenuItem searchitem = menu.findItem(R.id.search);  //1 get menu item reference
//        MenuItemCompat.setOnActionExpandListener(searchitem,expandListener);   // 2 set above expand listner
        SearchView searchView = (SearchView) searchitem.getActionView(); //  3
        SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                tvSearch.setText("On Submit: "+query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                tvSearch.setText("While Editing: "+newText);


                searchFromDb(newText);
                spinner.setVisibility(View.VISIBLE);






                return true;
            }
        };
        searchView.setOnQueryTextListener(onQueryTextListener);
        return super.onCreateOptionsMenu(menu);
    }


    private void searchFromDb(String newText){
        Cursor c = dbStudents.rawQuery("SELECT id, name,status from contacts where name='"+newText+"'", null);
        c.moveToFirst();
        if(c.getCount()>0){
            arrayList.clear();
        }
        for (int i = 0; i < c.getCount(); i++) {
            Contacts contacts = new Contacts();
            contacts.id = c.getInt(0);
            contacts.name = c.getString(1);
            contacts.status = c.getString(2);
            arrayList.add(contacts);
            c.moveToNext();
        }


        contactListAdapter.notifyDataSetChanged();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.addcontacts){
             startActivity(new Intent(MainActivity.this,AddContactsActivity.class));
        }
        else if (item.getItemId() ==R.id.call){
            makeCall();
        }
        else if (item.getItemId() == R.id.scroll){
            startActivity(new Intent(MainActivity.this,ScrollingActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void makeCall(){
        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
        phoneIntent.setData(Uri.parse("tel:03039092082"));
        try {
            startActivity(phoneIntent);
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "Call faild, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }
}
