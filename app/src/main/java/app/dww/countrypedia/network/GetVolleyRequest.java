package app.dww.countrypedia.network;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by DWW on 24/04/2016.
 */
public class GetVolleyRequest extends VolleyRequest {

    public GetVolleyRequest(String url){
        super(url);
    }

    @Override
    public JsonArrayRequest generateRequest() {
        String url=getURL();
        return new JsonArrayRequest(Request.Method.GET,url,null,this,this);
    }

}
