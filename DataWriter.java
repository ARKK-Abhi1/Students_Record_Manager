import java.io.*;

class DataWriter
{   // declaration of instance variables
    private File f;
    private FileWriter fW;
    private BufferedWriter bW;
    private PrintWriter pW;
    private String fileName;
    DataWriter(String fileName,boolean append) throws FileNotFoundException,IOException
    {   //initialization of instance variables
        this.fileName=fileName;
        f=new File(fileName);
        fW=new FileWriter(f,append); //create a File
    }
  
 
    boolean store(String data,boolean append)
    {  //method to store data passed as argument in the file
        try
        {
            if(data!=null)
            {
                fW=new FileWriter(this.fileName,append);
                bW=new BufferedWriter(fW);
                pW=new PrintWriter(bW);
                pW.println(data);
                pW.close();
                return true;
            }
        }catch(Exception e)
        { System.out.println("store(String) : "+e.getMessage()); }
        return false;
    }
}
 