package com.cookie.cordovaplugindemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.alipay.sdk.app.PayTask;
import com.cookie.cordovaplugindemo.alipay.PayResult;
import com.cookie.datepicker.CTimePickerDialog;
import com.cookie.datepicker.CTimePickerDialogListener;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private static final int SDK_PAY_FLAG = 1;
    private static String TAG = "AliPay";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button filePickerBtn = (Button) findViewById(R.id.btn_filepicker);
        Button barScannerBtn = (Button) findViewById(R.id.bar_scanner);
        Button datePickerBtn = (Button) findViewById(R.id.btn_datepicker);
        Button alipayBtn = (Button) findViewById(R.id.btn_alipay);
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
                CTimePickerDialog timePickerDialog = new CTimePickerDialog(MainActivity.this);
                timePickerDialog.setTimePickerFlag(true);
                timePickerDialog.setDialogListener(new CTimePickerDialogListener() {
                    @Override
                    public void onSelectTimeDataBack(Date date) {
                        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        Toast.makeText(MainActivity.this,dft.format(date),Toast.LENGTH_SHORT).show();
                    }
                });
                timePickerDialog.show();
            }
        });

        alipayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, " 构造PayTask 对象 ");
                        PayTask alipay = new PayTask(MainActivity.this);
                        Log.i(TAG, " 调用支付接口，获取支付结果 ");
                        String result = alipay.pay("alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2017100309101629&biz_content=%7B%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22out_trade_no%22%3A%22%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22App%E6%94%AF%E4%BB%98%E6%B5%8B%E8%AF%95Java%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=%E5%95%86%E6%88%B7%E5%A4%96%E7%BD%91%E5%8F%AF%E4%BB%A5%E8%AE%BF%E9%97%AE%E7%9A%84%E5%BC%82%E6%AD%A5%E5%9C%B0%E5%9D%80&sign=hApzJfefGBenHxwj1ebFYuFFBrJy%2F8LLCQAM1Vimw18CQXc19Kd32DazQ86Wd14KW%2BO7aYgRPOkrRQcxWzzp7omkFX5N%2FMsWGNLAh6necHbl29ZYw4vhxFuN%2FymeMVQ4KgAH6KX6TWNQ8mDbODfzIHydun10RaXOagabd3FhWjukJFvjT0W0TTqPiC4NQDm%2BcCGwrdeFLu7yWN2zNFhICx3BOzDUfatf4yIKGDBoLxmp8QfbdBtLbNt1r2861%2FC3qwoqzB0ysAZFFsO7l747OU4csMZufjjiUmXYIAnpQg0NYrHKUYdl5j%2FdPXRn0xCKQgss60CdkvuncZff0STAHg%3D%3D&sign_type=RSA2&timestamp=2017-10-06+00%3A14%3A15&version=1.0", true);

                        // 更新主ui的Toast
                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);

                        PayResult payResult = new PayResult(result);
                        if (TextUtils.equals(payResult.getResultStatus(), "9000")) {
                            Log.i(TAG, " 9000则代表支付成功，具体状态码代表含义可参考接口文档 ");
                        } else {
                            Log.i(TAG, " 为非9000则代表可能支付失败 ");
                            if (TextUtils.equals(payResult.getResultStatus(),
                                    "8000")) {
                                Log.i(TAG,
                                        " 8000代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态） ");
                            } else {
                                Log.i(TAG, " 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误 ");
                            }
                        }
                    }
                });
            }
        });
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(MainActivity.this, "支付成功",
                                Toast.LENGTH_SHORT).show();
                    } else {

                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(MainActivity.this, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "支付失败",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
}
