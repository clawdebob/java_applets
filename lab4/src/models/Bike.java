package models;

public class Bike extends Vehicle implements IBehaviour{
	public Bike(int x, int y, int type, int speed) {
		super(x, y, type, speed);
	}
	public void action() {
		x-=speed;
	}
}
