package cn.com.chaochuang.goldgrid.filepreview;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import cn.com.chaochuang.goldgrid.filepreview.utill.MyConstant;
import com.kinggrid.iapppdf.company.common.KinggridConstant;
import com.kinggrid.iapppdf.emdev.utils.LayoutUtils;
import com.kinggrid.iapppdf.ui.viewer.IAppPDFActivity;
import com.kinggrid.pdfservice.Annotation;

import java.util.ArrayList;
import java.util.List;

/**
 * @项目名称:AD5  
 * @类  描  述:批注清单窗体
 */
public class NoteDialog extends Dialog implements OnItemClickListener, MyConstant {

    final Context context;
    private List<Annotation> noteList;
    private IAppPDFActivity activity;
    private final int subType;

    private LinearLayout noteFrame;
    private ListView annotList;
    
    
    private TextView no_list;
    private ImageView load_img;
    private TextView noteUser;
    private TextView noteTime;
    private TextView noteContent;
    private LinearLayout head;
    private RelativeLayout loading;
    
    
    public NoteDialog(final Context context, final IAppPDFActivity activity, final int subType) {
        super(context);
        this.context = context;
        this.activity = activity;
        this.subType = subType;
    }
 
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutUtils.maximizeWindow(getWindow());

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        
        setContentView(R.layout.note_layout);

        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.list_title);

        final Button btn_outline_close = (Button) findViewById(R.id.title_btn_close);
        btn_outline_close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                dismiss();
            }
        });
        final TextView tv_outline_title = (TextView) findViewById(R.id.list_title);
        if (/*this.subType.equals(ANNOT_SUBTYPE_POSTIL) || */this.subType == TYPE_ANNOT_STAMP) {
            tv_outline_title.setText(R.string.note_list);
        } else if (this.subType == TYPE_ANNOT_TEXT) {
            tv_outline_title.setText(R.string.text_note_list);
        } else if(this.subType == TYPE_ANNOT_SOUND){
        	tv_outline_title.setText(R.string.annot_sound_list);
        }
        
        noteFrame = (LinearLayout) findViewById(R.id.list_content);
        annotList = (ListView) findViewById(R.id.annot_list);
        head = (LinearLayout) noteFrame.findViewById(R.id.notelist_title);
        
        noteUser = (TextView) head.findViewById(R.id.note_user);
        noteTime = (TextView) head.findViewById(R.id.note_time);
        noteContent = (TextView) head.findViewById(R.id.note_content);
        
        if (/*this.subType.equals(ANNOT_SUBTYPE_POSTIL) || */this.subType == TYPE_ANNOT_STAMP || 
        		this.subType == TYPE_ANNOT_SOUND) {
          noteContent.setVisibility(View.GONE);
          noteUser.setText(R.string.note_user_postil);
          noteTime.setText(R.string.note_time_postil);
        } else if (this.subType == TYPE_ANNOT_TEXT) {
            noteUser.setText(R.string.note_user_text);
            noteTime.setText(R.string.note_time_text);
        }
        
        no_list = (TextView) findViewById(R.id.no_data_prompt);
        
        loading = (RelativeLayout) findViewById(R.id.loading);
        load_img = (ImageView) findViewById(R.id.progress_img);
        Animation loadAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.load_animation);
        load_img.setAnimation(loadAnimation);

    }

    public void updateView(ArrayList<Annotation> list){
      noteList = list;
      if(noteList != null && noteList.size() > 0){
        loading.setVisibility(View.GONE);
        annotList.setAdapter(new ListViewAdapter(noteList));
        annotList.setOnItemClickListener(this);
        noteFrame.setVisibility(View.VISIBLE);
      }else{
        loading.setVisibility(View.GONE);
        no_list.setVisibility(View.VISIBLE);
      }
    }
    
    @Override
    public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
        this.dismiss();
        final int pageNo = Integer.parseInt(noteList.get(position).getPageNo()) - 1;
        final int maxPage = activity.getPageCount() - 1;
        if (pageNo <= maxPage) {
        	activity.jumpToPage(pageNo);
        }
    }

    private class ListViewAdapter extends BaseAdapter {

        /**
         * 批注列表
         */
        private final List<Annotation> noteList;

        private ListViewAdapter(final List<Annotation> noteList) {
            this.noteList = noteList;
        }

        @Override
        public int getCount() {
            return noteList.size();
        }

        @Override
        public Object getItem(final int i) {
            return noteList.get(i);
        }

        @Override
        public long getItemId(final int id) {
            return id;
        }

        @SuppressWarnings("static-access")
		@SuppressLint("InflateParams")
		@Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            final Annotation note = noteList.get(position);
            Holder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.note_listview_layout, null);
                holder = new Holder();
                holder.note_textView = (TextView) convertView.findViewById(R.id.note_content);
                if (/*subType.equals(ANNOT_SUBTYPE_POSTIL) || */subType == TYPE_ANNOT_STAMP || 
                		subType == TYPE_ANNOT_SOUND) {
                    holder.note_textView.setVisibility(View.GONE);
                }
                holder.pageNum_textView = (TextView) convertView.findViewById(R.id.page_num);
                holder.time_textView = (TextView) convertView.findViewById(R.id.time_textView);
                holder.userName_textView = (TextView) convertView.findViewById(R.id.username_textView);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            
            String note_text = "";
            if(!note.getStyleName().equals(KinggridConstant.ANNOT_SUBTYPE_STAMP)){
            	note_text = note.getAnnoContent();
            }
            holder.note_textView.setText(note_text);
            holder.pageNum_textView.setText("第" + (Integer.valueOf(note.getPageNo())) + "页");
            holder.time_textView.setText(note.getCreateTime());
            holder.userName_textView.setText(note.getAuthorName());
            return convertView;
        }
    }

    class Holder {

        TextView note_textView;
        TextView pageNum_textView;
        TextView time_textView;
        TextView userName_textView;
    }
}
