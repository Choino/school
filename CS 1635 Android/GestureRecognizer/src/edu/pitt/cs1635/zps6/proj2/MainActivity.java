package edu.pitt.cs1635.zps6.proj2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	DrawCanvas drawSteve;
	String answer;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.activity_main);
        findViewById(R.id.button_clear).setOnClickListener(new HandleClick());
        findViewById(R.id.button_red).setOnClickListener(new HandleClick());
        findViewById(R.id.button_blue).setOnClickListener(new HandleClick());
        findViewById(R.id.button_green).setOnClickListener(new HandleClick());
        findViewById(R.id.button_black).setOnClickListener(new HandleClick());
        findViewById(R.id.button_save).setOnClickListener(new HandleClick());
        findViewById(R.id.button_load).setOnClickListener(new HandleClick());
        
        
        findViewById(R.id.button_send).setOnClickListener(new HandleClick2());
        
        
        LinearLayout v1 = new LinearLayout(this);
        v1.addView(new DrawCanvas(this));
        drawSteve = (DrawCanvas) findViewById(R.id.drawCanvas);
        
       

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    

  	 public void doTheLoad() {
  		 FileInputStream fos;
  		 ObjectInputStream oos;
  		/* 
  		try {	
			fos = this.openFileInput("Picture " ));
			oos = new ObjectOutputStream(fos);
		
	//		oos.writeObject(drawSteve);
			oos.writeObject(drawSteve.paths());
			oos.writeObject(drawSteve.paint());
			oos.writeObject(drawSteve.drawIt());
			oos.writeObject(drawSteve.color());
			oos.writeObject(drawSteve.curr());
			fos.close();
			oos.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
  	 }
    
    public void doTheSave() {
   		FileOutputStream fos;
		ObjectOutputStream oos;
   		try {	
			fos = this.openFileOutput("Picture " + (this.fileList().length + 1) , Context.MODE_PRIVATE);
			oos = new ObjectOutputStream(fos);
		
	//		oos.writeObject(drawSteve);
			oos.writeObject(drawSteve.paths());
			oos.writeObject(drawSteve.paint());
			oos.writeObject(drawSteve.drawIt());
			oos.writeObject(drawSteve.color());
			oos.writeObject(drawSteve.curr());
			fos.close();
			oos.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
   		new StevePls().plsWork();
   
   	 }
    
    private class StevePls {
    	public void plsWork() {
    		TextView textyBabe = (TextView)findViewById(R.id.result_area);
	   		textyBabe.setText(MainActivity.this.getString(R.string.success_content_string) + (MainActivity.this.fileList().length + 1) );
            textyBabe.postInvalidate();
    	} 
    }
    
    private class HandleClick implements OnClickListener{
   	 public void onClick(View arg0) {
   		 
   		 switch(arg0.getId()) {
   		 case R.id.button_clear:
   			 drawSteve.clear();
   			 break;
   		 
   		 case R.id.button_red:
   			drawSteve.setColor(getResources().getColor(R.color.color_red));
   			drawSteve.postInvalidate();
   			 break;
   		 
   		case R.id.button_blue:
   			drawSteve.setColor(getResources().getColor(R.color.color_blue));
   			drawSteve.postInvalidate();
  			 break;
  			 
   		case R.id.button_green:
   			drawSteve.setColor(getResources().getColor(R.color.color_green));
   			drawSteve.postInvalidate();
  			 break;
  			 
   		case R.id.button_black:
  			 drawSteve.setColor(getResources().getColor(R.color.color_black));
			drawSteve.postInvalidate();
  			 break;
   		 		
   		case R.id.button_save:
   			doTheSave();
   			break;
   			
   		case R.id.button_load:
   			doTheLoad();
   			break;
   		 }
   		 
   	 }

   }
    
    
    private class SteveThePostman extends AsyncTask<String, Void, String>{
    	
            @Override
            protected String doInBackground(String... params) {
                
            	HttpClient httpclient = new DefaultHttpClient();
           	    HttpPost httppost = new HttpPost("http://cwritepad.appspot.com/reco/usen");

           	    try {
           	        // Add your data
           	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
           	        nameValuePairs.add(new BasicNameValuePair("key", "11773edfd643f813c18d82f56a8104ed"));
           	        nameValuePairs.add(new BasicNameValuePair("q", params[0]));
           	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

           	        // Execute HTTP Post Request
           	        HttpResponse response = httpclient.execute(httppost);
	           	    BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	           	    StringBuilder ans = new StringBuilder();
	           	    String body = "";
		            while ((body = rd.readLine()) != null) 
		         	{
		           	      ans.append(body);
		           	}
           	        answer = ans.toString();
           	 

           	    } catch (ClientProtocolException e) {
           	        // TODO Auto-generated catch block
           	    	return "Exception :(";
           	    } catch (IOException e) {
           	        // TODO Auto-generated catch block
           	    	return "Exception :(";
           	    }
           	    

       	        return answer.toString();
             }
            	
            

            @Override
            protected void onPostExecute(String result) {
            	 TextView textyBabe = (TextView)findViewById(R.id.result_area);
                 textyBabe.setText(answer);
                 textyBabe.postInvalidate();
            }

            @Override
            protected void onPreExecute() {
            	
            }

            @Override
            protected void onProgressUpdate(Void... values) {}
        }
    
    
    
    private class HandleClick2 implements OnClickListener{
      	 public void onClick(View arg0) {
      		 
      		 SteveThePostman steveMan = new SteveThePostman();
      		 steveMan.execute(drawSteve.toString());
      		 
      	 }
  	 }
    
    
   
    
}
