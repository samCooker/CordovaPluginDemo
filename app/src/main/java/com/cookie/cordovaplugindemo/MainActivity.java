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
import com.cookie.datepicker.cons.DPMode;
import com.cookie.datepicker.views.DatePicker;
import com.cookie.filepicker.model.DialogProperties;

import java.util.Calendar;
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
                final DialogProperties properties=new DialogProperties();
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this);
                timePickerDialog.show();
//                final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).create();
//                dialog.show();
//                DatePicker picker = new DatePicker(MainActivity.this);
//                Calendar calendar = Calendar.getInstance();
//                picker.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
//                picker.setMode(DPMode.MULTIPLE);
//                picker.setOnDateSelectedListener(new DatePicker.OnDateSelectedListener() {
//                    @Override
//                    public void onDateSelected(List<String> date) {
//                        String result = "";
//                        Iterator iterator = date.iterator();
//                        while (iterator.hasNext()) {
//                            result += iterator.next();
//                            if (iterator.hasNext()) {
//                                result += "\n";
//                            }
//                        }
//                        Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
//                    }
//                });
//                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                dialog.getWindow().setContentView(picker, params);
//                dialog.getWindow().setGravity(Gravity.CENTER);
            }
        });
    }
}
