package test;

import java.util.*;

public class CreateRules {

 //LinkedList rule=new LinkedList();

 public int createrulse(LinkedList<Mylinklist> []dataset_link,LinkedList []isdescrete_index,float dataset_entropy,int start_entity,int end_entity,TreeStruct current_node)
    {        
        int i=0,j=0,max_column=0,max_entity=0;
        float split_value=-1.1f,max_gainratio=-1.1f;
        Sort sort_object=new Sort();
        LinkedList []tempdata_link=new LinkedList[end_entity]; 

        for(j=start_entity;j<end_entity;j++)     //ROW ENTITY
        {    tempdata_link[j]=new LinkedList();                }
            
            Split.temp_max_gainratio=-1.1f;

            for(i=0;i<dataset_link[start_entity].size()-1;i++)     //COLUMN    ATTRIBUTE
            {   
                if((Integer)isdescrete_index[i].getFirst()==0)  //0 CONTINUES colume value..
                {
                    for(j=start_entity;j<end_entity;j++)     //ROW ENTITY
                    {  
                        tempdata_link[j].add(0,(float)dataset_link[j].get(i).data);
                        tempdata_link[j].add(1,(int)dataset_link[j].get(dataset_link[start_entity].size()-1).data);
                    }
                    //------------------ ATTRIBUTE IS CONTINUES CALL SORT ------------------------
                    sort_object.sort(tempdata_link,start_entity,end_entity); //DATA SORTED, NOW SPLIT
                    
                    Split split_object = new Split();
                    split_object.split(tempdata_link,dataset_entropy,start_entity,end_entity);

                    if(Split.temp_max_gainratio > max_gainratio)
                    {
                        max_column=i;                       //COLUMN WITH MAX GAIN
                        max_gainratio=Split.temp_max_gainratio;  //ONLY FOR TEMPORARY USE
                        max_entity   =Split.temp_max_entity;
                        split_value  =(float)((Float)tempdata_link[max_entity].get(0)+(Float)tempdata_link[max_entity+1].get(0))/(float)2;
                    }
                }
                else          //DESCRETE EXPAND NODE
                {
                    System.out.println(" -------------- WILL NEVER BE PRINTED    ---------------");
                }

                for(j=start_entity;j<end_entity;j++)     //REMOVING FROM LIST
                {
                    tempdata_link[j].remove();
                    tempdata_link[j].remove();
                }

            }   //END OF THIS I GET COLUMN WITH MAX ENTROPY OUT OF TOTAL COLUMN

            //--------- STORE THIS LINKED LIST -----------------
            /*rule.addLast(max_column) ;              //column no with max entropy
            rule.addLast(max_entity);               //row no is added
            rule.addLast(max_gainratio);            //gain   is added
            rule.addLast(split_value);              //split value is added

            System.out.println("-- RULE -- ->"+rule);*/

            current_node.start_enitiy =start_entity;
            current_node.end_entity   =end_entity;
            current_node.max_column   =max_column;
            current_node.max_entity   =max_entity;
            current_node.max_gainvalue=max_gainratio;
            current_node.split_value  =split_value;

            LinkedList<Mylinklist> []newdataset_link=new LinkedList[end_entity];

            for(i=start_entity;i<end_entity;i++)
            {
                newdataset_link[i]=new LinkedList<Mylinklist>();
            }
            Mylinklist lstemp = null;
            int k=end_entity-1,l=start_entity;
            for(i=start_entity ; i<end_entity ; i++)
            {
                Iterator iterator=dataset_link[i].listIterator();//check value garter or not and divide dataset in 2 pates
                while(iterator.hasNext())
                {
                    lstemp=(Mylinklist)iterator.next();
                    if(lstemp.attribute_no==max_column)
                    {
                         if(Float.compare(split_value,lstemp.data) >= 0)
                         {
                            for(j=0;j<dataset_link[start_entity].size();j++)
                            {
                                newdataset_link[l].addLast(dataset_link[i].get(j));
                            }
                            l++;
                         }
                         else       //< 0
                         {
                            for(j=0;j<dataset_link[start_entity].size();j++)
                            {
                                newdataset_link[k].addLast(dataset_link[i].get(j));
                            }
                            k--;
                         }
                    }
                }
            }

            for(i=start_entity;i<end_entity;i++)
            {   for(j=0;j<newdataset_link[start_entity].size();j++)
                {
                    dataset_link[i].remove();
                }
            }
            for(i=start_entity;i<end_entity;i++)
            {   for(j=0;j<newdataset_link[start_entity].size();j++)
                {
                    dataset_link[i].add(newdataset_link[i].get(j));
                }
            }
              
      /*         lstemp =null;
            for(i=start_entity;i<end_entity;i++)
    {
        Iterator iterator=dataset_link[i].listIterator();
        while(iterator.hasNext())
        {
            lstemp=(Mylinklist)iterator.next();
            System.out.print(lstemp.data+" "+lstemp.attribute_no+" -> ");
        }
        System.out.println();
    }*/

             //-----------------  CALL EBNTROPY ATTRIBUTE FOR UPER & LOWER PART OF DATASET -------------------
             if(current_node.left==null)
             {
                //System.out.println("LEFT NODE MAX_ENTITY+1 "+max_entity+" START_ENTITY> "+current_node.start_enitiy+" END_ENTITY> "+current_node.end_entity+ " MAX_COLUMN> "+current_node.max_column+"<="+current_node.split_value);
                start_entity =current_node.start_enitiy;
                end_entity   =max_entity+1;//maximum row from where to devide...
                //System.out.println("in create rules in left function   START_ENTITY:"+start_entity +"END_ENTITY:"+end_entity+" dataset_leength: "+dataset_link[start_entity].size());
               
                TreeStruct newnode=new TreeStruct(start_entity, end_entity,max_column,max_entity,max_gainratio,split_value);
                newnode.parent=current_node;
                current_node.left=newnode;
                current_node=newnode;
                
                EntropyAttribute EntropyAttribute_object=new EntropyAttribute();
                EntropyAttribute_object.findentropy(dataset_link,isdescrete_index,start_entity,end_entity,current_node);
                current_node=current_node.parent;
             }
             
             if(current_node.right==null)
             {
                start_entity=current_node.left.end_entity;
                end_entity  =current_node.end_entity;
                //System.out.println("RIGHT NODE START_ENTITY> "+start_entity+" END_ENTITY> "+end_entity+ " MAX_COLUMN> "+current_node.max_column+">"+current_node.split_value);

                TreeStruct newnode=new TreeStruct(start_entity, end_entity , max_column , max_entity , max_gainratio , split_value);
                newnode.parent=current_node;
                current_node.right=newnode;
                current_node=newnode;
                
                EntropyAttribute EntropyAttribute_object=new EntropyAttribute();
                EntropyAttribute_object.findentropy(dataset_link , isdescrete_index , start_entity , end_entity , current_node);

                while(current_node.parent.right!=null && current_node.parent!=Main.root.parent)
                {
                    current_node=current_node.parent;
                }
             }
             
        return 0;
    }
}


