package cn.com.chaochuang.goldgrid.filepreview;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.*;
import cn.com.chaochuang.goldgrid.filepreview.utill.AnnotationToJsonUtil;
import cn.com.chaochuang.goldgrid.filepreview.utill.HttpServer;
import cn.com.chaochuang.goldgrid.filepreview.utill.MyConstant;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.kinggrid.iapppdf.emdev.ui.gl.GLConfiguration;
import com.kinggrid.iapppdf.emdev.utils.LayoutUtils;
import com.kinggrid.iapppdf.ui.viewer.IActivityController;
import com.kinggrid.iapppdf.ui.viewer.IAppPDFActivity;
import com.kinggrid.iapppdf.ui.viewer.PDFHandWriteView;
import com.kinggrid.iapppdf.ui.viewer.ViewerActivityController;
import com.kinggrid.iapppdf.ui.viewer.viewers.GLView;
import com.kinggrid.pdfservice.Annotation;
import com.kinggrid.pdfservice.PageViewMode;
import com.longmai.mtoken.k5.util.LogUtil;

import java.util.ArrayList;


public class PreviewContent extends IAppPDFActivity implements MyConstant {

    private Button backButton;
    private FloatingActionsMenu actionsMenu;
    private FloatingActionsMenu handleWriteMenu;
    private FloatingActionButton handWrite;
    private FloatingActionButton textInput;
    private FloatingActionButton btnSaveNote;

    /**
     * 全文批注按钮
     */
    public FloatingActionButton btnClose, btnClear, btnUndo, btnRedo, btnSave, btnPen, btnEarse, btnSend;

    private FrameLayout frameLayout;
    private boolean hasLoaded = false;
    //文件的路径
    public String path = "";
    public String pdfId = "";

    private final String SAVESIGNFINISH_ACTION = "com.kinggrid.iapppdf.broadcast.savesignfinish";

    BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.kinggrid.pages.bmp.save")) {
                isLocked = false;
                Toast.makeText(context, "保存页面图片完毕", Toast.LENGTH_SHORT).show();
            }
            if (action.equals(SAVESIGNFINISH_ACTION)) {
                if (loadPDFReaderCache) {
                    savePDFReadSettings();
                }
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }
    };

    @Override
    protected void onCreateImpl(Bundle savedInstanceState) {
        super.onCreateImpl(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        context = this;
        setContentView(R.layout.activity_preview_content);
        frameLayout = (FrameLayout) findViewById(R.id.book_frame);
//        backButton = findViewById(R.id.btn_back);
//        this.backButtonEvent();

        this.initPDFParams();
        super.initPDFView(frameLayout);
        this.initToolBar();
        this.initParentListener();
        super.setLoadingText(R.string.msg_loading_tip);


        IntentFilter filter = new IntentFilter("com.kinggrid.pages.bmp.save");
        registerReceiver(receiver, filter);

        IntentFilter save_filter = new IntentFilter(SAVESIGNFINISH_ACTION);
        registerReceiver(receiver, save_filter);
        BookShowerReceiver bookShowerReceiver = new BookShowerReceiver();
        IntentFilter delannotById = new IntentFilter(BroadCastActions.BC_DelAnnotSuccess);
        registerReceiver(bookShowerReceiver, delannotById);
        IntentFilter delallAnnot = new IntentFilter(BroadCastActions.BC_DelAnnotFailure);
        registerReceiver(bookShowerReceiver, delallAnnot);
        IntentFilter getannot = new IntentFilter(BroadCastActions.BC_GetAllAnnotSuccess);
        registerReceiver(bookShowerReceiver, getannot);
        IntentFilter getannot_f = new IntentFilter(BroadCastActions.BC_GetAllAnnotFailure);
        registerReceiver(bookShowerReceiver, getannot_f);
        IntentFilter sendannot_success = new IntentFilter(BroadCastActions.BC_AddAnnotSuccess);
        registerReceiver(bookShowerReceiver, sendannot_success);
        IntentFilter sendannot_failure = new IntentFilter(BroadCastActions.BC_AddAnnotFailure);
        registerReceiver(bookShowerReceiver, sendannot_failure);
        //更新文字批注
        IntentFilter updatetextannot_success = new IntentFilter(BroadCastActions.BC_AddAnnotSuccess);
        registerReceiver(bookShowerReceiver, updatetextannot_success);
        IntentFilter updatetextannot_failure = new IntentFilter(BroadCastActions.BC_AddAnnotFailure);
        registerReceiver(bookShowerReceiver, updatetextannot_failure);
        //发送批注
        IntentFilter SendAnnotannot_success = new IntentFilter(BroadCastActions.BC_SendAnnotSuccess);
        registerReceiver(bookShowerReceiver, SendAnnotannot_success);
        IntentFilter SendAnnottannot_failure = new IntentFilter(BroadCastActions.BC_SendAnnotFailure);
        registerReceiver(bookShowerReceiver, SendAnnottannot_failure);
    }

    public class BookShowerReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BroadCastActions.BC_DelAnnotSuccess)) {
                //删除批注成功
                refreshDocument();
            } else if (action.equals(BroadCastActions.BC_DelAnnotFailure)) {
                //删除批注失败
            } else if (action.equals(BroadCastActions.BC_GetAllAnnotSuccess)) {
                //获取批注成功
                LogUtil.d("pz", "获取批注成功");
                Thread thread = new Thread(new LoadAnnotations());
                thread.start();
                hasLoaded = true;
            } else if (action.equals(BroadCastActions.BC_GetAllAnnotFailure)) {
                //获取批注失败
                LogUtil.d("pz", "获取批注失败");
            } else if (action.equals(BroadCastActions.BC_AddAnnotSuccess)) {
                //添加批注成功
                Log.d("pz", "添加批注成功");
            } else if (action.equals(BroadCastActions.BC_AddAnnotFailure)) {
                //添加批注失败
                Log.d("pz", "添加批注失败");
            } else if (action.equals(BroadCastActions.BC_UpdateTextAnnotContentSuccess)) {
                //更新文字批注成功
                refreshDocument();
                Log.d("pz", "更新文字批注成功");
            } else if (action.equals(BroadCastActions.BC_UpdateTextAnnotContentFailure)) {
                //更新文字批注失败
                Log.d("pz", "更新文字批注失败");
            } else if (action.equals(BroadCastActions.BC_SendAnnotSuccess)) {
                //发送批注成功
                Log.d("pz", "发送批注成功");
                refreshDocument();
            } else if (action.equals(BroadCastActions.BC_SendAnnotFailure)) {
                //发送批注失败
                String error = intent.getStringExtra("error");
                Log.d("pz", "发送批注失败");
                Log.d("pz", "错误：" + error);
            }
        }
    }


    public class LoadAnnotations implements Runnable {
        @Override
        public void run() {
            touchEnable = false;
            Log.d("pz", "批注集合 = " + annotation_list.toString());
            int size = annotation_list.size();
            for (int i = 0; i < size; i++) {
                Annotation annot = annotation_list.get(i);
                String type = annot.getStyleName();
                int pageNo = Integer.parseInt(annot.getPageNo());
                float x = Float.parseFloat(annot.getX());
                float y = Float.parseFloat(annot.getY());
                String userName = annot.getAuthorName();
                String creatTime = annot.getCreateTime();
                String annotId = annot.getAnnotId();
                Log.d("pz", "annotId2 = " + annotId);
                if (type.equals("Stamp") || type.equals("test") || type.equals("unknown")) {
                    if (annot.getAnnoContent().startsWith("q")) {
                        insertVectorAnnotation(pageNo, x, y, Float.parseFloat(annot.getWidth()),
                                Float.parseFloat(annot.getHeight()), annot.getAnnoContent(), userName,
                                creatTime, 1, annotId);
                    } else {
                        insertHandWriteAnnotation(pageNo,
                                x,
                                y,
                                Float.parseFloat(annot.getWidth()),
                                Float.parseFloat(annot.getHeight()), annot
                                        .getAnnoContent(), userName,
                                creatTime, 1, annotId);
                    }
                } else if (type.equals("Text")) {
                    Log.d("pz", "insert text annotation");
                    insertTextAnnotation(pageNo,
                            x,
                            y,
                            annot.getAnnoContent(),
                            userName,
                            creatTime,
                            "", annotId);
                } else if (type.equals("Sound")) {
                    insertSoundAnnotation(annot.getUnType(), pageNo, x, y, userName, annot.getSoundData(), (int) annot.getSoundRate(),
                            (int) annot.getSoundChannels(), (int) annot.getSoundBitspersample(), creatTime);
                }
            }

            Message msg = new Message();
            msg.what = MSG_WHAT_REFRESHDOCUMENT;
            myHandler.sendMessage(msg);
        }

    }

    private void backButtonEvent() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 释放一些资源？
                PreviewContent.this.finish();
                finish();
            }
        });
    }

    private void initParentListener() {
        getController().setLoadPageFinishedListener(
            new ViewerActivityController.LoadPageFinishedListener() {
                @Override
                public void onLoadPageFinished() {
                    //填充模板，根据需求实现功能
                    if (!hasLoaded) {
                        HttpServer httpServer = new HttpServer();
                        if(userName!=null&&pdfId!=null) {
                            httpServer.insertAnnotationThread(userName, pdfId);
                        }
                        hasLoaded = true;
                    }

                }
            }
        );

        //同步接口
        super.setOnSynchronizationOptionListener(new OnSynchronizationOptionListener() {

            //删除手写签批的监听接口
            @Override
            public void onDeleteAnnot(String annotId) {
                Log.d("tbz", "onDeleteHandwriteAnnot annotId = " + annotId);

            }

            //插入文字注释的监听
            @Override
            public void onInsertTextAnnot(int pageNo, String annotId, float x,
                                          float y, String content, String author, String jsonString) {
           /*     Log.d("tbz", "onInsertTextAnnot annotId = " + annotId +
                        ", pageNo = " + pageNo + ", x = " + x + ", y = " + y + ", content = " + content
                        + ", author = " + author + ", jsonString = " + jsonString);*/
                String json = AnnotationToJsonUtil.annToJsonStr(pageNo,
                        annotId, x + "", y + "", 0 + "", 0 + "", author, content,
                        jsonString);
                Log.d("tbz", "保存批注！");
                //添加批注
                HttpServer httpServer = new HttpServer();
                if (userName==null) return;
                Log.d("tbz", "login_username:" + userName);
                if (pdfId==null) return;
                Log.d("tbz", "pdfid:" + pdfId);
                if (json==null) return;
                Log.d("tbz", "json:" + json);
                httpServer.AddAnnotThread(userName, pdfId, json);
            }

            //更新文字批注内容的监听
            @Override
            public void onUpdataTextAnnotRect(String annotId, int pageNo, float x,
                                              float y) {

                Log.d("tbz", "onUpdataTextAnnotRect annotId = " + annotId + ", pageNo = " + pageNo + ", x = " + x + ", y = " + y);

            }

            //移动文字批注的监听
            @Override
            public void onUpdateTextAnnotContent(String annotId, String content) {

                Log.d("tbz", "onUpdateTextAnnotContent annotId = " + annotId + ", content = " + content);
                String json = AnnotationToJsonUtil.updateTextannToJsonStr(
                        annotId, content);
                HttpServer httpServer = new HttpServer();
                if (userName==null) return;
                Log.d("tbz", "login_username:" + userName);
                if (pdfId==null) return;
                Log.d("tbz", "pdfid:" + pdfId);
                if (json==null) return;
                Log.d("tbz", "json:" + json);
                httpServer.updateTextAnnotContent(userName, pdfId, json);
            }

            //滑动的监听接口
            @Override
            public void onFling(float vX, float vY, Rect limits) {

                Log.d("tbz", "onFling vX = " + vX + ", vY = " + vY + ", limits = " + limits);

            }

            //插入手写签批的监听接口
            @Override
            public void onInsertHandwriteAnnot(int pageNo, String annotId, float x,
                                               float y, float w, float h, String imagePath,
                                               String imgName, String author, String annotInfo) {
                Log.d("tbz", "onInsertHandwriteAnnot imagePath = " + imagePath +
                        ", imgName = " + imgName + ", author = " + author + ", annotInfo = " + annotInfo + ", annotId = " + annotId + ", x = " + x
                        + ", y = " + y + ", w = " + w + ", h = " + h);

            }

            //滚动的监听接口
            @Override
            public void onScroll(int x, int y) {

                Log.d("tbz", "onScroll x = " + x + ", y = " + y);

            }

            //移动手写签批的监听接口
            @Override
            public void onUpdateHandwriteAnnot(String annotId, int pageNo, float x,
                                               float y) {

                Log.d("tbz", "onUpdateHandwriteAnnot annotId = " + annotId +
                        ", pageNo = " + pageNo + ", x = " + x + ", y = " + y);
            }

            //插入矢量签批的监听接口
            @Override
            public void onInsertVectorAnnot(int pageNo, String annotId, float x, float y,
                                            float w, float h, String author, String line,
                                            String annotInfo, boolean saveVector) {
                Log.d("tbz", "onInsertVectorAnnot author = " + author +
                        ", annotInfo = " + annotInfo + ", saveVector = " + saveVector + ", annotId = " + annotId + ", line = " + line);
                String json = AnnotationToJsonUtil.annToJsonStr(pageNo,
                        annotId, x + "", y + "", w + "", h + "", author, line,
                        annotInfo);

                if (isSaveOrSend == IS_SAVE_OR_SEND_1) {
                    Log.d("tbz", "保存批注！");
                    //添加批注
                    HttpServer httpServer = new HttpServer();
                    if (userName==null) return;
                    Log.d("tbz", "login_username:" + userName);
                    if (pdfId==null) return;
                    Log.d("tbz", "pdfid:" + pdfId);
                    if (json==null) return;
                    Log.d("tbz", "json:" + json);
                    httpServer.AddAnnotThread(userName, pdfId, json);
                } else if (isSaveOrSend == IS_SAVE_OR_SEND_2) {
                    Log.d("tbz", "发送批注！");
                    HttpServer httpServer = new HttpServer();
                    if (userName==null) return;
                    Log.d("tbz", "login_username:" + userName);
                    if (pdfId==null) return;
                    Log.d("tbz", "pdfid:" + pdfId);
                    if (json==null) return;
                    Log.d("tbz", "json:" + json);
                    httpServer.toSendAnnot(userName, json);
                }
            }
        });

    }


    /**
     * 初始化金格控件的参数,根据各自需求重写
     */
    private void initPDFParams() {
        Intent book_intent = getIntent();
        //授权码(必传)
        if (book_intent.hasExtra(LIC)) {
            copyRight = book_intent.getStringExtra(LIC); //授权码，必传，字符串格式
        }
        //文件路径（必须）
        if (book_intent.hasExtra(PDF_PATH)) {
            path = book_intent.getStringExtra(PDF_PATH);
        }
        //用户名，默认admin
        if (book_intent.hasExtra(NAME)) {
            //用户名，默认admin
            userName = book_intent.getStringExtra(NAME);
        }
        if (book_intent.hasExtra(KEY_PDFID)) {
            pdfId = book_intent.getStringExtra(KEY_PDFID);
        }
        //是否支持域编辑功能，在表单PDF文件中可体现此功能，默认为false
        if (book_intent.hasExtra(CANFIELDEIDT)) {
            isFieldEidt = book_intent.getBooleanExtra(CANFIELDEIDT, false);
        }
        //是否为E人E本模式，默认为false
        if (book_intent.hasExtra(T7MODENAME)) {
            isSupportEbenT7Mode = book_intent.getBooleanExtra(T7MODENAME, false);
        }
        if (book_intent.hasExtra(EBENSDKNAME)) {
            isUseEbenSDK = book_intent.getBooleanExtra(EBENSDKNAME, false);
        }
        //是否保存矢量信息到PDF文档中，默认为true(支持单笔删除，但较慢)，为false时删除一次的手写内容
        if (book_intent.hasExtra(SAVEVECTORNAME)) {
            isSaveVector = book_intent.getBooleanExtra(SAVEVECTORNAME, true);
        }
        //是否选用矢量方式，保存签批时通过此参判断是矢量方式还是图片方式保存
        if (book_intent.hasExtra(VECTORNAME)) {
            isVectorSign = book_intent.getExtras().getBoolean(VECTORNAME);
        }
        //阅读模式，默认PageViewMode.VSCROLL，竖向连续翻页,不需要重设可忽略
        if (book_intent.hasExtra(VIEWMODENAME)) {
            int mode = book_intent.getIntExtra(VIEWMODENAME, VIEWMODE_VSCROLL);
            switch (mode) {
                case VIEWMODE_VSCROLL:
                    pageViewMode = PageViewMode.VSCROLL;
                    break;
                case VIEWMODE_SINGLEV:
                    pageViewMode = PageViewMode.SINGLEV;
                    break;
                case VIEWMODE_SINGLEH:
                    pageViewMode = PageViewMode.SINGLEH;
                    break;
            }
        }
        //是否保留PDF上次阅读位置，默认为true,为false时每次都从第一页开始阅读
        if (book_intent.hasExtra(LOADCACHENAME)) {
            loadPDFReaderCache = book_intent.getBooleanExtra(LOADCACHENAME, true);
        }

        if (book_intent.hasExtra(FILE_NAME)) {
            fileName = book_intent.getStringExtra(FILE_NAME);
        }

        if (book_intent.hasExtra(FILE_DATA)) {
            fileData = book_intent.getByteArrayExtra(FILE_DATA);
        }

    }

    public void initPDFView(ViewGroup parent) {
        try {
            GLConfiguration.checkConfiguration();
            this.view = new GLView((IActivityController)this.getController());
            LayoutUtils.fillInParent(parent, this.view.getView());
            parent.addView(this.view.getView());
            //this.w = parent;
        } catch (Throwable var3) {
            Log.v("IAppPDFActivity", "initPDFView method found error");
        }

//        if(!this.D) {
//            this.b();
//            this.e();
//        }
    }

    private void initToolBar() {
        handWrite = findViewById(R.id.ac_hand_write);
        textInput = findViewById(R.id.ac_txt_input);
        actionsMenu = findViewById(R.id.action_menu);

        //手写页面
        handwriteView_layout = View.inflate(context,
                R.layout.signature_kinggrid_full, null);
        full_handWriteView = handwriteView_layout
                .findViewById(R.id.v_canvas1);
        handleWriteMenu = handwriteView_layout.findViewById(R.id.hand_write_menu);
        isSendPageFn = false;
        this.initBtnView(handwriteView_layout);


        handWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionsMenu.collapse();

                Message msg = new Message();
                msg.what = 100;
                myHandler2.sendMessage(msg);

                handleWriteMenu.expand();
            }
        });

