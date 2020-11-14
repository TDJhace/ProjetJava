package Datas;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Tha Data Class is basically the class representing, as its name indicates, a
 * data.
 */
public class Data implements Serializable{
    /**
     * Added to be able to write data on a binary file.
     */
    private static final long serialVersionUID = 1L;
    protected Object value;
    protected String satName;
    protected String compName;
    protected Date timeData;

    /**
     * 
     * As asking by the subject of the project, a data contains several elements :
     * 
     * @param satName  the name of the satellite giving this data
     * @param compName the name of the component giving this data
     * @param value    the value of the data ; here it's an instance of the Object
     *                 class, so it can be anything : a double, an image, etc...
     * @param timeData the date at which the data was taken
     */
    public Data(String satName, String compName, Object value) {
        this.value = value;
        this.satName = satName;
        this.compName = compName;
        Calendar calendar = Calendar.getInstance();
        this.timeData = calendar.getTime();
    }

    @Override
    public String toString() {
        return "{" + "value='"+this.valString()+ "'" + ", satName='" + satName + "'" + ", compName='" + compName
                + "'" + ", timeData='" + timeData + "'" + "}";
    }

    /**
     * It's basically a toString for the attribute value of a data.
     * @return the value convert to a string.
     */

    public String valString(){
        if(this.value instanceof int[][]){
            return (Arrays.deepToString((Object[]) this.value));
        }
        else if(value instanceof double[]){
            return (Arrays.toString((double[]) (this.value)));
        }
        else{
            return this.value.toString();
        }
    }

    /**
     * A simple method to get the type of the data.
     * @return The type of the data depending on the instance of the attribute value.
     */
    
    public String valType(){
        if(this.value instanceof int[][]){
            return("image");
        }
        else if(this.value instanceof Double){
            return("double");
        }
        else if(this.value instanceof double[]){
            return("localisation");
        }
        else{
            return("invalid type");
        }
    }

    public String getSatName() {
        return this.satName;
    }

    public String getCompName() {
        return this.compName;
    }

    public Date getTimeData() {
        return this.timeData;
    }

  

    

}
