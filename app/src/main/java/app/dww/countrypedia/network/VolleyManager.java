package app.dww.countrypedia.network;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.util.concurrent.TimeUnit;

/**
 * Created by DWW on 24/04/2016.
 */
public class VolleyManager {
    private static final int DEFAULT_SOCKET_TIMEOUT = (int) TimeUnit.SECONDS.toMillis(15);
    public static VolleyManager instance=null;
    private RequestQueue requestQueue;

    private VolleyManager(Context context){
        requestQueue= Volley.newRequestQueue(context);
    }

    //supaya instancenya singleton
    public static VolleyManager getInstance(Context context){
        if(instance==null){
            instance=new VolleyManager(context);
        }
        return instance;
    }

    public void createRequest(VolleyRequest volleyRequest){
        JsonArrayRequest request= volleyRequest.generateRequest();

        request.setRetryPolicy(new DefaultRetryPolicy(DEFAULT_SOCKET_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setTag(volleyRequest.getClass().getSimpleName());
        request.setShouldCache(false);
        requestQueue.add(request);
    }
    public void cancelRequest(String tag){
        requestQueue.cancelAll(tag);
    }
}
