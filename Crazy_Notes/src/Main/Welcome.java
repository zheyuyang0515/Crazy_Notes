package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel; 
public class Welcome {
    
    public static void main(String[] args) {    
        JFrame frame = new JFrame("Crazy Note");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int w = 700;
        int h = 400;
        frame.setSize(w, h);
        frame.setLocation(screenSize.width/2 - w/2, screenSize.height/2 - h/2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        JPanel panel = new JPanel();    
        panel.setBackground(Color.YELLOW);
        frame.add(panel);
        placeComponents(frame, panel);
        frame.setUndecorated(true);
        frame.setVisible(true);
        
    }

    private static void placeComponents(JFrame frame, JPanel panel) {

        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Crazy Notes",JLabel.CENTER);
        titleLabel.setBounds(0,75,700,150);
        Font fTitle = new Font("微软雅黑",Font.BOLD,32); 

        titleLabel.setFont(fTitle);
        panel.add(titleLabel);

        JLabel chooseTitleLable = new JLabel("Start enjoying your own music",JLabel.CENTER);
        chooseTitleLable.setBounds(0, 150, 700, 125);
        Font fChooseTitle = new Font("微软雅黑",Font.PLAIN,20); 
        chooseTitleLable.setFont(fChooseTitle);
        chooseTitleLable.setForeground(Color.RED);
        panel.add(chooseTitleLable);
        JButton pianoButton = new JButton("Start");
        pianoButton.setBounds(200, 265, 100, 50);
        Font fButton = new Font("微软雅黑",Font.BOLD,20); 
        pianoButton.setFont(fButton);
        pianoButton.setBackground(Color.ORANGE);
        panel.add(pianoButton);
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(400, 265, 100, 50);
        exitButton.setFont(fButton);
        exitButton.setBackground(Color.red);
        panel.add(exitButton);
        exitButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        	System.exit(0);
        	}
        });
        pianoButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        	frame.dispose();
        	 final PianoFunc paino = new PianoFunc();
             paino.open();
             JFrame f = new JFrame("Piano");
             f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
             f.setUndecorated(true);
             f.getContentPane().add("Center", paino);
             f.pack();
             Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
             int w = 1000;
             int h = 400;
             f.setLocation(screenSize.width/2 - w/2, screenSize.height/2 - h/2);
             f.setSize(w, h);
             f.setVisible(true);
        	}

        	});
    }

}