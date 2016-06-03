package com.example.i3090lab1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {
	
	private Button Switch;
	private EditText edit;
	private Button Finish;
	//private TextView string;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Switch = (Button) findViewById(R.id.button1);
        edit = (EditText) findViewById(R.id.editText1);
     
        Finish = (Button) findViewById(R.id.button2);

        Switch.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent = new Intent();
        		intent.setClass(MainActivity.this,Activity2.class);
        		Bundle bundle = new Bundle();
        		bundle.putString("KEY_TEXT", edit.getText().toString());
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
