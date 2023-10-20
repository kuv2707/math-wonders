import java.util.*;
import JGraph.BarGraph;//https://github.com/kuv2707/JGraph
class Probability_Distribution
{
    public static void main(String[] a)throws Exception
    {
        final int NUM_DICES=4;
        final int SCALE=100;
        double[] arr=new double[6*SCALE*NUM_DICES];
        double[] probabs=new double[25000];
        BarGraph bg=new BarGraph(arr,1);
        bg.show(true);
        int totalTrials=0;
        while(true)
        {
            double sum=0;
            for(int i=0;i<NUM_DICES;i++)
            {
                sum+=(6*Math.pow(Math.random(),1));
            }
            totalTrials++;
            sum=Math.floor(sum*SCALE);    //19.99995678*1000
            arr[(int)sum]++;
            probabs[(int)sum]=arr[(int)sum]/totalTrials;
            double mean=0;
            for(int i=0;i<arr.length;i++)
            {
                double d=arr[i];
                mean+=d*i;
            }
            mean/=totalTrials;
            bg.setKey("mean",(int)mean);
            bg.setKey("median",(int)arr.length/2);
            double probSum=0;
            for(int i=0; i<probabs.length; i++) {
                probSum += probabs[i];
            }
            // System.out.println(probSum);
        }
    }
}