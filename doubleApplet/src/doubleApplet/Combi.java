package doubleApplet;

import javax.swing.event.MouseInputAdapter;
import java.applet.Applet;
import java.awt.event.*;
import java.awt.*;
@SuppressWarnings({ "deprecation", "unused", "serial" })
public class Combi extends Applet {
int x=10, y=20;
public Combi(){
// обработчики событий от мыши и клавиатуры
}
public void init() { }
public void paint (Graphics g) {
g.drawString("Applet with Events",x, y);
}
public static void main(String args[]) {
Frame fr = new Frame("Апплет двойного назначения");
Combi c = new Combi();
c.init();
fr.add(c);
fr.setSize(400,300);
fr.setVisible(true);
// обработка события закрытие окна-рамки
fr.addWindowListener(new WindowAdapter(){
public void windowClosing(WindowEvent e) {
System.exit(0);
} });
}
}