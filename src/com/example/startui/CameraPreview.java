package com.example.startui;

import java.io.IOException;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class CameraPreview extends Activity implements OnClickListener,
		SurfaceHolder.Callback, Runnable {
	MediaRecorder recorder;
	private Thread thread;
	private long starttime;
	SurfaceHolder holder;
	boolean recording = false;
	private Camera mCamera;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		recorder = new MediaRecorder();
		initRecorder();
		setContentView(R.layout.activity_camera);

		// /////////////////////////////////////////////////////////////////////////////////
		//Context mContext = getApplicationContext();

		// オーバーレイするSurfaceView
		//SurfaceView OverLaySurfaceView = (SurfaceView) findViewById(R.id.surfaceView0);
		//OverLaySurfaceViewCallback OverLaySurfaceViewCallback = new OverLaySurfaceViewCallback(
		//		mContext);

		//SurfaceHolder overLayHolder = OverLaySurfaceView.getHolder();
		// ここで半透明にする
		//overLayHolder.setFormat(PixelFormat.TRANSLUCENT);
		//overLayHolder.addCallback(OverLaySurfaceViewCallback);

		// //////////////////////////////////////////////////////////////////////////////////

		SurfaceView cameraView = (SurfaceView) findViewById(R.id.surfaceView1);
		holder = cameraView.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		cameraView.setClickable(true);
		cameraView.setOnClickListener(this);

		// mPreview = mCamera;
		// LinearLayout preview = (LinearLayout)
		// findViewById(R.id.surfaceView1);
		// preview.addView(mPreview);
	}
	
	private void initRecorder() {

		// recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
		// recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		// recorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);

		recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
		recorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);

		CamcorderProfile cpHigh = CamcorderProfile
				.get(CamcorderProfile.QUALITY_HIGH);
		recorder.setProfile(cpHigh);
		recorder.setOutputFile("/sdcard/videocapture_example10.mp4");
		recorder.setMaxDuration(2000); // 50 seconds
		// recorder.setMaxFileSize(5000000); // Approximately 5 megabytes
	}

	private void prepareRecorder() {
		recorder.setPreviewDisplay(holder.getSurface());

		try {
			recorder.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
			finish();
		} catch (IOException e) {
			e.printStackTrace();
			finish();
		}
	}

	public void onClick(View v) {

		if (recording) {
			recorder.stop();
			recording = false;
			recorder.release();

			// Let's initRecorder so we can record again
			initRecorder();
			prepareRecorder();
		} else {
			recording = true;
			recorder.start();
			starttime = System.currentTimeMillis();
			thread = new Thread(this);
			thread.start();
		}
	}

	public void surfaceCreated(SurfaceHolder holder) {
		prepareRecorder();
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	public void run() {
		while (thread != null) {
			if (3000 <= (System.currentTimeMillis() - starttime)) {
				recorder.stop();
				recording = false;
				recorder.release();
				// break;
			}
		}
		// new MovieUploader(this).execute("/sdcard/1378617087449.mp4");
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		if (recording) {
			recorder.stop();
			recording = false;
		}
		recorder.release();
		finish();
	}
}