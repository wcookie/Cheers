package io.wcookie.com.cheers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class AddDrinks extends AppCompatActivity {

    private Button plusButton;
    private Button minusButton;
    private TextView drinkCount;
    private int numberOfDrinks;
    private TextView intoxicationLevel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drinks);

        plusButton = (Button) findViewById(R.id.plusButton);
        minusButton = (Button) findViewById(R.id.minusButton);
        drinkCount = (TextView) findViewById(R.id.drinkCount);
        intoxicationLevel = (TextView) findViewById(R.id.intoxicationLevel);
        numberOfDrinks=0;

        plusButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                numberOfDrinks++;
                drinkCount.setText(""+numberOfDrinks);
                intoxicationLevel.setText(checkDrunkeness());
            }

        });

        minusButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(numberOfDrinks>0) {
                    numberOfDrinks--;
                    drinkCount.setText(""+numberOfDrinks);
                    intoxicationLevel.setText(checkDrunkeness());
                }
            }

        });

    }

    public void onResume(){

        super.onResume();

        Log.d("AddDrinks.java", "registrationInfoInputted: " +ApplicationSettings.getBooleanPref(getApplicationContext(),"registrationInfoInputted"));

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

        if(numberOfDrinks<5)
            toReturn="Sober";
        else if(numberOfDrinks<10)
            toReturn="Tipsy";
        else if(numberOfDrinks<15)
            toReturn="Drunk";
        else
            toReturn="Call for Help";

        return toReturn;
    }
}
