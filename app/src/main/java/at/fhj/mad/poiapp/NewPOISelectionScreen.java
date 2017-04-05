package at.fhj.mad.poiapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NewPOISelectionScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_poiselection_screen);
    }

    public void openAddPOIViewGPS(View view) {
        Intent intent = new Intent(this, NewPOIScreenGPS.class);
        startActivity(intent);
    }
}
