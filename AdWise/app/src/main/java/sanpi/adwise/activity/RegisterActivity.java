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

import sanpi.adwise.R;
import sanpi.adwise.request.RegisterRequest;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText nameT = (EditText)findViewById(R.id.nameT);
        final EditText surnameT = (EditText)findViewById(R.id.surnameT);
        final EditText emailT = (EditText)findViewById(R.id.emailT);
        final EditText passT = (EditText)findViewById(R.id.passT);
        final EditText passRT = (EditText)findViewById(R.id.passRT);
        final Button signupB = (Button)findViewById(R.id.signupB);
        final TextView informer = (TextView)findViewById(R.id.informer);
        informer.setText("");

        signupB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String name = nameT.getText().toString();
                final String surname = surnameT.getText().toString();
                final String email = emailT.getText().toString();
                final String password = passT.getText().toString();
                final String rePassword = passRT.getText().toString();
                boolean successfulEmailRegister = false;
                boolean successfulEmptyRegister = false;

                if(!(email.endsWith("@ug.bilkent.edu.tr") || email.endsWith("@bilkent.edu.tr"))){
                    informer.setText("@bilkent mail is required!");
                    successfulEmailRegister = false;
                }else{
                    successfulEmailRegister = true;
                }
                if(name == null || name.length() == 0 || surname == null || surname.length() == 0
                        || email == null || email.length() == 0 || password == null || password.length() == 0){
                    successfulEmptyRegister = false;
                    informer.setText("Information can not be empty!");
                }else{
                    successfulEmptyRegister = true;
                }

                if(password.equalsIgnoreCase(rePassword)){

                    final Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    intent.putExtra("email",email);
                                    intent.putExtra("password",password);
                                    RegisterActivity.this.startActivity(intent);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("Register Failed");
                                    builder.setNegativeButton("Retry", null);
                                    builder.create();
                                    builder.show();
                                }
                            } catch (JSONException exc) {
                                exc.printStackTrace();
                            }
                        }
                    };

                    if(successfulEmptyRegister && successfulEmailRegister){
                        RegisterRequest registerRequest = new RegisterRequest(name, surname, email, password, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                        queue.add(registerRequest);
                    }

                } else{
                    informer.setText("Passwords are not match!");
                }
            }
        });
    }
}
