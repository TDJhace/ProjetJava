package SatConception.Components;

import Datas.Data;
import SatConception.Comps;


/**
 * The class Imager represents the components taking a gray picture.
 */
public class Imager extends Comps {

    /**
     * 
     * @param name the name of the Imager
     */
    public Imager(String name) {
        super(name, false);
    }

    /**
     * This method takes a data from the imager. For the moment, it returns a random
     * BufferedImage in grayscale.
     * 
     * @param satName the name of the satellite
     * @return the obtained data associated that is an image
     */
    @Override
    protected Data getMeasure(String satName) {
        int[][] img = new int[3][3];

        Data randomData = new Data(satName, this.name, img);
        return randomData;
    }

}
