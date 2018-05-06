package com.audio.rentsl.myaudiotest.wave;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.ColorInt;

/**
 * 波纹渲染逻辑
 */
public class WaveformRenderer2 implements WaveformRenderer {

    @ColorInt private final int mBackgroundColor;
    private final Paint mForegroundPaint;
    private final Path mWaveformPath;

    private Rect mRect = new Rect();
    private Paint mForePaint = new Paint();
    private float[] mPoints;

    private WaveformRenderer2(@ColorInt int backgroundColor, Paint foregroundPaint, Path waveformPath) {
        mBackgroundColor = backgroundColor;
        mForegroundPaint = foregroundPaint;
        mWaveformPath = waveformPath;
    }

    private static WaveformRenderer2 single=null;
    public static WaveformRenderer2 newInstance(@ColorInt int backgroundColor, @ColorInt int foregroundColour) {
        if (single == null) {
            Paint paint = new Paint();
            paint.setColor(foregroundColour);
            paint.setAntiAlias(true); // 抗锯齿
            paint.setStrokeWidth(8.0f); // 设置宽度
            paint.setStyle(Paint.Style.STROKE); // 填充

            Path waveformPath = new Path();

            return new WaveformRenderer2(backgroundColor, paint, waveformPath);
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

        //-------绘制曲线波形图-------
        // 如果point数组还未初始化
        if (mPoints == null || mPoints.length < waveform.length * 4) {
            mPoints = new float[waveform.length * 4];
        }

        for (int i = 0; i < waveform.length - 1; i++) {
            // 计算第i个点的x坐标
            mPoints[i * 4] = mRect.width() * i / (waveform.length - 1);
            // 根据bytes[i]的值（波形点的值）计算第i个点的y坐标
            mPoints[i * 4 + 1] = mRect.height() / 2
                    + ((byte) (waveform[i] + 128)) * (mRect.height() / 2) / 128;
            // 计算第i+1个点的x坐标
            mPoints[i * 4 + 2] = mRect.width() * (i + 1) / (waveform.length - 1);
            // 根据bytes[i+1]的值（波形点的值）计算第i+1个点的y坐标
            mPoints[i * 4 + 3] = mRect.height() / 2
                    + ((byte) (waveform[i + 1] + 128)) * (mRect.height() / 2) / 128;
        }
        // 绘制波形曲线
        canvas.drawLines(mPoints, mForePaint);
    }
}
