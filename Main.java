import java.io.*;
import java.util.Scanner;

class Main
{  //declaration of instance variables 
    private Input input;
    private DataReader dR;
    private  DataWriter dW;
    private DataModifier dM;
    private boolean end;
    private Scanner choice;
    Main(String fileName) throws Exception
    { //initializing instance Variables
        input=new Input();
        dW=new DataWriter(fileName,true);
        dR=new DataReader(fileName);
        dM=new DataModifier(fileName);
        end=false;
        choice=new Scanner(System.in);
    }


    void start() throws FileNotFoundException,IOException
    {
        //variable to control do=while loop if any mismatch occurs
        
       
            System.out.println("\n****STUDENTS' RECORD MANAGER****\n");
            System.out.println("'A'dd record\n'V'iew record\n'D'elete Record\n'M'odify Record"
            +"\n'P'rint Class Details\n'E'xit");//acceptiong user's 
            System.out.print("\nCHOOSE AN OPTION : ");//choice
            char ch=(input.firstChar(choice.next()));
            switch(ch)//using switch case to perform action of user's choice
            {
                case 'A' : //Add a record 
                do
                {
                    if(input.idInputAvailable(true,false))//accept identity
                    {                              //class,section,roll No.
                    
                        if(input.section!=null)//accept to the details
                        {                    // after accepting ID
                            if(!(input.acceptInfo())) 
                            {
                                System.out.println("\nTO ADD OTHER RECORDS");
                                continue;
                            }
                            input.rollNo=dM.generateRollNo(input.className,input.section,input.name);
                            dW.store(input.getRecord(),true);
                            System.out.println("\n RECORD SAVED\n ");
                        } 
                    }
                    else   break;       
                }while(input.choice());//ask user to continue or not
                break;
                
                case 'V' : //View a student's record
                do
                {
                    if(input.idInputAvailable(true,true))
                    { 
                        dR.printRecord(input.className,input.section,input.rollNo); 
                        System.out.println("\nDISPLAYING MORE RECORDS");
                    }
                    else break; 
                    
                }while(input.choice());  //ask user whether to continue or not          
                break;
                
                case 'D' : 
                do
                {
                    if(input.idInputAvailable(true,true))
                    {
                        if((dM.deleteRecord(input.className,input.section,input.rollNo,true))!=null)
                        System.out.println("\nRECORD DELETED\n");
                        else 
                        System.out.println("\nRECORD UNAVAILABLE\n");
                    }
                    else break;
                    
                    
                }while(input.choice());//ask user to continue or not
                break;
                
                case 'M' : //modify an existing record
                do
                {
                    if(input.idInputAvailable(true,true))
                    {
                        try
                        {
                            if(dR.printRecord(input.className,input.section,input.rollNo))
                            {//check if record exists
                                do
                                {
                                    System.out.println("\nENTER '<' TO GO BACK");
                                    String f,v;
                                    if((f=input.acceptField()).equals("<")) break;
                                    if((v=input.acceptFieldValue()).equals("<")) break;
                                    if( dM.setField(input.className,input.section,input.rollNo,
                                                f,v) )
                                    {
                                        System.out.println("\nFIELD VALUE CHANGED\n");
                                        System.out.println("--------MODIFIED RECORD--------");
                                        dR.printRecord(input.className,input.section,dM.getchangedrN());
                                    }
                                    else
                                    System.out.println("\nUN-ABLE TO CHANGE THE FIELD VALUE\n");
                                    System.out.print("CHANGING OTHER FIELDS\n");
                                }while(input.choice());
                            }
                        }catch(StringIndexOutOfBoundsException siobE)
                        { 
                            System.out.println("\nFIELD/VALUE INPUT NULL . PLEASE TRY AGAIN"); 
                            break;
                        }
                        System.out.println("\nMODIFYING OTHER RECORDS");
                    }
                    else break;
                    
                  }while(input.choice());//ask user to continue modifying other rcords or not
                break;
                
                case 'P' : //print all the record of a particular class' students
                do
                {
                    if(input.idInputAvailable(true,false))//accept class and section
                    dR.printAll(input.className,input.section);//print all the records
                    else break;
                    
                }while(input.choice());
                break;
                
                case 'E' : //exit from the application
                System.out.println("\n_____________________________________________________A");
                System.out.println("\nPROGRAMMER  : ABHISHEK BISHT[ARKK]");
                System.out.println("SCHOOL      : ST. THERESA'S SCHOOL, SRINAGAR(GARHWAL)");
                System.out.println("CLASS       : 11A");
                System.out.println("ROLL NUMBER : 03");
                System.out.println("______________________________________________________K");
                end=true; 
                break;
                
                default  : 
                System.out.println("\nINVALID CHOICE. PLEASE TRY AGAIN\n");

                break;
            }
        
    }
     
    public static void main(String[] args)// main method
    {
        try
        {
            Main m=new Main("Students' Record.arkk");//creating instance 
            do                                     //of Main class
            { 
                m.start();
            }while(!m.end);
            m.dR.closeFileScanner();//close the file Scanner
        }catch(Exception e)// catch any exception occurrence
        { System.out.println("Exception in main() method : "+e);}
    }
}

  
  