package com.audio.rentsl.myaudiotest;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.audiofx.Visualizer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.audio.rentsl.myaudiotest.wave.RendererFactory;
import com.audio.rentsl.myaudiotest.wave.WaveformView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final int CAPTURE_SIZE = 256; // 获取这些数据, 用于显示
    Activity activity = null;

    // 权限
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.MODIFY_AUDIO_SETTINGS
    };
    RendererFactory rendererFactory;

    @Bind(R.id.main_wv_waveform) WaveformView mWvWaveform; // 波纹视图

    private Visualizer mVisualizer; // 音频可视化类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        activity = this;
        rendererFactory = new RendererFactory();
        mWvWaveform.setRenderer(rendererFactory.createWaveformRender1(ContextCompat.getColor(this, R.color.colorPrimary), Color.WHITE));
        mWvWaveform.setTypeNumber((byte) 4);


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            // 没有权限。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {
                // 用户拒绝过这个权限了，应该提示用户，为什么需要这个权限。
            } else {
                // 申请授权。
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO,Manifest.permission.MODIFY_AUDIO_SETTINGS}, 1234);
            }
        }else{
            startVisualiser();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1234: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意
                    startVisualiser();
                } else {
                    // 权限被用户拒绝了
                    finish();
                }
                return;
            }
        }
    }

    // 设置音频线
    private void startVisualiser() {
        mVisualizer = new Visualizer(0); // 初始化
        mVisualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
            byte defType = mWvWaveform.getType();
            @Override
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {
                byte type = mWvWaveform.getType();
                if (type == defType){

                }else {
                    switch (type){
                        case 0:
                            mWvWaveform.setRenderer(rendererFactory.createWaveformRender1(ContextCompat.getColor(activity, R.color.colorPrimary), Color.WHITE));
                            break;
                        case 1:
                            mWvWaveform.setRenderer(rendererFactory.createWaveformRender2(ContextCompat.getColor(activity, R.color.colorPrimary), Color.WHITE));
                            break;
                        case 2:
                            mWvWaveform.setRenderer(rendererFactory.createWaveformRender3(ContextCompat.getColor(activity, R.color.colorPrimary), Color.WHITE));
                            break;
                        case 3:
                            mWvWaveform.setRenderer(rendererFactory.createWaveformRender4(ContextCompat.getColor(activity, R.color.colorPrimary), Color.WHITE));
                            break;
                        default:
                            break;
                    }
                    defType = type;
                }

                if (mWvWaveform != null) {
                    mWvWaveform.setWaveform(waveform);
                }
            }

            @Override
            public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {

            }
        }, Visualizer.getMaxCaptureRate(), true, false);
        mVisualizer.setCaptureSize(CAPTURE_SIZE);
        mVisualizer.setEnabled(true);
    }

    @Override
    protected void onPause() {
        if (mVisualizer != null) {
            mVisualizer.setEnabled(false);
            mVisualizer.release();
        }
        super.onPause();
    }
}
