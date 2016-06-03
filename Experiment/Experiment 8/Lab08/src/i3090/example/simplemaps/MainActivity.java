package i3090.example.simplemaps;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {  
	private EditText edit;
	private EditText edit2;
	private Button recordbtn;
	private Button startbtn;
	String from="";
	String to="";
	private static final String MAP_URL = "file:///android_asset/map_v3.html";
	private WebView webView;
	Intent intent = new Intent();
	Bundle bundle = new Bundle();
	private Spinner spinner;
	private String[] list = {"開車", "步行", "單車", "大眾運輸"};
	private ArrayAdapter<String> listAdapter;
	private String mode="DRIVING";
	
	@Override
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_v3);
		setupWebView();

		edit = (EditText) findViewById(R.id.EditText01);
		edit2 = (EditText) findViewById(R.id.EditText02);
		recordbtn = (Button) findViewById(R.id.Button01);
		startbtn = (Button) findViewById(R.id.Button02);
		spinner = (Spinner)findViewById(R.id.mySpinner);
		
		listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		spinner.setAdapter(listAdapter);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {
			   if(position == 0)	mode = "DRIVING";
			   if(position == 1)	mode = "WALKING";
			   if(position == 2)	mode = "BICYCLING";
			   if(position == 3)	mode = "TRANSIT";
			   webView.loadUrl( "javascript:route()");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			   // TODO Auto-generated method stub
			}
		});
		
		startbtn.setOnClickListener( new OnClickListener() {
			public void onClick(View v) {
				from=edit.getText().toString();
				to=edit2.getText().toString();
				webView.loadUrl( "javascript:route()");
			}
		});
		recordbtn.setOnClickListener( new OnClickListener() {
			public void onClick(View v) {
	    		intent.setClass(MainActivity.this, IntentTest2.class);
	    		
	    		bundle.putString("KEY_TEXT1", edit.getText().toString());
	    		bundle.putString("KEY_TEXT2", edit2.getText().toString());
		    	intent.putExtras(bundle);
		    	startActivityForResult(intent, 0);	
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
 
		if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
			Log.d("123", "suceess");
			bundle = data.getExtras();
			from=bundle.getString("KEY_TEXT1");
	        to=bundle.getString("KEY_TEXT2");
			edit.setText(from);
			edit2.setText(to);
			webView.loadUrl( "javascript:route()");
		}
	}
	
	@JavascriptInterface
	public String getMode(){
		return mode;
	}
	
	@JavascriptInterface
	public String getFrom(){
		return from;
	}
	@JavascriptInterface
	public String getTo(){
		return to;
	}
	
	@SuppressLint("JavascriptInterface")
	private void setupWebView(){
		webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);//�ҥ�Webview��JavaScript�\��
		webView.addJavascriptInterface(this ,"Android");
		webView.loadUrl(MAP_URL);
	}
}