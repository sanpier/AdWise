package sanpi.adwise.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import sanpi.adwise.R;
import sanpi.adwise.helper.RequestHandler;
import sanpi.adwise.app.AppConfig;
import sanpi.adwise.app.AppController;
import sanpi.adwise.request.CustomizeRequest;
import sanpi.adwise.request.ImageUploadRequest;

public class CustomizeProfileActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String UPLOAD_URL = "http://adwiseapp.com/myphp/UploadImage.php";
    public static final String UPLOAD_KEY = "image";
    public static final String TAG = "MY MESSAGE";

    private Bitmap bitmap;

    private static final int RESULT_LOAD_IMAGE = 1;
    Button selectB;
    Button uploadB;
    Button applyB;
    ImageView profilView;

    //Gender
    private static RadioGroup radio_g;
    private static RadioButton radioB;
    String gender;

    //Birthdate
    EditText dateT;
    String birthdate;

    //City
    Spinner spinner;
    String city;

    //Department
    Spinner spinner2;
    String department;

    //Call Back
    private JSONObject obj;
    private static Context context;

    String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_profile);
        this.context = this;

        //Take email
        Intent intent= getIntent();
        email = intent.getStringExtra("email");

        //Image
        selectB = (Button)findViewById(R.id.selectB);
        uploadB = (Button)findViewById(R.id.uploadB);
        applyB  = (Button)findViewById(R.id.applyB);
        selectB.setOnClickListener(this);
        uploadB.setOnClickListener(this);
        applyB.setOnClickListener(this);

        //Gender
        radio_g = (RadioGroup)findViewById(R.id.radio_g);

        //Birthdate
        dateT = (EditText)findViewById(R.id.dateT);
        dateT.addTextChangedListener(new TextWatcher() {
            int len = 0;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String str = dateT.getText().toString();
                len = str.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = dateT.getText().toString();
                if((str.length()==2 && len<str.length())||(str.length()==5 && len<str.length())){
                    dateT.append("/");
                }
            }
        });

        //City
        spinner = (Spinner)findViewById(R.id.spinner);
        loadCities(new CallBack() {
            @Override
            public void onSuccess(ArrayList<String>  cityList) {

                String[] cityArray = new String[81];
                for(int i = 0; i < cityArray.length; i++){
                    cityArray[i] = cityList.get(i);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_item, cityArray);
                spinner.setAdapter(adapter);
            }

            @Override
            public void onFail(String msg) {
                // Do Stuff
            }
        });

        //Department
        spinner2 = (Spinner)findViewById(R.id.spinner2);
        loadDepartments(new CallBack() {
            @Override
            public void onSuccess(ArrayList<String>  departmentList) {

                String[] departmentArray = new String[departmentList.size()];
                for(int i = 0; i < departmentArray.length; i++){
                    departmentArray[i] = departmentList.get(i);
                }
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_item, departmentArray);
                spinner2.setAdapter(adapter2);
            }

            @Override
            public void onFail(String msg) {
                // Do Stuff
            }
        });

        //onClickListenerButton();
    }

    //On click
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.selectB:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);
                break;
            case R.id.uploadB:
                uploadImage();
                break;
            case R.id.applyB:
                //Gender
                if(radio_g.isSelected())
                {
                    int selected_id = radio_g.getCheckedRadioButtonId();
                    radioB = (RadioButton)findViewById(selected_id);
                    gender = radioB.getText().toString();
                }
                else
                    gender = "";

                //Birthdate
                if(dateT.getText() != null)
                    birthdate = dateT.getText().toString();
                else
                    birthdate = "";

                //City
                city = spinner.getSelectedItem().toString();
                //Department
                department = spinner2.getSelectedItem().toString();

                //Apply Changes
                final Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                Intent intent = new Intent(CustomizeProfileActivity.this, SettingsActivity.class);
                                CustomizeProfileActivity.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(CustomizeProfileActivity.this);
                                builder.setMessage("Changes could not applied!");
                                builder.setNegativeButton("Retry", null);
                                builder.create();
                                builder.show();
                            }
                        } catch (JSONException exc) {
                            exc.printStackTrace();
                        }
                    }
                };

                CustomizeRequest customizeRequest = new CustomizeRequest(email, gender, birthdate, department, city, responseListener);
                RequestQueue queue = Volley.newRequestQueue(CustomizeProfileActivity.this);
                queue.add(customizeRequest);

                /*finish();
                startActivity(getIntent());*/

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){
        class UploadImage extends AsyncTask<Bitmap,Void,String>{

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(CustomizeProfileActivity.this, "Uploading Image", "Please wait...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];

                if(bitmap != null) {
                    String image = getStringImage(bitmap);
                    //Image Upload
                    final Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                    Intent intent = new Intent(CustomizeProfileActivity.this, CustomizeProfileActivity.class);
                                    CustomizeProfileActivity.this.startActivity(intent);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(CustomizeProfileActivity.this);
                                    builder.setMessage("Image could not uploaded!");
                                    builder.setNegativeButton("Retry", null);
                                    builder.create();
                                    builder.show();
                                }
                            } catch (JSONException exc) {
                                exc.printStackTrace();
                            }
                        }
                    };

                    ImageUploadRequest imageUploadRequest = new ImageUploadRequest(email, image, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(CustomizeProfileActivity.this);
                    queue.add(imageUploadRequest);

                    return "Upload process is completed";
                }

                return "No image to upload!";
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }

    public void  loadCities(final CallBack onCallBack){
        // Tag used to cancel the request
        String tag_json_arry = "json_array_req";
        final ArrayList<String> cityList = new ArrayList<String>();

        JsonArrayRequest req = new JsonArrayRequest(AppConfig.CITY_REQUEST_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        obj = new JSONObject();

                        for(int i=0; i<response.length(); i++)
                        {
                            try {
                                obj = response.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                cityList.add(obj.getString("name"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        onCallBack.onSuccess(cityList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("onErrorResponse: ", "Error: " + error.getMessage());
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(getContext(), req, tag_json_arry);
    }

    public void  loadDepartments(final CallBack onCallBack){
        // Tag used to cancel the request
        String tag_json_arry = "json_array_req";
        final ArrayList<String> departmentList = new ArrayList<String>();

        JsonArrayRequest req2 = new JsonArrayRequest(AppConfig.DEPARTMENT_REQUEST_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        obj = new JSONObject();

                        for(int i=0; i<response.length(); i++)
                        {
                            try {
                                obj = response.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                departmentList.add(obj.getString("name"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        onCallBack.onSuccess(departmentList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("onErrorResponse: ", "Error: " + error.getMessage());
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(getContext(), req2, tag_json_arry);
    }

    public interface CallBack {
        void onSuccess(ArrayList<String> cityList);

        void onFail(String msg);
    }

    public static Context getContext(){
        return context;
    }
}