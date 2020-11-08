package Datas;

import java.util.Calendar;
import java.util.Date;

/**
 * Tha Data Class is basically the class representing, as its name indicates, a
 * data.
 */
public class Data implements java.io.Serializable {
    /**
     *
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
        return "{" + " value='" + value.toString() + "'" + ", satName='" + satName + "'" + ", compName='" + compName
                + "'" + ", timeData='" + timeData + "'" + "}";
    }

}
