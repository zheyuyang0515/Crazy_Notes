package Functional;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class SoundProcess
{
	static SoundProcess s = null;
	private int size;
	
	private SourceDataLine soundLine;
	private File file;
	private AudioInputStream audioInputStream;
	public byte[] header;
	public Hrtf hr;
	public AudioFormat audioFormat;
	public DataLine.Info info;
	public SoundProcess(int bufferSize, File file, Hrtf hr)
	{
		this.hr = hr;
		size = bufferSize;
		this.file = file;
		try
		{
			openConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void openConnection() throws Exception
	{
		audioInputStream = AudioSystem.getAudioInputStream(file);
		audioFormat = audioInputStream.getFormat();
		info = new DataLine.Info(SourceDataLine.class, audioFormat);
		if(soundLine != null)
		{
			soundLine.close();
		}
		soundLine = (SourceDataLine) AudioSystem.getLine(info);
		soundLine.open(audioFormat); 
		soundLine.start();
	}


	public void closeConnection() {
		soundLine.close();
		try {
			audioInputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean play() throws IOException
	{
		int result = 0;
		byte[] sampledData = new byte[size];
		try
		{
			result = audioInputStream.read(sampledData, 0, sampledData.length);			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		if (result > 0)
		{
			byte[] convoledData = applyHrtf(sampledData);
			soundLine.write(convoledData, 0, convoledData.length);
			return true;
		}
		return false;
	}
	
	private static byte[] shortToByte(int s)
	{
		byte[] targets = new byte[2];
		targets[0] = (byte) (s >> 8 & 0xFF);
		targets[1] = (byte) (s & 0xFF);
		return targets;
	}
	
	public static byte[] doubleToByte(double[] input)
	{
		byte[] bytes = new byte[input.length*4];
		short[]  stArray = new short[input.length];
		for (int i = 0; i < input.length; i++)
		{
			stArray[i] = (short) (input[i] * 32768);
		}

		int count = 0;
		for (int i = 0; i < stArray.length; i++)
		{
			byte[] temp = {shortToByte(stArray[i])[1], shortToByte(stArray[i])[0]};
			bytes[count] = temp[0];
			bytes[count+1] = temp[1];
			bytes[count+2] = temp[0];
			bytes[count+3] = temp[1];
			count += 4;
		}
		return bytes;
	}
	
	public static double[] byteToDouble(byte[] input)
	{
		double[] doubles = new double[(input.length / 4)];
		ByteBuffer byteBuffer = ByteBuffer.wrap(input);
		int count = 0;

		while(byteBuffer.remaining() >= 2)
		{
			doubles[count++] = ((double)((byteBuffer.get() & 0xff) | (byteBuffer.get() << 8))) / 32768;
			byteBuffer.getShort();
		}
		return doubles;

	}
	
	private byte[] applyHrtf(byte[] data) 
	{
		double[] dataDoubles = byteToDouble(data);

		double[] convolvedLeft = Conv(dataDoubles, hr.hrir_l.data().asDouble());
		double[] convolvedRight = Conv(dataDoubles, hr.hrir_r.data().asDouble());
		ByteBuffer leftBuffer = ByteBuffer.wrap(doubleToByte(convolvedLeft));
		ByteBuffer rightBuffer = ByteBuffer.wrap(doubleToByte(convolvedRight));

		ByteBuffer combined = ByteBuffer.allocate(Math.max(leftBuffer.capacity(), rightBuffer.capacity()));
		while(leftBuffer.remaining() > 0 && rightBuffer.remaining() > 0) {
			combined.put(leftBuffer.get());
			combined.put(leftBuffer.get());
			combined.put(rightBuffer.get());
			combined.put(rightBuffer.get());
			leftBuffer.getShort();
			rightBuffer.getShort();
		}
		return combined.array();
	}


	public void changeSoundDirection(double angle)
	{
		hr.angle = angle;
		hr.reload();
	}
	//conv
	public double[] Conv(double[] input, double[] core) {
		int inputlength = input.length;
		int corelength = core.length;
		double [] convres = new  double[inputlength + corelength - 1];
		
		for(int i = 0; i < convres.length - 1; i++) {
			convres[i] = 0;
		}
		
		for(int i = 0; i < inputlength - 1; i++) {
			for(int j = 0; j < corelength - 1; j++) {
				convres[i + j] = convres[i + j] + input[i] * core[j];
			}
		}
		return convres;
	}
}
