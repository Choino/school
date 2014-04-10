package cs1635.pitt.edu.zps6;

import java.util.ArrayList;


import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.URLEntity;
import twitter4j.User;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Audio.Media;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MoreActivity extends Activity {
    Button url1, url2, url3, imagebutton;
    
    TextView username, realname, followers, friends, tweets, tweet;
    
    Status status;
    User user;
    String urlS1, urlS2, urlS3, urlS4;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweet_more);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            status = (Status)extras.get("status");
            user = status.getUser();
        }
        
        initComponents();
    }
 
    private void initComponents() {
    	username = (TextView) findViewById(R.id.more_username);
    	realname = (TextView) findViewById(R.id.more_realname);
    	followers = (TextView) findViewById(R.id.more_followers_num);
    	friends = (TextView) findViewById(R.id.more_friends_num);
    	tweets = (TextView) findViewById(R.id.more_tweet_num);
    	tweet = (TextView) findViewById(R.id.more_content);

    	url1 = (Button) findViewById(R.id.more_url1);
    	url2 = (Button) findViewById(R.id.more_url2);
    	url3 = (Button) findViewById(R.id.more_url3);
    	imagebutton = (Button) findViewById(R.id.more_image);
    	
    	username.setText("@" + user.getScreenName());
    	username.setTextColor(Color.BLUE);
    	realname.setText(user.getName());
    	followers.setText(String.valueOf(user.getFollowersCount()));
    	friends.setText(String.valueOf(user.getFriendsCount()));
    	tweets.setText(String.valueOf(user.getStatusesCount()));
    	tweet.setText(status.getText());
    	

		findViewById(R.id.more_hasURL).setVisibility(Button.INVISIBLE);
    	url1.setVisibility(Button.INVISIBLE);
    	url2.setVisibility(Button.INVISIBLE);
    	url3.setVisibility(Button.INVISIBLE);
    	imagebutton.setVisibility(Button.INVISIBLE);
    	
    	ArrayList<URLEntity> pls = new ArrayList<URLEntity>();
    	ArrayList<MediaEntity> media = new ArrayList<MediaEntity>();
    	
    	
    	
    	for (URLEntity p : status.getURLEntities()) {
    		pls.add(p);
    	}
    	
    	for (MediaEntity p : status.getMediaEntities()) {
    		media.add(p);
    	}

    	
    	if (pls.size() >= 1) {
    		findViewById(R.id.more_hasURL).setVisibility(Button.VISIBLE);
    		
    		url1.setText(pls.get(0).getDisplayURL());
    		url1.setVisibility(Button.VISIBLE);
    		urlS1 = pls.get(0).getURL();
    		url1.setOnClickListener(but1);
    	}
    	if (pls.size() >= 2) {
    		url2.setText(pls.get(1).getDisplayURL());
    		url2.setVisibility(Button.VISIBLE);
    		urlS2 = pls.get(1).getURL();

    		url2.setOnClickListener(but2);
    	}
    	if (pls.size() >= 3) {
    		url3.setText(pls.get(2).getDisplayURL());
    		url3.setVisibility(Button.VISIBLE);
    		urlS3 = pls.get(2).getURL();

    		url3.setOnClickListener(but3);
    	}
    	
    	if (media.size() > 0) {
    		imagebutton.setVisibility(Button.VISIBLE);
    		urlS4 = media.get(0).getURL();
    		
    		imagebutton.setOnClickListener(but4);
    	}
    	
    }
    
    private OnClickListener but1 = new OnClickListener() {

        @Override
        public void onClick(View v) {
        	Intent i = new Intent(Intent.ACTION_VIEW);
        	i.setData(Uri.parse(urlS1));
        	startActivity(i);
        }
    };
    
    private OnClickListener but2 = new OnClickListener() {

        @Override
        public void onClick(View v) {
        	Intent i = new Intent(Intent.ACTION_VIEW);
        	i.setData(Uri.parse(urlS2));
        	startActivity(i);
        }
    };
    
    private OnClickListener but3 = new OnClickListener() {

        @Override
        public void onClick(View v) {
        	Intent i = new Intent(Intent.ACTION_VIEW);
        	i.setData(Uri.parse(urlS3));
        	startActivity(i);
        }
    };
    
    private OnClickListener but4 = new OnClickListener() {

        @Override
        public void onClick(View v) {
        	Intent i = new Intent(Intent.ACTION_VIEW);
        	i.setData(Uri.parse(urlS4));
        	startActivity(i);
        }
    };
    
    
}