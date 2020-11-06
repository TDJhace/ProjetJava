package Datas;

import java.util.ArrayList;

import SatConception.Satellite;
import java.io.File;

/**
 * The DataCenter class is the major class : it contains and refers all the
 * satellites and the datas associated.
 */
public class DataCenter {

    private ArrayList<Satellite> listSats;
    private ArrayList<Data> listDatas;

    /**
     * @param listSats  is an ArrayList of the Satellites in order to store them
     *                  somewhere
     * @param listDatas is an ArrayList that stored all the taken datas
     */
    public DataCenter() {
        this.listSats = new ArrayList<Satellite>();
        this.listDatas = new ArrayList<Data>();
    }

    /**
     * Just a simple method to add a data to the DataCenter.
     * 
     * @param dat the data to be added
     */
    public void addData(Data dat) {
        if (dat != null) {
            this.listDatas.add(dat);
        }

    }

    /**
     * Just a simple method to add a satellite to the DataCenter.
     * 
     * @param dat the data to be added
     * @throws Exception
     */
    public void addSat(Satellite sat) throws Exception {

        if (sat != null) {
            for (Satellite existingSats : this.listSats) {
                // We first check that there is no satellite with the same name
                if (existingSats.getName().equals(sat.getName())) {
                    throw new IllegalArgumentException(
                            "A satellite with the same name already exists, please change name");
                }
            }
            this.listSats.add(sat);

            String satDir = "src/CHANNELS/" + sat.getName() + "/";
            File file = new File(satDir);
            if (!file.exists() && !file.mkdir()) {
                throw new Exception(
                        "A problem occured during the creation of the satellite directory. Please delete any existing File in the CHANNELS directory.");
            }

        }

    }

    public ArrayList<Data> getDatas() {
        return this.listDatas;
    }

    @Override
    public String toString() {
        return "Satellites : " + listSats.toString() + "\nDatas : " + listDatas.toString();
    }

    /**
     * This method returns a satellite, given its name
     * 
     * @param satName the satellite name
     * @return the satellite associated to this name and stored in the DataCenter
     *         instance. If the name isn't associated to any of the satellites, this
     *         method returns null.
     */
    private Satellite getSatByName(String satName) {
        for (Satellite s : this.listSats) {
            if (s.getName().equals(satName)) {
                return s;
            }
        }
        return null;
    }

    /**
     * This method is the method used to interact with the satellites.
     * 
     * @param satName         the name of the aimed satellite
     * @param compName        the name of the aimed component of the previous
     *                        satellite
     * @param typeInstruction a String containing the instruction : basically, "ON",
     *                        "OFF" or "DATA"
     */
    public void teleOperation(String satName, String compName, String typeInstruction) {

        Satellite aimedSat = this.getSatByName(satName);

        if (aimedSat == null) {
            // The satellite doesn't exist in the database
            System.out.println("Sorry, this satellite is not registered in the database.");
        } else {
            // The satellite exists in the database
            if (aimedSat.operation(compName, typeInstruction)) {
                System.out.println("OK");

                // If the satellite is OK, and that we want a measure, we can do it
                if (typeInstruction.equals("DATA")) {
                    this.addData(aimedSat.getData(compName));
                }

            } else {
                System.out.println("KO");
            }

        }

    }

    public void endProgram() {
        ArrayList<String> listSatNames = new ArrayList<>();
        for (Satellite sat : this.listSats) {
            listSatNames.add(sat.getName());
        }

    }

}
