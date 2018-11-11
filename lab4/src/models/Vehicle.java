package models;

import java.awt.*;

public abstract class Vehicle{
	public Vehicle(int x, int y,int type, int speed) {
		super();
		this.type = type;
		this.speed = speed;
		this.x=x;
		this.y=y;
	}
	private int type;
	protected int x;
	private int y;
	protected Image sprite;
	protected int speed;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public Image getSprite() {
		return sprite;
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
	
}
