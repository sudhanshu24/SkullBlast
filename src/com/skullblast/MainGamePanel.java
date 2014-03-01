package com.skullblast;

import java.util.Random;

import com.faces.skullFace;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {

	//----------------variables-----------------
	private MainThread thread;
	private skullFace[][] skullMatrix;
	int matRows, matCols;
	private static final String TAG = MainGamePanel.class.getSimpleName();
	//----------------methods-------------------
	public MainGamePanel(Context context) {
		super(context);
		
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);
				
		// create a face and a skull and load the bitmap
		//face = new skullFace(BitmapFactory.decodeResource(getResources(), R.drawable.face_pic), 50, 50);
		//skull = new skullFace(BitmapFactory.decodeResource(getResources(), R.drawable.skull_pic), 50, 50);
		
		//populate the skull matrix in MainThread.java
		matRows = matCols = 4;
		int size = 100;
		skullMatrix = new skullFace[matRows][matCols];
		
		Random rand = new Random();
		for(int i=0; i<matRows; i++)
			for(int j=0; j<matCols; j++)
			{
				if(rand.nextInt() > 500000)
					skullMatrix[i][j] = new skullFace(BitmapFactory.decodeResource(getResources(), R.drawable.face_pic), (i+1)*size, (j+1)*size,false);
				else
					skullMatrix[i][j] = new skullFace(BitmapFactory.decodeResource(getResources(), R.drawable.skull_pic), (i+1)*size, (j+1)*size,true);				
			}
		//Create the new main thread
		thread = new MainThread(getHolder(), this);
		
		// make the GamePanel focusable so it can handle events
		setFocusable(true);

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		//start the main thread
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
		Log.d(TAG, "Surface is being destroyed.");
		//Delete this thread and wait for it to finish
		boolean finished = false;
		while(!finished){
			try {
				thread.join();
				finished = true;
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		Log.d(TAG, "Clean thread shutdown.");
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		
		//asking the skullFace object to handle its own part
		if(skullMatrix != null){
			for(int i=0; i<matRows; i++)
				for(int j=0; j<matCols; j++)
					skullMatrix[i][j].handeleActionDown((int)event.getX(), (int)event.getY());
		}
		
		
		//if the event is the start of a press on the screen
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			//if the press is in the lower part of the screen then stop
			if(event.getY() > getHeight() - 100){
				thread.setRunning(false);
				((Activity)getContext()).finish();
			}
			else{
				for(int i=0; i<matRows; i++)
					for(int j=0; j<matCols; j++)
					{
						if(skullMatrix[i][j].isSkull() && skullMatrix[i][j].isTouched())
							skullMatrix[i][j] = new skullFace(BitmapFactory.decodeResource(getResources(), R.drawable.fire), skullMatrix[i][j].getX(),skullMatrix[i][j].getY(),false);
					}
//				if(face != null){
//					if(face.isTouched()){
//						fire = new skullFace(BitmapFactory.decodeResource(getResources(), R.drawable.fire), face.getX(),face.getY());
//						fire.setTouched(false);
//						face = null;
//					}
//				}
				
				Log.d(TAG, "Co-ord : x="+event.getX()+" y="+event.getY());
			}
		}
		
		/*if(event.getAction() == MotionEvent.ACTION_MOVE){
			//if the drag action was done
			if(face.isTouched()){
				face.setX((int)event.getX());
				face.setY((int)event.getY());
			}			
		}
		
		if(event.getAction() == MotionEvent.ACTION_UP){
			//release the face if it was touched
			if(face.isTouched())
				face.setTouched(false);
		}*/
		
		return true;
		
	}
	
	@Override
	protected void onDraw(Canvas canvas){
		//canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.skull_1), 10, 10, null);
		canvas.drawColor(Color.BLACK);
//		if(face != null)
//			face.draw(canvas);
//		else
//			fire.draw(canvas);
		if(skullMatrix != null)
		{
			for(int i=0; i<matRows; i++)
				for(int j=0; j<matCols; j++)
					skullMatrix[i][j].draw(canvas);
		}
		
		//Draw the skull matrix on the canvas.
	}

}
