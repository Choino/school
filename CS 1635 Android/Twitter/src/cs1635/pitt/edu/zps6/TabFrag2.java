package cs1635.pitt.edu.zps6;

import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class TabFrag2 extends Fragment {
	EditText editTextStatus;  
	TextView  textViewStatus;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
	                           Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.tab2, container, false);
		
		Button buttonUpdateStatus = (Button) view.findViewById(R.id.buttonUpdateStatus);
	    editTextStatus = (EditText) view.findViewById(R.id.editTextStatus);
	    textViewStatus = (TextView) view.findViewById(R.id.textViewStatus);
	   
		buttonUpdateStatus.setOnClickListener(buttonUpdateStatusOnClickListener);
       
		
		// do list stuff here
		return view;
	  }
	  
	  private OnClickListener buttonUpdateStatusOnClickListener = new OnClickListener() {

	        @Override
	        public void onClick(View v) {
	            String status = editTextStatus.getText().toString();
	            if (status != null && !status.equals("")) {
	                new TwitterUpdateStatusTask().execute(status);
	                editTextStatus.setText("");
	                Toast.makeText(getActivity(), "Status posted!", Toast.LENGTH_LONG).show();

	            } else {
	                Toast.makeText(getActivity(), "Please enter a status", Toast.LENGTH_LONG).show();
	            }

	        }
	    };
	    
	    private class TwitterUpdateStatusTask extends AsyncTask<String, String, Boolean> {

	        @Override
	        protected void onPostExecute(Boolean result) {
	            if (result)
	                Toast.makeText(getActivity(), "Tweet posted successfully!", Toast.LENGTH_SHORT).show();
	            else
	                Toast.makeText(getActivity(), "Tweet failed", Toast.LENGTH_SHORT).show();
	        }
	        
	        @Override
	        protected Boolean doInBackground(String... params) {
	            try {
	                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
	                String accessTokenString = sharedPreferences.getString("TWITTER_OAUTH_TOKEN", "");
	                String accessTokenSecret = sharedPreferences.getString("TWITTER_OAUTH_TOKEN_SECRET", "");

	                if (accessTokenString != null && accessTokenSecret != null) {
	                    AccessToken accessToken = new AccessToken(accessTokenString, accessTokenSecret);
	                    twitter4j.Status status = TwitterUtil.getInstance().getTwitterFactory().getInstance(accessToken).updateStatus(params[0]);
	                    return true;
	                }

	            } catch (TwitterException e) {
	                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
	            }
	            return false;  //To change body of implemented methods use File | Settings | File Templates.

	        }
	    }

	    
	}