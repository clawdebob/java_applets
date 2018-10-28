package models;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings({ "serial", "deprecation" })
public class Habitat extends Applet{
private Timer m_timer = new Timer();
private boolean m_runViaFrame = false;
private double m_time = 0;
private Image offScreenImage;
private final Random random = new Random();
private boolean timerhidden=false;
private Image []sprites=new Image [6];
private Image []bsprites=new Image [4];
private ArrayList<Car> cars=new ArrayList<Car>();
private ArrayList<Bike> bikes=new ArrayList<Bike>();
private static int scounter=0;
private boolean active=true;
private class Updater extends TimerTask {
	private Habitat m_applet = null;
	private boolean m_firstRun = true;
	private long m_startTime = 0;
	private long m_lastTime = 0;
	public Updater(Habitat applet) {
		m_applet = applet;
	}
	public void run() {
		if (m_firstRun) {
			m_startTime = System.currentTimeMillis();
			m_lastTime = m_startTime;
			m_firstRun = false;
		}
		long currentTime = System.currentTimeMillis();
		double elapsed = (currentTime - m_startTime) / 1000.0;
		double frameTime = (m_lastTime - m_startTime) / 1000.0;
		m_applet.Update(elapsed, frameTime);
		m_lastTime = currentTime;
	}
}
private Updater up=new Updater(this);
public Habitat() {
	KeyAdapter pk;
	pk = new KeyAdapter(){
	public void keyPressed(KeyEvent e) {
	System.out.println(e);
	int keyCode = e.getKeyCode();
	switch(keyCode) {
		case KeyEvent.VK_T:
			timerhidden=timerhidden ? false : true;
			repaint(); 
			break;
		case KeyEvent.VK_E:
			m_timer.cancel();
			active=false;
			repaint(); 
			break;
		case KeyEvent.VK_B:
			m_timer.schedule(up, 0, 100);
			repaint(); 
			break;
		}
	}
	};
	this.addKeyListener(pk);
	Init();
}
public Habitat(boolean viaFrame) {
	m_runViaFrame = viaFrame;
	Init();
}
public void init() {
	resize(1245, 960);
	try {
		sprites[0]=getImage(new URL(getDocumentBase(),"sedan.jpg"));
		sprites[1]=getImage(new URL(getDocumentBase(),"compact.jpg"));
		sprites[2]=getImage(new URL(getDocumentBase(),"taxi.jpg"));
		sprites[3]=getImage(new URL(getDocumentBase(),"ambulance.jpg"));
		sprites[4]=getImage(new URL(getDocumentBase(),"jeep.jpg"));
		sprites[5]=getImage(new URL(getDocumentBase(),"pickup.jpg"));
		bsprites[0]=getImage(new URL(getDocumentBase(),"moped.jpg"));
		bsprites[1]=getImage(new URL(getDocumentBase(),"nrg.jpg"));
		bsprites[2]=getImage(new URL(getDocumentBase(),"sanchez.jpg"));
		bsprites[3]=getImage(new URL(getDocumentBase(),"ultra.jpg"));
	} catch (MalformedURLException e) {
		e.printStackTrace();
	}
}
private void Init() {}
public void Update(double elapsedTime, double frameTime) {
	m_time = elapsedTime;
	int tim = (int)m_time;
	float p1=random.nextInt(100);
	float p2=random.nextInt(100);
	scounter++;
	if(scounter%10==0) {
		if(tim%2==0 && p1>40) {
			cars.add(new Car(random.nextInt(100),random.nextInt(800),random.nextInt(6),10));
		}
		if(tim%4==0 && p2>60) {
			bikes.add(new Bike(1000+random.nextInt(100),random.nextInt(800),random.nextInt(4),10));
		}
		scounter=0;
	}
	this.repaint();
	Toolkit.getDefaultToolkit().sync();
}
@Override
public void paint(Graphics g) {
	int width = getSize().width;
	int height = getSize().height;
	offScreenImage=createImage(width,height);
	Graphics offScreenGraphics= offScreenImage.getGraphics();
	if(active) {
	offScreenGraphics.setColor(Color.WHITE);
	offScreenGraphics.fillRect(0, 0, this.getWidth(), this.getHeight());
	offScreenGraphics.setColor(Color.BLACK);
	String str = "Time = " + Double.toString(m_time);
	if(timerhidden) {
		offScreenGraphics.drawString(str, 15, 15);
	}
	for(int c=0;c<cars.size();c++) {
		cars.get(c).action();
		if(cars.get(c).getX()<2000) {
			offScreenGraphics.drawImage(sprites[cars.get(c).getType()],cars.get(c).getX(),cars.get(c).getY(),this);
		} else {
			//cars.remove(c);
		}
	}
	for(int c=0;c<bikes.size();c++) {
		bikes.get(c).action();
		if(cars.get(c).getX()>0) {
			offScreenGraphics.drawImage(bsprites[bikes.get(c).getType()],bikes.get(c).getX(),bikes.get(c).getY(),this);
		} else {
			//bikes.remove(c);
		}
	}
	}
	else {
		String str = "Время симуляции = " + Double.toString(m_time);
		String bcount="Мотоциклов создано = " + bikes.size();
		String ccount="Машин создано = " + cars.size();
		offScreenGraphics.setColor(Color.RED);
		offScreenGraphics.setFont(new Font("TimesRoman", Font.PLAIN, 18));
		offScreenGraphics.drawString(str, 15, 15);
		offScreenGraphics.setColor(Color.BLUE);
		offScreenGraphics.setFont(new Font("Helvetica", Font.BOLD, 16));
		offScreenGraphics.drawString(bcount, 15, 35);
		offScreenGraphics.setColor(Color.BLACK);
		offScreenGraphics.setFont(new Font("Helvetica", Font.BOLD, 16));
		offScreenGraphics.drawString(ccount, 15, 55);
		bikes.clear();
		cars.clear();
	}
	g.drawImage(offScreenImage, 0, 0, this);
}
public void update(Graphics g) {
	paint(g);
}
}