package SatConception.Family;

import java.util.ArrayList;

import SatConception.Comps;
import SatConception.Satellite;
import SatConception.Components.Imager;
import SatConception.Components.RandomDouble;

/**
 * This class is a second family of satellites. An instance of this class is a
 * satellite corresponding to this second family.
 * 
 * This second satellites family is composed of 1 Imagers and 1 RandomDouble
 * generator.
 */
public class Fam2 extends Satellite {

    /**
     * A call to the constructor of the Fam2 class creates 1 Imager called "IMAGER1"
     * and a RandomDouble called "RANDOM1". They are associated to the Fam1
     * satellite as its subcomponents.
     * 
     * @param name the name of the satellite. It will be called "FAM2" + name
     */
    public Fam2(String name, String fam) {
        super("FAM2" + name, fam);
        ArrayList<Comps> SubList = new ArrayList<>();
        SubList.add(new Imager("IMAGER1"));
        SubList.add(new RandomDouble("RANDOM1"));
        this.Subsystems = SubList;
    }

    @Override
    public String toString() {
        return "{" + "name=" + this.name + " Subsystems='" + this.Subsystems + "'" + "}";
    }

}