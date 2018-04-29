package test;

import java.util.LinkedList;

public class Split {
    static float temp_max_gainratio=-1.1f;
    static int   temp_max_entity;

    public void split(LinkedList []tempdata_link,float dataset_entropy,int start_entity,int end_entity)
    {    
    int i,j=0,law_cnt0=0,law_cnt1=0,high_cnt0=0,high_cnt1=0,temp=0,i_current=start_entity,entity_total=end_entity-start_entity;
    float law_total=0 , high_total=0 , law_entropy=0 , high_entropy=0 ,temp_calc0=0,temp_calc1=0;
    float gain,gainratio,splittinginfo;

    for(i=start_entity;i<end_entity-1;i++)
    {   //0 = NON SPAM       1 = SPAM
        law_cnt0=0; law_cnt1=0;     high_cnt0=0;    high_cnt1=0;

        if(Float.compare( (Float)tempdata_link[i].get(0) , (Float)tempdata_link[i+1].get(0) ) == 0 )
        {   //SKIP
            i_current++;
        }

        else
        {
        for(j=start_entity;j<=i_current;j++)
        {
            temp=(Integer)tempdata_link[j].get(1);
            if(temp==0)
                law_cnt0++;
            else
                law_cnt1++;
        }
        i_current++;

        for(j=i_current;j<end_entity;j++)
        {
            temp=(Integer)tempdata_link[j].get(1);
            if(temp==0)
                high_cnt0++;
            else
                high_cnt1++;
        }

        law_total =law_cnt0 + law_cnt1;
        temp_calc0=(float)law_cnt0 /(float) law_total;
        temp_calc1=(float)law_cnt1 /(float) law_total;

        if(law_cnt0==0)
            law_entropy=0.0f;   //0 OR 1????????
        else if (law_cnt1==0)
            law_entropy=0.0f;
        else
            law_entropy=-(float)(( (float) ((float)temp_calc0 * ((float)((Math.log((float)temp_calc0) / (float)Math.log(2)))))) +
                                 ( (float) ((float)temp_calc1 * ((float)((Math.log((float)temp_calc1) / (float)Math.log(2)))))) );


        high_total=high_cnt0 + high_cnt1;
        temp_calc0=(float)high_cnt0 /(float) high_total;
        temp_calc1=(float)high_cnt1 /(float) high_total;

        if(high_cnt0==0)
            high_entropy=0.0f;
        else if(high_cnt1==0)
            high_entropy=0.0f;
        else
            high_entropy=-(float)(( (float) ((float)temp_calc0 * ((float)((Math.log((float)temp_calc0) / (float)Math.log(2)))))) +
                                  ( (float) ((float)temp_calc1 * ((float)((Math.log((float)temp_calc1) / (float)Math.log(2)))))) );

gain=(float)((float)dataset_entropy-(float)((float)((float)(law_total*law_entropy)+(float)(high_total*high_entropy))/(float)(law_total+high_total)));

splittinginfo = -(float) ( (float)( (float)(i_current-start_entity)* (float)( Math.log((float)(i_current-start_entity)/(float)(entity_total)) /(float) Math.log(2))) +
                           (float)( (float)(end_entity-i_current)  * (float)( Math.log((float)(end_entity-i_current)  /(float)(entity_total)) /(float) Math.log(2))) ) /(float)(entity_total);
//---- check above EQUATION -------
gainratio= (float) (gain / splittinginfo);

            if(Float.compare(gainratio,temp_max_gainratio) > 0)    //first is greater
            {//System.out.print(" i: "+i);
                Split.temp_max_gainratio=gainratio;
                Split.temp_max_entity=i;
                //System.out.println("gain> "+gain+" gainratio > " +gainratio+" splittinginfo> "+splittinginfo);
            }
        }   //else over
        
    }   //FOR LOOP OVER

    }

}    