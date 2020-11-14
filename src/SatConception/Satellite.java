package SatConception;

import java.util.ArrayList;

import Datas.Data;

/**
 * This abstract class contains all the satellites.
 */
public abstract class Satellite {
    protected String name;
    protected ArrayList<Comps> Subsystems;
    public String Famille;

    /**
     * 
     * @param name the name of the satellite
     */
    public Satellite(String name, String fam) {
        this.name = name;
        this.Famille = fam;
    }

    @Override
    public String toString() {
        return "{" + " name='" + getName() + "'" + "}";
    }

    public String getName() {
        return this.name;
    }

    public String getFamily(){
        return this.Famille;
    }

    public ArrayList<Comps> getSubsystems() {
        return this.Subsystems;
    }

    /**
     * This method returns a component of the satellite, given its name
     * 
     * @param compName the component name
     * @return the component associated with this name among those belonging to the
     *         satellite. If the name isn't associated to any of the components,
     *         this method returns null.
     */
    private Comps getCompByName(String compName) {
        for (Comps components : this.Subsystems) {
            if (components.getName().equals(compName)) {
                return components;
            }
        }
        return null;
    }


    /**
     * This method implements the operations that the satellite is going to do in
     * function of the given instruction.
     * 
     * @param compName    the name of the component
     * @param instruction the instruction : "OFF", "ON" or "DATA" for the moment
     * @return true if the the opeartion is successful, else false
     */
    protected boolean operation(String compName, String instruction) {
        Comps compSat = this.getCompByName(compName);
        if (compSat == null) {
            // the component doesn't exist
            return false;
        } else {
            // the component exists
            if (instruction.equals("ON")) {
                return compSat.switchOn();
            } else if (instruction.equals("OFF")) {
                return compSat.switchOff();
            } else if (instruction.equals("DATA")) {
                return compSat.data();
            } else {
                return false; // If the instruction is anything else, it returns false
            }
        }
    }

    /**
     * This method implements the datas measures.
     * 
     * @param compName the name of the component
     * @return a corresponding data
     */
    protected Data getData(String compName) {
        Comps compSat = this.getCompByName(compName);
        if (compSat == null) {
            // the component doesn't exist
            return null;
        } else {
            // the component exists
            return compSat.getMeasure(this.name);
        }
    }

}
