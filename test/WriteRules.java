package test;

import java.io.*;

public class WriteRules {
static int count=0;
static int gloabal_write_cnt=0;
    public void writerules(TreeStruct root,int countnode)
    {
       // System.out.println("-------------    "+countrules); work properly...
        float [][] path=new float[400][2];
        try
        {
            FileWriter fstream = new FileWriter("E:\\SpamRule2.txt");
            BufferedWriter out = new BufferedWriter(fstream);
            printpathrecur(root,path,0,out,0);
            out.close();
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e);
        }
        //System.out.println("gloabal_write_cnt---> "+ gloabal_write_cnt);
    }

  public void printpathrecur(TreeStruct root,float path[][],int pathlen,BufferedWriter out,int dir) throws IOException
    {
        if(root==null)
        {
            return;
        }

        path[pathlen][0]=root.max_column;
        path[pathlen][1]=root.split_value;

        pathlen++;
        if(root.right==null && root.left==null)
        {
            printarray(path,pathlen,out,dir);

            out.write(Integer.toString((Integer)EntropyAttribute.spam_value.get(count)));
            count++;
            out.newLine();
            return;
        }
        else
        {
            printpathrecur(root.left,path,pathlen,out,1);
            printpathrecur(root.right,path,pathlen,out,2);
        }
        
      }

    public void printarray(float path[][],int len,BufferedWriter out,int dir)
    {
        int i;
        try
        {
            for(i=0;i<len-1;i++)
            {
                out.write(Integer.toString((int)path[i][0]));  //column no

                if(dir==1)  //call by left add <= SIGN
                {   out.write("<=");    }
                else if(dir==2)
                {   out.write(">");     }
                    
                out.write(Float.toString(path[i][1]));  //spliting info
                gloabal_write_cnt++;
                
                out.write(",");
             }

        }
        catch(Exception e)
        {
            System.out.println(" message "+e);
        }
        System.out.println();
    }
}