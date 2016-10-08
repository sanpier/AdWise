package sanpi.adwise.adapter;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sanpi.adwise.activity.HomeActivity;
import sanpi.adwise.R;
import sanpi.adwise.app.AppConfig;
import sanpi.adwise.app.AppController;
import sanpi.adwise.domain.Question;
import sanpi.adwise.helper.SQLiteHandler;

/**
 * Created by Teyfik on 01.10.2016.
 */
public class QuestionAdapter extends BaseAdapter {

    private static final String TAG = QuestionAdapter.class.getSimpleName();
    private ArrayList<Question> questionArray;
    private SQLiteHandler db;

    public QuestionAdapter(ArrayList<Question> questionArray) {
        this.questionArray = questionArray;
    }

    @Override
    public int getCount() {
        return questionArray.size();    // total number of elements in the list
    }

    @Override
    public Object getItem(int i) {
        return questionArray.get(i);    // single item in the list
    }

    @Override
    public long getItemId(int i) {
        return i;                   // index number
    }

    @Override
    public View getView(final int index, View view, final ViewGroup parent) {

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.single_question, parent, false);
        }

        final Question dataQuestion = questionArray.get(index);

        TextView textView = (TextView) view.findViewById(R.id.tv_question);
        textView.setText(dataQuestion.getQuestion_title());

        final Button option1 = (Button) view.findViewById(R.id.btn_option1);
        final Button option2 = (Button) view.findViewById(R.id.btn_option2);
        final Button option3 = (Button) view.findViewById(R.id.btn_option3);
        final Button option4 = (Button) view.findViewById(R.id.btn_option4);

        //Option 1
        if(!dataQuestion.getChoice_1().getName().equals("null")){
            option1.setText("" + dataQuestion.getChoice_1().getName());
            option1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    option1.setBackgroundColor(Color.GREEN);
                    option2.setBackgroundColor(Color.WHITE);
                    option3.setBackgroundColor(Color.WHITE);
                    option4.setBackgroundColor(Color.WHITE);
                    notifyDataSetChanged();

                    Question question = (Question) getItem(index);
                    answerQuestion(question.getId(), question.getChoice_1().getId());
                }
            });
        }
        else {
            option1.setVisibility(View.GONE);
        }

        //Option 2
        if(!dataQuestion.getChoice_2().getName().equals("null")){
            option2.setText("" + dataQuestion.getChoice_2().getName());
            option2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    option1.setBackgroundColor(Color.WHITE);
                    option2.setBackgroundColor(Color.GREEN);
                    option3.setBackgroundColor(Color.WHITE);
                    option4.setBackgroundColor(Color.WHITE);
                    notifyDataSetChanged();

                    Question question = (Question) getItem(index);
                    answerQuestion(question.getId(), question.getChoice_2().getId());
                }
            });
        }
        else {
            option2.setVisibility(View.GONE);
        }

        //Option 3
        if(!dataQuestion.getChoice_3().getName().equals("null")){
            option3.setText("" + dataQuestion.getChoice_3().getName());
            option3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    option1.setBackgroundColor(Color.WHITE);
                    option2.setBackgroundColor(Color.WHITE);
                    option3.setBackgroundColor(Color.GREEN);
                    option4.setBackgroundColor(Color.WHITE);
                    notifyDataSetChanged();

                    Question question = (Question) getItem(index);
                    answerQuestion(question.getId(), question.getChoice_3().getId());
                }
            });
        }
        else {
            option3.setVisibility(View.GONE);
        }

        //Option 4
        if(!dataQuestion.getChoice_4().getName().equals("null")){
            option4.setText("" + dataQuestion.getChoice_4().getName());
            option4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    option1.setBackgroundColor(Color.WHITE);
                    option2.setBackgroundColor(Color.WHITE);
                    option3.setBackgroundColor(Color.WHITE);
                    option4.setBackgroundColor(Color.GREEN);
                    notifyDataSetChanged();

                    Question question = (Question) getItem(index);
                    answerQuestion(question.getId(), question.getChoice_4().getId());
                }
            });
        }
        else {
            option4.setVisibility(View.GONE);
        }

        return view;
    }

    private void answerQuestion(final int question_id, final int choice_id) {
        // Tag used to cancel the request
        String tag_string_req = "req_answer";

        db = new SQLiteHandler(HomeActivity.getContext());

        Log.d("Burdayım: " , Integer.toString(question_id));
        Log.d("Burdayım: " , Integer.toString(choice_id));
        Log.d("Burdayım: " , Integer.toString(db.getUser().getId()));

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.ANSWER_REQUEST_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Answer Error: " + error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put( "question_id", Integer.toString(question_id) );
                params.put( "choice_id", Integer.toString(choice_id) );
                params.put( "user_id", Integer.toString(db.getUser().getId()) );

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueueW(strReq, tag_string_req);
    }

}