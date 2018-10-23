package applet_animation;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
@SuppressWarnings({ "deprecation", "serial" })
public class ParamUrlImage extends Applet {
Image Im;
private String m_FileName = "simple.gif";
private final String PARAM_String_1 = "fileName";
public String[][] getParameterInfo() {
String[][] info ={{ PARAM_String_1, "fileName", "name of file" },};
return info;
}
public void init() {
String param;
param = getParameter(PARAM_String_1);
if (param != null) m_FileName = param;
Im=getImage( getDocumentBase(),m_FileName);
resize(320, 240);
}

public void paint(Graphics g){
	g.drawImage(Im,0,0,this);
	//g.drawString("Applet with Parameters",10, 20);
}
}