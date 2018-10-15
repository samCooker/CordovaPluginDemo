package com.chaochuang.oa.dataaec.util;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.util.*;

public class AesTool {

  //是否进行加密解密
  public static final boolean ENC_DEC_FLAG = true;
  public static final String EN_UTF8 = "UTF-8";
  private static String ivParameter = "ql5itsgsgyetrv7d";// 默认偏移

  private static String WAYS = "AES";
  private static String MODE = "";
  private static boolean isPwd = false;
  public static int MODE_TYPE = 1;

  private static final String PUBLIC_KEY = "PznhCA26zpkEqLmz";

  private static String val = "0";

  /**
   * 加密模式
   * @param type
   * @return
   */
  public static String selectMod(int type) {
    String modeCode = "PKCS5Padding";
    switch (type) {
      case 0:
        isPwd = false;
        MODE = WAYS + "/" + AesType.ECB.key() + "/" + modeCode;
        break;
      case 1:
        isPwd = true;
        MODE = WAYS + "/" + AesType.CBC.key() + "/" + modeCode;
        break;
      case 2:
        isPwd = true;
        MODE = WAYS + "/" + AesType.CFB.key() + "/" + modeCode;
        break;
      case 3:
        isPwd = true;
        MODE = WAYS + "/" + AesType.OFB.key() + "/" + modeCode;
        break;

    }

    return MODE;

  }

  /**
   * 加密
   * @param content
   * @return
   */
  public static String encode(String content){
    if(content==null||"".equals(content)){
      return "";
    }
    try {
      return new String(AesTool.encrypt(content, AesTool.PUBLIC_KEY, AesTool.MODE_TYPE));
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }

  /**
   * 加密
   * @param content
   * @param key
   * @return
   */
  public static String encode(String content,String key){
    if(content==null||"".equals(content)){
      return "";
    }
    try {
      return new String(AesTool.encrypt(content, key, AesTool.MODE_TYPE));
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }

  public static byte[] encrypt(String sSrc, String sKey, int type) throws Exception {
    sKey = toMakekey(sKey, 16, val);
    Cipher cipher = Cipher.getInstance(selectMod(type));
    byte[] raw = sKey.getBytes();
    SecretKeySpec skeySpec = new SecretKeySpec(raw, WAYS);

    IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
    if (!isPwd) {
      // ECB 不用密码
      cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
    } else {
      cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
    }

    byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
    return Base64.encode(encrypted);
  }

  // 解密
  public static String decode(String content){
    if(content==null||"".equals(content)){
      return "";
    }
    try {
      return decrypt(content, AesTool.PUBLIC_KEY, AesTool.MODE_TYPE);
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }

  public static String decrypt(String sSrc, String sKey, int type) throws Exception {
    sKey = toMakekey(sKey, 16, val);
    try {
      byte[] raw = sKey.getBytes("ASCII");
      SecretKeySpec skeySpec = new SecretKeySpec(raw, WAYS);
      Cipher cipher = Cipher.getInstance(selectMod(type));
      IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
      if (!isPwd) {// ECB 不用密码
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
      } else {
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
      }
      byte[] encrypted1 = Base64.decode(sSrc.getBytes());// 先用base64解密
      byte[] original = cipher.doFinal(encrypted1);
      return new String(original, AesTool.EN_UTF8);
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }

  // key
  public static String toMakekey(String str, int strLength, String val) {

    int strLen = str.length();
    if (strLen < strLength) {
      while (strLen < strLength) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(str).append(val);
        str = buffer.toString();
        strLen = str.length();
      }
    }
    return str;
  }

  /**
   * md5String
   *
   * @param s
   * @return
   */
  public final static String md5(String s) {
    char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    try {
      byte[] btInput = s.getBytes();
      MessageDigest mdInst = MessageDigest.getInstance("MD5");
      mdInst.update(btInput);
      // 获得密文
      byte[] md = mdInst.digest();
      // 把密文转换成十六进制的字符串形式
      int j = md.length;
      char str[] = new char[j * 2];
      int k = 0;
      for (int i = 0; i < j; i++) {
        byte byte0 = md[i];
        str[k++] = hexDigits[byte0 >>> 4 & 0xf];
        str[k++] = hexDigits[byte0 & 0xf];
      }
      return new String(str);
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }

  /**
   * 解析请求的字符串参数(p1=xx&p2=xx...)
   * @param decStr
   * @return
   */
  public static Map<String, Object> deserializeData(String decStr) {
    Map<String, Object> dataMap = new HashMap<String, Object>();
    try {
      if (decStr != null && !"".equals(decStr)) {

        String[] dataArr = decStr.split("&");
        for (String str : dataArr) {
          String[] params = new String[2];
          int i = str.indexOf("=");
          if (i >= 0) {
            params[0] = str.substring(0, i);
            if (i + 1 < str.length()) {
              params[1] = str.substring(i + 1);
            }
            if (params[1] == null) {
              params[1] = "";
            }
          }
          if (params[0] != null) {
            Object oldVal = dataMap.put(URLDecoder.decode(params[0], AesTool.EN_UTF8), URLDecoder.decode(params[1], AesTool.EN_UTF8));
            if (oldVal != null) {
              if (oldVal instanceof String) {
                dataMap.put(URLDecoder.decode(params[0], AesTool.EN_UTF8), new String[]{URLDecoder.decode(params[1], AesTool.EN_UTF8), oldVal.toString()});
              }
              if (oldVal instanceof String[]) {
                List<String> strArr = new ArrayList<String>(Arrays.asList((String[]) oldVal));
                strArr.add(URLDecoder.decode(params[1], AesTool.EN_UTF8));
                dataMap.put(URLDecoder.decode(params[0], AesTool.EN_UTF8), strArr.toArray());
              }
            }
          }
        }
      }
    }catch (Exception e){
      e.printStackTrace();
    }
    return dataMap;
  }


  public static void main(String[] args){
    System.out.println(encode("WQVgJWpZEBgWyE2ndVDzZEfQxttbMCcEXalTxPPCYT2bvb3NKCLPhF9C6RVdp9GjkGbXFo0Hu kws8jpQs5APo1d78aru4OQpRKmeap6oTlNZzw79Rg/7oewWrA3DQDUM6iH4CmIs6GoIo0 8FuWBl6zLGO/s8/QmGW408IIbdw="));
  }

}
