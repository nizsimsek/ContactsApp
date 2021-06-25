package com.nizsimsek.contactsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ContactDetails extends AppCompatActivity {

    DbHelper dbHelper;

    Button btnUpdateContact, btnDeleteContact;
    TextView txtName, txtSurname, txtPhone, txtEmail, txtWebsite, txtAddress;

    static public String selectedItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactdetails);

        txtName = findViewById(R.id.txtName);
        txtSurname = findViewById(R.id.txtSurname);
        txtPhone = findViewById(R.id.txtPhone);
        txtEmail = findViewById(R.id.txtEmail);
        txtWebsite = findViewById(R.id.txtWebsite);
        txtAddress = findViewById(R.id.txtAddress);

        btnUpdateContact = findViewById(R.id.btnUpdateContact);
        btnDeleteContact = findViewById(R.id.btnDeleteContact);

        btnUpdateContact.setOnClickListener(v -> {
            Intent contactDetailsPage = new Intent(this, UpdateContacts.class);
            startActivityForResult(contactDetailsPage, 1);
            Intent intent = new Intent();
            setResult(1, intent);
            this.finish();
        });

        btnDeleteContact.setOnClickListener(v -> {
            deleteContact(selectedItemId);
            Intent intent = new Intent();
            setResult(1, intent);
            this.finish();
        });

        dbHelper = new DbHelper(this, "ContactsDb", null, 1);

        getContacts(selectedItemId);
    }

    void getContacts(String selectedItemId) {
        SQLiteDatabase dbObject = dbHelper.getReadableDatabase();
        @SuppressLint("Recycle") Cursor reader = dbObject.rawQuery("SELECT * FROM Contacts WHERE Id = " + selectedItemId + ";", null);
        while (reader.moveToNext()) {
            txtName.setText(reader.getString(reader.getColumnIndex("Name")));
            txtSurname.setText(reader.getString(reader.getColumnIndex("Surname")));
            txtPhone.setText(reader.getString(reader.getColumnIndex("Number")));
            txtEmail.setText(reader.getString(reader.getColumnIndex("Email")));
            txtWebsite.setText(reader.getString(reader.getColumnIndex("Website")));
            txtAddress.setText(reader.getString(reader.getColumnIndex("Address")));
        }
        reader.close();
        dbObject.close();
        dbHelper.close();
    }

    void deleteContact(String selectedItemId) {
        SQLiteDatabase dbObject = dbHelper.getWritableDatabase();
        dbObject.delete("Contacts", "Id" + " = ?",
                new String[]{String.valueOf(selectedItemId)});
        dbObject.close();
    }
}