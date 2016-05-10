package app.dww.countrypedia.network;

/**
 * Created by DWW on 24/04/2016.
 */
public class GetCountryVolleyRequest extends GetVolleyRequest{
    public GetCountryVolleyRequest() {
        //super("http://api.openweathermap.org/data/2.5/forecast/daily");
        super("https://restcountries.eu/rest/v1");
    }
}
