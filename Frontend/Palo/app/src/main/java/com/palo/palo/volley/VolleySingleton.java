package com.palo.palo.volley;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/*
    Singleton pattern to handle all the request queue's, rather than creating new one's each time.
 */
public class VolleySingleton {
    private static VolleySingleton instance;
    private RequestQueue requestQueue;
    private static Context context;

    private VolleySingleton(Context context){
        this.context = context;
        requestQueue = this.getRequestQueue();
    }

    public static synchronized VolleySingleton getInstance(Context c){
        if (instance == null) instance = new VolleySingleton(c);
        return instance;
    }

    public RequestQueue getRequestQueue(){
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request){
        this.getRequestQueue().add(request);
    }
}
