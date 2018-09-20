package com.pinnet.chargerapp.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.utils.LogUtils;
import com.pinnet.chargerapp.utils.ScreenUtils;


/**
 * @author P00558
 * @date 2018/4/25
 * 充电动画
 */

public class BatteryView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    /**
     * 屏幕宽高
     */
    private int SCREEN_WIDTH;
    private int SCREEN_HEIGHT;
    /**
     * 容器宽高
     */
    private int containerWidth = SCREEN_WIDTH / 3;
    private int containerHeight;
    private int centerHeight;
    private int topMargin;
    private int bottomMargin;
    /**
     * 边框宽度
     */
    private int borderWidth = 10;

    /**
     * 边框颜色
     */
    private int borderColor = Color.parseColor("#5eff9933");
    private int itemBgColor = Color.parseColor("#ff9933");
    private Paint mContainerPaint;
    private Paint mProgressPaint;
    private Paint mInitBgPaint;
    private Thread thread;
    private SurfaceHolder surfaceHolder;
    private boolean isThreadRunning = true;
    private int currentProgress = 10;

    private Bitmap mBatteryTop;
    private Bitmap mBatteryBottom;
    private Bitmap mBatteryBackground;
    private Paint mBitmapPaint;
    private boolean mDrawFlag = true;
    private Matrix mMatrix = new Matrix();


    public BatteryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BatteryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BatteryView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mBatteryTop = BitmapFactory.decodeResource(getResources(), R.drawable.charger_battery_top);
        mBatteryBottom = BitmapFactory.decodeResource(getResources(), R.drawable.charger_battery_bottom);
        mBatteryBackground = BitmapFactory.decodeResource(getResources(), R.drawable.charger_battery_bg);
// 不使用硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        SCREEN_HEIGHT = SCREEN_HEIGHT - ScreenUtils.dip2px(getContext(), 40);
        borderWidth = ScreenUtils.dip2px(getContext(), 6);
        containerWidth = SCREEN_WIDTH / 3;
        centerHeight = containerHeight / 2;
        topMargin = containerWidth / 8;
        bottomMargin = containerWidth / 8;

        initPaint();
    }

    private void initPaint() {
        mMatrix.setRotate(10, 20, 20);

        mInitBgPaint = new Paint();
        mInitBgPaint.setColor(Color.parseColor("#00000000"));

        mContainerPaint = new Paint();
        mContainerPaint.setStrokeWidth(borderWidth);
        mContainerPaint.setColor(borderColor);
        mContainerPaint.setStyle(Paint.Style.STROKE);
        mContainerPaint.setAntiAlias(true);

        mProgressPaint = new Paint();
        mProgressPaint.setColor(itemBgColor);
        mProgressPaint.setStyle(Paint.Style.FILL);
        mProgressPaint.setAntiAlias(true);
        LinearGradient lg = new LinearGradient(0, 0, containerWidth, 80,
                new int[]{Color.parseColor("#FFcc66"), Color.parseColor("#FF9933"), Color.parseColor("#F8DA8B")}, null, Shader.TileMode.MIRROR);
        mProgressPaint.setShader(lg);
        // 设置画笔遮罩滤镜
        mProgressPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID));

        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);


    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mDrawFlag = true;
        isThreadRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isThreadRunning = false;
        synchronized (this) {
            mDrawFlag = false;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        if (modeWidth == MeasureSpec.AT_MOST || modeWidth == MeasureSpec.UNSPECIFIED) {
            sizeWidth = containerWidth;
        }
        if (modeHeight == MeasureSpec.AT_MOST || modeHeight == MeasureSpec.UNSPECIFIED) {
            containerHeight = sizeHeight;
        }
        containerHeight = sizeHeight;
        setMeasuredDimension(sizeWidth, sizeHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void onDrawBitmap(Canvas canvas) {
        canvas.drawBitmap(mBatteryBackground, null, new RectF(0, mBatteryTop.getHeight() + topMargin,
                containerWidth, containerHeight - mBatteryBottom.getHeight() - bottomMargin), mBitmapPaint);
        canvas.drawBitmap(mBatteryTop, null, new RectF(0, topMargin, containerWidth, mBatteryTop.getHeight() + topMargin), mBitmapPaint);
        canvas.drawBitmap(mBatteryBottom, null, new RectF(0, containerHeight - mBatteryBottom.getHeight() - bottomMargin, containerWidth, containerHeight - bottomMargin), mBitmapPaint);
    }

    private void drawProgress(Canvas canvas) {
        RectF rectF = new RectF(15, containerHeight - mBatteryBottom.getHeight() - currentProgress - topMargin,
                containerWidth - 15, containerHeight - mBatteryBottom.getHeight() - bottomMargin);
        canvas.drawRoundRect(rectF, 10, 10, mProgressPaint);
    }

    @Override
    public void run() {
        while (isThreadRunning) {

            /**取得更新之前的时间**/
            long startTime = System.currentTimeMillis();


            /**在这里加上线程安全锁**/
            synchronized (surfaceHolder) {
                if (mDrawFlag) {
                    Canvas canvas = surfaceHolder.lockCanvas(null);
                    try {
                        if (canvas != null) {
                            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                            currentProgress += 5;
                            if (containerHeight - mBatteryBottom.getHeight() - currentProgress - bottomMargin <
                                    topMargin + mBatteryTop.getHeight() + ScreenUtils.dip2px(getContext(), 5)) {
                                currentProgress = 10;
                            }
                            onDrawBitmap(canvas);
                            drawProgress(canvas);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        Surface surface = surfaceHolder.getSurface();
                        if (canvas != null && surface != null && surface.isValid()) {
                            try {
                                surfaceHolder.unlockCanvasAndPost(canvas);
                            } catch (Exception e) {
                                e.printStackTrace();
                                LogUtils.getInstance().e("surfaceview battery ", e);
                            }
                        }


                    }
                }

                /**取得更新结束的时间**/
                long endTime = System.currentTimeMillis();

                /**计算出一次更新的毫秒数**/
                int diffTime = (int) (endTime - startTime);

                /**确保每次更新时间为30帧**/
                while (diffTime <= 60) {
                    diffTime = (int) (System.currentTimeMillis() - startTime);
                    /**线程等待**/
                    Thread.yield();
                }
            }
        }
    }

    public void stopThread() {
        isThreadRunning = false;
    }
}
