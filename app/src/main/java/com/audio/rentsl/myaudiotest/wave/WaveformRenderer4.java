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
public class WaveformRenderer4 implements WaveformRenderer {

    @ColorInt private final int mBackgroundColor;
    private final Paint mForegroundPaint;
    private final Path mWaveformPath;

    private Rect mRect = new Rect();
    private Paint mForePaint = new Paint();
    private float[] mPoints;

    private WaveformRenderer4(@ColorInt int backgroundColor, Paint foregroundPaint, Path waveformPath) {
        mBackgroundColor = backgroundColor;
        mForegroundPaint = foregroundPaint;
        mWaveformPath = waveformPath;
    }

    private static WaveformRenderer4 single=null;
    public static WaveformRenderer4 newInstance(@ColorInt int backgroundColor, @ColorInt int foregroundColour) {
        if (single == null) {
            Paint paint = new Paint();
            paint.setColor(foregroundColour);
            paint.setAntiAlias(true); // 抗锯齿
            paint.setStrokeWidth(8.0f); // 设置宽度
            paint.setStyle(Paint.Style.STROKE); // 填充

            Path waveformPath = new Path();

            return new WaveformRenderer4(backgroundColor, paint, waveformPath);
        }else{
            return single;
        }

    }

    @Override public void render(Canvas canvas, byte[] waveform) {
//        canvas.drawColor(mBackgroundColor);
        float width = canvas.getWidth();
        float height = canvas.getHeight();
        mForePaint.setStrokeWidth(1f);
        mForePaint.setAntiAlias(true);
        mForePaint.setColor(Color.rgb(0, 128, 255));
        mRect.set(0,0,(int)width,(int)height);

        // -------展示频谱图-------
        if (mPoints == null || mPoints.length < waveform.length * 4) {
            mPoints = new float[waveform.length * 4];
        }

        for (int i = 0; i < 9; i++) {
            if (waveform[i] < 0) {
                waveform[i] = 127;
            }
            mPoints[i * 4] = mRect.width() * i / 9;
            mPoints[i * 4 + 1] = mRect.height() / 2;
            mPoints[i * 4 + 2] = mRect.width() * i / 9;
            mPoints[i * 4 + 3] = 2 + mRect.height() / 2 + waveform[i];
        }
        canvas.drawLines(mPoints, mForePaint);
    }
}
