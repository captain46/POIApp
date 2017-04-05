package at.fhj.mad.poiapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StartScreen extends AppCompatActivity {

    private static final String TAG = "StartScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
    }

    public void openPOIList(View view) {
        Intent intent = new Intent(this, POIList.class);
        startActivity(intent);
    }

    public void openAddPOISelectionView(View view) {
        Intent intent = new Intent(this, NewPOISelectionScreen.class);
        startActivity(intent);
    }
}
