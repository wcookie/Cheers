package io.wcookie.com.cheers;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by wcook on 2/28/2016.
 */
public class UpdateContacts extends AppCompatActivity {

    private Button primaryContact;
    private Button secondaryContact;
    private Button submitButton;
    private TextView primaryTextView;
    private TextView secondaryTextView;

    private static final String TAG = "UpdateContacts";
    static final int PICK_CONTACT_PRIMARY=1;
    static final int PICK_CONTACT_SECONDARY=2;

    private String primaryName;
    private String primaryNumber;
    private String secondaryName;
    private String secondaryNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contacts);

        primaryContact = (Button) findViewById(R.id.primaryContact);

        //Creates contact selecter when contact "+" is clicked
        primaryContact.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT_PRIMARY);
            }
        });

        primaryTextView = (TextView)  findViewById(R.id.primaryTextView);


        secondaryContact = (Button) findViewById(R.id.secondaryContact);

        secondaryContact.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT_SECONDARY);
            }
        });

        secondaryTextView = (TextView)  findViewById(R.id.secondaryTextView);

        submitButton = (Button) findViewById(R.id.submitButton);



        //Creates contact selecter when contact "+" is clicked
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                ApplicationSettings.setBooleanPref(getApplicationContext(),"contactInfoInputted",true);
                ApplicationSettings.setStringPref(getApplicationContext(),"primaryName",primaryName);
                ApplicationSettings.setStringPref(getApplicationContext(),"primaryNumber",primaryNumber);
                ApplicationSettings.setStringPref(getApplicationContext(),"secondaryName",secondaryName);
                ApplicationSettings.setStringPref(getApplicationContext(),"secondaryNumber",secondaryNumber);
                finish();
            }
        });

        if(ApplicationSettings.getBooleanPref(getApplicationContext(),"contactInfoInputted")){

            primaryName = ApplicationSettings.getStringPref(getApplicationContext(),"primaryName");
            primaryNumber= ApplicationSettings.getStringPref(getApplicationContext(),"primaryNumber");
            secondaryName= ApplicationSettings.getStringPref(getApplicationContext(),"secondaryName");
            secondaryNumber= ApplicationSettings.getStringPref(getApplicationContext(),"secondaryNumber");

            if(primaryName!=null)
                primaryTextView.setText(primaryName + ": " + primaryNumber);

            if(secondaryName!=null)
                secondaryTextView.setText(secondaryName + ": " + secondaryNumber);

        }

    }

    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT_PRIMARY) :
                if (resultCode == Activity.RESULT_OK) {

                    Uri contactData = data.getData();
                    Cursor c =  managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {


                        String id =c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone =c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));


                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,
                                    null, null);
                            phones.moveToFirst();
                            primaryNumber = phones.getString(phones.getColumnIndex("data1"));

                        }
                        primaryName = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                        primaryTextView.setText(primaryName + ": " + primaryNumber);

                    }
                }
                break;
            case(PICK_CONTACT_SECONDARY):

                if (resultCode == Activity.RESULT_OK) {

                    Uri contactData = data.getData();
                    Cursor c =  managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {


                        String id =c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone =c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));


                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,
                                    null, null);
                            phones.moveToFirst();
                            secondaryNumber = phones.getString(phones.getColumnIndex("data1"));

                        }
                        secondaryName = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                        secondaryTextView.setText(secondaryName + ": " + secondaryNumber);
                    }
                }
                break;
        }
    }

}


