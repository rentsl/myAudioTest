package com.audio.rentsl.myaudiotest.wave;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.ColorInt;

/**
 * 波纹渲染逻辑
 */
public class WaveformRenderer3 implements WaveformRenderer {

    @ColorInt private final int mBackgroundColor;
    private final Paint mForegroundPaint;
    private final Path mWaveformPath;

    private Rect mRect = new Rect();
    private Paint mForePaint = new Paint();
    private float[] mPoints;

    private WaveformRenderer3(@ColorInt int backgroundColor, Paint foregroundPaint, Path waveformPath) {
        mBackgroundColor = backgroundColor;
        mForegroundPaint = foregroundPaint;
        mWaveformPath = waveformPath;
    }

    private static WaveformRenderer3 single=null;
    public static WaveformRenderer3 newInstance(@ColorInt int backgroundColor, @ColorInt int foregroundColour) {
        if (single == null) {
            Paint paint = new Paint();
            paint.setColor(foregroundColour);
            paint.setAntiAlias(true); // 抗锯齿
            paint.setStrokeWidth(8.0f); // 设置宽度
            paint.setStyle(Paint.Style.STROKE); // 填充

            Path waveformPath = new Path();

            return new WaveformRenderer3(backgroundColor, paint, waveformPath);
        }else{
            return single;
        }

    }

    @Override public void render(Canvas canvas, byte[] waveform) {
        float width = canvas.getWidth();
        float height = canvas.getHeight();
        mForePaint.setStrokeWidth(1f);
        mForePaint.setAntiAlias(true);
        mForePaint.setColor(Color.rgb(0, 128, 255));
        mRect.set(0,0,(int)width,(int)height);

        // -------绘制柱状的波形图（每隔10个抽样点绘制一个矩形）-------
        for (int i = 0; i < waveform.length - 1; i += 10) {
            float left = mRect.width() * i / (waveform.length - 1);
            // 根据波形值计算该矩形的高度
            float top = mRect.height()/2 - (byte) (waveform[i + 1] + 128)
                    * (mRect.height()/2) / 128;
            float right = left + 6;
            float bottom = mRect.height()/2;
            canvas.drawRect(left, top, right, bottom, mForePaint);
        }

    }
}
