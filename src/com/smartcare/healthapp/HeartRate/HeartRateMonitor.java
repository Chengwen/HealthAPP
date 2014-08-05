package com.smartcare.healthapp.heartrate;

import java.util.concurrent.atomic.AtomicBoolean;

import com.google.analytics.tracking.android.EasyTracker;
import com.smartcare.database.DataHandler;
import com.smartcare.healthapp.ChartActivity;
import com.smartcare.healthapp.FirstActivity;
import com.smartcare.healthapp.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

/**
 * This class extends Activity to handle a picture preview, process the preview
 * for a red values and determine a heart beat.
 */
public class HeartRateMonitor extends Activity {

	private static final String TAG = "HeartRateMonitor";
	private static final AtomicBoolean processing = new AtomicBoolean(false);

	private static SurfaceView preview = null;
	private static SurfaceHolder previewHolder = null;
	private static Camera camera = null;
	private static View image = null;
	private static TextView text = null;
	private static int first = 0;

	private static WakeLock wakeLock = null;

	private static int averageIndex = 0;
	private static final int averageArraySize = 4;
	private static final int[] averageArray = new int[averageArraySize];

	public static SoundPool soundPool;

	public static enum TYPE {
		GREEN, RED
	};

	private static TYPE currentType = TYPE.GREEN;

	public static TYPE getCurrent() {
		return currentType;
	}

	private static int beatsIndex = 0;
	private static final int beatsArraySize = 3;
	private static final int[] beatsArray = new int[beatsArraySize];
	private static double beats = 0;
	private static long startTime = 0;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.heartrate);

		soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
		soundPool.load(this, R.raw.beat, 1);
		preview = (SurfaceView) findViewById(R.id.preview);
		previewHolder = preview.getHolder();
		previewHolder.addCallback(surfaceCallback);
		previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		image = findViewById(R.id.image);
		text = (TextView) findViewById(R.id.text);

		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakeLock = pm
				.newWakeLock(PowerManager.FULL_WAKE_LOCK, "DoNotDimScreen");

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onResume() {
		super.onResume();

		wakeLock.acquire();

		camera = Camera.open();

		startTime = System.currentTimeMillis();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onPause() {
		super.onPause();

		wakeLock.release();

		camera.setPreviewCallback(null);
		camera.stopPreview();
		camera.release();
		camera = null;
	}

	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this); // Add this method.
	}

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this); // Add this method.
	}

	private static PreviewCallback previewCallback = new PreviewCallback() {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void onPreviewFrame(byte[] data, Camera cam) {
			if (data == null)
				throw new NullPointerException();
			Camera.Size size = cam.getParameters().getPreviewSize();
			if (size == null)
				throw new NullPointerException();

			if (!processing.compareAndSet(false, true))
				return;

			int width = size.width;
			int height = size.height;

			int imgAvg = ImageProcessing.decodeYUV420SPtoRedAvg(data.clone(),
					height, width);
			// Log.i(TAG, "imgAvg="+imgAvg);
			if (imgAvg == 0 || imgAvg == 255) {
				processing.set(false);
				return;
			}

			int averageArrayAvg = 0;
			int averageArrayCnt = 0;
			for (int i = 0; i < averageArray.length; i++) {
				if (averageArray[i] > 0) {
					averageArrayAvg += averageArray[i];
					averageArrayCnt++;
				}
			}

			int rollingAverage = (averageArrayCnt > 0) ? (averageArrayAvg / averageArrayCnt)
					: 0;
			TYPE newType = currentType;
			if (imgAvg < rollingAverage) {
				newType = TYPE.RED;
				if (newType != currentType) {
					beats++;
					// Log.d(TAG, "BEAT!! beats="+beats);
				}
			} else if (imgAvg > rollingAverage) {
				newType = TYPE.GREEN;
			}

			if (averageIndex == averageArraySize)
				averageIndex = 0;
			averageArray[averageIndex] = imgAvg;
			averageIndex++;

			// Transitioned from one state to another to the same
			if (newType != currentType) {
				if (newType == TYPE.RED) {
					soundPool.play(1, 1, 1, 0, 0, 1);
				}
				currentType = newType;
				image.postInvalidate();
			}

			long endTime = System.currentTimeMillis();
			double totalTimeInSecs = (endTime - startTime) / 1000d;
			if (totalTimeInSecs >= 10) {
				double bps = (beats / totalTimeInSecs);
				int dpm = (int) (bps * 60d);
				if (dpm < 30 || dpm > 180) {
					startTime = System.currentTimeMillis();
					beats = 0;
					processing.set(false);
					return;
				}

				// Log.d(TAG,
				// "totalTimeInSecs="+totalTimeInSecs+" beats="+beats);

				if (beatsIndex == beatsArraySize)
					beatsIndex = 0;
				beatsArray[beatsIndex] = dpm;
				beatsIndex++;

				int beatsArrayAvg = 0;
				int beatsArrayCnt = 0;
				for (int i = 0; i < beatsArray.length; i++) {
					if (beatsArray[i] > 0) {
						beatsArrayAvg += beatsArray[i];
						beatsArrayCnt++;
					}
				}
				int beatsAvg = (beatsArrayAvg / beatsArrayCnt);
				if (first == 0) {
					first = beatsAvg;
				} else {
					first = 0;
					DataHandler d = new DataHandler(
							FirstActivity.fa.getApplicationContext());
					d.open();
					d.insertData(beatsAvg, 1, "");
					Log.d("Insert BPM", String.valueOf(beatsAvg));
					d.close();

					Intent intent = new Intent();
					intent.setClass(FirstActivity.fa, ChartActivity.class);
					FirstActivity.fa.startActivity(intent);
					FirstActivity.fa.overridePendingTransition(R.anim.fade_in,
							R.anim.fade_out);

				}
				text.setText(String.valueOf(beatsAvg) + " BPM");
				startTime = System.currentTimeMillis();
				beats = 0;
			}
			processing.set(false);
		}
	};

	private static SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			try {
				camera.setPreviewDisplay(previewHolder);
				camera.setPreviewCallback(previewCallback);
			} catch (Throwable t) {
				Log.e("PreviewDemo-surfaceCallback",
						"Exception in setPreviewDisplay()", t);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			Camera.Parameters parameters = camera.getParameters();
			parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
			Camera.Size size = getSmallestPreviewSize(300, 500, parameters);
			if (size != null) {
				parameters.setPreviewSize(size.width, size.height);
				Log.d(TAG, "Using width=" + size.width + " height="
						+ size.height);
			}
			camera.setParameters(parameters);
			camera.startPreview();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// Ignore
		}
	};

	private static Camera.Size getSmallestPreviewSize(int width, int height,
			Camera.Parameters parameters) {
		Camera.Size result = null;

		for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
			if (size.width <= width && size.height <= height) {
				if (result == null) {
					result = size;
				} else {
					int resultArea = result.width * result.height;
					int newArea = size.width * size.height;

					if (newArea < resultArea)
						result = size;
				}
			}
		}

		return result;
	}
}