//        handleWriteBtn = findViewById(R.id.btn_handle_write);
//        handleWriteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sxpzFromJs("admin");
//            }
//        });
    }


    // 判断是否已经打开手写批注
    public boolean isOpenWrite = false;
    private View handwriteView_layout;
    private PDFHandWriteView full_handWriteView;
    // 是否进行分页;(true 表示分页，false表示不分页)
    public boolean isSendPageFn = true;

    @SuppressLint("HandlerLeak")
    Handler myHandler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            openHandwriteAnnotation(handwriteView_layout, full_handWriteView);
        }
    };



    // 判断是否是保存或者发送(发送 0,保存1)
    public int isSaveOrSend = 0;
    /*
    * 初始化全文批注界面中的按钮，并设置按钮点击事件的监听接口
    */
    private void initBtnView(final View layout) {
        btnClose = layout.findViewById(R.id.btn_close);
        //关闭
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSendPageFn = true;
                closeWirte(layout);
            }
        });
        //保存
        btnSave = layout.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (full_handWriteView.canSave()) {
                    //添加等待交互
                    if (insertVertorFlag != 0) {
                        return;
                    }
                    final View dialog_view = getLayoutInflater().inflate(R.layout.insert_progress, null);
                    showViewToPDF(dialog_view);
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            Log.v("tbz", "doSaveHandwriteInfo run");
                            isSaveOrSend = IS_SAVE_OR_SEND_1;
                            insertVertorFlag = 1;
                            doSaveHandwriteInfo(true, false, full_handWriteView);
                            //取消等待交互
                            Message msg = new Message();
                            msg.what = MSG_WHAT_DISMISSDIALOG;
                            msg.obj = dialog_view;
                            myHandler.sendMessage(msg);

                        }
                    };
                    Thread thread = new Thread(runnable);
                    thread.start();
                } else {
                    Toast.makeText(context, "没有保存内容", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnUndo = layout.findViewById(R.id.btn_undo);
        btnUndo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                full_handWriteView.doUndoHandwriteInfo();
            }
        });

        btnRedo = layout.findViewById(R.id.btn_redo);
        btnRedo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                full_handWriteView.doRedoHandwriteInfo();
            }
        });

        btnClear =  layout.findViewById(R.id.btn_clear);
        btnClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                full_handWriteView.doClearHandwriteInfo();
            }
        });

        btnPen = layout.findViewById(R.id.btn_pen);
        btnPen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                full_handWriteView.setPenSettingInfoName("demo_type", "demo_size", "demo_color");
                full_handWriteView.doSettingHandwriteInfo();
            }
        });
        //发送
