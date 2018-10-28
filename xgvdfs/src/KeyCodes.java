import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JApplet;
import javax.swing.JFrame;

public class KeyCodes extends Applet{
int yHeight; // высота символов шрифта,
Dimension appSize; // текущий размер окна апплета
public void init() {
	KeyListener mm = new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
		Graphics g = getGraphics();
		FontMetrics fm = g.getFontMetrics();
		yHeight = fm.getHeight();
		String s = "Char - " + e.getKeyChar() + ", Key - " + e.getKeyCode()
		+ ", Modifiers - " + e.getModifiers();
		g.drawString(s, 10, yHeight);
		}
		public void keyReleased(KeyEvent e) {
		Graphics g = getGraphics();
		g.copyArea(0,1,appSize.width-1,appSize.height-yHeight-5,0,yHeight+1);
		g.setColor(Color.YELLOW);
		g.fillRect(1,1,appSize.width-2,yHeight+1);
		}
		};
		addKeyListener(mm);
}
public void paint(Graphics g) {
appSize = getSize();
g.setColor(Color.YELLOW);
g.fillRect(1,1,appSize.width-1,appSize.height-1);
}
public static void main(String[] args) {
JFrame frame = new JFrame ("Пример");
KeyCodes appl = new KeyCodes();
appl.init();
frame.getContentPane().add(appl);
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setSize(800, 300);
frame.setVisible(true);
}
}