public class Sort
{
    public int[] bubbleSort(int[] aint)
    {
        for(int i=aint.length-1;i>0;i--)
        for(int j=0;j<i;j++)
        { 
            int temp;
            if(aint[j+1]<aint[j])
            {
                temp=aint[j+1];
                aint[j+1]=aint[j];
                aint[j]=temp;
            }
        }
        return aint;
    }
}