package com.cookie.datepicker;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cookie.datepicker.bizs.languages.DPLManager;
import com.cookie.datepicker.cons.DPMode;
import com.cookie.datepicker.utils.DataUtils;
import com.cookie.datepicker.views.MonthView;
import com.cookie.datepicker.views.TimeScrollPicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 2017-8-28
 *
 * @author Shicx
 */

public class TimePickerDialog extends Dialog {

    private TimePickerDialogListener dialogListener;
    private DPLManager mLManager;// 语言管理器
    private MonthView monthView;
    private TextView  tvYear,tvMonth;
    private TimeScrollPicker viewMinu;
    private TimeScrollPicker viewHour;
    private boolean timePickerFlag=true;

    public TimePickerDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Calendar calendar = Calendar.getInstance();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if(timePickerFlag){
            setContentView(R.layout.dialog_tp_main);
            //有时间选择
            viewMinu = (TimeScrollPicker) findViewById(R.id.view_minu);
            viewMinu.setData(this.getMinutes());
            viewMinu.setSelectedPosition(calendar.get(Calendar.MINUTE));
            //24小时制
            viewHour = (TimeScrollPicker) findViewById(R.id.view_hour);
            viewHour.setData(this.getHours());
            viewHour.setSelectedPosition(calendar.get(Calendar.HOUR_OF_DAY));
        }else{
            setContentView(R.layout.dialog_dp_main);
        }

        mLManager = DPLManager.getInstance();

        monthView = (MonthView) findViewById(R.id.v_main_month);
        monthView.initDate(null);
        //单选
        monthView.setDPMode(DPMode.SINGLE);

        tvYear = (TextView) findViewById(R.id.tv_tle_year);
        tvMonth = (TextView) findViewById(R.id.tv_tle_month);

        tvYear.setText(monthView.getCenterYear()+"");
        tvMonth.setText(mLManager.titleMonth()[monthView.getCenterMonth()]);
        monthView.setOnDateChangeListener(new MonthView.OnDateChangeListener() {
            @Override
            public void onMonthChange(int month) {
                tvMonth.setText(mLManager.titleMonth()[month - 1]);
            }

            @Override
            public void onYearChange(int year) {
                String tmp = String.valueOf(year);
                if (tmp.startsWith("-")) {
                    tmp = tmp.replace("-", mLManager.titleBC());
                }
                tvYear.setText(tmp);
            }
        });

        Button select = (Button) findViewById(R.id.tp_select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> selectedList = monthView.getDateSelected();
                if(selectedList!=null&&selectedList.size()>0){
                    CharSequence hourSeq = "00";
                    if(viewHour!=null){
                        hourSeq = viewHour.getSelectedItem();
                    }
                    CharSequence minuSeq = "00";
                    if(viewMinu!=null){
                        minuSeq = viewMinu.getSelectedItem();
                    }
                    String timeStr=selectedList.get(0)+" "+hourSeq+":"+minuSeq;
                    if(dialogListener!=null){
                        Date date = null;
                        try {
                            date = DataUtils.DATE_FORMAT.parse(timeStr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dialogListener.onSelectTimeDataBack(date);
                        dismiss();
                    }
                }
            }
        });

        Button cancel = (Button) findViewById(R.id.tp_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private List<CharSequence> getHours() {
        List<CharSequence> hours = new ArrayList<>();
        for(int i=0;i<24;i++){
            if(i<10){
                hours.add("0"+i);
            }else{
                hours.add(i+"");
            }
        }
        return hours;
    }

    private List<CharSequence> getMinutes() {
        List<CharSequence> minutes = new ArrayList<>();
        for(int i=0;i<60;i++){
            if(i<10){
                minutes.add("0"+i);
            }else{
                minutes.add(i+"");
            }
        }
        return minutes;
    }

    public void setDialogListener(TimePickerDialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    public void setTimePickerFlag(boolean timePickerFlag) {
        this.timePickerFlag = timePickerFlag;
    }
}
