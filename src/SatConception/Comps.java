package SatConception;

import Datas.Data;

/**
 * This abstract class represents the subcomponents of the satellites.
 */
public abstract class Comps{

    protected String name;
    protected boolean state;

    /**
     * 
     * @param name  the name of the component
     * @param state the state of the component (on or off)
     */
    protected Comps(String name, boolean state) {
        this.name = name;
        this.state = state;
    }

    /**
     * 
     * @return true if the component is off and turns it on, false if the component
     *         is on
     */
    protected boolean switchOn() {
        if (this.state) {
            return false;
        } else {
            this.state = true;
            return true;
        }
    }

    /**
     * 
     * @return false if the component is off, true if the component is on and turns
     *         it off
     */
    protected boolean switchOff() {
        if (this.state) {
            this.state = false;
            return true;
        } else {
            return false;
        }
    }

    /**
     * 
     * @return true if the component is switched on, else false
     */
    protected boolean data() {
        if (this.state) {
            return true;

        } else {
            return false;
        }
    }

    /**
     * This method aims to get the datas from the components.
     * 
     * @param satName the name of the corresponding satellite
     * @return a data corresponding to the component
     */
    protected abstract Data getMeasure(String satName);

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "{" + " name='" + getName() + "'" + ", state='" + this.state + "'" + "}";
    }

}
