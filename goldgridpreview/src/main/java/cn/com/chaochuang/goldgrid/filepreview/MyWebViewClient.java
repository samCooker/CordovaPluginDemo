package cn.com.chaochuang.goldgrid.filepreview;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import cn.com.chaochuang.goldgrid.filepreview.utill.MyConstant;

/**
 * Created by lyy on 2017/5/24.
 */

public class MyWebViewClient extends WebViewClient implements MyConstant {
    private Context mContext;
    private ProgressDialog loadPd;

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        // Toast.makeText(mContext, "同步失败，请稍候再试=====1",
        // Toast.LENGTH_LONG).show();
        handler.proceed();
    }

    public MyWebViewClient(Context context) {
        this.mContext = context;
    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.i("tbz","webviewUrl:"+url.toString());
        view.loadUrl(url);
        return true;
    }


    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        failingUrl  = failingUrl.substring(failingUrl.indexOf("://")+3, failingUrl.lastIndexOf("/"+PRO));
        view.loadUrl("file:///android_asset/err_no_connon.html?failingUrl="+failingUrl+"&appv=");
        super.onReceivedError(view, errorCode, description, failingUrl);

    }

    @Override
    public void onPageFinished(WebView view, String url) {

        if (loadPd.isShowing()) {
            loadPd.dismiss();
        }
        super.onPageFinished(view, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {

        if (loadPd == null) {
            loadPd = new ProgressDialog(mContext);
            loadPd.setMessage("页面加载中，请稍后...");
            loadPd.show();
            view.setEnabled(false);// 当加载网页的时候将网页进行隐藏
        }
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }

}
