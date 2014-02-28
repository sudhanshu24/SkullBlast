package com.faces;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class skullFace {
	private Bitmap face;
	private int x;
	private int y;
	private boolean touched;
	private boolean skull;
	
	public boolean isSkull() {
		return skull;
	}

	public void setSkull(boolean skull) {
		this.skull = skull;
	}

	public boolean isTouched() {
		return touched;
	}

	public void setTouched(boolean touch) {
		this.touched = touch;
	}
	//can adjust the image properties by this method like height and width
	public skullFace(Bitmap face, int x, int y, boolean skull) {
		this.face = face;
		int size = 100;
		this.x = x;
		this.y = y;
		this.skull = skull;
		this.face = Bitmap.createScaledBitmap(face, size, size, true);
	}
	
	public skullFace(int choice, int x, int y) {
		if(choice == 1);
		this.x = x;
		this.y = y;
	}
	
	public Bitmap getFace() {
		return face;
	}
	public void setFace(Bitmap face) {
		this.face = face;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	public void draw(Canvas canvas){
		//int margin = 10;
		//canvas.drawBitmap(face, x - (face.getWidth()/2), y - (face.getHeight()/2), null);
		canvas.drawBitmap(face, x - (face.getWidth()), y - (face.getHeight()), null);
	}
	
	public void handeleActionDown(int eventX, int eventY){
		if(eventX >= x - (face.getWidth()/2) && eventX <= x + (face.getWidth()/2)){
			
			if(eventY >= y - (face.getHeight()/2) && eventY <= y + (face.getHeight()/2)){
				//valid touch to the face
				setTouched(true);
			}
			else
				setTouched(false);
		}
		else
			setTouched(false);
	}
}
