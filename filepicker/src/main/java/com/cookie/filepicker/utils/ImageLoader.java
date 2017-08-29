package com.cookie.filepicker.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import com.cookie.filepicker.R;
import com.cookie.filepicker.model.ImageViewContainer;

/**
 * 2017-8-29
 *
 * @author Shicx
 * 图片延迟加载器
 */

public class ImageLoader extends AsyncTask<ImageViewContainer,Void,ImageViewContainer> {

    @Override
    protected ImageViewContainer doInBackground(ImageViewContainer... params) {
        ImageViewContainer viewContainer = params[0];
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 5;
        if(viewContainer.getFileListItem()!=null) {
            Bitmap preBitmap = BitmapFactory.decodeFile(viewContainer.getFileListItem().getLocation(), options);
            if (preBitmap != null) {
                Bitmap smallBitmap = Bitmap.createScaledBitmap(preBitmap, 90, 90, true);// 将原来的位图缩小
                preBitmap.recycle();// 释放内存
                viewContainer.setBitmap(smallBitmap);
            }
        }
        return viewContainer;
    }

    @Override
    protected void onPostExecute(ImageViewContainer viewContainer) {
        ImageView imageView = (ImageView)viewContainer.getView();
        if(viewContainer.getBitmap()!=null) {
            imageView.setImageBitmap(viewContainer.getBitmap());
            viewContainer.getFileListItem().setBitmap(viewContainer.getBitmap());
        }
    }
}
