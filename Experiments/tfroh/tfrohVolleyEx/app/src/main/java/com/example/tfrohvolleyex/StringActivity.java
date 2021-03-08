package com.example.tfrohvolleyex;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.tfrohvolleyex.app.AppController;
import com.example.tfrohvolleyex.net_utils.Const;

public class StringActivity extends AppCompatActivity {

    private String TAG = StringActivity.class.getSimpleName();
    private Button btnStringReq;
    private TextView msgStrResponse;
    private ProgressDialog pDialog;
    private String tag_string_req = "string_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_string);

        btnStringReq = findViewById(R.id.stringInReq);
        msgStrResponse = findViewById(R.id.msgStrResponse);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        

        btnStringReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeStringReq();
            }
        });
    }

    private void makeStringReq() {
        pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.GET, Const.URL_STRING_REQ, response -> {
            Log.d(TAG, response.toString());
            msgStrResponse.setText(response.toString());
            pDialog.hide();
        }, error -> {
            VolleyLog.d(TAG, "Error: " + error.getMessage());
            pDialog.hide();
        });
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}