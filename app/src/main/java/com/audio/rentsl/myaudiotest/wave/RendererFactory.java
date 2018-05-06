package com.audio.rentsl.myaudiotest.wave;

import android.support.annotation.ColorInt;

/**
 * 工厂模式
 * <p>
 * Created by wangchenlong on 16/2/12.
 */
public class RendererFactory {
    public WaveformRenderer createWaveformRender1(@ColorInt int foreground, @ColorInt int background) {
        return WaveformRenderer1.newInstance(background, foreground);
    }
    public WaveformRenderer createWaveformRender2(@ColorInt int foreground, @ColorInt int background) {
        return WaveformRenderer2.newInstance(background, foreground);
    }
    public WaveformRenderer createWaveformRender3(@ColorInt int foreground, @ColorInt int background) {
        return WaveformRenderer3.newInstance(background, foreground);
    }
    public WaveformRenderer createWaveformRender4(@ColorInt int foreground, @ColorInt int background) {
        return WaveformRenderer4.newInstance(background, foreground);
    }
}
