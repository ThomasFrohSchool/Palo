package com.palo.palo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.palo.palo.R;
import com.palo.palo.SharedPrefManager;
import com.palo.palo.fragments.directMessage.DirectMessageFragment;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import static com.palo.palo.volley.ServerURLs.DMSOCKET;

public class DirectMessageUserActivity extends AppCompatActivity {
    private TextView messageHistory;
    private EditText message;
    private Context context;
    private ImageButton back;
    private Button send;
    private WebSocketClient mWebSocketClient;
    private String userTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_message_user);
        context = getApplicationContext();
        back = findViewById(R.id.back);
        message = findViewById(R.id.editTextMessage);
        messageHistory = findViewById(R.id.messageHistory);
        send = findViewById(R.id.send);
        userTo = getIntent().getStringExtra("USER_TO_SEND");

        connectWebSocket();

        back.setOnClickListener(v -> {
            mWebSocketClient.close();
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        });

        send.setOnClickListener(v -> {
            String msg = message.getText().toString();

            if(msg != null && msg.length() > 0) {
                mWebSocketClient.send(msg);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebSocketClient.close();
    }

    private void connectWebSocket() {
        URI uri;
        try {
            uri = new URI(DMSOCKET + SharedPrefManager.getInstance(context).getUser().getUsername() + "/" + userTo);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri) {

            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
            }

            @Override
            public void onMessage(String msg) {
                Log.i("Websocket", "Message Received");
                messageHistory.append("\n" + msg);
            }

            @Override
            public void onClose(int errorCode, String reason, boolean remote) {
                Log.i("Websocket", "Closed " + reason);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();
    }
}