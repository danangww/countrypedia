package app.dww.countrypedia;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import app.dww.countrypedia.database.DataCountry;

/**
 * Created by DWW on 09/05/2016.
 */
public class DetailFragment extends Fragment {


    private String TAG=getClass().getSimpleName();
    private String extraCountry;
    private DataCountry dataCountry=null;
    private TextView txtName;
    private TextView txtCapital;
    private TextView txtRegion;
    private TextView txtSubRegion;
    private TextView txtPopulation;
    private TextView txtLat;
    private TextView txtLon;

    public DetailFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        dataCountry=new DataCountry(getContext());
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detail,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_detail_location:
                //Toast.makeText(getActivity(),"Not implemented yet",Toast.LENGTH_SHORT).show();
                String lat=txtLat.getText().toString();
                lat=lat.split("=")[1].replace(" ","");
                String lon=txtLon.getText().toString();
                lon=lon.split("=")[1].replace(" ","");
                String title=txtName.getText().toString();
                title=title.split("=")[1].replace(" ","");

                openPreferredLocationInMap(lat,lon,title);
                break;
        }
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        View rootView=inflater.inflate(R.layout.fragment_detail,container,false);
        if (intent != null && intent.hasExtra("country_name")) {
            extraCountry = intent.getStringExtra("country_name");
            ArrayList data=dataCountry.getDetailCountry(extraCountry);

            txtName = (TextView) rootView.findViewById(R.id.textview_detail_name);
            txtCapital = (TextView) rootView.findViewById(R.id.textview_detail_capital);
            txtRegion = (TextView) rootView.findViewById(R.id.textview_detail_region);
            txtSubRegion = (TextView) rootView.findViewById(R.id.textview_detail_subregion);
            txtPopulation = (TextView) rootView.findViewById(R.id.textview_detail_population);
            txtLat = (TextView) rootView.findViewById(R.id.textview_detail_latitude);
            txtLon = (TextView) rootView.findViewById(R.id.textview_detail_longitude);

            txtName.setText("Name = "+data.get(0).toString());
            txtCapital.setText("Capital = "+data.get(1).toString());
            txtRegion.setText("Region = "+data.get(2).toString());
            txtSubRegion.setText("SubRegion = "+data.get(3).toString());
            txtPopulation.setText("Population = "+data.get(4).toString());
            txtLat.setText("Latitude = "+data.get(5).toString());
            txtLon.setText("Longitude = "+data.get(6).toString());
        }
        return rootView;
    }

    private void openPreferredLocationInMap(String lat,String lon,String title) {
        String geoUri = "http://maps.google.com/maps?q=loc:" + lat + "," + lon + " (" + title + ")";

        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(geoUri));
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.e(TAG, "Could not find location");
        }
    }
}
