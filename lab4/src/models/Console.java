package models;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.PipedWriter;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Console extends JFrame{
	private JButton submit;
	private JButton save;
	private JButton upload;
	private JTextArea area;
	private JPanel panel;
	public boolean str = false;
	public boolean sv = false;
	public boolean ld = false;
	public int len = 0;
	public PipedWriter writer = new PipedWriter();
	Console(){
		super("Консоль");
		setSize(500, 500);
		panel = new JPanel();
		panel.setLayout(null);
		panel.setSize(500, 500);
		panel.setLocation(0, 0);
		submit = new JButton("Выполнить");
		upload = new JButton("Загрузить");
		save = new JButton("Сохранить");
		area = new JTextArea();
		area.setLocation(10,10);
		area.setSize(300,300);
		submit.setSize(150,30);
		submit.setLocation(340, 10);
		save.setSize(150,30);
		save.setLocation(340, 50);
		upload.setSize(150,30);
		upload.setLocation(340, 90);
		panel.add(submit);
		panel.add(area);
		panel.add(upload);
		panel.add(save);
		add(panel);
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String []data =area.getText().split("\n");
				String last = data[data.length - 1];
				char []text = new char [last.length()];
				text = last.toCharArray();
				len = text.length;
				try {
					writer.write(text, 0, len);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				str = true;
			}   
		});
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileopen = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("json","json");
				fileopen.setFileFilter(filter);
                int ret = fileopen.showDialog(null, "Открыть файл"); 
                if (ret == JFileChooser.APPROVE_OPTION) {
                    String file = fileopen.getSelectedFile().getPath();
                    if(file!=null) {
                    	char []text = new char [file.length()];
        				text = file.toCharArray();
        				len = text.length;
        				try {
        					writer.write(text, 0, len);
        				} catch (IOException e) {
        					// TODO Auto-generated catch block
        					e.printStackTrace();
        				}
        				sv=true;
                    }
                }
			}   
		});
		upload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileopen = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("json","json");
				fileopen.setFileFilter(filter);
                int ret = fileopen.showDialog(null, "Открыть файл"); 
                if (ret == JFileChooser.APPROVE_OPTION) {
                    String file = fileopen.getSelectedFile().getPath();
                    if(file!=null) {
                    	char []text = new char [file.length()];
        				text = file.toCharArray();
        				len = text.length;
        				try {
        					writer.write(text, 0, len);
        				} catch (IOException e) {
        					// TODO Auto-generated catch block
        					e.printStackTrace();
        				}
        				ld = true;
                    }
                }
			}   
		});
	}
	
	public void log(String text) {
		area.setText(area.getText()+"\n" + text + "\n");
	}
	
}
