package cn.com.chaochuang.goldgrid.filepreview;

import android.widget.Toast;
import com.kinggrid.iapppdf.ui.viewer.IAppPDFActivity;
import com.kinggrid.pdfservice.Annotation;

/**
 * Created by lyy on 2017/5/24.
 * 批注工具类：插入和显示批注
 * com.kinggrid.iapppdf.demo.AnnotUtil
 */

public class AnnotUtil {
    private PreviewContent bookShower;
    private String userName;

    public AnnotUtil(PreviewContent bookShower, String userName) {
        this.bookShower = bookShower;
        this.userName = userName;
    }
    /**
     * 插入语音批注
     * @param x 插入批注在文档X值
     * @param y 插入批注在文档Y值
     */
    public void addSoundAnnot(final float x,final float y){

    }

}
