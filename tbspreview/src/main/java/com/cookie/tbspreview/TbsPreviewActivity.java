package com.cookie.tbspreview;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.tencent.smtt.sdk.TbsReaderView;

public class TbsPreviewActivity extends AppCompatActivity implements TbsReaderView.ReaderCallback {

    private TbsReaderView mTbsReaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent resultIntent = new Intent();
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tbs_preview);
            mTbsReaderView = new TbsReaderView(this, this);
            RelativeLayout contentLayout = findViewById(R.id.tbs_rel_layout);
            contentLayout.addView(mTbsReaderView,new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            Intent intent=getIntent();
            Bundle bundle=intent.getExtras();
            if(bundle!=null){
                String filePath = bundle.getString("filePath");
                bundle.putString("filePath", "/storage/emulated/0/Download/P020150720516332194302.doc");
                bundle.putString("tempPath", Environment.getDataDirectory().getPath());
                boolean result = mTbsReaderView.preOpen(parseFormat(filePath), false);
                if (result) {
                    mTbsReaderView.openFile(bundle);
                    resultIntent.putExtra("error","0");
                }else{
                    resultIntent.putExtra("error","1");
                }
            }else{
                resultIntent.putExtra("error","1");
            }
            this.setResult(RESULT_OK,resultIntent);
        }catch (Exception e){
            resultIntent.putExtra("error","1");
            this.setResult(RESULT_OK,resultIntent);
        }

    }

    private String parseFormat(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {

    }
}
