package com.nizsimsek.contactsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ContactsList extends AppCompatActivity {

    DbHelper dbHelper;

    ListView contactsList;
    EditText txtSearch;
    Button btnAddContact, btnDeleteAllContact, btnSearch;
    ArrayAdapter<String> adapter;
    String searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactslist);

        contactsList = findViewById(R.id.contactslist);
        btnAddContact = findViewById(R.id.btnAddContact);
        btnDeleteAllContact = findViewById(R.id.btnDeleteAllContact);
        btnSearch = findViewById(R.id.btnSearch);
        txtSearch = findViewById(R.id.txtSearch);

        btnSearch.setOnClickListener(v -> {
            searchText = txtSearch.getText().toString();
            getContactByName(searchText);
        });

        btnDeleteAllContact.setOnClickListener(v -> deleteAllContacts());

        btnAddContact.setOnClickListener(v -> {
            Intent addContactsPage = new Intent(this, AddContacts.class);
            startActivityForResult(addContactsPage, 1);
        });

        contactsList.setOnItemClickListener((parent, view, position, id) -> {
            String text = parent.getItemAtPosition(position).toString();
            String[] splitedData = text.split(": ");
            ContactDetails.selectedItemId = splitedData[1];
            Intent contactDetailsPage = new Intent(this, ContactDetails.class);
            startActivityForResult(contactDetailsPage, 1);
        });

        dbHelper = new DbHelper(this, "ContactsDb", null, 1);
        getContactNameSurname();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            getContactNameSurname();
        }
    }

    void getContactByName(String searchName) {
        SQLiteDatabase dbObject = dbHelper.getReadableDatabase();
        @SuppressLint("Recycle") Cursor reader = dbObject.rawQuery("SELECT * FROM Contacts WHERE Name LIKE'%" + searchName +"%';", null);
        List<String> datas = new ArrayList<>();
        while (reader.moveToNext()) {
            String Name = reader.getString(reader.getColumnIndex("Name")).toUpperCase();
            String Surname = reader.getString(reader.getColumnIndex("Surname")).toUpperCase();
            datas.add(Name + " " + Surname);
        }
        String[] contactsArray = new String[datas.size()];
        datas.toArray(contactsArray);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item,contactsArray);
        contactsList.setAdapter(adapter);
    }

    void getContactNameSurname() {
        SQLiteDatabase dbObject = dbHelper.getReadableDatabase();
        @SuppressLint("Recycle") Cursor reader = dbObject.rawQuery("SELECT * FROM Contacts ORDER BY Name ASC;", null);
        List<String> datas = new ArrayList<>();
        while (reader.moveToNext()) {
            int Id = reader.getInt(reader.getColumnIndex("Id"));
            String Name = reader.getString(reader.getColumnIndex("Name")).toUpperCase();
            String Surname = reader.getString(reader.getColumnIndex("Surname")).toUpperCase();
            datas.add(Name + " " + Surname + " Id : " + Id);
        }
        String[] contactsArray = new String[datas.size()];
        datas.toArray(contactsArray);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item, contactsArray);
        contactsList.setAdapter(adapter);
    }

    void deleteAllContacts() {
        SQLiteDatabase dbObject = dbHelper.getWritableDatabase();
        dbObject.delete("Contacts", null, null);
        dbObject.close();
        getContactNameSurname();
    }
}