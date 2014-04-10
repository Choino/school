package cs1635.pitt.edu.zps6;

import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	/*
	API key				RF5ndYaN7sfNtB3hRYpq25xAG
	API secret			IlP0JTZrfRhMnCgC0GRe0DoYNmk0VhbIvNfott6c9jZXvIrVTP
	Access token		1694686074-13YtNRpU4LHags8bWwZaMJ61qz3ucjXAo60NVLr
	Access token secret	msVmK6yBFz90jqmChNwsdErxcv0JrQC6PQsl8hz6BscmK
	*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	 
	    if (!isNetworkAvailable()) {
	    	Toast.makeText(this, "Sorry, network is unavailable.", Toast.LENGTH_LONG).show();
	    	return;
	    }
	 
	 
	    initButton();
	    new TwitterAuthenticateTask().execute();
		 Intent intent = new Intent(MainActivity.this, TwitterActivity.class);
        startActivity(intent);
	}
	
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	private void initButton() {
		Button login = (Button) findViewById(R.id.button_login);
		login.setOnClickListener(new LoginListener());
	}
	
	private class LoginListener implements OnClickListener {
		public void onClick(View arg0) {
			new TwitterAuthenticateTask().execute();
			 Intent intent = new Intent(MainActivity.this, TwitterActivity.class);
             startActivity(intent);
		}
		   	
		
	}

	class TwitterAuthenticateTask extends AsyncTask<String, String, RequestToken> {

        @Override
        protected void onPostExecute(RequestToken requestToken) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(requestToken.getAuthenticationURL()));
            startActivity(intent);
        }

        @Override
        protected RequestToken doInBackground(String... params) {
            return TwitterUtil.getInstance().getRequestToken();
        }
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
