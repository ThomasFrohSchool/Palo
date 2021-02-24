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
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.tfrohvolleyex.app.AppController;
import com.example.tfrohvolleyex.net_utils.Const;

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
        jsonResponse = findViewById(R.id.msgResponse);

        p = new ProgressDialog(this);
        p.setMessage("Loading...");

        btnOb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeJsonObjectReq();
            }
        });
    }

    private void makeJsonObjectReq() {
        p.show();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Const.URL_JSON_OBJECT, null,
                response -> {
                    Log.d(TAG, response.toString());
                    jsonResponse.setText(response.toString());
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
}