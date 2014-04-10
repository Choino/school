package edu.pitt.cs1635.zps6.proj2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class OtherActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    setContentView(R.layout.loading_screen);
//        findViewById(R.id.back_button).setOnClickListener(new HandleClick());   
    }
    
    private class HandleClick implements OnClickListener{
   	 public void onClick(View arg0) {
   		Intent intent = new Intent(OtherActivity.this, MainActivity.class);
		startActivity(intent);
   	 }
    }
}
