package com.example.videoview;

import java.io.IOException;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
	
	private VideoView intro_video;
	private AssetFileDescriptor fileDescriptor;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_intro_video);
		intro_video = (VideoView)this.findViewById(R.id.intro_video);
		initVideo("intro.mp4");
	}
	
	private void initVideo(String video) {
		AssetManager assetMg= this.getApplicationContext().getAssets();
		try{
			fileDescriptor = assetMg.openFd(video);
			intro_video.setOnPreparedListener(mOnPreparedListener);
			intro_video.setOnCompletionListener(mOnCompletionListener);
			intro_video.setOnErrorListener(mOnErrorListener);
			intro_video.setAssetPath(fileDescriptor);
		}catch(Exception e){
		}
	}
	
	private MediaPlayer.OnPreparedListener mOnPreparedListener = new MediaPlayer.OnPreparedListener() {
		@Override
		public void onPrepared(MediaPlayer mediaPlayer) {
			DisplayMetrics metric = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(metric);
			
			int mVideoWidth = intro_video.getVideoWidth();
			int mVideoHeight = intro_video.getVideoHeight();
			LinearLayout.LayoutParams llp = (LinearLayout.LayoutParams)intro_video.getLayoutParams();
			int width = metric.widthPixels;
	        int height = metric.heightPixels;
	        if (mVideoWidth > 0 && mVideoHeight > 0) {
	            if ( mVideoWidth * height  > width * mVideoHeight ) {
	                height = width * mVideoHeight / mVideoWidth;
	            } else if ( mVideoWidth * height  < width * mVideoHeight ) {
	                width = height * mVideoWidth / mVideoHeight;
	            } else {
	            }
	            if(width != mVideoWidth ||  height != mVideoHeight){
	            	llp.width = width;
	            	llp.height = height;
	            	intro_video.setLayoutParams(llp);
		        }
	        }
			intro_video.start();
		}
	};

	private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
		@Override
		public void onCompletion(MediaPlayer mediaPlayer) {
		}
	};
	
	private MediaPlayer.OnErrorListener mOnErrorListener = new MediaPlayer.OnErrorListener() {
		@Override
		public boolean onError(MediaPlayer mp, int what, int extra) {
			return true;
		}
	};
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(fileDescriptor != null){
			try {
				fileDescriptor.close();
			} catch (IOException e) {
			}
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
	
}
