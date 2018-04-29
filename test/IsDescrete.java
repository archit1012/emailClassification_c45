package test;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

/*  Following code will check which column in dataset is Continues (return 0) & which is Descrete (return 1)      */
public class IsDescrete
{
String str_file = null , delim = ":";
String []temp=null ;
int i=0;

    public void isdescrete(String filename_name,LinkedList isdescrete_index[])
    {
        File file = new File(filename_name);
        FileInputStream     fis = null;
        BufferedInputStream bis = null;
        DataInputStream     dis = null;
          
        try {
              fis = new FileInputStream(file);               
              bis = new BufferedInputStream(fis);       // Here BufferedInputStream is added for fast reading.
              dis = new DataInputStream(bis);

      // dis.available() returns 0 if the file does not have more lines.
              while (dis.available() != 0)
              {
                str_file=dis.readLine();   //read file line by line & store it in string form
                temp=str_file.split(delim);//seperate with ','  store in temp string array
                //System.out.println("temp"+temp[0]+" "+temp[1]);
                isdescrete_index[i] =new LinkedList();
                if(temp[1].equals("continuous"))                                    
                    isdescrete_index[i].addFirst(0);  //0 indicate attribute is continuous call sorting                
                else               
                    isdescrete_index[i].addFirst(1);  //1 indicate attribute is descrete               
               // System.out.println(" column_index="+isdescrete_index[i]);
                i++;
              }
              System.out.println(" isdescrete over i="+i);      //TILL THIS PROPER

                // dispose all the resources after using them.
              fis.close();
              bis.close();
              dis.close();
            }   //try ends

        catch (FileNotFoundException e)
        {
            System.out.println(e);
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
    }   //function ends
}   //is descrete class ends
