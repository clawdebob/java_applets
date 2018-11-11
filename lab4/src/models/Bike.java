package models;

public class Bike extends Vehicle implements IBehaviour, Runnable{
	public Bike(int x, int y, int type, int speed) {
		super(x, y, type, speed);
	}
	public void action() {
		x-=speed;
	}
	@Override
	public void run() {
		action();
	}
}
