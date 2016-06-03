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
import android.widget.TextView;

public class Activity2 extends Activity {
	
	private Button Switch;
	private EditText edit;
	private TextView string;
	private Button Finish;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_activity2);
        
        Switch = (Button) findViewById(R.id.button1);
        Finish = (Button) findViewById(R.id.button2);
        edit = (EditText) findViewById(R.id.editText1);
        string = (TextView) findViewById(R.id.textView1);
        Bundle bundle;
		bundle = this.getIntent().getExtras();
		string.setText(bundle.getString("KEY_TEXT"));
		
		
		string = (TextView) findViewById(R.id.textView1);
		
		bundle = this.getIntent().getExtras();
		string.setText(bundle.getString("KEY_TEXT"));
		
		Finish.setOnClickListener( new OnClickListener(){
			public void onClick(View v){
				finish();
			}
		});
		
		Switch.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent = new Intent();
        		intent.setClass(Activity2.this,Activity3.class);
        		Bundle bundle = new Bundle();
        		bundle.putString("KEY_TEXT", edit.getText().toString());
        		intent.putExtras(bundle);
        		startActivity(intent);
        	}
        	
        });
    }



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity2, menu);
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
