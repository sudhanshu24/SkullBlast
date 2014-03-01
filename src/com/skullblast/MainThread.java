package com.skullblast;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread{
	//------------- Variables------------------
	private boolean running;
	private SurfaceHolder surfaceHolder;
	private MainGamePanel gamePanelOne;
	
	private static final String TAG = MainThread.class.getSimpleName(); 
	
	
	//----------------methods-------------------
	public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanelOne){
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanelOne = gamePanelOne;
	}
	public void setRunning(boolean value){
		running = value;
	}
	
	
	@SuppressLint("WrongCall")
	@Override
	public void run(){
		Canvas canvas;
		long tickCount = 0L;
		//logging stages of game loop
		Log.d(TAG, "Starting game loop");
		
		while(running){
			canvas = null;
			tickCount++;
			
			try{
				//locking canvas for exclusive pixel editing on the surface
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder) {
					//update the game state
					//draw the canvas on the game panel
					this.gamePanelOne.onDraw(canvas);
				}
			}
			finally
			{
				//in case of an exception, the surface should not be left in an inconsistent state
				if(canvas != null)
					surfaceHolder.unlockCanvasAndPost(canvas);
			}
			//render the updated state
		}
		Log.d(TAG, "Game loop executed "+tickCount+" times.");
	}
	

}
