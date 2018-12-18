package models;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.Reader;
import java.io.Writer;

@SuppressWarnings({ "serial", "deprecation" })
public class Habitat extends Applet{
private Timer m_timer = new Timer();
private boolean pressed = false;
private Console con = new Console();
private boolean m_runViaFrame = false;
private double m_time = 0;
private Image offScreenImage;
private final Random random = new Random();
private boolean timerhidden=false;
private Image []sprites=new Image [6];
private Image []bsprites=new Image [9];
private ArrayList<Car> cars=new ArrayList<Car>();
private ArrayList<Bike> bikes=new ArrayList<Bike>();
private ArrayList<Thread> multit = new ArrayList<Thread>();
private HashMap<Integer,Double> bbirth = new HashMap<Integer,Double>();
private HashMap<Integer,Double> cbirth = new HashMap<Integer,Double>();
private static PipedReader reader = new PipedReader();
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
		try {
			m_applet.Update(elapsed, frameTime);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		case KeyEvent.VK_Z:
			pressed= true;
			con.setVisible(true);
			try {
				reader.connect(con.writer);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		break;
		case KeyEvent.VK_T:
			timerhidden=timerhidden ? false : true;
			repaint(); 
			break;
		case KeyEvent.VK_E:
			m_timer.cancel();
			active=false;
			for(int c=1;c<=cbirth.size();c++) {
				System.out.println("Машина №"+c+" Время содания:"+cbirth.get(c));
			}
			for(int c=1;c<=bbirth.size();c++) {
				System.out.println("Мотоцикл №"+c+" Время содания:"+bbirth.get(c));
			}
			repaint(); 
			break;
		case KeyEvent.VK_S:
			String host = "localhost";
			int port = 3333;
			Socket sock = null;
			try {
				sock = new Socket(host, port);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		DataOutputStream outStream;
		try {
			outStream = new DataOutputStream(sock.getOutputStream());
			DataInputStream inStream = new DataInputStream(sock.getInputStream());
			Gson gson = new GsonBuilder().create();
			State st = new State(cars.size(),bikes.size());
			outStream.writeUTF(gson.toJson(st));
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
			break;
		case KeyEvent.VK_R:
			host = "localhost";
			port = 3333;
			sock = null;
			try {
				sock = new Socket(host, port);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		try {
			outStream = new DataOutputStream(sock.getOutputStream());
			DataInputStream inStream = new DataInputStream(sock.getInputStream());
			Gson gson = new GsonBuilder().create();
			outStream.writeUTF("reload");
			setVisible(false);
			cars.clear();
			bikes.clear();
			Thread.sleep(2000);
			setVisible(true);
			System.out.println(inStream.readUTF());
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			break;
		case KeyEvent.VK_B:
			m_timer.schedule(up, 0, 100);
			repaint(); 
			break;
		case KeyEvent.VK_C:
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
		sprites[0]=getImage(new URL(getDocumentBase(),"sedan.png"));
		sprites[1]=getImage(new URL(getDocumentBase(),"compact.png"));
		sprites[2]=getImage(new URL(getDocumentBase(),"taxi.png"));
		sprites[3]=getImage(new URL(getDocumentBase(),"ambulance.jpg"));
		sprites[4]=getImage(new URL(getDocumentBase(),"jeep.png"));
		sprites[5]=getImage(new URL(getDocumentBase(),"pickup.png"));
		bsprites[0]=getImage(new URL(getDocumentBase(),"moped.png"));
		bsprites[1]=getImage(new URL(getDocumentBase(),"nrg.png"));
		bsprites[2]=getImage(new URL(getDocumentBase(),"sanchez.png"));
		bsprites[3]=getImage(new URL(getDocumentBase(),"ultra.png"));
		bsprites[4]=getImage(new URL(getDocumentBase(),"noodle.png"));
		bsprites[5]=getImage(new URL(getDocumentBase(),"psj.png"));
		bsprites[6]=getImage(new URL(getDocumentBase(),"fultra.png"));
		bsprites[7]=getImage(new URL(getDocumentBase(),"faggio.png"));
		bsprites[8]=getImage(new URL(getDocumentBase(),"green.png"));
	} catch (MalformedURLException e) {
		e.printStackTrace();
	}
}
private void Init() {}
public void Update(double elapsedTime, double frameTime) throws IOException {
	int frequency = 40;
	if(pressed) {
		if(con.str) {
			char []com = new char [con.len];
			
			try {
				reader.read(com,0,con.len);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String fin = new String(com);
			System.out.println(fin);
			con.str=false;
			if(fin.contains("frequency")) {
				try {
				String val = fin.split("\\s+")[1];
				frequency = Integer.parseInt(val);
				con.log("bikes frequency changed to:" + val);
				}catch(Exception e1){
					con.log("Parsing exeption!!!");
				}
			}
			else {
				con.log("unknown command");
			}
		}
		if(con.sv) {
			char []com = new char [con.len];
			
			try {
				reader.read(com,0,con.len);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String fin = new String(com);
			State st = new State(cars.size(),bikes.size());
			con.sv=false;
			System.out.println(fin);
			try (Writer writer = new FileWriter(fin)) {
			    Gson gson = new GsonBuilder().create();
			    gson.toJson(st, writer);
			    writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(con.ld) {
			//m_timer.cancel();
			char []com = new char [con.len];
			
			try {
				reader.read(com,0,con.len);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String fin = new String(com);
			con.ld=false;
			System.out.println(fin);
			Reader reader;
			try {
				reader = new FileReader(fin);
				State ps = new Gson().fromJson(reader, State.class);
				cars.clear();
				bikes.clear();
				for(int c=0;c<ps.cars;c++) {
					cars.add(new Car(2100,2100,2100,10));
				}
				for(int c=0;c<ps.bikes;c++) {
					bikes.add(new Bike(0,0,0,10));
				}
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	m_time = elapsedTime;
	int tim = (int)m_time;
	float p1=random.nextInt(100);
	float p2=random.nextInt(100);
	scounter++;
	if(scounter%10==0) {
		if(tim%2==0 && p1>40) {
			Car ne = new Car(random.nextInt(100),random.nextInt(800),random.nextInt(6),10);
			cars.add(ne);
			multit.add(new Thread(ne));
			cbirth.put(cars.size(), m_time);
		}
		if(tim%4==0 && p2>100 - frequency) {
			Bike ne = new Bike(1000+random.nextInt(100),random.nextInt(800),random.nextInt(9),10);
			bikes.add(ne);
			multit.add(new Thread(ne));
			bbirth.put(bikes.size(), m_time);
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
		Thread cart = new Thread(cars.get(c));
		cart.start();
		//cars.get(c).action();
		if(cars.get(c).getX()<2000) {
			offScreenGraphics.drawImage(sprites[cars.get(c).getType()],cars.get(c).getX(),cars.get(c).getY(),this);
		} else {
			//cars.remove(c);
		}
	}
	for(int c=0;c<bikes.size();c++) {
		Thread biket = new Thread(bikes.get(c));
		biket.start();
		//bikes.get(c).action();
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