package cn.com.chaochuang.goldgrid.filepreview.utill;

import android.annotation.SuppressLint;
import android.util.Log;
import com.kinggrid.pdfservice.Annotation;
import com.kinggrid.signature.commen.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * Created by lyy on 2017/5/24.
 */
@SuppressLint("DefaultLocale")
public class AnnotationToJsonUtil implements MyConstant {

    private static final String TAG = "BookShower";

    public static Annotation createAnnotation(JSONObject o) {
        try {
            Annotation annotation = new Annotation();
            annotation.setAnnotId(o.getString("annotid"));
            annotation.setPageNo(o.getString("pageno"));
            annotation.setX(o.getString("x"));
            annotation.setY(o.getString("y"));
            annotation.setWidth(o.getString("width"));
            annotation.setHeight(o.getString("height"));
            annotation.setCreateTime(o.getString("createtime")) ;
            annotation.setAuthorName(o.getString("authorname"));
            annotation.setAuthorId("");
            String styleName = "";
            if (o.has("stylename")) {
                styleName = o.getString("stylename");
            }
            annotation.setStyleName(styleName);
            String unType = "";
            if (o.has("untype")) {
                unType = o.getString("untype");
            }
            annotation.setUnType(unType);
            // System.out.println("stylename=="+o.getString("stylename")+"=="+o.has("annotcontent")+"=>"+o.getString("annotsignature").length());
            if (o.getString("stylename").equals("Stamp")
                    || o.getString("stylename").equals("Text")
                    || o.getString("stylename").equals("test")
                    || o.getString("stylename").equals("unknown")) {
                if (o.has("annotcontent")) {
                    annotation.setAnnoContent(o.getString("annotcontent"));
                } else {
                    byte[] annotSignature = AnnotationToJsonUtil.hexStr2Bytes(o
                            .getString("annotsignature"));
                    String annotContent = FileUtils.getPdfWriteImagePath();
                    annotation.setAnnoContent(annotContent);
                    writeToFile(annotContent, annotSignature);
                }
            }
            return annotation;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void writeToFile(String path, byte[] data) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(path);
            outputStream.write(data);
            outputStream.flush();
        } catch (Exception e) {
            System.out.println("ERR>" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    // 转JSON
    public static String annToJsonStr(int pageNo, String annotId, String x,
                                      String y, String w, String h, String author, String line,
                                      String jsonString) {
        try {
            Log.i("BookShower", pageNo + "," + annotId + "," + x + "," + y
                    + "," + w + "," + h + "," + author + "," + line + ","
                    + jsonString);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("annotid", annotId);
            jsonObject.put("authorname", author);
            jsonObject.put("untype", "");
            JSONTokener jsonParser = new JSONTokener(jsonString);
            JSONObject annotInfoJB = (JSONObject) jsonParser.nextValue();
            jsonObject.put("stylename", annotInfoJB.getString("type"));
            jsonObject.put("pageno", pageNo);
            jsonObject.put("x", x);
            jsonObject.put("y", y);
            jsonObject.put("width", w);
            jsonObject.put("height", h);
            jsonObject.put("createtime", DATE_F1.format(new Date()).toString());
            jsonObject.put("annotcontent", line);
            jsonObject.put("annotsignature", "");
            // JSONObject object = new JSONObject();
            // object.put("json", jsonObject);
            // Log.d(TAG, "json="+jsonObject);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    // 转JSON
    public static String updateTextannToJsonStr(String annotId, String content) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("annotid", annotId);
            jsonObject.put("annotContent", content);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] hexStr2Bytes(String hex) {
        if (hex == null || hex.equals("")) {
            return null;
        }
        hex = hex.toLowerCase();
        int len = hex.length() / 2;
        char[] hexChars = hex.toCharArray();
        byte[] b = new byte[len];
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            b[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return b;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * 将数组转换为JSON格式的数据。
     * 数据源
     * @return JSON格式的数据
     */
    public static String AnnotationToJson(List<Annotation> annotList) {
        try {
            JSONArray array = new JSONArray();
            int length = annotList.size();
            for (int i = 0; i < length; i++) {
                Annotation annot = annotList.get(i);
                JSONObject jsonObject = new JSONObject();
                String authorId = annot.getAuthorId();
                String authorName = annot.getAuthorName();
                String pageNo = annot.getPageNo();
                String X = annot.getX();
                String Y = annot.getY();
                String width = annot.getWidth();
                String height = annot.getHeight();
                String styleName = annot.getStyleName();
                String createTime = "2016-07-12 23:59:06";
                // annot.getCreateTime();
                Log.i(TAG, i + "=createTime=" + annot.getCreateTime());
                String annotContent = annot.getAnnoContent();
                String unType = annot.getUnType();
                jsonObject.put("authorid", authorId);
                jsonObject.put("authorname", authorName);
                jsonObject.put("untype", unType);
                jsonObject.put("stylename", styleName);
                jsonObject.put("pageno", pageNo);
                jsonObject.put("x", X);
                jsonObject.put("y", Y);
                jsonObject.put("width", width);
                jsonObject.put("height", height);
                jsonObject.put("createtime", createTime);
                jsonObject.put("annotcontent", annotContent);
                if (annot.getStyleName().equals("Stamp")) {
                    jsonObject.put("styleid", "12");
                    if (annotContent != null) {
                        if (annotContent.startsWith("q")) {
                            jsonObject.put("annotsignature", "");
                        } else {
                            jsonObject.put("annotsignature",
                                    bytesToHexString(getBytesFromFile(new File(
                                            annotContent))));
                        }
                    }
                } else if (annot.getStyleName().equals("Text")) {
                    jsonObject.put("styleid", "0");
                    jsonObject.put("annotsignature", "");
                }
                array.put(jsonObject);
            }
            JSONObject object = new JSONObject();
            object.put("json", array);
            return object.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    private static byte[] getBytesFromFile(File f) {
        if (f == null) {
            return null;
        }
        try {
            FileInputStream stream = new FileInputStream(f);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = stream.read(b)) != -1)
                out.write(b, 0, n);
            stream.close();
            out.close();
            Log.i("tbz", "out = " + out.toByteArray());
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
