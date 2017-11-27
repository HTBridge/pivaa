package com.htbridge.pivaa.handlers;


import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/*
    MyVulnerableService is an on-purpose vulnerable Android service.

    This service is recording audio, it stops when the audio file size reach 1MB.
    The audio file is saved at the root of the external storage.

    This service is exported in the AndroidManifest.xml, allowing any application to start this service.

    So any application without the recording audio permission can abuse of this service in order to record audio.
*/
public class VulnerableService extends Service implements MediaRecorder.OnInfoListener {
    //setting maximum file size to be recorded
    private long Audio_MAX_FILE_SIZE = 1000000; // 1 MB
    private MediaRecorder mRecorder;
    private int[] amplitudes = new int[100];
    private int i = 0;
    private File mOutputFile;
    private long mStartTime = 0;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return Service.START_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        startRecording();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startRecording() {
        Toast.makeText(this, "START RECORDING AUDIO", Toast.LENGTH_SHORT).show();

        try {
            mRecorder = new MediaRecorder();
            mRecorder.setOnInfoListener(this);
            try {
                mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    mRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
            mRecorder.setMaxFileSize(Audio_MAX_FILE_SIZE);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

            // For compatibility purpose
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.HE_AAC);
                mRecorder.setAudioEncodingBitRate(48000);
            } else {
                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                mRecorder.setAudioEncodingBitRate(64000);
            }

            mRecorder.setAudioSamplingRate(16000);
            mOutputFile = getOutputFile();
            mOutputFile.getParentFile().mkdirs();
            mRecorder.setOutputFile(mOutputFile.getAbsolutePath());

            try {
                mRecorder.prepare();
                mRecorder.start();
                mStartTime = SystemClock.elapsedRealtime();
            } catch (IOException e) {
                Log.d("AudioService", e.getMessage());
            }
        } catch (Exception e) {
            Toast.makeText(this, "ERROR STARTING AUDIO RECORDING. REASON: YOU'RE USING EMULATOR", Toast.LENGTH_SHORT).show();
        }
    }

    protected void stopRecording(boolean saveFile) {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        mStartTime = 0;
        if (!saveFile && mOutputFile != null) {
            mOutputFile.delete();
        }

        Toast.makeText(this, "STOP RECORDING AUDIO", Toast.LENGTH_SHORT).show();

        // to stop the service by itself
        stopSelf();
    }

    private File getOutputFile() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.US);

        String full_path = Environment.getExternalStorageDirectory()
                .getAbsolutePath().toString()
                + "/RECORDING_"
                + dateFormat.format(new Date())
                + ".m4a";

        Toast.makeText(this, "Audio file: " + full_path, Toast.LENGTH_SHORT);

        return new File(full_path);
    }

    @Override
    public void onInfo(MediaRecorder mr, int what, int extra) {
        //check whether the file size has reached to 1 MB to stop recording
        if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED) {
            stopRecording(true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRecording(true);
    }
}