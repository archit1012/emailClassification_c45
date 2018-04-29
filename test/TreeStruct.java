
package test;
public class TreeStruct {

    int start_enitiy,end_entity,max_column,max_entity;
    float max_gainvalue,split_value;
    TreeStruct left=null,right=null,parent=null;

    public TreeStruct(int start_entity,int end_entity,int max_column,int max_entity,float max_gainvalue,float split_value)
    {
        this.start_enitiy  = start_entity;
        this.end_entity    = end_entity  ;
        this.left  =null;
        this.right =null;
        this.parent=null;

        this.max_column=max_column;
        this.max_entity=max_entity;
        this.split_value=split_value;
        this.max_gainvalue=max_gainvalue;

    }
}
