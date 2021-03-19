package com.example.tfrohvolleyex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.tfrohvolleyex.app.AppController;
import com.example.tfrohvolleyex.net_utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JsonActivity extends AppCompatActivity {
    private Button btnOb;
    private Button btnAr;
    private TextView jsonResponse;
    private ProgressDialog p;
    private String TAG = JsonActivity.class.getSimpleName();
    private String tag_json_obj = "jobj_req", tag_json_array = "jarray_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        btnOb = findViewById(R.id.jsonInReq);
        btnAr = findViewById(R.id.jsonArrInReq);
        jsonResponse = findViewById(R.id.msgResponse);

        p = new ProgressDialog(this);
        p.setMessage("Loading...");

        btnOb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeJsonObjectReq();
            }
        });
        btnAr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeJsonArrayRequest();
            }
        });
    }

    private void makeJsonObjectReq() {
        p.show();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Const.URL_JSON_OBJECT, null,
                response -> {
                    Log.d(TAG, response.toString());
                    String firstName = null;
                    try {
                        firstName = response.getString("name");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        p.hide();
                    }
                    jsonResponse.setText(firstName);
                    p.hide();
                }, error -> {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    p.hide();
                }); /*{

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", "Androidhive");
                params.put("email", "abc@androidhive.info");
                params.put("pass", "password123");

                return params;
            }
        };*/

        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    private void makeJsonArrayRequest() {
        p.show();

        JsonObjectRequest arr = new JsonObjectRequest(Request.Method.GET, Const.URL_JSON_ARRAY, null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("array");

                        for(int i = 0; i < jsonArray.length(); i++) {
                            JSONObject person = jsonArray.getJSONObject(i);
                            String firstName = person.getString("first");
                            String lastName = person.getString("last");
                            int age = person.getInt("age");
                            jsonResponse.append(firstName + " " + lastName + " is " + age + " years old.\n");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    p.hide();
                }, error -> {
                    error.printStackTrace();
                    p.hide();
                });

        AppController.getInstance().addToRequestQueue(arr, tag_json_array);
    }
}