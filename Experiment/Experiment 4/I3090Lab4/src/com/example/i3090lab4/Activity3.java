package com.example.i3090lab4;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;


public class Activity3 extends Activity implements OnPreparedListener{
	
	VideoView videoView;
	MediaController mediaController;
	int REQUEST_CODE = 1;
	String TAG = "Activity3";
	Intent FileSelect;
	private TextView string;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
    	
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_activity3);
        
        videoView = (VideoView)findViewById(R.id.videoView);
        mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        videoView.setOnPreparedListener(this);
        
        FileSelect = new Intent(Intent.ACTION_GET_CONTENT);
        FileSelect.setType("video/*");
        
        this.startActivityForResult(FileSelect, REQUEST_CODE);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent FileSelect) {
    	super.onActivityResult(requestCode, resultCode, FileSelect);
    	if( resultCode != RESULT_OK ){
    		//Log.e(TAG, "讀檔失敗");
    		finish();
    		return;
    	}
    	if( requestCode != REQUEST_CODE ){
    		Log.d(TAG, "不是讀檔");
    		finish();
    		return;
    	}
    	
    	Uri uri = FileSelect.getData();
    	
    	Log.d(TAG, "取得檔案路徑" + uri.getPath());
    	Log.d(TAG, "傳送檔案路徑到VideoView");
    	
    	videoView.setVideoURI(uri);
    	Cursor c = getContentResolver().query(uri, null, null, null, null);
    	c.moveToFirst();
    	int i = c.getColumnIndex(OpenableColumns.DISPLAY_NAME);
    	String name = c.getString(i);
    	c.close();
    	string = (TextView) findViewById(R.id.textView1);
    	string.setText(name);
    }
    
	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
		Log.d(TAG, "已取得檔案路徑，開始播放");
		videoView.start();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		switch (keyCode){
			case KeyEvent.KEYCODE_VOLUME_DOWN:
			//do something
				break;
			case KeyEvent.KEYCODE_VOLUME_UP:
			//do something
				break;
			case KeyEvent.KEYCODE_BACK:
				videoView.suspend();
				FileSelect = new Intent(Intent.ACTION_GET_CONTENT);
				FileSelect.setType("video/*");
				this.startActivityForResult(FileSelect, REQUEST_CODE);
				break;
			case KeyEvent.KEYCODE_MEDIA_STOP:
			//do something
				break;
			case KeyEvent.KEYCODE_MENU:
			//do something
				break;
			case KeyEvent.KEYCODE_HOME:
			//invalid...
				break;
		}
		return super.onKeyDown(keyCode, event);
	}

}