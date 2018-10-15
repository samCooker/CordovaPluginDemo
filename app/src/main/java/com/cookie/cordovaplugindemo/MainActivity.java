package com.cookie.cordovaplugindemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.cookie.imagepicker.GlideImageLoader;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final int SDK_PAY_FLAG = 1;
    private static String TAG = "AliPay";
    public static final int IMAGE_PICKER=0;

    String copyRight = "SxD/phFsuhBWZSmMVtSjKZmm/c/3zSMrkV2Bbj5tznSkEVZmTwJv0wwMmH/+p6wLH8aAHP4uHwybWPqtGG+2+dEp+R9AIBVe8F5pIZkZmlw/tC12y3EYzketxYJge2y52ehDPE940OcknJulMPreZ8NSyZtjuAFny7U2Uw34YrbjbCd5PSCvGZD+1JNEwdcqpwECJMGX7f5ig3WelgJE1J7CHHwcDBnMVj8djMthFaapMFm/i6swvGEQ2JoygFU3aVajuYu/yrZMT/GcKh5Jm2J4C9pL9rTcXXb4rsAQi4ugSOimMBZXAWJyoNec+zKVPMvlGx3VCgB00ugs/giWbmbOGDsTXe358SjOA3eCpX9fjgZng3BAkQ0sSjCWwWV9Oa2MQzTCfDM1NDjQqGRF6KZJhr2fg9mMpqlUrMXb+X4JAdTVJzI55s8uemwOVAPMNmT3Gc6ktkK/7bviN76kdW9XmCmwlZlxKnkAgp1jgbY=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button filePickerBtn = (Button) findViewById(R.id.btn_filepicker);

        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setMultiMode(false);
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素

        filePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ImageGridActivity.class);
                startActivityForResult(intent, IMAGE_PICKER);
            }
        });


//        Button barScannerBtn = (Button) findViewById(R.id.bar_scanner);
//        Button datePickerBtn = (Button) findViewById(R.id.btn_datepicker);
//        Button alipayBtn = (Button) findViewById(R.id.btn_alipay);
//        final Intent pickerFileIntent = new Intent(this, com.cookie.cordovaplugindemo.filepicker.MainActivity.class);
//        final Intent barScannerIntent = new Intent(this, BarScanner.class);
////        final Intent tbsPreviewIntent = new Intent(this, TbsPreviewActivity.class);
////        tbsPreviewIntent.putExtra("filePath","file:///storage/emulated/0/swfda_mobile_downloaduf_15de86ce801_1.docx");
////        filePickerBtn.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                startActivity(pickerFileIntent);
////            }
////        });
////        barScannerBtn.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                startActivity(barScannerIntent);
////            }
////        });
//        datePickerBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CTimePickerDialog timePickerDialog = new CTimePickerDialog(MainActivity.this);
//                timePickerDialog.setTimePickerFlag(true);
//                timePickerDialog.setDialogListener(new CTimePickerDialogListener() {
//                    @Override
//                    public void onSelectTimeDataBack(Date date) {
//                        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//                        Toast.makeText(MainActivity.this,dft.format(date),Toast.LENGTH_SHORT).show();
//                    }
//                });
//                timePickerDialog.show();
//            }
//        });
//
//        alipayBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //内置sd卡路径
////                String sdcardRootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
////                String sdcardPath = sdcardRootPath+"/Spring Data JPA中文文档[1.4.3].pdf";
//////              String sdcardPath = "/storage/emulated/0/$MuMu共享文件夹/Spring Data JPA中文文档[1.4.3].pdf";
////                File file = new File(sdcardPath);
////                Uri uri = Uri.fromFile(file);
////                Intent intent = new Intent("android.intent.action.VIEW", uri);
////                //intent.setClass(context, BookShower.class);
////                intent.setClassName(MainActivity.this, PreviewContent.class.getName());
////                // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                // 传递PDF ID
////                intent.putExtra(KEY_PDFID, "30222");
////                // 传递PDF TRUENAME
////                intent.putExtra(KEY_PDFTRUENAME, "Spring Data JPA中文文档[1.4.3].pdf");
////                // 传递用户名，默认admin
////                intent.putExtra(NAME, "admin");
////                // 传递授权码(必传)
////                intent.putExtra(LIC, copyRight);
////                // 是否支持域编辑功能，在表单PDF文件中可体现此功能，默认为false
////                intent.putExtra(CANFIELDEIDT, true);// 可选值为布尔值
////                // 文档保存之后批注是否只读，默认为false,不需要修改请忽略此参
////                intent.putExtra(ANNOTPROTECTNAME, true);
////                intent.putExtra(T7MODENAME, false);
////                // 默认用矢量方式
////                intent.putExtra(VECTORNAME, true);
////                // 是否保留PDF上次阅读位置，默认为true,为false时每次都从第一页开始阅读
////                intent.putExtra(LOADCACHENAME, true);
////                // 是否填充模板(选用)
////                intent.putExtra(FILLTEMPLATE, true);
////                // 阅读模式，默认PageViewMode.VSCROLL，竖向连续翻页,不需要重设可忽略
////                intent.putExtra(VIEWMODENAME, VIEWMODE_SINGLEH);
////                intent.putExtra(USER_NAME_KEY, "admin");
////                intent.putExtra(REAL_NAME_KEY, "系统管理员");
////                intent.putExtra(MEETING_ID, "30047");
////                startActivity(intent);
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if(images!=null){
                    for(ImageItem imageItem:images){
                        System.out.println("path"+imageItem.path);
                    }
                }
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
