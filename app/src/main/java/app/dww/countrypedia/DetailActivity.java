package app.dww.countrypedia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        DetailFragment detailFragment=new DetailFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_detail,detailFragment)
                .commit();
    }
}
