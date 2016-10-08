package sanpi.adwise.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import sanpi.adwise.helper.SQLiteHandler;
import sanpi.adwise.helper.SessionManager;
import sanpi.adwise.request.LoginRequest;
import sanpi.adwise.R;

public class LoginActivity extends AppCompatActivity {

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText emailLoginT = (EditText)findViewById(R.id.emailLoginT);
        final EditText passLoginT = (EditText)findViewById(R.id.passLoginT);
        final Button loginB = (Button)findViewById(R.id.loginB);
        final TextView clickToSign = (TextView)findViewById(R.id.clickToSign);

        Intent intent = getIntent();
        String emailIN = intent.getStringExtra("email");
        String passIN = intent.getStringExtra("password");
        emailLoginT.setText(emailIN);
        passLoginT.setText(passIN);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent_login = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent_login);
            finish();
        }

        //Sign up Button
        clickToSign.setOnClickListener(new View.OnClickListener(){
            //On click
            @Override
            public void onClick(View v){
                Intent signUpIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(signUpIntent);
            }
        });

        loginB.setOnClickListener(new View.OnClickListener(){
            //On click
            @Override
            public void onClick(View v){
                final String email = emailLoginT.getText().toString();
                final String password = passLoginT.getText().toString();

                Response.Listener<String> listener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try{
                            JSONObject loginObj = new JSONObject(response);//String response (array) converter
                            boolean success = loginObj.getBoolean("success");

                            if(success){
                                session.setLogin(true);

                                String name = loginObj.getString("name");
                                String surname = loginObj.getString("surname");
                                int uid = loginObj.getInt("id");

                                db.addUser(name, surname, uid, email);

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                LoginActivity.this.startActivity(intent);

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Failed");
                                builder.setNegativeButton("Retry", null);
                                builder.create();
                                builder.show();
                            }

                        }catch(JSONException exc){
                            exc.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(email, password, listener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }
}