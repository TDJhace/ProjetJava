package SatConception;

import java.io.*;

import SatConception.Family.Fam2;

public class Fam2SatMain {

    public static void main(String[] args) throws IOException {
        Fam2 sat = new Fam2("SAT"); // the only thing that differs in the different satellites executables classes

        String satName = sat.getName();

        System.out.println("Welcome in the '" + satName + "' Main Program.");
        System.out.println("You basically have nothing to do.");
        System.out.println("Just press Crtl + C when your job is done.");

        while (true) {

            String satDir = "src/CHANNELS/" + satName + "/UPLINK";
            File testFile = new File(satDir);
            if (testFile.canRead()) {
                FileReader in = new FileReader(satDir);
                BufferedReader bin = new BufferedReader(in);
                String commandedComp = bin.readLine();
                String instruction = bin.readLine();
                bin.close();
                testFile.delete();

                // We create the DOWNLINK file
                File downlinkFile = new File("src/CHANNELS/" + satName + "/DOWNLINK");
                downlinkFile.createNewFile();
                PrintWriter writer = new PrintWriter(downlinkFile);

                // Now we begin to treat the command
                boolean status = sat.operation(commandedComp, instruction);
                if (status) {
                    // The operation is successful
                    writer.write("OK");
                    System.out.println(commandedComp + ":" + instruction + " - Success");

                    // the data case
                    if (instruction.equals("DATA")) {

                        File dataFile = new File("src/CHANNELS/" + satName + "/DATALINK");
                        dataFile.createNewFile();

                        ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(
                                new FileOutputStream("src/CHANNELS/" + satName + "/DATALINK")));

                        out.writeObject(sat.getData(commandedComp).toString());
                        out.close();
                        System.out.println("Data from " + commandedComp + " correctly sent.");
                    }

                } else {
                    writer.write("KO");
                    System.out.println(commandedComp + ":" + instruction + " - Fail");
                }
                writer.close();

            }

            try {
                Thread.sleep(100); // small delay in order to not saturate the execution
            } catch (InterruptedException e) {
                System.out.println("Fatal error.");
                System.exit(0);
                e.printStackTrace();
            }

        }

    }

}
