//IMPLEMENTATION OF C4.5

package test;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.sql.*;


public class Main {

static TreeStruct root,current_node;

    public static void main(String[] args)  throws IOException{

//MAIN CODE READING FROM .DATA FILE & LOADING DATA IN 2D ARRAY    
    File file = new File("E:\\training_dataset.data");     //training_dataset
    FileInputStream     fis = null,fis_test=null;
    BufferedInputStream bis = null,bis_test=null;
    DataInputStream     dis = null,dis_test=null;

    String str_file=null , temp[]=null , delim=",";
    int i=1,j=0,k=0 , noof_attribute=0,start_entity=0,end_entity=0;
    float [][]dataset=null;

    try {
        fis_test = new FileInputStream(file);
        bis_test = new BufferedInputStream(fis_test);// Here BufferedInputStream is added for fast reading.
        dis_test = new DataInputStream(bis_test);

        str_file=dis_test.readLine();
        temp=str_file.split(delim);
        noof_attribute=temp.length; //WILL BE 0 TO 57

        while(dis_test.available()!=0)
        {
            dis_test.readLine();
            i++;         
        }
        j=i;
        end_entity=i;
        dataset = new float[j][noof_attribute];  //DYNAMICALLY ARRAY DECLARATION -->> i=row  temp.length=cloumn
        System.out.println("row:"+j+"column:"+noof_attribute);
        
      fis_test.close();
      bis_test.close();
      dis_test.close();

        // dis.available() RETURNS 0 IF FILE DOES NOT HAVE MORE LINES
        fis = new FileInputStream(file);
        bis = new BufferedInputStream(fis);     
        dis = new DataInputStream(bis);

       for(i=0;i<j;i++)        // while (dis.available() != 0)
        {
            str_file=dis.readLine();        //READ FILE LINE BY LINE & STORE IT IN STRING FORM
            temp=str_file.split(delim);     //SEPERATE WITH ',' STORE IN TEMP STRING ARRAY

            for(k=0;k<noof_attribute;k++)  //k=column i=row
            {
                dataset[i][k]=Float.parseFloat(temp[k]);    //CONVERTING STRING TO FLOAT
               // System.out.print(dataset[i][k]+",");
            }            
           // System.out.println(" ");
        }              
      fis.close();      // dispose all the resources after using them.
      bis.close();
      dis.close();
    }

    catch (FileNotFoundException e)
    {
      System.out.println(e);
    }
    catch (IOException e)
    {    
     System.out.println(e);
    }

    //DATASET TO LINKED LIST CONVERSION
   LinkedList<Mylinklist> []dataset_link=new LinkedList[end_entity];

    for(i=0;i<end_entity;i++)  //CONVERT 2D ARRAY IN TO LINKLIST FORM
    {
        dataset_link[i]=new LinkedList<Mylinklist>();
        for(j=0;j<noof_attribute;j++)
        {
            Mylinklist mylinklist_object = new Mylinklist(dataset[i][j], j);
            dataset_link[i].add(mylinklist_object);
        }
    }

  
    /*for(i=0;i<end_entity;i++)
    {   Mylinklist lstemp;
        Iterator iterator=dataset_link[i].listIterator();
        while(iterator.hasNext())
        {
            lstemp=(Mylinklist)iterator.next();
            System.out.print(lstemp.data+" "+lstemp.attribute_no+" -> ");
        }
        System.out.println();
    } */    //WORKING PROPER

   //-------------  CHECK ATTRIBUTE IS DESCRETE OR NOT -------------------
   LinkedList []isdescrete_index =new LinkedList[noof_attribute-1];
    
   IsDescrete IsDescrete_object=new IsDescrete();
   IsDescrete_object.isdescrete("E:\\spambase.names",isdescrete_index);//WORKING PROPERLY isdescrete_index

   int max_column=-1,max_entity=-1;
   float max_gainvalue=-1f,split_value=-1f;
   root=new TreeStruct(start_entity, end_entity , max_column , max_entity , max_gainvalue , split_value);
   root.parent =root;
   current_node=root;

 EntropyAttribute EntropyAttribute_object=new EntropyAttribute() ;
 EntropyAttribute_object.findentropy(dataset_link,isdescrete_index,start_entity,end_entity,current_node);

 System.out.println("rule generated");
 //System.out.println("total node --> "+EntropyAttribute.countnode);
 //System.out.println("no of rules "+EntropyAttribute.spam_value.size());
 System.out.println("root node  split"+root.split_value+"  column -> "+root.max_column);

WriteRules WriteRules_object=new WriteRules();
WriteRules_object.writerules(root,EntropyAttribute.countnode);
 
    }
}