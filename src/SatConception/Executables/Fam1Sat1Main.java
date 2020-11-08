package SatConception.Executables;

import java.io.*;

import SatConception.Family.Fam1;

public class Fam1Sat1Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        Fam1 fam1Sat1 = new Fam1("SAT1");

        System.out.println("Welcome in the 'FAM1SAT1' Main Program.");
        System.out.println("You basically have nothing to do.");
        System.out.println("Just press Crtl + C when your job is done.");

        while (true) {

            String satDir = "src/CHANNELS/FAM1SAT1/UPLINK";
            File testFile = new File(satDir);
            if (testFile.canRead()) {
                FileReader in = new FileReader(satDir);
                BufferedReader bin = new BufferedReader(in);
                String commandedComp = bin.readLine();
                String instruction = bin.readLine();
                bin.close();
                testFile.delete();

                // We create the DOWNLINK file
                File downlinkFile = new File("src/CHANNELS/FAM1SAT1/DOWNLINK");
                downlinkFile.createNewFile();
                PrintWriter writer = new PrintWriter(downlinkFile);

                // Now we begin to treat the command
                boolean status = fam1Sat1.operation(commandedComp, instruction);
                if (status) {
                    // The operation is successful
                    writer.write("OK");
                    System.out.println(commandedComp + ":" + instruction + " - Success");

                    // the data case
                    if (instruction.equals("DATA")) {

                        File dataFile = new File("src/CHANNELS/FAM1SAT1/DATALINK");
                        dataFile.createNewFile();

                        ObjectOutputStream out = new ObjectOutputStream(
                                new BufferedOutputStream(new FileOutputStream("src/CHANNELS/FAM1SAT1/DATALINK")));

                        // PrintWriter writer2 = new PrintWriter(dataFile);
                        // writer2.write(fam1Sat1.getData(commandedComp).toString());
                        // writer2.close();
                        out.writeObject(fam1Sat1.getData(commandedComp).toString());
                        out.close();
                        System.out.println("Data from " + commandedComp + " correctly sent.");
                    }

                } else {
                    writer.write("KO");
                    System.out.println(commandedComp + ":" + instruction + " - Fail");
                }
                writer.close();

            }

            Thread.sleep(100);

        }

    }

}
