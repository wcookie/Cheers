package io.wcookie.com.cheers;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class AddDrinks extends AppCompatActivity {

    private ImageButton plusButton;
    private ImageButton minusButton;
    private TextView drinkCount;
    private int numberOfDrinks;
    private ImageButton primaryPhone;
    private ImageButton secondaryPhone;
    private ImageButton redWatchBandPhone;
    private ImageButton primaryText;
    private ImageButton secondaryText;
    private ImageButton redWatchBandText;
    private TextView intoxicationLevel;
    private TextView primaryContact;
    private TextView secondaryContact;
    private TextView redWatchBand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drinks);

        plusButton = (ImageButton) findViewById(R.id.plusButton);
        minusButton = (ImageButton) findViewById(R.id.minusButton);
        drinkCount = (TextView) findViewById(R.id.drinkCount);
        intoxicationLevel = (TextView) findViewById(R.id.intoxicationLevel);
        primaryContact = (TextView)findViewById(R.id.contactFriend1);
        secondaryContact = (TextView)findViewById(R.id.contactFriend2);
        redWatchBand = (TextView)findViewById(R.id.contactRedWatchBand);
        primaryPhone = (ImageButton)findViewById(R.id.callFriend);
        secondaryPhone = (ImageButton)findViewById(R.id.callFriend2);
        redWatchBandPhone = (ImageButton)findViewById(R.id.callRedWatchBand);
        primaryText = (ImageButton)findViewById(R.id.msgFriend);
        secondaryText = (ImageButton)findViewById(R.id.msgFriend2);
        redWatchBandText = (ImageButton)findViewById(R.id.msgRedWatchBand);


        numberOfDrinks=ApplicationSettings.getIntPref(getApplicationContext(),"numberOfDrinks");

        if(numberOfDrinks<0)
            numberOfDrinks=0;

        drinkCount.setText(""+numberOfDrinks);
        intoxicationLevel.setText(checkDrunkeness());

        String temp =ApplicationSettings.getStringPref(getApplicationContext(),"primaryName");
        if(temp!=null)
            primaryContact.setText(temp);

        temp =ApplicationSettings.getStringPref(getApplicationContext(),"secondaryName");

        if(temp!=null)
            secondaryContact.setText(temp);

        plusButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                numberOfDrinks++;
                drinkCount.setText(""+numberOfDrinks);
                intoxicationLevel.setText(checkDrunkeness());
                ApplicationSettings.setIntPref(getApplicationContext(),"numberOfDrinks", numberOfDrinks);
            }

        });

        minusButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(numberOfDrinks>0) {
                    numberOfDrinks--;
                    drinkCount.setText(""+numberOfDrinks);
                    ApplicationSettings.setIntPref(getApplicationContext(),"numberOfDrinks", numberOfDrinks);
                    intoxicationLevel.setText(checkDrunkeness());
                }
            }

        });

        primaryPhone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + ApplicationSettings.getStringPref(getApplicationContext(),"primaryNumber")));
                int res = getApplicationContext().checkCallingOrSelfPermission("android.permission.CALL_PHONE");
                if (res == PackageManager.PERMISSION_GRANTED)
                    startActivity(callIntent);
            }

        });

        secondaryPhone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + ApplicationSettings.getStringPref(getApplicationContext(),"secondaryNumber")));
                int res = getApplicationContext().checkCallingOrSelfPermission("android.permission.CALL_PHONE");
                if (res == PackageManager.PERMISSION_GRANTED)
                    startActivity(callIntent);
            }

        });



    }

    public void onResume(){

        super.onResume();

        Log.d("AddDrinks.java", "registrationInfoInputted: " + ApplicationSettings.getBooleanPref(getApplicationContext(), "registrationInfoInputted"));

        if(!ApplicationSettings.getBooleanPref(getApplicationContext(),"registrationInfoInputted")){

            Intent i = new Intent(AddDrinks.this, Registration.class);
            startActivity(i);
        }

        if(!ApplicationSettings.getBooleanPref(getApplicationContext(),"contactInfoInputted")){

            Intent i = new Intent(AddDrinks.this, UpdateContacts.class);
            startActivity(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_drinks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_changeRegistration) {

            Intent i = new Intent(AddDrinks.this, Registration.class);
            startActivity(i);
        }

        else if(id == R.id.action_changeContacts){

            Intent i = new Intent(AddDrinks.this, UpdateContacts.class);
            startActivity(i);
        }

        else if(id == R.id.action_changeText){

            Intent i = new Intent(AddDrinks.this, ChangeText.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    private String checkDrunkeness(){

        String toReturn;
        if(numberOfDrinks<5) {
            toReturn = "Sober";
            primaryContact.setTextColor(Color.parseColor("#000000"));
            secondaryContact.setTextColor(Color.parseColor("#000000"));
            redWatchBand.setTextColor(Color.parseColor("#000000"));
        }
        else if(numberOfDrinks<10) {
            toReturn = "Tipsy";
            primaryContact.setTextColor(Color.parseColor("#FF0000"));
            secondaryContact.setTextColor(Color.parseColor("#FF0000"));
            redWatchBand.setTextColor(Color.parseColor("#000000"));
        }
        else if(numberOfDrinks<15) {
            toReturn = "Drunk";
            primaryContact.setTextColor(Color.parseColor("#FF0000"));
            secondaryContact.setTextColor(Color.parseColor("#FF0000"));
            redWatchBand.setTextColor(Color.parseColor("#FF0000"));
        }
        else {
            primaryContact.setTextColor(Color.parseColor("#000000"));
            secondaryContact.setTextColor(Color.parseColor("#000000"));
            redWatchBand.setTextColor(Color.parseColor("#FF0000"));
            toReturn = "Call for Help";
        }

        return toReturn;
    }
}