//        btnSend = layout.findViewById(R.id.btn_send);

        //按钮添加到工具栏
    }

    // 关闭手写签批
    private void closeWirte(View layout) {
        isOpenWrite = false;
        if (null != layout && null != full_handWriteView) {
            doCloseHandwriteInfo(layout, full_handWriteView);
        }
    }

    private NoteDialog note_dlg;
    /**
     * 打开批注或注释的列表
     *
     * @param subType 批注类型
     */
    private void openAnnotationList(final int subType) {
        note_dlg = new NoteDialog(context, PreviewContent.this, subType);
        note_dlg.show();
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                final ArrayList<Annotation> newNoteList = getAnnotationNewList("", subType);
                final Message msg = new Message();
                msg.what = MSG_WHAT_LOADANNOTCOMPLETE;
                final Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("annot_list", newNoteList);
                msg.setData(bundle);
                myHandler.sendMessage(msg);
            }

        }).start();
    }

    @SuppressLint("HandlerLeak")
    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_WHAT_DISMISSDIALOG:
                    View rm_view = (View) msg.obj;
                    hiddenViewFromPDF(rm_view);
                    hiddenViewFromPDF(handwriteView_layout);
                    break;
                case MSG_WHAT_LOADANNOTCOMPLETE:
                    //获取批注之后，更新批注清单
                    ArrayList<Annotation> list = msg.getData().getParcelableArrayList("annot_list");
                    for (int i = 0; i < list.size(); i++) {
                        Log.d("tbz", "annotId3 = " + list.get(i).getAnnotId());
                    }
                    note_dlg.updateView(list);
                    break;
                case MSG_WHAT_REFRESHDOCUMENT:
                    if (!touchEnable) {
                        touchEnable = true;
                    }
                    Log.d("pz", "刷新");
                    refreshDocument();
                    break;
            }
        }
    };

}
