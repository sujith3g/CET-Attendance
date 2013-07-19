package com.hedcet.attendance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	EditText usr_name;
	Intent i;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        Intent dataFromGetData = getIntent();
        
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        i = new Intent(this,GetData.class);
        Button submit = (Button)findViewById(R.id.button1);
        final EditText usr_name = (EditText)findViewById(R.id.editText1);
        final EditText pwd = (EditText)findViewById(R.id.editText2);
        TextView tv1 = (TextView)findViewById(R.id.textView1);
// Check the file for user details        
        File file = getBaseContext().getFileStreamPath("test.txt");
        String readString="";
        //Toast.makeText(AttndcActivity.this,"In start", Toast.LENGTH_LONG).show();
        
        if (file.exists()) {

            FileInputStream fis;
            
            try {
            	fis = openFileInput("test.txt");
            	StringBuffer fileContent = new StringBuffer("");
            	String str="";
            	
            	byte[] buffer = new byte[1024];
            	int length,i=1;
            	while ((length = fis.read(buffer)) != -1) {
                fileContent.append(new String(buffer));
                	if(i==1){
                		str = new String(buffer);
                		i++;
                	}
            	}
                if(fileContent.length()>0){
                	
                	String edit = dataFromGetData.getStringExtra("edit");
                	//String[] usrdata = str.split("|");
                	//Toast.makeText(AttndcActivity.this,str.trim().length()+"=="+str.trim(),Toast.LENGTH_LONG).show();
            		if(edit.equals("Settings")){
            			String usr = str.trim().substring(0, 6);
            			String pswd = str.trim().substring(7);
            			usr_name.setText(usr);
            			pwd.setText(pswd);
            			//Toast.makeText(AttndcActivity.this,str.trim(),Toast.LENGTH_LONG).show();
            		}
                	
                }
            	} catch (Exception e) {
            		Toast.makeText(MainActivity.this,"In exception", Toast.LENGTH_LONG).show();
            		e.printStackTrace();

            	}	 
        	}else{
        		//Toast.makeText(AttndcActivity.this,"In else", Toast.LENGTH_LONG).show();
        	}
        
        	submit.setOnClickListener(new View.OnClickListener() {			
        	
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//String str = new String("hello");
				//tv1.setText("hello",0,str.length());
				//Toast.makeText(v.getContext(),"user:"+usr_name.getText()+"pwd:"+pwd.getText().toString(), 500).show();
				//i.putExtra("data", usr_name.getText()+"|"+pwd.getText());
				if(usr_name.getText().length()==6 && pwd.getText().length()>0){
					try{
						String FILENAME = "test.txt";
						String string = usr_name.getText()+"|"+pwd.getText();

						FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
						fos.write(string.getBytes());
						fos.close();
						Intent getdata = new Intent("com.hedcet.attendance.GetData");
						getdata.putExtra("data", usr_name.getText()+"|"+pwd.getText());
						getdata.putExtra("QuickCheck", "False");
						startActivity(getdata);
					}catch(Exception e){
						e.printStackTrace();
						
					}
				}else{
					Toast.makeText(v.getContext(),"Enter a valid Username and Password..", 500).show();
				}
			}
		});
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		 super.onCreateOptionsMenu(menu);
		 //getMenuInflater().inflate(R, menu)
		 MenuInflater mInflator = getMenuInflater();
		 mInflator.inflate(R.menu.about, menu);
		 return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.about:
			Intent i = new Intent("com.hedcet.attendance.AboutUs");
			startActivity(i);
			break;
		}
		return false;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//finish();
	}
    
}