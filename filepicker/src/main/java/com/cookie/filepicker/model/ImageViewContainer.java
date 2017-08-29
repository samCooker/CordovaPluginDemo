package com.cookie.filepicker.model;

import android.graphics.Bitmap;
import android.view.View;

/**
 * 2017-8-29
 *
 * @author Shicx
 */

public class ImageViewContainer {

    private FileListItem fileListItem;
    private View view;
    private Bitmap bitmap;

    public ImageViewContainer() {
    }

    public ImageViewContainer(View view,FileListItem fileListItem) {
        this.view = view;
        this.fileListItem = fileListItem;
    }

    public FileListItem getFileListItem() {
        return fileListItem;
    }

    public void setFileListItem(FileListItem fileListItem) {
        this.fileListItem = fileListItem;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
