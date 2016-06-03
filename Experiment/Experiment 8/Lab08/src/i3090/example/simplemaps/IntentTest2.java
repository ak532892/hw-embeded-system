package i3090.example.simplemaps;

import android.os.Bundle;
import i3090.example.simplemaps.R;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class IntentTest2 extends ListActivity {
	private EditText edit;
	private Button recordbtn;
	private Button deletetbtn;
	private Button startbtn;
	private int row;
	private static int num = 0;
	TextView showfrom;
	TextView showto;
	String from;
	String to;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle bundle = this.getIntent().getExtras();
    	final Intent intent = this.getIntent();
        setContentView(R.layout.main2);
        //Tell the list view which view to display when the list is empty
        getListView().setEmptyView(findViewById(R.id.TextView01));
        setAdapter();
        showfrom=(TextView) findViewById(R.id.TextViewFrom);
        showto=(TextView) findViewById(R.id.TextViewTo);
        from=bundle.getString("KEY_TEXT1");
        to=bundle.getString("KEY_TEXT2");
        showfrom.setText(from);
        showto.setText(to);
        
        recordbtn=(Button) findViewById(R.id.Button01);
        deletetbtn=(Button) findViewById(R.id.Button02);
        startbtn = (Button) findViewById(R.id.Button03);
        edit = (EditText) findViewById(R.id.EditText01);
        
        recordbtn.setOnClickListener( new OnClickListener() {
			public void onClick(View v) {
				String noteName = " 從  " + from + "  到  "+ to;
				mDbHelper.create(++num + noteName);
                fillData();
                v.setBackgroundColor(Color.rgb(74, 172, 255));
			}
		});
        
        deletetbtn.setOnClickListener( new OnClickListener() {
			public void onClick(View v) {
				String Text = edit.getText().toString();
				
				if(checkValid(Text) == 1)
					updateID(Integer.valueOf(Text));
				row = 0;
				v.setBackgroundColor(Color.rgb(239, 2477, 255));
			}
		});
        
        startbtn.setOnClickListener( new OnClickListener() {
			public void onClick(View v) {
	    		bundle.clear();
	    		bundle.putString("KEY_TEXT1", from);
	    		bundle.putString("KEY_TEXT2", to);
		    	intent.putExtras(bundle);
		    	IntentTest2.this.setResult(MainActivity.RESULT_OK, intent);
		    	IntentTest2.this.finish();
			}
		});
    }
    
    private int checkValid(String Text){
    	int valid = 1;
		
		for(int i = 0; i < Text.length(); i++){
			if(!Character.isDigit(Text.charAt(i))){
				valid = 0;
				break;
			}
		}
		if(Text.equals("") || Text.charAt(0)=='0')
			valid = 0;
		edit.setText("");
		return valid;
    }
    private void updateID(int R){
		mDbHelper.delete(R);
		int t = mDbHelper.getCount();
		
		for(int i = R; i <= t; i++){
			mDbHelper.updateRow(i+1,i);
			String s = mDbHelper.get(i).getString(1);
			int j;
			for(j = 0; j < s.length(); j++){
				if(!Character.isDigit(s.charAt(j)))
					break;
			}
			String neww = i+"";
			neww += s.substring(j, s.length());
			mDbHelper.update(i, neww);
		}
        fillData();
        num = t;
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        row = (int)id;
        edit.setText(row+"");
        
        String s = mDbHelper.get(row).getString(1);
        
        String[] cmds = s.split(" ");
        int i = 1;
        for(String d:cmds){
        	if(i == 4)
        		from = d;
        	if(i == 8)
        		to = d;
            i++;
        }
        v.setBackgroundColor(Color.rgb(197, 230, 255));
    }

    private DB mDbHelper;
    private Cursor mNotesCursor;
    
    private void setAdapter() {
    	mDbHelper = new DB(this);
        mDbHelper.open();
        fillData();
    }
    
    private void fillData() {
        mNotesCursor = mDbHelper.getAll();
        startManagingCursor(mNotesCursor);

        String[] from = new String[]{DB.KEY_NOTE};
        int[] to = new int[]{android.R.id.text1};

        // Now create a simple cursor adapter
        SimpleCursorAdapter adapter =
                    new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, mNotesCursor, from, to);
        setListAdapter(adapter);
    }
    
    private int mNoteNumber = 1;
    protected static final int MENU_INSERT = Menu.FIRST;
    protected static final int MENU_DELETE = Menu.FIRST+1;
	protected static final int MENU_CLEAR = Menu.FIRST+2;
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        menu.add(0, MENU_INSERT, 0, "新增");
        menu.add(0, MENU_DELETE, 0, "刪除");
		menu.add(0, MENU_CLEAR, 0, "清空");
        return super.onCreateOptionsMenu(menu);
    }
 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch(item.getItemId()) {
			case MENU_INSERT:
				String noteName = " Note " + mNoteNumber++;
				mDbHelper.create(noteName);
				fillData();
				break;
			case MENU_DELETE:
				if(checkValid(row+"") == 1)
					updateID(row);
				row = 0;
				fillData();
				break;
			case MENU_CLEAR:
				num=0;
				mDbHelper.clear(); 
				fillData();
				break;
        }

        return super.onOptionsItemSelected(item);
    }
}