package io.wcookie.com.cheers;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by wcook on 2/28/2016.
 */
public class UpdateContacts extends AppCompatActivity {

    private Button primaryContact;
    private Button secondaryContact;
    private Button submitButton;
    private static final String TAG = "UpdateContacts";
    private static final int CONTACT_PICKER_RESULT = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contacts);
        primaryContact = (Button) findViewById(R.id.primaryContact);

        //Creates contact selecter when contact "+" is clicked
        primaryContact.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                        ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
            }
        });

        submitButton = (Button) findViewById(R.id.submitButton);

        //Creates contact selecter when contact "+" is clicked
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                finish();
            }
        });

    }

//Called when contact selecter is finished and returns the email address of the contact

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CONTACT_PICKER_RESULT:
                    Cursor cursor = null;
                    String phoneNumber = "";
                    try {
                        Uri result = data.getData();
                        Log.v(TAG, "Got a contact result: "
                                + result.toString());

                        // get the contact id from the Uri
                        String id = result.getLastPathSegment();

                        // query for everything phoneNumber
                        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{id},
                                null);

                        int phoneIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA);

                        // let's just get the first phoneNumber
                        if (cursor.moveToFirst()) {
                            phoneNumber = cursor.getString(phoneIdx);
                            Log.v(TAG, "Got Phone Number: " + phoneNumber);
                        } else {
                            Log.w(TAG, "No results");
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Failed to get Phone Number data", e);
                    } finally {
                        if (cursor != null) {
                            cursor.close();
                        }
                        //EditText emailEntry = (EditText) findViewById(R.id.contactText);
                        //emailEntry.setText(phoneNumber);
                        if (phoneNumber.length() == 0) {
                            Toast.makeText(this, "No Phone Number found for contact.",
                                    Toast.LENGTH_LONG).show();
                        }

                    }

                    break;
            }
        }
    }
}


