package at.fhj.mad.poiapp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import at.fhj.mad.poiapp.service.POIService;
import at.fhj.mad.poiapp.service.POIServiceImpl;

public class POIList extends AppCompatActivity {

    private POIService poiService;
    private ArrayAdapter<PoiLocation> arrayAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poilist);

        poiService = new POIServiceImpl();
        listView = (ListView) findViewById(R.id.allPois);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PoiLocation poiLocation = (PoiLocation)parent.getAdapter().getItem(position);
                Intent intent = new Intent(view.getContext(), POIDetail.class);
                intent.putExtra("poiLocation", poiLocation);
                startActivity(intent);
            }
        });
        showAllPOIs();
    }

    public void deleteAllPOIs(View view) {
        poiService.deleteAllPois(this);
        showAllPOIs();
    }

    public void showAllPOIs() {
        List<PoiLocation> pois = poiService.getAllPOIs(this);
        arrayAdapter = new ArrayAdapter<PoiLocation>(this, android.R.layout.simple_list_item_1, pois);
        listView.setAdapter(arrayAdapter);
    }
}
