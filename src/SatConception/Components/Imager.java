package SatConception.Components;

import java.util.Random;

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
    protected Data getMeasure(String satName){
        int[][] img = new int[5][5];
        for (int[] row : img){
            for(int i=0; i < row.length; i += 1){
                int m = getRandomInt(0, 255);
                row[i] = m;
            }
        }
        Data randomData = new Data(satName, this.name, img);
        return randomData;
    }

    protected int getRandomInt(int min, int max) {

        if (min >= max) {
            /** Added just after the M6 about the exceptions ;). */
            throw new IllegalArgumentException("max has to be greater than min");
        }
    
        Random r = new Random();
        /** The nextInt method of the random class return, uniformly distributed int value between 0 
         * (inclusive) and the specified value (exclusive)*/
        return r.nextInt((max - min) + 1) + min;
    }

}
