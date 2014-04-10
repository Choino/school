package cs1635.pitt.edu.zps6;

import twitter4j.Status;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TweetAdapter extends ArrayAdapter<Status> {
  private final Context context;
  private final Status[] values;
  
  TextView name;
  TextView contents;
  
  public TweetAdapter(Context context, Status[] values) {
    super(context, R.layout.tweet_status, values);
    this.context = context;
    this.values = values;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
  	  LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.tweet_status, parent, false);
    name = (TextView) rowView.findViewById(R.id.tweet_username);
    TextView date = (TextView) rowView.findViewById(R.id.tweet_date);
    contents = (TextView) rowView.findViewById(R.id.tweet_contents);

    name.setTextColor(Color.BLUE);
    
   // ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
    if (values[position] != null) {
	    name.setText("@" + values[position].getUser().getScreenName());
	
	    date.setText(values[position].getCreatedAt().toString());
	    
	    contents.setText(values[position].getText());    

    }
    	return rowView;
  }
  
} 