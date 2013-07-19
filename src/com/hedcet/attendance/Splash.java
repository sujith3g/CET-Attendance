package com.hedcet.attendance;

import java.io.File;
import java.io.FileInputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Toast;


public class Splash extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash);
		//setContentView(R.layout.splash);
		TelephonyManager telephonyManager = 
				(TelephonyManager)Splash.this.getSystemService(Context.TELEPHONY_SERVICE);

				String phoneNumber = telephonyManager.getSubscriberId();
				
		//Toast.makeText(Splash.this,phoneNumber,Toast.LENGTH_LONG).show();
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		Thread timer = new Thread(){
			public void run(){
				try{
					sleep(3500);
				}catch(InterruptedException e){
					e.printStackTrace();
				}finally{
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
			                	
			                	//String edit = dataFromGetData.getStringExtra("edit");
			                	//String[] usrdata = str.split("|");
			                	//Toast.makeText(AttndcActivity.this,str.trim().length()+"=="+str.trim(),Toast.LENGTH_LONG).show();
			                	if(str.trim().length()>6){
			                		Intent getdata = new Intent("com.hedcet.attendance.GetData");
									getdata.putExtra("data", str.trim());
									getdata.putExtra("QuickCheck", "False");
									startActivity(getdata);
			                	}else goToAttndcActivity();
			                }else goToAttndcActivity();
			            	} catch (Exception e) {
			            		Toast.makeText(Splash.this,"In exception", Toast.LENGTH_LONG).show();
			            		e.printStackTrace();

			            	}	 
			        	}else{
			        		goToAttndcActivity();
			        		//Toast.makeText(AttndcActivity.this,"In else", Toast.LENGTH_LONG).show();
			        	}
					
					
				}
			}
		};
		timer.start();
	}
	public void goToAttndcActivity(){
		Intent attndc = new Intent("com.hedcet.attendance.MainActivity");
		attndc.putExtra("edit", "");
		startActivity(attndc);
	} 
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

}
