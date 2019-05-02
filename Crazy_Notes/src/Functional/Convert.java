package Functional;

import java.nio.ByteBuffer;

public class Convert
{
	/*public static double[] byteToDouble(byte[] input)
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

	}*/
	/*public static byte[] doubleToByte(double[] input)
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

	private static byte[] shortToByte(int s)
	{
		byte[] targets = new byte[2];
		targets[0] = (byte) (s >> 8 & 0xFF);
		targets[1] = (byte) (s & 0xFF);
		return targets;
	}*/
}
