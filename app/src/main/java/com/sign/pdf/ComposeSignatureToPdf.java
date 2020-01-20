package com.sign.pdf;

import android.graphics.Bitmap;

import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.sign.com.MyApplication;
import com.sign.util.LogUtils;
import com.sign.util.SPUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

public class ComposeSignatureToPdf {

    private float mImgWidthPercent; //图片宽度占缩放后的pdf的百分比
    private float mImgHeightPercent; //图片高度占缩放后的pdf的百分比
    float widthScale; //图片缩放后，X相对于左下角的距离的百分比
    float heightScale; //图片缩放后，Y相对于左下角的距离的百分比
    String inPath;//当前的PDF地址
    String outPath;//要输出的PDF地址
    private int pageNum;//签名所在的页码
    private Bitmap bitmap;//签名图像

    public void setWidthScale(float widthScale) {
        this.widthScale = widthScale;
    }

    public void setHeightScale(float heightScale) {
        this.heightScale = heightScale;
    }

    /**
     * 设置嵌入的图片
     *
     * @param bitmap
     */
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    /**
     * 设置需要嵌入的页面
     *
     * @param pageNum
     */
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public ComposeSignatureToPdf(String inPath, String outPath) {
        this.inPath = inPath;
        this.outPath = outPath;
    }

    /**
     * 将图片加入PDF并保存
     */
    public void addText() {
        try {
            PdfReader reader = new PdfReader(inPath, "PDF".getBytes());///打开要写入的PDF
            FileOutputStream outputStream = new FileOutputStream(outPath);//设置涂鸦后的PDF
            LogUtils.e("要写入的PDF：", "inPath:  " + inPath);
            LogUtils.e("涂鸦后的PDF：", "outPath: " + outPath);
            SPUtils.getInstance(MyApplication.getActivity()).save("path_tu_ya", outPath);
            PdfStamper stamp;
            stamp = new PdfStamper(reader, outputStream);
            PdfContentByte over = stamp.getOverContent(pageNum);//用于设置在第几页打印签名

            byte[] bytes = Bitmap2Bytes(bitmap);
            Image img = Image.getInstance(bytes);//将要放到PDF的图片传过来，要设置为byte[]类型
            com.lowagie.text.Rectangle rectangle = reader.getPageSize(pageNum); //域
            img.setAlignment(0);//控件透明
            // 实际PDF*图片占放大后PDF的百分比= 要缩放的图片大小
            img.scaleAbsolute(rectangle.getWidth() * mImgWidthPercent, rectangle.getHeight() * mImgHeightPercent);
            img.setAbsolutePosition(rectangle.getWidth() * widthScale, rectangle.getHeight() * heightScale);
            over.addImage(img);
            stamp.close();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("执行合成错误！！！！！！！！！！！");
        }
    }

    /**
     * 将BitMap转换为Bytes
     *
     * @param bm
     * @return
     */
    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }


    //图片占PDF文档大小的百分比
    public void setImgPercent(float widthPercent, float heightPercent) {
        mImgWidthPercent = widthPercent;
        mImgHeightPercent = heightPercent;
    }
}
