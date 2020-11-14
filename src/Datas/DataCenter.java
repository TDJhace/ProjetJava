package Datas;

import java.util.ArrayList;
import java.io.*;

import SatConception.Satellite;

/**
 * The DataCenter class is the major class : it contains and refers all the
 * satellites and the datas associated.
 */
public class DataCenter {

    private ArrayList<Satellite> listSats;
    private ArrayList<String> listDatas;

    /**
     * @param listSats  is an ArrayList of the Satellites in order to store them
     *                  somewhere
     * @param listDatas is an ArrayList that stored all the taken datas
     */
    public DataCenter() {
        this.listSats = new ArrayList<Satellite>();
        this.listDatas = new ArrayList<String>();
    }

    /**
     * Just a simple method to add a data to the DataCenter.
     * 
     * @param dat the data to be added
     */
    public void addData(String dat) {
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

            // We also create the CHANNELS directory corresponding to the created satellite
            String satDir = "src/CHANNELS/" + sat.getName() + "/";
            File file = new File(satDir);
            if (!file.exists() && !file.mkdir()) {
                throw new Exception(
                        "A problem occured during the creation of the satellite directory. Please delete any existing File in the CHANNELS directory.");
            }

        }

    }

    public ArrayList<String> getDatas() {
        return this.listDatas;
    }

    public ArrayList<Satellite> getSats() {
        return this.listSats;
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
     * @throws IOException
     * @throws InterruptedException
     * @throws ClassNotFoundException
     */
    public String teleOperation(String satName, String compName, String typeInstruction)
            throws IOException, InterruptedException, ClassNotFoundException {

        // We use tha data center as a copy of the statement of the global system
        // For example, below, we use the infos in the datacenter to be sure that a
        // correct satname was given

        // Moreover, the obtained datas are still stored in the instance of DataCenter
        // calling this method

        Satellite aimedSat = this.getSatByName(satName);

        if (aimedSat == null) {
            // The satellite doesn't exist in the database
            return "Sorry, this satellite is not registered in the database.";
        } else {
            // The given name is correct, we can create a channel file
            String satDir = "src/CHANNELS/" + satName + "/";

            File uplinkFile = new File(satDir + "UPLINK");
            uplinkFile.createNewFile();

            PrintWriter writer = new PrintWriter(uplinkFile);
            writer.println(compName);
            writer.println(typeInstruction);
            writer.close();

            File downlinkFile = new File(satDir + "DOWNLINK");

            while (!downlinkFile.exists()) {
                Thread.sleep(100); // in order to wait for the downlink file to be created and to block all the
                                   // rest of the commands in the control center
            }

            Thread.sleep(100); // I put a delay in execution here in order to be sure that the file had enough
                               // time to be well created
            // In fact, I had issues without this delay

            FileReader in = new FileReader(satDir + "DOWNLINK");
            BufferedReader bin = new BufferedReader(in);
            String status = bin.readLine();
            bin.close();
            downlinkFile.delete();

            // We now take care of the data (if there is one to get)
            try {
                if (typeInstruction.equals("DATA") && status.equals("OK")) {
                    Thread.sleep(100);
                    Object obtainedData;
                    ObjectInputStream inData = new ObjectInputStream(
                            new BufferedInputStream(new FileInputStream(satDir + "DATALINK")));
                    obtainedData = inData.readObject();
                    inData.close();

                    // just creating an instance of File to delete the DATALINK file
                    File datalink = new File(satDir + "DATALINK");
                    datalink.delete();

                    // adding the data
                    this.addData(obtainedData.toString());
                }
            } catch (NullPointerException e) {
                System.out.println(
                        "Small System Error. The datas weren't comprised. The execution is nonetheless stopped.");
                System.exit(0);
            }

            return status;

        }

    }

    /**
     * This function just deletes all the existing files (not the directories !) in
     * the CHANNELS directory.
     * 
     * @throws Exception
     */
    public void endProgram() throws Exception {

        File referenceFile = new File("src/CHANNELS/");
        String[] listFile = referenceFile.list();

        for (String filename : listFile) {
            File delFile = new File("src/CHANNELS/" + filename);
            if (delFile.exists()) {
                delFile.delete();
            }
        }

    }

}
