/*
 * Copyright (C) 2016 Angad Singh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cookie.filepicker.controller.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cookie.filepicker.R;
import com.cookie.filepicker.controller.NotifyItemChecked;
import com.cookie.filepicker.model.*;
import com.cookie.filepicker.utils.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * <p>
 * Created by Angad Singh on 09-07-2016.
 * </p>
 */

/**
 * Adapter Class that extends {@link BaseAdapter} that is
 * used to populate {@link ListView} with file info.
 */
public class FileListAdapter extends BaseAdapter {
    private ArrayList<FileListItem> listItem;
    private Context context;
    private DialogProperties properties;
    private NotifyItemChecked notifyItemChecked;

    public FileListAdapter(ArrayList<FileListItem> listItem, Context context, DialogProperties properties) {
        this.listItem = listItem;
        this.context = context;
        this.properties = properties;
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public FileListItem getItem(int i) {
        return listItem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.dialog_file_list_item, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final FileListItem item = listItem.get(i);
        if (MarkedItemList.hasItem(item.getLocation())) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.marked_item_animation);
            view.setAnimation(animation);
        } else {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.unmarked_item_animation);
            view.setAnimation(animation);
        }
        if (item.isDirectory()) {
            holder.type_icon.setImageResource(R.mipmap.ic_type_folder);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.type_icon.setColorFilter(context.getResources().getColor(R.color.fp_colorPrimary, context.getTheme()));
            } else {
                holder.type_icon.setColorFilter(context.getResources().getColor(R.color.fp_colorPrimary));
            }
            if (properties.selection_type == DialogConfigs.FILE_SELECT) {
                holder.fmark.setVisibility(View.INVISIBLE);
            } else {
                holder.fmark.setVisibility(View.VISIBLE);
            }
        } else {
            //holder.type_icon.setImageResource(R.mipmap.ic_type_file);
            boolean isImage = this.getFileIconByName(holder, item);
            if(isImage){
                holder.type_icon.clearColorFilter();
            }else{
                //非图片添加颜色
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    holder.type_icon.setColorFilter(context.getResources().getColor(R.color.fp_colorAccent, context.getTheme()));
                } else {
                    holder.type_icon.setColorFilter(context.getResources().getColor(R.color.fp_colorAccent));
                }
            }
            if (properties.selection_type == DialogConfigs.DIR_SELECT) {
                holder.fmark.setVisibility(View.INVISIBLE);
            } else {
                holder.fmark.setVisibility(View.VISIBLE);
            }
        }
        holder.type_icon.setContentDescription(item.getFilename());
        holder.name.setText(item.getFilename());
        SimpleDateFormat sdate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat stime = new SimpleDateFormat("HH:mm aa", Locale.getDefault());
        Date date = new Date(item.getTime());
        if (i == 0 && item.getFilename().startsWith("...")) {
            holder.type.setText(R.string.label_parent_directory);
        } else {
            holder.type.setText(sdate.format(date) + ", " + stime.format(date));
        }
        if (holder.fmark.getVisibility() == View.VISIBLE) {
            if (i == 0 && item.getFilename().startsWith("...")) {
                holder.fmark.setVisibility(View.INVISIBLE);
            }
            if (MarkedItemList.hasItem(item.getLocation())) {
                holder.fmark.setChecked(true);
            } else {
                holder.fmark.setChecked(false);
            }
        }
        holder.fmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setMarked(!item.isMarked());
                if (item.isMarked()) {
                    if (properties.selection_mode == DialogConfigs.MULTI_MODE) {
                        MarkedItemList.addSelectedItem(item);
                    } else {
                        MarkedItemList.addSingleFile(item);
                    }
                } else {
                    MarkedItemList.removeSelectedItem(item.getLocation());
                }
                notifyItemChecked.notifyCheckBoxIsClicked();
            }
        });
        return view;
    }

    private boolean getFileIconByName(ViewHolder holder, FileListItem fileItem) {
        boolean isImage = false;
        if (fileItem != null && fileItem.getFilename() != null) {
            int index = fileItem.getFilename().lastIndexOf(".");
            String prefix = "";
            if (index != -1) {
                prefix = fileItem.getFilename().substring(fileItem.getFilename().lastIndexOf("."));
            }
            if (prefix.toLowerCase().equals(".doc") || prefix.toLowerCase().equals(".docx")) {
                //word file
                holder.type_icon.setImageResource(R.drawable.ic_file_word);
            } else if (prefix.toLowerCase().equals(".xls") || prefix.toLowerCase().equals(".xlsx")) {
                //excel file
                holder.type_icon.setImageResource(R.drawable.ic_file_excel);
            } else if (prefix.toLowerCase().equals(".pptx") || prefix.toLowerCase().equals(".pptm")) {
                //ppt file
                holder.type_icon.setImageResource(R.drawable.ic_file_ppt);
            } else if (prefix.toLowerCase().equals(".pdf")) {
                //pdf file
                holder.type_icon.setImageResource(R.drawable.ic_file_pdf);
            } else if (prefix.toLowerCase().equals(".png") || prefix.toLowerCase().equals(".jpg") || prefix.toLowerCase().equals(".jpeg") || prefix.toLowerCase().equals(".tif")) {
                //image file
                if(fileItem.getBitmap()!=null){
                    holder.type_icon.setImageBitmap(fileItem.getBitmap() );
                }else{
                    //默认图片
                    holder.type_icon.setImageResource(R.drawable.ic_file_picture);
                    ImageViewContainer viewContainer = new ImageViewContainer(holder.type_icon,fileItem);
                    ImageLoader imageLoader = new ImageLoader();
                    imageLoader.execute(viewContainer);
                }
                isImage = true;
            } else {
                holder.type_icon.setImageResource(R.mipmap.ic_type_file);
            }
        } else {
            holder.type_icon.setImageResource(R.mipmap.ic_type_file);
        }
        return isImage;
    }

    private class ViewHolder {
        public ImageView type_icon;
        public TextView name, type;
        public CheckBox fmark;

        public ViewHolder(View itemView) {
            name = (TextView) itemView.findViewById(R.id.fname);
            type = (TextView) itemView.findViewById(R.id.ftype);
            type_icon = (ImageView) itemView.findViewById(R.id.image_type);
            fmark = (CheckBox) itemView.findViewById(R.id.file_mark);
        }
    }

    public void setNotifyItemCheckedListener(NotifyItemChecked notifyItemChecked) {
        this.notifyItemChecked = notifyItemChecked;
    }
}
