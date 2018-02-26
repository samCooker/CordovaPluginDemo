package cn.com.chaochuang.goldgrid.filepreview.utill;

import android.os.Environment;
import com.kinggrid.pdfservice.Annotation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by lyy on 2017/5/22.
 */

public interface MyConstant {
    final static int IS_SAVE_OR_SEND_0 = 0;//无状态
    final static int IS_SAVE_OR_SEND_2 = 2;//发送批注
    final static int IS_SAVE_OR_SEND_1 = 1;//保存批注

    final static String PRO = "mee_manager";//项目名称
    final static String MY_HTTP_HEAND = "http://";
    final static String API_HZ = "/hysj/padi/";
    final static String DOWANLOAD = "/hysj/";
    public final static SimpleDateFormat DATE_F1 = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    final static String CODE_17 = "17";
    final static String CODE_17_KEY = "key17";
    final static String CODE_502 = "502";
    final static String CODE_502_KEY = "key502";
    final static String CODE_500 = "500";
    final static String CODE_500_KEY = "key500";
    final static String CODE_501 = "501";
    final static String CODE_501_KEY = "key501";
    final static String CODE_503 = "503";
    final static String CODE_503_KEY = "key503";
    final static String CODE_504 = "504";
    final static String CODE_504_KEY = "key504";
    final static String CODE_505 = "505";
    final static String CODE_505_KEY = "key505";
    final static String DOWLOAD_URL_KEY = "dowloadUrl";//下载文件key
    final static String DOWLOAD_MYFILE = "downloadfile";//下载资料库文件
    final static String API_URL_KEY = "keyApiUrl";
    //版本更新信息
    final static String UPDATE_FILE_ROOT = "update";
    final static String UPDATE_URL = "update/update.json";
    final static String LV_ITEM_ID = "id";// 列表显示字段对应ID
    final static String LV_ITEM_NAME = "name";// 列表显示字段名称
    final static String LV_ITEM_TEXT = "text";// 列表显示文本名称
    final static String LOGIN_URL_KEY = "#/login";
    final static String SPD_FONT_SIZE_KEY = "spd_font_size";//设置字体大小样式保存缓存
    // final static String INIT_APP_URL = "http://192.168.1.104:8080/ad5/";

    final static String INIT_APP_URL = "192.168.20.8:38080";
    final static String INIT_PDF_DOWLOAD_URL = "192.168.20.8:38080";
    final static String APP_USERNAME_COPEY_KEY = "username_copey";//用户缓存
    final static String APP_URL_KEY = "app_url";// 保存URL KEY
    final static String PDF_DOWLOAD_URL_KEY = "pdf_dowload_url";// 保存URL KEY
    final static String PADI_SPF_KEY = "padi_spf";
    final static String REAL_NAME_KEY = "realname";//用户真实姓名
    final static String USER_NAME_KEY = "login_username";// 登录用户
    final static String MEETING_ID = "meeting_id";// 会议ID
    final static String Accessory_LIST = "Accessory_LIST";//文件map
    final static String Hostfn = "hostfn";//主持人姓名
    final static String Hostun = "hostun";//主持人账号

    final static String PDF_PATH = "path";

    /* final static int TYPE_ANNOT_HANDWRITE = 1; */
    final static int TYPE_ANNOT_STAMP = 1;// 全文批注
    final static int TYPE_ANNOT_TEXT = 2;// 文字注释
    final static int TYPE_ANNOT_SIGNATURE = 3;// 签名
    final static int TYPE_ANNOT_SOUND = 4;// 语音注释

    final static String SDCARD_PATH = Environment.getExternalStorageDirectory().getPath().toString();
    final static String PDF_TY_0_PATH = SDCARD_PATH + "/padi/notice";
    final static String PDF_TY_1_PATH = SDCARD_PATH + "/padi/meeting";
    final static String PDF_TY_2_PATH = SDCARD_PATH + "/padi/myfile";
    //2017-05-09
    final static String CacheDb = SDCARD_PATH + "/padi/cachedb";
    final static String CacheApp = SDCARD_PATH + "/padi/cacheapp";
    final static String PDF_SUFFIX = ".pdf";// PDF后缀名
    final static String KEY_PDFID = "pdfid";// PDFID
    final static String KEY_PDFTRUENAME = "pdftruename";// PDFTRUENAME
    // intent传递名称,实际使用中根据需要自定义名称
    final String NAME = "user_name";
    final String LIC = "cc_lic";
    final String CANFIELDEIDT = "demo_fieldEdit";
    final String T7MODENAME = "demo_T7Mode";
    final String EBENSDKNAME = "demo_ebenSDK";
    final String SAVEVECTORNAME = "demo_savevectortopdf";
    final String VECTORNAME = "demo_vectorsign";
    final String VIEWMODENAME = "demo_viewMode";
    final String LOADCACHENAME = "demo_loadCache";
    final String ANNOTPROTECTNAME = "demo_annotprotect";
    final String FILLTEMPLATE = "demo_filltemplate";
    final String ANNOT_TYPE = "demo_annottype";

    final String FILE_DATA = "demo_filedata";
    final String FILE_NAME = "demo_filename";
    // 阅读模式
    final int VIEWMODE_VSCROLL = 101;
    final int VIEWMODE_SINGLEH = 102;
    final int VIEWMODE_SINGLEV = 103;

    // Handler
    final int MSG_WHAT_DISMISSDIALOG = 201;
    final int MSG_WHAT_LOADANNOTCOMPLETE = 202;

    final int MSG_WHAT_REFRESHDOCUMENT = 203;

    // 拍照需要的参数
    final int REQUESTCODE_PHOTOS_TAKE = 100;
    final int REQUESTCODE_PHOTOS_CROP = 200;

    // 签名方式：域定位、位置定位、文字定位、数字签名等
    final int SIGN_MODE_FIELDNAME = 301;
    final int SIGN_MODE_TEXT = 302;
    final int SIGN_MODE_POSITION = 303;
    final int SIGN_MODE_SERVER = 304;
    final int SIGN_MODE_KEY = 305;
    final int SIGN_MODE_BDE = 306;


    //自定义
    final static int KEY_DOCUMENT_SAVE = 0;// 保存
    final static int KEY_FULL_SINGER = 1;// 全文批注
    final static int KEY_TEXT_NOTE = 2;// 文字注释
    final static int KEY_HIDE_ALLAnnotations = 3;//隐藏所以得注释
    final static int KEY_SHOW_ALLAnnotations = 4;//显示所以得注释
    final static int KEY_DEL_ALLPostil = 5;// 删除批注
    final static int KEY_START_Synchronization = 6;// 发起同步
    final static int KEY_READ_Synchronization = 7;// 同步阅读
    final static int KEY_follow_read = 8; // 跟读
    final static int KEY_VOICE = 9;// 语音播报
    final static int KEY_BOOKMARK_LIST = 10;// 大纲
    final static int KEY_select_file = 11;// 切换文件

    //初始化时获取PDF文件的所有标注
    public static ArrayList<Annotation> annotation_list = new ArrayList<Annotation>();
}
