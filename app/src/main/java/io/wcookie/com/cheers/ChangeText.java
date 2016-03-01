package io.wcookie.com.cheers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChangeText extends AppCompatActivity {

    private Button saveTextMessage;
    private EditText textMessageEdit;
    private TextView InformationTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_texts);

        textMessageEdit=(EditText) findViewById(R.id.textMessageEdit);

        saveTextMessage = (Button) findViewById(R.id.saveTextMessage);

        //Creates contact selecter when contact "+" is clicked
        saveTextMessage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                ApplicationSettings.setStringPref(getApplicationContext(),"defaultTextMessage", textMessageEdit.getText().toString());
                finish();
            }
        });

        InformationTitle= (TextView) findViewById(R.id.InformationTitle);

        InformationTitle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });

    }

}
