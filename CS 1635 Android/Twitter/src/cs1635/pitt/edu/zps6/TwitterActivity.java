package cs1635.pitt.edu.zps6;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TwitterActivity extends Activity {

    Button buttonUpdateStatus, buttonLogout, buttonTimeline;
    EditText editTextStatus;
    TextView textViewStatus, textViewUserName;
    
    ScrollView scrolly;
    

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twitter);

        initControl();
        initTabs();
    }
    
    private void initTabs() {
    	ActionBar.Tab tab1, tab2, tab3;
    	Fragment fragmentTab1 = new TabFrag1();
    	Fragment fragmentTab2 = new TabFrag2();
    	Fragment fragmentTab3 = new TabFrag3();
    	
            
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        tab1 = actionBar.newTab().setText("Timeline");
        tab2 = actionBar.newTab().setText("Tweet!");
        tab3 = actionBar.newTab().setText("@Mentions");
        
        tab1.setTabListener(new SuperListener(fragmentTab1));
        tab2.setTabListener(new SuperListener(fragmentTab2));
        tab3.setTabListener(new SuperListener(fragmentTab3));
        
        actionBar.addTab(tab1);
        actionBar.addTab(tab2);
        actionBar.addTab(tab3);
        
	 
    }
    
    private class SuperListener implements TabListener {

    	Fragment fragment;
    	
    	public SuperListener(Fragment fragment) {
    		this.fragment = fragment;
    	}
    	
		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			ft.replace(R.id.big_daddy, fragment);

		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			ft.remove(fragment);
		}
    	
    	
    }
    

    private void initControl() {
        Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith("oauth://TwitterBaby")) {
            String verifier = uri.getQueryParameter("oauth_verifier");
            new TwitterGetAccessTokenTask().execute(verifier);
        } else
            new TwitterGetAccessTokenTask().execute("");
    }

  

    class TwitterGetAccessTokenTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String userName) {
        }

        @Override
        protected String doInBackground(String... params) {

            Twitter twitter = TwitterUtil.getInstance().getTwitter();
            RequestToken requestToken = TwitterUtil.getInstance().getRequestToken();
            if (params[0] != null && !params[0].equals("")) {
                try {

                    AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, params[0]);
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("TWITTER_OAUTH_TOKEN", accessToken.getToken());
                    editor.putString("TWITTER_OAUTH_TOKEN_SECRET", accessToken.getTokenSecret());
                    editor.putBoolean("TWITTER_IS_LOGGED_IN", true);
                    editor.commit();
                    return twitter.showUser(accessToken.getUserId()).getName();
                } catch (TwitterException e) {
                    e.printStackTrace();  
                }
            } else {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String accessTokenString = sharedPreferences.getString("TWITTER_OAUTH_TOKEN", "");
                String accessTokenSecret = sharedPreferences.getString("TWITTER_OAUTH_TOKEN_SECRET", "");
                AccessToken accessToken = new AccessToken(accessTokenString, accessTokenSecret);
                try {
                    TwitterUtil.getInstance().setTwitterFactory(accessToken);
                    return TwitterUtil.getInstance().getTwitter().showUser(accessToken.getUserId()).getName();
                } catch (TwitterException e) {
                    e.printStackTrace();  
                }
            }

            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }
    }

   
    
  
}