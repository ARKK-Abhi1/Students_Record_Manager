import java.util.*;

class Input
{
    private Scanner input;
    String className,rollNo,section,name,gender,mother,father,address,phNo;
    private String record;
    Input()//initializing values
    {
        input=new Scanner(System.in);
        className=rollNo=name=gender=mother=father=address=phNo=null;
        section=" ";
    }
    
    public boolean acceptID(boolean acceptSect,boolean acceptRn )
    {     // Accept identity for the student(class,section,rollNo)
        byte control=0;//variable to control do-while loop
        try
        {
            do
            {
                input=new Scanner(System.in);
                System.out.println("\nENTER 'M' to RETURN TO PREVIOUS MENU\n");
                System.out.print("Enter the class Name(in numeric form) : "); 
                if(input.hasNextInt()) //continue if user enters digits
                {
                    className=input.next();
                    if(acceptSect) //continue if section is required
                    {
                        System.out.print("Enter the Section                     : ");   
                        section=((input.next()).trim()).toUpperCase();
                        if(section.equals("M"))                         return false;//if user wants to return to previous menu
                        if(acceptRn)//continue if Roll Number is required
                        {
                            System.out.print("Enter the Roll Number                 : ");
                            if(input.hasNextInt()) 
                            {
                                rollNo=input.next();
                                input.nextLine();
                                return true;
                            }      
                            else //if user does not enters digits
                            {
                                if((firstChar(rollNo=input.next()))=='M')
                                { //check if user wants to return to previous menu
                                    input.nextLine();className="C"; return false; 
                                }
                                System.out.println("\nINVALID ROLL NUMBER. PLEASE TRY AGAIN\n"); 
                                control++; 
                                if(control<=5) continue;
                                else return false;
                            }
                        }
                        input.nextLine();
                        return true;
                    }
                    input.nextLine();
                    return true;
                }
                else
                {
                    if(firstChar(className=input.next())=='M')
                    { 
                        input.nextLine(); return false; 
                    }
                    else
                    System.out.println("\nINVALID CLASS NAME. PLEASE TRY AGAIN\n");
                    control++;//increase control by one to re-run while loop
                }//break if invalid input is entered more than six times
            }while(control>0&control<=5); 
        }catch(Exception e)//catch any exception
        { 
            System.out.println(e.getMessage());
        }
        return false;
    }


    public boolean acceptInfo()//accept the details of student from user
    {
        System.out.println("\nPLEASE ENTER THE FOLLOWING DETAILS");
        System.out.println("\nENTER '<' TO GO BACK TO\n");
        System.out.print("Name           : "); name=(input.nextLine()).trim();
        if(name.equals("<")) return false;
        System.out.print("Gender         : "); gender=(input.nextLine()).trim();
        if(gender.equals("<")) return false;
        System.out.print("Name of Mother : "); mother=(input.nextLine()).trim();
        if(mother.equals("<")) return false;
        System.out.print("Name of Father : "); father=(input.nextLine()).trim();
        if(father.equals("<")) return false;
        System.out.print("Address        : "); address=(input.nextLine()).trim();
        if(address.equals("<")) return false;
        System.out.print("Phone Number   : "); phNo=(input.nextLine()).trim();
        if(phNo.equals("<")) return false;
        return true;       
    }
    String getRecord()
    {
        String delimiter="!,!";
        record=className+delimiter+section+delimiter+rollNo+delimiter+name+delimiter+gender+delimiter+mother+delimiter+father
               +delimiter+address+delimiter+phNo+delimiter;
        return record;
    }


    public String acceptField()//to accept a field to be modified
    {
        System.out.println("\nAVAILABLE FIELDS TO MODIFY");
        System.out.println("NAME, MOTHER'S NAME, FATHER'S NAME, GENDER, ADDRESS, PHONE NUMBER");
        System.out.print("\nEnter the the field          : ");
        return input.nextLine();
    }
 
    public String acceptFieldValue()//to accept the value of the field
    {
        System.out.print("\nEnter the value of the field : ");
        return input.nextLine();
    } 
 

    boolean choice()//function to accept user's choice
    {
        boolean b=false;
        System.out.println("DO YOU WANT TO CONTINUE ?<Y/N> : ");
        char cont=(((input.next()).trim()).toUpperCase()).charAt(0); //Ask to continue
        if(cont=='Y') b=true;
        else if(cont=='N') b=false;
        else System.out.println("\nINVALID CHOICE");
        input.nextLine();
        return b;
    }
 
    char firstChar(String str)//returns the first charcter of 
    {                   // a String in UpperCase
        if(str!=null)
          return ((((str).trim()).toUpperCase()).charAt(0));
        return (char)32;
    }
    
    boolean idInputAvailable(boolean acceptSect,boolean acceptRn)
    {    //method to check if requested values are entered by the user. Returns true ore false
        if((acceptID(acceptSect,acceptRn))==false)
        {     
            if((firstChar(className)=='M')||section.equals("M")||((firstChar(rollNo))=='M'))           
            return false;
            else
            System.out.println("\nUN-EXPECTED INPUT\n"); 
            return false;
        }
        return true;
    }
}

     
  