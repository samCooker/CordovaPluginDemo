package cn.com.chaochuang.goldgrid.filepreview.utill;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lyy on 2017/5/18.
 */

public class JSONHelper {
    public static JSONObject createJSONObjectFromString(String resource){
        JSONObject result = null;
        if( resource == null ) return null;
        if( resource.isEmpty() ) return null;
        try {
            result = new JSONObject(resource);
        } catch (JSONException e) {
            e.printStackTrace();
            result = null;
        }

        return result;
    }

    public static JSONObject getJSONObject(JSONObject source, String name){
        JSONObject result = null;
        if(source == null) return null;
        if(name == null) return null;
        if(name.isEmpty()) return null;
        try {
            result = source.getJSONObject(name);
        } catch (JSONException e) {
            e.printStackTrace();
            result = null;
        }

        return result;
    }

    public static JSONArray getJSONArray(JSONObject source, String name){
        JSONArray result = null;
        if(source == null) return null;
        if(name == null) return null;
        if(name.isEmpty()) return null;
        try {
            result = source.getJSONArray(name);
        } catch (JSONException e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }

    public static String getString(JSONObject source, String name) {
        String result = null;
        if(source == null) return null;
        if(name == null) return null;
        if(name.isEmpty()) return null;
        try {
            result = source.getString(name);
        } catch (JSONException e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }

    public static String getString(JSONObject source, String name, String defaultValue) {
        String result = null;
        if(source == null) return defaultValue;
        if(name == null) return defaultValue;
        if(name.isEmpty()) return defaultValue;
        try {
            result = source.getString(name);
            if(result == null) result = defaultValue;
        } catch (JSONException e) {
            e.printStackTrace();
            result = defaultValue;
        }
        return result;
    }

    public static int getInt(JSONObject source, String name) {
        int result = 0;
        if(source == null) return 0;
        if(name == null) return 0;
        if(name.isEmpty()) return 0;
        try {
            result = source.getInt(name);
        } catch (JSONException e) {
            e.printStackTrace();
            result = 0;
        }
        return result;
    }

    public static int getInt(JSONObject source, String name, int defaultValue) {
        int result = 0;
        if(source == null) return defaultValue;
        if(name == null) return defaultValue;
        if(name.isEmpty()) return defaultValue;
        try {
            result = source.getInt(name);
        } catch (JSONException e) {
            e.printStackTrace();
            result = defaultValue;
        }
        return result;
    }

    public static boolean getBoolean(JSONObject source, String name) {
        boolean result = false;
        if(source == null) return false;
        if(name == null) return false;
        if(name.isEmpty()) return false;
        try {
            result = source.getBoolean(name);
        } catch (JSONException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public static boolean getBoolean(JSONObject source, String name, boolean defaultValue) {
        boolean result = false;
        if(source == null) return defaultValue;
        if(name == null) return defaultValue;
        if(name.isEmpty()) return defaultValue;
        try {
            result = source.getBoolean(name);
        } catch (JSONException e) {
            e.printStackTrace();
            result = defaultValue;
        }
        return result;
    }

    public static long getLong(JSONObject source, String name) {
        long result = 0;
        if(source == null) return 0;
        if(name == null) return 0;
        if(name.isEmpty()) return 0;
        try {
            result = source.getLong(name);
        } catch (JSONException e) {
            e.printStackTrace();
            result = 0;
        }
        return result;
    }

    public static long getLong(JSONObject source, String name, long defaultValue) {
        long result = 0;
        if(source == null) return defaultValue;
        if(name == null) return defaultValue;
        if(name.isEmpty()) return defaultValue;
        try {
            result = source.getLong(name);
        } catch (JSONException e) {
            e.printStackTrace();
            result = defaultValue;
        }
        return result;
    }

    public static double getDouble(JSONObject source, String name) {
        double result = 0;
        if(source == null) return 0;
        if(name == null) return 0;
        if(name.isEmpty()) return 0;
        try {
            result = source.getLong(name);
        } catch (JSONException e) {
            e.printStackTrace();
            result = 0;
        }
        return result;
    }

    public static double getDouble(JSONObject source, String name, double defaultValue) {
        double result = 0;
        if(source == null) return defaultValue;
        if(name == null) return defaultValue;
        if(name.isEmpty()) return defaultValue;
        try {
            result = source.getLong(name);
        } catch (JSONException e) {
            e.printStackTrace();
            result = defaultValue;
        }
        return result;
    }
    public static JSONObject getJSONObject(JSONArray source, int i){
        JSONObject result = null;
        if(source == null) return null;
        try {
            result = source.getJSONObject(i);
        } catch (JSONException e) {
            e.printStackTrace();
            result = null;
        }

        return result;
    }
}
