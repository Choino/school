package cs1635.pitt.edu.zps6;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import android.app.Fragment;
import android.app.ListActivity;
import android.app.ListFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class TabFrag1 extends ListFragment {  
    ResponseList<Status> timeline;
    ListView list;
    Status[] data = new Status[1];
    TweetAdapter adapter;
    
    
    
    boolean updated = false;
    
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
	                           Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.tab1, container, false);
		
		    
        new TwitterTimelineRefreshTask().execute();
        
		return view;
	  }
	
	  @Override
	  public void onListItemClick(ListView l, View v, int position, long id) {
	    // do something with the data
		  Status curr = data[position];
		  User bud = curr.getUser();
		//  System.out.println(bud.getFollowersCount() + " , " + bud.getFriendsCount() + ", " + bud.getName() + " , " + bud.getStatusesCount());
	  
		  Intent i = new Intent(getActivity(), MoreActivity.class);
		  i.putExtra("status",curr);
		  startActivity(i);
	  
	  }
		
		
		
	
	
	private class TwitterTimelineRefreshTask extends AsyncTask<String, String, Boolean> {
	
	    @Override
	    protected void onPostExecute(Boolean result) {
	        if (result) {
	            if (updated)
	            	Toast.makeText(getActivity(), "Timeline updated!", Toast.LENGTH_SHORT).show();
	            data = timeline.toArray(data);
	            adapter = new TweetAdapter(getActivity(), data);
	    		setListAdapter(adapter);
	        }
	        else if (updated) {
	            Toast.makeText(getActivity(), "Error updating timeline!", Toast.LENGTH_SHORT).show();
	        }
	        updated = true;
	    }
	
	    @Override
	    protected Boolean doInBackground(String... params) {
	        try {
	            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
	            String accessTokenString = sharedPreferences.getString("TWITTER_OAUTH_TOKEN", "");
	            String accessTokenSecret = sharedPreferences.getString("TWITTER_OAUTH_TOKEN_SECRET", "");
	
	            if (accessTokenString != null && accessTokenSecret != null) {
	                AccessToken accessToken = new AccessToken(accessTokenString, accessTokenSecret);
	                timeline = TwitterUtil.getInstance().getTwitterFactory().getInstance(accessToken).getHomeTimeline();
	                
	                return true;
	            }
	
	        } catch (TwitterException e) {
	            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
	        }
	        return false;  //To change body of implemented methods use File | Settings | File Templates.
	
	    }
	}
}