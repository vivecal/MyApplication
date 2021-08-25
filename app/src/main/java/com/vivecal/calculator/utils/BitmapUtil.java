package com.vivecal.calculator.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 描述：
 * 创建日期:2021/7/7
 * 创建人：ChenKun
 */
public class BitmapUtil {
    /**
     * 将Drawable对象转化为Bitmap对象
     *
     * @param drawable Drawable对象
     * @return 对应的Bitmap对象
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap;

        //如果本身就是BitmapDrawable类型 直接转换即可
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        //取得Drawable固有宽高
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            //创建一个1x1像素的单位色图
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            //直接设置一下宽高和ARGB
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        //重新绘制Bitmap
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * @param base64Data 所需要被转换的base64字符串
     * @return Bitmap 返回转换好的bitmap
     * @Description 将base64形式的字符串转为bitmap
     * @author Mayouwei mayouwei@outlook.com
     * @date 2015年6月6日 下午2:36:08
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);// 将接收的字符串以base64的形式解码为字节数组
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);// 将字节数组转换为位图，然后返回该位图
    }

    /**
     * @param bitmap 所需要转换的bitmap
     * @return String 返回一个转换后的base64的字符串
     * @Description 将bitmap转为base64
     * @author Mayouwei mayouwei@outlook.com
     * @date 2015年6月6日 下午2:34:40
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;// 定义一个结果字符串(用于存储最后的结果)
        ByteArrayOutputStream baos = null;// 定义一个字节数组输出流
        try {
            if (bitmap != null) {// 如果接收到的图片存在
                baos = new ByteArrayOutputStream();// 实例化字节数组输出流对象
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 将图片以JPEG格式，不进行任何压缩，将其读到流中

                baos.flush();// 刷新流

                byte[] bitmapBytes = baos.toByteArray();// 将存有数据的字节数组流转换为字节数组
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT).replace("\n", "");// 将字节数组以字符串的形式存储起来
            }
        } catch (IOException e) {
        } finally {
            try {
                if (baos != null) {// 如果流存在
                    baos.flush();// 刷新流
                    baos.close();// 关闭流
                }
            } catch (IOException e) {
            }
        }
        return result;// 将转换后的base64字符串返回
    }
}
