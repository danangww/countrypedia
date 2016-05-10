package app.dww.countrypedia;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.dww.countrypedia.database.DataCountry;
import app.dww.countrypedia.network.GetCountryVolleyRequest;
import app.dww.countrypedia.network.VolleyListener;
import app.dww.countrypedia.network.VolleyManager;
import app.dww.countrypedia.network.VolleyRequest;

/**
 * Created by DWW on 04/05/2016.
 */
public class MainFragment extends Fragment {
    String dataTemp[]={"Data is empty, please click Refresh"
    };
    private ArrayAdapter<String> adapterCountry;
    private String TAG=getClass().getSimpleName();
    private DataCountry dataCountry=null;
    ProgressDialog PD;

    public MainFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        dataCountry=new DataCountry(getContext());
    }

    @Override
    public void onStart() {
        super.onStart();
        checkDBIsEmpty();
    }

    private void checkDBIsEmpty() {
        if(!dataCountry.isEmpty()){
            ArrayList data=dataCountry.getAllCountry();
            adapterCountry.clear();
            adapterCountry.addAll(data);
            Log.i(TAG,"data is not empty");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_refresh:
                int permissionCheck=ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.INTERNET);
                if(permissionCheck==PackageManager.PERMISSION_GRANTED) {
                    updateCountry();
                }else{
                    Toast.makeText(getActivity(),"Access forbidden to the Internet",Toast.LENGTH_LONG).show();
                }
                break;
        }
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        List<String> listTemp= new ArrayList<>(Arrays.asList(dataTemp));
        adapterCountry = new ArrayAdapter<>(getActivity(), R.layout.list_item_country,R.id.list_item_country_textview,listTemp);
        View rootView=inflater.inflate(R.layout.fragment_main,container,false);
        ListView listViewCountry=(ListView)rootView.findViewById(R.id.listview_country);
        listViewCountry.setAdapter(adapterCountry);
        listViewCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txtName= (TextView) view.findViewById(R.id.list_item_country_textview);
                String name=txtName.getText().toString();
                //Toast.makeText(getActivity(),name,Toast.LENGTH_SHORT).show();)
                if(!name.equalsIgnoreCase("Data is empty, please click Refresh")) {
                    Intent intentDetail = new Intent(getActivity(), DetailActivity.class);
                    intentDetail.putExtra("country_name", name);
                    startActivity(intentDetail);
                }else{
                    Toast.makeText(getActivity(),"Please click refresh first",Toast.LENGTH_LONG).show();
                }
            }
        });
        return rootView;
    }

    public void updateCountry(){
        PD = new ProgressDialog(getActivity());
        PD.setMessage("Getting Data...");
        PD.setCancelable(false);
        PD.show();

        VolleyManager volleyManager=VolleyManager.getInstance(getActivity());
        GetCountryVolleyRequest request=new GetCountryVolleyRequest();
        request.setListener(new VolleyListener() {
            @Override
            public void onSuccess(VolleyRequest volleyRequest, JSONArray response) {

                Log.i(TAG, "response : " + response.length());
                dataCountry.clear();
                adapterCountry.clear();
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject objCountry=response.getJSONObject(i);
                        String name=objCountry.getString("name");
                        String capital=objCountry.getString("capital");
                        String region=objCountry.getString("region");
                        String subregion=objCountry.getString("subregion");
                        String population=objCountry.getString("population");
                        Object latlng=objCountry.get("latlng");
                        String[] temp=latlng.toString().split(",");
                        String lat = "0";
                        String lon = "0";
                        if(temp.length==2) {
                            lat = temp[0].replace("[","");
                            lon = temp[1].replace("]","");;
                        }

                        String hasil=dataCountry.inputCountry(name,capital,region,subregion,population,lat,lon);
                        //Log.i(TAG,i+". "+name+" "+hasil);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                PD.dismiss();
                checkDBIsEmpty();
            }

            @Override
            public void onError(VolleyRequest volleyRequest, String message) {
                PD.dismiss();
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                checkDBIsEmpty();
                //Log.e(TAG, "response : " + message);
            }
        });
        volleyManager.createRequest(request);
    }
}
