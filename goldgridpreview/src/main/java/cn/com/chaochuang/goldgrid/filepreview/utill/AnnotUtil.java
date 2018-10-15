package cn.com.chaochuang.goldgrid.filepreview.utill;

import android.widget.Toast;
import cn.com.chaochuang.goldgrid.filepreview.PreviewContent;
import cn.com.chaochuang.goldgrid.filepreview.R;
import cn.com.chaochuang.goldgrid.filepreview.ui.TextAnnotDialog;
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
     * 插入文字批注
     *
     * @param x 插入批注在文档X值
     * @param y 插入批注在文档Y值
     */
    public void addTextAnnot(final float x, final float y) {
        final Annotation annotation = new Annotation();
        annotation.setAuthorName(userName);
        TextAnnotDialog showAnnotContent = new TextAnnotDialog(bookShower, annotation);
        //showAnnotContent.setDeleteBtnGone();
        showAnnotContent.setSaveAnnotListener(new TextAnnotDialog.OnSaveAnnotListener() {

            @Override
            public void onAnnotSave(final Annotation annotTextNew) {
                //						if (IAppPDFActivity.progressBarStatus != 0) { //正在渲染
                //							return;
                //						}
                bookShower.doSaveTextAnnot(annotTextNew, x, y);
            }
        });

    }

    /**
     * 显示文字批注
     *
     * @param annotation
     */
    public void showTextAnnot(final Annotation annotation) {

        if (!annotation.getAuthorName().equals(IAppPDFActivity.userName)) {
            Toast.makeText(bookShower, R.string.username_different_edit, Toast.LENGTH_SHORT).show();
        }
        TextAnnotDialog showAnnotContent = new TextAnnotDialog(bookShower, annotation);
        showAnnotContent.setSaveAnnotListener(new TextAnnotDialog.OnSaveAnnotListener() {

            @Override
            public void onAnnotSave(final Annotation annotTextNew) {
                bookShower.doUpdateTextAnnotation(annotTextNew);
            }
        });
        showAnnotContent.setDeleteAnnotListener(new TextAnnotDialog.OnDeleteAnnotListener() {

            @Override
            public void onAnnotDelete() {
                bookShower.doDeleteTextAnnotation(annotation);
            }
        });
    }

}
