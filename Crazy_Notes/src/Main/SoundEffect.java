package Main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Functional.SoundProcess;
import Functional.Coordinators;
import Functional.Hrtf;

public class SoundEffect {
	static String filePath = "";
	static String converterPath = "";
	static SourceDataLine soundLine;
    static AudioFormat audioFormat;
    static DataLine.Info info;
	public SoundEffect(){
		converterPath = System.getProperty("user.dir");
		JFrame frame = new JFrame("Crazy Note");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int w = 700;
        int h = 400;
        frame.setSize(w, h);
        frame.setLocation(screenSize.width/2 - w/2, screenSize.height/2 - h/2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();    
        placeComponents(frame, panel);
        
        frame.add(panel);
        
        frame.setUndecorated(true);
        frame.setVisible(true); 
	}
	
	private static void placeComponents(JFrame frame, JPanel panel) {
		 panel.setLayout(null);
		 JLabel titleLabel = new JLabel("Sound Process",JLabel.CENTER);
	     titleLabel.setBounds(0,75,700,40);
	     Font fTitle = new Font("微软雅黑",Font.BOLD,40); 
	     titleLabel.setFont(fTitle);
	     panel.add(titleLabel);
	     JButton fileButton = new JButton("Choose a File");
	     fileButton.setBounds(250,200,200,40);
	     Font fButton = new Font("微软雅黑",Font.BOLD,20); 
	     fileButton.setFont(fButton);
	     fileButton.setBackground(Color.ORANGE);
	     panel.add(fileButton);
	     JLabel fileLabel = new JLabel("",JLabel.CENTER);
	     fileLabel.setBounds(0,150,700,40);
	     Font fFont = new Font("微软雅黑",Font.PLAIN,24); 
	     fileLabel.setFont(fFont);
		 panel.add(fileLabel);
		 JButton leftButton = new JButton("Left");
			leftButton.setBounds(100,210,100,100);
		    leftButton.setFont(fButton);
		    leftButton.setBackground(Color.yellow);
		    panel.add(leftButton);
		    JButton rightButton = new JButton("Right");
		    rightButton.setBounds(500,210,100,100);
		    rightButton.setFont(fButton);
		    rightButton.setBackground(Color.yellow);
		    panel.add(rightButton);
		    leftButton.setEnabled(false);
		    rightButton.setEnabled(false);
		    leftButton.addActionListener(new ActionListener(){
		    	public void actionPerformed(ActionEvent e) {
		    		PlayEffected.isLeft = true;
		    	}
		    });
		    rightButton.addActionListener(new ActionListener(){
		    	public void actionPerformed(ActionEvent e) {
		    		PlayEffected.isLeft = false;
		    	}
		    });
	     fileButton.addActionListener(new ActionListener(){
	     public void actionPerformed(ActionEvent e) {
	    	 boolean isChoose = fileButton.getText().startsWith("C");
	    	 if(isChoose) {
	    	 	JFileChooser jfc = new JFileChooser();
         		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
     			jfc.showDialog(new JLabel(), "Choose a mid file");
     			File file = jfc.getSelectedFile();
     			fileLabel.setText(file.getAbsolutePath());  
     			filePath = file.getAbsolutePath();
     			fileButton.setText("Start Process");
	    	 } else {
	    		 fileButton.setEnabled(false);
	    		 leftButton.setEnabled(true);
			 	 rightButton.setEnabled(true);
	    		 PlayEffected d = new PlayEffected(leftButton, rightButton, fileButton, fileLabel);
	    		 d.start();
	    	 }
	        }
	     });
	     JButton backButton = new JButton("Back");
	     backButton.setBounds(250,300,200,40);
	     backButton.setFont(fButton);
	     backButton.setBackground(Color.red);
	     panel.add(backButton);
	     backButton.addActionListener(new ActionListener(){
	     public void actionPerformed(ActionEvent e) {
	    	 		frame.dispose();
	        	}
	     });
	        
	}
	public static void playWav(byte[] bt){
        try {
            if(soundLine != null)
            {
                soundLine.close();
            }
            soundLine = (SourceDataLine) AudioSystem.getLine(info);
            soundLine.open(audioFormat);
            soundLine.start();
        }catch (LineUnavailableException e){
            e.printStackTrace();
        }
        soundLine.write(bt, 0, bt.length);
    }
	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		new SoundEffect();
	}
}
