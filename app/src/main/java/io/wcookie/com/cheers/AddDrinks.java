package io.wcookie.com.cheers;

import android.content.Intent;
import android.graphics.Color;
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
        numberOfDrinks=ApplicationSettings.getIntPref(getApplicationContext(),"numberOfDrinks");
        drinkCount.setText(""+numberOfDrinks);
        intoxicationLevel.setText(checkDrunkeness());

        primaryContact.setText(ApplicationSettings.getStringPref(getApplicationContext(),"primaryName"));
        secondaryContact.setText(ApplicationSettings.getStringPref(getApplicationContext(),"secondaryName"));

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
