package edu.pitt.cs1635.zps6.prog1;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;

public class PostageCalculator extends Activity {
	
	int buttonSelected;
	final static int LETTER = 0;
	final static int ENVELOPE = 1;
	final static int PACKAGE = 2;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postage_calculator);
        findViewById(R.id.calculate_button).setOnClickListener(new HandleClick());
        findViewById(R.id.calculate_button).setEnabled(false);
        findViewById(R.id.rules_button).setOnClickListener(new HandleClick2());    
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.postage_calculator, menu);
        return true;
    }
    
    private class HandleClick implements OnClickListener{
    	 public void onClick(View arg0) {
  
    		 EditText theirWeight = (EditText)findViewById(R.id.weight_textfield);
    		 Editable theWeight = theirWeight.getText();
    		 if (theirWeight.getText().toString().equals("")) {
    			 return;
    		 }
    		 double theWeightAsDouble= Double.parseDouble(theWeight.toString());
    		 
    		 if (buttonSelected == ENVELOPE) {
    			 theWeightAsDouble = envelopeCost(theWeightAsDouble);
    		 }
    		 if (buttonSelected == LETTER) {
    			 theWeightAsDouble = letterCost(theWeightAsDouble);
    		 }
    		 if (buttonSelected == PACKAGE) {
    			 theWeightAsDouble = packageCost(theWeightAsDouble);
    		 }
    		 
    		 if (theWeightAsDouble == -1) {
        		 new AlertDialog.Builder(PostageCalculator.this)
                 .setTitle(R.string.invalid_title)
                 .setMessage(R.string.invalid_value)
                 .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) { 
                     }
                  })
                  .show();

        		 return;
    		 }else if (theWeightAsDouble == -2) {
        		 new AlertDialog.Builder(PostageCalculator.this)
                 .setTitle(R.string.invalid_title)
                 .setMessage(R.string.letter_range)
                 .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) { 
                     }
                  })
                  .show();

        		 return;
    		 } else {
    			 String resultString = getString(R.string.calculate_result);
    			 resultString += " " + theWeightAsDouble + " " + getString(R.string.dollars);
    			 
    			 
    			 new AlertDialog.Builder(PostageCalculator.this)
                 .setTitle(R.string.calculate_success)
                 .setMessage(resultString)
                 .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) { 
                     }
                  })
                  .show();
    		 }
    	 
    	 }
    }
    
    private class HandleClick2 implements OnClickListener{
   	 public void onClick(View arg0) {
   		Intent intent = new Intent(PostageCalculator.this, OtherActivity.class);
		startActivity(intent);
   	 }
   }
    

    
    
    public double letterCost(double weight) {
    	double ans = -1;
    	
    	
    	if (weight <= 3.5) {
    		ans = 1.06;
    	}
    	if (weight <= 3) {
    		ans = .86;
    	}
    	if (weight <= 2) {
    		ans = .66;
    	}
    	if (weight <= 1) {
    		ans = .46;
    	}
    	if (weight <= 0 || weight > 3.5) {
    		ans = -2;
    	}
    	
    	return ans;
    }
    
    public double envelopeCost(double weight) {
    	double ans = -1;
    	
    	if (weight <= 13) {
    		ans = 3.32;
    	}
    	if (weight <= 12) {
    		ans = 3.12;
    	}
    	if (weight <= 11) {
    		ans = 2.92;
    	}
    	if (weight <= 10) {
    		ans = 2.72;
    	}
    	if (weight <= 9) {
    		ans = 2.52;
    	}
    	if (weight <= 8) {
    		ans = 2.32;
    	}
    	if (weight <= 7) {
    		ans = 2.12;
    	}
    	if (weight <= 6) {
    		ans = 1.92;
    	}
    	if (weight <= 5) {
    		ans = 1.72;
    	}
    	if (weight <= 4) {
    		ans = 1.52;
    	}
    	if (weight <= 3) {
    		ans = 1.32;
    	}
    	if (weight <= 2) {
    		ans = 1.12;
    	}
    	if (weight <= 1) {
    		ans = .92;
    	}
    	if (weight <= 0 || weight > 13) {
    		ans = -1;
    	}
    	
    	return ans;
    }
    
    public double packageCost(double weight) {
    	double ans = -1;
    	
    	if (weight <= 13) {
    		ans = 3.77;
    	}
    	if (weight <= 12) {
    		ans = 3.6;
    	}
    	if (weight <= 11) {
    		ans = 3.43;
    	}
    	if (weight <= 10) {
    		ans = 3.26;
    	}
    	if (weight <= 9) {
    		ans = 3.09;
    	}
    	if (weight <= 8) {
    		ans = 2.92;
    	}
    	if (weight <= 7) {
    		ans = 2.75;
    	}
    	if (weight <= 6) {
    		ans = 2.58;
    	}
    	if (weight <= 5) {
    		ans = 2.41;
    	}
    	if (weight <= 4) {
    		ans = 2.24;
    	}
    	if (weight <= 3) {
    		ans = 2.07;
    	}
    	if (weight <= 0 || weight > 13) {
    		ans = -1;
    	}
    	
    	return ans;
    }
    
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.envelope_button:
                if (checked)
                    buttonSelected = ENVELOPE;
                break;
            case R.id.letter_button:
                if (checked)
                	buttonSelected = LETTER;
                break;
            case R.id.package_button:
                if (checked)
                	buttonSelected = PACKAGE;
                break;
        }
        findViewById(R.id.calculate_button).setEnabled(true);
    }
}
