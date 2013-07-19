package com.hedcet.attendance;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EncodingUtils;

import android.R.color;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class GetData extends Activity {

	InputStream is;
	TextView tv,tv2;
	EditText etUsername,etPassword;
	Button btSubmit,btCheck,btDetailed;
	WebView wv1;
	String username="",password="";
	HttpResponse hr1;
	String QuickCheck;//Whether it is a Quick check(True) or Not(False)
	private boolean isNetworkConnected() {
		  ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		  NetworkInfo ni = cm.getActiveNetworkInfo();
		  if (ni == null) {
			  // There are no active networks.
			  return false;
		  }else  return true;
	 }
	
	public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
	    Reader reader = null;
	    reader = new InputStreamReader(stream, "UTF-8");        
	    char[] buffer = new char[len];
	    reader.read(buffer);
	    return new String(buffer);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		Intent i = getIntent();
		
		CookieSyncManager.createInstance(getBaseContext());
		
		String UserData = i.getStringExtra("data").toString();
		QuickCheck = i.getStringExtra("QuickCheck").toString();
		if(UserData.length() > 6){
			username = UserData.substring(0, 6);
			password= UserData.substring(7);
		}
		// = UserData[0];
		//String password = UserData[1];
		//Toast.makeText(GetData.this, UserData+"--"+UserData.length()+username , 5000).show();
		
        if(username.length()==6 && password.length()>0){
        	//tv.setText(password+"--"+password.length());
        	//Toast.makeText(GetData.this,password.length(), 5000).show();
        if(isNetworkConnected())	
        	sendPostRequest(username,password);
        else {
        	Toast.makeText(GetData.this, " Check Network Connection, Need active Internet Connection " , 5000).show();
        	Intent i1 = new Intent("com.hedcet.attendance.MainActivity");
			i1.putExtra("edit", "Settings");
			startActivity(i1);
        }
		}else{
			Toast.makeText(GetData.this, " Enter a valid username & password " , 5000).show();
		}
		
		
		
	}
	private void sendPostRequest(String givenUsername, String givenPassword) {

	    class SendPostReqAsyncTask extends AsyncTask<String, Void, String>{
	    	
	        @Override
	        protected String doInBackground(String... params) {

	            String paramUsername = params[0];
	            String paramPassword = params[1];

	           // System.out.println("*** doInBackground ** paramUsername " + paramUsername + " paramPassword :" + paramPassword);

	            HttpClient httpClient = new DefaultHttpClient();

	            // In a POST request, we don't pass the values in the URL.
	            //Therefore we use only the web page URL as the parameter of the HttpPost argument
	            HttpPost httpPost = new HttpPost("http://117.211.100.44:8080/index.php");
	            HttpPost httpPost1 = new HttpPost("http://117.211.100.44:8080/index.php");

	            // Because we are not passing values over the URL, we should have a mechanism to pass the values that can be
	            //uniquely separate by the other end.
	            //To achieve that we use BasicNameValuePair             
	            //Things we need to pass with the POST request
	            BasicNameValuePair usernameBasicNameValuePair = new BasicNameValuePair("userid", paramUsername);
	            BasicNameValuePair passwordBasicNameValuePAir = new BasicNameValuePair("password", paramPassword);
	            BasicNameValuePair submitBasicNameValuePAir = new BasicNameValuePair("Submit", "Submit");
	            
	            BasicNameValuePair moduleBasicNameValuePAir = new BasicNameValuePair("module", "com_views");
	            BasicNameValuePair taskBasicNameValuePAir = new BasicNameValuePair("task", "student_attendance_view");
	            //BasicNameValuePair submit4BasicNameValuePAir = new BasicNameValuePair("Submit4", "Show");
	            //BasicNameValuePair viewBasicNameValuePAir = new BasicNameValuePair("view_type", "summary");
	           // BasicNameValuePair startBasicNameValuePAir = new BasicNameValuePair("start_date", "20/06/2012");
	            //BasicNameValuePair endBasicNameValuePAir = new BasicNameValuePair("end_date", "12/10/2012");
	            

	            //POST http://117.211.100.44:8080/index.php start_date=20%2F06%2F2012&end_date=12%2F10%2F2012&view_type=summary&Submit4=Show&module=com_views&task=student_attendance_view

	            // We add the content that we want to pass with the POST request to as name-value pairs
	            //Now we put those sending details to an ArrayList with type safe of NameValuePair
	            List<BasicNameValuePair> nameValuePairList = new ArrayList<BasicNameValuePair>(); //ArrayList<BasicNameValuePair>();
	            nameValuePairList.add(usernameBasicNameValuePair);
	            nameValuePairList.add(passwordBasicNameValuePAir);
	            nameValuePairList.add(submitBasicNameValuePAir);
	            List<BasicNameValuePair> nameValuePairList1 = new ArrayList<BasicNameValuePair>(); //ArrayList<BasicNameValuePair>();
	            nameValuePairList1.add(moduleBasicNameValuePAir);
	            nameValuePairList1.add(taskBasicNameValuePAir);
	            List<BasicNameValuePair> nameValuePairList2 = new ArrayList<BasicNameValuePair>(); //ArrayList<BasicNameValuePair>();

	            nameValuePairList2.add(moduleBasicNameValuePAir);
	            nameValuePairList2.add(taskBasicNameValuePAir);
	            //nameValuePairList2.add(submit4BasicNameValuePAir);
	            //nameValuePairList2.add(viewBasicNameValuePAir);
	            //nameValuePairList2.add(startBasicNameValuePAir);
	            //nameValuePairList2.add(endBasicNameValuePAir);
	            
	            
	            try {
	                // UrlEncodedFormEntity is an entity composed of a list of url-encoded pairs. 
	                //This is typically useful while sending an HTTP POST request. 
	                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);
	                UrlEncodedFormEntity urlEncodedFormEntity1 = new UrlEncodedFormEntity(nameValuePairList2);

	                // setEntity() hands the entity (here it is urlEncodedFormEntity) to the request.
	                httpPost.setEntity(urlEncodedFormEntity);
	                httpPost1.setEntity(urlEncodedFormEntity1);

	                try {
	                    // HttpResponse is an interface just like HttpPost.
	                    //Therefore we can't initialize them
	                    HttpResponse httpResponse = httpClient.execute(httpPost);
	                    httpResponse = httpClient.execute(httpPost1);
	                    hr1 = httpResponse;	
	                    // According to the JAVA API, InputStream constructor do nothing. 
	                    //So we can't initialize InputStream although it is not an interface
	                    InputStream inputStream = httpResponse.getEntity().getContent();

	                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

	                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

	                    StringBuilder stringBuilder = new StringBuilder();

	                    String bufferedStrChunk = null;

	                    while((bufferedStrChunk = bufferedReader.readLine()) != null){
	                        stringBuilder.append(bufferedStrChunk);
	                    }

	                    return stringBuilder.toString();

	                } catch (ClientProtocolException cpe) {
	                    //System.out.println("First Exception caz of HttpResponese :" + cpe);
	                    cpe.printStackTrace();
	                } catch (IOException ioe) {
	                    //System.out.println("Second Exception caz of HttpResponse :" + ioe);
	                    ioe.printStackTrace();
	                }

	            } catch (UnsupportedEncodingException uee) {
	               // System.out.println("An Exception given because of UrlEncodedFormEntity argument :" + uee);
	                uee.printStackTrace();
	            }

	            return null;
	        }

	        @Override
	        protected void onPostExecute(String result) {
	            super.onPostExecute(result);
	            
	            if(result.length() > 10 && result.contains("Logged in as")){
	            try{	
	            	String[] res = result.split("Attendance till date :");
	            	String[] name = res[0].split("Logged in as");
	            	name = name[1].split("</td>");
	            	name = name[2].split("</span>");
	            	name = name[0].split("<td>");
	            	String[] percent = res[1].split("</td>");
	            	
	            	setContentView(R.layout.main);
	            	etPassword  = (EditText)findViewById(R.id.password);
	        		etUsername = (EditText)findViewById(R.id.username);
	        		btSubmit = (Button)findViewById(R.id.btSubmit);
	        		btDetailed = (Button)findViewById(R.id.btDetailed);
	        		etUsername.setVisibility(View.GONE);
	        		etPassword.setVisibility(View.GONE);
	        		btSubmit.setVisibility(View.GONE);
	               // tv = (TextView) findViewById(R.id.tv1);
	                tv2 = (TextView)findViewById(R.id.tv2);
	                //tv.setVisibility(View.VISIBLE);
	                //tv.setTextColor(color.white);
	            	//tv.setText("Hi "+name[1]+",\n  Your current Attendance is"+ percent[0]);
	            	tv2.setText("Hi "+name[1]+",\n  Your current Attendance is"+ percent[0]);
	            	//Toast.makeText(getApplicationContext(), "Hi "+name[1]+",\n  Your current Attendance is"+ percent[0]+"..!!",Toast.LENGTH_LONG).show();
	            	//GetData.test();
	            	
	            	// The new feature Try with some other username & password
	        		
	        		btCheck = (Button)findViewById(R.id.btCheck);
	        		
	        		//btSubmit.setOnClickListener(GetData.this);
	        		//btSubmit.setOnClickListener(listen);
	        		
	        		btCheck.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							etUsername.setVisibility(View.VISIBLE);
							etPassword.setVisibility(View.VISIBLE);
							btSubmit.setVisibility(View.VISIBLE);
							btCheck.setVisibility(View.GONE);
							btDetailed.setVisibility(View.GONE);
							etUsername.setVisibility(View.FOCUS_DOWN);
						}
					});
	        		btSubmit.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if(etUsername.getText().length()!=6 || etPassword.getText().length()==0){
	        					Toast.makeText(v.getContext(), "Please enter valid Username and Password..!!",Toast.LENGTH_LONG).show();
	        				}else{
	        					Intent getdata = new Intent("com.hedcet.attendance.GetData");
	    						getdata.putExtra("data", etUsername.getText()+"|"+etPassword.getText());
	    						getdata.putExtra("QuickCheck", "True");
	    						startActivity(getdata);
	        				}
						}
        		   });
	        		btDetailed.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							//setContentView(R.layout.detailed);
							byte[] post1 = EncodingUtils.getBytes("module=com_views&task=student_attendance_view&userid="+username+"&password="+password+"&Submit=Submit", "BASE64");
							byte[] post2 = EncodingUtils.getBytes("module=com_views&task=student_attendance_view", "BASE64");
							CookieManager.getInstance().setAcceptCookie(true);
							wv1 = new WebView(v.getContext());
							GetData.this.wv1.setWebViewClient(new WebViewClient(){

								@Override
								public boolean shouldOverrideUrlLoading(
										WebView view, String url) {
									// TODO Auto-generated method stub
									view.loadUrl(url);
									return true;
								}
							});
							/*GetData.this.wv1.setWebViewClient(new WebViewClient(){

							    @Override
							    public boolean shouldOverrideUrlLoading(WebView view, String url){
							      view.loadUrl(url);
							      return true;
							    }
							});*/
							wv1.getSettings().setJavaScriptEnabled(true);
							wv1.getSettings().setDomStorageEnabled(true);
							wv1.getSettings().setBuiltInZoomControls(true);
							wv1.getSettings().setUseWideViewPort(true);
							CookieSyncManager.getInstance().startSync();
							CookieSyncManager.getInstance().sync();
							//CookieManager.getInstance().setAcceptCookie(true);
							wv1.getProgress();
							wv1.postUrl("http://117.211.100.44:8080/index.php",post1);
							//wv1.postUrl("http://117.211.100.44:8080/index.php",post2);
							
							//wv1.loadUrl("javascript:window.location=\'http://117.211.100.44:8080/index.php?module=com_views&task=student_attendance_view\'");
							setContentView(wv1);
						}
					});  
	        		// End of Try-with-Some-Other- username&password
            	}catch(Exception e){
            		Toast.makeText(getApplicationContext(), "Please enter valid Username and Password..!!",Toast.LENGTH_LONG).show();
            		System.out.println("Here is the prob");
	        			e.printStackTrace();
        		}
	                //Toast.makeText(getApplicationContext(), "H"+percent[1]+" working..."+res[1], Toast.LENGTH_LONG).show();
	            }else{
	            	if(QuickCheck.equals("False")){
	            		try{
	            			String FILENAME = "test.txt";
	            			String string = "";

	            			FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
	            			fos.write(string.getBytes());
	            			fos.close();
	            		}catch(Exception e){e.printStackTrace();}
	            		Toast.makeText(getApplicationContext(), "Error !! Invalid Username or Password	...", Toast.LENGTH_LONG).show();
	            		Intent attndc = new Intent("com.hedcet.attendance.MainActivity");
	            		startActivity(attndc);
	            	}else{
	            		finish();
	            		Toast.makeText(getApplicationContext(), "Error !! Invalid Username or Password	...", Toast.LENGTH_LONG).show();
	            	}	
	            }
	        }           
	    }

	    SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
	    sendPostReqAsyncTask.execute(givenUsername, givenPassword);     
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		// TODO Auto-generated method stub
		 super.onCreateOptionsMenu(menu);
		 MenuInflater mInflator = getMenuInflater();
		 mInflator.inflate(R.menu.cool_menu, menu);
		 return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.aboutUs:
			Intent i = new Intent("com.hedcet.attendance.AboutUs");
			startActivity(i);
			break;
		case R.id.settings: //Opens Attndnc Activity
			Intent i1 = new Intent("com.hedcet.attendance.MainActivity");
			i1.putExtra("edit", "Settings");
			startActivity(i1);
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
