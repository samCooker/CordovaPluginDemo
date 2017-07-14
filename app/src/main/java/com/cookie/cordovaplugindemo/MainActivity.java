package com.cookie.cordovaplugindemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button filePickerBtn = (Button) findViewById(R.id.btn_filepicker);
        Button barScannerBtn = (Button) findViewById(R.id.bar_scanner);
        final Intent pickerFileIntent = new Intent(this, com.cookie.cordovaplugindemo.filepicker.MainActivity.class);
        final Intent barScannerIntent = new Intent(this, BarScanner.class);

        filePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(pickerFileIntent);
            }
        });
        barScannerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(barScannerIntent);
            }
        });
    }
}
