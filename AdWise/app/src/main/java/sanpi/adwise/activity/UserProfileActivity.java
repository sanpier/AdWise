package sanpi.adwise.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import sanpi.adwise.R;
import sanpi.adwise.activity.SettingsActivity;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        final TextView nameText = (TextView)findViewById(R.id.nameT);
        final TextView followingT = (TextView)findViewById(R.id.followingT);
        final TextView followerT = (TextView)findViewById(R.id.followersT);
        final TextView accuracyT = (TextView)findViewById(R.id.accuracyT);
        final TextView marginalityT = (TextView)findViewById(R.id.marginalityT);
        final TextView predictionT = (TextView)findViewById(R.id.predictionT);
        final ImageView profilPicture = (ImageView)findViewById(R.id.profilPicture);
        final Button settingsB = (Button)findViewById(R.id.settingsB);

        //Take information of User
        Intent intent= getIntent();
        String name = intent.getStringExtra("name") + " " + intent.getStringExtra("surname");
        final String email = intent.getStringExtra("email");
        nameText.setText("  " + name);

        //Customize Button
        settingsB.setOnClickListener(new View.OnClickListener(){
            //On click
            @Override
            public void onClick(View v){
                Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                settingsIntent.putExtra("email", email);
                startActivity(settingsIntent);
            }
        });

    }
}
