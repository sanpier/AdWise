package sanpi.adwise.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import sanpi.adwise.R;
import sanpi.adwise.activity.CustomizeProfileActivity;
import sanpi.adwise.activity.SecurityActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final Button customizeB = (Button)findViewById(R.id.customizeB);
        final Button securityB = (Button)findViewById(R.id.securityB);
        final Button helpB = (Button)findViewById(R.id.helpB);
        final Button logoutB = (Button)findViewById(R.id.logoutB);

        //Take email
        Intent intent= getIntent();
        final String email = intent.getStringExtra("email");

        //Customize Button
        customizeB.setOnClickListener(new View.OnClickListener(){
            //On click
            @Override
            public void onClick(View v){
                Intent customizeIntent = new Intent(getApplicationContext(), CustomizeProfileActivity.class);
                customizeIntent.putExtra("email", email);
                startActivity(customizeIntent);
            }
        });

        //Security Button
        securityB.setOnClickListener(new View.OnClickListener(){
            //On click
            @Override
            public void onClick(View v){
                Intent securityIntent = new Intent(getApplicationContext(), SecurityActivity.class);
                startActivity(securityIntent);
            }
        });
    }
}
