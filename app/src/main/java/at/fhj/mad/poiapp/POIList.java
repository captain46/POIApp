package at.fhj.mad.poiapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import at.fhj.mad.poiapp.service.POIService;
import at.fhj.mad.poiapp.service.POIServiceImpl;

public class POIList extends AppCompatActivity {

    private POIService poiService;
    private ArrayAdapter<String> arrayAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poilist);

        poiService = new POIServiceImpl();
        listView = (ListView) findViewById(R.id.allPois);
        showAllPOIs();
    }

    public void deleteAllPOIs(View view) {
        poiService.deleteAllPois(this);
        showAllPOIs();
    }

    public void showAllPOIs() {
        List<String> pois = poiService.getAllPOIs(this);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pois);
        listView.setAdapter(arrayAdapter);
    }
}
