package SatConception.Family;

import java.util.ArrayList;

import SatConception.Comps;
import SatConception.Satellite;
import SatConception.Components.Imager;
import SatConception.Components.Locator;

/**
 * This class is a first family of satellites. An instance of this class is a
 * satellite corresponding to this first family.
 * 
 * This first satellites family is composed of 2 Imagers.
 */
public class Fam1 extends Satellite {

    /**
     * A call to the constructor of the Fam1 class creates 2 Imager called "IMAGER1"
     * and "IMAGER2". They are associated to the Fam1 satellite as its
     * subcomponents.
     * 
     * @param name the name of the satellite. It will be called "FAM1" + name
     */
    public Fam1(String name, String fam) {
        super("FAM1" + name, fam);
        ArrayList<Comps> SubList = new ArrayList<>();
        SubList.add(new Imager("IMAGER1"));
        SubList.add(new Imager("IMAGER2"));
        SubList.add(new Locator("LOCATOR1"));
        this.Subsystems = SubList;
    }

    @Override
    public String toString() {
        return "{" + "name=" + this.name + " Subsystems='" + this.Subsystems + "'" + "}";
    }

}
