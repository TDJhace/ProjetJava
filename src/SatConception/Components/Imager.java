package SatConception.Components;

import Datas.Data;
import SatConception.Comps;
import java.awt.image.BufferedImage;

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
        Data randomData = new Data(satName, this.name, new BufferedImage(400, 400, BufferedImage.TYPE_BYTE_GRAY));
        return randomData;
    }

}
