package sanpi.adwise.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import sanpi.adwise.R;
import sanpi.adwise.adapter.QuestionAdapter;
import sanpi.adwise.app.AppConfig;
import sanpi.adwise.app.AppController;
import sanpi.adwise.domain.Choice;
import sanpi.adwise.domain.Question;

/**
 * Created by Teyfik on 28.09.2016.
 */
public class HomeActivity  extends Activity {
    private static final String TAG = HomeActivity.class.getSimpleName();
    private JSONObject obj;
    private static Context context;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.context = this;

        final ListView listView = (ListView) findViewById(R.id.list_questions);

        loadQuestions(new CallBack() {
            @Override
            public void onSuccess(ArrayList<Question> questionList) {

                QuestionAdapter customAdapter = new QuestionAdapter(questionList);

                listView.setAdapter(customAdapter);
            }

            @Override
            public void onFail(String msg) {
                // Do Stuff
            }
        });
    }

    public void  loadQuestions(final CallBack onCallBack){
        // Tag used to cancel the request
        String tag_json_arry = "json_array_req";

        final ArrayList<Question> questionList = new ArrayList<Question>();

        JsonArrayRequest req = new JsonArrayRequest(AppConfig.QUESTION_REQUEST_URL,
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
                                questionList.add(new Question(obj.getInt("id"), obj.getString("question_title"),
                                        obj.getInt("subcategory_id"), null, null,
                                        new Choice(obj.getInt("choice_1"), obj.getString("choice_1_name")),
                                        new Choice(obj.getInt("choice_2"), obj.getString("choice_2_name")),
                                        new Choice(obj.getInt("choice_3"), obj.getString("choice_3_name")),
                                        new Choice(obj.getInt("choice_4"), obj.getString("choice_4_name"))
                                ));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        onCallBack.onSuccess(questionList);

                        Log.d("onResponse: ", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("onErrorResponse: ", "Error: " + error.getMessage());
            }
        });

        // Adding request to request queue
        if(AppController.getInstance() == null)
            Log.d("HEYYYYY", "HEEEYYYYYYY");

        AppController.getInstance().addToRequestQueue(getContext(), req, tag_json_arry);
    }

    public interface CallBack {
        void onSuccess(ArrayList<Question> questionList);

        void onFail(String msg);
    }

    public static Context getContext(){
        return context;
    }
}

