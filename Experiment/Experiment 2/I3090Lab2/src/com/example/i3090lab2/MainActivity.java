package com.example.i3090lab2;

import java.io.IOException;
import java.util.Random;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {

	Button playRawBtn, playSDBtn, playURLBtn, stopBtn, volUpBtn, volDownBtn, pauseBtn, nextBtn, preBtn, RamBtn;
	MediaPlayer player;
	ProgressBar progressBar;
	TextView tv;
	boolean isPaused = false;  
	int progressState = 0, length, status = 0;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final AudioManager audioManager=(AudioManager)this.getSystemService(AUDIO_SERVICE);
        playRawBtn = (Button)findViewById(R.id.button1);
        playSDBtn = (Button)findViewById(R.id.button2);
        playURLBtn = (Button)findViewById(R.id.button3);
        stopBtn = (Button)findViewById(R.id.button6);
        pauseBtn = (Button)findViewById(R.id.button7);
        nextBtn = (Button)findViewById(R.id.button8);
        preBtn = (Button)findViewById(R.id.button9);
        RamBtn = (Button)findViewById(R.id.button10);
        volUpBtn = (Button)findViewById(R.id.button5);
        volDownBtn = (Button)findViewById(R.id.button4);
        progressBar = (ProgressBar)findViewById(R.id.progressBar1);
        
        player = new MediaPlayer();
        
        playSDBtn.setEnabled(true);
        playRawBtn.setEnabled(true);
        stopBtn.setEnabled(true);
        tv = (TextView)findViewById(R.id.textView2);
        
        playRawBtn.setOnClickListener(new View.OnClickListener(){
        	public void onClick(View v){
        		status = 1;
        		player.stop();
    			playSDBtn.setEnabled(true);
    			playURLBtn.setEnabled(true);
        		player = MediaPlayer.create(MainActivity.this,R.raw.sample);
        		isPaused = false;
        		tv.setText("音樂播放中"); 
        		try{
        			progressBar.setProgress(0);	
        			length = player.getDuration();
        			progressBar.setMax(length / 1000);
        			player.start();
        			new Thread(new ProcessBarRefresh()).start();
        			playRawBtn.setEnabled(false);
        			stopBtn.setEnabled(true);
        		} catch (IllegalStateException e){
        			e.printStackTrace();
        		} 
        	}
        });
        
        playSDBtn.setOnClickListener(new View.OnClickListener(){
        	public void onClick(View v){
        		try{
        			status = 2;
        			player.stop();
        			playRawBtn.setEnabled(true);
        			playURLBtn.setEnabled(true);
        			isPaused = false;
        			tv.setText("音樂播放中");
        			progressBar.setProgress(0);	
        			player = new MediaPlayer();
        			player.setDataSource("/sdcard/ss.mp3");
        			player.prepare();
        			length = player.getDuration();
        			progressBar.setMax(length / 1000);
        			player.start();
        			new Thread(new ProcessBarRefresh()).start();
        			playSDBtn.setEnabled(false);
        			stopBtn.setEnabled(true);
        		} catch (IllegalStateException e){
        			e.printStackTrace();
        		} catch (IllegalArgumentException e){
    				e.printStackTrace();
    			} catch (SecurityException e){
    				e.printStackTrace();
    			} catch (IOException e){
    				e.printStackTrace();
    			}
        	}
        });
        
        playURLBtn.setOnClickListener(new View.OnClickListener(){
        	public void onClick(View v){
        		try{
        			status = 3;
        			player.stop();
        			playRawBtn.setEnabled(true);
        			playSDBtn.setEnabled(true);
        			isPaused = false;
        			tv.setText("音樂播放中");
        			progressBar.setProgress(0);	
        			player = new MediaPlayer();
        			player.setDataSource("http://www.vorbis.com/music/Epoq-Lepidoptera.ogg");
        			player.prepare();
        			length = player.getDuration();
        			progressBar.setMax(length / 1000);
        			player.start();
        			new Thread(new ProcessBarRefresh()).start();
        			playURLBtn.setEnabled(false);
        			stopBtn.setEnabled(true);
        		} catch (IllegalStateException e){
        			e.printStackTrace();
        		} catch (IllegalArgumentException e){
    				e.printStackTrace();
    			} catch (SecurityException e){
    				e.printStackTrace();
    			} catch (IOException e){
    				e.printStackTrace();
    			}
        	}
        });
        
        stopBtn.setOnClickListener(new View.OnClickListener(){
        	public void onClick(View v){
        		try{
        			tv.setText("停止播放");
        			player.stop();
        			playRawBtn.setEnabled(true);
        			playSDBtn.setEnabled(true);
        			playURLBtn.setEnabled(true);
        			stopBtn.setEnabled(false);
        		} catch(IllegalStateException e){
        			e.printStackTrace();
        		}
        	}
        });
        
        pauseBtn.setOnClickListener(new View.OnClickListener(){
        	public void onClick(View v){
        		try{
        			if(isPaused == false){
        				tv.setText("暫停播放");
        				player.pause();
        				isPaused = true;
        			}
        			else{
        				tv.setText("開始播放");
        				player.start();
        				isPaused = false;
        			}
        		} catch(IllegalStateException e){
        			e.printStackTrace();
        		}
        	}
        });
        
        preBtn.setOnClickListener(new View.OnClickListener(){
        	public void onClick(View v){
        		try{
        			if(status == 1){
        				status = 3;
        				playURLBtn.callOnClick();
        			}
        			else if(status == 2){
        				status = 1;
        				playRawBtn.callOnClick();
        			}
        			else if(status == 3){
        				status = 2;
        				playSDBtn.callOnClick();
        			}
        		} catch(IllegalStateException e){
        			e.printStackTrace();
        		}
        	}
        });
        
        nextBtn.setOnClickListener(new View.OnClickListener(){
        	public void onClick(View v){
        		try{
        			if(status == 1){
        				status = 2;
        				playSDBtn.callOnClick();
        			}
        			else if(status == 2){
        				status = 3;
        				playURLBtn.callOnClick();
        			}
        			else if(status == 3){
        				status = 1;
        				playRawBtn.callOnClick();
        			}
        		} catch(IllegalStateException e){
        			e.printStackTrace();
        		}
        	}
        });
        
        RamBtn.setOnClickListener(new View.OnClickListener(){
        	public void onClick(View v){
        		try{
        			Random ran = new Random();
        			int r = ran.nextInt(3)+1;
        			
        			if(r == 1){
        				playSDBtn.callOnClick();
        			}
        			else if(r == 2){
        				playURLBtn.callOnClick();
        			}
        			else if(r == 3){
        				playRawBtn.callOnClick();
        			}
        		} catch(IllegalStateException e){
        			e.printStackTrace();
        		}
        	}
        });
        
        volUpBtn.setOnClickListener(new View.OnClickListener(){
        	public void onClick(View v){
        		audioManager.adjustVolume(AudioManager.ADJUST_RAISE, 5);
        	}
        });
        
        volDownBtn.setOnClickListener(new View.OnClickListener(){
        	public void onClick(View v){
        		audioManager.adjustVolume(AudioManager.ADJUST_LOWER, 5);
        	}
        });
    }

    class ProcessBarRefresh implements Runnable {

	    @Override
	    public void run() {
	    	// TODO Auto-generated method stub
	    	while(player.isPlaying()){
	    		progressState = player.getCurrentPosition() / 1000;
	    		progressBar.setProgress(progressState);
	    	}
	    }
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
}
