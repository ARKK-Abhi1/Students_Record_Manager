import java.io.*;
import java.util.*;
class Test
{
 public static void main(String[] args)throws Exception
 { 
  String s="ak\n";
  System.out.println(s+"good");
  s=s.trim();
  System.out.println(s+"god");
  DataWriter dW=new DataWriter("Test.txt",true);

  
    String s0="a";
  String s1="g";
  String s2="";
  for(int i=0;i<4;i++)
   s0+=s1+"\n";
  dW.store(s0,true);
  System.out.println(s0);
  boolean b1=true;
  boolean b2=true;
  boolean b3=true;
  System.out.println(b1&b2&b3);
  String srte="";
  if(srte==" ") System.out.println(" fgfgf");
  for(int i=0;i<=65000;i++)
  System.out.println(i+" : "+(char)i);
  String strg="11",strg2="2";
  System.out.println(strg.compareTo(strg2));
  Scanner fle=new Scanner(new File("Students' Record.txt"));
  PrintWriter pW=new PrintWriter(new BufferedWriter(new FileWriter("Students' Record.arkk",true)));
  while(fle.hasNextLine())
  {
      pW.println(fle.nextLine());
    }
    pW.close();
 }
}

  