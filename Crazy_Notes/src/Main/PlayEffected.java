package Main;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;

import Functional.Coordinators;
import Functional.Hrtf;
import Functional.SoundProcess;

public class PlayEffected extends Thread {
	JButton leftButton, rightButton, fileButton;
	JLabel fileLabel;
	static boolean isLeft = true;
	static double x = -10;
    static double y = 0;
    static double indexX = 1, indexY = -1;
	public PlayEffected(JButton leftButton, JButton rightButton, JButton fileButton, JLabel fileLabel) {
		// TODO Auto-generated constructor stub
		this.leftButton = leftButton;
		this.rightButton = rightButton;
		this.fileLabel = fileLabel;
		this.fileButton = fileButton;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			String command = "timidity " + SoundEffect.filePath + " -Ow -o out.wav";
   		
			Process p = Runtime.getRuntime().exec(command);
			p.waitFor();
			if (p.exitValue() != 0) {
				System.err.println("Error when converting file format");
			}
			//HrtfHandler s = new HrtfHandler();
			Hrtf h = new Hrtf(0);
			h.reload();
		    SoundProcess sound = new SoundProcess(44100 * 4, new File("out.wav"), h);
		   	do{
	    		if(x == -10) {
	    			indexX = 1;
	    		} else if(x == 10) {
	    			indexX = -1;
	    		}
	    		if(y == -10) {
	    			indexY = 1;
	    		} else if(y == 10) {
	    			indexY = -1;
	    		}
	    		if(isLeft) {
		    		x += indexX;
	    		} else {
	    			x -= indexX;		    		
	    		}
	    		y += indexY;
	    		Coordinators coor2 = new Coordinators(x, y);
	    		//coor2.setOrigin(new Coordinators(0, 0).degree);
		    	sound.changeSoundDirection(coor2.getRealDegree());
	    	} while(sound.play());	 
	    	leftButton.setEnabled(false);
	    	rightButton.setEnabled(false);
	    	fileButton.setEnabled(true); 
	    	fileLabel.setText("");
   		 	fileButton.setText("Choose a File"); 
   		 	sound.closeConnection();
   		 	return;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}


}
