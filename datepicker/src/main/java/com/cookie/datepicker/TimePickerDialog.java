package com.cookie.datepicker;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import com.cookie.datepicker.bizs.languages.DPLManager;
import com.cookie.datepicker.views.MonthView;

/**
 * 2017-8-28
 *
 * @author Shicx
 */

public class TimePickerDialog extends Dialog {

    private DPLManager mLManager;// 语言管理器
    private MonthView monthView;
    private TextView  tvYear,tvMonth;

    public TimePickerDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_tp_main);

        mLManager = DPLManager.getInstance();

        monthView = (MonthView) findViewById(R.id.v_main_month);
        tvYear = (TextView) findViewById(R.id.tv_tle_year);
        tvMonth = (TextView) findViewById(R.id.tv_tle_month);

        tvYear.setText(monthView.getCenterYear());
        tvMonth.setText(monthView.getCenterMonth());
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
    }
}
