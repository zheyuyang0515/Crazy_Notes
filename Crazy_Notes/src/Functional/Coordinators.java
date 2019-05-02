package Functional;

public class Coordinators
{
	public double x;
	public double y;
	public double degree;
	public Coordinators(double x, double y)
	{
		this.x = x;
		this.y = y;
		degree = Math.toDegrees(Math.atan2(x,y));
		/*degree = Math.atan(y / x) / 3.14 * 180;*/
	}

	public double getRealDegree()
	{
		if(degree > 90)
		{
			return (180 - degree);
		}
		else if(degree < -90)
		{
			return (-degree - 180);
		}
		else
		{
			return degree;
		}
	}
}
