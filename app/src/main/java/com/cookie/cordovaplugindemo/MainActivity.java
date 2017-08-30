package com.cookie.cordovaplugindemo;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.cookie.datepicker.TimePickerDialog;
import com.cookie.datepicker.TimePickerDialogListener;
import com.cookie.datepicker.cons.DPMode;
import com.cookie.datepicker.views.DatePicker;
import com.cookie.filepicker.model.DialogProperties;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button filePickerBtn = (Button) findViewById(R.id.btn_filepicker);
        Button barScannerBtn = (Button) findViewById(R.id.bar_scanner);
        Button datePickerBtn = (Button) findViewById(R.id.btn_datepicker);
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
        datePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this);
                timePickerDialog.setTimePickerFlag(true);
                timePickerDialog.setDialogListener(new TimePickerDialogListener() {
                    @Override
                    public void onSelectTimeDataBack(Date date) {
                        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        Toast.makeText(MainActivity.this,dft.format(date),Toast.LENGTH_SHORT).show();
                    }
                });
                timePickerDialog.show();
            }
        });
    }
}
