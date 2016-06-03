package com.example.i3090lab4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private Button Switch1;
	private Button Switch2;
	private Button Finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Switch1 = (Button) findViewById(R.id.button1);
        Switch2 = (Button) findViewById(R.id.button2);
        Finish = (Button) findViewById(R.id.button3);
        
        Switch1.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent = new Intent();
        		intent.setClass(MainActivity.this, Activity2.class);
        		Bundle bundle = new Bundle();
        		intent.putExtras(bundle);
        		startActivity(intent);
        	}
        });
		
        Switch2.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent = new Intent();
        		intent.setClass(MainActivity.this, Activity3.class);
        		Bundle bundle = new Bundle();
        		intent.putExtras(bundle);
        		startActivity(intent);
        	}
        });
        
		Finish.setOnClickListener( new OnClickListener(){
			public void onClick(View v){
				finish();
			}
		});
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
