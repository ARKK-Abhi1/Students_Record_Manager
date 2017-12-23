import java.io.*;
import java.util.*;

class DataModifier
{  //declaration of instance variables
    private Input input;
    private DataReader dR;
    private DataWriter dW;
    private String fileName,presentRecord,changedrN;/*variable to 
    store modified roll no. in case original is modified */
    int count;
    DataModifier(String fileName) throws FileNotFoundException,IOException
    {   //initalizing instance variables
        presentRecord=changedrN=null;
        this.fileName=fileName;
        input=new Input();
        dW=new DataWriter(this.fileName,true);
        dR=new DataReader(this.fileName);
    }
 
    public String deleteRecord(String className,String section,String rollNo,boolean shift)
    {   //method to delete the record of student with the identity 
        String temp="",deleted=null,deletedrN=null,deletedcN=null,tempcN="",temprN="",tempsc="";   //passed as argument
        if((dR.exists(className,section,rollNo))!=null)
        {//continue if record exists
            try
            { 
                dR.reset(); //reset the fileScanner
                while((presentRecord=dR.getRecord())!=null)
                {count++;
                    dR.setrecordScanner(presentRecord,"!,!");
                    boolean b1=(tempcN=dR.getField()).equals(className);
                    boolean b2=(tempsc=dR.getField()).equals(section);
                    boolean b3=(temprN=dR.getField()).equals(rollNo);
                    if(!(b1&b2&b3))  
                    temp+=presentRecord+"\n";
                    else 
                    {
                        deleted=presentRecord;
                        deletedrN=dR.getField(deleted,"rollNo");
                    }
                }
                dR.reset();
                if(shift)
                {
 
                    Scanner s=new Scanner(temp);
                    String temprec="",storetemp="";
                    if(deletedrN!=null)
                    {
                        while(s.hasNextLine())
                        {count++;
                            presentRecord=s.nextLine();
                            dR.setrecordScanner(presentRecord,"!,!");
                            if((dR.getField(presentRecord,"class")).equals(className))
                            {
                                if((dR.getField(presentRecord,"section")).equals(section))
                                {
                                    temprN=dR.getField(presentRecord,"rollNo");
                                    temprec=presentRecord;
                                    if((Integer.parseInt(temprN))>(Integer.parseInt(deletedrN)))
                                    {
                                        temprec="";
                                        dR.setrecordScanner(presentRecord,"!,!");
                                        String tempfld="";
                                        int i=1;
                                        while((tempfld=dR.getField())!=null)
                                        {
                                            if(i==3)
                                            {
                                                int rN=Integer.parseInt(temprN);
                                                rN--;
                                                temprec+=Integer.toString(rN)+"!,!";
                                            }
                                            else
                                            temprec+=tempfld+"!,!";
                                            i++;
                                        }
                                    }storetemp+=temprec+"\n";
                                }
                                else storetemp+=presentRecord+"\n";
                            }
                            else storetemp+=presentRecord+"\n";
                        }temp=storetemp;
                    }
                }
                temp=temp.trim();
                boolean stored=false;
                dW=new DataWriter(fileName,false);
                if(temp!="")
                stored=dW.store(temp,false); 
                else stored=true; 
                if(stored) return deleted;
            }
            catch(FileNotFoundException fnfE)
            {System.out.println("Exception in deleteRecord() : File not Found!"); }
            catch(NullPointerException npE)
            { System.out.println("Exception in deleteRecord()  : Scanner not Set!"); }
            catch(IOException ioE)
            {System.out.println("Exception in deleteRecord() "+ioE.getMessage()); }
        }
        return null;//return null if record is not available
    } 
 
    boolean setField(String className,String section,String rollNo,String field,String value) throws FileNotFoundException,IOException
    { // set the Field passed as argument with the passed value
        int fieldIndex=dR.getFieldIndex(field);
        changedrN=rollNo;
        if(fieldIndex!=-1)
        {
            boolean b=false;
            if(fieldIndex==3)  b=true;
            if((presentRecord=deleteRecord(className,section,rollNo,b))!=null)
            {
                String modifiedRecord="";
                String tempField="";
                dR.setrecordScanner(presentRecord,"!,!");
                for(int i=0;i<dR.getRecordLength();i++)
                {count++;
                    tempField=dR.getField();
                    if(i==2&fieldIndex==3) {
                        changedrN=generateRollNo(className,section,value);
                        modifiedRecord+=changedrN+"!,!";
                        dR.iterateRecord(presentRecord,3);/*bring back pointer of record Scanner to 3
                        in case it is changed by getRollNo */
                    }
                    else if(i==fieldIndex)//seperating the required  record 
                    modifiedRecord+=value+"!,!";//from the file
                    else 
                    modifiedRecord+=tempField+"!,!";
                }
                String fileContent=dR.getfileContent();
                fileContent+=modifiedRecord; 
                try{ dR.reset(); }catch(Exception e){} 
                dW=new DataWriter(fileName,false);
                dW.store(fileContent,false);//writing the mofified data
                return true;                //to the file
            }
            System.out.println("\nRECORD UN-AVAILABLE\n");
        }
        System.out.println("\nINVALID FIELD\n");
        return false;
    }
    
    String generateRollNo(String className,String section,String name)
    {
        try
        {
            String tempName="",temprN,shiftedrN;
            int[] rollNumbers=dR.getRollNumbers(className,section);
            for(int i=rollNumbers.length-1;i>=0;i--)
            {
                temprN=Integer.toString(rollNumbers[i]);
                presentRecord=dR.getRecord(className,section,temprN);
                if(presentRecord!=null)
                {
                    tempName=dR.getField(presentRecord,"name");
                    if(tempName!=null)
                    {
                        if(name.compareToIgnoreCase(tempName)<0)
                        {
                            shiftedrN=Integer.toString(rollNumbers[i]+1);
                            setField(className,section,temprN,"rollNo",shiftedrN);
                            continue;
                        }
                        else
                        return Integer.toString(rollNumbers[i]+1);
                    }
                }
            }
        }catch(Exception e){System.out.println(e);}
        return "1";
    }
    String getchangedrN()
    { return changedrN; }
            
}
 
 
 

