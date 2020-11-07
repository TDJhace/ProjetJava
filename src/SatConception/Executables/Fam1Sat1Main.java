package SatConception.Executables;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Fam1Sat1Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Welcome in the Satellite Main Program.");
        System.out.println("You basically have nothing to do.");
        System.out.println("Just press Crtl + C when your job is done.");

        while (true) {

            String satDir = "src/CHANNELS/FAM1SAT1/UPLINK";
            File testFile = new File(satDir);
            if (testFile.canRead()) {
                FileReader in = new FileReader(satDir);
                BufferedReader bin = new BufferedReader(in);
                String command = bin.readLine();
                bin.close();
                testFile.delete();
            }

            Thread.sleep(100);

        }

    }

}
