import java.io.*;
import java.util.*;

class DataReader
{
    private Scanner fileScanner,recordScanner;//declaration of instance variable
    private String fileName,record;          
    private int recordLength=9;
    int count;
    DataReader(String fileName) throws FileNotFoundException,IOException
    {            //initalization of values
        this.fileName=fileName;
        record=null;
        fileScanner=new Scanner(new File(fileName));
        recordScanner=null;
    }

    public String exists(String className,String section,String rollNo)
    {             //method to check if a record is available
        String temp=null;
        try
        {
            reset(); //reset the Scanners
            while((record=getRecord())!=null)
            {count++;
                setrecordScanner(record,"!,!");
                if( (getField()).equals(className))//check if
                if( (getField()).equals(section) )//the record
                if( (getField()).equals(rollNo)) //is available
                temp=record;
            }
            reset();
        }catch(NullPointerException npE)
        { System.out.println("Exception in exists(String,String) : Scanner not Set!"); }
        catch(Exception e)
        { System.out.println("Exception in exists(String,String) : "+e.getMessage()); }
        return temp;// return the record if it exists otherwise null
    }


    public boolean printRecord(String className,String section,String rollNo)
    {          // print the Detais of the student with the provided identity
        try
        {
            if((record=exists(className,section,rollNo))!=null)
            {
                setrecordScanner(record,"!,!");
                for(int i=0;i<3;i++) getField(); //skip class Name, Section, roll No
                System.out.println("_______________________________________________");
                System.out.println("\n\tName          : "+getField());
                System.out.println("\tGender        : "+getField());
                System.out.println("\tMother's Name : "+getField());
                System.out.println("\tFather's Name : "+getField());
                System.out.println("\tAddress       : "+getField());
                System.out.println("\tPhone Number  : "+getField()+"\n"); 
                System.out.println("_______________________________________________");
                reset();
                return true;
            }
            else System.out.println("\nRECORD UNAVAILABLE\n");
        }catch(Exception e)
        { System.out.println("printRecord(String,String) : "+e.getMessage()); }
        return false;
        
    }
 

    public void reset() throws FileNotFoundException //to reset variables to
    {                                         //  default values
        fileScanner=new Scanner(new File(fileName));
        recordScanner=null;
        record=null;
    }
 
    public String getRecord()
    {//get Record from the file, based on the fileScanner pointer
        if(fileScanner.hasNextLine()) return fileScanner.nextLine();
        return null;
    }
 
    public void setrecordScanner(String source,String delimiter)
    {//set recordScanner to the specified record along with delimiter
        recordScanner=new Scanner(source);
        recordScanner.useDelimiter(delimiter);
    }

    public String getField() throws NullPointerException
    { //get field of a record, based on the recordScanner pointer
        if(recordScanner.hasNext())
        return (recordScanner.next());
        return null;
    }
                   
 
    String iterateRecord(String record,int n) throws NullPointerException
    { //set the record pointer according to the value of n
        setrecordScanner(record,"!,!");
        String field=null;
        for(int i=0;i<n;i++){ count++;
        field=recordScanner.next();}
        return field;
    }
 
 
    String getField(String record,String field)
    { //overloaded method to return "field" from the "record"
        setrecordScanner(record,"!,!");
        char f=((field.trim()).toUpperCase()).charAt(0);
        switch(f)
        {
            case 'C' : return iterateRecord(record,1);
            case 'S' : return iterateRecord(record,2);
            case 'R' : return iterateRecord(record,3);
            case 'N' : return iterateRecord(record,4);
            case 'G' : return iterateRecord(record,5);
            case 'M' : return iterateRecord(record,6);
            case 'F' : return iterateRecord(record,7);
            case 'A' : return iterateRecord(record,8);
            case 'P' : return iterateRecord(record,9);
            default :  return null;
        }
    }

    String getfileContent()
    { //function to return the content of the file
        String store="";
        try{ reset(); }catch(Exception e){}
        while((record=getRecord())!=null) store+=record+"\n";
        try{ reset(); }catch(Exception e){}
        return store;
    }
 

    public String getRecord(String className,String section,String rollNo)
    {//overloader method to return a record of the student with the passed arguments
        if((record=exists(className,section,rollNo))!=null)
        {  return record;  }
        return null;
    }  

    int getRecordLength()
    {//method to return the no of fields in the record
        return recordLength;
    }

    int getFieldIndex(String field)
    { //method to return the index of the field passed as argument
        char f=((field.trim()).toUpperCase()).charAt(0);
        switch(f)
        { // return the index according to the argument
            case 'C' : return 0;
            case 'S' : return 1;
            case 'R' : return 2;
            case 'N' : return 3;
            case 'G' : return 4;
            case 'M' : return 5;
            case 'F' : return 6;
            case 'A' : return 7;
            case 'P' : return 8;
            default  : return -1;
        }
    } 
  
    void printAll(String className,String section) throws FileNotFoundException
    { //method to print the details of all students of the class & section passd
        int[] rNAr=getRollNumbers(className,section);
        if(rNAr.length!=0)
        {
            System.out.println("\n                CLASS   - "+className);
            System.out.println("                SECTION - "+section+"\n");
            System.out.println("           ROLL NUMBER         NAME");
            System.out.println("___________________________________________________________\n");
            for(int i=0;i<rNAr.length;i++)  //call the printRecord() method
            {
                System.out.print("               "+rNAr[i]);  
                record=getRecord(className,section,Integer.toString(rNAr[i]));
                System.out.println("               "+getField(record,"name")+"\n");
            }
            System.out.println("___________________________________________________________");
        }
        else System.out.println("\nRECORD OF CLASS "+className+" "+section+" NOT AVAILABLE");    
        reset();
    }
    
    void closeFileScanner()
    {
        fileScanner.close();
    }
    
    int[] getRollNumbers(String className,String section)
    {
        try{ reset(); }catch(Exception e){ }
        String rN="";
        while(fileScanner.hasNextLine())
        {
            record=fileScanner.nextLine();
            setrecordScanner(record,"!,!");
            if((recordScanner.next()).equals(className))
            if((recordScanner.next()).equals(section)){
            rN+=recordScanner.next()+" "; //store required roll numbers in a string
        }
        }
        setrecordScanner(rN," ");
        StringTokenizer sT=new StringTokenizer(rN);
        int[] rNAr=new int[sT.countTokens()];
        for(int i=0;i<rNAr.length;i++) //retrieve the roll numbers and 
        rNAr[i]=recordScanner.nextInt(); //store them in an array
        rNAr=(new Sort()).bubbleSort(rNAr);// sort the Roll Numbers in ascending order
        try{ reset(); }catch(Exception e){}
        return rNAr;
    }
}

 

      
    