package app.dww.countrypedia.network;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by DWW on 24/04/2016.
 */
public interface VolleyListener {
    void onSuccess(VolleyRequest volleyRequest, JSONArray response);
    void onError(VolleyRequest volleyRequest, String message);
}
