package models;

public class Car extends Vehicle implements IBehaviour{
	public Car(int x, int y, int type, int speed) {
		super(x, y, type, speed);
	}
	public void action() {
		x+=speed;
	}
}
