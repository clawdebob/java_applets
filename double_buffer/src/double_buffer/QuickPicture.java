package double_buffer;

import java.applet.Applet;
import java.awt.*;
import java.net.*;
@SuppressWarnings({ "deprecation", "serial" })
public class QuickPicture extends Applet {
Image pic;// проверка загрузки изображения
private String m_FileName = "simple.gif";
int x=0, y=0;
Image offScreenImage;
public void init(){
pic=getImage(getDocumentBase(), m_FileName);
resize(1245, 960);
}
public void paint (Graphics g){
// создание виртуального экрана
int width = getSize().width;
int height = getSize().height;
offScreenImage=createImage(width,height);
// получение его контекста
Graphics offScreenGraphics= offScreenImage.getGraphics();
// вывод изображения на виртуальный экран
offScreenGraphics.drawImage(pic,x,y,this);
}
/* Каждый раз, когда апплет вызывает метод drawImage(), он создает поток, вызывающий метод imageUpdate(), который можно переопределить в классе апплета и использовать для того, чтобы определить, какая часть изображения загружена в память.*/
public void update(Graphics g) {
    g.drawImage(pic, 0, 0, this);
}
}