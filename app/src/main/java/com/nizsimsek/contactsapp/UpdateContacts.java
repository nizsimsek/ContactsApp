package com.nizsimsek.contactsapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import static com.nizsimsek.contactsapp.ContactDetails.selectedItemId;

public class UpdateContacts extends Activity {

    DbHelper dbHelper;

    EditText txtName, txtSurname, txtPhone, txtEmail, txtWebsite, txtAddress;
    Button btnUpdateContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatecontact);

        txtName = findViewById(R.id.txtName);
        txtSurname = findViewById(R.id.txtSurname);
        txtPhone = findViewById(R.id.txtPhone);
        txtEmail = findViewById(R.id.txtEmail);
        txtWebsite = findViewById(R.id.txtWebsite);
        txtAddress = findViewById(R.id.txtAddress);
        btnUpdateContact = findViewById(R.id.btnUpdateContact);

        btnUpdateContact.setOnClickListener(v -> {
                    updateContacts(
                            txtName.getText().toString(),
                            txtSurname.getText().toString(),
                            txtPhone.getText().toString(),
                            txtEmail.getText().toString(),
                            txtWebsite.getText().toString(),
                            txtAddress.getText().toString());
                    Intent intent = new Intent();
                    setResult(1, intent);
                    this.finish();
                }
        );
    }

    void updateContacts(String txtName, String txtSurname, String txtPhone, String txtEmail, String txtWebsite, String txtAddress) {
        dbHelper = new DbHelper(this, "ContactsDb", null, 1);
        SQLiteDatabase dbObject = dbHelper.getWritableDatabase();
        ContentValues datas = new ContentValues();
        datas.put("Name", txtName);
        datas.put("Surname", txtSurname);
        datas.put("Number", txtPhone);
        datas.put("Email", txtEmail);
        datas.put("Website", txtWebsite);
        datas.put("Address", txtAddress);
        dbObject.update("Contacts", datas, "Id = ?", new String[]{String.valueOf(selectedItemId)});
        dbObject.close();
        dbHelper.close();
    }
}
