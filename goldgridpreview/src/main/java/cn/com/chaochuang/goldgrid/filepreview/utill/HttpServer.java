package cn.com.chaochuang.goldgrid.filepreview.utill;

import android.content.Context;
import android.content.Intent;
import cn.com.chaochuang.goldgrid.filepreview.common.CustomApplication;
import com.kinggrid.pdfservice.Annotation;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 2018-2-13
 *
 * @author Shicx
 */

public class HttpServer {

    public static String APP_BASE_URL = "http://192.168.20.8:38080";

    public static String RELATE_URL = "/hysj/padi/";

    /**
     * 获取某用户文件全部注解信息
     *
     * @param username 请求服务的用户帐号
     * @param pdfID    文件唯一ID
     */
    public void insertAnnotationThread(String username, String pdfID) {
        //请求地址
        String absoluteUrl = APP_BASE_URL+RELATE_URL;
        //参数
        Map<String, String> map = new HashMap<String, String>();
        StringBuilder sb = new StringBuilder();
        sb.append(absoluteUrl);
        sb.append("501/");
        sb.append(username);
        sb.append("/");
        sb.append(pdfID);
        String url = sb.toString();
        OkHttpUtils.postAsyn(url, map, new HttpCallback() {
            @Override
            public void onSuccess(String resultDesc) {
                super.onSuccess(resultDesc);
                boolean result = false;
                //成功
                JSONObject jsonObjectFromString = JSONHelper.createJSONObjectFromString(resultDesc);
                String code = JSONHelper.getString(jsonObjectFromString, "code", "");
                //解析JSON数据
                JSONArray body = JSONHelper.getJSONArray(jsonObjectFromString, "body");
                if (body == null) {
                    result = false;
                } else {
                    result = true;
                    ArrayList<Annotation> list = new ArrayList<Annotation>();
                    int length = body.length();
                    for (int i = 0; i < length; i++) {
                        JSONObject jsonObject = JSONHelper.getJSONObject(body, i);
                        Annotation annotation = AnnotationToJsonUtil.createAnnotation(jsonObject);
                        list.add(annotation);
                    }

                    MyConstant.annotation_list.clear();
                    MyConstant.annotation_list.addAll(list);
                }
                if (result) {
                    Intent intent = new Intent();
                    intent.setAction(BroadCastActions.BC_GetAllAnnotSuccess);
                    HttpServer.sendBroadcast(intent);
                } else {
                    Intent intent = new Intent();
                    intent.setAction(BroadCastActions.BC_GetAllAnnotFailure);
                    HttpServer.sendBroadcast(intent);
                }

            }

            @Override
            public void onFailure(int code, String message) {
                super.onFailure(code, message);
                //失败
                Intent intent = new Intent();
                intent.setAction(BroadCastActions.BC_GetAllAnnotFailure);
                HttpServer.sendBroadcast(intent);
            }
        });
    }

    /**
     * 添加单个批注
     *
     * @param username 请求服务的用户帐
     * @param pdfID    文件唯一ID
     * @param json     批注构建出来的json数据
     */
    public void AddAnnotThread(String username, String pdfID, String json) {
        //请求地址
        String absoluteUrl = APP_BASE_URL+RELATE_URL;
        StringBuilder sb = new StringBuilder();
        sb.append(absoluteUrl);
        sb.append("502/");
        sb.append(username);
        sb.append("/");
        sb.append(pdfID);
        String url = sb.toString();
        OkHttpUtils.postAync(url, json, new HttpCallback() {
            @Override
            public void onSuccess(String resultDesc) {
                super.onSuccess(resultDesc);
                JSONObject jsonObjectFromString = JSONHelper.createJSONObjectFromString(resultDesc);
                String code = JSONHelper.getString(jsonObjectFromString, "code", "");
                Intent intent = new Intent();
                intent.setAction(BroadCastActions.BC_AddAnnotSuccess);
                HttpServer.sendBroadcast(intent);
            }

            @Override
            public void onFailure(int code, String message) {
                super.onFailure(code, message);
                Intent intent = new Intent();
                intent.setAction(BroadCastActions.BC_AddAnnotFailure);
                HttpServer.sendBroadcast(intent);
            }
        });
    }

    /**
     * 修改文字批注
     *
     * @param username 请求服务的用户帐号
     * @param pdfID    文件唯一ID
     * @param json     批注构建出来的json数据
     */
    public void updateTextAnnotContent(String username, String pdfID, String json) {
        String absoluteUrl = APP_BASE_URL+RELATE_URL;
        StringBuilder sb = new StringBuilder();
        sb.append(absoluteUrl);
        sb.append("505/");
        sb.append(username);
        sb.append("/");
        sb.append(pdfID);
        String url = sb.toString();
        OkHttpUtils.postAync(url, json, new HttpCallback() {
            @Override
            public void onSuccess(String resultDesc) {
                super.onSuccess(resultDesc);
                JSONObject jsonObjectFromString = JSONHelper.createJSONObjectFromString(resultDesc);
                String code = JSONHelper.getString(jsonObjectFromString, "code", "");
                Intent intent = new Intent();
                intent.setAction(BroadCastActions.BC_UpdateTextAnnotContentSuccess);
                HttpServer.sendBroadcast(intent);
            }

            @Override
            public void onFailure(int code, String message) {
                super.onFailure(code, message);
                Intent intent = new Intent();
                intent.setAction(BroadCastActions.BC_UpdateTextAnnotContentFailure);
                HttpServer.sendBroadcast(intent);
            }
        });
    }

    /**
     * 发送批注
     *
     * @param username 请求服务的用户帐
     * @param json     批注构建出来的json数据
     */
    public void toSendAnnot(String username, String json) {
        String absoluteUrl = APP_BASE_URL+RELATE_URL;
        StringBuilder sb = new StringBuilder();
        sb.append(absoluteUrl);
        sb.append("sendannot");
        sb.append("?un=");
        sb.append(username);
        final String url = sb.toString();
        OkHttpUtils.postAync(url, json, new HttpCallback() {
            @Override
            public void onSuccess(String resultDesc) {
                super.onSuccess(resultDesc);
                JSONObject jsonObjectFromString = JSONHelper.createJSONObjectFromString(resultDesc);
                String code = JSONHelper.getString(jsonObjectFromString, "code", "");
                Intent intent = new Intent();
                intent.setAction(BroadCastActions.BC_SendAnnotSuccess);
                HttpServer.sendBroadcast(intent);
            }

            @Override
            public void onFailure(int code, String message) {
                super.onFailure(code, message);
                Intent intent = new Intent();
                intent.setAction(BroadCastActions.BC_SendAnnotFailure);
                intent.putExtra("error", "code:" + code + ",message" + message + ",url:" + url);
                HttpServer.sendBroadcast(intent);
            }
        });
    }

    public static void sendBroadcast(Intent intent){
        Context context;
        context = CustomApplication.getContextObject();
        if (context != null) context.sendBroadcast(intent);
    }
}
