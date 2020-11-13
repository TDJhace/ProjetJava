package SatConception.Components;

import java.util.Random;

import Datas.Data;
import SatConception.Comps;

public  class Locator extends Comps {
    
    public Locator(String name) {
        super(name, false);
    }

    public double generateRandomDoubleinRange(double min, double max){
        if (min >= max) {
            throw new IllegalArgumentException("max has to be greater than min");
        }
    
        Random r = new Random();
        /** The nextInt method of the random class return, uniformly distributed int value between 0 
         * (inclusive) and the specified value (exclusive)*/
        return r.nextDouble()*(max-min) + min;
    }


    @Override
    protected Data getMeasure(String satName) {
        double x = generateRandomDoubleinRange(1.0, 10.0 );
        double y = generateRandomDoubleinRange(1.0, 10.0 );
        double z = generateRandomDoubleinRange(1.0, 10.0 );
        double[] P = {x,y,z};
        Data d = new Data(satName, this.name, P);
        return d;
    }

}