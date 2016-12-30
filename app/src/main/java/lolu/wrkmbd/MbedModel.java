package lolu.wrkmbd;



/**
 * Created by hp on 23/11/2016.
 */

public class MbedModel {
    //test
    public int id;
    public int valueMbed;

    public MbedModel(int id,int valueMbed){
        this.id=id;
        this.valueMbed=valueMbed;
    }

    public MbedModel(){
        super();
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getValueMbed() {
        return valueMbed;
    }

    public void setValueMbed(int valueMbed) {
        this.valueMbed = valueMbed;
    }
}
