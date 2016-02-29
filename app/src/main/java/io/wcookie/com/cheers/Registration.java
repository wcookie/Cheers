package io.wcookie.com.cheers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class Registration extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText age;
    private EditText weight;
    private Button registerButton;
    private RadioButton isMale;
    private RadioButton isFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        registerButton = (Button) findViewById(R.id.RegisterButton);
        firstName = (EditText) findViewById(R.id.FirstName);
        lastName = (EditText) findViewById(R.id.LastName);
        age = (EditText) findViewById(R.id.Age);
        weight = (EditText) findViewById(R.id.Weight);
        isMale = (RadioButton)  findViewById(R.id.radioButtonMale);
        isFemale =(RadioButton)  findViewById(R.id.radioButtonFemale);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String checkFirstName = firstName.getText().toString();
                String checkLastName = lastName.getText().toString();

                int checkAge;
                int checkWeight;

                try {

                    checkAge = Integer.parseInt(age.getText().toString());
                    checkWeight = Integer.parseInt(weight.getText().toString());
                }catch(NumberFormatException e){
                    checkAge=0;
                    checkWeight=0;
                }
                boolean inputValid=true;


                if(checkFirstName.equals("")){

                    ((TextView)findViewById(R.id.firstNameError)).setText("Please input first name");
                    inputValid=false;
                }
                else{

                    ((TextView)findViewById(R.id.firstNameError)).setText("");
                    ApplicationSettings.setStringPref(getApplicationContext(),"firstName", checkFirstName);
                }
                if(checkLastName.equals("")){

                    ((TextView)findViewById(R.id.lastNameError)).setText("Please input last name");
                    inputValid=false;
                }
                else{

                    ((TextView)findViewById(R.id.lastNameError)).setText("");
                    ApplicationSettings.setStringPref(getApplicationContext(),"lastName", checkLastName);
                }

                if(checkAge<5 || checkAge >100){

                    ((TextView)findViewById(R.id.ageError)).setText("Please input valid Age");
                    inputValid=false;
                }
                else{

                    ((TextView)findViewById(R.id.ageError)).setText("");
                    ApplicationSettings.setIntPref(getApplicationContext(),"age", checkAge);
                }

                if(checkWeight<50 || checkWeight >300){

                    ((TextView)findViewById(R.id.weightError)).setText("Please input valid weight");
                    inputValid=false;
                }
                else{

                    ((TextView)findViewById(R.id.weightError)).setText("");
                    ApplicationSettings.setIntPref(getApplicationContext(),"weight", checkWeight);
                }

                ApplicationSettings.setBooleanPref(getApplicationContext(),"SexIsMale", isMale.isChecked());

                if(inputValid) {

                    ApplicationSettings.setBooleanPref(getApplicationContext(),"changeRegistrationSettings",false);
                    ApplicationSettings.setBooleanPref(getApplicationContext(),"infoInputted", true);
                    Intent i = new Intent(Registration.this, AddDrinks.class);
                    startActivity(i);
                }
            }
        });

    }

    public void onResume(){

        super.onResume();

        Log.d("Registration", "Info inputted: " + ApplicationSettings.getBooleanPref(getApplicationContext(),"infoInputted"));

        if(ApplicationSettings.getBooleanPref(getApplicationContext(),"infoInputted")){

            registerButton.setText("Update Information");
            firstName.setText(ApplicationSettings.getStringPref(getApplicationContext(),"firstName"));
            lastName.setText(ApplicationSettings.getStringPref(getApplicationContext(),"lastName"));
            age.setText(""+ApplicationSettings.getIntPref(getApplicationContext(),"age"));
            weight.setText(""+ApplicationSettings.getIntPref(getApplicationContext(),"weight"));

            if(ApplicationSettings.getBooleanPref(getApplicationContext(),"SexIsMale")) {
                isMale.setChecked(true);
                isFemale.setChecked(false);
            }

            else{
                isMale.setChecked(false);
                isFemale.setChecked(true);
            }

            if(!ApplicationSettings.getBooleanPref(getApplicationContext(),"changeRegistrationSettings")) {

                Intent i = new Intent(Registration.this, AddDrinks.class);
                startActivity(i);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }
}
