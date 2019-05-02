package Functional;

import java.io.IOException;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.NDArrayIndex;

public class Hrtf
{
	public double angle;
	public INDArray hrir_l = Nd4j.create(1,200), hrir_r = Nd4j.create(1,200);
	private int delay;
	public INDArray hrirLeft, hrirRight, itd, onLeft, onRight;
	public static double[] angles = new double[]{-180, -175, -160, -145, -120, -105, -90, -75, -60, -45, -30, -15,
    		0, 15, 30, 45, 60, 75, 90, 105, 120, 145, 160, 175, 180};
	public Hrtf(double angle)
	{
		readFile();
		this.angle = angle;
	}


	public void reload()
	{
		int index = getIndex(angles);
		hrir_l = hrirLeft.get(NDArrayIndex.point(index), NDArrayIndex.point(0), NDArrayIndex.all()).transpose();
		hrir_r = hrirRight.get(NDArrayIndex.point(index), NDArrayIndex.point(0), NDArrayIndex.all()).transpose();
		delay = (int) Math.abs(Math.round(itd.getDouble(index,0)));

		if (angle > 135)
		{
			hrir_l = Nd4j.concat(1, hrir_l, Nd4j.zeros(delay));
			hrir_r = Nd4j.concat(1, Nd4j.zeros(delay), hrir_r);
		}
		else
		{
			hrir_r = Nd4j.concat(1, hrir_r, Nd4j.zeros(delay));
			hrir_l = Nd4j.concat(1, Nd4j.zeros(delay), hrir_l);
		}

	}

	private int getIndex(double[] angles)
	{
		int index = 0;
		double difference = Double.MAX_VALUE;
		
		for (int i = 0; i < angles.length; i++)
		{
			if(difference >= Math.abs(angles[i] - angle)) {
				difference =  Math.abs(angles[i] - angle);
			} else {
				index = i;
				break;
			}
		}
		return index;
	}
	private void readFile() {
		 CipicParse c = new CipicParse();
	        try {
				hrirLeft = c.readHrir_l();
				hrirRight = c.readHrir_r();
		        itd = c.readITD();
		        onLeft = c.readOnLeft();
		        onRight = c.readOnRight();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
