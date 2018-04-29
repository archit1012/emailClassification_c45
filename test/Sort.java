package test;

import java.util.LinkedList;

public class Sort {

    public void sort(LinkedList []tempdata_link,int start_entity,int end_entity)
    {
        int i=0,j=0;
        LinkedList temp_link;

        //SELECTION SORT
        for(i=start_entity; i<end_entity; i++)
        {
            int index_of_min = i;
            for(j=i; j<end_entity; j++)
            {
                if( Float.compare((Float)tempdata_link[index_of_min].getFirst(),(Float)tempdata_link[j].getFirst() ) >0)
                    index_of_min = j;
            }
            /* temp = temp_dataset[i];      //ORIGINAL CODE
            temp_dataset[i] =temp_dataset[index_of_min];
            temp_dataset[index_of_min] = temp; */
            
            temp_link=tempdata_link[i];
            tempdata_link[i]=tempdata_link[index_of_min];
            tempdata_link[index_of_min]=temp_link;
         }

        //Display
        /*System.out.println("SORTED LINKLIST: ");
        for(i=start_entity;i<end_entity;i++)  //row
        {
            System.out.print(" "+ tempdata_link[i].getFirst()+" "+tempdata_link[i].getLast()+" -> ");
        }
        System.out.println();*/
       // System.out.println("proper");
    }
}
