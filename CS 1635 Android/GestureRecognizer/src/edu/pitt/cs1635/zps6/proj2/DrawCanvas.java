package edu.pitt.cs1635.zps6.proj2;

import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawCanvas extends View {
	
	ArrayList<MyPath> steveLovesPaths = new ArrayList<MyPath>();
	Paint drawPaint = new Paint();
	boolean drawItBby = true;
	int theColor = Color.BLACK;	
	
	MyPath currPath = new MyPath(theColor);
	
	public ArrayList<MyPath> paths() {
		return (ArrayList<MyPath>) steveLovesPaths.clone();
	}
	
	public Paint paint() {
		return new Paint(drawPaint);
	}
	
	public boolean drawIt() {
		return drawItBby;
	}
	
	public int color() {
		return theColor;
	}
	
	public MyPath curr() {
		return new MyPath(currPath);
	}
	
	public String toString() {
		StringBuilder steveString = new StringBuilder("[");
		
		for (MyPath stevePath : steveLovesPaths) {
			steveString.append(stevePath.toString());
			steveString.append("255, 0, ");
		}
		steveString.append("255, 255]");

		return steveString.toString();
	}
	
	public DrawCanvas(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public DrawCanvas(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public DrawCanvas(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	protected void onDraw(Canvas c) {
		// do something
		
		drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(5);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        
        
		c.drawColor(Color.WHITE);
		if (drawItBby) {
			drawPaint.setColor(currPath.steveColor);
			c.drawPath(currPath, drawPaint);
			
			for (MyPath stevePath : steveLovesPaths) {
				drawPaint.setColor(stevePath.steveColor);
				c.drawPath(stevePath, drawPaint);
			}
		}
		
	}
		
	public void setColor(int yeahDude) {
		currPath.steveColor = yeahDude;
	}
	
	public void clear() {
		drawItBby = true;
		steveLovesPaths.clear();
		postInvalidate();
	}
	
	public boolean onTouchEvent(MotionEvent event) {
	        float pointX = event.getX();
	        float pointY = event.getY();
	        // Checks for the event that occurs
	        switch (event.getAction()) {
	        case MotionEvent.ACTION_DOWN:
	            // Starts a new line in the path
	            currPath.moveTo(pointX, pointY);
	            currPath.addCoord(pointX, pointY, this.getWidth(), this.getHeight());
	            
	            break;
	        case MotionEvent.ACTION_MOVE:
	            // Draws line between last point and this point
	            currPath.lineTo(pointX, pointY);
	            currPath.addCoord(pointX, pointY, this.getWidth(), this.getHeight());
	            
	            break;
	        
	        case MotionEvent.ACTION_UP:
	        	steveLovesPaths.add(currPath);
	        	currPath = new MyPath(theColor);
	        	
	        	break;
	            
	        default:
	            return false;
	       }

	       postInvalidate(); // Indicate view should be redrawn
	       return true; // Indicate we've consumed the touch
   }
    
	private class MyPath extends Path {
		
		ArrayList<Integer> xPositions = new ArrayList<Integer>();
		ArrayList<Integer> yPositions = new ArrayList<Integer>();
		int steveColor;
		
		public MyPath(int color) {
			// do nothing bby
			steveColor = color;
		}
		
		public MyPath(MyPath other) {
			super(other);
			this.xPositions = other.xPositions;
			this.yPositions = other.yPositions;
			this.steveColor = other.steveColor;
		}
		
		public void addCoord(float x, float y, double width, double height) {
			xPositions.add(Integer.valueOf((int)(254*x/width)));
			yPositions.add(Integer.valueOf((int) (254*y/height)));
		}
		
		public String toString() {
			StringBuilder ans = new StringBuilder();
			for (int i = 0; i < xPositions.size(); i++) {
				ans.append(xPositions.get(i) + ", " + yPositions.get(i) + ", ");
			}
			return ans.toString();
		}
		
	}
	
}
