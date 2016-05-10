package app.dww.countrypedia.network;

import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DWW on 24/04/2016.
 */
public abstract class VolleyRequest implements Response.Listener<JSONArray>,Response.ErrorListener {
    private VolleyListener listener;
    private String url;


    public abstract JsonArrayRequest generateRequest();

    public VolleyRequest(String url){
        this.url=url;
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        if(listener!=null) {
            String message="Something wrong happened, please try again";
            if (error instanceof TimeoutError) {
                message="Timeout exceeded";
            } else if (error instanceof NoConnectionError) {
                message="No internet connection";
            }
            listener.onError(this,message);
        }
    }

    @Override
    public void onResponse(JSONArray response) {
        if(listener!=null){
            listener.onSuccess(this, response);
        }
    }

    public void setListener(VolleyListener listener) {
        this.listener = listener;
    }

    public String getURL() {
        return url;
    }
}
