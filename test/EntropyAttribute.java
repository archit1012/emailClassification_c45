package test;
import java.util.*;
import java.sql.*;
import java.sql.Driver;


public class EntropyAttribute {
static LinkedList spam_value=new LinkedList();      // IT ALSO COUNTS NO OF RULES
static int countnode=0;//total node count

    public int findentropy(LinkedList<Mylinklist> []dataset_link,LinkedList isdescrete_index[],int start_entity,int end_entity,TreeStruct current_node )
    {      
    int i=0,j=0,spam_no=0,spam_yes=0,total=0,ret,temp=0,flag=0;
    float dataset_entropy=0;
    //System.out.println("ENTROPY ATTRIBUTE start_entity -> "+start_entity + " end_entity - > "+ end_entity);
    EntropyAttribute.countnode++;

    for(i=start_entity;i<end_entity;i++)//check for continuous 1 or 0
    {
        if((int)dataset_link[i].get(57).data==(int)dataset_link[start_entity].get(57).data)
        {
            temp=(int)dataset_link[start_entity].get(57).data;
            flag=1;
        }
        else
        {
            flag=0;
            break;
        }
    }

    //-----------  BASE CASE -------------------------//
    if(flag==1)    //DECIDE BASE CASE
    { //NO COLUMN REMAINS OR NO ROWS OR WE GET THE RULE   
        //System.out.println("----BASE CASE ----lstemp.data->"+temp);
        spam_value.add(temp);   //(REMAINING)CHECK AFTER GENERATING RULE TREE TRAVERSAL IS PROPER
        //System.out.println(" ----CURRENT NODE IN BASE CASE---- START_ENTITY " +current_node.start_enitiy+" END_ENTITY "+current_node.end_entity+"  "+current_node.max_column+" --> "+current_node.split_value);
            return 0;
    }
    else
    {
    //FINDING ENTROPY OF WHOLE DATASET
    for(i=start_entity;i<end_entity;i++)
    {
        if( (int) dataset_link[i].get(dataset_link[start_entity].size()-1).data==0 )
                spam_no++;
        else
                spam_yes++;
    }
    total = spam_no + spam_yes;
    
    if(spam_no==0)        //WE GET THE RULE HERE THAT "ALL ARE SPAM" -->which column to select in rule?
    {
        dataset_entropy = 0.0f;
        //System.out.println("hi spamno=0");
    }
    else if(spam_yes==0) 
    {
        dataset_entropy = 0.0f;
        //System.out.println("hi spamyes=0");
    }
    else
        dataset_entropy = -((float)(((float)spam_yes /(float) total) * (Math.log((float)spam_yes /(float)total) / Math.log(2))) +
                            (float)(((float)spam_no  /(float) total) * (Math.log((float)spam_no  /(float)total) / Math.log(2))));
        
    
    //System.out.println("spam_no         -> "+spam_no+"spam_yes        -> "+spam_yes);
   // System.out.println("DATASET_ENTROPY -> "+dataset_entropy);

    CreateRules CreateRules_object=new CreateRules();
    ret=CreateRules_object.createrulse(dataset_link,isdescrete_index,dataset_entropy,start_entity,end_entity,current_node);

    }
    return 0;
    }
}