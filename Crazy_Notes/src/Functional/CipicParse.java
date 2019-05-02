package Functional;

import org.nd4j.linalg.api.buffer.DataBuffer;
import org.nd4j.linalg.api.buffer.DoubleBuffer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.NDArrayIndex;

import java.io.*;
public class CipicParse
{


    public INDArray readHrir_l() throws IOException
    {
    	
        String fileName = "res/hrtfs/58_hrir_l.csv";
        BufferedReader b = new BufferedReader(new FileReader(new File(fileName)));

        INDArray hrir_l = readHrir(b);

        b.close();

        return hrir_l;
    }

    public INDArray readHrir_r() throws IOException
    {

        String fileName = "res/hrtfs/58_hrir_r.csv";
        BufferedReader b = new BufferedReader(new FileReader(new File(fileName)));

        INDArray hrir_r = readHrir(b);

        b.close();

        return hrir_r;
    }


    private INDArray readHrir(BufferedReader b)
    {
        Nd4j.setDataType(DataBuffer.Type.DOUBLE);
        INDArray hrir_l = Nd4j.create(25, 50, 200);
        INDArray tempRow = Nd4j.create(50);
        DoubleBuffer buffer = new DoubleBuffer(50);
        String line = "";

        int row = 0, column = 0, channel = 0;
        try
        {
            while((line = b.readLine()) != null)
            {
                String[] dataPoints = line.split(",");
                for (String hrtf_data: dataPoints)
                {
                    if(channel == 200)
                    {
                        if(++row == 25)
                        {
                           break;
                        }
                        channel = 0;
                    }

                    if (column == 49)
                    {
                        buffer.put(column, Double.valueOf(hrtf_data));
                        tempRow = Nd4j.create(buffer);

                        hrir_l.get(NDArrayIndex.point(row), NDArrayIndex.all(), NDArrayIndex.point(channel)).assign(tempRow);

                        buffer.flush();

                        column = 0;
                        channel++;
                    }
                    else
                    {
                        buffer.put(column, Double.valueOf(hrtf_data));
                        column++;
                    }
                }

            }

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return hrir_l;
    }

    public INDArray readITD() throws IOException
    {
        String fileName = "res/hrtfs/58_ITD.csv";
        BufferedReader b = new BufferedReader(new FileReader(new File(fileName)));
        INDArray itdArray = readItd(b);
        b.close();
        return itdArray;
    }

    private INDArray readItd(BufferedReader b)
    {
        Nd4j.setDataType(DataBuffer.Type.DOUBLE);
        INDArray itdArray = Nd4j.create(25, 50);
        INDArray tempRow = Nd4j.create(50);
        DoubleBuffer buffer = new DoubleBuffer(50);
        int row = 0, column = 0;
        try
        {
            String line = "";
            while((line = b.readLine()) != null)
            {
                String[] itds = line.split(",");
                for (String delayValue: itds)
                {
                    if (column == 49)
                    {
                        buffer.put(column, Double.valueOf(delayValue));
                        tempRow = Nd4j.create(buffer);

                        itdArray.get(NDArrayIndex.point(row), NDArrayIndex.all()).assign(tempRow);
                        buffer.flush();

                        column = 0;
                        row++;
                    }
                    else
                    {
                        buffer.put(column, Double.valueOf(delayValue));
                        column++;
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return itdArray;
    }

    public INDArray readOnLeft() throws IOException
    {
        String fileName = "res/hrtfs/58_OnL.csv";
        BufferedReader b = new BufferedReader(new FileReader(new File(fileName)));
        INDArray OnL = readItd(b);
        b.close();
        return OnL;
    }

    public INDArray readOnRight() throws IOException
    {
        String fileName = "res/hrtfs/58_OnR.csv";
        BufferedReader b = new BufferedReader(new FileReader(new File(fileName)));
        INDArray OnR = readItd(b);
        b.close();
        return OnR;
    }
}
