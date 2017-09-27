package com.example.naveedanwar.menusearch;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Naveed Anwar on 24/09/2017.
 */

public class ContactListAdapter  extends ArrayAdapter {

    private Context context;
    private ArrayList<Contacts> contacts;

    public ContactListAdapter(Context context, ArrayList<Contacts> contacts) {
        super(context, R.layout.custom_list_view, contacts);
        this.context = context;
        this.contacts = contacts;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = null;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listItemView = inflater.inflate(R.layout.custom_list_view, null);
        }
        else {
            listItemView = convertView;
        }

        Contacts s = contacts.get(position);

        ((TextView)listItemView.findViewById(R.id.tvName)).setText(s.name);
        ((TextView)listItemView.findViewById(R.id.tvStatus)).setText(s.status);
        return listItemView;
    }
}
