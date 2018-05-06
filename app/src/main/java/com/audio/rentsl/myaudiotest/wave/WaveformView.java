package com.audio.rentsl.myaudiotest.wave;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Arrays;

/**
 * 音频波纹视图
 * <p>
 * Created by wangchenlong on 16/2/11.
 */
public class WaveformView extends View {

    private WaveformRenderer mRenderer; // 绘制类
    private byte[] mWaveform; // 波纹形状
    private byte type = 0; //波纹类型
    private byte typeNumber = 1; //波纹种类

    public WaveformView(Context context) {
        super(context);
    }

    public WaveformView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WaveformView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public WaveformView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 设置波形种类数量
     * @param number
     */
    public void setTypeNumber(byte number){
        typeNumber = number;
    }

    /**
     * 得到波形类型
     * @return
     */
    public byte getType(){
        return type;
    }

    public void setRenderer(WaveformRenderer renderer) {
        mRenderer = renderer;
    }

    public void setWaveform(byte[] waveform) {
        mWaveform = Arrays.copyOf(waveform, waveform.length); // 数组复制
        invalidate(); // 设置波纹之后, 需要重绘
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 当用户触碰该组件时，切换波形类型
        if(event.getAction() != MotionEvent.ACTION_DOWN)
        {
            return false;
        }
        type ++;
        if(type >= typeNumber)
        {
            type = 0;
        }
        return true;
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mRenderer != null) {
            mRenderer.render(canvas, mWaveform);
        }
    }
}
