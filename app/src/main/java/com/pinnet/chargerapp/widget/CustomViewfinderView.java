package com.pinnet.chargerapp.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.ViewfinderView;
import com.pinnet.chargerapp.utils.SystemUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * @author P00558
 * @date 2018/4/12
 * zxing 自定义扫描View
 */

public class CustomViewfinderView extends ViewfinderView {
    private boolean isBarcode = false;
    /**
     * 重绘时间间隔
     */
    public static final long CUSTOME_ANIMATION_DELAY = 16;

    /* ******************************************    边角线相关属性    ************************************************/

    /**
     * "边角线长度/扫描边框长度"的占比 (比例越大，线越长)
     */
    public float mLineRate = 0.1F;

    /**
     * 边角线厚度 (建议使用dp)
     */
    public float mLineDepth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics());

    /**
     * 边角线颜色
     */
    public int mLineColor = Color.WHITE;

    /* *******************************************    扫描线相关属性    ************************************************/

    /**
     * 扫描线起始位置
     */
    public int mScanLinePosition = 0;

    /**
     * 扫描线厚度
     */
    public float mScanLineDepth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());

    /**
     * 扫描线每次重绘的移动距离
     */
    public float mScanLineDy = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());

    /**
     * 线性梯度
     */
    public LinearGradient mLinearGradient;

    /**
     * 线性梯度位置
     */
    public float[] mPositions = new float[]{0f, 0.5f, 1f};

    /**
     * 线性梯度各个位置对应的颜色值
     */
    public int[] mScanLineColor = new int[]{Color.RED, Color.RED, Color.RED};


    public CustomViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void refreshSizes() {
        if (cameraPreview == null) {
            return;
        }

        //做多分辨率适配
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        double widthScale = (double) screenWidth / 1080;
        double heightScale = (double) screenHeight / 1920;

//        Rect framingRect = cameraPreview.getFramingRect();

        //切换扫描框大小
        Rect framingRect;
        if (isBarcode) {
            framingRect = new Rect(
                    (int) (30 * widthScale),
                    (int) (582 * heightScale),
                    (int) (1050 * widthScale),
                    (int) (1032 * heightScale));//条形码框
        } else {
            framingRect = new Rect(
                    (int) (165 * widthScale),
                    (int) (432 * heightScale),
                    (int) (915 * widthScale),
                    (int) (1182 * heightScale));//二维码框
        }

        Rect previewFramingRect = cameraPreview.getPreviewFramingRect();
        if (framingRect != null && previewFramingRect != null) {
            this.framingRect = framingRect;
            this.previewFramingRect = previewFramingRect;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        refreshSizes();
        if (framingRect == null || previewFramingRect == null) {
            return;
        }

        Rect frame = framingRect;
        Rect previewFrame = previewFramingRect;

        int width = canvas.getWidth();
        int height = canvas.getHeight();

//        //绘制4个角
//        paint.setColor(mLineColor); // 定义画笔的颜色
//        canvas.drawRect(frame.left, frame.top, frame.left + frame.width() * mLineRate, frame.top + mLineDepth, paint);
//        canvas.drawRect(frame.left, frame.top, frame.left + mLineDepth, frame.top + frame.height() * mLineRate, paint);
//
//        canvas.drawRect(frame.right - frame.width() * mLineRate, frame.top, frame.right, frame.top + mLineDepth, paint);
//        canvas.drawRect(frame.right - mLineDepth, frame.top, frame.right, frame.top + frame.height() * mLineRate, paint);
//
//        canvas.drawRect(frame.left, frame.bottom - mLineDepth, frame.left + frame.width() * mLineRate, frame.bottom, paint);
//        canvas.drawRect(frame.left, frame.bottom - frame.height() * mLineRate, frame.left + mLineDepth, frame.bottom, paint);
//
//        canvas.drawRect(frame.right - frame.width() * mLineRate, frame.bottom - mLineDepth, frame.right, frame.bottom, paint);
//        canvas.drawRect(frame.right - mLineDepth, frame.bottom - frame.height() * mLineRate, frame.right, frame.bottom, paint);

        // Draw the exterior (i.e. outside the framing rect) darkened
        paint.setColor(resultBitmap != null ? resultColor : maskColor);
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //绘制扫码框4个边角弧形

            //多分辨率适配
            int resultRadius = SystemUtil.dip2px(getContext(), 10);

            //左上角
            Path ltPath1 = new Path();
            Path ltPath2 = new Path();
            ltPath1.addRect(frame.left, frame.top, frame.left + resultRadius, frame.top + resultRadius, Path.Direction.CCW);
            ltPath2.addCircle(frame.left + resultRadius, frame.top + resultRadius, resultRadius, Path.Direction.CCW);
            ltPath1.op(ltPath2, Path.Op.DIFFERENCE);
            canvas.drawPath(ltPath1, paint);
            //右上角
            Path rtPath1 = new Path();
            Path rtPath2 = new Path();
            rtPath1.addRect(frame.right - resultRadius, frame.top, frame.right + 1, frame.top + resultRadius, Path.Direction.CCW);
            rtPath2.addCircle(frame.right - resultRadius, frame.top + resultRadius, resultRadius, Path.Direction.CCW);
            rtPath1.op(rtPath2, Path.Op.DIFFERENCE);
            canvas.drawPath(rtPath1, paint);
            //左下角
            Path lbPath1 = new Path();
            Path lbPath2 = new Path();
            lbPath1.addRect(frame.left, frame.bottom - resultRadius, frame.left + resultRadius, frame.bottom + 1, Path.Direction.CCW);
            lbPath2.addCircle(frame.left + resultRadius, frame.bottom - resultRadius, resultRadius, Path.Direction.CCW);
            lbPath1.op(lbPath2, Path.Op.DIFFERENCE);
            canvas.drawPath(lbPath1, paint);
            //右下角
            Path rbPath1 = new Path();
            Path rbPath2 = new Path();
            rbPath1.addRect(frame.right - resultRadius, frame.bottom - resultRadius, frame.right + 1, frame.bottom + 1, Path.Direction.CCW);
            rbPath2.addCircle(frame.right - resultRadius, frame.bottom - resultRadius, resultRadius, Path.Direction.CCW);
            rbPath1.op(rbPath2, Path.Op.DIFFERENCE);
            canvas.drawPath(rbPath1, paint);
        }
        if (resultBitmap != null) {
            // Draw the opaque result bitmap over the scanning rectangle
            paint.setAlpha(CURRENT_POINT_OPACITY);
            canvas.drawBitmap(resultBitmap, null, frame, paint);
        } else {
            // 绘制扫描线
            mScanLinePosition += mScanLineDy;
            if (mScanLinePosition > frame.height()) {
                mScanLinePosition = 0;
            }
            mLinearGradient = new LinearGradient(frame.left, frame.top + mScanLinePosition, frame.right, frame.top + mScanLinePosition, mScanLineColor, mPositions, Shader.TileMode.CLAMP);
            paint.setShader(mLinearGradient);
            canvas.drawRect(frame.left, frame.top + mScanLinePosition, frame.right, frame.top + mScanLinePosition + mScanLineDepth, paint);
            paint.setShader(null);

            float scaleX = frame.width() / (float) previewFrame.width();
            float scaleY = frame.height() / (float) previewFrame.height();

            List<ResultPoint> currentPossible = possibleResultPoints;
            List<ResultPoint> currentLast = lastPossibleResultPoints;
            int frameLeft = frame.left;
            int frameTop = frame.top;
            if (currentPossible.isEmpty()) {
                lastPossibleResultPoints = null;
            } else {
                possibleResultPoints = new ArrayList<>(5);
                lastPossibleResultPoints = currentPossible;
                paint.setAlpha(CURRENT_POINT_OPACITY);
                paint.setColor(resultPointColor);
                for (ResultPoint point : currentPossible) {
                    canvas.drawCircle(frameLeft + (int) (point.getX() * scaleX),
                            frameTop + (int) (point.getY() * scaleY),
                            POINT_SIZE, paint);
                }
            }
            if (currentLast != null) {
                paint.setAlpha(CURRENT_POINT_OPACITY / 2);
                paint.setColor(resultPointColor);
                float radius = POINT_SIZE / 2.0f;
                for (ResultPoint point : currentLast) {
                    canvas.drawCircle(frameLeft + (int) (point.getX() * scaleX),
                            frameTop + (int) (point.getY() * scaleY),
                            radius, paint);
                }
            }
        }

        // Request another update at the animation interval, but only repaint the laser line,
        // not the entire viewfinder mask.
        postInvalidateDelayed(CUSTOME_ANIMATION_DELAY,
                frame.left,
                frame.top,
                frame.right,
                frame.bottom);
    }
}