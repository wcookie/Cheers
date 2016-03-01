package io.wcookie.com.cheers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
        setContentView(R.layout.activity_registration_2);

        registerButton = (Button) findViewById(R.id.RegisterButton);
        firstName = (EditText) findViewById(R.id.FirstName);
        lastName = (EditText) findViewById(R.id.LastName);
        age = (EditText) findViewById(R.id.Age);
        weight = (EditText) findViewById(R.id.Weight);
        isMale = (RadioButton)  findViewById(R.id.radioButtonMale);
        isFemale =(RadioButton)  findViewById(R.id.radioButtonFemale);

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

        }

        android.support.v7.widget.CardView card= (android.support.v7.widget.CardView) findViewById(R.id.cv);

        card.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });

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

                    ApplicationSettings.setBooleanPref(getApplicationContext(),"registrationInfoInputted",true);
                    Intent i = new Intent(Registration.this, AddDrinks.class);
                    startActivity(i);
                }
            }
        });

    }
}
