package com.palo.palo.volley;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * VolleySignleton contains an instance of a Volley Request Queue.
 * The singleton pattern handles all the request queue's in this application, rather than creating new one's each time.
 */
public class VolleySingleton {
    private static VolleySingleton instance;
    private RequestQueue requestQueue;
    private static Context context;

    /**
     * Constructor for VolleySingleton.
     * @param context applications context
     */
    private VolleySingleton(Context context){
        this.context = context;
        requestQueue = this.getRequestQueue();
    }

    /**
     * Returns the instance of VolleySington if there is one for context. Otherwise creates and sets instance.
     * @param c applications context
     * @return
     */
    public static synchronized VolleySingleton getInstance(Context c){
        if (instance == null) instance = new VolleySingleton(c);
        return instance;
    }

    /**
     * Returns requestQueue. Creates a Volley requestQueue, if requestQueue is null.
     * @return
     */
    public RequestQueue getRequestQueue(){
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    /**
     * Adds volley request to request queue.
     * @param request
     * @param <T>
     */
    public <T> void addToRequestQueue(Request<T> request){
        this.getRequestQueue().add(request);
    }
}
