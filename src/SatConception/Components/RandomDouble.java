package SatConception.Components;

import Datas.Data;
import SatConception.Comps;

/**
 * The RandomDouble class represents a component that just measures random
 * values.
 */
public class RandomDouble extends Comps {

  
      /**
     * 
     * @param name the name of the component
     */
    public RandomDouble(String name) {
        super(name, false);
    }

    /**
     * This method takes a data from the component.
     * 
     * @param satName the name of the satellite
     * @return the obtained data associated that is a random double
     */
    @Override
    protected Data getMeasure(String satName) {
        Data randomData = new Data(satName, this.name, Math.random());
        return randomData;
    }

}
